import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by ryu on 17. 5. 21.
 */

/*
JAVA NIO
1. 필요성
    - 소켓에서 accept()와 read()를 할 때 쓰레드가 block 이 됨
    - 그렇기 때문에 여러 개의 클라이언트를 동시에 처리하기 위해서는 멀티 프로세스, 멀티 쓰레드 등을 사용해야 함
    - 하지만 자바에서는 많은 쓰레드를 돌리게 되면 과부하가 걸리게 됨
    - 이 쓰레드들을 관리할 수 있다고 해도 context-switching 등의 문제 때문에 오버헤드가 발생
2. 개념
- Multiplexing
- 쓰레드를 적게 사용하면서 실제로 멀티 쓰레드를 사용하는 것처럼 보이게 할 수 있음
- 레스토랑에서의 웨이터에 비유할 수 있음: 모든 테이블마다 웨이터를 할당할 수는 없음. 대신 한 명의 웨이터가 여러 개의 테이블을 맡음(non-blocking)
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
