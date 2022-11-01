package distributions;

import zxx.DistributionParameter;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import static java.lang.Math.log;

public class GumbelDistribution {
    //第一步 产生0到1之间的服从均匀分布的随机数
    public static double getRandom() {
        Random random = new Random();
        return random.nextDouble();//random.nextDouble() 传回0.0到1.0之间的随机数。
    }

    //第二步  gumbel 分布的CDF的反函数实现
    public static double inv_gumbel_cdf(double u, double beta) {
        double y = getRandom();
        System.out.println("y:" + y);
        return u - beta * log(-log(y));
    }

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();

        double u = (double) disp.get_next_parameters_value();

        double beta = (double) disp.get_next_parameters_value();

        int n = (int) disp.get_next_parameters_value();

        for (int i = 0; i < n; i++)
            arrayList.add(inv_gumbel_cdf(u, beta));

        return arrayList;
    }
}

