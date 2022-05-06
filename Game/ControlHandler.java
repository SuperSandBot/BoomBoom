package Game;

import Game.GameObject.Item;
import Game.GameObject.Player;
import Game.Sound.GameSound;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ControlHandler{

    public boolean gamepause = false;
    public boolean control = true;
    public Player player1;
    public Player player2;
    public GameHandler gameHandler;
    public Level level;
    public Scene scene;
    boolean ismovingup1 = false;
    boolean ismovingdown1 = false;
    boolean ismovingleft1 = false;
    boolean ismovingright1 = false;

    boolean ismovingup2 = false;
    boolean ismovingdown2 = false;
    boolean ismovingleft2 = false;
    boolean ismovingright2 = false;

    GameSound gs = new GameSound();

    public ControlHandler(Scene scene)
    {
        this.scene = scene;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
        
                if(!control) return;
                if(event.getCode() == KeyCode.ESCAPE)
                {
                    gamepause = !gamepause;
                    gameHandler.pauseGame(gamepause);
                }
        
                ///player 1 controller
                Item item1 = level.itemCheck(player1.Pos);
                if(item1 != null)
                {
                    player1.playerPickItem(item1); 
                    gs.Audio(GameSound.ITEM);
                    level.RemoveItem(item1);
                }

                if(!player1.hideplayer) 
                {
                    if(event.getCode() == KeyCode.SPACE)
                    {                 
                        player1.playerPlantBoom();
                    }
                }
        
                if(event.getCode() == KeyCode.W)
                {
                    ismovingup1 = true;
                }
        
                if(event.getCode() == KeyCode.S)
                {
                    ismovingdown1 = true;
                }
        
                if(event.getCode() == KeyCode.A)
                {
                    ismovingleft1 = true;
                }
                
                if(event.getCode() == KeyCode.D)
                {
                    ismovingright1 = true;
                }
        
                ////player2 

                Item item2 = level.itemCheck(player2.Pos);
                if(item2 != null)
                {
                    player2.playerPickItem(item2); 
                    gs.Audio(GameSound.ITEM);
                    level.RemoveItem(item2);
                }

                if(!player2.hideplayer) 
                {
                    if(event.getCode() == KeyCode.ENTER)
                    {                 
                        player2.playerPlantBoom();
                    }
                }
        
                if(event.getCode() == KeyCode.UP)
                {
                    ismovingup2 = true;
                }
        
                if(event.getCode() == KeyCode.DOWN)
                {
                    ismovingdown2= true;
                }
        
                if(event.getCode() == KeyCode.LEFT)
                {
                    ismovingleft2 = true;
                }
                
                if(event.getCode() == KeyCode.RIGHT)
                {
                    ismovingright2 = true;
                }
            }
            
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(!control) return;
        
                ///player 1 controller
        
                if(event.getCode() == KeyCode.W)
                {
                    ismovingup1 = false;
                }
        
                if(event.getCode() == KeyCode.S)
                {
                    ismovingdown1 = false;
                }
        
                if(event.getCode() == KeyCode.A)
                {
                    ismovingleft1 = false;
                }
                
                if(event.getCode() == KeyCode.D)
                {
                    ismovingright1 = false;
                }
				
                if(event.getCode() == KeyCode.UP)
                {
                    
                    ismovingup2 = false;
                }
        
                if(event.getCode() == KeyCode.DOWN)
                {
                    
                    ismovingdown2 = false;
                }
        
                if(event.getCode() == KeyCode.LEFT)
                {
                    
                    ismovingleft2 = false;
                }
                
                if(event.getCode() == KeyCode.RIGHT)
                {
                   
                    ismovingright2 = false;
                }
			}
            
        });

    }

    public void update()
    {
        if(ismovingup1)
        { 
            if(player1.Pos.top != null)
            {
                if(level.playerTouchBlock(player1,player1.Pos.top))
                {

                    if(level.distance(player1, player1.Pos.top) < level.distance(player1, player1.Pos))
                    {
                        player1.Pos = player1.Pos.top;
                    }   
                    player1.setScreenY(player1.getScreenY() - player1.getSpeed());
                    player1.setWorldY(player1.getWorldY() - player1.getSpeed()); 
                }
            }
            else
            {
                if(level.playerTouchMap(player1))
                {
                    player1.setScreenY(player1.getScreenY() - player1.getSpeed());
                    player1.setWorldY(player1.getWorldY() - player1.getSpeed()); 
                }
            }
            player1.facing = 0;
        }
        else if(ismovingdown1)
        {
            if(player1.Pos.down != null)
            {
                if(level.playerTouchBlock(player1,player1.Pos.down))
                {
                   
                    if(level.distance(player1, player1.Pos.down) + 20 < level.distance(player1, player1.Pos))
                    {
                        player1.Pos = player1.Pos.down;
                    }    
                    player1.setScreenY(player1.getScreenY() + player1.getSpeed());
                    player1.setWorldY(player1.getWorldY() + player1.getSpeed()); 
                }
            }
            else
            {
                if(level.playerTouchMap(player1))
                {
                    player1.setScreenY(player1.getScreenY() + player1.getSpeed());
                    player1.setWorldY(player1.getWorldY() + player1.getSpeed()); 
                }
            }
            player1.facing = 1;
        }
        else if(ismovingleft1)
        {
            if(player1.Pos.left != null)
            {
                if(level.playerTouchBlock(player1,player1.Pos.left))
                {
                
                    if(level.distance(player1, player1.Pos.left) < level.distance(player1, player1.Pos))
                    {
                        player1.Pos = player1.Pos.left;
                    }    
                    player1.setScreenX(player1.getScreenX() - player1.getSpeed());
                    player1.setWorldX(player1.getWorldX() - player1.getSpeed()); 
                }
            }
            else
            {
                if(level.playerTouchMap(player1))
                {
                    player1.setScreenX(player1.getScreenX() - player1.getSpeed());
                    player1.setWorldX(player1.getWorldX() - player1.getSpeed()); 
                }
            }
            player1.facing = 2;
        }
        else if(ismovingright1)
        {
            if(player1.Pos.right != null)
            {
                if(level.playerTouchBlock(player1,player1.Pos.right))
                {
                
                    if(level.distance(player1, player1.Pos.right) < level.distance(player1, player1.Pos))
                    {
                        player1.Pos = player1.Pos.right;
                    }   
                    player1.setScreenX(player1.getScreenX() + player1.getSpeed());
                    player1.setWorldX(player1.getWorldX() + player1.getSpeed()); 
                }
            }
            else
            {
                if(level.playerTouchMap(player1))
                {
                    player1.setScreenX(player1.getScreenX() + player1.getSpeed());
                    player1.setWorldX(player1.getWorldX() + player1.getSpeed()); 
                }
            }
            player1.facing = 3;
        }
        


        if(ismovingup2)
        {
            if(player2.Pos.top != null)
            {
                if(level.playerTouchBlock(player2,player2.Pos.top))
                {

                    if(level.distance(player2, player2.Pos.top) < level.distance(player2, player2.Pos))
                    {
                        player2.Pos = player2.Pos.top;
                    }   
                    player2.setScreenY(player2.getScreenY() - player2.getSpeed());
                    player2.setWorldY(player2.getWorldY() - player2.getSpeed());
                }
            }
            else
            {
                if(level.playerTouchMap(player2))
                {
                    player2.setScreenY(player2.getScreenY() - player2.getSpeed());
                    player2.setWorldY(player2.getWorldY() - player2.getSpeed());
                }
            }     
            player2.facing = 0;
        }
        else if(ismovingdown2)
        {
            if(player2.Pos.down != null)
            {
                if(level.playerTouchBlock(player2,player2.Pos.down))
                {
                   
                    if(level.distance(player2, player2.Pos.down) + 20 < level.distance(player2, player2.Pos))
                    {
                        player2.Pos = player2.Pos.down;
                    }    
                    player2.setScreenY(player2.getScreenY() + player2.getSpeed());
                    player2.setWorldY(player2.getWorldY() + player2.getSpeed());  
                }
            }
            else
            {
                if(level.playerTouchMap(player2))
                {
                    player2.setScreenY(player2.getScreenY() + player2.getSpeed());
                    player2.setWorldY(player2.getWorldY() + player2.getSpeed());  
                }
            }
            player2.facing = 1;
        }
        else if(ismovingleft2)
        {
            if(player2.Pos.left != null)
            {
                if(level.playerTouchBlock(player2,player2.Pos.left))
                {
                
                    if(level.distance(player2, player2.Pos.left) < level.distance(player2, player2.Pos))
                    {
                        player2.Pos = player2.Pos.left;
                    }    
                    player2.setScreenX(player2.getScreenX() - player2.getSpeed());
                    player2.setWorldX(player2.getWorldX() - player2.getSpeed()); 
                }
            }
            else
            {
                if(level.playerTouchMap(player2))
                {
                    player2.setScreenX(player2.getScreenX() - player2.getSpeed());
                    player2.setWorldX(player2.getWorldX() - player2.getSpeed()); 
                }
            }
            player2.facing = 2;
        }
        else if(ismovingright2)
        {
            if(player2.Pos.right != null)
            {
                if(level.playerTouchBlock(player2,player2.Pos.right))
                {
                
                    if(level.distance(player2, player2.Pos.right) < level.distance(player2, player2.Pos))
                    {
                        player2.Pos = player2.Pos.right;
                    }   
                    player2.setScreenX(player2.getScreenX() + player2.getSpeed());
                    player2.setWorldX(player2.getWorldX() + player2.getSpeed()); 
                }
            }
            else
            {
                if(level.playerTouchMap(player2))
                {
                    player2.setScreenX(player2.getScreenX() + player2.getSpeed());
                    player2.setWorldX(player2.getWorldX() + player2.getSpeed()); 
                }
            }
            player2.facing = 3;
        }
    }
}
