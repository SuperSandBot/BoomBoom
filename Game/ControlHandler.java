package Game;

import Game.GameObject.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class ControlHandler implements EventHandler<KeyEvent> {

    public Player player;

    @Override
    public void handle(KeyEvent event) {

        switch(event.getCode())
        {
            case UP:
                player.setWorldX(player.getWorldX());
                player.setWorldY(player.getWorldY() - player.speed);
                break;

            case DOWN:
                player.setWorldX(player.getWorldX());
                player.setWorldY(player.getWorldY() + player.speed);
                break; 
                
            case LEFT:
                player.setWorldX(player.getWorldX() - player.speed);
                player.setWorldY(player.getWorldY());
                break;

            case RIGHT:
                player.setWorldX(player.getWorldX() + player.speed);
                player.setWorldY(player.getWorldY());
                break;

            default:
                break;
        } 
    }
}
