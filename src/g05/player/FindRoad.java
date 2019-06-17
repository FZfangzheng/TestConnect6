package g05.player;

import core.board.PieceColor;

import java.util.ArrayList;

public class FindRoad extends Road{
   // Board4AI board4AI=new Board4AI();
    public  Road [][][] findRoads2(PieceColor[] b, int px,int py,Board4AI board4AI){
        int count=0;
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
            int fp = px*19+py;
            if(j==1){
                if (py+5<19) {
                    for (int move = 0; move < 6; move++) {
                        if (tb[px][py + move] == PieceColor.WHITE) {
                            wf = wf + 1;
                        }
                        if(tb[px][py + move] == PieceColor.BLACK){
                            bf = bf + 1;
                        }
                    }
                    Road road = new Road(px,py,j,wf,bf,true,1);
                    board4AI.segment[px][py][j-1]=road;
                    ar.add(road);
                }
            }
            else if(j==2){
                if(px+5<19) {
                    for (int move = 0; move < 6; move++) {
                        if (tb[px + move][py] == PieceColor.WHITE) {
                            wf = wf + 1;
                        }
                        if (tb[px + move][py] == PieceColor.BLACK){
                            bf = bf + 1;
                        }
                    }
                    Road road = new Road(px,py,j,wf,bf,true,2);
                    board4AI.segment[px][py][j-1]=road;
                    ar.add(road);
                }
            }
            else if(j==3){
                if(py+5<19&&px-5>=0) {
                    for (int move = 0; move < 6; move++) {
                        if (tb[px-move][py + move] == PieceColor.WHITE) {
                            wf = wf + 1;
                        }
                        if (tb[px-move][py + move] == PieceColor.BLACK){
                            bf = bf + 1;
                        }
                    }
                    Road road = new Road(px,py,j,wf,bf,true,3);
                    board4AI.segment[px][py][j-1]=road;
                    ar.add(road);
                }
            }
            else{
                if(py+5<19&&px+5<19) {
                    for (int move = 0; move < 6; move++) {
                        if (tb[px+move][py + move] == PieceColor.WHITE) {
                            wf = wf + 1;
                        }
                        if (tb[px+move][py + move] == PieceColor.BLACK){
                            bf = bf + 1;
                        }
                    }
                    Road road = new Road(px,py,j,wf,bf,true,4);
                    board4AI.segment[px][py][j-1]=road;
                    ar.add(road);
                }
            }
        }
        return board4AI.segment;
    }
}
