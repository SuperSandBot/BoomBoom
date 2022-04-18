package Game.HightScoreList;

public class HightScore {
    private string name;
    private int score;
    
    public HightScore(string name, int score){
        this.name = name;
        this.score = score;
    }

    public string getName(){
        return name;
    }

    public void setName(string name){
        this.name = name;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score; 
    }
}
