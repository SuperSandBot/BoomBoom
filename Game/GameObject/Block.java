package Game.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Block extends Object {

    public static enum blockTypes
    {
        DIRTBLOCK00(0),
        DIRTBLOCK01(1),
        GRASSBLOCK(2),
        STONEBLOCK(3);

        private int blockID;
        private Image blockImage;
        private blockTypes(int blockID)
        {
            this.blockID = blockID;

            switch (blockID)
            {
                case 0:
                    blockImage = new Image(getClass().getResourceAsStream("Asset/Block/DirtBlock00.png"));
                    break;
                case 1:
                    blockImage = new Image(getClass().getResourceAsStream("Asset/Block/DirtBlock01.png"));
                    break;
                case 2:
                    blockImage = new Image(getClass().getResourceAsStream("Asset/Block/grassblock.png"));
                    break;
                case 3:
                    blockImage = new Image(getClass().getResourceAsStream("Asset/Block/stone.png"));
                    break;
                default:
                    blockImage = null;
            }
        }
        public int getID()
        {
            return this.blockID;
        }
        public Image getImage()
        {
            return this.blockImage;
        }
    }

    Image image;

    public blockTypes bTypes;
    public Block top, down, left, right;

    public Block()
    {
        super();
    }

    public Block(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);

    }

    public void draw(GraphicsContext gp)
    {
        if(image != null)
        {
            gp.drawImage(image, (getScreenX() - image.getWidth())/2, (getScreenY() - image.getHeight() - 32)/2 );
        }
       
    }

    public void update(GraphicsContext gp)
    {
        
    }
    
}
