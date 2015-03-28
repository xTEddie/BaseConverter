
/**
 * 
 * @author Edward Tran
 *
 */

public class OneComplement {
	
	public static String oneComplement(String binaryNumber){
		
		// If the base of the number is not 2
		if(!BaseConverter.isValidBase(binaryNumber, 2))
			return null;
		
		char[] chars = binaryNumber.toCharArray();
		String oneComplement = "";
		
		for(int i = 0; i < chars.length; i++){
			// If character at position i is 1, invert 1 to 0
			if(chars[i] == '1') 
				oneComplement += 0;
			// If character at position i is 0, invert 0 to 1
			else if(chars[i] == '0')
				oneComplement += 1;
		}
		return oneComplement;
	}
}
