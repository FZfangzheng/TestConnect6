package g05.player;

import java.util.ArrayList;

import core.board.PieceColor;

public class Board4AI {
	final int ROW = 19;
	final int COL = 19;
	final int INF = 5000000;
	final int DEPTH = 6;
	final int TSSDEPTH = 13;
	final int MAXNODE = 100;
	
	private int [][] cover = new int[ROW][COL];
	private boolean [][] vis  = new boolean[ROW][COL];
	public ArrayList<Point> pointVector = new ArrayList<Point>();
	
	public Table [][] tables = new Table[7][7];
	public PieceColor [][] board = new PieceColor[ROW][COL];
	public Road [][][] segment = new Road[ROW][COL][4];
	
	public Board4AI()
	{
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 7; j++)
				tables[i][j] = new Table();
		}
		for(int i = 0; i < ROW; i++)
		{
			for(int j = 0; j < COL; j++)
			{
				for(int k = 0; k < 4; k++)
				{
					Road road = new Road(i, j, k, 0, 0, false, 0);
					segment[i][j][k] = road;
					if(Utils.isLegal(i + 5 * Config.dir[k][0], j + 5 * Config.dir[k][1]))
					{
						road.active = true;
						tables[0][0].Add(road);
					}
				}
				cover[i][j] = 0;
				board[i][j] = PieceColor.EMPTY;
			}
		}
	}
	
	public void init()
	{
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				tables[i][j].size = 0;
			}
		}
		
		for(int i = 0; i < ROW; i++)
		{
			for(int j = 0; j < COL; j++)
			{
				for(int k = 0; k < 4; k++)
				{
					Road road = (segment[i][j][k] = new Road(i, j, k, 0, 0, false, 0));
					if(Utils.isLegal(i + 5 * Config.dir[k][0], j + 5 * Config.dir[k][1]))
					{
						road.active = true;
						tables[0][0].Add(road);
					}
				}
				cover[i][j] = 0;
				board[i][j] = PieceColor.EMPTY;
			}
		}
	}
	
	public int getType(PieceColor pieceColor)
	{
		int type;
		
		if(pieceColor == PieceColor.BLACK)
			type = 0;
		else if(pieceColor == PieceColor.WHITE)
			type = 1;
		else
			type = 2;
		
		return type;
	}
	
	public int getValue(PieceColor pieceColor)
	{
		int res = 0;
		int type = getType(pieceColor);
		for(int i = 1; i <= 6; i++)
			res += tables[i][0].size * Config.score[type][i] - tables[0][i].size * Config.score[type^1][i];
		
		return (pieceColor == PieceColor.BLACK ? res : -res);
	}
	
	public PieceColor getCell(int row, int col)
	{
		return board[row][col];
	}
	
	public void makeMove(int row, int col, PieceColor pieceColor)
	{
		int xt, yt;
		board[row][col] = pieceColor;
		cover[row][col]++;
		
		int type = getType(pieceColor);
		
		for(int i = 0; i < 24; i++)
		{
			xt = row + Config.range[i][0];
			yt = col + Config.range[i][1];
			if(Utils.isLegal(xt, yt))
				cover[xt][yt]++;
		}
		
		for(int i = 0; i < 4; i++)
		{
			xt = row;
			yt = col;
			if(segment[xt][yt][i].active)
			{
				Road road = segment[xt][yt][i];
				tables[road.num[0]][road.num[1]].Remove(road);
				road.num[type]++;
				tables[road.num[0]][road.num[1]].Add(road);
			}
			for(int j = 0; j < 5; j++)
			{
				xt -= Config.dir[i][0];
				yt -= Config.dir[i][1];
				if(Utils.isLegal(xt, yt))
				{
					if(segment[xt][yt][i].active)
					{
						Road road = segment[xt][yt][i];
						tables[road.num[0]][road.num[1]].Remove(road);
						road.num[type]++;
						tables[road.num[0]][road.num[1]].Add(road);
					}
				}
				else break;
			}
		}
	}

	public void unMakeMove(int row, int col, PieceColor pieceColor)
	{
		int xt, yt;
		board[row][col] = PieceColor.EMPTY;
		cover[row][col]--;
		int type = getType(pieceColor);
		
		for(int i = 0; i < 24; i++)
		{
			xt = row + Config.range[i][0];
			yt = col + Config.range[i][1];
			if(Utils.isLegal(xt, yt))
				cover[xt][yt]--;
		}
		for(int i = 0; i < 4; i++)
		{
			xt = row;
			yt = col;
			if(segment[xt][yt][i].active)
			{
				Road road = segment[xt][yt][i];
				tables[road.num[0]][road.num[1]].Remove(road);
				road.num[type]--;
				tables[road.num[0]][road.num[1]].Add(road);
			}
			for(int j = 0; j < 5; j++)
			{
				xt -= Config.dir[i][0];
				yt -= Config.dir[i][1];
				if(Utils.isLegal(xt, yt))
				{
					Road road = segment[xt][yt][i];
					tables[road.num[0]][road.num[1]].Remove(road);
					road.num[type]--;
					tables[road.num[0]][road.num[1]].Add(road);
				}
				else break;
			}
		}
	}
	
	public void virtualMakeMove(int row, int col, PieceColor pieceColor)
	{
		int xt, yt;
		board[row][col] = pieceColor;
		int type = getType(pieceColor);
		
		for (int i = 0; i < 4; i++){
			xt = row;
			yt = col;
			if (segment[xt][yt][i].active){
				Road road = segment[xt][yt][i];
				tables[road.num[0]][road.num[1]].Remove(road);
				road.num[type]++;
				tables[road.num[0]][road.num[1]].Add(road);
			}
			for (int j = 0; j < 5; j++){
				xt -= Config.dir[i][0];
				yt -= Config.dir[i][1];
				if (Utils.isLegal(xt, yt)){
					if (segment[xt][yt][i].active){
						Road seg = segment[xt][yt][i];
						tables[seg.num[0]][seg.num[1]].Remove(seg);
						seg.num[type]++;
						tables[seg.num[0]][seg.num[1]].Add(seg);
					}
				}
				else break;
			}
		}
	}
	
	public void unVirtualMakeMove(int row, int col, PieceColor pieceColor)
	{
		int xt, yt;
		board[row][col] = PieceColor.EMPTY;
		int type = getType(pieceColor);
		
		for (int i = 0; i < 4; i++){
			xt = row;
			yt = col;
			if (segment[xt][yt][i].active){
				Road seg = segment[xt][yt][i];
				tables[seg.num[0]][seg.num[1]].Remove(seg);
				seg.num[type]--;
				tables[seg.num[0]][seg.num[1]].Add(seg);
			}
			for (int j = 0; j < 5; j++){
				xt -= Config.dir[i][0];
				yt -= Config.dir[i][1];
				if (Utils.isLegal(xt, yt)){
					if (segment[xt][yt][i].active){
						Road seg = segment[xt][yt][i];
						tables[seg.num[0]][seg.num[1]].Remove(seg);
						seg.num[type]--;
						tables[seg.num[0]][seg.num[1]].Add(seg);
					}
				}
				else break;
			}
		}
	}
	
	void update() {
		for (int i = 0; i < ROW; i++){
			for (int j = 0; j < COL; j++){
				for (int k = 0; k < 4; k++){
					if (segment[i][j][k].num[0] > 0 && segment[i][j][k].num[1] > 0){
						segment[i][j][k].active = false;
					}
				}
			}
		}
	}
	
	//计算是否存在双迫着，TSS辅助函数
	int countDoubleThreats(PieceColor pieceColor) {
		
		Table four = (pieceColor == PieceColor.BLACK ? tables[0][4] : tables[4][0]);
		Table five = (pieceColor == PieceColor.BLACK ? tables[0][5] : tables[5][0]);
		if (four.size + five.size == 0) return 0;
		//如果四字就存在双迫着，五子一定存在双迫着
		Table tab = (four.size == 0 ? five : four);

		for (int i = 0; i < 6; i++){
			int x = tab.roadVector[0].row + i * Config.dir[tab.roadVector[0].dir][0];
			int y = tab.roadVector[0].col + i * Config.dir[tab.roadVector[0].dir][1];
			if (getCell(x, y) != PieceColor.EMPTY)
				continue;
			//获取空格，虚拟落子，如果迫着消失，证明是单迫着
			virtualMakeMove(x, y, pieceColor);
			int t = four.size + five.size;
			unVirtualMakeMove(x, y, pieceColor);
			if (t == 0) return 1;
		}
		return 2;
	}
	
	int countAllThreats(PieceColor type) {
		for(int i = 0; i < ROW; i++)
		{
			for(int j = 0; j < COL; j++)
				vis[i][j] = false;
		}
		pointVector.clear();
		Table four = (type == PieceColor.BLACK ? tables[0][4] : tables[4][0]);
		Table five = (type == PieceColor.BLACK ? tables[0][5] : tables[5][0]);
		

		if (five.size + four.size == 0) return 0;

		Table tab = (four.size == 0 ? five : four);

		for (int i = 0; i < 6; i++){
			int x = tab.roadVector[0].row + i * Config.dir[tab.roadVector[0].dir][0];
			int y = tab.roadVector[0].col + i * Config.dir[tab.roadVector[0].dir][1];
			if (getCell(x, y) != PieceColor.EMPTY)
				continue;
			
			virtualMakeMove(x, y, type);
			int t = four.size + five.size;
			unVirtualMakeMove(x, y, type);
			if (t == 0) return 1;
		}

		for (int i = 0; i < five.size; i++){
			for (int j = 0; j < 6; j++){
				int x = five.roadVector[i].row + j * Config.dir[five.roadVector[i].dir][0];
				int y = five.roadVector[i].col + j * Config.dir[five.roadVector[i].dir][1];
				if (getCell(x, y) != PieceColor.EMPTY)
					continue;
				Point p = new Point(x, y);
				if (!vis[x][y]){
					vis[x][y] = true;
					pointVector.add(p);
				}
			}
		}

		for (int i = 0; i < four.size; i++){
			for (int j = 0; j < 6; j++){
				int x = four.roadVector[i].row + j * Config.dir[four.roadVector[i].dir][0];
				int y = four.roadVector[i].col + j * Config.dir[four.roadVector[i].dir][1];
				if (getCell(x, y) != PieceColor.EMPTY)
					continue;
				Point p = new Point(x, y);
				if (!vis[x][y]){
					vis[x][y] = true;
					pointVector.add(p);
				}
			}
		}

		boolean flag = false;
		int temp = pointVector.size();

		for (int i = 0; i < temp && !flag; i++){
			for (int j = i + 1; j < temp && !flag; j++){
				virtualMakeMove(pointVector.get(i).x, pointVector.get(i).y, type);
				virtualMakeMove(pointVector.get(j).x, pointVector.get(j).y, type);
				if (four.size + five.size == 0){
					flag = true;
				}
				unVirtualMakeMove(pointVector.get(i).x, pointVector.get(i).y, type);
				unVirtualMakeMove(pointVector.get(j).x, pointVector.get(j).y, type);
			}
		}

		return (flag ? 2 : 3);
	}
	
	Point getTopPoint(PieceColor type) {
		Point respoint = new Point();
		respoint.value = -INF;

		for (int i = 0; i <ROW; i++) {
			for (int j = 0; j <COL; j++) {
				
				if (cover[i][j] == 1 && board[i][j] == PieceColor.EMPTY) 
				{
					Point point = new Point(i, j);
					virtualMakeMove(i, j, type);
					point.value = getValue(type);
					if (point.value > respoint.value) {
						respoint = point;
					}
					unVirtualMakeMove(i, j, type);
				}
			}
		}
		return respoint;
	}
	
	Step getTopStep(PieceColor type) {
		Point pointI = getTopPoint(type);
		virtualMakeMove(pointI.x, pointI.y, type);
		Point pointJ = getTopPoint(type);
		virtualMakeMove(pointJ.x, pointJ.y, type);
		Step step = new Step(pointI.x, pointI.y, pointJ.x, pointJ.y);
		step.value = getValue(type);
		unVirtualMakeMove(pointJ.x, pointJ.y, type);
		unVirtualMakeMove(pointI.x, pointI.y, type);
		return step;
	}
}
