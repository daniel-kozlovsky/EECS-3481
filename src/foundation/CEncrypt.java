package foundation;

import util.CryptoTools;

public class CEncrypt {
	
	
	
	public static void main(String[] args) throws Exception
	{
		final String MD5 = "2C422B741EF90FD4424EBC83692398B0";
		final int KEY = 19;
		byte[] plainText;
		
		plainText = CryptoTools.fileToBytes("data/MSG1.pt");
		byte[] cleanPlainText = CryptoTools.clean(plainText);
		CryptoTools.bytesToFile(cleanPlainText, "data/MSG1.clean");
		
		//Caeser encrypt
		
		plainText = CryptoTools.fileToBytes("data/MSG1.clean");
		byte[] cipherText = new byte[plainText.length];
		
		for(int i = 0; i < plainText.length; i++)
		{
			int shiftedInt = (plainText[i] - 'A' + KEY) % 26 + 'A';
			cipherText[i] = (byte) shiftedInt;
		}
		//save to file
		CryptoTools.bytesToFile(cipherText, "data/MSG1.ct");
		
		if (!CryptoTools.getMD5(cipherText).equals(MD5))
		{
			throw new Exception("The ciphertext is not correct");
		}
		//ic of plaintext
		System.out.println("plaintext IC: " + CryptoTools.getIC(plainText));
		System.out.println("ciphertext IC: " + CryptoTools.getIC(cipherText));
		
	}

}
