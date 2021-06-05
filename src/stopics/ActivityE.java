package stopics;

import java.awt.Point;
import java.math.BigInteger;
import java.util.Random;

import util.CryptoTools;

public class ActivityE {

	public static void main(String[] args) {
		//q8();
		//q9();
		//q10();
		q5();

	}
	
	public static void q8()
	{
		BigInteger p = new BigInteger("341769842231234673709819975074677605139");
		BigInteger g = new BigInteger("37186859139075205179672162892481226795");
		BigInteger aX = new BigInteger("83986164647417479907629397738411168307");
		BigInteger bX = new BigInteger("140479748264028247931575653178988397140");
		
		BigInteger sessionKey;
		BigInteger publicKey;
		
		publicKey = g.modPow(aX, p);
		sessionKey = computeDHSessionKey(publicKey, bX, p);
		
		System.out.println(CryptoTools.bytesToHex(sessionKey.toByteArray()));
		
		
	}
	public static BigInteger computeDHSessionKey(BigInteger publicKey, BigInteger privateKey, BigInteger mod)
	{
		return publicKey.modPow(privateKey, mod);
	}
	
	public static void q9(){
		BigInteger secret = new BigInteger("291639075201575653178417");
		BigInteger[] shares = splitSecret(secret, 5);
		
		for(BigInteger share : shares)
		{
			System.out.println(share.toString());
		}
	}
	
	public static void q10()
	{
		Random rand = new Random();
		BigInteger p = BigInteger.probablePrime(8, rand);
		BigInteger[] shares = splitSecretShamir(44, 6, 3, p);
		
		for(BigInteger share : shares)
		{
			System.out.println(share.toString());
		}
	}
	
	public static BigInteger[] splitSecret(BigInteger secret, int numShares)
	{
		
		BigInteger[] shares = new BigInteger[numShares];
		int nBits = secret.pow(2).bitCount();
		Random rand = new Random();
		BigInteger runningBI = BigInteger.ZERO;
		
		for(int i=0; i<numShares-1;i++)
		{
			shares[i] = new BigInteger(nBits, rand);
			runningBI = runningBI.add(shares[i]);
		}
		shares[numShares-1] = secret.subtract(runningBI);
		
		return shares;
	}
	
	public static BigInteger[] splitSecretShamir(int secret, int numShares, int threshold, BigInteger p)
	{
		Random rand = new Random();
		//BigInteger p = BigInteger.probablePrime(64, rand);
		BigInteger[] points = new BigInteger[numShares];
		
		BigInteger[] randomCoefficients = new BigInteger[threshold-1];
		for(int i = 0; i<threshold-1; i++)
		{
			do {//get random number less than p
				randomCoefficients[i] = new BigInteger(p.bitCount(), rand);
			}while(randomCoefficients[i].compareTo(p) >= 0);
			 
		}
		
		//function
		
		for(int i = 1; i<=numShares; i++)
		{
			points[i-1] = (BigInteger.valueOf(secret));
			for(int j = 0; j <threshold-1;j++)
			{
				points[i-1] = points[i-1].add(randomCoefficients[j].multiply(BigInteger.valueOf(i).pow(j+1)));
			}
			
			
		}
		
		return points;
	}
	
	public static void q5()
	{
		BigInteger b = new BigInteger("2");
		BigInteger x = new BigInteger("20");
		BigInteger n = new BigInteger("11");
		
		System.out.println(discreteLog(b, x, n).toString());
		
	}
	public static BigInteger discreteLog(BigInteger b, BigInteger x, BigInteger n)
	{
		BigInteger bI =  BigInteger.ONE;
		
		for(BigInteger k = BigInteger.ZERO; k.compareTo(BigInteger.valueOf(1000000l)) <= 0; k = k.add(BigInteger.ONE))
		{
			bI = b.modPow(k, n);
			
			if(bI.compareTo(x) == 0)
			{
				return k;
			}
		}
		return null;
	}

}
