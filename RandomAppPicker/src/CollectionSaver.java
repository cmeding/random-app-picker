
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;

public class CollectionSaver extends JFileChooser{

    public CollectionSaver() {
        super();
        this.setMultiSelectionEnabled(false);  
    }

    public boolean run(CollectionList collections){
        int result = this.showSaveDialog(null);
        System.out.println(this.getSelectedFile());
        File read = this.getSelectedFile();

        File file = createFormat(read);
        if (file == null){return false;}
        try {
            file.createNewFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
                oos.writeObject(collections);
                System.out.println("Collections saved to \"" + file + "\"");
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private File createFormat(File read) {
        if (read == null){return null;}
        String full_read = read.getAbsolutePath().toString();
        String full_read_lower = full_read.toLowerCase();
        int index = full_read_lower.lastIndexOf("\\");
        String name = full_read_lower.substring(index+1);
        int tindex = name.lastIndexOf(".");
        if (tindex == -1){
            return new File (full_read + ".ser");
        } else {
            String type = name.substring(tindex+1);
            return type.equals("ser") ? read : null;
            
        }

    }
    


}
