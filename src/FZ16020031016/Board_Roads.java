package FZ16020031016;

import java.util.ArrayList;

public class Board_Roads {
    private ArrayList<Road> allRoad;
    Board_Roads(){
        this.allRoad=new ArrayList<>();
    }
    public ArrayList<Road> getAllRoad() {
        return allRoad;
    }

    public void setAllRoad(Road allRoad) {
        this.allRoad.add(allRoad);
    }
}
