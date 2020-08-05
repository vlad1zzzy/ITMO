import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Manag {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int count = 0;
		int t = sc.nextInt();
		for (int x = 0; x < t; x++) {
			HashMap<Integer,Integer> c_hashmap = new HashMap<Integer,Integer>();
			int n = sc.nextInt();
			ArrayList<Integer> nums = new ArrayList<Integer>();
			for (int j = 0; j < n; j++){
				nums.add(sc.nextInt());
			}
			for (int j = n - 2; j > 0; j--) {
				for (int k = n - 1; k > j; k--){
					int curr = c_hashmap.getOrDefault(nums.get(k), -1);
					System.out.println(curr);
					if (curr == -1){
						c_hashmap.putIfAbsent(nums.get(k), 1);
					} else {
						c_hashmap.put(nums.get(k), c_hashmap.get(nums.get(k)) + 1);
					}
				}
			}
			System.out.println(c_hashmap);
			for (int j = n - 2; j > 0; j--) {
				for (int i = 0; i < j; i++) {
					int current = 2 * nums.get(j) - nums.get(i);
					count += c_hashmap.getOrDefault(current, 0);
				}
			}
			System.out.println(count);
			count = 0;

		}
	}

}