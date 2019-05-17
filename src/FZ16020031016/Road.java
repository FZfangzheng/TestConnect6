package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import core.player.Player;
import java.io.*;
import java.util.ArrayList;


public class Road {
    private int fp;//起始点
    private int j;//方向
    private int wf;//白子数目
    private int bf;//黑子数目

    /**
     *
     * @param fp 起始点
     * @param j 方向
     * @param wf 白子数
     * @param bf 黑子数
     */
    Road(int fp,int j,int wf,int bf){
        this.fp=fp;
        this.j=j;
        this.wf=wf;
        this.bf=bf;
    }
    public int getFp() {
        return fp;
    }

    public void setFp(int fp) {
        this.fp = fp;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getWf() {
        return wf;
    }

    public void setWf(int wf) {
        this.wf = wf;
    }

    public int getBf() {
        return bf;
    }

    public void setBf(int bf) {
        this.bf = bf;
    }

    static public ArrayList<Road> getRoads(Board board,int pos){
        PieceColor[] p = Road.createMap(board);
        return Road.findRoads(p,pos);
    }
    /**
     * 输出该格局路的情况
     * @param b
     * @param pos
     * @return ArrayList<Road>
     */
    static private ArrayList<Road> findRoads(PieceColor[] b, int pos){
        int count=0;
        int px=0;
        int py=0;
        px = pos/19;
        py = pos%19;
        ArrayList<Road> ar = new ArrayList<>();
        PieceColor[][] tb = new PieceColor[19][19];

        for(int x=0;x<19;x++){
            for(int y=0;y<19;y++){
                tb[x][y] = b[count];
                count++;
            }
        }
        for(int j=1;j<5;j++){
            int wf=0;
            int bf=0;
            int fp=0;
            if(j==2){
                for(int movex=0;movex<6;movex++){
                    if(px-movex>=0&&px-movex+5<19){
                        bf=0;
                        wf=0;
                        int origin=px-movex;
                        fp = origin*19+py;
                        for(int oj=0;oj<6;oj++){

                            if(tb[origin+oj][py]==PieceColor.BLACK){
                                bf++;
                            }
                            if (tb[origin+oj][py]==PieceColor.WHITE){
                                wf++;
                            }
                        }
                        Road road = new Road(fp,j,wf,bf);
                        ar.add(road);
                    }
                    else{
                        continue;
                    }
                }
            }
            if(j==1){
                for(int movey=0;movey<6;movey++){
                    if(py-movey>=0&&py-movey+5<19){
                        bf=0;
                        wf=0;
                        int origin=py-movey;
                        fp = px*19+origin;
                        for(int oj=0;oj<6;oj++){
                            if(tb[px][origin+oj]==PieceColor.BLACK){
                                bf++;
                            }
                            if (tb[px][origin+oj]==PieceColor.WHITE){
                                wf++;
                            }
                        }
                        Road road = new Road(fp,j,wf,bf);
                        ar.add(road);
                    }
                    else{
                        continue;
                    }
                }
            }
            if(j==3){
                for(int move=0;move<6;move++){
                    if(py-move>=0&&py-move+5<19&&px+move<19&&px+move-5>=0){
                        bf=0;
                        wf=0;
                        int originx=px+move;
                        int originy=py-move;
                        fp = originx*19+originy;
                        for(int oj=0;oj<6;oj++){
                            if(tb[originx-oj][originy+oj]==PieceColor.BLACK){
                                bf++;
                            }
                            if (tb[originx-oj][originy+oj]==PieceColor.WHITE){
                                wf++;
                            }
                        }
                        Road road = new Road(fp,j,wf,bf);
                        ar.add(road);
                    }
                    else{
                        continue;
                    }
                }
            }
            if(j==4){
                for(int move=0;move<6;move++){
                    if(py-move>=0&&py-move+5<19&&px-move>=0&&px-move+5<19){
                        bf=0;
                        wf=0;
                        int originx=px-move;
                        int originy=py-move;
                        fp = originx*19+originy;
                        for(int oj=0;oj<6;oj++){
                            if(tb[originx+oj][originy+oj]==PieceColor.BLACK){
                                bf++;
                            }
                            if (tb[originx+oj][originy+oj]==PieceColor.WHITE){
                                wf++;
                            }
                        }
                        Road road = new Road(fp,j,wf,bf);
                        ar.add(road);
                    }
                    else{
                        continue;
                    }
                }
            }

        }


        return ar;
    }
    /**
     * Board生成对应的PieceColor
     * @param board
     * @return PieceColor
     */
    static private PieceColor[] createMap(Board board){
        PieceColor[] p = new PieceColor[361];
        for(int i = 0;i<361;i++){
            p[i] = board.get(i);
        }
        return p;
    }
}
