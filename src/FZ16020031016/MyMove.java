package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;

import static core.game.Move.*;
import java.util.ArrayList;
import java.util.Random;

import static core.board.PieceColor.EMPTY;


public class MyMove {
    private ArrayList<Step> allStep = new ArrayList<>();
    private int[][]map = new int[19][19];

    //合并的一些操作，获取落子点
    private ArrayList<Integer> operate(Board board,PieceColor opponent){
        for(int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                this.map[i][j]=0;
            }
        }
        for(int i = 0;i<19;i++){
            for(int j=0;j<19;j++){
                int index = i*19+j;
                if(this.map[i][j]==0&&board.get(index)==opponent){
                    find(i,j,opponent,board,0,0);
                }
            }
        }
        ArrayList<Integer> ai = new ArrayList<>();
        for(int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                if(this.map[i][j]==2){
                    int index=i*19+j;
                    ai.add(index);
                }
            }
        }
        return ai;
    }
    /**
     *
     * @param i
     * @param j
     * @param opponent
     * @param board
     * @param type
     * @param count
     */

    private void find(int i, int j, PieceColor opponent, Board board,int type, int count){
//        if(type==1){
//            System.out.print(i);
//            System.out.print(" ");
//            System.out.print(j);
//            System.out.print("\n");
//        }

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
                    this.map[i][j]=2;
                }
            }
        }

    }
    private void stopTwo(PieceColor myChess, PieceColor opponent, Board board){
        ArrayList<Integer> ai = this.operate(board,opponent);
        for(int i=0;i<ai.size();i++){
            for(int j=i+1;j<ai.size();j++){
                allStep.add(new Step(ai.get(i),ai.get(j)));
            }
        }
    }
    private void stopOne(PieceColor myChess, PieceColor opponent, Board board){
        ArrayList<Integer> ai = this.operate(board,opponent);
        ArrayList<Integer> ai2 = this.operate(board,myChess);
        for(int i=0;i<ai.size();i++){
            for(int j=0;j<ai2.size();j++){
                if(ai.get(i)!=ai2.get(j)){
                    allStep.add(new Step(ai.get(i),ai2.get(j)));
                }
            }
        }
    }
    //测试
    private void goodMyself(PieceColor myChess, PieceColor opponent, Board board){
        ArrayList<Integer> ai2 = this.operate(board,myChess);
        for(int i=0;i<ai2.size();i++){
            for(int j=i+1;j<ai2.size();j++){
                allStep.add(new Step(ai2.get(i),ai2.get(j)));
            }
        }
    }
    private void randomStep(Board board){
        Random rand = new Random();
        while (true) {
            int index1 = rand.nextInt(SIDE * SIDE);
            int index2 = rand.nextInt(SIDE * SIDE);

            if (index1 != index2 && board.get(index1) == EMPTY && board.get(index2) == EMPTY){
                Step s = new Step(index1,index2);
                this.allStep.add(s);
                break;
            }
        }
    }
    public void generateStep(PieceColor myChess, Board board){
        PieceColor opponent;
        if(myChess==PieceColor.BLACK){
            opponent=PieceColor.WHITE;
        }
        else{
            opponent=PieceColor.BLACK;
        }
        //二子堵
        stopTwo(myChess,opponent,board);
        //一子堵
        //stopOne(myChess,opponent,board);
        //自己好
        //this.goodMyself(myChess,opponent,board);
        //随机？
        this.randomStep(board);
    }

    public ArrayList<Step> getAllStep() {
        return allStep;
    }
}
