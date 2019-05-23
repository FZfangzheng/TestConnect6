package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;

import static core.game.Move.*;
import java.util.ArrayList;
import java.util.Random;

import static core.board.PieceColor.EMPTY;


public class MyMove {
    private ArrayList<Step> allStep = new ArrayList<>();
    private void mustStop(PieceColor myChess, PieceColor opponent, Board board){

    }
    private void stopTwo(PieceColor myChess, PieceColor opponent, Board board){

    }
    private void stopOne(PieceColor myChess, PieceColor opponent, Board board){

    }
    //测试
    private void goodMyself(PieceColor myChess, PieceColor opponent, Board board){
        Random rand = new Random();
        int index1 = 0, index2 = 0, i = 0;
        for(i=0;i<10;i++){
            //从左到右从上到下1-19列
            int x = rand.nextInt(13)+4;
            int y = rand.nextInt(13)+4;
            //转换到0-361
            index1 = (y-1)*19+x-1;
            if (board.get(index1)==EMPTY){
                break;
            }
        }
        //十次不中，全局随机
        if(i==10){
            while(true){
                index1 = rand.nextInt(SIDE*SIDE);
                if(board.get(index1)==EMPTY){
                    break;
                }
            }
        }
        for(i=0;i<10;i++){
            //从左到右从上到下1-19列
            int x = rand.nextInt(13)+4;
            int y = rand.nextInt(13)+4;
            index2 = (y-1)*19+x-1;
            if (board.get(index2)==EMPTY){
                break;
            }
        }
        if(i==10){
            while(true){
                index2 = rand.nextInt(SIDE*SIDE);
                if(board.get(index2)==EMPTY){
                    break;
                }
            }
        }
        Step s = new Step(index1,index2);
        this.allStep.add(s);
    }
    private void randomStep(Board board){
        Random rand = new Random();
        while (true) {
            int index1 = rand.nextInt(SIDE * SIDE);
            int index2 = rand.nextInt(SIDE * SIDE);

            if (index1 != index2 && board.get(index1) == EMPTY && board.get(index2) == EMPTY){
                Step s = new Step(index1,index2);
                this.allStep.add(s);
                break;
            }
        }
    }
    public void generateStep(PieceColor myChess, Board board){
        PieceColor opponent;
        if(myChess==PieceColor.BLACK){
            opponent=PieceColor.WHITE;
        }
        else{
            opponent=PieceColor.BLACK;
        }
        //必须堵

        //二子堵

        //一子堵

        //自己好

        //随机？
        this.goodMyself(myChess,opponent,board);
        this.randomStep(board);
        this.randomStep(board);
    }

    public ArrayList<Step> getAllStep() {
        return allStep;
    }
}
