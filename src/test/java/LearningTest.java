import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class LearningTest {
 @Test
 public void client() throws IOException {
  Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9090);
  OutputStream outputStream = socket.getOutputStream();

 }

 @Test
 public void server(){

 }
}
