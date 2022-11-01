package distributions;

import zxx.DistributionParameter;


import java.util.ArrayList;

public class ParetoDistribution {

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        int a = (int) disp.get_next_parameters_value();
        int b = (int) disp.get_next_parameters_value();
        int n = (int) disp.get_next_parameters_value();
        //采样n次
        for (int i = 0; i < n; i++) {
            arrayList.add(ParetoDistribution(a, b));
        }
        return arrayList;
    }

    public static double ParetoDistribution(int k, int x) {
        double d = Math.random();
        return x / Math.pow(1 - d, 1.0 / k);
    }
}