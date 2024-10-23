import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        //9876 포트에서 데이터그램 소켓 생성
        DatagramSocket serverSocket = new DatagramSocket(9876);


        //수신 및 송신용 바이트 배열 버퍼 생성
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while(true){
            //수신된 데이터그램을 저장할 공간 생성
            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);

            //데이터그램 수신
            serverSocket.receive(receivePacket);

            //수신된 데이터를 문자열로 변환
            String sentence = new String(receivePacket.getData());

            //송신자의 IP 주소와 포트번호 가져오기
            InetAddress ipAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            //수신된 문자열을 대문자로 변환
            String capitalizedSentence = sentence.toUpperCase();

            //변환된 문자열을 바이트 배열로 변환
            sendData = capitalizedSentence.getBytes();

            //클라이언트에게 보낼 데이터그램 생성(
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, ipAddress, port);

            //소켓을 통해 데이터그램 전송
            serverSocket.send(sendPacket);
        }// while 루프 종료, 다음 데이터그램을 기다리기 위해 루프로 돌아감
    }
}
