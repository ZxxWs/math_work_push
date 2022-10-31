package distributions;

import zxx.DistributionParameter;

import java.util.ArrayList;

class Normal_Distribution {//正态分布

    public static double Gen() {
        java.util.Random random = new java.util.Random();
        return random.nextGaussian();
    }
}

class Chi_Square_Distribution {
    public static double Gen(int freedom_degrees) {
        double result = 0.0;
        for (int i = 0; i < freedom_degrees; i++) {
            double x = Normal_Distribution.Gen();
            result += x * x;
        }
        return result;
    }
}

class F_Distribution_ {
    public static double Gen(int n1, int n2) {
        return (Chi_Square_Distribution.Gen(n1) / n1) / (Chi_Square_Distribution.Gen(n2) / n2);
    }
}

public class F_Distribution {
    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        int n1 = (int) disp.get_next_parameters_value();
        int n2 = (int) disp.get_next_parameters_value();
        int num = (int) disp.get_next_parameters_value();
        for (int i = 0; i < num; i++)
            arrayList.add(F_Distribution_.Gen(n1, n2));
        return arrayList;
    }
}
