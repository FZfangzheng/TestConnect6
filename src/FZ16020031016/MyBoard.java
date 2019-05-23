package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;

/**
 * 废弃
 */
public class MyBoard{
    private PieceColor[] my_board = new PieceColor[361];
    public MyBoard(Board board){
        for(int i=0;i<361;i++){
            my_board[i] = board.get(i);
        }
    }
    public void set(int k, PieceColor v) {
        assert Move.validSquare(k);

        this.my_board[k] = v;
    }
    public void unset(int k){
        this.my_board[k] = PieceColor.EMPTY;
    }
}
