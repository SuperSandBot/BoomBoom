package SeverHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import SeverHandler.Packet.packetTypes;

public class sever implements Runnable{
    
    private DatagramSocket socket;
    private boolean running = false;
    private ArrayList<PlayerMP> Playerlist = new ArrayList<PlayerMP>();

    public sever(int port)
    {
        try{
            socket = new DatagramSocket(port);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void start()
    {
        new Thread(this).start();
    }

    public void close()
    {
        running = false;
        socket.close();
    }

    public void sendData(byte[] data, InetAddress ip, int port)
    {
        DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
        try{
            socket.send(packet);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendDataToAll(byte[] data) {
        for (PlayerMP p : Playerlist) 
        {
            sendData(data, p.ip, p.port);
        }
    }

    public void handlingPacket(byte[] data, InetAddress ip, int port)
    {
        String s = new String(data).trim();
        packetTypes type = Packet.checkPacket(Integer.parseInt(s));
        switch (type)
        {
            case LOGIN:
            case DISCONNECT:

            default:
            case INVALID:
        }
    }

    @Override
    public void run() {

        running = true;
        while(running)
        {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            handlingPacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    

}
