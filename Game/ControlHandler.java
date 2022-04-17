package Game;

import Game.GameObject.Block;
import Game.GameObject.Player;
import Game.GameObject.Block.blockTypes;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ControlHandler implements EventHandler<KeyEvent> {

    public Player player;
    public Level level;
    private boolean isMoving= false;
    private int moveX,moveY;
    private Block targetPos;

    @Override
    public void handle(KeyEvent event) {

        switch(event.getCode())
        {
            case UP:
                if(isMoving)
                {
                    return;
                }
                if(player.Pos.top != null)
                {
                    if(player.Pos.top.bType == blockTypes.NONE)
                    {
                        targetPos = player.Pos.top;
                        Move();
                    }
                }
                break;

            case DOWN:
                if(isMoving)
                {
                    return;
                }
                if(player.Pos.down != null)
                {
                    if(player.Pos.down.bType == blockTypes.NONE)
                    {
                        targetPos = player.Pos.down;
                        Move();
                    }
                }
                break;
                
            case LEFT:
                if(isMoving)
                {
                    return;
                }
                if(player.Pos.left != null)
                {
                    if(player.Pos.left.bType == blockTypes.NONE)
                    {
                        targetPos = player.Pos.left;
                        Move();
                    }
                }
                break;  

            case RIGHT:
            
                if(isMoving)
                {
                    return;
                }
                if(player.Pos.right != null)
                {
                    if(player.Pos.right.bType == blockTypes.NONE)
                    {
                        targetPos = player.Pos.right;
                        Move();
                    }
                }
                break;
            default:
                break;
        } 

        if(event.getCode() == KeyCode.SPACE)
        {
            player.playerPlantBoom();
        }
    }

    public void update()
    {
        if(isMoving)
        {
            player.setWorldX(player.getWorldX() + moveX);
            player.setWorldY(player.getWorldY() + moveY);
            if(player.getWorldX() == targetPos.getWorldX() && player.getWorldY() == targetPos.getWorldY())
            {
                player.Pos = targetPos;
                isMoving = false;
            }
        }
        //System.out.println(player.getWorldX()+ "/" + player.getWorldY());
    }

    public void Move()
    {
        int dx = targetPos.getWorldX() - player.Pos.getWorldX();
        int dy = targetPos.getWorldY() - player.Pos.getWorldY();
        moveX = dx / player.speed;
        moveY = dy / player.speed;
        isMoving = true;  
    }
}
