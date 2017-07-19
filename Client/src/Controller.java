import com.ncookie.routercontroller.RouterController;
import com.ncookie.routercontroller.IpSetter;
import com.ncookie.network.ControlSock;

import javax.swing.*;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by ryu on 17. 5. 21.
 */
public class Controller {

    public static void main(String args[]) {

        Socket socket;
        ControlSock controlSock;

        try {
//            final String SERVER_IP = "192.168.0.113";
            final String SERVER_IP = "127.0.0.1";
//            final String SERVER_IP = "192.168.0.111";


            System.out.println("서버에 연결중입니다. 서버 IP : " + SERVER_IP);

            // 소켓을 생성하여 연결을 요청한다.
            socket = new Socket(SERVER_IP, 8000);
            controlSock = new ControlSock(socket);

            System.out.println("서버 연결 성공");

            JFrame frame = new JFrame("RouterController");
            frame.setContentPane(new RouterController(controlSock).mainPanel);  // 여기서 블락됨
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(1000, 750);
            frame.setVisible(true);

        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
