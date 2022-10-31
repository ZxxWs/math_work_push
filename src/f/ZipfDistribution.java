package distributions;

import zxx.DistributionParameter;

import java.util.*;

public class ZipfDistribution {
    static NavigableMap<Double, Integer> map;
    private static final double Constant = 1.0;
    private int n;
    private double a;
    private static double P[] = new double[1000];

    public void MyZipf(int R, double F) {
        a = F;
        n = R;
        map = computeMap(n, a);   //n是样本容量，a是曲线斜率， map存储的数据，都是以键值对的形式存在的，键不可重复，值可以重复。
    }

    public static NavigableMap<Double, Integer> computeMap(int size, double skew) {
        NavigableMap<Double, Integer> map = new TreeMap<Double, Integer>();//size是rank的个数，skew是数据倾斜程度，
        //总频率
        double div = 0;//总频率
        for (int i = 1; i <= size; i++) {
            //具体i位置对应的概率
            P[i - 1] = (Constant / Math.pow(i, skew));
            div += P[i - 1];
        }
        //计算每个rank对应的y值，所以靠前rank的y值区间远比后面rank的y值区间大
        double sum = 0;
        for (int i = 1; i <= size; i++) {
            double p = (Constant / Math.pow(i, skew)) / div;
            P[i - 1] = p;
            System.out.println(p);
            sum += p;
            map.put(sum, i - 1);
            //向map添加键值和对应值
        }
        return map;
    }

    public int[] sample() {//生成样本点
        int[] ins = new int[n];
        Arrays.fill(ins, 0);

        Random random = new Random(0);//用0作为种子创建一个随机数生成器

        double value;
        int y;
        for (int i = 0; i < 100000; i++) {
            value = random.nextDouble();//返回一个大于或等于 0.0 且小于 1.0 的随机浮点数。
            y = map.ceilingEntry(value).getValue() + 1;//返回大于等于给定键的最小键的键关联的Map.Entry对象，如果不存在这样的键，则返回null?
            ins[y - 1]++;
        }
        return ins;
    }

    public double[] calFrequency() {//计算样本在各个区间出现的频率
        int[] visit = this.sample();//获取访问各区间的次数
        double[] fre = new double[n];//新建数组，用于存放访问各区间的频率

        int visit_sum = 0;//用于计算访问总次数

        for (int i = 0; i < n; i++) {//计算访问总次数
            visit_sum = visit_sum + visit[i];
        }

        for (int j = 0; j < n; j++) {//计算访问各区间的频率
            fre[j] = ((double) visit[j]) / ((double) visit_sum);
        }
        return fre;
    }

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        int n;//排名数
        double a;//参数
        a = (double) disp.get_next_parameters_value();
        n = (int) disp.get_next_parameters_value();
        ZipfDistribution zf = new ZipfDistribution();
         zf.MyZipf(n, a);

        //        zf.drawZipf("frequency","F","rank","frequency");
        //        zf.drawZipf("probability","P","rank","probability");
        return arrayList;
    }
}