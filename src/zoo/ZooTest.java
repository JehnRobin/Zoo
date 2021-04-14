package zoo;

import animals.*;
import areas.*;
import dataStructures.CashCount;
import dataStructures.ICashCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ZooTest {
    Zoo zoo = new Zoo();

    IArea cage = new Cage(2);
    IArea enclosure = new Enclosure(4);
    IArea aquarium = new Aquarium(3);
    IArea aquarium2 = new Aquarium(3);
    IArea entrance = Entrance.getInstance();
    IArea picnic = new PicnicArea();

    @BeforeEach
    public void before() {
        zoo.addArea(cage);
        zoo.addArea(enclosure);
        zoo.addArea(aquarium);
        zoo.addArea(picnic);
        zoo.addArea(aquarium2);
    }

    @Test
    public void connectionTest() {
        zoo.connectAreas(0, 1);
        zoo.connectAreas(1, 2);
        zoo.connectAreas(2, 3);
        zoo.connectAreas(3, 4);
        zoo.connectAreas(4, 1);
        zoo.connectAreas(7, 1);

        assert (cage.getAdjacentAreas().get(0) == 2);
        assert (enclosure.getAdjacentAreas().get(0) == 3);
        assert (aquarium.getAdjacentAreas().get(0) == 4);
        assert (picnic.getAdjacentAreas().get(0) == 1);
    }

    @Test
    public void addAreaTest() {
        assert (zoo.addArea(entrance) == -1);
        assert (zoo.addArea(cage) == -1);

        IArea cage2 = new Cage(2);
        IArea enclosure2 = new Enclosure(4);
        IArea entrance2 = Entrance.getInstance();
        IArea picnic2 = new PicnicArea();

        assert (zoo.addArea(cage2) == 6);
        assert (zoo.addArea(enclosure2) == 7);
        assert (zoo.addArea(entrance2) == -1);
        assert (zoo.addArea(picnic2) == 8);
        zoo.removeArea(8);
        assert (zoo.addArea(picnic2) == 9);

    }

    @Test
    public void removeTest() {
        zoo.connectAreas(0, 1);
        zoo.connectAreas(1, 2);
        zoo.connectAreas(2, 3);
        zoo.connectAreas(3, 4);
        assert (zoo.getArea(3) != null);
        zoo.removeArea(3);
        assert (!zoo.getArea(2).getAdjacentAreas().contains(3));
        assert (zoo.getArea(3) == null);
        zoo.removeArea(3);

    }

    @Test
    public void pathTest() {
        zoo.connectAreas(0, 1);
        zoo.connectAreas(1, 2);
        zoo.connectAreas(2, 3);
        zoo.connectAreas(3, 4);
        zoo.connectAreas(4, 1);

        assert (zoo.isPathAllowed(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 1))));
        assert (!zoo.isPathAllowed(new ArrayList<>(Arrays.asList(0, 1, 5, 9))));
    }

    @Test
    public void addAnimalTest() {
        Lion theo = new Lion("Theo");
        Lion mufasa = new Lion("Mufasa");
        Zebra martin = new Zebra("Martin");
        Gazelle jumpy = new Gazelle("Jumpy");

        Seal robbin = new Seal("Robbin");
        Shark sharkira = new Shark("Sharkira");
        Shark bruce = new Shark("Bruce");
        Starfish patrick = new Starfish("patrick");

        Parrot oliver = new Parrot("Oliver");
        Buzzard buzzAldrin = new Buzzard("Buzz Aldrin");

        assert (zoo.addAnimal(1, oliver) == 0);
        assert (zoo.addAnimal(4, oliver) == 1);
        assert (zoo.addAnimal(2, oliver) == 2);
        assert (zoo.addAnimal(1, buzzAldrin) == 4);
        assert (zoo.addAnimal(1, oliver) == 0);
        assert (zoo.addAnimal(1, oliver) == 3);

        assert (zoo.addAnimal(2, theo) == 0);
        assert (zoo.addAnimal(2, mufasa) == 0);
        assert (zoo.addAnimal(2, martin) == 4);
        assert (zoo.addAnimal(2, jumpy) == 4);

        assert (zoo.addAnimal(3, robbin) == 0);
        assert (zoo.addAnimal(3, patrick) == 0);
        assert (zoo.addAnimal(3, sharkira) == 4);

        assert (zoo.addAnimal(5, sharkira) == 0);
        assert (zoo.addAnimal(5, bruce) == 0);
        assert (zoo.addAnimal(5, robbin) == 4);
        assert (zoo.addAnimal(5, patrick) == 0);
        assert (zoo.addAnimal(5, robbin) == 3);
    }

    @Test
    public void visitTest() {
        Lion theo = new Lion("Theo");
        Lion mufasa = new Lion("Mufasa");
        Zebra martin = new Zebra("Martin");
        Gazelle jumpy = new Gazelle("Jumpy");

        Seal robbin = new Seal("Robbin");
        Shark sharkira = new Shark("Sharkira");
        Shark bruce = new Shark("Bruce");
        Starfish patrick = new Starfish("Patrick");

        Parrot oliver = new Parrot("Oliver");
        Buzzard buzzAldrin = new Buzzard("Buzz Aldrin");

        zoo.connectAreas(0, 1);
        zoo.connectAreas(1, 2);
        zoo.connectAreas(2, 3);
        zoo.connectAreas(3, 4);
        zoo.connectAreas(4, 1);

        zoo.addAnimal(2, theo);
        zoo.addAnimal(2, mufasa);

        zoo.addAnimal(1, oliver);
        zoo.addAnimal(1, oliver);

        zoo.addAnimal(3, sharkira);
        zoo.addAnimal(3, bruce);
        zoo.addAnimal(3, patrick);

        assert (zoo.visit(new ArrayList<>(Arrays.asList(1, 2, 3))).equals(new ArrayList<>(Arrays.asList(oliver.getNickname(), oliver.getNickname(), theo.getNickname(), mufasa.getNickname(), sharkira.getNickname(), bruce.getNickname(), patrick.getNickname()))));
    }

    @Test
    public void testFindReachableAreas() {
        IArea cage2 = new Cage(2);
        IArea enclosure2 = new Enclosure(4);
        IArea picnic2 = new PicnicArea();

        zoo.addArea(cage2);
        zoo.addArea(enclosure2);
        zoo.addArea(picnic2);

        zoo.connectAreas(0, 1);
        zoo.connectAreas(1, 2);
        zoo.connectAreas(2, 3);
        zoo.connectAreas(3, 8);
        zoo.connectAreas(3, 5);

        System.out.println(zoo.findUnreachableAreas());

        assert (zoo.findUnreachableAreas().equals(new ArrayList<>(Arrays.asList(4, 6, 7))));
    }

    @Test
    public void rightChange(){
        zoo.setEntranceFee(12, 40);

        // Money originally inserted
        HashMap<Integer, Integer> coins = new HashMap<>();
        coins.put(2000, 5);
        coins.put(1000, 5);
        coins.put(500, 5);
        coins.put(200, 5);
        coins.put(100, 5);
        coins.put(50, 5);
        coins.put(20, 5);
        coins.put(10, 5);
        CashCount cashCount = new CashCount(coins);
        zoo.setCashSupply(cashCount);


        // Money inserted
        HashMap<Integer, Integer> pay = new HashMap<>();
        pay.put(2000, 1);
        pay.put(1000, 0);
        pay.put(500, 0);
        pay.put(200, 0);
        pay.put(100, 0);
        pay.put(50, 0);
        pay.put(20, 0);
        pay.put(10, 0);
        CashCount payCount = new CashCount(pay);


        // Expected change
        HashMap<Integer, Integer> returnH = new HashMap<>();
        returnH.put(2000, 0);
        returnH.put(1000, 0);
        returnH.put(500, 1);
        returnH.put(200, 1);
        returnH.put(100, 0);
        returnH.put(50, 1);
        returnH.put(20, 0);
        returnH.put(10, 1);
        CashCount returnCount = new CashCount(returnH);

        assert(((CashCount) zoo.payEntranceFee(payCount)).getCoins().equals(returnH));

        cashCount = (CashCount) zoo.getCashSupply();

        for (int key: cashCount.getCoins().keySet()) {
            System.out.println(key + ": " + cashCount.getCoins().get(key) + " " + returnCount.getCoins().get(key));
        }
    }

    @Test
    public void rightChange2(){
        zoo.setEntranceFee(12, 40);

        // Money originally inserted
        HashMap<Integer, Integer> coins = new HashMap<>();
        coins.put(2000, 5);
        coins.put(1000, 5);
        coins.put(500, 5);
        coins.put(200, 5);
        coins.put(100, 5);
        coins.put(50, 5);
        coins.put(20, 5);
        coins.put(10, 5);
        CashCount cashCount = new CashCount(coins);
        zoo.setCashSupply(cashCount);


        // Money inserted
        HashMap<Integer, Integer> pay = new HashMap<>();
        pay.put(2000, 0);
        pay.put(1000, 0);
        pay.put(500, 0);
        pay.put(200, 0);
        pay.put(100, 0);
        pay.put(50, 0);
        pay.put(20, 0);
        pay.put(10, 10000);
        CashCount payCount = new CashCount(pay);


        // Expected change 987.60
        HashMap<Integer, Integer> returnH = new HashMap<>();
        returnH.put(2000, 5);
        returnH.put(1000, 5);
        returnH.put(500, 5);
        returnH.put(200, 5);
        returnH.put(100, 5);
        returnH.put(50, 5);
        returnH.put(20, 5);
        returnH.put(10, 7941);
        CashCount returnCount = new CashCount(returnH);

        assert(((CashCount) zoo.payEntranceFee(payCount)).getCoins().equals(returnH));

        cashCount = (CashCount) zoo.getCashSupply();

        for (int key: cashCount.getCoins().keySet()) {
            System.out.println(key + ": " + cashCount.getCoins().get(key) + " " + returnCount.getCoins().get(key));
        }
    }
}