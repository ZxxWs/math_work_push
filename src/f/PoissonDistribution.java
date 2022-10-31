package f;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartFrame;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.awt.GridLayout;

public class PoissonDistribution {
    public static void main(String[] args) {
        double a[] = new double[1000];
        for (int i = 0; i < 1000; i++) {
            a[i] = Math.random();
            //            System.out.println("i = " + a[i]);
        }
        double p = 0.0;
        int lamda;
        Scanner scanner = new Scanner(System.in);
        lamda = scanner.nextInt();
        double tem_i[] = new double[1000];
        int count;
        count = 0;
        for (int j = 0; j < 1000; j++) {
            for (int i = 0; i < 100000; i++) {
                if (p < a[j]) {
                    int ii = 1;
                    for (int k = 1; k <= i; k++) {
                        ii *= k;
                    }
                    p += Math.pow(lamda, i) * Math.pow(Math.E, -lamda) / ii;
                } else {
                    System.out.print((i - 1) + " ");
                    tem_i[count] = i - 1;
                    count++;
                    p = 0.0;
                    break;
                }
            }
        }

        int out[] = new int[1000];
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < count; j++) {
                if (i == tem_i[j]) {
                    out[i]++;
                }
            }
            System.out.println("times" + out[i]);
        }
        System.out.println("hello");
    }
   
}
