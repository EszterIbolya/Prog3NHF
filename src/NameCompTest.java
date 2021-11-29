import static org.junit.Assert.*;
import org.junit.*;

public class NameCompTest {
    NameComp nameComp;
    Boolean[] array = {false, false, false};
    ReceptData r1;
    ReceptData r2;

    @Before
    public void setup() {
        nameComp = new NameComp();
        r1 = new ReceptData("A", array, "text");
        r2 = new ReceptData("B", array, "text");
    }

    @Test
    public void testCompare1stBigger() {
        int res = nameComp.compare(r1, r2);
        assertEquals(res, -1);
    }

    @Test
    public void testCompare2ndBigger(){
        r1.setName("B");
        r2.setName("A");
        int res = nameComp.compare(r1, r2);
        assertEquals(res, 1);
    }

    @Test
    public void testCompareEquals(){
        r1.setName("B");
        int res = nameComp.compare(r1, r2);
        assertEquals(res, 0);
    }
}