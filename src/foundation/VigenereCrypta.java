package foundation;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import util.CryptoTools;
import util.myTools;

public class VigenereCrypta {

	public static void main(String[] args) throws Exception
	{
		double[] englishFrequencies = CryptoTools.ENGLISH;
		byte[] cipherText = CryptoTools.fileToBytes("data/MSGA4.ct");
		for(int k = 1; k <= 50; k++)
		{
			double ic = CryptoTools.getIC(sample(cipherText, k));
			System.out.printf("Key length: %d IC: %f \n", k, ic);
		}
		
		//looks like key length is 9
		int keyLength = 9;
		byte[] key = new byte[keyLength];
		byte[] workingCT = Arrays.copyOf(cipherText, cipherText.length);
		for(int i = 1 ; i <= keyLength; i++)
		{
			
			int maxDP = 0;
			byte probKey = 0;
			byte[] collected = sample(workingCT, keyLength);
			for(int j=0; j<26; j++)
			{
				byte[] decryptedText = CDecrypt.decrypt(collected, j);
				int[] decryptedFreq = CryptoTools.getFrequencies(decryptedText);
				//maximal dot product indicates closeness
				int dotProduct = myTools.dotProduct(englishFrequencies, decryptedFreq);
				if(dotProduct > maxDP)
				{
					maxDP = dotProduct;
					probKey = (byte)('A' + j);
					
				}
				
			}
			workingCT = Arrays.copyOfRange(workingCT, 1, workingCT.length-1);
			System.out.printf("Key position: %d is probably: %s\n" , i, (char) (probKey) );
			key[i-1] = probKey;
		}
		String bk = new String(decrypt(cipherText, new String(key)));
		System.out.println(bk);
	}
	
	/**
	 * Returns a sample of bytes a fixed length apart from the cipherText array
	 */
	public static byte[] sample(byte[] cipherText, int keyLength) throws Exception
	{
		ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
		
		for(int i = 0; i < cipherText.length; i = i + keyLength)
		{
			byteArrayStream.write(cipherText[i]);
		}
		byteArrayStream.close();
		return byteArrayStream.toByteArray();
	}
	/**
	 * alphabet only(26)
	 * @param cipherText
	 * @param key
	 * @return
	 */
	public static byte[] decrypt(byte[] cipherText, String key)
	{
		byte[] decryptedText = new byte[cipherText.length];
		byte[] byteKey = key.getBytes();
		int keyLength = byteKey.length;
		
		for(int i = 0 ; i < cipherText.length; i++)
		{
			int shiftKey = byteKey[i % keyLength] - 'A';
			
			int tmp = (cipherText[i] - 'A' - shiftKey) % 26;
						
			if(tmp <0)
				tmp += 26;
						
			decryptedText[i] = (byte) (tmp + 'A');
		}
		
		return decryptedText;
		
		
	}
	
	

	
	

}
