package distributions;

import zxx.DistributionParameter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LevyDistribution {
	
	 public ArrayList<Object> getDistributionData(DistributionParameter disp) {
        ArrayList<Object> arrayList = new ArrayList<>();

//		Scanner input = new Scanner(System.in);
//		System.out.print("请输入：n,u,c:");
		double u = (double)disp.get_next_parameters_value();
		double c = (double)disp.get_next_parameters_value();

		int n =(int)disp.get_next_parameters_value();
		double[] result = levysc(n,u,c);
		for(int i =0; i<n; i++) {
			arrayList.add(result[i]);
		}
		return arrayList;
	
	
	}
	public static double[] levysc(int n,double u,double c) {
		Random random = new Random();
		double[] arr = new double[n];int i=0;
		while(n>0) {
			double x=random.nextDouble() * (500-u)+u;//[u,500]随机小数
			double y=random.nextDouble() * levypdf(u+c/3,u,c);
			if(levypdf(x,u,c) >= y) {
				arr[i]=x;
				i++;
				n--;
			}			
		}
		return arr;
	}
	//概率密度函数	
	public static double levypdf(double x,double u,double c) {
        double p1 = Math.sqrt(c/2/Math.PI);
        double p2 = Math.exp(-c/2/(x-u));
        double p3 = Math.pow(x - u, 3.0/2.0);
        return p1*p2/p3;
	}

}

/*def levysc(n, u, c):    
    xkey = []
    while n>0:
        x=np.random.uniform(u, 500)
        y=np.random.uniform(0, levyfb(u+c/3,u,c))
        if levyfb(x,u,c)>=y:
            xkey.append(x)
            n=n-1
    return xkey
*/

