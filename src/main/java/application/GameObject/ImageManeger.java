package application.GameObject;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class ImageManeger {

    static Image goldcoin;
    static Image boomupgrade;
    static Image boot;
    static Image vile;
    static Image shadow;
    static ArrayList<Image> boomImage;

    static Image splashBodyVertical;
    static Image splashBodyHorizontal;
    static Image splashEndTop;
    static Image splashEndDown;
    static Image splashEndLeft;
    static Image splashEndRight;

    static Image dirtBlock00;
    static Image dirtBlock01;
    static Image grassBlock;
    static Image stoneBlock;

    public ImageManeger()
    {
        goldcoin = new Image(getClass().getResourceAsStream("/application/Asset/Item/goldcoin.png"));
        boomupgrade = new Image(getClass().getResourceAsStream("/application/Asset/Item/boomupgrade.png"));
        boot = new Image(getClass().getResourceAsStream("/application/Asset/Item/boot.png"));
        vile = new Image(getClass().getResourceAsStream("/application/Asset/Item/vile.png"));
        shadow = new Image(getClass().getResourceAsStream("/application/Asset/Item/shadow.png"));

        boomImage = new ArrayList<Image>();
        boomImage.add(new Image(getClass().getResourceAsStream("/application/Asset/Boom/boom00.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/application/Asset/Boom/boom01.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/application/Asset/Boom/boom02.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/application/Asset/Boom/boom03.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/application/Asset/Boom/boom04.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/application/Asset/Boom/boom05.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/application/Asset/Boom/boom06.png")));
        boomImage.add(new Image(getClass().getResourceAsStream("/application/Asset/Boom/boom07.png")));

        splashBodyVertical = new Image(getClass().getResourceAsStream("/application/Asset/Boom/splashBodyVertical.png"));
        splashBodyHorizontal = new Image(getClass().getResourceAsStream("/application/Asset/Boom/splashBodyHorizontal.png"));
        splashEndTop = new Image(getClass().getResourceAsStream("/application/Asset/Boom/splashEndTop.png"));
        splashEndDown = new Image(getClass().getResourceAsStream("/application/Asset/Boom/splashEndDown.png"));
        splashEndLeft = new Image(getClass().getResourceAsStream("/application/Asset/Boom/splashEndLeft.png"));
        splashEndRight = new Image(getClass().getResourceAsStream("/application/Asset/Boom/splashEndRight.png"));

        dirtBlock00 = new Image(getClass().getResourceAsStream("/application/Asset/Block/DirtBlock00.png"));
        dirtBlock01 = new Image(getClass().getResourceAsStream("/application/Asset/Block/DirtBlock01.png"));
        grassBlock = new Image(getClass().getResourceAsStream("/application/Asset/Block/grassblock.png"));
        stoneBlock = new Image(getClass().getResourceAsStream("/application/Asset/Block/stone.png"));
    }

    public static Image getItemImage(Item.itemTypes iTypes)
    {
        switch (iTypes)
            {
                case GOLDCOIN:
                    return goldcoin;
                case BOOMUPGRADE:
                   return boomupgrade;
                case BOOT:
                    return boot;
                case POTION:
                    return vile;
                default:   
                    return null;
            }
    }

    public static Image getBlockImage(Block.blockTypes bTypes)
    {
        switch (bTypes)
        {
            case DIRTBLOCK00:
                return dirtBlock00;
            case DIRTBLOCK01:
                return dirtBlock01;
            case GRASSBLOCK:
                return grassBlock;
            case STONEBLOCK:
                return stoneBlock;
            default:
                case NONE:
                return null; 
        }
    }

    public static Image getShadow()
    {
        return shadow;
    }

    public static ArrayList<Image> getBoomImage()
    {
        return boomImage;
    }

    public static Image getSplashEnd(String dir)
    {
        switch(dir)
            {
                case "top":

                    return splashEndTop;
                case "down":
                    return splashEndDown;
                case "left":
                    return splashEndLeft;
                case "right":  
                    return splashEndRight;
                default:
                    return null;
            }  
    }
    public static Image getSplashBodyVertical()
    {
        return splashBodyVertical;
    }
    public static Image getSplashBodyHorizontal()
    {
        return splashBodyHorizontal;
    }
}


