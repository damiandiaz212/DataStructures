import java.util.Random;
import java.io.PrintWriter;

/**
 * @author Damian Diaz
 * @title CreateData.java
 */
public class CreateData {

	public static void main(String[] args) throws Exception {

		int num1 = Integer.parseInt(args[0]);
		int num2 = Integer.parseInt(args[1]);

		int min = Math.min(num1, num2);
		int max = Math.max(num1, num2);

		generateFile(min, max, 0);
		generateFile(min, max, 1);

	}

	/**
	 * @name generateFile method
	 * 
	 * @description - file will generate a file of random integers or strings within bounds { k | i <= k <= j} set by parameter.
	 * @param _i - minimum bounds of random range
	 * @param _j - maximum bounds of random range
	 * @param _type - type of file to be generated, 
	 * 0 = integer 1 = string
	 */ 
	private static void generateFile(int _i, int _j, int _type) throws Exception{

		Random rand = new Random();

		// 0 = integer, 1 = string
		int type = _type; 
		int total = 0;
		
		String typeStr = "";

		for(int i = _i; i <= _j; i++){
			total += (int)Math.pow(10, i);
		}

		PrintWriter writer = new PrintWriter(typeStr + total + ".txt", "UTF-8");

		switch(type){
		case 0:
			typeStr = "int";
			break;
		case 1:
			typeStr = "strings";
			break;
		}

		boolean done = false;
		int k = _i;
		int amount = (int)Math.pow(10, k);

		//string use only
		int length = k + 5;

		while(!done){

			System.out.println("Printing " + amount + " lines.");
			while(amount > 0){

				//Determine type of file
				if(type == 0)
					writer.println(rand.nextInt((int) Math.pow(10, k+1)));
				else
					writer.println(getRandomString(length));

				amount --;
			}
			System.out.println("done.");

			if(k < _j){
				k ++;
				amount = (int)Math.pow(10, k);

				//String use only
				length = k + 5;
			}
			else
				done = true;
		}

		writer.close();
	}

	/**
	 * @name  getRandomString method
	 * 
	 * @description this method will return a random string whose length is passed through the argument.
	 * @param length - length of requested string
	 * @return random string
	 */
	private static String getRandomString(int length){

		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*";
		String result = "";
		Random rand = new Random();

		for(int i = 0; i < length; i++)
			result += chars.charAt(rand.nextInt(chars.length()));

		return result;
	}
}