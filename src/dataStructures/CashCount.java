package dataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CashCount implements ICashCount {
    HashMap<Integer, Integer> coins = new HashMap<>();
    ArrayList<Integer> allowedCoins = new ArrayList<>(Arrays.asList(2000, 1000, 500, 200, 100, 50, 20, 10));

    public CashCount() {
        for (int coin : allowedCoins) {
            coins.put(coin, 0);
        }
    }

    public CashCount(HashMap<Integer, Integer> coins) {
        // Initialize as empty
        this();

        // Override coins if HashMap is valid
        if (checkHashMapIsValid(coins)){
            this.coins = coins;
        } else {
            System.err.println("Instead the object has been initialized with no coins in it!");
        }
    }


    @Override
    public int getNrNotes_20pounds() {
        return coins.get(2000);
    }

    @Override
    public void setNrNotes_20pounds(int noteCount) {
        if (checkIsValidAmount(noteCount)){
            coins.put(2000, noteCount);
        }
    }

    @Override
    public int getNrNotes_10pounds() {
        return coins.get(1000);
    }

    @Override
    public void setNrNotes_10pounds(int noteCount) {
        coins.put(1000, noteCount);
    }

    @Override
    public int getNrNotes_5pounds() {
        return coins.get(500);
    }

    @Override
    public void setNrNotes_5pounds(int noteCount) {
        coins.put(500, noteCount);
    }

    @Override
    public int getNrCoins_2pounds() {
        return coins.get(200);
    }

    @Override
    public void setNrCoins_2pounds(int coinCount) {
        coins.put(200, coinCount);
    }

    @Override
    public int getNrCoins_1pound() {
        return coins.get(100);
    }

    @Override
    public void setNrCoins_1pound(int coinCount) {
        coins.put(100, coinCount);
    }

    @Override
    public int getNrCoins_50p() {
        return coins.get(50);
    }

    @Override
    public void setNrCoins_50p(int coinCount) {
        coins.put(50, coinCount);
    }

    @Override
    public int getNrCoins_20p() {
        return coins.get(20);
    }

    @Override
    public void setNrCoins_20p(int coinCount) {
        coins.put(20, coinCount);
    }

    @Override
    public int getNrCoins_10p() {
        return coins.get(10);
    }

    @Override
    public void setNrCoins_10p(int coinCount) {
        coins.put(10, coinCount);
    }

    private boolean checkIsValidAmount(int amount){
        if (amount < 0) {
            System.err.println("A negative amount of coins cannot be added to the machine!");
        }
        return amount >= 0;
    }

    public HashMap<Integer, Integer> getCoins() {
        return coins;
    }

    public void setCoins(HashMap<Integer, Integer> coins) {
        if (checkHashMapIsValid(coins)) {
            this.coins = coins;
        }
    }

    public void addCoins(HashMap<Integer, Integer> coins) {
        if (checkHashMapIsValid(coins)) {
            // Sum the value for each coin
            for (int key : coins.keySet()) {
                this.coins.put(key, this.coins.get(key) + coins.get(key));
            }
        }
    }

    private boolean checkHashMapIsValid(HashMap<Integer, Integer> coins) {
        boolean retBool = true;

        // Returns true if every key is in the list of allowedCoins and the corresponding number is positive
        for (int key : coins.keySet()) {
            retBool &= this.allowedCoins.contains(key) && coins.get(key) >= 0;
        }

        // If something is wrong
        if (!retBool) {
            System.err.println("There is something wrong with the HashMap of coins!");
        }

        return retBool;
    }

    public int cashCountValue() {
        int value = 0;

        for (int key : coins.keySet()) {
            value += key * coins.get(key);
        }

        return value;
    }

    // Returns the least possible number of coins for a given value if possible, otherwise it returns false
    public CashCount returnCoins(int value) {
        HashMap<Integer, Integer> hashReturnCoins = new HashMap<>();

        // Calculates the correct change in the least possible coins if
        for (int key : coins.keySet()) {
            while (key <= value && coins.get(key) > 0) {
                value -= key;
                hashReturnCoins.put(key, hashReturnCoins.get(key) + 1);
                coins.put(key, coins.get(key) - 1);
            }
        }

        // If correct change could be calculated return it, else return null
        if (value == 0) {
            return new CashCount(hashReturnCoins);
        } else {
            // Puts the coins back if change cannot be produced
            this.addCoins(hashReturnCoins);
            return null;
        }

    }
}
