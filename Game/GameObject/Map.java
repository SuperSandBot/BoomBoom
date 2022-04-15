package Game.GameObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Map extends Object {

    public int[][] blockMap;
    public Image image;
    public int Width, Hight;
    public int Row = 8;
    public int Col = 6;

    public Map()
    {
        image = new Image(getClass().getResourceAsStream("/Game/Asset/Map/battleground02.png"));
        LoadMap("/Game/Asset/Map/battleground02.txt");
        this.Width = (int) image.getWidth();
        this.Hight = (int) image.getHeight();

    }
    
    public Map(int worldX, int worldY, int screenX, int screenY) {
        super(worldX, worldY, screenX, screenY);

        image = new Image(getClass().getResourceAsStream("/Game/Asset/Map/battleground02.png"));
    }

    private void LoadMap(String file)
    {
        blockMap = new int[Row][Col];

        try {
            InputStream in = getClass().getResourceAsStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
           

            for(int i = 0; i < Row; i++)
            {
                String line = br.readLine();
                String nums[] = line.split(" ");

                for(int j = 0; j < Col; j++)
                {
                    int num = Integer.parseInt(nums[j]);
                    blockMap[i][j] = num;

                }
            }
            
        } catch (NullPointerException e) {
            return;
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gp) 
    {
        gp.drawImage(image,getScreenX(),getScreenY());
    }
}