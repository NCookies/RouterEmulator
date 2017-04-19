import com.UI.RouterController;

import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello World");

        JFrame frame = new JFrame("RouterController");
        frame.setContentPane(new RouterController().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 750);
        frame.setVisible(true);
    }
}
