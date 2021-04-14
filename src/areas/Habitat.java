package areas;

import animals.Animal;
import zoo.Codes;

import java.util.ArrayList;

public abstract class Habitat extends Area {
    protected int capacity;
    protected ArrayList<Animal> animals = new ArrayList<>();

    public Habitat(int capacity) {
        this.capacity = capacity;
    }

    public abstract byte add(Animal animal);

    public boolean compatible(Animal animal) {
        boolean isCompatible = true;

        for (Animal animalInHabitat : animals) {
            isCompatible &= animalInHabitat.isCompatibleWith(animal);
        }

        return isCompatible;
    }

    public boolean isFull() {
        return capacity == animals.size();
    }

    public byte spaceAndCompatible(Animal animal) {
        if (isFull()) {
            return Codes.HABITAT_FULL;
        } else {
            if (!compatible(animal)) {
                return Codes.INCOMPATIBLE_INHABITANTS;
            } else {
                return Codes.ANIMAL_ADDED;
            }
        }
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }
}
