/**
 作者     : Zxx
 文件名    : Ifreechart.py
 创建时间  : 2022/9/30 9:41
 代码内容  ：
 */
package zxx;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 自定义的绘图的Ifreechart类继承了JFrame类
 */
public class Ifreechart extends JFrame {

    private double left;
    private double right;
    private String tableName;

    /**
     * 构造函数，三个参数分别是生成的随机数据、数据的左右端点
     * */
    public Ifreechart(double[] data, double left, double right, String tableName) {
        this.left = left;
        this.right = right;
        this.tableName = tableName;
        initUI(data);
    }

    private void initUI(double[] data) {
        //柱状图内容（down）//////////////////////////////////////////////////////////
        //根据样本点创建数据集
        CategoryDataset dataset = createDataset(data);
        //用过数据集来创建一个chart
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        //这句我也不晓得干啥的，你可以删掉（删掉也不影响程序运行）
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 0));
        //设置图片背景
        chartPanel.setBackground(Color.white);
        add(chartPanel);
        pack();
        setTitle("数据统计");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //柱状图内容（up）////////////////////////////////////////////////////////////
    }

    /**
     将样本点填充的到数条形图的dataset中
     **/
    private CategoryDataset createDataset(double[] data) {
        //样本数据的个数data_len
        int data_len = data.length;
        //设置柱状条的个数interval_count
        int interval_count;
        //根据样本点的个数来决定生成的柱状图的条柱个数
        if (data_len < 100) {
            interval_count = 10;
        } else if (data_len < 1000) {
            interval_count = 20;
        } else {
            interval_count = 30;
        }
        //计算每条柱的区间
        double step = (this.right - this.left) / interval_count;
        //初始化每条柱中样本点个数的计数器，这里应该可以优化一下
        double[] count = new double[interval_count];
        for (int i = 0; i < interval_count; i++) {
            count[i] = 0;
        }
        //填充每条柱中样本点个数的计数器
        for (double datum : data) {
            double tag = this.left;
            int j = 0;
            do {
                tag = tag + step;
                j++;
            } while (datum > tag);
            count[j - 1] += 1;
        }
        //初始化一个数据集对象
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //通过上面的计数器来填充数据集对象
        for (int i = 0; i < interval_count; i++) {
            //l,r是每条柱的左右端点（不是整个样本区间的左右端点）
            double l = (this.left + (step * i));
            double r = (this.left + (step * (i + 1)));
            //此处用count[i] / data_len是将样本点的个数计算为出现频率
            dataset.setValue(count[i] / data_len, "??", String.valueOf(l) + "-" + String.valueOf(r));
        }

        return dataset;
    }

    /**
     通过填充的数据集来生成柱状图
     */
    private JFreeChart createChart(CategoryDataset dataset) {
        //通过图片工厂类来创建一个barchart对象
        JFreeChart barChart = ChartFactory.createBarChart(this.tableName, "", "", dataset, PlotOrientation.VERTICAL, false, true, false);
        return barChart;
    }
}
