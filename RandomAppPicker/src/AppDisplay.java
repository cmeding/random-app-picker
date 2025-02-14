import java.awt.Component;
import javax.swing.JScrollPane;
//?

public class AppDisplay extends JScrollPane{
    private AppDisplayList a_list = null;

    @Override
    public void setViewportView(Component view){
        super.setViewportView(view);
        a_list = (AppDisplayList) view;
    }
}
