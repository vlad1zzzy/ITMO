import java.util.Scanner;

public class Acc{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		int b = in.nextInt();
		int n = in.nextInt();
		int k = (int) Math.ceil((double) (n-b)/(b-a));
		System.out.println(2 * k + 1);
	}
}