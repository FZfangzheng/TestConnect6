package FZ16020031016;

public class Step {
    private int[] firstStep = new int[2];
    private int[] secondStep = new int[2];
    Step(int f,int s){
        int[] ff = new int[2];
        ff[0] = f/19;
        ff[1] = f%19;
        int[] ss = new int[2];
        ss[0] = s/19;
        ss[1] = s%19;
        this.firstStep = ff;
        this.secondStep = ss;
    }
    Step(int[] f,int[] s){
        this.firstStep = f;
        this.secondStep = s;
    }
    public int getFirstStep() {
        return firstStep[0]*19+firstStep[1];
    }

    /**
     * step转成两个0-360的值
     * @return
     */
    public int getSecondStep() {
        return secondStep[0]*19+secondStep[1];
    }
}
