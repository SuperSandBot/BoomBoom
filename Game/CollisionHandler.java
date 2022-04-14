package Game;

import java.util.ArrayList;

import Game.GameObject.Block;
import Game.GameObject.Player;

public class CollisionHandler{
    
    public static ArrayList<BoxCollider> collidersList = new ArrayList<BoxCollider>();

    public void update()
    {
        for(int i = 0; i < collidersList.size(); i++ )
        {
            if(collidersList.get(i).Colision) continue;

            for(int j = i + 1 ; j < collidersList.size(); j++)
            {
                if(collidersList.get(j).Colision) continue;

                if(ColisionCheck(collidersList.get(i), collidersList.get(j)))
                {
                    System.out.println("COLLISION !");
                }
            }
        }
    }

    public boolean ColisionCheck(BoxCollider b1, BoxCollider b2)
    {
        return b1.leftX < b2.leftX + b2.rightX && 
                b1.leftX + b1.rightX > b2.leftX &&
                b1.leftY < b2.leftY + b2.rightY &&
                b1.leftY + b1.rightY > b2.leftY;
    }
}
