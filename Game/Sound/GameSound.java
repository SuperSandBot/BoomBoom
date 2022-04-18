
package Game.Sound;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class GameSound {
	public static GameSound instance;

	public static final String MENU = "menu.wav";
	public static final String PLAYGAME = "playgame.wav";
	public static final String BOMB = "newbomb.wav";
	public static final String BOMBER_DIE = "bomber_die.wav";
	public static final String BOMBER_DieDRINK = "bomDrink.wav";
	public static final String MONSTER_DIE = "monster_die.wav";
	public static final String BOMB_BANG = "bomb_bang.wav";
	public static final String ITEM = "item.wav";
	public static final String WIN = "win.wav";
	public static final String LOSE = "lose.mid";
	public static final String FOOT = "foot.wav";


	public static GameSound getIstance() {
		if (instance == null) {
			instance = new GameSound();
		}
		return instance;
	}

	public void Audio(String name) {
		Media sound = new Media(getClass().getResource(name).toExternalForm());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();

	}

}

