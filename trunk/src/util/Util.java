package util;


/**
 * Offers static methods for converting data types (which are of general use, 
 * but not offered by the java API).
 * 
 * @author Karl-Peter Fuchs
 */
public final class Util {
	
	
	/**
	 * Empty constructor. Never used since all methods are static.
	 */
	private Util() {
		
	}
	
	
	/**
	 * Converts the bypassed long value to a byte array.
	 * 
	 * @param source	The long value to be translated.
	 * @return 			Byte array representation of the bypassed long value.
	 */
	public static byte[] longToByteArray(long source) {
		
		byte[] result = new byte[8];
		
		for (int i=0; i<8; i++) {
			
			result[i] = new Long((source >> (i << 3)) & 255L).byteValue();
			
		}
		
		return result;
	}
	
	
	/**
	 * Converts the bypassed byte array to a long value.
	 * 
	 * @param byteArray	The byte array to be translated.
	 * @return 			"long" representation of the bypassed byte array.
	 */
	public static long byteArrayToLong(byte[] byteArray) {

		long result = 0;
		
		for (int i=0; (i<byteArray.length) && (i<8); i++) {
			
			result |= ((((long) byteArray[i]) & 255L) << (i << 3));
			
		}
		
		return result;
	}
	
	
	/**
	 * Converts the bypassed int value to a byte array.
	 * 
	 * @param source	The int value to be translated.
	 * @return 			Byte array representation of the bypassed int value.
	 */
	public static byte[] intToByteArray(int source) {
		
		byte[] result = new byte[4];
		
		for (int i = 0; i < 4; ++i) {
			
			result[3-i] = (byte)((source & (0xff << (i << 3))) >>> (i << 3));
			
		}
		
		return result;
	}
	
	
	/**
	 * Converts the bypassed byte array to an int value.
	 * 
	 * @param byteArray	The byte array to be translated.
	 * @return 			"int" representation of the bypassed byte array.
	 */
	public static int byteArrayToInt(byte[] byteArray) {
		
		int result = 0;
	
		for (int i = 0; (i<byteArray.length) && (i<8); i++) {
			
			result |= (byteArray[3-i] & 0xff) << (i << 3);
			
		}
		
		return result;
					
	}	
	
	
	/**
	 * Converts the bypassed short value to a byte array.
	 * 
	 * @param source	The short value to be translated.
	 * @return 			Byte array representation of the bypassed short value.
	 */
	public static byte[] shortToByteArray(int source) {
		
		byte[] result = new byte[2];
		
		result[0] = (byte)((source & 0xFF00) >> 8);
		result[1] = (byte)(source & 0x00FF);
		
		return result;

	}
	
	
	/**
	 * Converts the bypassed byte array to a short value.
	 * 
	 * @param byteArray	The byte array to be translated.
	 * @return 			"short" representation of the bypassed byte array.
	 */
	public static int byteArrayToShort(byte[] byteArray) {
		
		int result = 0;
		
		result |= (byteArray[0] & 0xFF);
		result <<= 8;
		result |= (byteArray[1] & 0xFF);
		
		return result;
		
	}
	
	
	/**
	 * Merges the bypassed byte arrays (<code>secondArray</code> is appended to 
	 * <code>firstArray</code>).
	 * 
	 * @param firstArray 	Array to be extended.
	 * @param secondArray 	Array to be appended.
	 * 
	 * @return 				Merged array. 
	 */
	public static byte[] mergeArrays(byte[] firstArray, byte[] secondArray) {
		
		byte[] result = new byte[firstArray.length + secondArray.length];
		
		System.arraycopy(firstArray, 0, result, 0, firstArray.length);
		
		System.arraycopy(	secondArray,
						 	0, result, 
						 	firstArray.length, 
						 	secondArray.length
						 	);
		
		return result;
		
	}
	
}