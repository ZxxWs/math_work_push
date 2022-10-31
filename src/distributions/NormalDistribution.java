package distributions;

import zxx.DistributionParameter;

import java.util.ArrayList;

public class NormalDistribution {

    private double nextnd;
    private boolean flag = false;

    //计数器
    private static int count1 = 0;
    private static int count2 = 0;
    private static int count3 = 0;
    private static int count4 = 0;

    //均值为avgvalue，标准差为standard_deviation
    //生成符合N(avgvalue,standard_deviation^2)的正态分布
    public double getNDNumberWithParameter(double avgvalue, double standard_deviation) {
        return avgvalue + (getNDNumber() * standard_deviation);
    }

    //将均匀分布的随机数转换生成正态分布的随机数
    public double getNDNumber() {

        if (flag) {
            flag = false;
            return nextnd;//若v1已返回，则返回v2
        } else {
            //使用公式转换为极坐标方式实现
            double v1, v2, s;
            do {
                v1 = 2 * Math.random() - 1; // 生成一个-1到1之间符合均匀分布的数
                v2 = 2 * Math.random() - 1; // 生成一个-1到1之间符合均匀分布的数
                s = v1 * v1 + v2 * v2;
            } while (s >= 1 || s == 0);
            //为了方便计算，将公式转换成极坐标来简化计算
            double multiplier = Math.sqrt(-2 * Math.log(s) / s);
            nextnd = v2 * multiplier;
            flag = true;
            return v1 * multiplier;   //返回v1
        }
    }

    private static void CheckRange(double avgvalue, double sd, int i) {
        if (i < (avgvalue + sd) && i > (avgvalue - sd))//统计一个标准差范围中的出现的样本
        {
            count1++;
        }
        if (i < (avgvalue + 2 * sd) && i > (avgvalue - 2 * sd))//统计二个标准差范围中的出现的样本
        {
            count2++;
        }
        if (i < (avgvalue + 3 * sd) && i > (avgvalue - 3 * sd))//统计三个标准差范围中的出现的样本
        {
            count3++;
        }
        //统计在三个标准差之外的样本
        else {
            count4++;
        }
    }

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        NormalDistribution dd = new NormalDistribution();

        double avgvalue = (double) disp.get_next_parameters_value();
        double standard_deviation = (double) disp.get_next_parameters_value();
        int n = (int) disp.get_next_parameters_value();
        for (int i = 0; i < n; i++) {
            arrayList.add((int) dd.getNDNumberWithParameter(avgvalue, standard_deviation));
        }
        return arrayList;
    }
}

