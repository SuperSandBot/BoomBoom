package Game;

public class BoxCollider{

    public boolean Colision = false;
    public int leftX,rightX,leftY,rightY;

    public BoxCollider(int worldX, int worldY, int Width, int Height)
    {
        leftX = worldX;
        rightX = Width;
        leftY = worldY;
        rightY = Height;

        CollisionHandler.collidersList.add(this);
    }
}
