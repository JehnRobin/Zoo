package areas;

import org.junit.Test;

public class AreaTest {
    @Test
    public void connectionTest() {
        Area cage = new Cage(1);
        Area enclosure = new Enclosure(1);
        Area aquarium = new Aquarium(1);
        Area entrance = Entrance.getInstance();
        Area picnic = new PicnicArea();
    }

}
