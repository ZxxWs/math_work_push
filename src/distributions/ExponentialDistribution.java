package distributions;

import zxx.DistributionParameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


import java.util.*;

public class ExponentialDistribution {

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        double x, y, z, f;
        double lamda;

        //分布函数和密度函数
        lamda = (double) disp.get_next_parameters_value();

        //        System.out.println("请输入样本数量：");
        int n = (int) disp.get_next_parameters_value();

//        List<Double> listx = new ArrayList<>(); //存储样本点
//        List<Double> listF = new ArrayList<>(); //存储分布函数
//        List<Double> listf = new ArrayList<>(); //存储密度函数

        for (int i = 0; i < n; i++) {
            y = new Random().nextDouble();  //随机生成y，区间在(0, 1)之间
            z = 1 - y;
            x = -(1 / lamda) * Math.log(z); //通过公式获取x
//            listx.add(x);   //存储x
//            listF.add(y);   //存储x对应的F(x)
            f = lamda * Math.exp(-1 * lamda * x);   //计算概率密度函数
//            listf.add(f);   //存储x对应的f(x)
            arrayList.add(x);
        }
        return arrayList;

        //        File file = new File("./data2.txt");
        //        if(!file.exists()) file.createNewFile();
        //        FileWriter fw = new FileWriter(file);
        //        for(int i = 0; i < listx.size(); i++){
        //            fw.write(listx.get(i) + " " + listF.get(i) + " " + listf.get(i) + "\n");
        //        }
        //        fw.close();
    }
}
