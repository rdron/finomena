package com.game.finomena.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.game.finomena.domain.Game;
import com.game.finomena.domain.MoveTuple;
import com.game.finomena.domain.Player;
import com.game.finomena.domain.persistence.PersistenceService;
import com.game.finomena.domain.persistence.PersistenceServiceImpl;



@Component
@Path("/request")
public class GameService {

	static PersistenceService service = new PersistenceServiceImpl();


	@POST
	@Path("/newGame")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response requestNewGame(@FormParam("playerName") String name,
			@FormParam("x") String x,@FormParam("y") String y,
			@FormParam("maxPlayers") String maxPlayers,@FormParam("minPlayers") String minPlayers,
			@FormParam("waitTime") String waitTime) {
		Game game= null;
		try{
			game = new Game(name, Integer.parseInt(x),Integer.parseInt(y), 
					Integer.parseInt(maxPlayers), Integer.parseInt(minPlayers),	waitTime);
		}catch(NumberFormatException e){
			return Response.status(Status.BAD_REQUEST).entity("Illegal Argument").build();
		}
		service.addGame(game);
		return Response.status(200).entity(getResult(game).toJSONString()).build();
	}

	@POST
	@Path("/joinGame")
	public Response joinGame(@FormParam("gameId") String gameId, @FormParam("name") String playerName) {
		String output = null;
		Game game = service.getGameById(Integer.parseInt(gameId));
		if(game==null){
			output="Invalid Game Id";
			return Response.status(Status.BAD_REQUEST).entity(output).build();
		}
		int result = game.addPlayer(playerName);
		switch (result) {
		case 0:
			output="Player Name already exists";
			return Response.status(Status.FOUND).entity(output).build();
		case -1:
			output="Max Players Reached";
			return Response.status(Status.FOUND).entity(output).build();
		case 1:
			output+= playerName +" + Joined with color code "+game.getPlayers().lastElement().getColorCode(); ;
			if(game.getPlayers().size()==game.getMinPlayers()){
				output+="+ Game Started";
			}
			return Response.status(200).entity(getResult(game).toJSONString()).build();
		default:
			break;
		}
		return Response.status(200).entity(output).build();
	}


	@POST
	@Path("move")
	public Response move(@FormParam("gameId") String gameId,@FormParam("playerName") String playerName, @FormParam("x") String xlocation,
			@FormParam("y") String ylocation) {
		Game game =null;
		try{
			game = service.getGameById(Integer.parseInt(gameId));
		}
		catch(NumberFormatException e){
			String output="Invalid Game Id";
			return Response.status(Status.BAD_REQUEST).entity(output).build();
		}
		game.move(playerName, Integer.parseInt(xlocation), Integer.parseInt(ylocation));
		return Response.status(200).entity(getResult(game).toJSONString()).build();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getGames")
	public Response getGames() {

		List<Game> games = service.getAllGames();
		JSONObject gameList = new JSONObject();
		for(Game game: games){
			JSONObject gameDetail = new JSONObject();
			gameDetail.put("gameId", game.getGameId());
			gameDetail.put("boardsquares", game.getBoard().length);
			gameDetail.put("noOfPlayers", game.getPlayers().size());
			gameDetail.put("minPlayer", game.getMinPlayers());
			gameDetail.put("maxPlayer", game.getMaxPlayers());
			gameList.put(game.getGameId(), gameDetail);
		}
		return Response.status(200).entity(gameList.toJSONString()).build();
	}

	@GET
	@Path("index.html")
	public Response index() {
		String output =  "<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta charset=\"UTF-8\">\n" + 
				"<title>Insert title here</title>\n" + 
				"</head>\n" + 
				"<body>\n" + 
				"<form action=\"newGame\" method=\"post\"> \n" + 
				"  Player name:<br>\n" + 
				"  <input type=\"text\" name=\"playerName\" value=\"Mickey\">\n" + 
				"  <br>\n" + 
				"  Dimension:<br>\n" + 
				"  <input type=\"text\" name=\"x\" placeholder=\"Length\"><input type=\"text\" name=\"y\" placeholder=\"Breadth\">\n" + 
				"  <br>\n" + 
				"  Players:<br>\n" + 
				"  <input type=\"text\" name=\"maxPlayers\" placeholder=\"maxPlayers\"><input type=\"text\" name=\"minPlayers\" placeholder=\"minPlayers\">\n" + 
				"  <br>\n" + 
				"  Wait Time:<br>\n" + 
				"  <input type=\"text\" name=\"waitTime\">\n" + 
				"  <br>\n" + 
				"  <input type=\"submit\" value=\"Submit\">\n" + 
				"</form> \n" + 

 				"<form action=\"joinGame\" method=\"post\"> \n" + 
 				"  Join Game:<br>\n" +
 				"  Player name:<br>\n" + 
 				"  <input type=\"text\" name=\"name\" value=\"Mickey\">\n" + 
 				"  <br>\n" + 
 				"  GameID:<br>\n" + 
 				"  <input type=\"text\" name=\"gameId\">" + 
 				"  <br>\n" + 
 				"  <input type=\"submit\" value=\"Submit\">\n" + 
 				"</form> " + 
 				"\n" + 
 				"<form action=\"move\" method=\"post\"> \n" + 
 				"  Make Move:<br>\n" +
 				"  Player name:<br>\n" + 
 				"  <input type=\"text\" name=\"playerName\" value=\"Mickey\">\n" + 
 				"  <br>\n" + 
 				"  GameID:<br>\n" + 
 				"  <input type=\"text\" name=\"gameId\">" + 
 				"  <br>\n" + 
 				"  Move:<br>\n" + 
 				"  <input type=\"text\" name=\"x\" placeholder=\"x\"><input type=\"text\" name=\"y\" placeholder=\"y\">\n" + 

 				"  <input type=\"submit\" value=\"Submit\">\n" + 
 				"</form> " + 
 				"\n" + 
 				"<form action=\"getGames\" method=\"get\"> \n" + 
 				"  Get Games:<br>\n" +
 				"  <input type=\"submit\" value=\"Submit\">\n" + 
 				"</form> " + 


 				"</body>\n" + 
 				"</html>";
		return Response.status(200).entity(output).build();
	}
	@SuppressWarnings("unchecked")
	private JSONObject getResult(Game game){
		JSONObject result = new JSONObject();
		JSONArray players = new JSONArray();
		result.put("gameID", game.getGameId());
		result.put("status", game.getPlayers().size()>=game.getMinPlayers()?"Started":"Waiting");
		if( game.getBoard().length - game.getPlayers().stream().mapToInt(u->u.getCurrentScore()).sum()==0){
			result.put("status","Finished");
			result.put("Winner", game.getPlayers().stream().sorted().findFirst().get().getName());
		}
		for(Player player:game.getPlayers()){
			JSONObject playerResult = new JSONObject();
			playerResult.put("name", player.getName());
			playerResult.put("color", player.getColorCode());
			playerResult.put("score", player.getCurrentScore());
			JSONArray playerMoves = new JSONArray();
			for(MoveTuple moves:player.getMoves()){
				JSONObject playerMove = new JSONObject();
				playerMove.put("x", moves.getX());
				playerMove.put("y", moves.getY());
				playerMoves.add(playerMove);
			}
			players.add(playerMoves);
			players.add(playerResult);
		}
		result.put("players", players);

		return result;

	}
}