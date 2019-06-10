package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;


public class MyBoard{
    private PieceColor[] my_board = new PieceColor[361];
    public MyBoard(Board board){
        for(int i=0;i<361;i++){
            my_board[i] = board.get(i);
        }
    }
    public void set(int i, int j,PieceColor v) {
        this.my_board[i] = v;
        this.my_board[j] = v;
    }
    public void unset(int i,int j){
        this.my_board[i] = PieceColor.EMPTY;
        this.my_board[j] = PieceColor.EMPTY;
    }
    public PieceColor get(int i){
        return my_board[i];
    }
    public String toString(boolean legend) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("  ");

        int i;
        for(i = 0; i < 19; ++i) {
            strBuff.append((char)(65 + i)).append("  ");
        }

        strBuff.append("\n");

        for(i = 0; i < 361; ++i) {
            if (i % 19 == 0) {
                strBuff.append((char)(65 + i / 19) + " ");
            }

            if (this.my_board[i] == PieceColor.EMPTY) {
                strBuff.append("-").append("  ");
            } else if (this.my_board[i] == PieceColor.BLACK) {
                strBuff.append("x").append("  ");
            } else {
                strBuff.append("o").append("  ");
            }

            if ((i + 1) % 19 == 0) {
                strBuff.append("\n");
            }
        }

        return strBuff.toString();
    }
    public void draw() {
        System.out.print(this.toString(true));
    }
}
