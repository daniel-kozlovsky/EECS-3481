package foundation;

import java.math.BigInteger;

import util.CryptoTools;
import util.myTools;

public class AExhaustive {

	public static void main(String[] args) throws Exception
	{
		byte[] cipherText = CryptoTools.fileToBytes("data/final.ct");
		double[] englishFrequencies = CryptoTools.ENGLISH;
		String probableDecryption ="n/a";
		int largestDotProd = 0;
		
		for(int alpha = 1; alpha <= 25; alpha++)
		{
			for(int beta = 0; beta < 26; beta++)
			{
				try
				{
					byte[] decryptedText = decrypt(cipherText, alpha, beta);
					int[] decryptedFreq = CryptoTools.getFrequencies(decryptedText);
					//maximal dot product indicates closeness
					int dotProduct = myTools.dotProduct(englishFrequencies, decryptedFreq);
					if (dotProduct > largestDotProd)
					{
						largestDotProd = dotProduct;
						probableDecryption = "alpha: " + alpha + " beta: " + beta + ", DP: " + dotProduct + " : " + new String(decryptedText);
					}
					System.out.println("alpha: " + alpha + " beta: " + beta + ", DP: " + dotProduct + " : "+ new String(decryptedText));
				}catch(ArithmeticException e)
				{
					System.out.println("alpha: " + alpha + " is not coprime with 26");
				}
				
				
			}
		}
		System.out.println("Probable: " + probableDecryption);
	}
	
	public static byte[] decrypt(byte[] cipherText, int alpha, int beta)
	{
		byte[] decryptedText = new byte[cipherText.length];
		
		BigInteger BI = BigInteger.valueOf((long)alpha);
		int x = BI.modInverse(BigInteger.valueOf(26l)).intValue();
		
		for(int i = 0; i< cipherText.length; i++)
		{
			
			//c-b
			int tmp = cipherText[i] - 'A' - beta;
			if(tmp < 0)
				tmp+=26;
			
			decryptedText[i] = (byte) ((x * tmp) % 26 + 'A');
		}
		return decryptedText;
	}

}
