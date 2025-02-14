import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MainWindow extends JFrame{
    private AppDisplayList CURR_LIST = null;
    private CollectionList COL_LIST = new CollectionList();
    private String FILEPATH = "./Collections";
    private String FILENAME = "allCollections";
    private Color BGCOLOR = new Color(30,30,30);

    public MainWindow(String w_title) throws HeadlessException {
        super();
        this.setLayout(new GridBagLayout());
        this.setTitle(w_title);
        this.getContentPane().setBackground(BGCOLOR);

        COL_LIST = loadCollections();
        COL_LIST.initPopup();
        COL_LIST.initAppPopup();
        JPanel fileRow = createFileRow();
        JPanel buttonRow = createButtonRow();

        this.setTransferHandler(createTransferHandler());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveCollections(COL_LIST);
                MainWindow.this.dispose();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.75;
        this.add(fileRow, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.02;
        JPanel filler = new JPanel();
        filler.setBackground(BGCOLOR);
        this.add(filler,gbc);

        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 0.28;
        this.add(buttonRow, gbc);
    }

    public JPanel createFileRow(){
        JPanel fileRow = new JPanel(new GridLayout(1,2,15,0));
        fileRow.setBackground(BGCOLOR);

        JScrollPane apps = new JScrollPane();
        COL_LIST.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                CURR_LIST = COL_LIST.getSelectedValue();
                apps.setViewportView(CURR_LIST);
            }
        });
        
        JScrollPane collections = new JScrollPane();
        collections.setViewportView(COL_LIST);

        fileRow.add(collections);
        fileRow.add(apps);
        //------------------------------------------------------------------------
        return fileRow;
    }

    public JPanel createButtonRow(){
        JPanel buttonRow = new JPanel(new GridLayout(1,3,15,0));
        buttonRow.setBackground(BGCOLOR);
        
        JButton addApps = new JButton("Add Apps");
        addApps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CURR_LIST.addSelection((new AppSelector()).run(null));
            }
        });

        JButton randomize = new JButton("Randomize");
        randomize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RandomPicker.randomSingleDisplay(CURR_LIST);
            }
            
        });

        buttonRow.add(createCollectionButtons());
        buttonRow.add(randomize);
        buttonRow.add(addApps);
        //------------------------------------------------------------------------
        return buttonRow;
    }

    public JPanel createCollectionButtons(){
        JPanel col_bt = new JPanel(new GridLayout(3,1,0,10));
        col_bt.setBackground(BGCOLOR);
        //-------------------------------------------------------------------------

        JButton createCollection = new JButton("Create Collection");
        createCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                COL_LIST.createAppList();
            }
        });

        JButton save = new JButton("Save Collections");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("SAVE");
                if (false == new CollectionSaver().run(COL_LIST)){System.out.println("CANCEL");}
            }
        });

        JButton load = new JButton("Load Collections");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("LOAD");
                CollectionList new_col = (new CollectionLoader()).run();
                if(new_col == null){
                    System.out.println("CANCEL");
                } else {
                    COL_LIST.setModel(new_col.getModel());
                    COL_LIST.repaint();
                    COL_LIST.revalidate();

                    COL_LIST = new_col;

                    COL_LIST.initPopup();
                    COL_LIST.initAppPopup();
                }
            }
        });

        //-------------------------------------------------------------------------
        col_bt.add(createCollection);
        col_bt.add(save);
        col_bt.add(load);

        return col_bt;
    }

    public TransferHandler createTransferHandler(){
        return (new TransferHandler(){
            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                try {
                    List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    CURR_LIST.addSelection(files);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
    }

    public void saveCollections(CollectionList col){
        String f_path = FILEPATH + "/" + FILENAME + ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f_path))){
            oos.writeObject(col);
            System.out.println("Collections saved to \"" + f_path + "\"");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public CollectionList loadCollections(){
        String f_path = FILEPATH + "/" + FILENAME + ".ser";
        File file = new File(f_path);
        System.out.println(file.toPath().toAbsolutePath().toString());
        if (!file.exists()){
            try {
                System.out.println(file.exists());
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            CollectionList col = (CollectionList) ois.readObject();
            System.out.println("Collections loaded from \"" + f_path + "\"");
            return col;
        } catch (IOException e){
            System.err.println("Collection FILE Corrupted!");
            return new CollectionList();
        } catch (Exception e){
            e.printStackTrace();
            return new CollectionList();
        }
    }

    
}
