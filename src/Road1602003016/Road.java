package Road1602003016;

import core.board.PieceColor;

import java.io.*;
import java.util.ArrayList;


public class Road {

    static public byte[] toByte(int i){
        byte[] ans = new byte[4];
        //System.out.print((byte)((i>>24)&0xFF));
        //System.out.print((byte)((i>>16)&0xFF));
        //System.out.print((byte)((i>>8)&0xFF));
        //System.out.print((byte)(i&0xFF));
        ans[0]=(byte)((i>>24)&0xFF);
        ans[1]=(byte)((i>>16)&0xFF);
        ans[2]=(byte)((i>>8)&0xFF);
        ans[3]=(byte)(i&0xFF);
        return ans;
    }
    static public ArrayList<Road> findRoads(PieceColor[] b, int pos){
        int count=0;
        int px=0;
        int py=0;
        px = pos/19;
        py = pos%19;
        ArrayList<Road> ar = new ArrayList<>();
        PieceColor[][] tb = new PieceColor[19][19];
        String data_path="s16020031016.dat";
        File target = new File("s16020031016.dat");
        try {
            if (!target.exists()) {
                target.createNewFile();
            }
            int ffff=0;
            DataOutputStream out = new DataOutputStream(new FileOutputStream(data_path, true));
            for(int i=0;i<100;i++){
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
                                ffff++;
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
                                //FileWriter fileWriter = new FileWriter(target.getAbsoluteFile(), true);

                                out.write(toByte(fp));
                                out.write(toByte(j));
                                out.write(toByte(bf));
                                out.write(toByte(wf));

                            }
                            else{
                                continue;
                            }
                        }
                    }
                    if(j==1){
                        for(int movey=0;movey<6;movey++){
                            if(py-movey>=0&&py-movey+5<19){
                                ffff++;
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
                                //DataOutputStream out = new DataOutputStream(new FileOutputStream(data_path, true));
                                out.write(toByte(fp));
                                out.write(toByte(j));
                                out.write(toByte(bf));
                                out.write(toByte(wf));
                                //out.close();
                            }
                            else{
                                continue;
                            }
                        }
                    }
                    if(j==3){
                        for(int move=0;move<6;move++){
                            if(py-move>=0&&py-move+5<19&&px+move<19&&px+move-5>=0){
                                ffff++;
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
                                //DataOutputStream out = new DataOutputStream(new FileOutputStream(data_path, true));
                                out.write(toByte(fp));
                                out.write(toByte(j));
                                out.write(toByte(bf));
                                out.write(toByte(wf));
                                //out.close();

                            }
                            else{
                                continue;
                            }
                        }
                    }
                    if(j==4){
                        for(int move=0;move<6;move++){
                            if(py-move>=0&&py-move+5<19&&px-move>=0&&px-move+5<19){
                                ffff++;
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
                                //DataOutputStream out = new DataOutputStream(new FileOutputStream(data_path, true));
                                out.write(toByte(fp));
                                out.write(toByte(j));
                                out.write(toByte(bf));
                                out.write(toByte(wf));
                                //out.close();
                            }
                            else{
                                continue;
                            }
                        }
                    }

                }

            }
            out.close();
            System.out.print(ffff);
            System.out.println("");


        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return ar;
    }
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("100种棋局状态.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            PieceColor[] b = new PieceColor[36100];
            // 按行读取字符串
            int line=0;
            int location = 0;
            String finalStr="";
            while ((str = bf.readLine()) != null) {
                if(line==1900){
                    finalStr = str;
                }
                else{
                    line++;
                    for(int i = 0;i<19;i++){
                        //System.out.println(str.subSequence(i,i+1));
                        if(str.subSequence(i,i+1).equals("-")){
                            b[location] = PieceColor.EMPTY;
                            location++;
                            continue;
                        }
                        if(str.subSequence(i,i+1).equals("x")){
                            b[location] = PieceColor.BLACK;
                            location++;
                            continue;
                        }
                        if(str.subSequence(i,i+1).equals("o")){
                            b[location] = PieceColor.WHITE;
                            location++;
                            continue;
                        }
                    }
                }
            }
            File target = new File("s16020031016.dat");
            if(target.exists()){
                target.delete();
            }
            String[] positions = finalStr.split(" ");
            for(int i = 0;i<positions.length;i++){
                int pos = Integer.parseInt(positions[i]);
                findRoads(b,pos);
            }
            //System.out.print(b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
