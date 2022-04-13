package SeverHandler;

public class Packet00Login extends Packet {

    private String userName;
    
    public Packet00Login(byte[] packetID) {
        super(00);

        this.userName = readData(packetID);
    }

    public Packet00Login(String username) {
        super(00);
        this.userName = username;
    }

    @Override
    public void writeData(client c) {
        c.sendData(getData());
    }

    @Override
    public void writeData(sever s) {
        s.sendDataToAll(getData());
    }

    @Override
    public byte[] getData() {
        return ("00" + this.userName).getBytes();
    }
    
}
