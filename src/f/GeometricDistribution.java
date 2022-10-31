package f;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GeometricDistribution {
    public void setA(double[][] a) {
        this.a = a;
    }

    public void setB(double[][] b) {
        this.b = b;
    }

    public boolean isOrigin() {
        return origin;
    }

    public void setOrigin(boolean origin) {
        this.origin = origin;
    }

    double[][] a = {{}, {}};
    double[][] b = {{}, {}};
    boolean origin = false;

    public void geom_cdf(double p) {
        double before = 0, after, count = 0;
        List<Double> li_i = new ArrayList<>();
        List<Double> li_count = new ArrayList<>();
        int i;
        for (i = 0; ; i++) {
            double result = Math.pow((1 - p), i) * p;
            after = result;
            if ((before == after)||i==30)
                break;
//            System.out.println("number: " + i + " count: " + result);
            System.out.println(before-after);
            before = after;
            count += before;
            li_i.add(i + 1.0);
            li_count.add(count);
        }
        System.out.println(li_i);
        System.out.println(li_count);
        Object array_u[] = li_count.toArray();
        Object array_i[] = li_i.toArray();
        double[][] tmp = new double[2][i];
        for (int j = 0; j < i; j++) {
            tmp[0][j] = (double) array_i[j];
            tmp[1][j] = (double) array_u[j];
        }
        setB(tmp);
    }

    public void geom_random() {
        Scanner sc = new Scanner(System.in);
//        输入参数
        System.out.println("input num and p");
        int num = sc.nextInt();
        double p = sc.nextDouble();
//        是否打印分布函数图像
        System.out.println("print cdf? y/n");
        String ans = sc.next();
        if (ans.equals("y") || ans.equals("Y")) {
            this.setOrigin(true);
            geom_cdf(p);
        }
        List<Double> li = new ArrayList<>();
        List<Double> li_u = new ArrayList<>();
//        套用公式分别求出对应的X
        for (int i = 0; i < num; i++) {
//            生成随机数U
            double U = Math.random();
//            加入随机数列表
            li_u.add(U);
//            反解出U对应的X
            double tmp = Math.log(1 - U) / Math.log(1 - p);
            double out = Math.floor(tmp) + 1;
//            将生成的X加入列表
            li.add(out);
        }
        System.out.println(li_u);
        System.out.println(li);
        Object array_u[] = li_u.toArray();
        Object array_i[] = li.toArray();
        double[][] tmp = new double[2][num];
        for (int i = 0; i < num; i++) {
            tmp[0][i] = (double) array_i[i];
            tmp[1][i] = (double) array_u[i];
        }
        this.setA(tmp);

    }

    public static void main(String[] args) {
        GeometricDistribution geometric2Dplot = new GeometricDistribution();
        geometric2Dplot.geom_random();
//        geometric2Dplot.plot_2D(geometric2Dplot.a, geometric2Dplot.b, "geom", "random points");
    }
}
