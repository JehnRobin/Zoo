package areas;

import animals.Aerial;
import animals.Animal;
import zoo.Codes;

public class Cage extends Habitat {
    public Cage(int capacity) {
        super(capacity);
    }

    @Override
    public byte add(Animal animal) {
        byte returnCode;

        if (animal instanceof Aerial) {
            returnCode = spaceAndCompatible(animal);
            if (returnCode == 0) {
                animals.add(animal);
            }
        } else {
            returnCode = Codes.WRONG_HABITAT;
        }

        return returnCode;
    }
}
