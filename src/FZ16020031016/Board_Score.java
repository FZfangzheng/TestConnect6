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

    public void setMyChess(PieceColor myChess) {
        this.myChess = myChess;
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
        int i,j,f,fj;
        i = r.getBf();
        j = r.getWf();
        f = r.getFp();
        fj = r.getJ();
        ArrayList<Road> ar = blackorwhite[i][j].getAllRoad();
        if(ar.size()==0){
            blackorwhite[i][j].setAllRoad(r);
        }
        else {//ȥ�ظ�
            int flag = 0;
            for (Road r1 : ar) {
                if (r1.getBf() == i && r1.getWf() == j && r1.getFp() == f && r1.getJ() == fj) {
                    flag=1;
                    break;
                }
            }
            if (flag==0){
                blackorwhite[i][j].setAllRoad(r);
            }
        }

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
                if(myChess==PieceColor.BLACK){
                    score = score+len*w[0][i]+len*w[1][j];
                }
                else{
                    score = score+len*w[1][i]+len*w[0][j];
                }
            }
        }
    }
}
