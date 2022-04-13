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

    public void setBType(int blockID)
    {
        switch(blockID)
        {
            case 0:
                this.bType = blockTypes.NONE;
                break;
            case 1:
                this.bType = blockTypes.DIRTBLOCK00;
                break;
            case 2:
                this.bType = blockTypes.DIRTBLOCK01;
                break;
            case 3:
                this.bType = blockTypes.GRASSBLOCK;
                break;
            case 4:
                this.bType = blockTypes.STONEBLOCK;
                break;  
        }

        switch (blockID)
            {
                case 1:
                    blockImage = new Image(getClass().getResourceAsStream("/Game/Asset/Block/DirtBlock00.png"));
                    break;
                case 2:
                    blockImage = new Image(getClass().getResourceAsStream("/Game/Asset/Block/DirtBlock01.png"));
                    break;
                case 3:
                    blockImage = new Image(getClass().getResourceAsStream("/Game/Asset/Block/grassblock.png"));
                    break;
                case 4:
                    blockImage = new Image(getClass().getResourceAsStream("/Game/Asset/Block/stone.png"));
                    break;
                default:
                    case 0:
                    blockImage = null;
                    break; 
            }
    }

    public void draw(GraphicsContext gp)
    {
        if(blockImage != null)
        {
            gp.drawImage(blockImage, getScreenY(), getScreenX()- 32 );
        }
       
    }

    public void update(GraphicsContext gp)
    {
        
    }
    
}
