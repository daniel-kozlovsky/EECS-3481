package hash;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class ActivityD {

	public static void main(String[] args) {
		
		//question1();
		question2();
		//question3();
		//question4();
		//finalQ();

	}
	public static void finalQ()
	{
		String pt = "No one can make you feel inferior without your consent.";
		String key = "specialfloorsudd";
		try 
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(pt.getBytes());
			Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
			
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] ct = cipher.doFinal(hash);
			
			System.out.println(new String(CryptoTools.bytesToHex(ct)));
			
		}catch(Exception E)
		{
			E.printStackTrace();
		}
			
			
		}
	public static void question1()
	{
		final String n =
			      "945874683351289829816050197767812346183848578056570056860845622609107886220137" +
			      "220709264916908438536900712481301344278323249667285825328323632215422317870682" +
			      "037630270674000828353944598575250177072847684118190067762114937353265007829546" +
			      "21660256501187035611332577696332459049538105669711385995976912007767106063";
		final String e = "74327";
		final String d = "7289370196881601766768920490284861650464951706793000236386405648425161747775298" +
			      "3441046583933853592091262678338882236956093668440986552405421520173544428836766" +
			      "3419319185756836904299985444024205035318170370675348574916529512369448767695219" +
			      "8090537385200990850805837963871485320168470788328336240930212290450023";
		
		BigInteger nBI = new BigInteger(n);
		BigInteger eBI = new BigInteger(e);
		BigInteger dBI = new BigInteger(d);
		
		final String message = "Meet me at 5 pm tomorrow";
		
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] sign = md.digest(message.getBytes());
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec RSAPubKeySpec = new RSAPublicKeySpec(nBI, eBI);
			RSAPrivateKeySpec RSAPrivKeySpec = new RSAPrivateKeySpec(nBI, dBI);
			PublicKey pubKey = keyFactory.generatePublic(RSAPubKeySpec);
			PrivateKey privKey = keyFactory.generatePrivate(RSAPrivKeySpec);
			
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			
			byte[] ctSign = cipher.doFinal(sign);
			byte[] ct = cipher.doFinal(message.getBytes());
			System.out.println("signature: " + new String(sign));
			System.out.println("Encrypted signature: " + new String(ctSign));
			System.out.println("Encrypted pt: " + new String(ct));
			
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			
			byte[] ptSign = cipher.doFinal(ctSign);
			byte[] pt = cipher.doFinal(ct);
			
			byte[] signedPt = md.digest(pt);
			System.out.println("decrypted signature: " + new String(ptSign));
			System.out.println("decrypted pt: " + new String(pt));
			System.out.println("sign from d pt: " + new String(signedPt));
			
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
		
	}
	public static void question2()
	{
		//test
		BigInteger test = new BigInteger("7289370196881601766768920490284861650464951706793000236386405648425161747775298" + 
				"3441046583933853592091262678338882236956093668440986552405421520173544428836766" + 
				"3419319185756836904299985444024205035318170370675348574916529512369448767695219" + 
				"8090537385200990850805837963871485320168470788328336240930212290450023");
		//
		BigInteger nA = new BigInteger("171024704183616109700818066925197841516671277");
		BigInteger eA = new BigInteger("1571");
		
		BigInteger pB = new BigInteger("98763457697834568934613");
		BigInteger qB = new BigInteger("8495789457893457345793");
		BigInteger eB = new BigInteger("87697");
		
		BigInteger mEnc = new BigInteger("418726553997094258577980055061305150940547956");
		BigInteger sEnc = new BigInteger("749142649641548101520133634736865752883277237");
		
		//compute phi (p-1)(q-1)
		BigInteger phiBI = qB.subtract(BigInteger.valueOf(1l)).multiply(pB.subtract(BigInteger.valueOf(1l)));
		//d is inverse of e mod phi
		BigInteger dB = eB.modInverse(phiBI);//bobs private key
		BigInteger nB = qB.multiply(pB);
		
		int x = nB.bitLength();
		int y = nA.bitLength();
		try
		{
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			RSAPublicKeySpec RSAPubKeySpec = new RSAPublicKeySpec(nB, eB);
			RSAPrivateKeySpec RSAPrivKeySpec = new RSAPrivateKeySpec(nB, dB);
			
			//PublicKey pubKey = keyFactory.generatePublic(RSAPubKeySpec);
			PrivateKey privKey = keyFactory.generatePrivate(RSAPrivKeySpec);
			
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			
			
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			
			byte[] pt = cipher.doFinal(mEnc.toByteArray());
			byte[] decSign = cipher.doFinal(sEnc.toByteArray());
			
			System.out.println("decrypted signature: " + new String(decSign));
			System.out.println("decrypted pt: " + new String(pt));
			
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
		
	}

	public static void question3()
	{
		final int SHA256BlockSize = 64;
		String pt = "Mainly cloudy with 40 percent chance of showers";
		String key = "This is an ultra-secret key";
		
		byte[] ptBytes = pt.getBytes();
		byte[] keyBytes = key.getBytes();
		String opadValue = "5C";
		String ipadValue = "36";
		byte[] opad = new byte[SHA256BlockSize];
		byte[] ipad = new byte[SHA256BlockSize];
		
		//populate pads
		for(int i =0; i<SHA256BlockSize; i++) {
			ipad[i] = CryptoTools.hexToBytes(ipadValue)[0];
			opad[i] = CryptoTools.hexToBytes(opadValue)[0];
			
		}
		
				/*The padding constants are:
			  byte[] opad = bytes valued 0x5c
			  byte[] ipad = bytes valued 0x36
			The padding constants are reversed:
			  byte[] opad = bytes valued 0x36
			  byte[] ipad = bytes valued 0x5c
*/
		//SHA-256 implementation
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			//K'
			
			if(keyBytes.length > SHA256BlockSize)
			{
				keyBytes = md.digest(keyBytes);
			}
			else
			{
				//pad with 0 until block size
				byte[] paddedKey = Arrays.copyOf(keyBytes, SHA256BlockSize);
				keyBytes = paddedKey;
			}
			byte[] ipadKey = new byte[SHA256BlockSize];
			byte[] opadKey = new byte[SHA256BlockSize];
			//hash p1
			
			for(int i =0; i<SHA256BlockSize; i++) {
				ipadKey[i] = (byte) (keyBytes[i] ^ ipad[i]);
				opadKey[i] = (byte) (keyBytes[i] ^ opad[i]);
			}
			
			
			byte[] hashOne = new byte[ipadKey.length + ptBytes.length];
			System.arraycopy(ipadKey, 0, hashOne, 0, ipadKey.length);
			System.arraycopy(ptBytes, 0, hashOne, ipadKey.length, ptBytes.length);
			
			hashOne = md.digest(hashOne);
			
			byte[] hashTwo = new byte[opadKey.length + hashOne.length];
			System.arraycopy(opadKey, 0, hashTwo, 0, opadKey.length);
			System.arraycopy(hashOne, 0, hashTwo, opadKey.length, hashOne.length);
			
			byte[] finalHash = md.digest(hashTwo);
			System.out.println(CryptoTools.bytesToHex(finalHash));
			
		}
		catch(Exception e)
		{
			
		}
		
		
		
		/*//test bench
		try 
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(pt.getBytes());
			Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
			
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] ct = cipher.doFinal(hash);
			
			System.out.println(new String(CryptoTools.bytesToHex(ct)));
			
		}catch(Exception E)
		{
			E.printStackTrace();
		}*/
			
			
		}

	public static void question4()
	{
		/*
		 * A hash cannot be unhashed because it is a one way mapping and data is actually lost during the process.
		 * Inifinite inputs, 2^128 outputs. 
		 * 
		 * A preimage attack can be executed by brute forcing all possible inputs until a collision is reached or
		 * using a more efficient birthday attack
		 * 2^n/2
		 * 
		 * "crypto" rainnbow tables
		 * https://crackstation.net/
		 */
		String psw = "dQstGg_1";
		
		MessageDigest md;
		try {
			
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(psw.getBytes());
			System.out.println(CryptoTools.bytesToHex(digest));
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void question5()
	{
		//P(N people having different birthdays) = (365/365)*(365-1/365)*(365-2/365)*….(365-n+1)/365.
        //= 365!/((365-n)! * 365n)
		//	1	–	exp(-x2/2N)	
		//S becomes ≥ 50% when x >= 1.177*sqrt(N)
		//
		//P(Two people have the same birthday) = 1 – P(Two people having different birthday)
        //= 1 – (365*365)*(364*365
		/*Finding	Collisions	
		For	a	hash	of	size	n	bits,	N=2n	and	we	need	only	generate	√N
				§ Meet	in	the	Middle	Birthday	Attack	
				A	version	of	the	Meet-in-the-Middle	against	cipher-based	
				hash	functions.
				§ Fabricating	a	message	
				Make	2n/2	variations	of	the	real	message	and	2n/2	of	the	
				fraudulent	one.	Probability	of	a	match	is	over	50%.*/
	}
}
