import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//import org.ini4j.Ini;


public class Preferences {

	private final static String kPreferencesPath = "collapse/preferences.ini";
	
	public Preferences()
	{
		
	}
	
    public static void getPreferenceSize() throws IOException
    {   
//        Ini ini = new Ini();
//        
//        ini.load(new FileReader(new File(kPreferencesPath)));
//        Ini.Section section = ini.get("Board Size");
//        int prefSize = Integer.parseInt(section.get("small"));
//        
//        return prefSize;
    }
}