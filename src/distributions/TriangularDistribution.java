package distributions;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import zxx.DistributionParameter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.Math;
import java.util.List;
import java.util.Scanner;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;

import java.util.Scanner;

import java.util.ArrayList;

//import src.*;
public class TriangularDistribution {

    public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();
        double a = (double) disp.get_next_parameters_value();
        double b = (double) disp.get_next_parameters_value();
        double c = (double) disp.get_next_parameters_value();
        int n = (int) disp.get_next_parameters_value();
        //采样n次
        for (int i = 0; i < n; i++) {
            arrayList.add(triangularDistribution(a, b, c));
        }
        return arrayList;
    }

    private static int isInit;
    private static int index;
    private static int[] MT = new int[624];  //624 * 32 - 31 = 19937
    private final static long a = 2567483615L;
    private final static long b = 2636928640L;
    private final static long c = 4022730752L;

    public static void Srand(int seed) {
        index = 0;
        isInit = 1;
        MT[0] = seed;
        for (int i = 1; i < 624; i++) {
            int t = 1812433253 * (MT[i - 1] ^ (MT[i - 1] >> 30)) + i;
            MT[i] = t & 0xffffffff;   //取最后的32位
        }
    }

    public static void Generate() {
        for (int i = 0; i < 624; i++) {
            // 2^31 = 0x80000000
            // 2^31-1 = 0x7fffffff
            int y = (MT[i] & 0x80000000) + (MT[(i + 1) % 624] & 0x7fffffff);
            MT[i] = MT[(i + 397) % 624] ^ (y >> 1);
            if ((y & 1) == 1)
                MT[i] ^= a;
        }
    }

    public static int MasonRotate() {
        if (isInit == 0)
            Srand(42);
        if (index == 0)
            Generate();
        int y = MT[index];
        y ^= (y >> 11);
        y ^= ((y << 7) & b);
        y ^= ((y << 15) & c);
        y ^= (y >> 18);
        index = (index + 1) % 624;
        return y;
    }

    public static double triangularDistribution(double a, double b, double c) {
        double F = (c - a) / (b - a);
        double rand = (Double.valueOf(MasonRotate()) % (Double.valueOf(Integer.MAX_VALUE)) / Double.valueOf(Integer.MAX_VALUE));
        // System.out.println(rand);
        //System.out.println(Double.valueOf((MTRandom.MasonRotate()%Integer.MAX_VALUE)/Integer.MAX_VALUE))
        if (rand < F) {
            return a + Math.sqrt(rand * (b - a) * (c - a));
        } else {
            return b - Math.sqrt((1 - rand) * (b - a) * (b - c));
        }
    }
}


