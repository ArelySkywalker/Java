import java.io.*;
import java.util.*;
//import org.ini4j.*;

/**
 * CollapseConsole is the text-based console user interface to the Collapse game.
 * The main() method starts the application running using System.in and System.out.
 * CollapseConsole can also return the top five high scores from the Hall of Fame.
 * 
 * @author Erik Owen
 * @version 1
 */
public class CollapseConsole
{
    private static BufferedReader reader;
    private static PrintWriter writer;
//    /*How many hall of fame entries to return */
    public final static int kHallSize = 5;
//    /*Path to high scores directory*/
    private final static String kHallOfFameDirPath = "collapse";
//    /*Path to high scores file*/
    private final static String kHallOfFamePath = "collapse/halloffame.ser";
    private final static String kPreferencesPath = "collapse/preferences.ini";
    private final static int kNumBoards = 5000;
    private final static int kRestart = 1;
    private final static int kNewGame = 2;
    private final static int kSelectGame = 3;
    private final static int kScores = 4;
    private final static int kCheat = 5;
    private final static int kQuit = 6;
    private final static int kCharToInt = 65;
    private final static int kMaxNameLength = 20;
    private final static int kDefaultBoardSize = 8;
    private int boardNum;
    private CollapseGame game;
    private int boardPrefSize;
    
    /** Entry point for the application.
     *
     *  @param args ignored     
     */
    public static void main(String[] args)
    {   
        CollapseConsole app = new CollapseConsole(); 
        app.setIOsources(new InputStreamReader(System.in), 
            new OutputStreamWriter(System.out));
        app.run();
    }

    /** Construct a new instance of this class.
     * Create the game components and initialize them,
     * create the user interface and connect it to the game.
     */
    public CollapseConsole()
    {
        this.boardNum = new Random().nextInt(kNumBoards);
		this.boardPrefSize = 8;
    }
    
    /** Set input/output sources for Stream-based user interfaces.
     * @param rdr A Reader from which user input is obtained.
     * @param wtr A Writer to which program output is displayed.
     */
    public void setIOsources(Reader rdr, Writer wtr) 
    {
        this.reader = new BufferedReader(rdr);
        this.writer = new PrintWriter(wtr);
    }

    /** Run the console user interface, using the i/o streams provided by
     *  setIOsources();
     *  @pre setIOsources() has been called.
     */
    public void run()
    {
        this.game = new CollapseGame(this.boardPrefSize, this.boardNum);
        String userInput = "";
        int userChoice = 0;
        boolean gameOver = false, validMove = false;
        Scanner scan = new Scanner(reader);

        displayBoardAndOptions();
        
        /*Keep accepting user input until the user's choice is to quit*/
        while(userChoice != kQuit && scan.hasNext())
        {   
            try
            {
                /*Only reads in from scanner if there is a next line*/
                if(scan.hasNext())
                {
                    userInput = scan.nextLine().trim();   
                }
                
                /*If the user only enters one character*/
                if(userInput.length() == 1)
                {
                    userChoice = Integer.parseInt(userInput);
                    executeCommand(scan, userChoice);
                }
                /*If the character enters more than one character*/
                else if(userInput.length() > 1)
                {
                    userInput = userInput.substring(0, 2);
                    int row = userInput.substring(0, 1)
                        .toUpperCase().charAt(0) - kCharToInt;
                    int column = Integer.parseInt(userInput.substring(1, 2)) - 1;
                    
                    validMove = game.takeTurn(row, column);
                    
                    /*Determines if the move is valid*/
                    if(validMove)
                    {
                        gameOver = game.isGameOver();
                        displayBoardAndOptions();
                    }
                    /*Determines if the game is over*/
                    if(gameOver && validMove)
                    {
                        gameOver(scan);
                    }
                }
            }
            catch(Exception e)
            {
                userChoice = 0;
            }
        }
        
    }
    
    private void executeCommand(Scanner scan, int userChoice) throws IOException
    {
        /*Determines which choice the user made and executes that option*/
        switch(userChoice)
        {
            case kRestart:
                //restart the game
                this.game = new CollapseGame(this.boardPrefSize, this.boardNum);
                displayBoardAndOptions();
                break;
            case kNewGame:
                //Start a new game on this board
                if(this.boardNum == kNumBoards)
                {
                    this.boardNum = 1;
                }
                else
                {
                    this.boardNum++;
                }
                this.game = new CollapseGame(this.boardPrefSize, this.boardNum);
                displayBoardAndOptions();
                break;
            case kSelectGame:
                //Select a game
                selectGame(scan);
                displayBoardAndOptions();
                break;
            case kScores:
                //View high scores
                writer.write("-- High Scores --\n");
                writer.write(HighScores.getHighScores());
                writer.flush();           
                break;
            case kCheat:
                //cheat
                this.game.cheat();
                displayBoardAndOptions();
                break;
            case kQuit:
                //exit
                scan.close();
                //System.exit(0);
                break;
            default:
                break;
        }
    }
    
    private void gameOver(Scanner scan) throws IOException
    {
        String input = "", name = "";

        writer.write("Game Won Notification: Game " + this.boardNum + " Cleared! \n");
        writer.write("Save your score? (y/n)\n");
        writer.flush();
        
        /*Only reads in from scanner if there is another line*/
        if(scan.hasNext())
        {
            input = scan.nextLine().trim().toLowerCase();   
        }
        
        /*Determines if user wants to save high score or not*/
        if(input.equals("y"))
        {
            writer.write("Name Entry: Your score of " + game.getNumberOfMoves()
                + " will be entered into the Hall of Fame. \n");
            writer.write("Enter your name: \n");
            writer.flush();
            
            /*Only reads in from scanner if there is another line*/
            if(scan.hasNext())
            {
                name = scan.nextLine();
            }
            
            /*Truncates name if it is more than 20 characters*/
            if(name.length() > kMaxNameLength)
            {
                name = name.substring(0, kMaxNameLength);
            }
                
            HighScores.addHighScore(name, this.game.getNumberOfMoves());
        }
    }
    
    private void selectGame(Scanner scan)
    {
        String boardNumberString = "-1";
        int boardNumber;

        writer.write("Select Game: Enter desired game number (1 - 5000):\n");
        writer.flush();
        
        /*Only reads in from scanner if there is another line*/
        if(scan.hasNext())
        {
            boardNumberString = scan.nextLine();
        }
        
        boardNumber = Integer.parseInt(boardNumberString);
        
        /*Makes sure the board number is in the range of 1-5000*/
        if(boardNumber > 0 && boardNumber <= kNumBoards)
        {
            this.game = new CollapseGame(this.boardPrefSize, boardNumber);
            this.boardNum = boardNumber;
        }
    }
    
    private void displayBoardAndOptions()
    {
        char[][] board = game.getCharacterBoard();
        writer.write("Collapse - board " + this.boardNum + "\n");
        writer.write("Tiles left: " + game.getTilesLeft() + "    Moves: "
            + game.getNumberOfMoves() + "\n");
        String colString = "     ";
        
        /*Creates the top row of numbers on the board*/
        for(int colIter = 1; colIter < board[0].length; colIter++)
        {
            colString = colString.concat(colIter + "  ");
        }
        colString = colString.concat(board[0].length + "\n");
        writer.write(colString);
        
        char curLetter = 'A';
        String curRow = "";
        
        /*Iterates through each row on the board*/
        for(int rowIter = 0; rowIter < board.length; rowIter++, curLetter++)
        {
            curRow = curRow.concat(" " + curLetter + ":  ");
            
            /*Creates each row of the board by concatenating all of the columns*/
            for(int colIter = 0; colIter < board[0].length; colIter++)
            {
                curRow = curRow.concat(new
                    Character(board[rowIter][colIter]).toString());
                    
                /*Makes sure spacing is correct*/
                if(colIter < board[0].length - 1)
                {
                    curRow = curRow.concat("  ");
                }
            }
                
            curRow = curRow.concat("\n");
            writer.write(curRow);
            curRow = "";
                
        }
            
        String dashedLine = " ----";
        
        /*Creates the dashed line's size depending on the size of the board*/
        for(int dashNdx = 0; dashNdx < board[0].length - 1; dashNdx++)
        {
            dashedLine = dashedLine.concat("---");
        }
            
        dashedLine = dashedLine.concat("-\n");
        writer.write(dashedLine);
        writer.write("1)Restart 2)New Game 3)Select Game 4)Scores 5)Cheat 6)Quit \n");
        writer.flush();
    }

//    /** Adds a new high score to the high scores file
//     * 
//     * @param name The name of the score with the high score
//     * @param score The score the player receieved
//     */
    protected void addHighScore(String name, int score) throws IOException
    {
        File highScoresFile = new File(kHallOfFamePath);
        boolean createdDir = false, createdFile = false;

        /*Creates a high scores file if it does not exist*/
        if(!highScoresFile.exists())
        {       
            createdFile = highScoresFile.createNewFile();
        }
            
        /*Writes to the high score file*/
        FileWriter highScoreWriter = new FileWriter(highScoresFile, true);
        highScoreWriter.write(score + " " + name + "\n");
        highScoreWriter.close();
    }
    
    private String parseAndSortHighScores(File highScoresFile) throws IOException
    {
        String highScoresString = "";
        ArrayList<HighScore> scoresList = new ArrayList<HighScore>();
        
        Scanner scan = new Scanner(highScoresFile);
        
        /*Reads all of the scores in the high scores file*/
        while(scan.hasNextLine())
        {
            String curLine = scan.nextLine();
            scoresList.add(new HighScore(Integer.parseInt(curLine.substring(0,
                 curLine.indexOf(" "))), curLine.substring(curLine.indexOf(" ") + 1,
                      curLine.length())));
        }
        
       scan.close();
        Collections.sort(scoresList, new HighScoreComparator());
        
        /*Gets the top 5 best scores*/
        for(int scoreNdx = 0; scoreNdx < kHallSize && scoreNdx < scoresList.size();
            scoreNdx++)
        {
            HighScore curScore = scoresList.get(scoreNdx);
            highScoresString = highScoresString.concat(String.format("%10s",
                 curScore.getScore()) + "    " + curScore.getName() + "\n");
        }
        highScoresString = highScoresString.concat("\n");
        return highScoresString;
    }
    
    /** Return a string representation of the top five high scores. 
     *  @return string is the top scores, one per line, with the
     *  score and name (in that order), separated by one or more blanks.
     *  Name is twenty characters max.  Leading blanks are allowed.
     */
    public String getHighScores()
    {
        File highScoresFile = new File(kHallOfFamePath);
        String highScoresString = "";
        
        try
        {
            /*Gets the high scores if the file exists*/
            if (highScoresFile.exists())
            {
                highScoresString = parseAndSortHighScores(highScoresFile);
            }
        }
        catch(Exception e)
        {
            highScoresString = "";
        }
        
        return highScoresString;
    }
    
//    private int getPreferenceSize() throws IOException
//    {   
//        Ini ini = new Ini();
//        
//        ini.load(new FileReader(new File(kPreferencesPath)));
//        Ini.Section section = ini.get("Board Size");
//        int prefSize = Integer.parseInt(section.get("small"));
//        
//        return prefSize;
//    }
}