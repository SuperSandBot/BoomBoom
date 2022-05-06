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
        this.itemImage = ImageManeger.getItemImage(iTypes);
        this.itemShadow = ImageManeger.getShadow();
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
