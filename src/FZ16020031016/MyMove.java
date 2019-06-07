package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;

import static core.game.Move.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import static core.board.PieceColor.EMPTY;


public class MyMove {
    private ArrayList<Step> allStep = new ArrayList<>();
    ArrayList<PosSore> stop = new ArrayList<PosSore>();
    ArrayList<PosSore> myself = new ArrayList<PosSore>();
    private int[][]map = new int[19][19];
    String str1= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int[][] dir = {
            {0,1},  // 右
            {1,0},  // 下
            {-1,1}, // 右上
            {1,1}   // 右下
    };

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

    private void operator(Board board,PieceColor myChess){
        //沿着路的方向搜六步
        ArrayList<int[]> mustgo = new ArrayList<int[]>();
        for (PosSore pos : stop){
            int index = pos.getI() *19 +pos.getJ();
            ArrayList<Road> roads = Road.getRoads(board,index);
            //必须阻住必须走的step
            int i = pos.getI(),j = pos.getJ();
            for (Road road:roads){
                if (mustgo.size() > 2)
                    break;
                if(ChessCount.getYour(road,myChess) > 3)
                    continue;
                int d = road.getJ() - 1;//方向坐标
                int dirs[] = {-1,-1,-1,-1,-1,-1};
                for (int k = 0 ; k < 6;k++){
                    int y = i + dir[d][0] * k,x = j + dir[d][1] * k;
                    if(y>=0&&y<19&&x>=0&&x<19){
                        dirs[k] = board.get(y*19+x) != EMPTY ? 1:0;
                    }
                }
                int count =1 ,begin = 10;
                for (int k = 1 ; k < 6;k++){
                    if(dirs[k] == 1 && dirs[k-1] == 1){
                        count ++;
                        begin = Math.min(begin,k);
                    }
                    if(count != 1 && dirs[k] == 0)
                        break;
                }
                if(count >= 4){
                    if(begin - 1 >= 0){     //堵开始
                        int arr[]= {i + dir[d][0] * (begin - 1),i + dir[d][1] * (begin - 1)};
                        mustgo.add(arr);
                    }
                }
                if (begin + count < 19){    //堵结尾或中间
                    int arr[]= {i + dir[d][0] * (begin+ count - 1),i + dir[d][1] * (begin + count- 1)};
                    mustgo.add(arr);
                }
            }
            if (mustgo.size() > 2)
                break;
        }
        if (mustgo.size() >= 2)
            allStep.add(new Step(mustgo.get(0),mustgo.get(1)));
        else if (mustgo.size() == 1){
            /*
                这里也可以取myself的头几个，但是这里没有取XD
             */
            for (PosSore my : myself){
                int[] arr = {my.getI(),my.getJ()};
                allStep.add(new Step(mustgo.get(0),arr));
            }
            if(allStep.size() == 0){
                //如果myself为空的话，就会造成allStep为空，但是有个点必须要堵
                //所以另一个点随机取
                Random rand = new Random();
                int index2 = mustgo.get(0)[0] * 19 + mustgo.get(0)[1];
                while (true) {
                    int index1 = rand.nextInt(SIDE * SIDE);
                    if (index1 != index2 && board.get(index1) == EMPTY ){
                        Step s = new Step(index1,index2);
                        this.allStep.add(s);
                        break;
                    }
                }
            }
        }
        else{
            /*
                因为然后把myself中的所有满足的点都进行取组合数，会造成allStep过大，所以让取分数最高的n个
                候补点，n此时取3，allstep大小为6
             */
            int size = Math.min(myself.size(),3);
            for (int i  = 0;i < size ; i++){
                int  pi = myself.get(i).getI() *19 + myself.get(i).getJ();
                for (int j  = i + 1;j < size ; j++) {
                    PosSore pj = myself.get(j);
                    allStep.add(new Step(pi,pj.getI()*19+pj.getJ()));
                }
            }
            if(allStep.size() == 0)
                this.randomStep(board);

        }
    }


    public void generateStep(PieceColor myChess, Board board){
//        二子堵
//        stopTwo(myChess,opponent,board);
////        一子堵
//        stopOne(myChess,opponent,board);
////        自己好
//        this.goodMyself(myChess,opponent,board);
//        随机？
//        this.randomStep(board);


        PieceColor opponent;
        if(myChess==PieceColor.BLACK)
            opponent=PieceColor.WHITE;
        else
            opponent=PieceColor.BLACK;
        //x24+1
        int[] soreList = {0,1,25,601,14425,346201};
        for (int i = 0 ; i < 19;i++){
            for(int j = 0;j<19;j++){
                int index = i*19+j;
                ArrayList<Road> roads = Road.getRoads(board,index);
                //对路上的各个方向进行判断，计算各个点的得分
                int mysore = 0,yousore = 0;
                for (Road road : roads) {
                    int my = ChessCount.getMy(road,myChess);
                    int your = ChessCount.getYour(road,myChess);
                    //一条路上只有自己的子，才会加分
                    if(your == 0)
                        mysore += soreList[my];
                    //一条路上只有对方的子，才会加分
                    if(my == 0)
                        yousore += soreList[your];
                }
                //这个点有路含有4个或者5个，必须要堵的时候加入
                if(board.get(index) == opponent && yousore >= 14425)
                    stop.add(new PosSore(i,j,yousore));
                //这个点可以用来给自己给获得优势或者是妨碍对方
                if (board.get(index) == EMPTY){
                    if(yousore > 0)
                        myself.add(new PosSore(i,j,yousore));
                    else if(mysore > 0)
                        myself.add(new PosSore(i, j, mysore));
                }
            }
        }
        //对要堵的进行排序
        Collections.sort(stop, new SortBySore());
        //对可能要下的地方进行排序，分越高的，给自己的优势或者给对方造成的妨碍越大
        Collections.sort(myself, new SortBySore());
        //开始下棋
        this.operator(board,myChess);
    }

    public ArrayList<Step> getAllStep() {
        return allStep;
    }
}
