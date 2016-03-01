import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class HighScores {

    /*Path to high scores directory*/
    private final static String kHallOfFameDirPath = "collapse";
    /*Path to high scores file*/
    private final static String kHallOfFamePath = "collapse/halloffame.ser";
    /*How many hall of fame entries to return */
    public final static int kHallSize = 5;
	
    /** Adds a new high score to the high scores file
     * 
     * @param name The name of the score with the high score
     * @param score The score the player receieved
     */
    public static void addHighScore(String name, int score) throws IOException
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
    
    private static String parseAndSortHighScores(File highScoresFile) throws IOException
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
    public static String getHighScores()
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
}