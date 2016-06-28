/**
 * 
 */
package com.game.finomena.domain;

import java.util.Vector;


/**
 * @author ron
 *
 */
public class Game {
	
	Vector<Player> players = new Vector<Player>() ;
	public static int gameCounter=0;
	public int maxPlayers;
	public int minPlayers;
	private long waitTime;
	
	int gameId;
	Boolean paused = false;
	String[][] board;
	
	public Game(String playerName,int dimensionX,int dimensionY, int maxPlayer, int minPlayers, String waitTime){
		Player player = new Player(this,playerName);
		players.add(player);
		this.maxPlayers = maxPlayer;
		this.minPlayers = minPlayers;
		gameId = gameCounter++;
		this.waitTime= Long.parseLong(waitTime)*1000;
		board = new String[dimensionX][dimensionY];
	}
	
	public synchronized int move(String playerName,int x, int y){
		synchronized (paused) {
			
			if(!paused){
				if(board[x][y]==null && players.stream().filter(u->u.name.equalsIgnoreCase(playerName)).count()==1){
					paused = true;
					Runnable a = new Runnable() {
						
						@Override
						public void run() {
							try {
								Thread.sleep(waitTime);
								paused = false;
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					};
					new Thread(a).start();
					board[x][y] = playerName;
					Player player = players.stream().filter(u->u.name.equalsIgnoreCase(playerName)).findFirst().get();
					player.currentScore++;
					player.moves.add( new MoveTuple(x, y));
					return board.length - players.stream().mapToInt(u->u.currentScore).sum();
				}
				else if(board[x][y]==null && players.stream().filter(u->u.name.equalsIgnoreCase(playerName)).count()!=1){
					return -1;
				}
				else if(board[x][y]!=null){
					return -2;
				}
			}
		}
		
		return -3;
	}
	
	
	public synchronized int addPlayer(String playerName){
		if(players.size()>=maxPlayers){
			return -1;
		}
		if(players.stream().filter(u->u.name.equalsIgnoreCase(playerName)).count()>0)
			return 0;
		else {
			Player player = new Player(this,playerName);
			players.add(player);
			return 1;
		}
	}
	
	public int getGameId(){
		return this.gameId;
	}

	/**
	 * @return the players
	 */
	public Vector<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(Vector<Player> players) {
		this.players = players;
	}

	/**
	 * @return the gameCounter
	 */
	public static Integer getGameCounter() {
		return gameCounter;
	}

	/**
	 * @param gameCounter the gameCounter to set
	 */
	public static void setGameCounter(Integer gameCounter) {
		Game.gameCounter = gameCounter;
	}

	/**
	 * @return the maxPlayers
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * @param maxPlayers the maxPlayers to set
	 */
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	/**
	 * @return the minPlayers
	 */
	public int getMinPlayers() {
		return minPlayers;
	}

	/**
	 * @param minPlayers the minPlayers to set
	 */
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	/**
	 * @return the waitTime
	 */
	public long getWaitTime() {
		return waitTime;
	}

	/**
	 * @param waitTime the waitTime to set
	 */
	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	/**
	 * @return the paused
	 */
	public Boolean getPaused() {
		return paused;
	}

	/**
	 * @param paused the paused to set
	 */
	public void setPaused(Boolean paused) {
		this.paused = paused;
	}

	/**
	 * @return the board
	 */
	public String[][] getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(String[][] board) {
		this.board = board;
	}

	/**
	 * @param gameId the gameId to set
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
}
