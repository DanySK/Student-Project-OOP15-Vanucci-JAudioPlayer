package controller.player;

import java.util.List;
import model.track.Track;

/**
 * This is the interface for the PlayerControllerImpl Class, defines the only two public methods called
 * by the app main controller
 * @author Francesco
 *
 */
public interface PlayerController {

	void setPlayQueue(List<Track> queue);
	
	void startQueue();
}
