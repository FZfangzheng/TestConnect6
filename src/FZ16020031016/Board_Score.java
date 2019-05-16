package FZ16020031016;

import core.board.PieceColor;

import java.util.ArrayList;

public class Board_Score {
    private int[][] w ={{0,1,2,3,4,5,10000000},{0,-1,-2,-3,-4,-5,-10000000}};
    private int score;
    private PieceColor myChess;
    //第一维是自己
    private Board_Roads[][]blackorwhite = new Board_Roads[7][7];
    public int getScore() {
        return score;
    }
    public Board_Roads[][] getBlackorwhite() {
        return blackorwhite;
    }

    public void setMyChess(PieceColor myChess) {
        this.myChess = myChess;
    }

    /**
     * 添加路到计分表中
     * @param r 路
     */
    public void setRoad(Road r){
        int i,j;
        if(myChess==PieceColor.WHITE){
            i = r.getWf();
            j = r.getBf();
        }
        else{
            i = r.getBf();
            j = r.getWf();
        }
        blackorwhite[i][j].setAllRoad(r);
    }

    /**
     * 统计完路数使用
     */
    public void calcScore(){
        this.score=0;
        //我方有i个子，对方j个子
        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                ArrayList<Road> ar = blackorwhite[i][j].getAllRoad();
                int len = ar.size();
                score = score+len*w[0][i]+len*w[1][j];
            }
        }
    }
}
