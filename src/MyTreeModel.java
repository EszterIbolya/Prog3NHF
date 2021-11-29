import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

public class MyTreeModel {
    DefaultTreeModel model;
    MutableTreeNode root = new DefaultMutableTreeNode("Receptek");
    MutableTreeNode node1 = new DefaultMutableTreeNode("Levesek");
    MutableTreeNode node2 = new DefaultMutableTreeNode("Főételek");
    MutableTreeNode node3 = new DefaultMutableTreeNode("Desszertek");
    int i1,i2,i3,i4 = 0;

    public MyTreeModel(){
        model = new DefaultTreeModel(root);
        root.insert(node1, 0);
        root.insert(node2, 1);
        root.insert(node3, 2);
    }

    public void decrease(Boolean[] arr){
        if(arr[0] == true){i1--;}
        else if(arr[1] == true){i2--;}
        else if(arr[2] == true){i3--;}
        else {i4--;}
    }

    public void setModel(DefaultTreeModel tr){
        model = tr;
    }

    public DefaultTreeModel getModel(){
        return model;
    }

    public MutableTreeNode getNode1(){
        return node1;
    }

    public MutableTreeNode getNode2(){
        return node2;
    }

    public MutableTreeNode getNode3(){
        return node3;
    }

    public MutableTreeNode getRoot(){
        return root;
    }

    public int getI1(){
        return i1;
    }

    public int getI2(){
        return i2;
    }

    public int getI3(){
        return i3;
    }

    public int getI4(){
        return i4;
    }

    public void increaseI1(){
        i1++;
    }

    public void increaseI2(){
        i2++;
    }

    public void increaseI3(){
        i3++;
    }

    public void increaseI4(){
        i4++;
    }
}

