package distributions;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import zxx.DistributionParameter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChiSquaredDistribution {
    //    public static void print_plot(double[][] data, String name, String title) {
    //        //创建一个dataset对象，将要化成图表的数据储存起来
    //        DefaultXYDataset xydataset = new DefaultXYDataset();
    //        xydataset.addSeries(title, data);//设置点的图标title一般表示这画的是决策变量还是目标函数值
    //        //创建JFreeChart对象，负责将图表绘制出来
    //        JFreeChart chart = ChartFactory.createScatterPlot(name, "X", "Y", xydataset,
    //                PlotOrientation.VERTICAL, true, true, false);//设置表头，x轴，y轴，name表示问题的类型
    //        //// 利用awt（控件）进行显示
    //        ChartFrame frame = new ChartFrame("卡方分布概率密度函数图像", chart, true);
    //        XYPlot xyplot = (XYPlot) chart.getPlot();
    //        xyplot.setBackgroundPaint(Color.white);//设置背景面板颜色
    //        ValueAxis vaaxis = xyplot.getDomainAxis();
    //        vaaxis.setAxisLineStroke(new BasicStroke(1f));//设置坐标轴粗细
    //        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //        frame.pack();
    //        frame.setVisible(true);
    //    }


    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();

        //        Scanner input = new Scanner(System.in);
        //        System.out.println("请输入自由度k：");
        int k = (int) disp.get_next_parameters_value();
        //        System.out.println("请输入生成样本点个数num：");
        int num = (int) disp.get_next_parameters_value();
        double[][] tmp = new double[2][num];

        org.apache.commons.math3.distribution.ChiSquaredDistribution cd = new org.apache.commons.math3.distribution.ChiSquaredDistribution(k);
        double a[] = cd.sample(num);
        int n = -1;
        for (double i : a) {
            n++;
            tmp[0][n] = i;
            tmp[1][n] = cd.density(i);
            arrayList.add(i);
            //            System.out.println("样本点：" + i);//"样本点" +
            //            System.out.print("概率：");
            //            System.out.println(cd.density(i));//"概率" +
        }
        //        ChiSquaredDistribution cs = new ChiSquaredDistribution();
        //        cs.print_plot(tmp,"y","x");
        return arrayList;
    }
}


