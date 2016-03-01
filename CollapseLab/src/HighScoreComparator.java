import java.util.Comparator;

/**
 * Compares two highscores.
 * 
 * @author Arely Miramontes
 *
 */
public class HighScoreComparator implements Comparator<HighScore>
{
    /**
     * Compares two high scores
     * 
     * @param x the first high score being compared
     * @param y the second high score being compared
     * 
     * @return an integer indicating the result of the comparison.
     */
    @Override
    public int compare(HighScore x, HighScore y)
    {
        int returnVal = 0;
        
        /* tests if x high score is greater than y*/
        if(x.getScore() < y.getScore())
        {
            returnVal = -1;
        }
        /*tests if y high score is greater than x*/
        if(x.getScore() > y.getScore())
        {
            returnVal = 1;
        }
        /*Determines if the two scores are equal*/
        if(x.getScore() == y.getScore())
        {
            /*Compares x's name alphabetically to y's*/
            if(x.getName().compareTo(y.getName()) > 0)
            {
                returnVal = 1;
            }
            /*Compares y's name alphabetically to x's*/
            if(x.getName().compareTo(y.getName()) < 0)
            {
                returnVal = -1;
            }
        }
        
        return returnVal;
    }
}