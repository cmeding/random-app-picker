
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;

public class CollectionLoader extends JFileChooser{
    public CollectionLoader(){
        super();
        this.setMultiSelectionEnabled(false);  
    }

    public CollectionList run(){
        int result = this.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION){return null;}
        
        File file = this.getSelectedFile();
        if (checkFile(file) == false){return null;}

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            CollectionList col = (CollectionList) ois.readObject();
            System.out.println("Collections loaded from \"" + file + "\"");
            return col;
        } catch (IOException e){
            System.err.println("Collection FILE Corrupted!");
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }    

    private boolean checkFile(File file) {
        if (!file.exists()){return false;}
        String full_path = file.getAbsolutePath().toString();
        int index = full_path.lastIndexOf(".");
        String type = full_path.substring(index+1);
        type = type.toLowerCase();
        if (!type.equals("ser")){return false;}
        return true;
    }

}
