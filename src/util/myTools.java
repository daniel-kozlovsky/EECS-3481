package util;

public class myTools {
	
	public static int dotProduct(double[] english, int[] b)
	{
		if(english.length != b.length)
			throw new IllegalArgumentException("Both arrays must be of equal length to compute dot product!");
		
		int sum = 0;
		for(int i = 0; i<english.length; i++)
		{
			sum += english[i] * b[i];
		}
		return sum;
	}

}
