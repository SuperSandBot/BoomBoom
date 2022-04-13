package Game.GameObject;

import javafx.scene.image.Image;

public class Map extends Object {

    public Image image;
    public int Col = 13;
    public int Row = 10;

    public Map()
    {

    }
    
    public Map(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);
        //TODO Auto-generated constructor stub

        image = new Image(getClass().getResourceAsStream("Asset/lanepanel.png"));
    }
}