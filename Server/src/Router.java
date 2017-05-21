import com.ncookie.routeremulator.APManager;
import com.ncookie.routeremulator.DHCPServer;
import com.ncookie.simulator.DeviceManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by ryu on 17. 5. 21.
 */
public class Router {

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            // 서버소켓을 생성 및 설정
            serverSocket = new ServerSocket(8000);
            System.out.println(getTime() + " 서버가 준비되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 'while' statement cannot complete without throwing an exception
        // 이 경고는 무시해도 됨: false positive
        while (true) {
            try {
                System.out.println(getTime() + " 연결요청을 기다립니다.");
                // 서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 계속 기다린다.
                // 클라이언트의 연결요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.

                socket = serverSocket.accept();
                System.out.println(getTime() + socket.getInetAddress() + " 로부터 연결요청이 들어왔습니다.");

                ServerReceiver thread = new ServerReceiver(socket);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }
}
