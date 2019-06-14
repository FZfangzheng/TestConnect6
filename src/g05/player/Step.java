package g05.player;

public class Step {
	public int [] pos = new int[4];
	public int value;
	public Step(int x1, int y1, int x2, int y2)
	{
		this.pos[0] = x1;
		this.pos[1] = y1;
		this.pos[2] = x2;
		this.pos[3] = y2;
	}
	
	public Step()
	{
		pos[0] = -1;
		pos[1] = -1;
		pos[2] = -1;
		pos[3] = -1;
	}
}
