import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class TextWindow extends JDialog{
    private final int sWIDTH = 600;
    private final int sHEIGHT = 300;
    public TextWindow(String upper_text, String lower_text) throws HeadlessException {
        super();
        this.setSize(sWIDTH, sHEIGHT);
        this.setResizable(false);
        this.setModal(true);
        this.setTitle("Text Window");
        this.setLayout(null);

        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosed(WindowEvent e) {
                TextWindow.this.dispose();
            }
        });
        
        JTextField upper = new JTextField();
        upper.setText(upper_text);

        upper.setHorizontalAlignment(SwingConstants.CENTER);
        upper.setEditable(false);
        upper.setBorder(null);
        upper.setOpaque(false);
        
        upper.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //----------------------------------------------

        JTextField lower = new JTextField();
        lower.setText(lower_text);

        lower.setHorizontalAlignment(SwingConstants.CENTER);
        lower.setEditable(false);
        lower.setBorder(null);
        lower.setOpaque(false);
        
        lower.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton confirm = new JButton("OK");
        confirm.setBounds(sWIDTH/3,sHEIGHT/6 + sHEIGHT/2 ,sWIDTH/3,sHEIGHT/6);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextWindow.this.setVisible(false);
                TextWindow.this.dispose();
            }
        });
        
        this.getRootPane().registerKeyboardAction(
            e -> confirm.doClick(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
            );

        JPanel field = new JPanel();
        field.setBounds(0,HEIGHT/6,sWIDTH,sHEIGHT/6);
        field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));

        field.add(upper);
        field.add(lower);
        
            
        //this.add(text);
        this.add(field);
        this.add(confirm);
        this.setVisible(true);
    }
}
