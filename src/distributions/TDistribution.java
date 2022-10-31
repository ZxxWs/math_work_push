package distributions;

import org.apache.commons.math3.special.Gamma;
import zxx.DistributionParameter;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartFrame;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.CategoryAxis;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.renderer.category.BarRenderer;
//import org.jfree.data.category.DefaultCategoryDataset;
import java.math.BigDecimal;
import java.util.ArrayList;

public class TDistribution {
    static long x0 = System.nanoTime();

    public static double fx(double x, int n) {//概率密度函数
        return (Gamma.gamma((n + 1) * 1.0 / 2)) * Math.pow((1 + (x * x) / n), -1 * (n + 1) * 1.0 / 2) / (Math.sqrt(n * Math.PI) * Gamma.gamma(n * 1.0 / 2));
    }

    public static double LCGrandom() {//线性同余法后取后六位数
        long a_random = 25214903917L;
        int c_random = 11;
        long m_random = 281474976710656L;
        long x1 = (a_random * x0 + c_random) % m_random;
        x0 = x1;
        BigDecimal sx = BigDecimal.valueOf(x1 % 1000000);//避免精度损失
        BigDecimal si = BigDecimal.valueOf(0.000001);
        return sx.multiply(si).doubleValue();//相乘并将结果转为double

    }

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        //输入自由度n
        //        Scanner scanner = new Scanner(System.in);
        //        System.out.println("输入自由度n");

        //输入样本点个数t
        //        System.out.println("输入样本点个数");
        int t = (int) disp.get_next_parameters_value();
        int n = (int) disp.get_next_parameters_value();
        //x单侧取值范围
        int x_range = 5;
        //区间个数
        int interval = 50;
        //区间间隔
        double space = x_range * 2.0 / interval;
        //概率密度最大值
        double max = fx(0, n);
        //频数数组
        double[] brr = new double[t];
        int[] arr = new int[interval];
        for (int i = 0; i < t; ) {
            double x = BigDecimal.valueOf(LCGrandom()).multiply(BigDecimal.valueOf(x_range)).doubleValue();//生成随机数
            double y = BigDecimal.valueOf((LCGrandom() / 2 + 0.5)).multiply(BigDecimal.valueOf(max)).doubleValue();//[0,max]
            double ff = fx(x, n);
            if (y <= ff) {
                for (double j = -1 * x_range; j < x_range; j = j + space) {
                    if (x >= j && x < j + space) {
                        arr[(int) ((j + x_range) / space)]++;
                        brr[i] = x;
                        arrayList.add(x);
                        i++;
                        break;
                    }
                }
            }
        }
        //输出BRR
        return arrayList;
    }
}