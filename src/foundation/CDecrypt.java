package foundation;

import util.CryptoTools;
import util.myTools;

public class CDecrypt {

	public static void main(String[] args) throws Exception
	{
		
		byte[] cipherText = CryptoTools.fileToBytes("data/MSG2.ct");
		//loop
		double[] englishFrequencies = CryptoTools.ENGLISH;
		for(int i=0; i<26; i++)
		{
			byte[] decryptedText = decrypt(cipherText, i);
			int[] decryptedFreq = CryptoTools.getFrequencies(decryptedText);
			//maximal dot product indicates closeness
			int dotProduct = myTools.dotProduct(englishFrequencies, decryptedFreq);
			
			System.out.println("Key: " + i + ", DP: " + dotProduct + " : "+ new String(decryptedText));
		}
		
	}



	public static byte[] decrypt(byte[] cipherText, int shiftKey)
	{
		byte[] decryptedText = new byte[cipherText.length];
		
		//decryption
		for(int i =0; i < cipherText.length; i++)
		{
			
			int tmp = (cipherText[i] - 'A' - shiftKey) % 26;
			
			if(tmp <0)
				tmp += 26;
			
			decryptedText[i] = (byte) (tmp + 'A');
		}
		
		return decryptedText;
	}
	
	
}