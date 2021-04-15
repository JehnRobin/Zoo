package zoo;

import animals.Animal;
import areas.*;
import dataStructures.CashCount;
import dataStructures.ICashCount;

import java.util.ArrayList;
import java.util.HashMap;

public class Zoo implements IZoo {

    private HashMap<Integer, IArea> areas = new HashMap<>();
    private int idCounter = 1;

    private int entranceFee;
    private CashCount cashCount = new CashCount();

    public Zoo() {
        this.areas.put(0, new Entrance());
    }

    @Override
    public int addArea(IArea area) {
        int id = idCounter;

        if (addAreaError(area)) {
            id = -1;
        } else {
            areas.put(idCounter, area);
            // Increase the ID counter, so there is a unique ID for each area
            idCounter++;
        }

        return id;
    }

    private boolean addAreaError(IArea area) {
        if (area instanceof Entrance) {
            System.err.println("Another entrance can't be added!");
            return true;
        } else if (areas.containsValue(area)) { // If area is already in the zoo
            System.err.println("Area is already in the zoo!");
            return true;
        }

        return false;
    }

    @Override
    public void removeArea(int areaID) {
        if (!removeError(areaID)) {
            areas.remove(areaID);
            removeFromAdjacentAreas(areaID);
        }
    }

    private void removeFromAdjacentAreas(int areaID) {
        for (int key: areas.keySet()) {
            areas.get(key).removeAdjacentArea(areaID);
        }
    }

    private boolean removeError(int areaID) {
        // Check that the area is even in the zoo
        if (areas.get(areaID) == null) {
            System.err.println("Area with ID " + areaID + " doesn't exist!");
            return true;
        } else if (areaID == 0) {
            System.err.println("The entrance cannot be removed!");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IArea getArea(int areaId) {
        return areas.get(areaId);
    }

    @Override
    public byte addAnimal(int areaId, Animal animal) {
        IArea area = areas.get(areaId);
        if (area instanceof Habitat) {
            return ((Habitat) area).add(animal);
        } else {
            return Codes.NOT_A_HABITAT;
        }
    }

    @Override
    public void connectAreas(int fromAreaId, int toAreaId) {
        IArea fromArea = areas.get(fromAreaId);
        IArea toArea = areas.get(toAreaId);

        // Only add connection if no error occurs
        if (!connectionError(fromAreaId, fromArea, toAreaId, toArea)) {
            fromArea.addAdjacentArea(toAreaId);
        }
    }

    private boolean connectionError(int fromAreaId, IArea from, int toAreaId, IArea to) {
        // Can't connect areas that don't exist
        if (from == null && to == null) {
            System.err.println("The starting area with ID " + fromAreaId + " and the ending area with ID " + toAreaId + " don't exist!");
        } else if (from == null) {
            System.err.println("The starting area with ID " + fromAreaId + " doesn't exist!");
        } else if (to == null) {
            System.err.println("The ending area with ID " + toAreaId + " doesn't exist!");
        } else if (from.getAdjacentAreas().contains(toAreaId)) {
            // If the connection already exists
            System.err.println("A connection from area " + fromAreaId + " to area " + toAreaId + " already exists!");
        } else {
            // If there is no error
            return false;
        }

        return true;
    }

    @Override
    public boolean isPathAllowed(ArrayList<Integer> areaIds) {
        boolean isAllowed = true;

        // Check there is no path error between consecutive areas
        for (int i = 0; i < areaIds.size() - 1; i++) {
            isAllowed &= !pathError(areaIds.get(i), i, areaIds.get(i + 1), i + 1);
        }

        return isAllowed;
    }

    private boolean pathError(int fromAreaID, int fromIndex, int toAreaID, int toIndex) {
        if (areas.get(fromAreaID) == null && areas.get(toAreaID) == null) {
            System.err.println("Path is not allowed: Area at index " + fromIndex + " with ID " + fromAreaID + " and area at index " + toIndex + " with ID " + toAreaID + " don't exist!");
            return true;
        } else if (areas.get(fromAreaID) == null) {
            System.err.println("Path is not allowed: Area at index " + fromIndex + " with ID " + fromAreaID + " doesn't exist!");
            return true;
        } else if (areas.get(toAreaID) == null) {
            System.err.println("Path is not allowed: Area at index " + toIndex + " with ID " + toAreaID + " doesn't exist!");
            return true;
        } else if (!areas.get(fromAreaID).getAdjacentAreas().contains(toAreaID)) { // If the Area you want to go to is not in the list of adjacent areas
            System.err.println("Your notes are confiscated, you can't take this path! There is no direct path from area " + fromAreaID + " to area " + toAreaID + ".");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<String> visit(ArrayList<Integer> areaIdsVisited) {
        // Check that the path is allowed
        if (!isPathAllowed(areaIdsVisited)) {
            return null;
        }

        ArrayList<String> names = new ArrayList<>();
        IArea currentArea;

        // Foreach area if it is a habitat add the animals names in it to the list.
        for (int id : areaIdsVisited) {
            currentArea = areas.get(id);
            if (currentArea instanceof Habitat) {
                for (Animal currentAnimal : ((Habitat) currentArea).getAnimals()) {
                    names.add(currentAnimal.getNickname());
                }
            }
        }

        return names;
    }

    @Override
    public ArrayList<Integer> findUnreachableAreas() {
        ArrayList<Integer> reachableAreas = findReachableAreas();
        ArrayList<Integer> unreachableAreas = new ArrayList<>();

        // For each area in the zoo, check if it is in the list of reachable areas
        for (int areaID : areas.keySet()) {
            if (!reachableAreas.contains(areaID)) {
                unreachableAreas.add(areaID);
            }
        }

        return unreachableAreas;
    }

    public ArrayList<Integer> findReachableAreas() {
        ArrayList<Integer> reachableAreas = new ArrayList<>();

        // Add entrance
        reachableAreas.add(0);

        for (int j = 0; j < reachableAreas.size(); j++) {
            // Find all areas you can get to from the reachable areas
            for (int areaID : areas.get(reachableAreas.get(j)).getAdjacentAreas()) {
                // Add the areas you can get to if they are not yet in the list
                if (!reachableAreas.contains(areaID)) {
                    reachableAreas.add(areaID);
                }
            }
        }

        return reachableAreas;
    }

    @Override
    public void setEntranceFee(int pounds, int pence) {
        if (!entranceFeeError(pounds, pence)) {
            entranceFee = pounds * 100 + pence;
        }
    }

    private boolean entranceFeeError(int pounds, int pence) {
        if (pence % 10 != 0) {
            System.err.println("Invalid entrance fee! No coins smaller than 10 allowed.");
            return true;
        } else if (pence < 0 || pounds < 0){
            System.err.println("Invalid amount! The value has to be positive.");
            return true;
        }
        return false;
    }

    @Override
    public ICashCount getCashSupply() {
        return cashCount;
    }

    @Override
    public void setCashSupply(ICashCount coins) {
        if (coins instanceof CashCount) {
            this.cashCount = (CashCount) coins;
        }
    }

    @Override
    public ICashCount payEntranceFee(ICashCount cashInserted) {
        int moneyInserted = ((CashCount) cashInserted).cashCountValue();
        int change = moneyInserted - entranceFee;

        // Creating a temporary CashCount that replace the other if transaction is successful
        CashCount tmp = new CashCount();
        tmp.addCoins(cashCount);
        tmp.addCoins((CashCount) cashInserted);

        CashCount returnCash = tmp.returnCoins(change);

        if (returnCash == null) {
            System.err.println("Transaction unsuccessful!");
            return cashInserted;
        } else {
            this.cashCount = tmp;
            return returnCash;
        }
    }
}
