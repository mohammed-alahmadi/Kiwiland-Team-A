package nz.ac.aut.ense701.gameModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import nz.ac.aut.ense701.gui.PlaySound;
import nz.ac.aut.ense701.tools.MapLoader;
import nz.ac.aut.ense701.tools.OccupantLoader;

/**
 * This is the class that knows the Kiwi Island game rules and state
 * and enforces those rules.
 *
 * @author AS
 * @version 1.0 - created
 * Maintenance History
 * August 2011 Extended for stage 2. AS
 */

public class Game
{
    //Constants shared with UI to provide player data
    public static final int STAMINA_INDEX = 0;
    public static final int MAXSTAMINA_INDEX = 1;
    public static final int MAXWEIGHT_INDEX = 2;
    public static final int WEIGHT_INDEX = 3;
    public static final int MAXSIZE_INDEX = 4;
    public static final int SIZE_INDEX = 5;
    private String mapSelected;
    
    private final KiwiFacts kiwiFacts;
    
    private final PredatorFacts PredatorFacts;
    
    private boolean isFactForPlayer;
    private boolean isFactPredatorForPlayer;
    
    
    
    private PlaySound playSound;
    
    /**
     * A new instance of Kiwi island that reads data from "IslandData.txt".
     */
    public Game() 
    {   
        playSound = new PlaySound();
        eventListeners = new HashSet<GameEventListener>();
        kiwiFacts = new KiwiFacts();
        PredatorFacts = new PredatorFacts();
        isFactForPlayer = false;
        isFactPredatorForPlayer =false;
        //createNewGame();
    }
    
    
    /**
     * Starts a new game.
     * At this stage data is being read from a text file
     */
    public void createNewGame()
    {
        totalPredators = 0;
        totalKiwis = 0;
        predatorsTrapped = 0;
        kiwiCount = 0;
        if(mapSelected == null){
            initialiseIslandFromFile("Map.xml");
        }else{
        initialiseIslandFromFile(mapSelected); 
        }
        drawIsland();
        state = GameState.PLAYING;
        winMessage = "";
        loseMessage = "";
        playerMessage = "";
        notifyGameEventListeners();
    }

    /***********************************************************************************************************************
     * Accessor methods for game data
    ************************************************************************************************************************/
    
    public void setMap(String map){
        this.mapSelected = map;
    }
    
    /**
     * Get number of rows on island
     * @return number of rows.
     */
    public int getNumRows()
    {
        return island.getNumRows();
    }
    
    /**
     * Get number of columns on island
     * @return number of columns.
     */
    public int getNumColumns()
    {
        return island.getNumColumns();
    }
    
    /**
     * Gets the current state of the game.
     * 
     * @return the current state of the game
     */
    public GameState getState()
    {
        return state;
    }    
 
    /**
     * Provide a description of occupant
     * @param whichOccupant
     * @return description if whichOccuoant is an instance of occupant, empty string otherwise
     */
    public String getOccupantDescription(Object whichOccupant)
    {
       String description = "";
        if(whichOccupant !=null && whichOccupant instanceof Occupant)
        {
            Occupant occupant = (Occupant) whichOccupant;
            description = occupant.getDescription();
        }
        return description;
    }
 
        /**
     * Gets the player object.
     * @return the player object
     */
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Checks if possible to move the player in the specified direction.
     * 
     * @param direction the direction to move
     * @return true if the move was successful, false if it was an invalid move
     */
    public boolean isPlayerMovePossible(MoveDirection direction)
    {
        boolean isMovePossible = false;
        // what position is the player moving to?
        Position newPosition = player.getPosition().getNewPosition(direction);
        // is that a valid position?
        if ( (newPosition != null) && newPosition.isOnIsland() )
        {
            // what is the terrain at that new position?
            Terrain newTerrain = island.getTerrain(newPosition);
            // can the playuer do it?
            isMovePossible = player.hasStaminaToMove(newTerrain) && 
                             player.isAlive();
        }
        return isMovePossible;
    }
    
      /**
     * Get terrain for position
     * @param row
     * @param column
     * @return Terrain at position row, column
     */
    public Terrain getTerrain(int row, int column) {
        return island.getTerrain(new Position(island, row, column));
    }

    /**
     * Is this position visible?
     * @param row
     * @param column
     * @return true if position row, column is visible
     */
    public boolean isVisible(int row, int column) {
        return island.isVisible(new Position(island, row, column));

    }
   
    /**
    * Is this position explored?
    * @param row
    * @param column
    * @return true if position row, column is explored.
    */
    public boolean isExplored(int row, int column) {
        return island.isExplored(new Position(island, row, column));
    }

    /**
     * Get occupants for player's position
     * @return occupants at player's position
     */
    public Occupant[] getOccupantsPlayerPosition()
    {
        return island.getOccupants(player.getPosition());
    }
    
    /**
     * Get string for occupants of this position
     * @param row
     * @param column
     * @return occupant string for this position row, column
     */
    public String getOccupantStringRepresentation(int row, int column) {
        return island.getOccupantStringRepresentation(new Position(island, row, column));
    }
    
    /**
     * Get values from player for GUI display
     * @return player values related to stamina and backpack.
     */
    public int[] getPlayerValues()
    {
        int[] playerValues = new int[6];
        playerValues[STAMINA_INDEX ]= (int) player.getStaminaLevel();
        playerValues[MAXSTAMINA_INDEX]= (int) player.getMaximumStaminaLevel();
        playerValues[MAXWEIGHT_INDEX ]= (int) player.getMaximumBackpackWeight();
        playerValues[WEIGHT_INDEX]= (int) player.getCurrentBackpackWeight();
        playerValues[MAXSIZE_INDEX ]= (int) player.getMaximumBackpackSize();
        playerValues[SIZE_INDEX]= (int) player.getCurrentBackpackSize();
            
        return playerValues;
        
    }
    
    /**
     * How many kiwis have been counted?
     * @return count
     */
    public int getKiwiCount()
    {
        return kiwiCount;
    }
    
    /**
     * How many predators are left?
     * @return number remaining
     */
    public int getPredatorsRemaining()
    {
        return totalPredators - predatorsTrapped;
    }
    
    /**
     * Get contents of player backpack
     * @return objects in backpack
     */
    public Object[] getPlayerInventory()
            {
              return  player.getInventory().toArray();
            }
    
    /**
     * Get player name
     * @return player name
     */
    public String getPlayerName()
    {
        return player.getName();
    }

    /**
     * Is player in this position?
     * @param row
     * @param column
     * @return true if player is at row, column
     */
    public boolean hasPlayer(int row, int column) 
    {
        return island.hasPlayer(new Position(island, row, column));
    }
    
    /**
     * Only exists for use of unit tests
     * @return island
     */
    public Island getIsland()
    {
        return island;
    }
    
    /**
     * Draws the island grid to standard output.
     */
    public void drawIsland()
    {  
          island.draw();
    }
    
     /**
     * Is this object collectable
     * @param itemToCollect
     * @return true if is an item that can be collected.
     */
    public boolean canCollect(Object itemToCollect)
    {
        boolean result = (itemToCollect != null)&&(itemToCollect instanceof Item);
        if(result)
        {
            Item item = (Item) itemToCollect;
            result = item.isOkToCarry();
        }
        return result;
    }
    
    /**
     * Is this object a countable kiwi
     * @param itemToCount
     * @return true if is an item is a kiwi.
     */
    public boolean canCount(Object itemToCount)
    {
        boolean result = (itemToCount != null)&&(itemToCount instanceof Kiwi);
        if(result)
        {
            Kiwi kiwi = (Kiwi) itemToCount;
            result = !kiwi.counted();
        }
        return result;
    }
    /**
     * Is this object usable
     * @param itemToUse
     * @return true if is an item that can be collected.
     */
    public boolean canUse(Object itemToUse)
    {
        boolean result = (itemToUse != null)&&(itemToUse instanceof Item);
        if(result)
        {
            //Food can always be used (though may be wasted)
            // so no need to change result

            if(itemToUse instanceof Tool)
            {
                Tool tool = (Tool)itemToUse;
                //Traps can only be used if there is a predator to catch
                if(tool.isTrap())
                {
                    result = island.hasPredator(player.getPosition());
                    
                    
                }
                //Screwdriver can only be used if player has a broken trap
                else if (tool.isScrewdriver() && player.hasTrap())
                {
                    result = player.getTrap().isBroken();
                }
                else
                {
                    result = false;
                }
            }            
        }
        return result;
    }
    
        
    /**
     * Details of why player won
     * @return winMessage
     */
    public String getWinMessage()
    {
        return winMessage;
    }
    
    /**
     * Details of why player lost
     * @return loseMessage
     */
    public String getLoseMessage()
    {
        return loseMessage;
    }
    
    /**
     * Details of information for player
     * @return playerMessage
     */
    public String getPlayerMessage()
    {
        String message = playerMessage;
        playerMessage = ""; // Already told player.
        return message;
    }
    
    /**
     * Is there a message for player?
     * @return true if player message available
     */
    public boolean messageForPlayer() {
        return !("".equals(playerMessage));
    }
    
    public String getKiwiFact() {
        playSound.setSound(PlaySound.SOUND.KIWI);
        playSound.playSound();
        return kiwiFacts.getFact();
    }
    
   public String getFactPredator() {
        
        return PredatorFacts.getFactPredator();
    }
   
    public boolean isFactForPlayer() {
        if (isFactForPlayer) {
            isFactForPlayer = false;
            return true;
        }
        return false;
    }
   public boolean isFactPredatorForPlayer() {
        if (isFactPredatorForPlayer) {
            isFactPredatorForPlayer = false;
            return true;
        }
        return false;
    }
    
    /***************************************************************************************************************
     * Mutator Methods
    ****************************************************************************************************************/
    
   
    
    /**
     * Picks up an item at the current position of the player
     * Ignores any objects that are not items as they cannot be picked up
     * @param item the item to pick up
     * @return true if item was picked up, false if not
     */
    public boolean collectItem(Object item)
    {
        boolean success = (item instanceof Item) && (player.collect((Item)item));
        if(success)
        {
            // player has picked up an item: remove from grid square
            island.removeOccupant(player.getPosition(), (Item)item);
            
            
            // everybody has to know about the change
            notifyGameEventListeners();
        }      
        return success;
    } 

    
    /**
     * Drops what from the player's backpack.
     *
     * @param what  to drop
     * @return true if what was dropped, false if not
     */
    public boolean dropItem(Object what)
    {
        boolean success = player.drop((Item)what);
        if ( success )
        {
            // player has dropped an what: try to add to grid square
            Item item = (Item)what;
            success = island.addOccupant(player.getPosition(), item);
            if ( success )
            {
                // drop successful: everybody has to know that
                notifyGameEventListeners();
            }
            else
            {
                // grid square is full: player has to take what back
                player.collect(item);                     
            }
        }
        return success;
    } 
      
    
    /**
     * Uses an item in the player's inventory.
     * This can  be food or tool items.
     * @param item to use
     * @return true if the item has been used, false if not
     */
    public boolean useItem(Object item)
    {  
        boolean success = false;
        if ( item instanceof Food && player.hasItem((Food) item) )
        //Player east food to increase stamina
        {
            Food food = (Food) item;
            // player gets energy boost from food
            player.increaseStamina(food.getEnergy());
            // player has consumed the food: remove from inventory
            player.drop(food);
            // use successful: everybody has to know that
            notifyGameEventListeners();
        }
        else if (item instanceof Tool)
        {
            Tool tool = (Tool) item;
            if (tool.isTrap()&& !tool.isBroken())
            {
                 success = trapPredator(); 
            }
            else if(tool.isScrewdriver())// Use screwdriver (to fix trap)
            {
                if(player.hasTrap())
                    {
                        Tool trap = player.getTrap();
                        trap.fix();
                    }
            }
        }
        updateGameState();
        return success;
    }
    //new
    public void cookItem()
    {
    	player.Cookmeat();
    	updateGameState();
    }
    //done
    /**
     * Count any kiwis in this position
     */
    public void countKiwi() 
    {
        //check if there are any kiwis here
        for (Occupant occupant : island.getOccupants(player.getPosition())) {
            if (occupant instanceof Kiwi) {
                Kiwi kiwi = (Kiwi) occupant;
                if (!kiwi.counted()) {
                    kiwi.count();
                    kiwiCount++;
                    isFactForPlayer = true;
                }
            }
        }
        updateGameState();
    }
       
    /**
     * Attempts to move the player in the specified direction.
     * 
     * @param direction the direction to move
     * @return true if the move was successful, false if it was an invalid move
     */
    public boolean playerMove(MoveDirection direction)
    {
        // what terrain is the player moving on currently
        boolean successfulMove = false;
        if ( isPlayerMovePossible(direction) )
        {
            Position newPosition = player.getPosition().getNewPosition(direction);
            Terrain  terrain     = island.getTerrain(newPosition);

            // move the player to new position
            player.moveToPosition(newPosition, terrain);
            island.updatePlayerPosition(player);
            successfulMove = true;
                    
            // Is there a hazard?
            checkForHazard();

            updateGameState();            
        }
        return successfulMove;
    }
    
    
    
    /**
     * Adds a game event listener.
     * @param listener the listener to add
     */
    public void addGameEventListener(GameEventListener listener)
    {
        eventListeners.add(listener);
    }
    
    
    /**
     * Removes a game event listener.
     * @param listener the listener to remove
     */
    public void removeGameEventListener(GameEventListener listener)
    {
        eventListeners.remove(listener);
    }
   
    
    /*********************************************************************************************************************************
     *  Private methods
     *********************************************************************************************************************************/
    
    /**
     * Used after player actions to update game state.
     * Applies the Win/Lose rules.
     */
    private void updateGameState()
    {
         String message = "";
        if ( !player.isAlive() )
        {
            playSound.setSound(PlaySound.SOUND.LOSE);
            playSound.playSound();
            state = GameState.LOST;
            message = "Sorry, you have lost the game. " + this.getLoseMessage();
            this.setLoseMessage(message);
        }
        else if (!playerCanMove() )
        {
            playSound.setSound(PlaySound.SOUND.LOSE);
            playSound.playSound();
            state = GameState.LOST;
            message = "Sorry, you have lost the game. You do not have sufficient stamina to move.";
            this.setLoseMessage(message);
        }
        else if(predatorsTrapped == totalPredators)
        {
            playSound.setSound(PlaySound.SOUND.WIN);
            playSound.playSound();
            state = GameState.WON;
            message = "You win! You have done an excellent job and trapped all the predators.";
            this.setWinMessage(message);
        }
        else if(kiwiCount == totalKiwis)
        {
            if(predatorsTrapped >= totalPredators * MIN_REQUIRED_CATCH)
            {
                playSound.setSound(PlaySound.SOUND.WIN);
                playSound.playSound();
                state = GameState.WON;
                message = "You win! You have counted all the kiwi and trapped at least 80% of the predators.";
                this.setWinMessage(message);
            }
        }
        // notify listeners about changes
            notifyGameEventListeners();
    }
    
       
    /**
     * Sets details about players win
     * @param message 
     */
    private void setWinMessage(String message)
    {
        winMessage = message;
    }
    
    /**
     * Sets details of why player lost
     * @param message 
     */
    private void setLoseMessage(String message)
    {
        loseMessage = message;
    }
    
    /**
     * Set a message for the player
     * @param message 
     */
    private void setPlayerMessage(String message) 
    {
        playerMessage = message;
        
    }
    /**
     * Check if player able to move
     * @return true if player can move
     */
    private boolean playerCanMove() 
    {
        return ( isPlayerMovePossible(MoveDirection.NORTH)|| isPlayerMovePossible(MoveDirection.SOUTH)
                || isPlayerMovePossible(MoveDirection.EAST) || isPlayerMovePossible(MoveDirection.WEST));

    }
        
    /**
     * Trap a predator in this position
     * @return true if predator trapped
     */
    private boolean trapPredator()
    {
        Position current= player.getPosition();
        boolean hadPredator = island.hasPredator(current);
        if(hadPredator) //can trap it
        {
            Occupant occupant = island.getPredator(current);
            //Predator has been trapped so remove
            island.removeOccupant(current, occupant); 
           
            predatorsTrapped++;
            isFactPredatorForPlayer = true;
        }
        
        return hadPredator;
    }
    
    /**
     * Checks if the player has met a hazard and applies hazard impact.
     * Fatal hazards kill player and end game.
     */
    private void checkForHazard()
    {
        //check if there are hazards
        for ( Occupant occupant : island.getOccupants(player.getPosition())  )
        {
            if ( occupant instanceof Hazard )
            {
                //playSound.setSound(PlaySound.SOUND.HAZARD);
                //playSound.playSound();
                handleHazard((Hazard) occupant);
            }
        }
    }
    
    /**
     * Apply impact of hazard
     * @param hazard to handle
     */
    private void handleHazard(Hazard hazard) {
        if (hazard.isFatal()) 
        {
            player.kill();
            this.setLoseMessage(hazard.getDescription() + " has killed you.");
        } 
        else if (hazard.isBreakTrap()) 
        {
            Tool trap = player.getTrap();
            if (trap != null) {
                trap.setBroken();
                this.setPlayerMessage("Sorry your predator trap is broken. You will need to find tools to fix it before you can use it again.");
            }
        } 
        else // hazard reduces player's stamina
        {
            double impact = hazard.getImpact();
            // Impact is a reduction in players energy by this % of Max Stamina
            double reduction = player.getMaximumStaminaLevel() * impact;
            player.reduceStamina(reduction);
            // if stamina drops to zero: player is dead
            if (player.getStaminaLevel() <= 0.0) {
                player.kill();
                this.setLoseMessage(" You have run out of stamina");
            }
            else // Let player know what happened
            {
                this.setPlayerMessage(hazard.getDescription() + " has reduced your stamina.");
            }
        }
    }
    
    
    /**
     * Notifies all game event listeners about a change.
     */
    private void notifyGameEventListeners()
    {
        for ( GameEventListener listener : eventListeners ) 
        {
            listener.gameStateChanged();
        }
    }

    
    /**
     * Loads terrain and occupant data from a file.
     * At this stage this method assumes that the data file is correct and just
     * throws an exception or ignores it if it is not.
     * 
     * @param fileName file name of the data file
     */
    private void initialiseIslandFromFile(String fileName) 
    {
        Exception temp = null;
        try{
            File occupantFile = new File("Occupants.xml");
            SAXParserFactory occupantFactory = SAXParserFactory.newInstance();
            SAXParser occupantSAXParser = occupantFactory.newSAXParser();
            OccupantLoader occupantLoader = new OccupantLoader();
            occupantSAXParser.parse(occupantFile, occupantLoader);
            
            System.out.println("test");
            File mapFile = new File(fileName);
            SAXParserFactory mapFactory = SAXParserFactory.newInstance();
            SAXParser mapSAXParser = mapFactory.newSAXParser();
            MapLoader mapLoader = new MapLoader(occupantLoader);
            mapSAXParser.parse(mapFile, mapLoader);
            
            int mapSize = mapLoader.getMapSize();
            island = new Island(mapSize, mapSize);
            
            setUpTerrain(mapLoader.getTerrain());
            
            
            setUpPlayer();
            
            setUpOccupants(mapLoader.getOccupantSize(), occupantLoader, mapLoader.getOccupants());
        }catch(Exception e){
            temp = e;
        }
        if(temp!=null){
            temp.printStackTrace();
        }
//        try
//        {
//            Scanner input = new Scanner(new File(fileName));
//            // make sure decimal numbers are read in the form "123.23"
//            input.useLocale(Locale.US);
//            input.useDelimiter("\\s*,\\s*");
//
//            // create the island
//            int numRows    = input.nextInt();
//            int numColumns = input.nextInt();
//            island = new Island(numRows, numColumns);
//
//            // read and setup the terrain
//            setUpTerrain(input);
//
//            // read and setup the player
//            setUpPlayer(input);
//
//            // read and setup the occupants
//            setUpOccupants(input);
//
//            input.close();
//        }
//        catch(FileNotFoundException e)
//        {
//            System.err.println("Unable to find data file '" + fileName + "'");
//        }
//        catch(IOException e)
//        {
//            System.err.println("Problem encountered processing file.");
//        }
    }

    /**
     * Reads terrain data and creates the terrain.
     * 
     * @param input data from the level file
     */
    private void setUpTerrain(String tempTerrain) 
    {
        System.err.println(tempTerrain);
        for ( int row = 0 ; row < island.getNumRows() ; row++ ) 
        {
            String terrainRow = tempTerrain;
            for ( int col = 0 ; col < island.getNumColumns() ; col++ )
            {
                Position pos = new Position(island, row, col);
                String   terrainString = terrainRow.substring(col, col+1);
                Terrain  terrain = Terrain.getTerrainFromStringRepresentation(terrainString);
                island.setTerrain(pos, terrain);
            }
        }
    }

    /**
     * Reads player data and creates the player.
     * @param input data from the level file
     */
    private void setUpPlayer() 
    {
        //TEMP setup. Will change
        
        Position pos = new Position(island, 0, 2);
        player = new Player(pos, "Name Here", 100.0, 10.0, 5.0);
        island.updatePlayerPosition(player);
        
//        String playerName              = input.next();
//        int    playerPosRow            = input.nextInt();
//        int    playerPosCol            = input.nextInt();
//        double playerMaxStamina        = input.nextDouble();
//        double playerMaxBackpackWeight = input.nextDouble();
//        double playerMaxBackpackSize   = input.nextDouble();
//        
//        Position pos = new Position(island, playerPosRow, playerPosCol);
//        player = new Player(pos, playerName, 
//                playerMaxStamina, 
//                playerMaxBackpackWeight, playerMaxBackpackSize);
//        island.updatePlayerPosition(player);
    }

    /**
     * Creates occupants listed in the file and adds them to the island.
     * @param input data from the level file
     */
    private void setUpOccupants(int occupantCount, OccupantLoader occupantLoader, ArrayList<MapLoader.OccupantObject> occupantList) 
    {
        
        for(MapLoader.OccupantObject tempOccupant: occupantList){
            OccupantLoader.LoaderItem item = occupantLoader.getOccupantObject(tempOccupant.getItemID());
            
            Position occPos = new Position(island, tempOccupant.getxPos(), tempOccupant.getyPos());
            
            Occupant occupant = null;
            
            System.err.println("Game Test " + item.getType() + " " + item.getItemName() + " " + tempOccupant.getItemID());
            
            if(item.getType().equalsIgnoreCase("Trap")){
                occupant = new Tool(occPos, item.getItemName(), item.getItemDescription(), 
                        item.getItemWeight(), item.getItemSize());
                
            }else if(item.getType().equalsIgnoreCase("Food")){
                occupant = new Food(occPos, item.getItemName(), item.getItemDescription(), 
                        item.getItemWeight(), item.getItemSize(), item.getItemEnergy());
            }else if(item.getType().equalsIgnoreCase("Hazard")){
                occupant = new Hazard(occPos, item.getItemName(), item.getItemDescription(),
                        item.getImpact());
                System.err.println("Check Name Hazard : " + item.getItemName());
            }else if(item.getType().equalsIgnoreCase("Kiwi")){
                occupant = new Kiwi(occPos, item.getItemName(), item.getItemDescription());
                totalKiwis++;
            }else if(item.getType().equalsIgnoreCase("Fauna")){
                occupant = new Fauna(occPos, item.getItemName(), item.getItemDescription());
            }else if(item.getType().equalsIgnoreCase("Predator")){
                occupant = new Predator(occPos, item.getItemName(), item.getItemDescription());
                totalPredators++;
            }
            if ( occupant != null ) island.addOccupant(occPos, occupant);
        }
        
        
//        int numItems = input.nextInt();
//        for ( int i = 0 ; i < numItems ; i++ ) 
//        {
//            String occType  = input.next();
//            String occName  = input.next(); 
//            String occDesc  = input.next();
//            int    occRow   = input.nextInt();
//            int    occCol   = input.nextInt();
//            Position occPos = new Position(island, occRow, occCol);
//            Occupant occupant    = null;
//
//            if ( occType.equals("T") )
//            {
//                double weight = input.nextDouble();
//                double size   = input.nextDouble();
//                occupant = new Tool(occPos, occName, occDesc, weight, size);
//            }
//            else if ( occType.equals("E") )
//            {
//                double weight = input.nextDouble();
//                double size   = input.nextDouble();
//                double energy = input.nextDouble();
//                occupant = new Food(occPos, occName, occDesc, weight, size, energy);
//            }
//            else if ( occType.equals("H") )
//            {
//                double impact = input.nextDouble();
//                occupant = new Hazard(occPos, occName, occDesc,impact);
//            }
//            else if ( occType.equals("K") )
//            {
//                occupant = new Kiwi(occPos, occName, occDesc);
//                totalKiwis++;
//            }
//            else if ( occType.equals("P") )
//            {
//                occupant = new Predator(occPos, occName, occDesc);
//                totalPredators++;
//            }
//            else if ( occType.equals("F") )
//            {
//                occupant = new Fauna(occPos, occName, occDesc);
//            }
//            if ( occupant != null ) island.addOccupant(occPos, occupant);
//        }
    }    


    private Island island;
    private Player player;
    private GameState state;
    private int kiwiCount;
    private int totalPredators;
    private int totalKiwis;
    private int predatorsTrapped;
    private Set<GameEventListener> eventListeners;
    
    private final double MIN_REQUIRED_CATCH = 0.8;
        
    private String winMessage = "";
    private String loseMessage  = "";
    private String playerMessage  = "";   

    



}
