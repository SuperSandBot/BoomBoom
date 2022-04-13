package Game.SeverHandler;

import java.net.InetAddress;

import Game.GameObject.Player;

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
