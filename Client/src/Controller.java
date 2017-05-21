import com.ncookie.routercontroller.RouterController;
import com.ncookie.sock.ControlSock;

import javax.swing.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by ryu on 17. 5. 21.
 */
public class Controller {

    public static void main(String args[]) {

        Socket socket;
        ControlSock controlSock;

        final String SERVER_IP = "127.0.0.1";

        try {
            System.out.println("서버에 연결중입니다. 서버 IP : " + SERVER_IP);

            // 소켓을 생성하여 연결을 요청한다.
            socket = new Socket(SERVER_IP, 8000);
            controlSock = new ControlSock(socket);

            JFrame frame = new JFrame("RouterController");
            frame.setContentPane(new RouterController(controlSock).mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(1000, 750);
            frame.setVisible(true);

        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



//        try {
//            while (true) {
//                // 소켓의 입력스트림을 얻는다.
//                InputStream in = socket.getInputStream();
//                dis = new DataInputStream(in);  // 기본형 단위로 처리하는 보조스트림
//
//                // 소켓으로 부터 받은 데이터를 출력한다.
//                System.out.println("서버로부터 받은 메세지 : " + dis.readUTF());
//                System.out.println("연결을 종료합니다.");
//            }
//        } catch (IOException ie) {
//            ie.printStackTrace();
//        }

        // 스트림과 소켓을 닫는다.


    }

}
