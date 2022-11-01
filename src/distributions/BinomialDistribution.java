package distributions;

import zxx.DistributionParameter;

import java.math.BigDecimal;
import java.lang.*;
import java.util.ArrayList;

public class BinomialDistribution {
    public ArrayList<Object> getDistributionData(DistributionParameter disp) {


        BinomialDistribution b = new BinomialDistribution();

        double p = (double) disp.get_next_parameters_value();
        int m = (int) disp.get_next_parameters_value();
        int n = (int) disp.get_next_parameters_value();


        return b.Binom(m, p, n);
    }

    public static double combination(double n, double k) {
        double n1 = k;
        double n2 = n - k;
        double numerator = 1;
        double denominator = 1;
        while (n > n2) {       //计算分子
            numerator = numerator * n;
            n--;
        }
        while (n1 > 0) {       //计算分母
            denominator = denominator * n1;
            n1--;
        }
        return numerator / denominator;
    }

    //计算二项分布P(X=k)的各个值,(k=0,1,2....n)
    public static double distribution(int n, int k, double p) {
        double a = 1;
        double b = 1;
        double c = combination(n, k);
        while (n - k > 0) {   //计算(1-p)的(n-k)次方
            b = b * (1 - p);
            n--;
        }
        while (k > 0) {    //计算p的k次方
            a = a * p;
            k--;
        }
        return a * b * c;
    }

    public static ArrayList<Object> Binom(int n, double p, int m) {
        double a;
        double probability;
        double F;
        double U;
        int X;
        ArrayList<Object> arrayList = new ArrayList<>();
        BinomialDistribution b = new BinomialDistribution();
        for (int i = 0; i < m; i++) {
            U = Math.random();
            a = p / (1 - p);
            probability = Math.pow(1 - p, n);
            F = probability;
            for (int k = 0; k < n; k++) {
                if (U < F) {
                    X = k;
                    arrayList.add(BigDecimal.valueOf(b.distribution(n, X, p)));
                    //                    System.out.println(X + " " + BigDecimal.valueOf(b.distribution(100, X, 0.6)));
                    break;
                } else {
                    probability = a * (n - k) / (k + 1) * probability;
                    F = F + probability;
                }
            }
        }
        return arrayList;
    }
}
