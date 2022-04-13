package Game.SeverHandler;

public abstract class Packet {
    public static enum packetTypes
    {
        INVALID(-1), LOGIN(00), DISCONNECT(01);

        private int packetID;
        private packetTypes(int packetID)
        {
            this.packetID = packetID;
        }
        public int getID()
        {
            return this.packetID;
        }
    }

    public byte packetID;

    public Packet(int packetID)
    {
        this. packetID = (byte) packetID;
    }

    public abstract void writeData(client c);
    public abstract void writeData(sever s);

    public String readData(byte[] data)
    {
        String s = new String(data).trim();
        return s.substring(2);
    }

    public abstract byte[] getData();

    public static packetTypes checkPacket(int id)
    {
        for (packetTypes types : packetTypes.values()) {
            if(types.getID() == id)
            {
                return types;
            }
        }
        return packetTypes.INVALID;
    }
}
