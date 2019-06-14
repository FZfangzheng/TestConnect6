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
		//�ȵ���getWinMove�鿴��û�б�ʤ�ŷ�
		generateWinMove(chessBoard, type);
		if (moveList.size() != 0) return;
		//����Է���в
		int threats = chessBoard.countAllThreats(type);
		//��һ��������һ��
		if (threats == 1){
			generateSingleBlocks(chessBoard, type);
		}
		//����ȫ��������
		else if (threats >= 2){
			generateDoubleBlocks(chessBoard, type);
		}
		//�����õ�
		else{
			generateGoodValuePoints(chessBoard, type);
		}
		//��������ʽ����
		sortMove();
	}
	
	//����˫��в�ŷ�������TSS����
	void createDoubleThreatsMove(Board4AI chessBoard, PieceColor type)
	{
		moveList.clear();
		pointList.clear();
		//�������ɱ�ʤ�ŷ�
		generateWinMove(chessBoard, type);
		if (moveList.size() != 0) return;
		//����Է���в��
		int threats = chessBoard.countDoubleThreats(type);
		//�Է�û����в��չ��˫��в����
		if (threats == 0){
			generateDoubleThreats(chessBoard, type);
		}
		//�Է�������в���������
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
	
	//��ȡǱ���㣬�����ܳ�Ϊ��в�ĵ�
	public void getPotentialPoints(Board4AI chessBoard, PieceColor type){
		//�ҷ��������������ĵ㼴ΪǱ����
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
	
	//������ʤ�ŷ�
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
	
	//������ȫ��������
	void generateDoubleBlocks(Board4AI chessBoard, PieceColor type){
		//�Է����Ļ�������
		Table four = (type == PieceColor.BLACK ? chessBoard.tables[0][4] : chessBoard.tables[4][0]);
		Table five = (type == PieceColor.BLACK ? chessBoard.tables[0][5] : chessBoard.tables[5][0]);
		int lenfour = four.size, lenfive = five.size;
		pointList.clear();
		for(int i = 0; i < 19; i++)
		{
			for(int j = 0; j < 19; j++)
				vis[i][j] = false;
		}
		//�Ȱѿհ׿������
		for (int k = 0; k < lenfour; k++){
			findblanks(four.roadVector[k], chessBoard);
		}

		for (int k = 0; k < lenfive; k++) {
			findblanks(five.roadVector[k], chessBoard);
		}

		int rowlen = pointList.size();
		//����հ׿飬�������ܲ��������Ƴ���в
		for (int i = 0; i < rowlen; i++)
		{
			for (int j = i + 1; j < rowlen; j++)
			{
				chessBoard.virtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
				chessBoard.virtualMakeMove(pointList.get(j).x, pointList.get(j).y, type);
				//���Ƴ�������
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
	
	//��һ��������һ��
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

		//������һ���㣬�����ڶ�����
		int temp1 = pointList.size();
		getGoodConnectPoints(chessBoard, type);
		int temp2 = temp1 + (int)((pointList.size() - temp1));
		for (int i = 0; i < temp1; i++){
			//�Ƴ�
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
	
	//���������ϵĺõ㣬����õ㣺�ҷ���1��2��3���Է���2��3
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
		//��������
		for (int i = 0; i < len; i++)
		{
			chessBoard.virtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
			pointList.get(i).value = chessBoard.getValue(type);
			chessBoard.unVirtualMakeMove(pointList.get(i).x, pointList.get(i).y, type);
		}
	}
	
	//�ۺ������������õ��ŷ�
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
		//�Ż���ȥ��С��ƽ��ֵ��
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
			//�Ż������ļм�
			temp--;
		}
	}
	
	//����˫��в�ŷ�
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
				//��Ǳ�����н���ɸѡ���õ�������в��
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
