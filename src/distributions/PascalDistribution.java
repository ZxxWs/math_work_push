package distributions;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Pair;
import zxx.DistributionParameter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;


public class PascalDistribution{
    /**
     * 存储已经计算过的组合数
     */
    static Map<String, Long> map = new HashMap<>();

    /**
     * 计算组合数
     *
     * @param m m个
     * @param n 取n个
     * @return 结果
     */
    public static long comb(int m, int n) {
        String key = m + "," + n;
        if (n == 0)
            return 1;
        if (n == 1)
            return m;
        if (n > m / 2)
            return comb(m, m - n);
        if (n > 1) {
            if (!map.containsKey(key))
                map.put(key, comb(m - 1, n - 1) + comb(m - 1, n));
            return map.get(key);
        }
        return -1;
    }

    /**
     * @param k 失败次数
     * @param r 成功次数
     * @param p 成功概率
     * @return 帕斯卡概率质量函数
     */
    public static double pascal_pmf(int k, int r, double p) {
        return comb(k + r - 1, k) * FastMath.pow(p, r) * FastMath.pow(1 - p, k);
    }

    /**
     * @param k 失败次数
     * @param r 成功次数
     * @param p 成功概率
     * @return 舍选法生成一个符合帕斯卡分布的随机数
     */
    public static Pair<Integer, Double> pascal_rand(int k, int r, double p) {
        Random random = new Random();
        int a;
        double b;
        while (true) {
            a = random.nextInt(r + k);
            b = random.nextDouble();
            if (b <= pascal_pmf(a, r, p)) {
                return new Pair<>(a, b);
            }
        }
    }
    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        int r = (int) disp.get_next_parameters_value();
        double p = (double) disp.get_next_parameters_value();
        int t = (int) disp.get_next_parameters_value();
        //采样t次
        for (int i = 0; i < t; i++) {
            Pair<Integer, Double> res = pascal_rand(i, r, p);
            arrayList.add(res.getFirst());
        }
        return arrayList;
    }
}