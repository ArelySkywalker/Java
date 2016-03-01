import java.util.HashSet;
import java.util.Random;


public class AshBrain
{
	// These Hashmaps store data for quick lookup time
	static HashSet<String> pokemonHash = new HashSet<String>();
	static HashSet<String> stateHash = new HashSet<String>();

	//Constructor, initializes AI by grabbing data from ENUMS.
	public AshBrain()
	{
		getEnums();
	}

	/*
	 * This method determines the response to the user upon receiving input.
	 * It implements direct matching, partial matching, and stochasticity.
	 */
	public static String communicate(String input){

		String response;


		if(input.toLowerCase().equals("hi") || input.toLowerCase().equals("hello") || 
				input.toLowerCase().equals("hey") || input.toLowerCase().equals("yo") || 
				input.toLowerCase().equals("howdy") || input.toLowerCase().equals("hola"))
			response = "Hi!";	
		
		else if(input.toLowerCase().contains("bye"))
			response = "Bye!";
		
		else if(input.toLowerCase().contains("you") 
				&& input.toLowerCase().contains("old")
				|| input.toLowerCase().contains("age"))
			response = "I am 10 years old. How old are you?";

		else if(input.toLowerCase().contains("how") && input.toLowerCase().contains("are") && 
				input.toLowerCase().contains("you"))
			response = "Fine thanks! Do you like Pokemon?";

		else if(input.toLowerCase().contains("what") && input.toLowerCase().contains("your")
				&& input.toLowerCase().contains("name"))
			response = "My name is Ash. What's yours?";
		
		else if(input.toLowerCase().contains("pikachu") 
				&& input.toLowerCase().contains("you")
				&& input.toLowerCase().contains("did") 
				|| input.toLowerCase().contains("get")
				|| input.toLowerCase().contains("receive")
				|| input.toLowerCase().contains("gave"))
			response = "Professor Oak. Do you have a Pikachu?";

		else if(input.toLowerCase().contains("your") 
				&& input.toLowerCase().contains("favorite")
				&& input.toLowerCase().contains("pokemon"))
			response = "My Pikachu of course! What's yours?";

		else if(input.toLowerCase().contains("where") 
				&& input.toLowerCase().contains("you")
				&& input.toLowerCase().contains("from"))
			response = "I'm from Pallet Town. What state are you from?";

		else if(input.toLowerCase().contains("your") 
				&& input.toLowerCase().contains("first")
				&& input.toLowerCase().contains("pokemon"))
			response = "Pikachu! What was yours?";

		else if(input.toLowerCase().contains("badges") 
				&& input.toLowerCase().contains("you")
				&& input.toLowerCase().contains("have"))
			response = "57 right now! How many do you have?";

		else if(input.toLowerCase().contains("you") 
				&& input.toLowerCase().contains("caught"))
			response = "77 so far. What about you?";

		else if(input.toLowerCase().contains("many") 
				|| input.toLowerCase().contains("much")
				&& input.toLowerCase().contains("pokemon")
				&& input.toLowerCase().contains("there")
				|| input.toLowerCase().contains("exist"))
			response = "Hmm... my Pokedex says there 721 discovered Pokemon so far!";
		
		else if(input.toLowerCase().contains("who")
				&& input.toLowerCase().contains("want")
				&& input.toLowerCase().contains("you")
				|| input.toLowerCase().contains("goal"))
			response = "I want to be the very best, like no one ever was! Who do you want to be?";

		else if(input.toLowerCase().contains("you") 
				&& input.toLowerCase().contains("friends")
				|| input.toLowerCase().contains("best"))
			response = "My best friends are Brock and Misty! Who are yours?";

		else if(input.toLowerCase().contains("what") 
				&& input.toLowerCase().contains("you")
				&& input.toLowerCase().contains("test"))
			response = "To catch them is my real test!";

		else if(input.toLowerCase().contains("what") 
				&& input.toLowerCase().contains("you")
				&& input.toLowerCase().contains("cause"))
			response = "To train them is my cause!";

		else if(input.toLowerCase().equals("meowth"))
			response = "Prepare for trouble. Make it double. "
					+ "To protect the world from devastation. To unite all peoples\n "
					+ "within our nation. To denounce the evils of truth and love.\n To "
					+ "extend our reach to the stars above. Jessie! James!\n Team Rocket,"
					+ " blast off at the speed of light! Surrender now, or prepare to fight!\n"
					+ " Meowth! That's right!";
		
		else if(isNumeric(input))
			response = "That's a nice number!";
		
		else if (pokemonHash.contains(input.toLowerCase())
				|| input.toLowerCase().contains("my")
				|| input.toLowerCase().contains("favorite")
				|| input.toLowerCase().contains("is"))
				response = "That's a cool Pokemon!";
		
		else if (stateHash.contains(input.toLowerCase())
				|| input.toLowerCase().contains("I")
				|| input.toLowerCase().contains("am")
				|| input.toLowerCase().contains("from"))
				response = "I've never been there before! Do they have cool Pokemon?";
		
		else if(input.toLowerCase().contains("yes"))
			response = "Cool.";

		else if(input.toLowerCase().contains("no"))
			response = "Oh ok.";

		//If input is unrecognized, return a random response.
		else response = randomResponse();

		return response;
	}

	
	//This generates a random response. It is called when the input from the user is unrecognized.
	private static String randomResponse(){
		Random rand = new Random();
		int randNum = rand.nextInt(6);

		if(randNum == 0) return "Do they have cool pokemon where you're from?";
		else if(randNum == 1) return "Want to battle?";
		else if(randNum == 2) return "I really like Pokemon! Let's talk more about that!";
		else if(randNum == 3) return "Are we still talking about Pokemon?";
		else if(randNum == 4) return "uuuhhhh I've never heard of that Pokemon";
		else return "I don't know what you're talking about. Let's keep this Pokemon oriented!";

	}
	
	//This function checks if an entered string is a number
	private static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	
	//This function is used to get out ENUMS and put them in hashmaps for quick searches
	public static void getEnums()
	{

		for (PokemonNames name : PokemonNames.values())
		{
			pokemonHash.add(name.name());
		}
		
		for (State stateName : State.values())
		{
			stateHash.add(stateName.name());
		}
	}

}