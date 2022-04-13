package SeverHandler;
import java.net.InetAddress;

import GameMain.Player;

public class PlayerMP extends Player{

    public InetAddress ip;
    public int port;

    public PlayerMP(InetAddress ip,int port)
    {
        super();
        this.ip =ip;
        this.port = port;
    }
}
