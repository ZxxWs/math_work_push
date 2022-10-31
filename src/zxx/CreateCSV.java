/**
 作者     : Zxx
 文件名    : CreateCSV.py
 创建时间  : 2022/10/30 16:16
 代码内容  ：
 */
package zxx;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class CreateCSV {
    private String fileName;

    public CreateCSV(String distributionName) {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        this.fileName = "./data/" + distributionName + "_" + ft.format(date).toString() + ".csv";
    }

    public void setData(ArrayList<Object> arrayList) throws IOException {
        // 从数组取出写入到csv文件里
        //        System.out.println(this.fileName);
        FileOutputStream fos = new FileOutputStream(this.fileName);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("num", "data");
        CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat);

        for (int i = 0; i < arrayList.size(); i += 1) {
            csvPrinter.printRecord(i, arrayList.get(i));
        }
        csvPrinter.flush();
        csvPrinter.close();
    }
}
