package distributions;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartFrame;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;

import zxx.DistributionParameter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.GridLayout;

public class PoissonDistribution {

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();

        double p = 0.0;
        int lamda;
        //        Scanner scanner = new Scanner(System.in);
        lamda = (int) disp.get_next_parameters_value();
        int n = (int) disp.get_next_parameters_value();
        double a[] = new double[n];
        for (int i = 0; i < n; i++) {
            a[i] = Math.random();
            //            System.out.println("i = " + a[i]);
        }
        double tem_i[] = new double[n];
        int count;
        count = 0;
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                if (p < a[j]) {
                    int ii = 1;
                    for (int k = 1; k <= i; k++) {
                        ii *= k;
                    }
                    p += Math.pow(lamda, i) * Math.pow(Math.E, -lamda) / ii;
                } else {
                    //                    System.out.print((i - 1) + " ");
                    arrayList.add(i - 1);
                    tem_i[count] = i - 1;
                    count++;
                    p = 0.0;
                    break;
                }
            }
        }
        return arrayList;
    }

}
