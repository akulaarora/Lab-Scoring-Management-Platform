
/**
 * Farm class is a class designed to simulate a farm.
 *
 * @author Akul Arora
 * @version 1.0
 */
public class Farm
{
    // instance variables
    private Animal[] a = new Animal[3];
    
    /**
     * Default constructor for farm.
     * Will create a NamedCow, Chick, and Pig.
     * @return none
     */
    public Farm()
    {
        a[0] = new NamedCow("cow","Elsie","moo");
        a[1] = new Chick("chick","cheep","cluck");
        a[2] = new Pig("pig","oink"); 
    }
    
    /**
     * Prints all animals sounds and the cow's name.
     * @return none
     * @param none
     */
    public void animalSounds()
    {
        for (int i = 0; i < a.length; i++)
        {
            System.out.println(a[i].getType() + " goes " + a[i].getSound());
        }
        System.out.println("The cow is known as " + ((NamedCow)a[0]).getName());
    }
}
