import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        String studentName; // 학생 이름
        String response; // 클라이언트에게 보낼 응답
        String studentId = ""; // 학번
        String alert = "*올바른 이름을 입력해주세요*"; //올바르지 않은 이름 입력시 경고문

        // 51000번 포트로 서버 소켓 생성 (포트 51000에 바인딩)
        try (ServerSocket welcomeSocket = new ServerSocket(51000)) {
            System.out.println("TCP 서버가 시작되었습니다. 클라이언트를 기다립니다 ...");
            //서버 계속 실행되면서 클라이언트의 connection 요청 오기전까지 대기
            while (true) {
                //클라이언트의 연결 요청 수락, 통신용 소켓 생성
                try (Socket connectionSocket = welcomeSocket.accept();
                     //클라이언트로부터 데이터를 읽기 위한 입력 스트림 생성 (UTF-8 인코딩 사용)
                     BufferedReader inFromClient =
                             new BufferedReader(new InputStreamReader(connectionSocket.getInputStream(), StandardCharsets.UTF_8));

                     //클라이언트에게 데이터를 보내기 위한 출력 스트림 생성
                     //autoFlush를 true로 설정하여 println() 호출시 자동으로 버퍼 비우기
                     PrintWriter outToClient =
                             new PrintWriter(new OutputStreamWriter(connectionSocket.getOutputStream(), StandardCharsets.UTF_8), true)) {

                    //클라이언트가 보낸 이름 읽기
                    studentName = inFromClient.readLine();

                    System.out.println("클라이언트로부터 수신한 이름 : " + studentName);


                    // 입력받은 이름이 올바르면 학번을, 그렇지 않으면 경고 메시지를 response로 설정
                    if ("김태현".equals(studentName)) {
                        response = studentId;
                    } else {
                        response = alert;
                    }

                    //설정된 응답을 클라이언트에게 전송
                    outToClient.println(response);
                } catch (IOException e) {
                    System.err.println("클라이언트 연결 오류 : " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("서버 소켓 생성 오류 : " + e.getMessage());
        }
    }
}
