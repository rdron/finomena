# finomena

Its a restful web Service.
The requirements were - 

    On the home page players should be able to start a game or join current games
    When joining a game, ask for player name
    Each player is assigned a random color, when he/she joins the game
    A board of configurable dimension will be shown to all the players
    A game automatically starts when at least 2 players join it
    Each player can hover over the board and squares will light up with their assigned color
    Player can select the square by clicking it
    A player can acquire the square by clicking it
    Once a square is acquired it gets filled with the player's color
    An acquired square cannot be taken by any other player and its color will not change on hovering
    Once a square is selected by a player, all players are blocked for x seconds to do anything
    After x seconds, board becomes available again for all the players
    Game ends when all squares are colored and players with maximum squared colored wins.
    

There is a sample page hosted request/index.html to test the application on the server side requests.
Response is in JSON string.

There are 4 actions -
  1. request/getGames - gets the list of games
  2. request/newGame  - creates a new game with a player with given configuration on every request
  3. request/joinGame - adds a player to an existing game
  4. request/move     - request to make a move - success/failure depends on the constraints as given in requirements


  The below requirement is best implemented on the frontend. 
  --Each player can hover over the board and squares will light up with their assigned color

