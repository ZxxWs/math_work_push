package distributions;

import java.util.ArrayList;

import zxx.DistributionParameter;

//import src.*;
public class BetaDistribution {

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        int a = (int) disp.get_next_parameters_value();
        int b = (int) disp.get_next_parameters_value();
        int n = (int) disp.get_next_parameters_value();
        //采样n次
        for (int i = 0; i < n; i++) {
            arrayList.add(BetaDistribution(a, b));
        }
        return arrayList;
    }

    public static double BetaDistribution(double alpha, double beta) {
        double a = alpha + beta;
        double b = Math.sqrt((a - 2) / (2 * alpha * beta - a));
        if (Math.min(alpha, beta) <= 1) {
            b = Math.max(1 / alpha, 1 / beta);
        }
        double c = alpha + 1 / b;
        double W = 0;
        boolean reject = true;
        while (reject) {
            double U1 = Math.random();
            double U2 = Math.random();
            double V = b * Math.log(U1 / (1 - U1));
            W = alpha * Math.exp(V);
            reject = (a * Math.log(a / (beta + W)) + c * V - Math.log(4)) < Math.log(U1 * U1 * U2);
        }
        return (W / (beta + W));
    }

}
