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
     * ��ȡ�������
     */
    public int[] generateStep(){
        Search search = new Search();
        Step step = search.mustWin(this.board,this.myChess);
        //�Ҳ�����ʤ
        if (step.getFirstStep() < 0){
            Step stop_step = search.mustStop(this.board,this.myChess);
            if(stop_step.getFirstStep()<0) {
                //���Ц��¼�֦���������
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

    public long alphabeta(int depth, long alpha, long beta, PieceColor myChess, MyBoard board) {
        long t_value=-Long.MAX_VALUE;
        long max_value = -Long.MAX_VALUE;
        long min_value = Long.MAX_VALUE;
        int choose = 0;
        PieceColor nowChess;
        if (depth==this.F_depth){
            return Utiles.getValue(board,this.myChess);
        }
        else{
            MyMove move = new MyMove();
            //�Լ�Ԥ������
            if(depth%2==0){
                move.generateStep(this.myChess,board);
                nowChess = this.myChess;
            }
            //��������
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
            //ȡ����������
            ArrayList<Step> AS = move.getAllStep();
            for (int i = 0;i<AS.size();i++){
                Step _step = AS.get(i);
                board.set(_step.getFirstStep(), _step.getSecondStep(),nowChess);
                //System.out.println("����������ӣ�\n");
                //board.draw();
                t_value = alphabeta(depth+1,alpha,beta,nowChess,board);
                board.unset(_step.getFirstStep(), _step.getSecondStep());
                //���ڻ�ȡ��ѷ���
                if(depth==0){
                    if (t_value>max_value){
                        choose=i;
                    }
                }
                //���ݲ��������������С
                if(t_value>max_value){
                    max_value=t_value;
                }
                if(t_value<min_value){
                    min_value=t_value;
                }
                //��֦����
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
            //��ǰ�����������ѷ���
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
