package application.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Block extends Object {

    public static enum blockTypes
    {
        NONE,
        DIRTBLOCK00,
        DIRTBLOCK01,
        GRASSBLOCK,
        STONEBLOCK;
    }

    Image blockImage;

    public blockTypes bTypes;
    public Block top, down, left, right;
    public int width,height;

    public Block()
    {
        super();
        width = 64;
        height = 64;
    }

    public Block(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);
        width = 64;
        height = 64;
    }

    public void setBType(blockTypes bTypes)
    {
        this.bTypes = bTypes;
        blockImage = ImageManeger.getBlockImage(bTypes);
    }

    public void draw(GraphicsContext gp)
    {
        if(bTypes != blockTypes.NONE)
        {
            gp.drawImage(blockImage, getScreenX(), getScreenY()- 32 );
        }
       
    }
}
