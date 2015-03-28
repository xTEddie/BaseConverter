
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


	public static boolean isValidBase(String str, int base){
		if(str.isEmpty() || str == null)
			return false;
		char[] chars = str.toUpperCase().toCharArray();
		for(int i = 0; i < chars.length; i++){
			// If value is higher than base for value from 0 to 9
			if((int)chars[i] >= 48 && (int)chars[i] <= 57 && (int)chars[i] - 48 >= base)
				return false; 
			// If value is higher than base for value from A to F
			else if((int)chars[i] >= 65 && (int)chars[i] <= 70 && BaseConverter.toHex(Character.toString(chars[i])) >= base){
				return false;
			}
		}
		return true;
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
}

