import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

public class CollectionList extends JList<AppDisplayList> implements Serializable{
    private final List<AppDisplayList> list = new ArrayList<>();
    private final DefaultListModel<AppDisplayList> model = new DefaultListModel<>();
    private transient JPopupMenu rMenu;
    public CollectionList() {
        this.setModel(model);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initPopup();
    }

    public void initPopup(){
        rMenu = new JPopupMenu();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            public void showPopup(MouseEvent e){
                if (e.isPopupTrigger()){
                    int row = CollectionList.this.locationToIndex(e.getPoint());
                    if (row != -1) {
                        if(!(CollectionList.this.isSelectedIndex(row))){
                            CollectionList.this.setSelectedIndex(row);
                        }
                        rMenu.show(CollectionList.this, e.getX(), e.getY());
                    }
                }
            }
        });

        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelected();
            }
        });

        rMenu.add(delete);

        JMenuItem rename = new JMenuItem("Rename");
        rename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renameSelected();
            }
        });

        rMenu.add(rename);
    }

    public void createAppList(){
        NamingWindow naming = new NamingWindow();
        naming.setVisible(true);
        String input = naming.getInput();
        if (input != null && !(input.equals(""))){
            if (!(doesExist(input))){
                list.add(new AppDisplayList(input));
                model.addElement(list.get(list.size()-1));
            }
        }
        naming.dispose();
    }

    public boolean doesExist(String s){
        for (AppDisplayList apl:list){
            if(apl.getName().equals(s)){
                return true;
            }
        }
        return false;
    }

    public void deleteSelected(){
        list.remove(CollectionList.this.getSelectedIndex());
        model.remove(CollectionList.this.getSelectedIndex());
        CollectionList.this.repaint();
    }

    public void renameSelected(){
        NamingWindow naming = new NamingWindow();
        naming.setVisible(true);
        String input = naming.getInput();
        if (input != null && !(input.equals(""))){
            if (!(doesExist(input))){
                list.get(CollectionList.this.getSelectedIndex()).setName(input);
                model.get(CollectionList.this.getSelectedIndex()).setName(input);
                CollectionList.this.repaint();
            }
        }
        naming.dispose();   
    }

    public void initAppPopup(){
        for (AppDisplayList apl: list){
            apl.initPopup();
        }
    }

}
