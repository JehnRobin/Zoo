package animals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class AnimalTest {

    @Test
    public void testCompatibilityAqua() {
        Shark sharky = new Shark("sharky");
        Seal shanty = new Seal("shanty");
        Starfish patrick = new Starfish("patrick");

        assert (shanty.isCompatibleWith(patrick));
        assert (!shanty.isCompatibleWith(sharky));
        assert (shanty.isCompatibleWith(shanty));

        assert (sharky.isCompatibleWith(patrick));
        assert (!sharky.isCompatibleWith(shanty));
        assert (sharky.isCompatibleWith(sharky));

        assert (patrick.isCompatibleWith(shanty));
        assert (patrick.isCompatibleWith(sharky));
        assert (patrick.isCompatibleWith(patrick));
    }

    @Test
    public void testCompatibilityTerra() {
        Lion leo = new Lion("leo");
        Gazelle geez = new Gazelle("geez");
        Zebra dabi = new Zebra("dabi");

        assert (!leo.isCompatibleWith(geez));
        assert (!leo.isCompatibleWith(dabi));
        assert (leo.isCompatibleWith(leo));

        assert (dabi.isCompatibleWith(geez));
        assert (!dabi.isCompatibleWith(leo));
        assert (dabi.isCompatibleWith(dabi));

        assert (geez.isCompatibleWith(dabi));
        assert (!geez.isCompatibleWith(leo));
        assert (geez.isCompatibleWith(geez));

    }

    @Test
    public void testCompatibilityAir() {
        Buzzard buzwing = new Buzzard("buzwing");
        Parrot perry = new Parrot("perry");

        assert (!buzwing.isCompatibleWith(perry));
        assert (buzwing.isCompatibleWith(buzwing));

        assert (!perry.isCompatibleWith(buzwing));
        assert (perry.isCompatibleWith(perry));
    }

}
