package asymmetric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import util.CryptoTools;

public class ActivityC {
	/*Pick two large primes p and q. n = pq
			2. Compute phi = (p-1)(q-1)
			3. Pick phi > e > 1 such that GCD(e, phi) = 1
			4. Compute d = inverse of e mod phi
			5. Destroy p, q, and phi
			6. Make n,e public; keep d private*/
	public static void main(String[] args) {
		//question1();
		//question2();
		//question3();
		//question4();
		//System.out.println(question5(new BigInteger("1033931178476059651954862004553")));
		//finalQ();
		//Auto key
		/*KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(< specify n'th size in bits >);
		KeyPair pair = kpg.generateKeyPair();
		PrivateKey priv = pair.getPrivate();
		PublicKey pub = pair.getPublic();
		Cipher cipher = Cipher.getInstance("RSA/ECB<PKCS1/No>Padding");
		cipher.init(Cipher.<ENCRYPT/DECRYPT>_MODE, <pub/priv>);
		byte[] ct = cipher.doFinal(pt);*/
	}
	
	public static void question1()
	{
		final String n = "945874683351289829816050197767812346183848578056570056860845622609107886220137" + 
				"220709264916908438536900712481301344278323249667285825328323632215422317870682" + 
				"037630270674000828353944598575250177072847684118190067762114937353265007829546" + 
				"21660256501187035611332577696332459049538105669711385995976912007767106063";
		
		final String e = "74327";
		
		final String d = "7289370196881601766768920490284861650464951706793000236386405648425161747775298" + 
				"3441046583933853592091262678338882236956093668440986552405421520173544428836766" + 
				"3419319185756836904299985444024205035318170370675348574916529512369448767695219" + 
				"8090537385200990850805837963871485320168470788328336240930212290450023";
		
		final String ct = "8701485697571629912108508730957703831688317541285382011555129355623048840582638" + 
				"5706604303724175236985573832006395540199066061101502996745421485579743246846982" + 
				"6363174405058850929567231994074036320411089130186716135085720028980086157008585" + 
				"79079601105011909417884801902333329415712320494308682279897714456370814";
		
		BigInteger nBI = new BigInteger(n);
		BigInteger eBI = new BigInteger(e);
		BigInteger dBI = new BigInteger(d);
		BigInteger ctBI = new BigInteger(ct);
		
		
		
		try
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec RSAPubKeySpec = new RSAPublicKeySpec(nBI, eBI);
			RSAPrivateKeySpec RSAPrivKeySpec = new RSAPrivateKeySpec(nBI, dBI);
			PublicKey pubKey = keyFactory.generatePublic(RSAPubKeySpec);
			PrivateKey privKey = keyFactory.generatePrivate(RSAPrivKeySpec);
			
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] ctb = ctBI.toByteArray();
			byte[] pt = cipher.doFinal(ctb);
			System.out.println(new String(pt).trim());
			
			
			
		}catch(Exception E)
		{
			E.printStackTrace();
		}
		
		
	
	}
	
	public static void question2()
	{
		final String n = "94587468335128982981605019776781234618384857805657005686084562260910788622013722" + 
				"07092649169084385369007124813013442783232496672858253283236322154223178706820376" + 
				"30270674000828353944598575250177072847684118190067762114937353265007829546216602" + 
				"56501187035611332577696332459049538105669711385995976912007767106063";
		
		final String e = "74327";
		
		
		final String ct = "10870101966939556606443697147757930290262227730644958783498257036423105365610629" +
			      "52991052582846432979261500260278236678653125327546335884041286783340625646715334" +
			      "51395019521734099553221296896703454456327755743017818003765454489903326085581032" +
			      "66831217073027652061091790342124418143422318965525239492387183438956";
		
		final String p = "10358344307803887695931304169230543785620607743682421994532795393937342395753127" +
			      "888522373061586445417642355843316524942445924294144921649080401518286829171";
		
		BigInteger testctBI = new BigInteger("70893522764415123918378349913514324921739355433331649222160929404893956476" + 
				"0755177129322861597207887293623059572957652159663120463339983539986325446838884714523382888630816432800" + 
				"53725226851067590389764953325265811370918958261786310636805283078659471676991686273809430255744543469027459425018958931438834272896");
		
		
		BigInteger nBI = new BigInteger(n);
		BigInteger eBI = new BigInteger(e);
		BigInteger ctBI = new BigInteger(ct);
		BigInteger pBI = new BigInteger(p);
		
		
		BigInteger qBI = nBI.divide(pBI);//q found
		//compute phi (p-1)(q-1)
		BigInteger phiBI = qBI.subtract(BigInteger.valueOf(1l)).multiply(pBI.subtract(BigInteger.valueOf(1l)));
		//d is inverse of e mod phi
		BigInteger dBI = eBI.modInverse(phiBI);
		
		
		try
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec RSAPubKeySpec = new RSAPublicKeySpec(nBI, eBI);
			RSAPrivateKeySpec RSAPrivKeySpec = new RSAPrivateKeySpec(nBI, dBI);
			PublicKey pubKey = keyFactory.generatePublic(RSAPubKeySpec);
			PrivateKey privKey = keyFactory.generatePrivate(RSAPrivKeySpec);
			
			
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] ctb = ctBI.toByteArray();
			byte[] pt = cipher.doFinal(ctb);
			System.out.println(new String(pt).trim());
			
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] testCt = cipher.doFinal("Why hello there!".getBytes());
			BigInteger testCtBI = new BigInteger(testCt);
			System.out.println(testCtBI);
			
			
			
		}catch(Exception E)
		{
			E.printStackTrace();
		}
		
		
	
	}
	
	public static void question3()
	{
		BigInteger phi = new BigInteger("8584037913642434144111279062847405921823163865842701785008602377400681495147541519557274092429073976252689387304835782258785521935078205581766754116919200");
		BigInteger q = new BigInteger("87020952829623092932322362936864583897972618059974315662422560067745889600571");
		BigInteger e = new BigInteger("65537");
		BigInteger c = new BigInteger("1817487313698347891034157970684926175211834109573277793102901854482611726141560963120214926234448852417078321539316776648109260519063106558303669880226359");
		
		BigInteger testC = new BigInteger("4119424814248475834776111600334927284379845108049936101321911498466224164605366693993862773479007935052419022563972037651338040810181693929220515959632456");
		
		//p = [phi/q-1] +1
		BigInteger p = phi.divide(q.subtract(BigInteger.valueOf(1l))).add(BigInteger.valueOf(1l));
		//n = qp
		BigInteger n = q.multiply(p);
		//d = mod inverse of e
		BigInteger d = e.modInverse(phi);
		try
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec RSAPrivKeySpec = new RSAPrivateKeySpec(n, d);
			PrivateKey privKey = keyFactory.generatePrivate(RSAPrivKeySpec);
			
			RSAPublicKeySpec RSAPubKeySpec = new RSAPublicKeySpec(n, e);
			PublicKey pubKey = keyFactory.generatePublic(RSAPubKeySpec);
			
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] ctb = c.toByteArray();
			byte[] pt = cipher.doFinal(ctb);
			System.out.println(new String(pt).trim());
			
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] testCt = cipher.doFinal("Why hello there!".getBytes());
			BigInteger testCtBI = new BigInteger(testCt);
			System.out.println(testCtBI);
			
			
		}catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	public static void question4()
	{
		//x mod 1055827021987 = 365944767426
		//x mod 973491987203  = 698856040412
		//Y = P.q.qinv + Q.p.pinv (mod n)
		BigInteger p = new BigInteger("1055827021987");
		BigInteger q = new BigInteger("973491987203");
		//smallest P and Q
		BigInteger P = new BigInteger("365944767426");
		BigInteger Q = new BigInteger("698856040412");
		//finding Y will give us smallest X
		
		//Test p and q are prime
		BigInteger n = p.multiply(q);
		if(p.isProbablePrime(100) && q.isProbablePrime(100))
		{
			//BigInteger test = P.multiply(q).multiply(q.modInverse(p));
			BigInteger result = P.multiply(q).multiply(q.modInverse(p)).add(Q.multiply(p).multiply(p.modInverse(q))).mod(n);
			
			
			System.out.println(result.toString());
			System.out.printf("%d mod %d = %d \n", result, p, result.mod(p));
			System.out.printf("%d mod %d = %d\n", result, q, result.mod(q));
			
		}
		else
		{
			System.out.println("Not possible");
		}
		
	}
	//miller rabin
	public static String question5(BigInteger n)
	{
		/*Test the primality of the number:
			  n = 1033931178476059651954862004553
			using the Miller-Rabin test and the base 2. Write Composite or Inconclusive as your answer.*/
		//Fermat 2^n-1 mod n = 1 then could be prime. 
		//Euler sqrt(2^n-1) mod n = +-1
		//miller rabin n-1 = 2^k.q
		BigInteger base = BigInteger.valueOf(2l);
		//BigInteger n = new BigInteger("1033931178476059651954862004553");
		
		
		//copy
		BigInteger q = n.subtract(BigInteger.ONE);
		int k = 0;
		//this gives q and k
		while(q.mod(BigInteger.valueOf(2l)).intValue() == 0)
		{
			q = q.divide(BigInteger.valueOf(2l));
			k++;
		}
		
		//algo
		System.out.println(base.modPow(q, n));
		if(!base.modPow(q, n).equals(BigInteger.ONE) && !base.modPow(q, n).equals(n.subtract(BigInteger.ONE)))
		{
			for(int i =1; i<=k-1; i++)
			{
				System.out.println(base.pow(2*i).mod(n).toString());
				if(base.pow(2*i).mod(n).equals(BigInteger.ONE))
				{
					return "composite";
				}
				
			}
			System.out.println(base.pow(2*k-1).mod(n));
			if(!base.pow(2*(k-1)).mod(n).equals(n.subtract(BigInteger.ONE)))
			{
				return "composite";
			}
		}
		return "inconclusive";
	}
	public static void finalQ()
	{
		BigInteger n = new BigInteger("7898870574382869894256417188139072477282435866806016313735090721850951414899460604222169148086189219296326447854906012451763174378089987340326283901892341");
		BigInteger e = new BigInteger("101");
		BigInteger d = new BigInteger("6334737787376361004304651408309553174850270348626607142698439093761654105018235565659195932349186531864371575950663093924537224601585272148999762533499753");
		BigInteger ct = new BigInteger("909714279198195620422275139790384933946109644600484564209676411873624567680963619582700931705877430793683424953801890661200283008295293729549557216633457");
		
		
		try
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec RSAPubKeySpec = new RSAPublicKeySpec(n, e);
			RSAPrivateKeySpec RSAPrivKeySpec = new RSAPrivateKeySpec(n, d);
			PublicKey pubKey = keyFactory.generatePublic(RSAPubKeySpec);
			PrivateKey privKey = keyFactory.generatePrivate(RSAPrivKeySpec);
			
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] ctb = ct.toByteArray();
			byte[] pt = cipher.doFinal(ctb);
			System.out.println(new String(pt).trim());
			
			
			
		}catch(Exception E)
		{
			E.printStackTrace();
		}
		
		
	
	}

}
