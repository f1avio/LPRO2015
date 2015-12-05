package scrabble_client;

/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */
public class User {  /*It will be used later on to more complicated things*/
    private String name =null;
    /**
     * Stores the username of the newly logged in user
     * @param name The username to be stored
     */
    public void setName(String name)
    {
     this.name = name;
    }
    
    /**
     * Retrieves the stored name
     * @return the stored name
     */
    public String getName ()
    {
        return name;
    }
    
}
