package Game;

import Game.GameObject.Player;
import javafx.scene.Scene;

public class ControlHandler {

    public Player player;


    public ControlHandler(Scene scene)
    {
        scene.setOnKeyPressed(event ->
        {
            switch(event.getCode())
            {
                case UP:
                    break;

                case DOWN:
                    break; 
                    
                case LEFT:
                    break;

                case RIGHT:
                    break;

                default:
                    break;
            }
        });
    }
}
