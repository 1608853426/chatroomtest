package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientThread extends Thread
{
    // 该客户端线程负责处理的输入流
    BufferedReader br=null;
    // 使用一个网络输入流来创建客户端线程
    public ClientThread(BufferedReader br)
    {
        this.br=br;
    }
    public void run()
    {
        try
        {
            String line=null;
            // 不断地输入流中读取数据，并将这些数据打印输出
            while((line=br.readLine())!=null)
            {
                System.out.println(line);
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        // 使用finally块来关闭该线程对应的输入流
        finally
        {
            try
            {
                if (br !=null)
                {
                    br.close();
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}

