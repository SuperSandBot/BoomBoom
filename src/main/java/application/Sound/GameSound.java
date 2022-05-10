
package application.Sound;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class GameSound {
	public static GameSound instance;
	
	public static final String BOMB = "newbomb.wav";
	public static final String BOMBER_DIE = "bomber_die.wav";
	public static final String BOMB_BANG = "bomb_bang.wav";
	public static final String ITEM = "item.wav";
	public static final String GAMEOVER = "gameover_theme.mp3";
	public static final String GAMEPLAY = "gameplay_theme.mp3";
	public static final String GAMEMENU = "menu_theme.mp3";
	public static final String GAMESTARTSOUND = "gamestart_theme.mp3";
	public static final String BTNSOUND = "btnclicked_theme.mp3";

	public static GameSound getIstance() {
		if (instance == null) {
			instance = new GameSound();
		}
		return instance;
	}
	
	public MediaPlayer sound;
	public MediaPlayer backGroundMusic;

	public void playAudio(String name) {
		Media media = new Media(getClass().getResource(name).toExternalForm());
		MediaPlayer sound = new MediaPlayer(media);
		sound.play();
	}
	
	public void playOnBackGround(String name) {
		Media media = new Media(getClass().getResource(name).toExternalForm());
		backGroundMusic = new MediaPlayer(media);
		backGroundMusic.setCycleCount(3);
		backGroundMusic.play();
	}

}

