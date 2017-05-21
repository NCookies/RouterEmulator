import com.ncookie.routercontroller.RouterController;
import com.ncookie.routeremulator.Router;
import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        Router router = new Router();

        JFrame frame = new JFrame("RouterController");
        frame.setContentPane(new RouterController(router).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 750);
        frame.setVisible(true);
    }
}
