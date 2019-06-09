package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;
import jdk.jshell.execution.Util;

import java.util.ArrayList;

public class Forecast {
    private PieceColor myChess;
    private MyBoard board;
    private Step step;
    private int F_depth;
    Forecast(MyBoard board, PieceColor myChess, int level){
        this.board = board;
        this.myChess = myChess;
        this.F_depth = level;
    }

    /**
     * 获取最佳落子
     */
    public int[] generateStep(){
        Search search = new Search();
        Step step = search.mustWin(this.board,this.myChess);
        //找不到必胜
        if (step.getFirstStep() < 0){
            Step stop_step = search.mustStop(this.board,this.myChess);
            if(stop_step.getFirstStep()<0) {
                //进行αβ剪枝搜索最合适
                alphabeta(0, -10000000, 10000000, myChess, board);
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

    public int alphabeta(int depth, int alpha, int beta, PieceColor myChess, MyBoard board) {
        int t_value=-10000000;
        int max_value = -10000000;
        int min_value = 10000000;
        int choose = 0;
        PieceColor nowChess;
        if (depth==this.F_depth){
            return Utiles.getValue(board,this.myChess);
        }
        else{
            MyMove move = new MyMove();
            //自己预测落子
            if(depth%2==0){
                move.generateStep(this.myChess,board);
                nowChess = this.myChess;
            }
            //对手落子
            else{
                if(this.myChess==PieceColor.BLACK) {
                    move.generateStep(PieceColor.WHITE, board);
                    nowChess=PieceColor.WHITE;
                }

                else{
                    move.generateStep(PieceColor.BLACK,board);
                    nowChess = PieceColor.BLACK;
                }
            }
            //取出所有落子
            ArrayList<Step> AS = move.getAllStep();
            for (int i = 0;i<AS.size();i++){
                Step _step = AS.get(i);
                board.set(_step.getFirstStep(), _step.getSecondStep(),nowChess);
                //System.out.println("输出测试样子：\n");
                //board.draw();
                t_value = alphabeta(depth+1,alpha,beta,nowChess,board);
                board.unset(_step.getFirstStep(), _step.getSecondStep());
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
                        break;
                    }
                    if(t_value<beta){
                        beta=t_value;
                    }
                }
                else{
                    if(beta<t_value){
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
