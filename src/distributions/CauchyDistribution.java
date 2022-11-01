package distributions;
import zxx.DistributionParameter;

import java.io.*;

import java.util.ArrayList;

//import src.*;
public class CauchyDistribution {

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        double a = (double) disp.get_next_parameters_value();
        double b = (double) disp.get_next_parameters_value();
        int n = (int) disp.get_next_parameters_value();
        //采样n次
        for (int i = 0; i < n; i++) {
            arrayList.add(cauchy(a, b));
        }
        return arrayList;
    }

    public static double cauchy(double u, double r) {
        double x;
        x = u + r * Math.tan(Math.PI * (Math.random() - 0.5));
        return x;
        }
}

