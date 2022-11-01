package distributions;

import org.apache.commons.math3.special.Gamma;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import zxx.DistributionParameter;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
public class TDistribution {
    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        int n = (int) disp.get_next_parameters_value();//自由度
        int t = (int) disp.get_next_parameters_value();
        //采样t次
        for (int i = 0; i < t; i++) {
            arrayList.add(TDistribution(n));
        }
        return arrayList;
    }
    static long x0=System.nanoTime();
    public static double fx(double x, int n){//概率密度函数
        return (Gamma.gamma((n+1)*1.0/2))*Math.pow((1+(x*x)/n),-1*(n+1)*1.0/2)
                /(Math.sqrt(n*Math.PI) * Gamma.gamma(n*1.0/2));
    }
    public static double LCGrandom(){//线性同余法后取后六位数
        long a_random = 25214903917L;
        int c_random =11;
        long m_random = 281474976710656L;
        long x1 = (a_random*x0 + c_random)%m_random;
        x0=x1;
        BigDecimal sx = BigDecimal.valueOf(x1 % 1000000);//避免精度损失
        BigDecimal si = BigDecimal.valueOf(0.000001);
        return sx.multiply(si).doubleValue();//相乘并将结果转为double

    }
    public static double TDistribution(int n) {
        //自由度n
        //x单侧取值范围
        int x_range = 5;
        //区间个数
        int interval = 50;
        //区间间隔
        double space = x_range*2.0/interval;
        //概率密度最大值
        double max = fx(0,n);
        int [] arr=new int[interval];
        while (true){
            double x = BigDecimal.valueOf(LCGrandom()).multiply(BigDecimal.valueOf(x_range)).doubleValue();//生成随机数
            double y = BigDecimal.valueOf((LCGrandom()/2+0.5)).multiply(BigDecimal.valueOf(max)).doubleValue();//[0,max]
            double ff=fx(x,n);
            if( y <= ff){
                return x;
            }
        }
    }
}