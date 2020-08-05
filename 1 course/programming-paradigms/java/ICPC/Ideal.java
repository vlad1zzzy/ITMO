import java.util.Scanner;

public class Ideal{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		long xl = Long.MAX_VALUE, xr = Long.MIN_VALUE, yl = Long.MAX_VALUE, yr = Long.MIN_VALUE;
		int n = Integer.parseInt(in.nextLine());
		
		for (int i = 0; i < n; i++){
			long x_curr = in.nextLong();
			long y_curr = in.nextLong();
			long h_curr = in.nextLong();
			if (x_curr - h_curr < xl)
				xl = x_curr - h_curr;
			if (x_curr + h_curr > xr)
				xr = x_curr + h_curr;
			if (y_curr - h_curr < yl)
				yl = y_curr - h_curr;
			if (y_curr + h_curr > yr)
				yr = y_curr + h_curr;
		}
		long x = (xl + xr)/2;
		long y = (yl + yr)/2;
		int h = (int) Math.ceil((double) Math.max(xr-xl,yr-yl)/2);
		System.out.print(x + " " + y + " " + h);

	}
}