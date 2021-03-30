package areas;

import java.util.ArrayList;

public abstract class Area implements IArea {

    protected ArrayList<Integer> adjacentAreas = new ArrayList<>();

    @Override
    public ArrayList<Integer> getAdjacentAreas() {
        return adjacentAreas;
    }

    public void addConnection(int toAreaID) {
        adjacentAreas.add(toAreaID);
    }

}
