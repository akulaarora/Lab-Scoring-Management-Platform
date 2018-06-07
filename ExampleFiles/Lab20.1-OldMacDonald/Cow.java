
/**
 * Class Cow is for the cow animal and implements the Animal interface.
 *
 * @author Akul Arora
 * @version 1.0
 */
public class Cow implements Animal
{
    // instance variables
    private String myType; // String of type of animal
    private String mySound; // String of sound cow makes
    
    /**
     * Default constructor for Cow class
     * @param String type (of animal), String sound (sound made)
     */
    public Cow(String type, String sound)
    {
        myType = type;
        mySound = sound;
    }
    /**
     * Gets sound made
     * @return String sound
     */
    public String getSound() { return mySound; }
    /**
     * Gets type
     * @return String type
     */
    public String getType() { return myType; } 
}
