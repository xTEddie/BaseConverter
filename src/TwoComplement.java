
/**
 * 
 * @author Edward Tran
 *
 */

public class TwoComplement {
	
	public static String twoComplement(String binaryNumber){
		
		Integer oneComplement = Integer.parseInt(BaseConverter.getDecimalNumber(OneComplement.oneComplement(binaryNumber), 2));
		Integer sum = oneComplement + 1;
		String twoComplement = BaseConverter.convertDecimalNumber(sum, 2);
		
		return twoComplement;
	}
}
