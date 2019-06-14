package FZ16020031016;

import core.board.PieceColor;

import java.util.ArrayList;

public class Board_Score {
    //x925
    private long[][] w ={{0,1,925,855625,791453125,732094140625L,677187080078125L},{0,-1,-925,-855625,-791453125,-732094140625L,-677187080078125L}};
    private long score;
    private PieceColor myChess;
    //��һά���Լ�
    private Board_Roads[][]blackorwhite;
    public long getScore() {
        return score;
    }
    public Board_Roads[][] getBlackorwhite() {
        return blackorwhite;
    }
    Board_Score(PieceColor p){
        this.myChess = p;
        this.score = 0;
        this.blackorwhite = new Board_Roads[7][7];
        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                this.blackorwhite[i][j] = new Board_Roads();
            }
        }
    }

    /**
     * ���·���Ʒֱ���
     * @param r ·
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
     * ͳ����·��ʹ��
     */
    public void calcScore(){
        this.score=0;
        //�ҷ���i���ӣ��Է�j����
        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                ArrayList<Road> ar = blackorwhite[i][j].getAllRoad();
                int len = ar.size();
                score = score+len*w[0][i]+len*w[1][j];
            }
        }
    }
}
