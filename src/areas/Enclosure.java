package areas;

import animals.Animal;
import animals.Terrestrial;
import zoo.Codes;

public class Enclosure extends Habitat {
    public Enclosure(int capacity) {
        super(capacity);
    }

    @Override
    public byte add(Animal animal) {
        byte returnCode;

        if (animal instanceof Terrestrial) {
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
