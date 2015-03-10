import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author Edward Tran
 *
 */

public class BaseConverter {
	
	private static final String hex = "0123456789ABCDEF";
	
	public static String toChar(int number){
		return String.valueOf(hex.charAt(number));
	}
	
	public static int toHex(String c){
		return hex.indexOf(c);
	}
	
	public static String convertDecimalNumber(int decimalNumber, int base){
		int quotient = decimalNumber / base;
		int remainder = decimalNumber % base;
		
		// If number is not in the range (2-16)
		if(base < 2 || base > 16)
			return null;
		
		if(decimalNumber == 0){
			return "";
		}else{
			String output = Integer.toString(remainder);
			// If a hex value is present, convert into integer
			if(base >= 10){
				output = toChar(remainder);
			} 
			return convertDecimalNumber(quotient, base) + output;
		}
	}
	
	public static String getDecimalNumber(String baseNumber, int base){
		// If number is not in the range (2-16) 
		if(base < 2 || base > 16)
			return null;
		
		// Separate every single character into a String array and reverse it 
		String[] str = new StringBuilder(baseNumber).reverse().toString().split("");
		int decimalNumber = 0;
		for(int i = 1; i < str.length; i++){
			// If a hex value is present, convert into integer
			if(base >= 10)
				str[i] = Integer.toString(toHex(str[i])).toString();
			// If baseNumber does not correspond to the base
			if(Integer.parseInt(str[i]) >= base)
				return null;
			decimalNumber += Integer.parseInt(str[i])*(int)Math.pow(base, i-1);
		}
		return Integer.toString(decimalNumber);
	}
	
	public static String convertBase(String number, int inputBase, int outputBase){
		// If number is not in the range (2-16) or is null
//		if(number == null || Integer.parseInt(number) < 2 || Integer.parseInt(number) > 16)
//			return null; ????
		// If input base is 10, convert number to specific base
		if(inputBase == 10){
			return convertDecimalNumber(Integer.parseInt(number), outputBase);
		// If output base is 10, convert to decimal
		}else if(outputBase == 10){
			return getDecimalNumber(number, inputBase);
		}
		// Convert number to decimal, then to outputBase 
		return convertDecimalNumber(Integer.parseInt(getDecimalNumber(number, inputBase)), outputBase);
	}
	
	public static void main(String[] args) throws IOException{
		String number;
		int inputBase;
		int outputBase;
		boolean repeat;
		Scanner kb;
		
		System.out.println("<<<<<----------< Base^ Converter >---------->>>>>");
		
		do{
			repeat = false;
			kb = new Scanner(System.in);
			System.out.println("\nPlease enter input base");
			inputBase = kb.nextInt();
			
			System.out.println("Please enter number:");
			number = kb.next().toUpperCase();
			
			System.out.println("Please enter output base:");
			outputBase = kb.nextInt();
			
			System.out.println(BaseConverter.convertBase(number, inputBase, outputBase));
			
			System.out.println("\nMore conversion? (Y/N)");
			if(kb.next().equalsIgnoreCase("y")){
				repeat = true;
			}else{
				System.out.println("\nBye!");
			}
		}while(repeat);
		kb.close();
//		BaseConverterGUI gui = new BaseConverterGUI();
	}
}

