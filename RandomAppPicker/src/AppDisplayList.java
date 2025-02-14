import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

public class AppDisplayList extends JList<AppItem> implements Serializable{
    private transient JPopupMenu rMenu;
    private String name = "$PLACEHOLDER$";
    private final List<AppItem> list = new ArrayList<>();
    private final DefaultListModel<AppItem> model = new DefaultListModel<>();

    public AppDisplayList(String n) {
        name = n;
        this.setModel(model);
        this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
                    int row = AppDisplayList.this.locationToIndex(e.getPoint());
                    if (row != -1) {
                        if(!(AppDisplayList.this.isSelectedIndex(row))){
                            AppDisplayList.this.setSelectedIndex(row);
                        }
                        rMenu.show(AppDisplayList.this, e.getX(), e.getY());
                    }
                }
            }
        });

        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<AppItem> toDelete = AppDisplayList.this.getSelectedValuesList();
                for (AppItem o: toDelete){
                    list.remove(o);
                }
                while(toDelete.size() > 0){
                    for (int i = 0; i < model.size(); i++){
                        if (model.get(i).equals(toDelete.get(0))){
                            model.remove(i);
                            toDelete.remove(0);
                        }
                    }
                }
            }
        });

        rMenu.add(delete);
    }

    public void addSelection(File[] files){
        if (files == null){return;}
        for (File f: files){
            AppItem temp = new AppItem(f);
            if (list.contains(temp)){
                System.out.println("CONTAINS");
                continue;
            }
            list.add(temp);
            model.addElement(temp);
        }
    }

    public void addSelection(List<File> files){
        if (files == null){return;}
        for (File f: files){
            AppItem temp = new AppItem(f);
            if (list.contains(temp)){
                System.out.println("CONTAINS");
                continue;
            }
            list.add(temp);
            model.addElement(temp);
        }
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof AppDisplayList){
            return (this.name.equals(((AppDisplayList) obj).name));
        }
        return false;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String s){
        name = s;
    }

    public int getListSize(){
        return this.list.size();
    }

    public List<AppItem> getList(){
        return list;
    }
    
}
