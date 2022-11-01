package zxx; /**
 作者     : Zxx
 文件名    : main_UI.py
 创建时间  : 2022/10/29 20:53
 代码内容  ：
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

//import
//import Dis
public class Main_UI {
    private JComboBox comboBox1;
    private JPanel jpanel;
    private JButton button_create;
    private JTable table1;
    private JList list1;
    private JButton button_open_data_dir;
    private JButton button_show;
    private DistributionParameter distributionParameter;
    //分布所需要的参数
    private static ArrayList<HashMap> parameters;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("");
        Main_UI main_ui = new Main_UI();
        jFrame.setContentPane(main_ui.jpanel);

        main_ui.init();

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(800, 600);

    }

    private void init() {

        //写的太差劲了
        this.distributionParameter = new DistributionParameter();
        //获取拥有的分布
        String[] distributions = this.distributionParameter.get_Distribution_files_name();
        //设置下拉菜单
        this.set_comboBox1(distributions);

        this.init_button();
        this.table1.setFont(new Font("宋体", Font.PLAIN, 26));
        this.table1.setRowHeight(30);
        //       this. table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF  );

    }

    //设置下拉菜单
    private void set_comboBox1(String[] distributions) {
        // 添加条目选中状态改变的监听器


        this.comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // 只处理选中的状态
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem().toString() != null) {
                        //                        System.out.println(e.getItem().toString());
                        parameters = distributionParameter.getParameter(e.getItem().toString());
                        setParameterList(parameters);
                        //此处的返回值先不做验证
                    }
                }
            }
        });
        for (String distribution : distributions) {
            this.comboBox1.addItem(distribution);
        }
    }


    private void setParameterList(ArrayList<HashMap> parameters) {
        //定义一维数据作为列标题
        Object[] columnTitle = {"parameter", "type", "value"};
        String[][] new_parameters = new String[parameters.size() + 1][2];

        for (int i = 0; i < parameters.size(); i++) {
            HashMap m = parameters.get(i);
            String pa_name = (String) m.get("name");
            String pa_type = (String) m.get("type");
            new_parameters[i][0] = pa_name;
            new_parameters[i][1] = pa_type;
        }
        new_parameters[parameters.size()][0] = "sample count";
        new_parameters[parameters.size()][1] = "int";
        DefaultTableModel model;//表格模型
        model = new DefaultTableModel(new_parameters, columnTitle);//设置模型
        this.table1.setModel(model);

        //        this.table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

    }


    private void init_button() {
        // 添加按钮的点击事件监听器
        this.button_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                push_button_create();
            }
        });
        this.button_open_data_dir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File f = new File("./data/");
                    Desktop.getDesktop().open(f);
                } catch (Exception ee) {
                    System.out.println("fail");
                }
            }
        });
        this.button_show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("cmd.exe /c notepad.exe ./src/xml/parameter_.xml");
                } catch (IOException e1) {
                    System.out.println("/src/xml/parameter_.xml ");
                }
            }
        });
    }

    private void push_button_create() {
        this.distributionParameter.clear_parameters_value();
        for (int i = 0; i < parameters.size(); i++) {
            String ty = (String) this.table1.getValueAt(i, 1);
            switch (ty) {
                case "int":
                    int v_int = Integer.parseInt((String) this.table1.getValueAt(i, 2));
                    this.distributionParameter.set_parameters_value(v_int);
                    break;
                case "double":
                    double v_double = Double.parseDouble((String) this.table1.getValueAt(i, 2));
                    this.distributionParameter.set_parameters_value(v_double);
                    break;
            }

        }
        int v_int = Integer.parseInt((String) this.table1.getValueAt(parameters.size(), 2));
        this.distributionParameter.set_parameters_value(v_int);
        try {
            String disName = (String) this.comboBox1.getSelectedItem();
            ArrayList<Object> arrayList = this.get_distribution_data(disName);
            this.show_data_list(arrayList);
            this.save_data_as_csv(arrayList, disName);
            this.show_data_img(arrayList, disName);

        } catch (Exception e) {
            System.out.println("fail");
        }
    }


    private ArrayList<Object> get_distribution_data(String cname) throws Exception {
        String className = "distributions." + cname;
        String methodName = "getDistributionData";
        Class clz = Class.forName(className);
        Object obj = clz.newInstance();
        Method m = obj.getClass().getDeclaredMethod(methodName, DistributionParameter.class);
        //调用方法
        ArrayList<Object> result = (ArrayList<Object>) m.invoke(obj, distributionParameter);
        return result;
    }

    private void show_data_list(ArrayList<Object> result) {
        String[] strArr = new String[result.size()];
        for (int i = 0; i < result.size(); i++) {
            strArr[i] = result.get(i).toString();
        }
        //这里需要添加一个滚动，嗯，加了。
        this.list1.setListData(strArr);
    }

    private void save_data_as_csv(ArrayList<Object> result, String distributionName) {

        try {
            CreateCSV createCSV = new CreateCSV(distributionName);
            createCSV.setData(result);
        } catch (Exception e) {
            System.out.println("createCSV fail");
        }
    }

    private void show_data_img(ArrayList<Object> result, String disName) {
        double[] arr = new double[result.size()];
        for (int i = 0; i < result.size(); i++) {
            arr[i] = Double.parseDouble(result.get(i).toString());
        }
        Arrays.sort(arr);
        double left = arr[0] - (Math.abs(arr[0]) * 0.01);
        double right = arr[arr.length - 1] + (Math.abs(arr[arr.length - 1]) * 0.01);

        //        System.out.println(left);
        //        System.out.println(right);
        //显示图像
        SwingUtilities.invokeLater(() -> {
            Ifreechart ex = new Ifreechart(arr, left, right, disName);
            ex.setVisible(true);
        });
    }
}
