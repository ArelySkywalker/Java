
/**
 * This is an immutable class representing a highscore.
 * 
 * @author Arely Miramontes
 */
public class HighScore
{
	/*the highscore, which is the number of clicks*/
	private int score;
	private String name;

	/**
	 * Constructor for objects of class HighScore
	 * 
	 * @param score the number of moves the player made
	 * @param name the name of the player
	 */
	public HighScore(int score, String name)
	{
		this.score = score;
		this.name = name;
	}

	/**
	 * Accessor method for getting a highscore.
	 * 
	 * @return  the score of this highscore
	 */
	public int getScore()
	{
		return this.score;
	}

	/**
	 * Accessor method for getting the name associated with a highscore.
	 * 
	 * @return the name of this highscore
	 */
	public String getName()
	{
		return this.name;
	}
}
