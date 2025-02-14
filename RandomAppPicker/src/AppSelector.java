import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;

public class AppSelector extends JFileChooser{

    private File[] last_files = null;

    public AppSelector() {
        super();
        this.setMultiSelectionEnabled(true);   
    }

    public File[] run(Component parent){
        int result = super.showOpenDialog(parent);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                System.out.println("SELECTED");
                last_files = super.getSelectedFiles();
                return last_files;
            case JFileChooser.CANCEL_OPTION:
                System.out.println("CANCEL");
                break;
            default:
                throw new AssertionError();
        }
        return null;
    }

    public File[] getLastFiles(){
        return last_files;
    }

    public void printFiles(){
        for (File f: last_files){
            System.out.println(f);
        }
    }
    
}
