package distributions;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;
import zxx.DistributionParameter;

import java.util.ArrayList;

public class GammaDistribution extends AbstractRealDistribution {
    //    默认逆累积概率准确性。
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
    //    可序列化的版本标识符。
    private static final long serialVersionUID = 20120524L;
    private final double shape;//形状参数alpha
    private final double scale;//逆尺度参数beta

    //    shape + g + 0.5的常数值,g是Lanczos常数
    private final double shiftedShape;

    //    当自然计算不发生溢出时，将使用此前置因子。
    private final double densityPrefactor1; //在density中使用
    private final double logDensityPrefactor1;//在logDensity中使用

    //    当自然计算发生溢出时，将使用此前置因子。
    private final double densityPrefactor2;//density
    private final double logDensityPrefactor2;//logDensity

    //    {@code y = x / scale}的下限，用于选择{@link #density（double）}中的计算方法。 对于{@code y <= minY}，自然计算会溢出。
    private final double minY;
    //    {@code log（y）}（{@code y = x / scale}）的上限，用于选择{@link #density（double）}中的计算方法。对于{@code log（y）> = maxLogY}，自然计算会溢出。
    private final double maxLogY;
    //    逆累积概率精度
    private final double solverAbsoluteAccuracy;

    //    使用指定的形状和比例参数值创建新的gamma分布。
    public GammaDistribution(RandomGenerator rng, double shape, double scale, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        super(rng);//alpha,beta>=0
        if (shape <= 0.0D) {//非法输入，报错
            throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, shape);//Construct the exception with a shape(specific) context.
        } else if (scale <= 0.0D) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SCALE, scale);
        } else {
            this.shape = shape;
            this.scale = scale;
            this.solverAbsoluteAccuracy = inverseCumAccuracy;
            this.shiftedShape = shape + 4.7421875D + 0.5D;
            double aux = 2.718281828459045D / (6.283185307179586D * this.shiftedShape);
            this.densityPrefactor2 = shape * FastMath.sqrt(aux) / Gamma.lanczos(shape);
            this.logDensityPrefactor2 = FastMath.log(shape) + 0.5D * FastMath.log(aux) - FastMath.log(Gamma.lanczos(shape));
            this.densityPrefactor1 = this.densityPrefactor2 / scale * FastMath.pow(this.shiftedShape, -shape) * FastMath.exp(shape + 4.7421875D);
            this.logDensityPrefactor1 = this.logDensityPrefactor2 - FastMath.log(scale) - FastMath.log(this.shiftedShape) * shape + shape + 4.7421875D;
            this.minY = shape + 4.7421875D - FastMath.log(1.7976931348623157E308D);
            this.maxLogY = FastMath.log(1.7976931348623157E308D) / (shape - 1.0D);
        }
    }

    /**
     @deprecated
     */
    @Deprecated
    public double getAlpha() {
        return this.shape;
    }

    public double getShape() {
        return this.shape;
    }

    /**
     @deprecated
     */
    @Deprecated
    public double getBeta() {
        return this.scale;
    }

    public double getScale() {
        return this.scale;
    }

    //    返回在指定点x处评估的此分布的概率密度函数（PDF）。
    //    如果在x处不存在导数，则应返回适当的替换值，
    //    例如 Double.POSITIVE_INFINITY，Double.NaN或差商的下限或上限。
    public double density(double x) {
        if (x < 0.0D) {
            return 0.0D;
        } else {
            double y = x / this.scale;
            if (y > this.minY && FastMath.log(y) < this.maxLogY) {//溢出
                return this.densityPrefactor1 * FastMath.exp(-y) * FastMath.pow(y, this.shape - 1.0D);
            } else {
                double aux1 = (y - this.shiftedShape) / this.shiftedShape;
                double aux2 = this.shape * (FastMath.log1p(aux1) - aux1);
                double aux3 = -y * 5.2421875D / this.shiftedShape + 4.7421875D + aux2;
                return this.densityPrefactor2 / x * FastMath.exp(aux3);
            }
        }
    }

    //    返回在指定点x处评估的此分布的概率密度函数（PDF）的自然对数。
    public double logDensity(double x) {
        if (x < 0.0D) {
            return -1.0D / 0.0;
        } else {
            double y = x / this.scale;
            if (y > this.minY && FastMath.log(y) < this.maxLogY) {
                return this.logDensityPrefactor1 - y + FastMath.log(y) * (this.shape - 1.0D);
            } else {
                double aux1 = (y - this.shiftedShape) / this.shiftedShape;
                double aux2 = this.shape * (FastMath.log1p(aux1) - aux1);
                double aux3 = -y * 5.2421875D / this.shiftedShape + 4.7421875D + aux2;
                return this.logDensityPrefactor2 - FastMath.log(x) + aux3;
            }
        }
    }

    //对于值按此分布分布的随机变量x，此方法返回p（x<=x）。
    public double cumulativeProbability(double x) {
        double ret;
        if (x <= 0.0D) {
            ret = 0.0D;
        } else {
            ret = Gamma.regularizedGammaP(this.shape, x / this.scale);
        }

        return ret;
    }

    //    返回逆累积计算的求解器绝对精度。
    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    //    使用此方法获得此分布平均值的数值。
    public double getNumericalMean() {
        return this.shape * this.scale;
    }

    //    使用此方法获取此分布的方差的数值。
    public double getNumericalVariance() {
        return this.shape * this.scale * this.scale;
    }

    //    访问支撑的下限。
    public double getSupportLowerBound() {
        return 0.0D;
    }

    //    访问支撑的上限
    public double getSupportUpperBound() {
        return 1.0D / 0.0;
    }

    //    支撑的下限是否在密度函数的范围内。
    public boolean isSupportLowerBoundInclusive() {
        return true;
    }

    //    支撑的上限是否在密度函数的范围内。
    public boolean isSupportUpperBoundInclusive() {
        return false;
    }

    //    使用此方法可获取有关支撑件是否已连接的信息
    public boolean isSupportConnected() {
        return true;
    }

    //    此实现使用以下算法：
    public double sample() {
        double d;
        double bGS;
        double x;
        double v;
        double x2;
        if (this.shape < 1.0D) {
            label29:
            do {
                do {
                    d = this.random.nextDouble();
                    bGS = 1.0D + this.shape / 2.718281828459045D;
                    x = bGS * d;
                    if (x <= 1.0D) {
                        v = FastMath.pow(x, 1.0D / this.shape);
                        x2 = this.random.nextDouble();
                        continue label29;
                    }

                    v = -1.0D * FastMath.log((bGS - x) / this.shape);
                    x2 = this.random.nextDouble();
                } while (x2 > FastMath.pow(v, this.shape - 1.0D));

                return this.scale * v;
            } while (x2 > FastMath.exp(-v));

            return this.scale * v;
        } else {
            d = this.shape - 0.3333333333333333D;
            bGS = 1.0D / (3.0D * FastMath.sqrt(d));

            double u;
            do {
                do {
                    x = this.random.nextGaussian();
                    v = (1.0D + bGS * x) * (1.0D + bGS * x) * (1.0D + bGS * x);
                } while (v <= 0.0D);

                x2 = x * x;
                u = this.random.nextDouble();
                if (u < 1.0D - 0.0331D * x2 * x2) {
                    return this.scale * d * v;
                }
            } while (FastMath.log(u) >= 0.5D * x2 + d * (1.0D - v + FastMath.log(v)));

            return this.scale * d * v;
        }
    }


    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        // write your code here
        double shape;
        double scale;
        double inverseCumAccuracy = 1.0E-9D;
        RandomGenerator randomGenerator = new JDKRandomGenerator();
        //        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入shape：");//alpha
        shape = (double) disp.get_next_parameters_value();
        System.out.println("请输入scale：");//beta
        scale = (double) disp.get_next_parameters_value();
        GammaDistribution gammaDistribution = new GammaDistribution(randomGenerator, shape, scale, inverseCumAccuracy);

        int count = (int) disp.get_next_parameters_value();
        for (int x = 0; x < count; x++) {
            double y = gammaDistribution.cumulativeProbability(x);
            arrayList.add(y);
        }
        return arrayList;
    }
}
