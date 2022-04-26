package Game.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Item extends Object{

    public static enum itemTypes
    {
        GOLDCOIN,
        BOOMUPGRADE,
        BOOT,
        VILE;
    }

    public Image itemImage;
    public Image itemShadow;

    public Block pos;
    public itemTypes iTypes;

    public Item()
    {
        super();
    }

    public Item(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);

    }

    public void setBType(itemTypes iTypes)
    {
        this.iTypes = iTypes;

        switch (iTypes)
            {
                case GOLDCOIN:
                    itemImage = new Image(getClass().getResourceAsStream("/Game/Asset/Item/goldcoin.png"));
                    break;
                case BOOMUPGRADE:
                    itemImage = new Image(getClass().getResourceAsStream("/Game/Asset/Item/boomupgrade.png"));
                    break;
                case BOOT:
                    itemImage = new Image(getClass().getResourceAsStream("/Game/Asset/Item/boot.png"));
                    break;
                case VILE:
                    itemImage = new Image(getClass().getResourceAsStream("/Game/Asset/Item/vile.png"));
                    break;
                default:   
                    itemImage = null;
                    break; 
            }
    }

    public void draw(GraphicsContext gp)
    {
        if(itemImage != null)
        {
            gp.drawImage(itemImage, getScreenX(), getScreenY());
            gp.drawImage(itemShadow, getScreenX(), getScreenY());
        }
       
    }

    public void update()
    {
        if(pos != null)
        {
            setScreenX(pos.getScreenX());
            setScreenY(pos.getScreenY());
        } 
    }   
}
