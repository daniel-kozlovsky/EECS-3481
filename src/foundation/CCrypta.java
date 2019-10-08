package foundation;

import util.CryptoTools;

public class CCrypta {

	public static void main(String[] args) throws Exception
	{
		
		byte[] cipherText = CryptoTools.fileToBytes("data/MSG2.ct");
		
		int[] ctFreq = CryptoTools.getFrequencies(cipherText);
		
		for(double i : ctFreq)
		{
			System.out.println(i / cipherText.length);
			//most frequenct is prob 'E'
		}
		//'E' + x = 'A'
		int KEY = 26 - ('E' - 'A');
		
		System.out.println("The key is probably " + KEY);
	}

}
