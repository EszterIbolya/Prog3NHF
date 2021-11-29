import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Receptkonyvframe extends JFrame {
    private List<ReceptData> receptek;

    JTextField search = new JTextField(20);
    JTextArea show = new JTextArea();
    JTextField nametext = new JTextField(10);
    JTextArea recepttext = new JTextArea(5,50);
    JCheckBox flag1 = new JCheckBox("Leves");
    JCheckBox flag2 = new JCheckBox("Főétel");
    JCheckBox flag3 = new JCheckBox("Desszert");
    JButton sortbutton = new JButton("Rendez");
    JButton deletebutton = new JButton("Töröl");
    JButton showbutton = new JButton("Mutat");
    JButton searchbutton = new JButton("Keres");
    JButton addbutton = new JButton("Felvétel");
    MyTreeModel model = new MyTreeModel();
    JTree recepttree = new JTree(model.getModel());
    JCheckBox[] checkList = new JCheckBox[3];

    public JButton getDeletebutton(){
        return deletebutton;
    }

    public JButton getSortbutton(){
        return sortbutton;
    }

    public JButton getSearchbutton(){
        return searchbutton;
    }

    public void setSearch(String str){
        search.setText(str);
    }

    public JTextArea getShow(){
        return show;
    }

    public JButton getAddbutton(){
        return addbutton;
    }

    public JButton getShowbutton(){
        return showbutton;
    }

    public List<ReceptData> getReceptek(){
        return receptek;
    }

    public void setAddTexts(String str1, String str2){
        nametext.setText(str1);
        recepttext.setText(str2);
    }

    public class SearchButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            show.setText("");
            String s = search.getText();
            ListIterator<ReceptData> iter = receptek.listIterator();
            while ( iter.hasNext()){
                ReceptData tmp = iter.next();
                if(tmp.getName().toUpperCase().contains(s) || tmp.getName().toLowerCase().contains(s)) {
                    show.setText(show.getText() + tmp.getName() + "\n" + tmp.getText() + "\n\n");
                }
            }
            if(show.getText().equals("")){
                show.setText("Nincs találat!");
            }
        }
    }

    public class AddButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String strname = nametext.getText();
            String strtext = recepttext.getText();
            Boolean[] flags = new Boolean[3];
            flags[0] = flag1.isSelected();
            flags[1] = flag2.isSelected();
            flags[2] = flag3.isSelected();
            ReceptData temp = new ReceptData(strname, flags, strtext);
            receptek.add(temp);
            MutableTreeNode node = new DefaultMutableTreeNode(strname);
            if(flag1.isSelected()) { model.getModel().insertNodeInto(node,model.getNode1(), model.getI1()); model.increaseI1();}
            else if (flag2.isSelected()) { model.getModel().insertNodeInto(node,model.getNode2(), model.getI2()); model.increaseI2();}
            else if (flag3.isSelected()) { model.getModel().insertNodeInto(node, model.getNode3(), model.getI3()); model.increaseI3();}
            else {model.getModel().insertNodeInto(node, model.getRoot(), model.getI4()); model.increaseI4();}
            recepttree.setModel(model.getModel());
            nametext.setText("");
            recepttext.setText("");
        }
    }

    public class DeleteButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String str = recepttree.getSelectionPath().getLastPathComponent().toString();
            Object current = recepttree.getSelectionPath().getLastPathComponent();
            DefaultMutableTreeNode currentnode = (DefaultMutableTreeNode) current;
            Object parent = recepttree.getSelectionPath().getParentPath().getLastPathComponent();
            DefaultMutableTreeNode parentnode = (DefaultMutableTreeNode) parent;
            DefaultMutableTreeNode actual = (DefaultMutableTreeNode) parentnode.getFirstChild();
            int idx = 0;
            DefaultMutableTreeNode node1 = (DefaultMutableTreeNode)((DefaultMutableTreeNode) parent).getChildBefore(currentnode);
            int a = 0;
            if(node1 != null){ a=parentnode.getIndex(node1);}
            while(a!=0){
                recepttree.setSelectionPath(new TreePath(actual));
                if(recepttree.getSelectionPath().getLastPathComponent().toString().equals(str)) {
                    idx++;
                }
                a--;
            }
            ListIterator<ReceptData> iter = receptek.listIterator();
            Boolean[] arr = new Boolean[3];
            while(iter.hasNext()){
                ReceptData temp = iter.next();
                arr = temp.getFlags();
                if(idx == 0 && temp.getName().equals(str)){iter.remove();}
                if(temp.getName().equals(str) ){ idx--; }
                if(idx == 0 && temp.getName().equals(str)){iter.remove();}
            }
            DefaultMutableTreeNode selected = (DefaultMutableTreeNode) recepttree.getSelectionPath().getLastPathComponent();
            DefaultTreeModel newmodel = (DefaultTreeModel) recepttree.getModel();
            newmodel.removeNodeFromParent(selected);
            model.setModel(newmodel);
            model.decrease(arr);
        }
    }

    public class ShowButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            show.setText("");
            String str = recepttree.getSelectionPath().getLastPathComponent().toString();
            ListIterator<ReceptData> iter = receptek.listIterator();
            while(iter.hasNext()){
                ReceptData tmp = iter.next();
                if(tmp.getName().equals(str)){
                    show.setText(tmp.getName() + "\n" + tmp.getText() + "\n");
                }
            }
            if(show.getText().equals("")){
                show.setText("Nincs találat!");
            }
        }
    }

    public class SortButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Collections.sort(receptek, new NameComp());
            MyTreeModel newmodel = new MyTreeModel();
            ListIterator<ReceptData> iter = receptek.listIterator();
            while(iter.hasNext()){
                ReceptData tmp = iter.next();
                Boolean[] flagek = tmp.getFlags();
                MutableTreeNode node = new DefaultMutableTreeNode(tmp.getName());
                if(flagek[0]) { newmodel.getModel().insertNodeInto(node,newmodel.getNode1(), newmodel.getI1()); newmodel.increaseI1();}
                else if (flagek[1]) { newmodel.getModel().insertNodeInto(node,newmodel.getNode2(), newmodel.getI2()); newmodel.increaseI2();}
                else if (flagek[2]) { newmodel.getModel().insertNodeInto(node,newmodel.getNode3(), newmodel.getI3()); newmodel.increaseI3();}
                else {newmodel.getModel().insertNodeInto(node, newmodel.getRoot(), newmodel.getI4()); newmodel.increaseI4();}
            }
            model = newmodel;
            recepttree.setModel(model.getModel());
        }
    }
    public class Listener implements ItemListener {
        private final int maxselected = 1;
        private int selectionCounter = 0;

        @Override
        public void itemStateChanged(ItemEvent e) {
            JCheckBox source = (JCheckBox) e.getSource();

            if (source.isSelected()) {
                selectionCounter++;
                if (selectionCounter == maxselected)
                    for (JCheckBox box: checkList)
                        if (!box.isSelected())
                            box.setEnabled(false);
            }
            else {
                selectionCounter--;
                if (selectionCounter < maxselected)
                    for (JCheckBox box: checkList)
                        box.setEnabled(true);
            }
        }
    }

    private void initComponents(){
        JPanel newrecept = new JPanel();
        JPanel viewrecept = new JPanel();
        JPanel treepanel = new JPanel();

        show.setEditable(false);
        viewrecept.setLayout(new BorderLayout());
        viewrecept.add(show, BorderLayout.CENTER);
        JPanel toprow = new JPanel();
        JLabel s = new JLabel("Keresés:");
        JScrollPane jScrollPane1 = new JScrollPane(show);
        toprow.setLayout(new FlowLayout());
        toprow.add(s);
        toprow.add(search);
        toprow.add(searchbutton);
        viewrecept.add(toprow, BorderLayout.NORTH);
        viewrecept.add(jScrollPane1, BorderLayout.CENTER);

        JLabel namelabel = new JLabel("Új recept neve:");
        JLabel szoveglabel = new JLabel("Szövege:");
        JScrollPane scrollPane2 = new JScrollPane(recepttext);
        newrecept.setLayout(new FlowLayout());
        newrecept.add(namelabel);
        newrecept.add(nametext);
        newrecept.add(szoveglabel);
        newrecept.add(scrollPane2);
        newrecept.add(flag1);
        newrecept.add(flag2);
        newrecept.add(flag3);
        newrecept.add(addbutton);

        treepanel.setLayout(new BorderLayout());
        JScrollPane jScrollPane3 = new JScrollPane(recepttree);
        treepanel.add(jScrollPane3, BorderLayout.CENTER);
        JPanel bottomrow = new JPanel();
        bottomrow.setLayout(new FlowLayout());
        bottomrow.add(deletebutton);
        bottomrow.add(showbutton);
        bottomrow.add(sortbutton);
        treepanel.add(bottomrow, BorderLayout.SOUTH);

        searchbutton.addActionListener(new SearchButtonActionListener());
        addbutton.addActionListener(new AddButtonActionListener());
        deletebutton.addActionListener(new DeleteButtonActionListener());
        showbutton.addActionListener(new ShowButtonActionListener());
        sortbutton.addActionListener(new SortButtonActionListener());
        checkList[0] = flag1;
        checkList[1] = flag2;
        checkList[2] = flag3;
        for(int i = 0; i < 3; i++){
            checkList[i].addItemListener(new Listener());
        }

        this.setLayout(new BorderLayout());
        add(treepanel, BorderLayout.WEST);
        add(newrecept, BorderLayout.SOUTH);
        add(viewrecept, BorderLayout.CENTER);


    }

    public Receptkonyvframe() {
        super("Receptkönyv");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try{
            receptek = new ArrayList<ReceptData>();
            BufferedReader br = new BufferedReader(new FileReader("receptek.json"));
            String str = br.readLine();
            while(str != null) {
                JSONObject obj = (JSONObject) new JSONParser().parse(str);
                Boolean[] flagek = new Boolean[3];
                flagek[0] = (boolean)obj.get("flag1");
                flagek[1] = (boolean)obj.get("flag2");
                flagek[2] = (boolean)obj.get("flag3");
                ReceptData tmp = new ReceptData((String) obj.get("name"), flagek ,(String)obj.get("text"));
                receptek.add(tmp);
                MutableTreeNode node = new DefaultMutableTreeNode((String)obj.get("name"));
                if(flagek[0]) { model.getModel().insertNodeInto(node,model.getNode1(), model.getI1()); model.increaseI1();}
                else if (flagek[1]) { model.getModel().insertNodeInto(node,model.getNode2(), model.getI2()); model.increaseI2();}
                else if (flagek[2]) { model.getModel().insertNodeInto(node,model.getNode3(), model.getI3()); model.increaseI3();}
                else {model.getModel().insertNodeInto(node, model.getRoot(), model.getI4()); model.increaseI4();}
                str = br.readLine();
            }
            br.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    ListIterator<ReceptData> iter = receptek.listIterator();
                    BufferedWriter wr = new BufferedWriter(new FileWriter("receptek.json"));
                    while(iter.hasNext()) {
                        ReceptData tmp = iter.next();
                        Boolean[] flagek = tmp.getFlags();
                        JSONObject inner = new JSONObject();
                        inner.put("name", tmp.getName());
                        inner.put("flag1", flagek[0]);
                        inner.put("flag2", flagek[1]);
                        inner.put("flag3", flagek[2]);
                        inner.put("text", tmp.getText());
                        String str = inner.toString();
                        wr.write(str);
                        wr.newLine();
                    }
                    wr.close();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setMinimumSize(new Dimension(1200, 500));
        initComponents();
    }

    public static void main(String[] args){
        Receptkonyvframe rf = new Receptkonyvframe();
        rf.setVisible(true);
    }
}
