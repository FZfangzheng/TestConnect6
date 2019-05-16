package s16020031016;
import core.board.Board;
import core.game.Move;
import java.util.ArrayList;
import java.util.Random;

import static core.board.PieceColor.EMPTY;
import static core.game.Move.SIDE;

public class Move_method2 extends core.player.AI {

    private boolean checkMap(int n){
        if (n < 0 || n >= 19*19)
            return false;
        else
            return true;
    }
    @Override
    public Move findMove(Move arg0) {
        // TODO Auto-generated method stub
        //用于循环遍历，各个值加上index1的值即为index2的值
        int[] s = new int[]{-20,-19,-18,-1,1,18,19,20};
        Board b = board();
        Random rand = new Random();
        while (true) {
            int index1 = rand.nextInt(SIDE * SIDE);
            //若不能落子，则重新取值
            if(b.get(index1)!=EMPTY){
                continue;
            }
            //用于存周围有空的位置
            ArrayList<Integer> location = new ArrayList<>();
            for(int i = 0;i<8;i++){
                //checkMap查看是否超边界，若不超边界且为空，存入数组
                if(checkMap(index1+s[i])&&b.get(index1+s[i])==EMPTY){
                    location.add(index1+s[i]);
                }
            }
            int size = location.size();
            //若无可落子，则全局随机
            if(size==0){
                while(true){
                    int index2 = rand.nextInt(SIDE*SIDE);
                    if(b.get(index2)==EMPTY) {
                        return new Move(index1, index2);
                    }
                }
            }
            //有可落子，则随机数组
            else{
                int seed_move = rand.nextInt(size);
                return new Move(index1, location.get(seed_move));
            }
        }
    }

    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "16020031016_2";
    }

}
