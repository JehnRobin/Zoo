package areas;

import animals.Animal;
import animals.Aquatic;
import zoo.Codes;

public class Aquarium extends Habitat {
    public Aquarium(int capacity) {
        super(capacity);
    }

    @Override
    public byte add(Animal animal) {
        byte returnCode;

        if (animal instanceof Aquatic) {
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
