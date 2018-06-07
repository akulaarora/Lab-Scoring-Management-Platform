
/**
 * OldMacDonald uses various classes to simulate the animals from OldMacDonald.
 *
 * @author Akul Arora
 * @version 1.0
 */
public class Driver
{
    public static void main(String[] args)
    {
        // Instantiate objects
        Cow c = new Cow("cow", "moo");
        Chick ch = new Chick("chick", "cheep", "cluck");
        Pig p = new Pig("pig", "oink");
        Farm f = new Farm();

        // Print output
        System.out.println( c.getType() + " goes " + c.getSound() );
        System.out.println( ch.getType() + " goes " + ch.getSound() );
        System.out.println( p.getType() + " goes " + p.getSound() );
        f.animalSounds();
    }
}
