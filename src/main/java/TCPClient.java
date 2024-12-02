import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        String name; //서버로 전송할 이름
        String serverResponse; // 서버로부터 받은 응답

        //사용자 이름 입력 받기 위한 입력 스트림 생성(UTF-8 인코딩 사용)
        try (BufferedReader inFromUser =
                     new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            // localhost의 51000번 포트로 서버에 연결
            try (Socket clientSocket = new Socket("localhost", 51000);
                 //서버로 데이터 보내기 위한 출력 스트림생성
                 // autoFlush를 true로 설정하여 println() 호출시 자동으로 버퍼를 비움
                 PrintWriter outToServer =
                         new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true);

                 //서버로부터 데이터 받기 위한 스트림 생성
                 BufferedReader inFromServer =
                         new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8))) {

                //이름 입력 받기
                System.out.print("이름을 입력하세요 : ");
                name = inFromUser.readLine();

                // 입력받은 이름을 서버로 전송
                outToServer.println(name);

                //서버로부터 응답 수신 및 출력
                serverResponse = inFromServer.readLine();
                System.out.println("서버로부터 수신한 학번 : " + serverResponse);

            } catch (IOException e) {
                System.err.println("서버 연결 오류: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("입력 오류 :" + e.getMessage());
        }
    }
}
