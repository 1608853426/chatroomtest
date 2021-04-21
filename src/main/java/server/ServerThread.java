package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket socket;
    BufferedReader br = null;
    PrintStream ps = null;
    //定义一个构造器，用于接收一个Socket来创建ServerThread线程
    public ServerThread(Socket socket) {
    this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //获取该Socket对应的输入流
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //获取该Socket对应的输出流
            ps = new PrintStream(socket.getOutputStream());
            String line = null;
            while ((line = br.readLine()) != null) {
                //如果读到的行以ProtocolCharacter.USER_ROUND开始，并以其结束
                //则可以确定读到的是用户登录的用户名
                if (line.startsWith(ProtocolCharacter.USER_ROUND) && line.endsWith(ProtocolCharacter.USER_ROUND)) {
                    //得到真实消息
                    String userName = getRealMsg(line);
                    //如果用户名重复
                    if (Server.clients.map.containsKey(userName)) {
                        System.out.println("重复");
                        ps.println(ProtocolCharacter.NAME_REP);
                    } else {
                        System.out.println("成功");
                        ps.println(ProtocolCharacter.LOGIN_SUCCESS);
                        Server.clients.put(userName, ps);
                        for (PrintStream clientPs : Server.clients.valueSet()){
                            clientPs.println("当前在线用户：");
                            for(Object key : Server.clients.map.keySet()){
                                String msg = key.toString();
                                clientPs.println(msg);
                            }
                            clientPs.println("-----------");
                        }
                    }
                }
                //如果读到的行以ProtocolCharacter.PRIVATE_ROUND开始，
                //则可以确定是私聊信息，私聊信息只向特定的输入流发送
                else if (line.startsWith(ProtocolCharacter.PRIVATE_ROUND) && line.endsWith(ProtocolCharacter.PRIVATE_ROUND)) {
                    //得到真实的消息
                    String userAndMsg = getRealMsg(line);
                    //以SPLIT_SIGN分割字符串，前半是私聊用户，后半是聊天信息
                    String user = userAndMsg.split(ProtocolCharacter.SPLIT_SIGN)[0];
                    String msg = userAndMsg.split(ProtocolCharacter.SPLIT_SIGN)[1];
                    //获取私聊用户对应的输出流，并发送私聊信息
                    try {
                        Server.clients.map.get(user).println(Server.clients.getKeyByValue(ps) + "私聊对你说：" + msg);
                    }catch (NullPointerException e){
                        ps.println("！！！当前用户不在线 ： " + user);
                        ps.println("发送失败！");
                    }

                }
                //公聊要向每一个Socket发送
                else {
                    //得到真实消息
                    String msg = getRealMsg(line);
                    //遍历clients中的每个输出流
                    for (PrintStream clientPs : Server.clients.valueSet()) {
                        clientPs.println(Server.clients.getKeyByValue(ps) + "说：" + msg);
                    }
                }
            }
        }
        //捕获到异常后，表明Socket对应的客户端已经出现了问题
        //所以程序将其对应的输出流从Map中删除
        catch (IOException e) {
            String name = Server.clients.getKeyByValue(ps);
            Server.clients.removeByValue(ps);
            System.out.println(Server.clients.map.size());
            System.out.println(name);
            for (PrintStream clientPs : Server.clients.valueSet()){
                clientPs.println(name + "下线了");
            }
            //关闭网络，IO资源
            try {
                if (br != null) {
                    br.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //将读到的内容去掉前后协议字符，恢复成真实数据
    private String getRealMsg(String line) {
        return line.substring(ProtocolCharacter.PROTOCOL_LEN,line.length()- ProtocolCharacter.PROTOCOL_LEN);
    }

}
