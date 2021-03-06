package application.GameObject;

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
    public int Row = 13;
    public int Col = 10;

    public Map()
    {
        image = new Image(getClass().getResourceAsStream("/application/Asset/Map/lanepanel.png"));
        LoadMap("/application/Asset/Map/lanepanel.txt");
        this.Width = (int) image.getWidth();
        this.Hight = (int) image.getHeight();

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