package areas;

public class Entrance extends Area{

    private Entrance(){}

    private static Entrance instance;


    public static Entrance getInstance() {
        if (Entrance.instance == null){
            Entrance.instance = new Entrance();
        }
        return Entrance.instance;
    }
}
