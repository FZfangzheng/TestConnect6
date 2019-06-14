package g05.player;

public class Road {
	public int row;
	public int col;
	public int dir; // 路的方向
	public int num[] = new int[3]; //该路中黑子白子数量
	public int index; // 路在tables中的下标
	boolean active; // 是否有效，定义有效：该路中白子为0或者黑子为0
	
	public Road() {}
	public Road(int row, int col, int dir, int whites, int blacks, boolean active, int index)
	{
		this.row = row;
		this.col = col;
		this.dir = dir;
		this.num[0] = blacks;
		this.num[1] = whites;
		this.active = active;
		this.index = index;
	}
}
