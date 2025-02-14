import java.io.File;
import java.io.Serializable;

public class AppItem implements Serializable{
    private String app_name = "$PLACEHOLDER$";
    private File file = null;

    public AppItem(File f) {
        file = f;
        app_name = createName(f);
    }

    public String createName(File f){
        String path_name = f.toString();
        int index = path_name.lastIndexOf("\\");
        return path_name.substring(index+1);
    }

    @Override
    public String toString() {
        //return file.toString();
        return app_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AppItem) {
            File temp = ((AppItem) obj).file;
            return (this.file.equals(temp));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }
    
    public String completeName(){
        return "[" + app_name + ":" + "\"" + file.getAbsolutePath() + "\"" + "]";
    }

    public String getName(){
        return app_name;
    }

    public String getPath(){
        return "\"" + file.getAbsolutePath() + "\"";
    }
}
