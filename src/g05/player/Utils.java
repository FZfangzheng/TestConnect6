package g05.player;

public class Utils {
	public static boolean isLegal(int row, int col)
	{
		return row >= 0 && col >=0 && row <= 18 && col <= 18; 
	}
}
