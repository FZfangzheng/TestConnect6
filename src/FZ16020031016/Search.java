package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import s2017666.AI;

import java.util.ArrayList;

import static core.board.PieceColor.EMPTY;

public class Search {
    private int[][]map = new int[19][19];
    private ArrayList<Step> AS;
    Search(){
        for(int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                this.map[i][j]=0;
            }
        }
        AS = new ArrayList<>();
    }
    private void find(int i, int j, PieceColor opponent, Board board,int type, int count){
        if(i>=0&&i<19&&j>=0&&j<19){
            int index = i*19+j;
            //没有查过，并且是某子
            if(this.map[i][j]==0&&board.get(index)==opponent){
                this.map[i][j]=1;
                if(type==0){
                    int t_type = 1;
                    //八个方向找
                    for(int m=-1;m<2;m++){
                        for(int n=-1;n<2;n++){
                            if(!(m==0&&n==0)){
                                find(i+m,j+n,opponent,board,t_type,count+1);
                                t_type++;
                            }
                        }
                    }
                }
                else{
                    switch (type){
                        case 1:find(i-1,j-1,opponent,board,type,count+1);break;
                        case 2:find(i-1,j,opponent,board,type,count+1);break;
                        case 3:find(i-1,j+1,opponent,board,type,count+1);break;
                        case 4:find(i,j-1,opponent,board,type,count+1);break;
                        case 5:find(i,j+1,opponent,board,type,count+1);break;
                        case 6:find(i+1,j-1,opponent,board,type,count+1);break;
                        case 7:find(i+1,j,opponent,board,type,count+1);break;
                        case 8:find(i+1,j+1,opponent,board,type,count+1);break;
                    }
                }

            }
            else{
                if(this.map[i][j]==0&&board.get(index)==EMPTY){
                    switch (type){
                        case 1:if(((i-1)*19+j-1)<361&&((i-1)*19+j-1)>=0&&board.get((i-1)*19+j-1)==EMPTY)this.AS.add(new Step(i*19+j,(i-1)*19+j-1));break;
                        case 2:if(((i-1)*19+j)<361&&((i-1)*19+j)>=0&&board.get((i-1)*19+j)==EMPTY)this.AS.add(new Step(i*19+j,(i-1)*19+j));break;
                        case 3:if(((i-1)*19+j+1)<361&&((i-1)*19+j+1)>=0&&board.get((i-1)*19+j+1)==EMPTY)this.AS.add(new Step(i*19+j,(i-1)*19+j+1));break;
                        case 4:if(((i)*19+j-1)<361&&((i)*19+j-1)>=0&&board.get((i)*19+j-1)==EMPTY)this.AS.add(new Step(i*19+j,(i)*19+j-1));break;
                        case 5:if(((i)*19+j+1)<361&&((i)*19+j+1)>=0&&board.get((i)*19+j+1)==EMPTY)this.AS.add(new Step(i*19+j,(i)*19+j+1));break;
                        case 6:if(((i+1)*19+j-1)<361&&((i+1)*19+j-1)>=0&&board.get((i+1)*19+j-1)==EMPTY)this.AS.add(new Step(i*19+j,(i+1)*19+j-1));break;
                        case 7:if(((i+1)*19+j)<361&&((i+1)*19+j)>=0&&board.get((i+1)*19+j)==EMPTY)this.AS.add(new Step(i*19+j,(i+1)*19+j));break;
                        case 8:if(((i+1)*19+j+1)<361&&((i+1)*19+j+1)>=0&&board.get((i+1)*19+j+1)==EMPTY)this.AS.add(new Step(i*19+j,(i+1)*19+j+1));break;
                    }
                    this.map[i][j]=2;
                }
            }
        }

    }
    /**
     * 必胜格局搜索
     * @param board
     * @param myChess
     * @return
     */
    public Step mustWin(Board board, PieceColor myChess){
        for(int i = 0;i<19;i++){
            for(int j=0;j<19;j++){
                int index = i*19+j;
                if(this.map[i][j]==0&&board.get(index)==myChess){
                    find(i,j,myChess,board,0,0);
                }
            }
        }
        if(AS.size()!=0){
            return AS.get(0);
        }
        else{
            return new Step(new int[]{-1,-1},new int[]{-1,-1});
        }

    }

}
