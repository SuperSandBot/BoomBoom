package Game.GameObject;

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

    public blockTypes bType;
    public Block top, down, left, right;

    public Block()
    {
        super();
    }

    public Block(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);
    }

    public void setBType(blockTypes bType)
    {
        this.bType = bType;

        switch (bType)
            {
                case DIRTBLOCK00:
                    blockImage = new Image(getClass().getResourceAsStream("/Game/Asset/Block/DirtBlock00.png"));
                    break;
                case DIRTBLOCK01:
                    blockImage = new Image(getClass().getResourceAsStream("/Game/Asset/Block/DirtBlock01.png"));
                    break;
                case GRASSBLOCK:
                    blockImage = new Image(getClass().getResourceAsStream("/Game/Asset/Block/grassblock.png"));
                    break;
                case STONEBLOCK:
                    blockImage = new Image(getClass().getResourceAsStream("/Game/Asset/Block/stone.png"));
                    break;
                default:
                    case NONE:
                    blockImage = null;
                    break; 
            }
    }

    public void draw(GraphicsContext gp)
    {
        if(bType != blockTypes.NONE)
        {
            gp.drawImage(blockImage, getScreenX(), getScreenY()- 32 );
        }
       
    }

    public void update()
    {

    }
    
}
