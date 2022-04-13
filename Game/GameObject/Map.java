package Game.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Map extends Object {

    public Image image;
    public int Col = 13;
    public int Row = 10;

    public Map()
    {
        image = new Image(getClass().getResourceAsStream("/Game/Asset/Map/lanepanel.png"));
    }
    
    public Map(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);

        image = new Image(getClass().getResourceAsStream("/Game/Asset/Map/lanepanel.png"));
    }

    public void draw(GraphicsContext gp) 
    {
        gp.drawImage(image,getScreenX(),getScreenY());
    }
}