import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class NamingWindow extends JDialog{
    private String INPUT = null;

    public NamingWindow(){
        super();
        this.setTitle("NAMING");
        this.setSize(800, 400);
        this.setBackground(Color.BLACK);
        this.getContentPane().setBackground(new Color(30,30,30));
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setModal(true);
        
        this.setLayout(new GridLayout(2,1));
        JPanel lowerHalf = new JPanel();
        lowerHalf.setLayout(new GridLayout(1,2));

        JTextField input_field = new JTextField("New Collection");
        
        JButton confirm = new JButton("CONFIRM");
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                INPUT = input_field.getText();
                close();
            }
        });

        

        JButton cancel = new JButton("CANCEL");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

        this.add(input_field);
        lowerHalf.add(confirm);
        lowerHalf.add(cancel);
        this.add(lowerHalf);

        /*
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    INPUT = input_field.getText();
                    close();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    close();
                }
            }
        });
        */

        this.getRootPane().registerKeyboardAction(
            e -> cancel.doClick(), // Simulate Cancel Button Click
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        this.getRootPane().registerKeyboardAction(
            e -> confirm.doClick(), // Simulate Cancel Button Click
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }


    public void close(){
        this.setVisible(false);
    }

    public String getInput(){
        return INPUT;
    }
}
