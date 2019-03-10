
public class ZEROADD {

	public static String Zeroes(int len)
	{
		
		StringBuilder builder = new StringBuilder();
		for(int i=0 ; i<len ; i++)
		{
			builder.append('0');
		}
		return builder.toString();
	}
}
