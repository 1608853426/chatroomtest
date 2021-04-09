package extrasClients;

import client.Client;
import client.ClientThread;
import server.CrazyitProtocol;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client_test{
    private static final int SERVE_PORT = 30000;
    private Socket socket;
    private PrintStream printStream;
    private BufferedReader brServer;
    private BufferedReader keyIN;

    public static void main(String[] args) {
        Client_test client = new Client_test();
        client.init();//初始化
        client.readAndSend();
    }

    public void init() {
        try {
            //初始化键盘输入流
            keyIN = new BufferedReader(new InputStreamReader(System.in));
            //连接到服务器端
            socket = new Socket("127.0.0.1",SERVE_PORT);
            //获得该socket的输入输出流
            printStream = new PrintStream(socket.getOutputStream());
            brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String tip = " ";
            //循环弹出对话框要求用户输入用户名
            while (true){
                String usename = JOptionPane.showInputDialog(tip + "请输入用户名");
                //用户输入的用户名前后增加协议字符串后发送
                printStream.println(CrazyitProtocol.USER_ROUND + usename + CrazyitProtocol.USER_ROUND);


                //读取服务器响应
                String result = brServer.readLine();
                //如果用户名重复，则开始下一次循环
                if (result.equals(CrazyitProtocol.NAME_REP)){
                    tip = "用户名重复！ ";
                    continue;
                }
                //如果服务器端返回登录成功，则结束循环
                if (result.equals(CrazyitProtocol.LOGIN_SUCCESS)){
                    break;
                }
            }
        } //捕获到异常，关闭网络资源，并退出该程序
        catch (UnknownHostException ex){
            System.out.println("找不到远程服务器，请确定服务器已经启动");
            closeRs();
            System.exit(1);
        }
        catch(IOException ex){
            System.out.println("网络异常，请重新登录");
            closeRs();
            System.exit(1);
        }
        // 以该Socket对应的输入流启动ClientThread线程
        new ClientThread(brServer).start();
    }



    private void closeRs() {
        // 关闭Socket、输入流、输出流的方法
        try {
            if (keyIN != null){
                keyIN.close();
            }
            if (brServer != null){
                brServer.close();
            }
            if (socket != null){
                socket.close();
            }
            if (printStream != null){
                printStream.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //定义一个读取键盘输出，并以网络发送的方法
    private void readAndSend() {
        try {

            //不断从键盘读入
            String line = null;
            while ((line = keyIN.readLine()) != null){
                //如果发送的信号中有冒号，并以//开头，则认为想发送私聊信息
                if (line.indexOf(":") > 0 && line.startsWith("//")){
                    line = line.substring(2);
                    printStream.println(CrazyitProtocol.PRIVATE_ROUND+line.split(":")[0]+CrazyitProtocol.SPLIT_SIGN+line.split(":")[1]+CrazyitProtocol.PRIVATE_ROUND);
                }else{
                    printStream.println(CrazyitProtocol.MSG_ROUND+line+CrazyitProtocol.MSG_ROUND);
                }
            }
        }catch (IOException e){
            System.out.println("网络异常，请重新登陆");
            closeRs();
            System.exit(1);
        }
    }
}
