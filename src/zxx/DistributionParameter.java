package zxx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 作者     : Zxx
 文件名    : Parameter.py
 创建时间  : 2022/10/30 1:48
 代码内容  ：
 */

public class DistributionParameter {

    private Document parameter_doc;
    private static ArrayList<Object> parameters_value = new ArrayList<>();
    private static int subscript = 0;

    public DistributionParameter() {
        try {
            //1.创建DocumentBuilderFactory对象
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.parameter_doc = builder.parse("src/xml/parameter_.xml");
        } catch (Exception e) {
            System.out.println("DistributionParameter fail ");
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap> getParameter(String distribution_name) {
        //        System.out.println(distribution_name);
        ArrayList<HashMap> ret = new ArrayList<>();
        //        return ret;
        NodeList sList = this.parameter_doc.getElementsByTagName("dis_name");
        for (int i = 0; i < sList.getLength(); i++) {
            Element ele = (Element) sList.item(i);
            if (ele.getAttribute("name").toString().equals(distribution_name)) {
                NodeList childNodes = ele.getElementsByTagName("pa");
                //                System.out.println(ele.getAttribute("name"));
                for (int j = 0; j < childNodes.getLength(); j++) {

                    String name = childNodes.item(j).getTextContent();
                    String type = ((Element) childNodes.item(j)).getAttribute("type");
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", name);
                    map.put("type", type);
                    ret.add(map);
                }
            }
        }
        return ret;
    }

    public String[] get_Distribution_files_name() {
        try {
            String k = "./src/distributions/";
            String[] files = new File(k).list();
            for (int i = 0; i < files.length; i++) {
                files[i] = files[i].replace(".java", "");
            }
            return files;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public void clear_parameters_value() {
        subscript = 0;
        parameters_value.clear();
    }

    public void set_parameters_value(Object o) {
        parameters_value.add(o);
//        System.out.println("add a num");
    }

    public Object get_next_parameters_value() {
        Object v = parameters_value.get(subscript);
//        System.out.println(v.getClass());
        subscript++;
        return v;
    }


}
