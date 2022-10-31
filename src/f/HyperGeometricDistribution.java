package f;

import java.util.Date;

public class HyperGeometricDistribution {
    private static long seed;
    private static long l;

    public static void INIT() {

        Date d = new Date(System.currentTimeMillis());
        seed = (d.getTime() % 10000000);
        l = 8;
    }

    public static long intlen(long in)     //整数in的长度
    {
        long count = 0;
        while (in != 0) {
            in /= 10;
            count++;
        }
        return count;
    }

    public static long power_10(long in)     //10的in次幂
    {
        long i, out = 1;
        for (i = 0; i < in; i++)
            out *= 10;
        return out;
    }

    public static long random() {

        while (intlen(seed) != l) {
            if (intlen(seed) > l) {
                seed = seed / 10;
                System.out.println("seed:" + seed);
            }
            if (intlen(seed) < l) {
                seed = ((seed) * 9) + 1234;
            }
        }//保持位数

        long temp = seed * seed;            //平方
        temp = temp / power_10(l / 2);    //去掉后s位
        temp = temp % power_10(l);          //留下中间2s位
        seed = temp;
        return seed;//(double) seed/(double) power_10(l);
    }

    public static void main(String[] args) {


        HyperGeometricDistribution.INIT();
        int count[] = new int[1000];
        for (int i = 0; i < 1000; i++) {
            count[i] = 0;
        }

        for (int i = 0; i < 10000000; i++) {
            long s = HyperGeometricDistribution.random();
            double dou = ((double) s) / 100000000;
            int ToMy = (int) (s % 1000);
            count[ToMy]++;
            System.out.println("随机数：" + s);
            System.out.println("转换为0~999的整数:" + ToMy);
        }
        long sum = 0;
        for (int i = 0; i < 1000; i++) {
            sum = sum + (count[i] - 10000) * (count[i] - 10000);
            System.out.println("count number " + i + "的次数:" + count[i]);
        }
        sum = sum / 1000;
        System.out.println("方差:" + sum);


    }
}
