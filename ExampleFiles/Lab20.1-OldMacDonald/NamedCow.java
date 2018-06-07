
/**
 * NamedCow is a cow animal that has a a name as well.
 *
 * @author Akul Arora
 * @version 1.0
 */
public class NamedCow extends Cow
{
    // instance variables
    private String myName; // name of the cow
    
    /**
     * Default constructor for NamedCow
     * @param String type (of animal), String name (name of animal), String sound (sound made)
     */
    public NamedCow(String type, String name, String sound)
    {
        super(type,sound);
        myName = name;
    }
    
    /**
     * Returns the name of the cow.
     * @return String name
     */
    public String getName() { return myName; }
}
