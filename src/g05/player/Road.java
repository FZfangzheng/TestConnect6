package g05.player;

public class Road {
	public int row;
	public int col;
	public int dir; // ·�ķ���
	public int num[] = new int[3]; //��·�к��Ӱ�������
	public int index; // ·��tables�е��±�
	boolean active; // �Ƿ���Ч��������Ч����·�а���Ϊ0���ߺ���Ϊ0
	
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
