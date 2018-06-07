
/**
 * Class Chick is for the chicken animal and implements the Animal interface.
 *
 * @author Akul Arora
 * @version 1.0
 */

import java.util.Random;

public class Chick implements Animal
{
    // instance variables
    private String myType; // String of type of animal
    private String[] mySound; // String of sound chick makes

    /**
     * Default constructor for Chick class
     * @param String type (of animal), String sound (sound made)
     */
    public Chick(String type, String sound)
    {
        myType = type;
        mySound = new String[2];
        mySound[0] = sound;
        mySound[1] = "";
    }
    
    /**
     * Constructor for Chick class that takes two sounds.
     * @param String type (of animal), String sound (sound made), String sound2 (second sound made)
     */
    public Chick(String type, String sound, String sound2)
    {
        myType = type;
        mySound = new String[2];
        mySound[0] = sound;
        mySound[1] = sound2;
    }
    
    /**
     * Gets sound made. Will return the sound if only one was passed, or
     * will randomly return one of the two sounds provided.
     * @return String sound
     */
    public String getSound()
    { 
        // Instantiate objects
        Random rand = new Random();
        
        // If only one sound exists
        if (mySound[1].equals(""))
            return mySound[0];
        // Otherwise randomly pick one
        else
            return mySound[rand.nextInt(2)];
    }
    
    /**
     * Gets type
     * @return String type
     */
    public String getType() { return myType; } 
}
