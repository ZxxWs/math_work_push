package f;


import zxx.DistributionParameter;

import java.util.ArrayList;

public class Cauchydistribution {
    //设置柯西分布对应的累积分布函数为F(x ; x0_gamma)的两个必要参数
    //	@x0  为定义分布峰值位置的位置参数
    // @gamma  为最大值一半处的一半宽度的尺度参数
    public double gamma;
    public double x0;
    int n = 10;

    double x[] = new double[n];    //用于存放生成的随机数
    double y[] = new double[n];    //用于存放对应的概率密度值
    double y_0[] = new double[n];  //存放对应的分布函数值

    public Cauchydistribution(double gamma, double x0) {
        this.gamma = gamma;
        this.x0 = x0;
    }

    //求柯西分布对应的累积分布函数的反函数[y=gamma*tan((x-1/2)*pi)+x0]；
    public double inverseCumulativeProbability(double x) {
        double y = gamma * Math.tan((x - 0.5) * Math.PI) + x0;
        return y;
    }

    //利用（0，1）之间的均匀分布生成满足柯西分布的随机数
    public void CauchyDataGenerator() {
        System.out.println("x为原始随机数:");
        System.out.println("y为生成随机数:");
        for (int i = 1; i <= n; i++) {
            double x1 = Math.random();
            x[i - 1] = inverseCumulativeProbability(x1);
            y_0[i - 1] = x1;  //输出图像为分布函数图像时
            y[i - 1] = densityFunction(x[i - 1]);  //输出函数图像为概率密度函数时
            System.out.println("原始随机数:" + x1 + "  生成随机数" + x[i - 1] + "  概率密度" + y[i - 1]);

        }
    }


    //柯西分布的概率密度函数
    public double densityFunction(double x) {
        double y = (1 / (1 + Math.pow(((x - x0) / gamma), 2))) / (gamma * Math.PI);
        return y;
    }


    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();

        //        System.out.println("满足柯西分布的伪随机数生成：");
        Cauchydistribution ca = new Cauchydistribution(1, 0);
        ca.CauchyDataGenerator();
        //        ca.show();
        return arrayList;
    }

}
