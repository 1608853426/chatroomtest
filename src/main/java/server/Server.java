package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int SERVER_PORT = 30000;
    //使用UserStreamMap对象来保存每个客户名字和对应输出流之间的对应关系
    public static UserStreamMap<String, PrintStream> clients = new UserStreamMap<>();

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
    }

    private void init() {
        //建立监听的ServerSocket
        try (ServerSocket ss = new ServerSocket(SERVER_PORT)) {
            //采用死循环来不断地接收来自客户端的请求
            while (true){
                Socket socket = ss.accept();
                new ServerThread(socket).start();
            }
        }catch (IOException e){
            System.out.println("服务器启动失败，是否端口"+SERVER_PORT+"已被占用");
        }


    }
}
