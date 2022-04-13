package Game.GameObject;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class Player extends Object
{
    ArrayList<Image> playerwalk;

    public Player(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);
    }  
}
