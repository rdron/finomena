/**
 * 
 */
package com.game.finomena.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ron
 *
 */
public class Player implements Comparable<Player> {
	Game game;
	String name;
	int currentScore = 0;
	Double colorCode = null;
	List<MoveTuple> moves = new ArrayList<MoveTuple>();
	
	public Player(Game game, String name){
		this.game =  game;
		this.name = name;
		colorCode= Math.random() * 0x1000000;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the currentScore
	 */
	public int getCurrentScore() {
		return currentScore;
	}

	/**
	 * @param currentScore the currentScore to set
	 */
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	/**
	 * @return the colorCode
	 */
	public Double getColorCode() {
		return colorCode;
	}
	
	/**
	 * @return the moves
	 */
	public List<MoveTuple> getMoves() {
		return moves;
	}

	/**
	 * @param moves the moves to set
	 */
	public void setMoves(List<MoveTuple> moves) {
		this.moves = moves;
	}

	/**
	 * @param colorCode the colorCode to set
	 */
	public void setColorCode(Double colorCode) {
		this.colorCode = colorCode;
	}

	@Override
	public int compareTo(Player o) {
		return o.getCurrentScore()-this.currentScore;
	}

}
