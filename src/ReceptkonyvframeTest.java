import org.junit.Test;

import javax.swing.*;
import javax.swing.tree.TreePath;

import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class ReceptkonyvframeTest {
    @Test
    public void DeleteButtonTest() throws InterruptedException {
        Receptkonyvframe fr = new Receptkonyvframe();
        fr.setAddTexts("C","A");
        fr.getAddbutton().doClick();
        Object[] node = {fr.recepttree.getModel().getRoot(), fr.recepttree.getModel().getChild(fr.recepttree.getModel().getRoot(),0)};
        fr.recepttree.setSelectionPath(new TreePath(node));
        fr.recepttree.getSelectionPath().getLastPathComponent().toString();
        fr.getDeletebutton().doClick();
        int test = 0;
        ListIterator<ReceptData> iter = fr.getReceptek().listIterator();
        while(iter.hasNext()){
            ReceptData tmp = iter.next();
            if(tmp.getName().equals("C")){
                test = 1;
            }
        }
        assertEquals(test, 0);

    }

    @Test
    public void AddButtonTest(){
        Receptkonyvframe fr = new Receptkonyvframe();
        fr.setAddTexts("A","A");
        fr.getAddbutton().doClick();
        int test = 0;
        ListIterator<ReceptData> iter = fr.getReceptek().listIterator();
        while(iter.hasNext()){
            ReceptData tmp = iter.next();
            if(tmp.getName().equals("A")){
                test = 1;
            }
        }
        assertEquals(test, 1);

    }

    @Test
    public void ShowButtonTest(){
        Receptkonyvframe fr = new Receptkonyvframe();
        fr.setAddTexts("A","A");
        fr.getAddbutton().doClick();
        Object[] node = {fr.recepttree.getModel().getRoot(), fr.recepttree.getModel().getChild(fr.recepttree.getModel().getRoot(),0)};
        fr.recepttree.setSelectionPath(new TreePath(node));
        fr.recepttree.getSelectionPath().getLastPathComponent().toString();
        fr.getShowbutton().doClick();
        int rows = fr.getShow().getRows();
        assertNotEquals(rows, 1);
    }

    @Test
    public void SortButtonTest(){
        Receptkonyvframe fr = new Receptkonyvframe();
        fr.setAddTexts("B","A");
        fr.getAddbutton().doClick();
        fr.setAddTexts("A","A");
        fr.getAddbutton().doClick();
        fr.getSortbutton().doClick();
        int test = 0;
        if(fr.getReceptek().get(0).getName().equals("A")) {test = 1;}
        assertEquals(test, 1);
    }

    @Test
    public void SearchButtonTest(){
        Receptkonyvframe fr = new Receptkonyvframe();
        fr.setAddTexts("B","A");
        fr.getAddbutton().doClick();
        fr.setSearch("B");
        fr.getSearchbutton().doClick();
        int rows = fr.getShow().getRows();
        assertNotEquals(rows,1 );
    }
}