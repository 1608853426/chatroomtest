import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LearningTest {
    @Test
    public void client() throws IOException {
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(3);
        list1.add(4);
        Mail mail = new Mail("sunamnxin",list);
        Mail mail1 = new Mail("jbless",list1);
        out.writeObject(mail);
        out.writeObject(mail1);
        out. close();
        socket.close();


        /*        FileInputStream fis = new FileInputStream(new File("C:\\Users\\sunam\\Desktop\\chatroomtest\\src\\test\\java\\Test.txt"));

        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        fis.close();
        outputStream.close();
        socket.close();*/
    }

    @Test
    public void server() throws IOException {
        ServerSocket ss = new ServerSocket(9090);
        Socket socket = ss.accept();
        ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
        try {
            Mail mail = (Mail) oi.readObject();
            System.out.println(mail.getAddress());
            Mail mail1 = (Mail) oi.readObject();
            System.out.println(mail1.getAddress());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        socket.close();
        ss.close();


    }

}
class Mail {
    private String address;
    private ArrayList<Integer> list;

    public Mail(String address, ArrayList<Integer> list) {
        this.address = address;
        this.list = list;
    }

    public Mail(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }
}