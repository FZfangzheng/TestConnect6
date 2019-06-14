package g05.player;

import java.util.ArrayList;

import core.board.PieceColor;

public class Search {
	final int INF = 5000000;
	final int DEPTH = 6;
	final int TSSDEPTH = 13;
	final int MAXNODE = 100;
	
	private PieceColor computerSide;
	private int grad;
	private int t;
	private boolean isFind;
	private ArrayList<Step> moveVector = new ArrayList<Step>();
	private ArrayList<Step> firstSteps = new ArrayList<Step>();
	private Step bestMove;
	
	public Search()
	{
		isFind = false;
		t = 0;
		grad = 2;
	}
	
	public Step findBestMove(PieceColor type, Board4AI chessBoard)
	{
		computerSide = type;

		//���Ƚ���TSS����
		for (int i = 1; i <= TSSDEPTH; i += 2) {
			if (TSS(type, chessBoard, i)) {
				return bestMove;
			}
		}

		//TSSʧ�ܣ�����alphaBeta����
		firstSteps.clear();
		int typenum = chessBoard.getType(computerSide) ^ 1;
		if(typenum == 0)
			computerSide = PieceColor.BLACK;
		else if(typenum == 1)
			computerSide = PieceColor.WHITE;
		else
			computerSide = PieceColor.EMPTY;
		
		isFind = false;
		int value = alphaBeta(DEPTH - grad, -INF, INF, type, chessBoard);
		if (!isFind) bestMove = firstSteps.get(0);
		return bestMove;
	}
	
	int alphaBeta(int depth, int alpha, int beta, PieceColor type, Board4AI chessBoard) 
	{
		if (depth == 0) { return chessBoard.getValue(type); }

		int temp = -INF;
		boolean flag = false;

		Move4AI move = new Move4AI();
		move.createMove(chessBoard, type);
		int num = move.moveList.size();
		
		if(num > MAXNODE + grad * 6)
			num = MAXNODE + grad * 6;

		if (depth == DEPTH - grad) {
			for (int i = 0; i < num; i++) firstSteps.add(move.moveList.get(i));
		}
		for (int i = 0; i < num; i++) {
			chessBoard.makeMove(move.moveList.get(i).pos[0], move.moveList.get(i).pos[1], type);
			chessBoard.makeMove(move.moveList.get(i).pos[2], move.moveList.get(i).pos[3], type);

			int typenum = chessBoard.getType(type) ^ 1;
			PieceColor piece;
			if(typenum == 0)
				piece = PieceColor.BLACK;
			else if(typenum == 1)
				piece = PieceColor.WHITE;
			else
				piece = PieceColor.EMPTY;
			
			if (flag) {
				//�մ���̽
				temp = -alphaBeta(depth - 1, -alpha - 1, -alpha, piece, chessBoard);
				if (temp > alpha && temp < beta) {
					temp = -alphaBeta(depth - 1, -beta, -alpha, piece, chessBoard);
				}
			}
			else {
				temp = -alphaBeta(depth - 1, -beta, -alpha, piece, chessBoard);
			}

			chessBoard.unMakeMove(move.moveList.get(i).pos[0], move.moveList.get(i).pos[1], type);
			chessBoard.unMakeMove(move.moveList.get(i).pos[2], move.moveList.get(i).pos[3], type);


			if (temp >= beta) {
				return beta;
			}
			if (temp > alpha) {
				alpha = temp;
				flag = true;
				if (depth == DEPTH - grad) {
					chessBoard.makeMove(move.moveList.get(i).pos[0], move.moveList.get(i).pos[1], type);
					chessBoard.makeMove(move.moveList.get(i).pos[2], move.moveList.get(i).pos[3], type);
					if (!TSS(piece, chessBoard, 15)) {
						bestMove = move.moveList.get(i);
						isFind = true;
					}
					chessBoard.unMakeMove(move.moveList.get(i).pos[0], move.moveList.get(i).pos[1], type);
					chessBoard.unMakeMove(move.moveList.get(i).pos[2], move.moveList.get(i).pos[3], type);
				}
			}
		}
		return alpha;
	}
	
	boolean TSS(PieceColor type, Board4AI chessBoard, int depth) 
	{
		//����ҷ�������return true��
		int temp = (computerSide == PieceColor.BLACK ? chessBoard.tables[6][0].size : chessBoard.tables[0][6].size);

		if (temp > 0) {
			bestMove = moveVector.get(0);
			return true;
		}
		//����Է�������в�������ҷ�û����в��return false��
		
		int typenum = chessBoard.getType(type) ^ 1;
		PieceColor piece;
		if(typenum == 0)
			piece = PieceColor.BLACK;
		else if(typenum == 1)
			piece = PieceColor.WHITE;
		else
			piece = PieceColor.EMPTY;
		
		if (type == computerSide && chessBoard.countAllThreats(type) > 0
			&& chessBoard.countAllThreats(piece) == 0) return false;

		typenum = chessBoard.getType(computerSide) ^ 1;
		if(typenum == 0)
			piece = PieceColor.BLACK;
		else if(typenum == 1)
			piece = PieceColor.WHITE;
		else
			piece = PieceColor.EMPTY;
			
		//����ҷ���в����3��return true��
		if (type == (piece) && (chessBoard.countAllThreats(type) >= 3)) {
			bestMove = moveVector.get(0);
			return true;
		}
		//depthΪ0��return false�� 
		if (depth == 0) return false;

		//����
		Move4AI move = new Move4AI();
		move.createDoubleThreatsMove(chessBoard, type);
		int num = move.moveList.size();

		typenum = chessBoard.getType(type) ^ 1;
		if(typenum == 0)
			piece = PieceColor.BLACK;
		else if(typenum == 1)
			piece = PieceColor.WHITE;
		else
			piece = PieceColor.EMPTY;
		
		for (int i = 0; i < num; i++) {
			chessBoard.makeMove(move.moveList.get(i).pos[0], move.moveList.get(i).pos[1], type);
			chessBoard.makeMove(move.moveList.get(i).pos[2], move.moveList.get(i).pos[3], type);
			moveVector.add(move.moveList.get(i));
			boolean flag = TSS(piece, chessBoard, depth - 1);
			moveVector.remove(moveVector.size() - 2);
			chessBoard.unMakeMove(move.moveList.get(i).pos[2], move.moveList.get(i).pos[3], type);
			chessBoard.unMakeMove(move.moveList.get(i).pos[0], move.moveList.get(i).pos[1], type);

			if (type == computerSide) {
				flag = false || flag;
				if (flag) return true;
			}
			else {
				flag = true && flag;
				if (!flag) return false;
			}
		}
		return isFind;
	}
}
