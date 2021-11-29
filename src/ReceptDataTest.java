import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReceptDataTest {
    ReceptData r1;

    @Before
    public void setuo(){
        Boolean[] array = {false, false, false};
        r1 =  new ReceptData("name", array, "text");
    }

    @Test
    public void getName() {
        String n = r1.getName();
        assertEquals(n, "name");
    }

    @Test
    public void getFlags() {
        Boolean[] n = r1.getFlags();
        Boolean[] arr = {false, false, false};
        assertArrayEquals(n, arr);
    }

    @Test
    public void getText() {
        String t = r1.getText();
        assertEquals(t, "text");
    }

    @Test
    public void setName() {
        String str = "new";
        r1.setName(str);
        assertEquals(r1.getName(), str);
    }

    @Test
    public void setFlags() {
        Boolean[] arr = {false, true, false};
        r1.setFlags(arr);
        assertArrayEquals(r1.getFlags(), arr);
    }

    @Test
    public void setText() {
        String str = "new";
        r1.setText(str);
        assertEquals(r1.getText(), str);
    }
}