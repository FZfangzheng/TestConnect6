package FZ16020031016;

public class Step {
    private int[] firstStep = new int[2];
    private int[] secondStep = new int[2];
    Step(int[] f,int[] s){
        this.firstStep = f;
        this.secondStep = s;
    }
    public int[] getFirstStep() {
        return firstStep;
    }

    /**
     * step转成两个0-360的值
     * @return
     */
    public int[] getSecondStep() {
        return secondStep;
    }
}
