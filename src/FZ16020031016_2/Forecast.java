package FZ16020031016_2;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;


import java.util.ArrayList;

public class Forecast {
    private PieceColor myChess;
    private MyBoard board;
    private Step step;
    private int F_depth;
    private Board_Score BS;
    Forecast(MyBoard board, PieceColor myChess, int level){
        this.board = board;
        this.myChess = myChess;
        this.F_depth = level;
        this.BS = new Board_Score(myChess);
        Utiles.getBS(BS,board,myChess);
    }

    /**
     * 获取最佳落子
     */
    public int[] generateStep(){
        Search search = new Search();
        Step step = search.mustWin(this.board,this.myChess,this.BS);
        //找不到必胜
        if (step.getFirstStep() < 0){
            Step stop_step = search.mustStop(this.board,this.myChess,this.BS);
            if(stop_step.getFirstStep()<0) {
                //Step attack_step = search.attack(this.board,this.myChess,this.BS,2);
                //攻击失败
                //if(attack_step.getFirstStep()<0) {
                    //进行αβ剪枝搜索最合适
                    alphabeta(0, -Long.MAX_VALUE, Long.MAX_VALUE, myChess, board);
                //}
                return Utiles.stepToInt(this.step);
            }
            else{
                return Utiles.stepToInt(stop_step);
            }
        }
        else{
            return Utiles.stepToInt(step);
        }
    }

    public long alphabeta(int depth, long alpha, long beta, PieceColor myChess, MyBoard board) {
        long t_value=-Long.MAX_VALUE;
        long max_value = -Long.MAX_VALUE;
        long min_value = Long.MAX_VALUE;
        int choose = 0;
        PieceColor nowChess;
        if (depth==this.F_depth){
            BS.calcScore();
            return this.BS.getScore();
        }
        else{
            MyMove move = new MyMove();
            //自己预测落子
            if(depth%2==0){
                move.generateStep(this.myChess,board,BS);
                nowChess = this.myChess;
            }
            //对手落子
            else{
                if(this.myChess==PieceColor.BLACK) {
                    move.generateStep(PieceColor.WHITE, board,BS);
                    nowChess=PieceColor.WHITE;
                }

                else{
                    move.generateStep(PieceColor.BLACK,board,BS);
                    nowChess = PieceColor.BLACK;
                }
            }
            //取出所有落子
            ArrayList<Step> AS = move.getAllStep();
            for (int i = 0;i<AS.size();i++){
                Step _step = AS.get(i);

                board.set(_step.getFirstStep(), _step.getSecondStep(),nowChess,this.BS);

                //System.out.println("输出测试样子：\n");
                //board.draw();
                t_value = alphabeta(depth+1,alpha,beta,nowChess,board);
                board.unset(_step.getFirstStep(), _step.getSecondStep(),this.BS);

                //用于获取最佳方案
                if(depth==0){
                    if (t_value>max_value){
                        choose=i;
                    }
                }
                //根据层数输出最大或者最小
                if(t_value>max_value){
                    max_value=t_value;
                }
                if(t_value<min_value){
                    min_value=t_value;
                }
                //剪枝操作
                if(nowChess!=this.myChess){
                    if(t_value<alpha){
                        //System.out.println("剪枝"+String.valueOf(depth));
                        break;

                    }
                    if(t_value<beta){
                        beta=t_value;
                    }
                }
                else{
                    if(beta<t_value){
                        //System.out.println("剪枝"+String.valueOf(depth));
                        break;
                    }
                    if(alpha<t_value){
                        alpha=t_value;
                    }
                }

            }
            //当前层数，输出最佳方案
            if(depth==0){
                this.step=AS.get(choose);
            }
            if(nowChess!=this.myChess){
                return min_value;
            }
            else{
                return max_value;
            }
        }
    }
}
