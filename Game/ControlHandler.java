package Game;

import Game.GameObject.Block;
import Game.GameObject.Item;
import Game.GameObject.Player;
import Game.GameObject.Block.blockTypes;
import Game.Sound.GameSound;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ControlHandler implements EventHandler<KeyEvent> {

    public Player player1;
    public Player player2;
    public Level level;
    private boolean isMoving1= false;
    private boolean isMoving2= false;
    private int moveX1,moveY1;
    private int moveX2,moveY2;
    private Block targetPos1;
    private Block targetPos2;
    GameSound gs = new GameSound();

    @Override
    public void handle(KeyEvent event) {

       
        switch(event.getCode())
        {
            case W:
                if(isMoving1 || player1.hideplayer)
                {
                    return;
                }
                if(player1.Pos.top != null)
                {
                    if(player1.Pos.top.bType == blockTypes.NONE)
                    {
                        if(level.checkBoomPos(player1.Pos.top)) return;
                        targetPos1 = player1.Pos.top;
                        player1.facing = 0;
                        Move1();
                        
                    }
                }
                break;

            case S:
                if(isMoving1 || player1.hideplayer)
                {
                    return;
                }
                if(player1.Pos.down != null)
                {
                    if(player1.Pos.down.bType == blockTypes.NONE)
                    {
                        if(level.checkBoomPos(player1.Pos.down)) return;
                        targetPos1 = player1.Pos.down;
                        player1.facing = 1;
                        Move1();
                        
                    }
                }
                break;
                
            case A:
                if(isMoving1 || player1.hideplayer)
                {
                    return;
                }
                if(player1.Pos.left != null)
                {
                    if(player1.Pos.left.bType == blockTypes.NONE)
                    {
                        if(level.checkBoomPos(player1.Pos.left)) return;
                        targetPos1 = player1.Pos.left;
                        player1.facing = 2;
                        Move1();
                        
                    }
                }
                break;  

            case D:
            
                if(isMoving1 || player1.hideplayer)
                {
                    return;
                }
                if(player1.Pos.right != null)
                {
                    if(player1.Pos.right.bType == blockTypes.NONE)
                    {
                        if(level.checkBoomPos(player1.Pos.right)) return;
                        targetPos1 = player1.Pos.right;
                        player1.facing = 3;
                        Move1();
                        	
                        
                    }
                }
                break;
/////////////////////////////////////////////////////////////////////////////////////////////////
                case UP:
                if(isMoving2 || player2.hideplayer)
                {
                    return;
                }
                if(player2.Pos.top != null)
                {
                    if(player2.Pos.top.bType == blockTypes.NONE)
                    {
                        if(level.checkBoomPos(player2.Pos.top)) return;
                        targetPos2 = player2.Pos.top;
                        player2.facing = 0;
                        Move2();
                        
                    }
                }
                break;

            case DOWN:
                if(isMoving2 || player2.hideplayer)
                {
                    return;
                }
                if(player2.Pos.down != null)
                {
                    if(player2.Pos.down.bType == blockTypes.NONE)
                    {
                        if(level.checkBoomPos(player2.Pos.down)) return;
                        targetPos2 = player2.Pos.down;
                        player2.facing = 1;
                        Move2();
                        
                    }
                }
                break;
                
            case LEFT:
                if(isMoving2 || player2.hideplayer)
                {
                    return;
                }
                if(player2.Pos.left != null)
                {
                    if(player2.Pos.left.bType == blockTypes.NONE)
                    {
                        if(level.checkBoomPos(player2.Pos.left)) return;
                        targetPos2 = player2.Pos.left;
                        player2.facing = 2;
                        Move2();
                        
                    }
                }
                break;  

            case RIGHT:
            
                if(isMoving2 || player2.hideplayer)
                {
                    return;
                }
                if(player2.Pos.right != null)
                {
                    if(player2.Pos.right.bType == blockTypes.NONE)
                    {
                        if(level.checkBoomPos(player2.Pos.right)) return;
                        targetPos2 = player2.Pos.right;
                        player2.facing = 3;
                        Move2();
                        	
                        
                    }
                }
            default:
                break;
        } 

        if(event.getCode() == KeyCode.SPACE)
        {
            
            if(isMoving1 || player1.hideplayer) return;
            player1.playerPlantBoom();
        }

        if(event.getCode() == KeyCode.ENTER)
        {
            if(isMoving2 || player2.hideplayer) return;
            player2.playerPlantBoom();
        }
    }


    public void update()
    {
        if(isMoving1)
        {
            player1.setScreenX(player1.getScreenX() + moveX1);
            player1.setScreenY(player1.getScreenY() + moveY1);
            player1.setWorldX(player1.getWorldX() + moveX1);
            player1.setWorldY(player1.getWorldY() + moveY1);
            if(player1.getWorldX() == targetPos1.getWorldX() && player1.getWorldY() == targetPos1.getWorldY())
            {
                isMoving1 = false;      
                Item item = level.itemCheck(targetPos1);
                if(item != null)
                {
                    player1.playerPickItem(item); 
                    gs.Audio(GameSound.ITEM);
                    level.RemoveItem(item);
                }
            }
        }

        if(isMoving2)
        {
            player2.setScreenX(player2.getScreenX() + moveX2);
            player2.setScreenY(player2.getScreenY() + moveY2);
            player2.setWorldX(player2.getWorldX() + moveX2);
            player2.setWorldY(player2.getWorldY() + moveY2);
            if(player2.getWorldX() == targetPos2.getWorldX() && player2.getWorldY() == targetPos2.getWorldY())
            {
                isMoving2 = false;      
                Item item = level.itemCheck(targetPos2);
                if(item != null)
                {
                    player2.playerPickItem(item); 
                    gs.Audio(GameSound.ITEM);
                    level.RemoveItem(item);
                }
            }
        }
    }

    public void Move1()
    {
        gs.Audio(GameSound.FOOT);
        int dx = targetPos1.getWorldX() - player1.Pos.getWorldX();
        int dy = targetPos1.getWorldY() - player1.Pos.getWorldY();
        moveX1 = dx / player1.getSpeed();
        moveY1 = dy / player1.getSpeed();
        isMoving1 = true;  
        player1.Pos = targetPos1;
    }

    public void Move2()
    {
        gs.Audio(GameSound.FOOT);
        int dx = targetPos2.getWorldX() - player2.Pos.getWorldX();
        int dy = targetPos2.getWorldY() - player2.Pos.getWorldY();
        moveX2 = dx / player2.getSpeed();
        moveY2 = dy / player2.getSpeed();
        isMoving2 = true;  
        player2.Pos = targetPos2;
    }
}
