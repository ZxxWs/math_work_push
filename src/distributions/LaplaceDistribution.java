package distributions;

import zxx.DistributionParameter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LaplaceDistribution {

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();


        //        Scanner input = new Scanner(System.in);
        //        System.out.print("请输入：n,mu,beta:");
        double mu = (double) disp.get_next_parameters_value();
        double beta = (double) disp.get_next_parameters_value();
        int n = (int) disp.get_next_parameters_value();
        double[] result = lap(n, mu, beta);

        for (int i = 0; i < n; i++) {
            arrayList.add(result[i]);
        }
        return arrayList;

    }


    public static double[] lap(int n, double mu, double beta) {
        Random random = new Random();
        double[] arr = new double[n];
        int i = 0;
        while (n > 0) {
            double x = random.nextDouble();
            arr[i] = inverseCumulativeProbability(x, mu, beta);
            i++;
            n--;
        }
        return arr;
    }


    public static double inverseCumulativeProbability(double p, double mu, double beta) {

        double x = (p > 0.5) ? -Math.log(2.0 - 2.0 * p) : Math.log(2.0 * p);
        return mu + beta * x;
    }


}
