package s16020031016;
import core.board.Board;
import core.game.Move;
import java.util.ArrayList;
import java.util.Random;

import static core.board.PieceColor.EMPTY;
import static core.game.Move.SIDE;

public class Move_method3 extends core.player.AI {


    @Override
    public Move findMove(Move arg0) {
        // TODO Auto-generated method stub
        Board b = board();
        Random rand = new Random();
        int index1 = 0, index2 = 0, i = 0;
        for(i=0;i<10;i++){
            //从左到右从上到下1-19列
            int x = rand.nextInt(13)+4;
            int y = rand.nextInt(13)+4;
            //转换到0-361
            index1 = (y-1)*19+x-1;
            if (b.get(index1)==EMPTY){
                break;
            }
        }
        //十次不中，全局随机
        if(i==10){
            while(true){
                index1 = rand.nextInt(SIDE*SIDE);
                if(b.get(index1)==EMPTY){
                    break;
                }
            }
        }
        for(i=0;i<10;i++){
            //从左到右从上到下1-19列
            int x = rand.nextInt(13)+4;
            int y = rand.nextInt(13)+4;
            index2 = (y-1)*19+x-1;
            if (b.get(index2)==EMPTY){
                break;
            }
        }
        if(i==10){
            while(true){
                index2 = rand.nextInt(SIDE*SIDE);
                if(b.get(index2)==EMPTY){
                    break;
                }
            }
        }
        return new Move(index1, index2);
    }

    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "16020031016_3";
    }

}
