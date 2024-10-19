import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        //입력 스트림 생성
        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        //클라이언트 소켓 생성
        DatagramSocket clientSocket = new DatagramSocket();

        //DNS를 통해 hostname을 IP 주소로 변환
        InetAddress ipAddress = InetAddress.getByName("hostname");

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();

        //데이터그램 생성(데이터, 데이터 길이, ip 주소, 포트 번호)
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, ipAddress, 9876);

        //서버로 데이터그램 송신
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);

        //서버로부터 데이터그램 수신
        clientSocket.receive(receivePacket);

        String modifiedSentence =
                new String(receivePacket.getData());

        System.out.println("FROM SERVER:" + modifiedSentence);

        clientSocket.close();
    }
}
