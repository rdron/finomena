/**
 * 
 */
package com.game.finomena.domain.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.game.finomena.domain.Game;


/**
 * @author ron
 *
 */
public class PersistenceServiceImpl implements PersistenceService {
	
	static Map<Integer,Game> gameCollection = new HashMap<Integer, Game>(); 

	@Override
	public List<Game> getAllGames() {
		List<Game> games = new ArrayList<Game>();
	    gameCollection.forEach((t,v)->{
	    	games.add(v);
	    	});
		return games;
	}

	@Override
	public Game getGameById(Integer id) {
		if(gameCollection.containsKey(id))
			return gameCollection.get(id);
		else 
			return null;
	}

	@Override
	public int addGame(Game game) {
		if(gameCollection.containsKey(game.getGameId()))
			return -1;
		else{
			gameCollection.put(game.getGameId(), game);
			return 0;
		}
	}
	
}
