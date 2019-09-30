package foundation;

import util.CryptoTools;

// Monte Carlo computation of the Index of Coincidence of a text file
public class IC
{
	public static void main(String[] args) throws Exception
	{
		byte[] raw = CryptoTools.fileToBytes("data/MSG1.pt");
		byte[] pt = CryptoTools.clean(raw);
		int trials = 1500;
		int count = 0;
		for (int i = 1; i <= trials; i++)
		{
			int pos2;
			int pos1 = (int) (pt.length * Math.random());
			do
			{
				pos2 = (int) (pt.length * Math.random());
			} while (pos2 == pos1);
			if (pt[pos1] == pt[pos2]) count++;
			System.out.println(count / (double) i);
		}
		System.out.println(count / (double) trials);
		
	}
}