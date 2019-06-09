package FZ16020031016;
import core.board.Board;
import core.board.PieceColor;

import java.util.ArrayList;

public class Utiles {
    static public ArrayList<Integer> getMap(MyBoard board){
        ArrayList<Integer> AI = new ArrayList<>();
        for (int i = 0;i<19;i++){
            for(int j = 0;j<19;j++){
                int pos = i*19+j;
                if (board.get(pos)!=PieceColor.EMPTY){
                    int t_i_min = i-5;
                    int t_i_max = i+5;
                    int t_j_min = j-5;
                    int t_j_max = j+5;
                    if(t_i_min<0){
                        t_i_min=0;
                    }
                    if(t_i_max>18){
                        t_i_max=18;
                    }
                    if(t_j_min<0){
                        t_j_min=0;
                    }
                    if(t_j_max>18){
                        t_j_max=18;
                    }
                    for(int m = t_i_min;m<=t_i_max;m++){
                        for(int n = t_j_min;n<=t_j_max;n++){
                            AI.add(m*19+n);
                        }
                    }
                }
            }

        }
        return AI;
    }
    /**
     * 返回棋局评分
     * @param board 棋盘
     * @param myChess 执子颜色
     * @return 返回得分
     */
    static public int getValue(MyBoard board, PieceColor myChess){
        Board_Score BS = new Board_Score(myChess);
        ArrayList<Integer> AI = getMap(board);
        for(int i:AI){
            if(board.get(i)==PieceColor.EMPTY){
                ArrayList<Road> ar = Road.getRoads(board,i);
                for (Road r:ar
                     ) {
                    BS.setRoad(r);
                }
            }
        }
        //System.out.println("输出测试样子：\n");
        //board.draw();
        BS.calcScore();
        return BS.getScore();
    }
    static public ArrayList<Road> getAllRoad(MyBoard board){
        ArrayList<Road> AR = new ArrayList<>();
        for(int i=0;i<361;i++){
            ArrayList<Road> ar = Road.getRoads(board,i);
            AR.addAll(ar);
        }
        return AR;
    }
    static public int[] stepToInt(Step step){
        int[] ans = new int[]{step.getFirstStep(),step.getSecondStep()};
        return ans;
    }
}
