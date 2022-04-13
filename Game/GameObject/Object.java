package Game.GameObject;

public class Object {
    
    private int worldX,worldY;
    private int screenX, screenY;

    public Object()
    {

    }

    public Object(int worldX, int worldY, int screenX, int screenY){
        this.worldX = worldX;
        this.worldY = worldY;
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getWorldX()
    {
        return this.worldX;
    }

    public int getWorldY()
    {
        return this.worldY;
    }

    public void setWorldX(int x) {
        this.worldX = x;
    }

    public void setWorldY(int y) {
        this.worldY = y;
    }

}
