/**
 * 
 */
package com.game.finomena.domain.persistence;

import java.util.List;

import com.game.finomena.domain.Game;

/**
 * @author ron
 *
 */
public interface PersistenceService {
	public List<Game> getAllGames();
	
	public Game getGameById(Integer id);
	
	public int addGame(Game game);
	
}
