import java.util.Scanner;

public class FractionalBaseConverter {
	
	// Works for base 2-9
	public static String convertDecimalFraction(double decimalFraction, int base){
		// If number is not in the range (2-16) 
		if(base < 2 || base > 16)
			return null;
		double product = base * decimalFraction;
		
		if(product - (int)product == 0)
			return Integer.toString((int)product);
		return Integer.toString((int)product) + convertDecimalFraction(product - (int)product, base);
	}
	
	public static String getDecimalFraction(String baseFraction, int base){
		// If number is not in the range (2-16) 
		if(base < 2 || base > 16)
			return null;

		// Separate every single character into a String array  
		String[] str = new StringBuilder(baseFraction).toString().split("");
		double decimalFraction = 0;
		
		
		for(int i = 1; i < str.length; i++){
			// If a hex value is present, convert into integer
//			if(base >= 10)
//				str[i] = Integer.toString(toHex(str[i])).toString();
			// If baseNumber does not correspond to the base
			if(Integer.parseInt(str[i]) >= base)
				return null;
			decimalFraction += Integer.parseInt(str[i])*(int)Math.pow(base, i-1);
		}
		return Double.toString(decimalFraction);
	}

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
//		String fractionalNumber = kb.next();
//		System.out.println(fractionalNumber.indexOf("."));
//		System.out.println("0." + convertDecimalFraction(0.68,8));
//		System.out.println("0." + convertDecimalFraction(0.76,8));
	}

}
