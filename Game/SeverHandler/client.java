package Game.SeverHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class client implements Runnable{

    private InetAddress host;
    private int port;

    private DatagramSocket socket;

    private boolean running = false;
    
    public client(String host,int port)
    {
        this.port = port;
        try {
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    // kết nối
    public void connect()
    {
        try{
            
            socket = new DatagramSocket();

            new Thread(this).start();

        }catch(ConnectException e)
        {
            System.out.println("Unable to connect to sever");
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    // đóng kết nối
    public void close()
    {
        running = false;
        socket.close();
    }

    public void sendData(byte[] data)
    {
        DatagramPacket packet = new DatagramPacket(data,data.length,host,port);
        try{
            socket.send(packet);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
       running = true;

       while(running)
       {
           byte[] data = new byte[1024];
           DatagramPacket packet = new DatagramPacket(data, data.length);

           try{
               socket.receive(packet);
           }catch(IOException e)
           {
               e.printStackTrace();
           }
       }
        
    }
    
}
