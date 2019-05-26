package FZ16020031016;

import java.util.Comparator;

public class PosSore {
    private int i;
    private int j;

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getScore() {
        return score;
    }

    private int score;
    PosSore(int i,int j,int score){
        this.i = i;
        this.j = j;
        this.score = score;
    }
}

class SortBySore implements Comparator {
    public int compare(Object o1, Object o2) {
        PosSore s1 = (PosSore) o1;
        PosSore s2 = (PosSore) o2;
        if (s1.getScore()  < s2.getScore())
            return 1;
        else if (s1.getScore() == s2.getScore())
            return 0;
        return -1;
    }
}