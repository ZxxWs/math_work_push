package distributions;

import zxx.DistributionParameter;

import java.util.Scanner;

import java.util.ArrayList;


//import src.*;
public class WeibullDistribution {

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        double a = (double) disp.get_next_parameters_value();
        double b = (double) disp.get_next_parameters_value();
        int n = (int) disp.get_next_parameters_value();
        //采样n次
        for (int i = 0; i < n; i++) {
            arrayList.add(weibull(a, b));
        }
        return arrayList;
    }

    public static double random() {
        long x0 = System.nanoTime();
        long a = 25214903917L;
        int c = 11;
        long m = 281474976710656L;
        long x1 = Math.abs((a * x0 + c) % m);
        double x2 = x1 / 1E14;
        return x2;
    }

    //反函数x=λ[−ln(1−μ)]1/k

    public static double weibull(double k, double t) {
        double x = random();
        while (x > 1)
            x = random();
        double res = t * (Math.pow(-Math.log(1 - x), 1 / k));
        return res;
    }
}
