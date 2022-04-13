package Game.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Item extends Object{

    public static enum itemTypes
    {
        GOLDCOIN(0),
        BOOMUPGRADE(1),
        BOOT(2),
        ENERGYDRINK(3),
        VILE(4);

        private int itemID;
        private Image itemkImage;
        private itemTypes(int itemID)
        {
            this.itemID = itemID;

            switch (itemID)
            {
                case 0:
                    itemkImage = new Image(getClass().getResourceAsStream("Asset/Item/goldcoin.png"));
                    break;
                case 1:
                    itemkImage = new Image(getClass().getResourceAsStream("Asset/Item/boomupgrade.png"));
                    break;
                case 2:
                    itemkImage = new Image(getClass().getResourceAsStream("Asset/Item/boot.png"));
                    break;
                case 3:
                    itemkImage = new Image(getClass().getResourceAsStream("Asset/Item/energydrink.png"));
                    break;
                case 4:
                    itemkImage = new Image(getClass().getResourceAsStream("Asset/Item/vile.png"));
                    break;
                default:
                    itemkImage = null;
            }
        }
        public int getID()
        {
            return this.itemID;
        }
        public Image getImage()
        {
            return this.itemkImage;
        }
    }

    Image image;
    Image itemShadow;

    public itemTypes iTypes;

    public Item()
    {
        super();
    }

    public Item(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);

    }

    public void draw(GraphicsContext gp)
    {
        if(image != null)
        {
            gp.drawImage(image, (getScreenX() - image.getWidth())/2, (getScreenY() - image.getHeight())/2 );
            gp.drawImage(itemShadow, (getScreenX() - image.getWidth())/2, (getScreenY() - image.getHeight())/2);
        }
       
    }

    public void update(GraphicsContext gp)
    {
        
    }
    
}
