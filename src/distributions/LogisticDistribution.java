package distributions;

import zxx.DistributionParameter;

import java.util.ArrayList;

public class LogisticDistribution {

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        int min = (int) disp.get_next_parameters_value();
        int max = (int) disp.get_next_parameters_value();
        System.out.println("请输入参数μ和γ?");
        double mu = (double) disp.get_next_parameters_value();
        double ga = (double) disp.get_next_parameters_value();
        int rand_num = (int) disp.get_next_parameters_value();
        return Logistic_Distribution(rand_num, min, max, mu, ga);

    }

    public static ArrayList<Object> Logistic_Distribution(int m, int min, int max, double mu, double ga) {

        ArrayList<Object> arrayList = new ArrayList<>();
        double x, y;
        for (int i = 0; i < m; i++) {
            x = Math.random() * (max - min + 1) + min;
            y = 1 / (1 + Math.exp(-(x - mu) / ga));//产生随机数并根据接口代入到函数中，随机数在0-1之间
            if (!Double.isNaN(y)) {
                arrayList.add(y);
            }
        }
        return arrayList;
    }

}
