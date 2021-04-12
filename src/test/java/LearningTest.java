import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class LearningTest {
    @Test
    public void client() throws IOException {
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
        OutputStream outputStream = socket.getOutputStream();
        FileInputStream fis = new FileInputStream(new File("C:\\Users\\sunam\\Desktop\\chatroomtest\\src\\test\\java\\Test.txt"));

        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        fis.close();
        outputStream.close();
        socket.close();
    }

    @Test
    public void server() throws IOException {
        ServerSocket ss = new ServerSocket(9090);
        Socket socket = ss.accept();
        InputStream inputStream = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream("beau.txt");
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        inputStream.close();
        fos.close();
        socket.close();
        ss.close();
    }

}
