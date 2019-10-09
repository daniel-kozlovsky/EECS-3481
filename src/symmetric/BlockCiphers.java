package symmetric;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import util.CryptoTools;

public class BlockCiphers {

	public static void main(String[] args) throws Exception
	{
		question1();
		question2();
		question3();//only last block is salvageable
		question4();
		question5();
		finalQ();
	}
	
	public static void question1() throws Exception
	{
		//Q1
		final byte[] CIPHER_TEXT = CryptoTools.hexToBytes("F38ADBA8A7B4CC613578355032205D50");
		byte[] key = CryptoTools.hexToBytes("9F0DCEDB322F3C6873F9256E01376BA4");
		byte[] iv = CryptoTools.hexToBytes("20FC19123087BF6CAC8D0F1254123004");
		
		AlgorithmParameterSpec ivAps = new IvParameterSpec(iv);
		Key secretKey = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivAps);
		byte[] decryptedText = cipher.doFinal(CIPHER_TEXT);
		
		System.out.println(">" + new String(decryptedText)+ "<");
		
		
	}
	
	public static void question2() throws Exception
	{
		//Q2
		final byte[] CIPHER_TEXT = CryptoTools.hexToBytes("3188073EA5DB3F5C05B6307B3595607135F5D4B22F2C3EB710AA31377F78B997");
		byte[] key = new String("DO NOT TELL EVE!").getBytes();
		byte[] iv = CryptoTools.hexToBytes("20FC19123087BF6CAC8D0F1254123004");
		
		AlgorithmParameterSpec ivAps = new IvParameterSpec(iv);
		Key secretKey = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivAps);
		byte[] decryptedText = cipher.doFinal(CIPHER_TEXT);
		
		System.out.println(">" + new String(decryptedText)+ "<");
		
		
	}
	
	public static void question3() throws Exception
	{
		//Q3
		final byte[] CIPHER_TEXT = CryptoTools.hexToBytes("AAAAAAAAAAAAAAAA4E51297B424F90D8B2ACD6ADF010DDC4");
		byte[] key = new String("CSE@YORK").getBytes();
		byte[] iv = CryptoTools.hexToBytes("0123456701234567");
		
		AlgorithmParameterSpec ivAps = new IvParameterSpec(iv);
		Key secretKey = new SecretKeySpec(key, "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivAps);
		byte[] decryptedText = cipher.doFinal(CIPHER_TEXT);
		
		System.out.println(">" + new String(decryptedText)+ "<");
		
		
	}
	
	public static void question4() throws Exception
	{
		//Q4
		final byte[] CIPHER_TEXT = CryptoTools.hexToBytes("437DBAB5607137A5CFC1031114634087");
		byte[] key = CryptoTools.hexToBytes("6B79466F724D4F50");
		byte[] iv = CryptoTools.hexToBytes("6976466F724D4F50");
		
		byte[] ivComplement = new byte[iv.length];
		for(int i =0; i < iv.length; i++)
		{
			ivComplement[i] = (byte) ~ iv[i];
		}
		
		AlgorithmParameterSpec ivAps = new IvParameterSpec(ivComplement);
		Key secretKey = new SecretKeySpec(key, "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivAps);
		
		
		byte[] decryptedText  = new byte[CIPHER_TEXT.length/2];
		cipher.update(CIPHER_TEXT, 0, 8, decryptedText);
		
		//~ previous cipher text block
		byte[] ivComplement2 = new byte[decryptedText.length];
		for(int i = 0; i < CIPHER_TEXT.length/2; i++)
		{
			ivComplement2[i] = (byte) ~ CIPHER_TEXT[i];
		}
		
		byte[] decryptedText2  = new byte[decryptedText.length];
		AlgorithmParameterSpec ivAps2 = new IvParameterSpec(ivComplement2);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivAps2);
		cipher.update(CIPHER_TEXT, 8,8, decryptedText2);
		
		System.out.println("4>" + new String(decryptedText) + new String(decryptedText2)+ "<");
		
		
	}
	
	public static void question5() throws Exception
	{
		//Q5
		final byte[] CIPHER_TEXT = CryptoTools.hexToBytes("8A9FF0E2CD27DA4DC7F0C810E73D0E3B3B27CA03762BAE85597995997E625BDF0FEC655994EDD4B0851D7955B3F66717A52F83D01D73ABD9C593DA8C8CCBB073BB19E78442D9AA6D13B307EC0E8EA191E6A21897A82F1A643DC3BE0E12854D01C6006AA1D0EB1B94CAC573908018F284");
		byte[] key = new String("FACEBOOK").getBytes();
		
		byte[] keyComplement = new byte[key.length];
		for(int i =0; i < key.length; i++)
		{
			keyComplement[i] = (byte) ~ key[i];
		}
		
		Key secretKey1 = new SecretKeySpec(key, "DES");
		Key secretKey2 = new SecretKeySpec(keyComplement, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey2);
		byte[] tmpDecryptedText = cipher.doFinal(CIPHER_TEXT);
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey1);
		byte[] decryptedText = cipher.doFinal(tmpDecryptedText);
		
		System.out.println(">" + new String(decryptedText)+ "<");
		
		
	}
	
	public static void finalQ() throws Exception
	{
		//assessment
		String data = new String(Files.readAllBytes(Paths.get("data/bfinal.ct"))); 
		final byte[] CIPHER_TEXT = CryptoTools.hexToBytes(data);
		byte[] key = CryptoTools.hexToBytes("7072616374696365");
		
		
		byte[] keyComplement = new byte[key.length];
		for(int i =0; i < key.length; i++)
		{
			keyComplement[i] = (byte) ~ key[i];
		}
		
		Key secretKey1 = new SecretKeySpec(key, "DES");
		Key secretKey2 = new SecretKeySpec(keyComplement, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey2);
		byte[] tmpDecryptedText = cipher.doFinal(CIPHER_TEXT);
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey1);
		byte[] decryptedText = cipher.doFinal(tmpDecryptedText);
		
		System.out.println(">" + new String(decryptedText)+ "<");
		
		
	}
	

}
