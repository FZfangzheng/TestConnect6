package g05.player;

import java.util.ArrayList;
import java.util.Comparator;

import core.board.PieceColor;

public class Move4AI {
	public ArrayList<Step> moveList = new ArrayList<Step>();
	public ArrayList<Point> pointList = new ArrayList<Point>();
	public boolean [][] vis = new boolean[19][19];
	
	public Move4AI() {}
	
	public void createMove(Board4AI chessBoard, PieceColor type)
	{
		moveList.clear();
		pointList.clear();
		//先调用getWinMove查看有没有必胜着法
		generateWinMove(chessBoard, type);
		if (moveList.size() != 0) return;
		//计算对方威胁
		int threats = chessBoard.countAllThreats(type);
		//堵一个攻击另一个
		if (threats == 1){
			generateSingleBlocks(chessBoard, type);
		}
		//两个全部用来堵
		else if (threats >= 2){
			generateDoubleBlocks(chessBoard, type);
		}
		//产生好点
		else{
			generateGoodValuePoints(chessBoard, type);
		}
		//排序，启发式搜索
		sortMove();
	}
	
	//长生双威胁着法，用于TSS搜索
	void createDoubleThreatsMove(Board4AI chessBoard, PieceColor type)
	{
		moveList.clear();
		pointList.clear();
		//首先生成必胜着法
		generateWinMove(chessBoard, type);
		if (moveList.size() != 0) return;
		//计算对方威胁数
		int threats = chessBoard.countDoubleThreats(type);
		//对方没有威胁，展开双威胁搜索
		if (threats == 0){
			generateDoubleThreats(chessBoard, type);
		}
		//对方存在威胁，必须防守
		else{
			generateDoubleBlocks(chessBoard, type);
		}
	}
	
	public void sortMove(){
		moveList.sort(new Comparator<Step>() {

			@Override
			public int compare(Step o1, Step o2) {
				// TODO Auto-generated method stub
				if(o1.value <= o2.value)
					return 0;
				return 1;
			}
		});
	}
	
	public void findblanks(Road road, Board4AI chessBoard) {
		for (int i = 0; i < 6; i++) {
			int x = road.row + i * Config.dir[road.dir][0];
			int y = road.col + i * Config.dir[road.dir][1];
			if (chessBoard.getCell(x, y) != PieceColor.EMPTY)
				continue;
			if (vis[x][y] == false) {
				Point p = new Point(x, y);
				pointList.add(p);
				vis[x][y] = true;
			}
		}
	}
	
	//获取潜力点，即可能成为威胁的点
	public void getPotentialPoints(Board4AI chessBoard, PieceColor type){
		//我方连二或者连三的点即为潜力点
		Table ownTwo = (type == PieceColor.BLACK ? chessBoard.tables[2][0] : chessBoard.tables[0][2]);
		Table ownThree = (type == PieceColor.BLACK ? chessBoard.tables[3][0] : chessBoard.tables[0][3]);
		int lenOwnTwo = ownTwo.size, lenOwnThree = ownThree.size;

		for (int k = 0; k < lenOwnThree; k++){
			findblanks(ownThree.roadVector[k], chessBoard);
		}

		for (int k = 0; k < lenOwnTwo; k++){
			findblanks(ownTwo.roadVector[k], chessBoard);
		}
	}
	
	//产生必胜着法
	public void generateWinMove(Board4AI chessBoard, PieceColor type){
		Table four = (type == PieceColor.BLACK ? chessBoard.tables[4][0] : chessBoard.tables[0][4]);
		Table five = (type == PieceColor.BLACK ? chessBoard.tables[5][0] : chessBoard.tables[0][5]);
		Step step = new Step();
		if (four.size != 0){
			for (int i = 0, ind = 0; i < 6; i++){
				int x = four.roadVector[0].row + i * Config.dir[four.roadVector[0].dir][0];
				int y = four.roadVector[0].col + i * Config.dir[four.roadVector[0].dir][1];
				if (chessBoard.getCell(x,y)!= PieceColor.EMPTY)
					continue;
				if (ind == 0){
					step.pos[0] = x;
					step.pos[1] = y;
					ind++;
				}
				else{
					step.pos[2] = x;
					step.pos[3] = y;
				}
			}
			chessBoard.virtualMakeMove(step.pos[0], step.pos[1], type);
			chessBoard.virtualMakeMove(step.pos[2], step.pos[3], type);
			step.value = chessBoard.getValue(type);
			chessBoard.unVirtualMakeMove(step.pos[0], step.pos[1], type);
			chessBoard.unVirtualMakeMove(step.pos[2], step.pos[3], type);
			moveList.add(step);
		}

		if (five.size != 0 && moveList.size() == 0){
			step = chessBoard.getTopStep(type);
			for (int i = 0; i < 6; i++){
				int x = five.roadVector[0].row + i * Config.dir[five.roadVector[0].dir][0];
				int y = five.roadVector[0].col + i * Config.dir[five.roadVector[0].dir][1];
				if (chessBoard.getCell(x,y) != PieceColor.EMPTY)
					continue;
				step.pos[0] = x;
				step.pos[1] = y;
			}
			chessBoard.virtualMakeMove(step.pos[0], step.pos[1], type);
			chessBoard.virtualMakeMove(step.pos[2], step.pos[3], type);
			step.value = chessBoard.getValue(type);
			chessBoard.unVirtualMakeMove(step.pos[0], step.pos[1], type);
			chessBoard.unVirtualMakeMove(step.pos[2], step.pos[3], type);
			moveList.add(step);
		}
	}
	
	//两个子全部用来堵
	void generateDoubleBlocks(Board4AI chessBoard, PieceColor type){
		//对方连四或者连五
		Table four = (type == PieceColor.BLACK ? chessBoard.tables[0][4] : chessBoard.tables[4][0]);
		Table five = (type == PieceColor.BLACK ? chessBoard.tables[0][5] : chessBoard.tables[5][0]);
		int lenfour = four.size, lenfive = five.size;
		pointList.clear();
		for(int i = 0; i < 19; i++)
		{
			for(int j = 0; j < 19; j++)
				vis[i][j] = false;
		}
		//先把空白块存起来
		for (int k = 0; k < lenfour; k++){
			findblanks(four.roadVector[k], chessBoard);
		}

		for (int k = 0; k < lenfive; k++) {
			findblanks(five.roadVector[k], chessBoard);
		}

		int rowlen = pointList.size();
		//计算空白块，看看它能不能真正破除威胁
		for (int i = 0; i < rowlen; i++)
		{
			for (int j = i + 1; j < rowlen; j++)
			{
				chessBoard.virtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
				chessBoard.virtualMakeMove(pointList.get(j).x, pointList.get(j).y, type);
				//能破除，上升
				if (four.size + five.size == 0){
					Step step = new Step(pointList.get(i).x, pointList.get(i).y, pointList.get(j).x, pointList.get(j).y);
					step.value = chessBoard.getValue(type);
					moveList.add(step);
				}
				chessBoard.unVirtualMakeMove(pointList.get(j).x, pointList.get(j).y, type);
				chessBoard.unVirtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
			}
		}
		if (moveList.size() == 0){
			Step step = chessBoard.getTopStep(type);
			chessBoard.virtualMakeMove(step.pos[0], step.pos[1], type);
			chessBoard.virtualMakeMove(step.pos[2], step.pos[3], type);
			step.value = chessBoard.getValue(type);
			chessBoard.unVirtualMakeMove(step.pos[2], step.pos[3], type);
			chessBoard.unVirtualMakeMove(step.pos[0], step.pos[1], type);
			moveList.add(step);
		}
	}
	
	//堵一个攻击另一个
	void generateSingleBlocks(Board4AI chessBoard, PieceColor type){
		Table four = (type == PieceColor.BLACK ? chessBoard.tables[0][4] : chessBoard.tables[4][0]);
		Table five = (type == PieceColor.BLACK ? chessBoard.tables[0][5] : chessBoard.tables[5][0]);
		int lenfour = four.size, lenfive = five.size;
		pointList.clear();
		for(int i = 0; i < 19; i++)
		{
			for(int j = 0; j < 19; j++)
			{
				vis[i][j] = false;
			}
		}
		for (int k = 0; k < lenfour; k++){
			findblanks(four.roadVector[k], chessBoard);
		}

		for (int k = 0; k < lenfive; k++) {
			findblanks(five.roadVector[k], chessBoard);
		}

		//防御第一个点，攻击第二个点
		int temp1 = pointList.size();
		getGoodConnectPoints(chessBoard, type);
		int temp2 = temp1 + (int)((pointList.size() - temp1));
		for (int i = 0; i < temp1; i++){
			//破除
			chessBoard.virtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
			if (four.size + five.size == 0){
				for (int j = i + 1; j < temp2; j++)
				{
					chessBoard.virtualMakeMove(pointList.get(j).x, pointList.get(j).y, type);
					Step step = new Step(pointList.get(i).x, pointList.get(i).y, pointList.get(j).x, pointList.get(j).y);
					step.value = chessBoard.getValue(type);
					moveList.add(step);
					chessBoard.unVirtualMakeMove(pointList.get(j).x, pointList.get(j).y, type);
				}
			}
			chessBoard.unVirtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
		}
	}
	
	//产生连接上的好点，定义好点：我方连1，2，3，对方连2，3
	void getGoodConnectPoints(Board4AI chessBoard, PieceColor type){
		Table two = (type == PieceColor.BLACK ? chessBoard.tables[0][2] : chessBoard.tables[2][0]);
		Table three = (type == PieceColor.BLACK ? chessBoard.tables[0][3] : chessBoard.tables[3][0]);
		Table ownOne = (type == PieceColor.BLACK ? chessBoard.tables[1][0] : chessBoard.tables[0][1]);
		Table ownTwo = (type == PieceColor.BLACK ? chessBoard.tables[2][0] : chessBoard.tables[0][2]);
		Table ownThree = (type == PieceColor.BLACK ? chessBoard.tables[3][0] : chessBoard.tables[0][3]);

		int lenOwnOne = ownOne.size, lenOwnTwo = ownTwo.size, lenthree = three.size, lentwo = two.size, 
			lenOwnThree = ownThree.size;

		for (int k = 0; k < lenthree; k++){
			findblanks(three.roadVector[k], chessBoard);
		}

		for (int k = 0; k < lentwo; k++) {
			findblanks(two.roadVector[k], chessBoard);
		}
		for (int k = 0; k < lenOwnThree; k++) {
			findblanks(ownThree.roadVector[k], chessBoard);
		}
		for (int k = 0; k < lenOwnTwo; k++) {
			findblanks(ownTwo.roadVector[k], chessBoard);
		}

		for (int k = 0; k < lenOwnOne; k++) {
			findblanks(ownOne.roadVector[k], chessBoard);
		}
		int len = pointList.size();
		//计算评分
		for (int i = 0; i < len; i++)
		{
			chessBoard.virtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
			pointList.get(i).value = chessBoard.getValue(type);
			chessBoard.unVirtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
		}
	}
	
	//综合评估，产生好的着法
	void generateGoodValuePoints(Board4AI chessBoard, PieceColor type){
		pointList.clear();
		for(int i = 0; i < 19; i++)
		{
			for(int j = 0; j < 19; j++)
			{
				vis[i][j] = false;
			}
		}
		getGoodConnectPoints(chessBoard, type);
		
		pointList.sort(new Comparator<Point>()
		{

			@Override
			public int compare(Point arg0, Point arg1) {
				// TODO Auto-generated method stub
				if(arg0.value <= arg1.value)
					return 0;
				return 1;
			}
		});

		int len = pointList.size();
		int sum = 0;
		int temp = len;
		//优化，去除小于平均值的
		for (int i = 0; i < len; i++){ 
			sum += pointList.get(i).value;
		}
		if(len != 0)
			sum /= len;

		for (int i = 0; i < len; i++){
			if (pointList.get(i).value < sum){
				temp = i;
				break;
			}
		}

		for (int i = 0; i < len; i++){
			for (int j = i + 1; j < temp; j++) {
				chessBoard.virtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
				chessBoard.virtualMakeMove(pointList.get(j).x, pointList.get(j).y, type);
				Step step = new Step(pointList.get(i).x, pointList.get(i).y, pointList.get(j).x, pointList.get(j).y);
				step.value = chessBoard.getValue(type);
				moveList.add(step);
				chessBoard.unVirtualMakeMove(pointList.get(j).x, pointList.get(j).y, type);
				chessBoard.unVirtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
			}
			//优化，中心夹挤
			temp--;
		}
	}
	
	//产生双威胁着法
	void generateDoubleThreats(Board4AI chessBoard, PieceColor type) {

		pointList.clear();
		for(int i = 0; i < 19; i++)
		{
			for(int j = 0; j < 19; j++)
			{
				vis[i][j] = false;
			}
		}
		getPotentialPoints(chessBoard, type);
		int len = pointList.size();

		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				//从潜力点中进行筛选，得到真正威胁点
				chessBoard.virtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
				chessBoard.virtualMakeMove(pointList.get(j).x, pointList.get(j).y, type);
				int typenum = chessBoard.getType(type);
				PieceColor piece;
				if((typenum ^ 1) == 0)
					piece = PieceColor.BLACK;
				else if((typenum ^ 1) == 1)
					piece = PieceColor.WHITE;
				else
					piece = PieceColor.EMPTY;
				if (chessBoard.countDoubleThreats(piece) >= 2) {
					Step step = new Step(pointList.get(i).x, pointList.get(i).y, pointList.get(j).x, pointList.get(j).y);
					moveList.add(step);
				}
				chessBoard.unVirtualMakeMove(pointList.get(j).x, pointList.get(j).y, type);
				chessBoard.unVirtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
			}
		}
	}
}
