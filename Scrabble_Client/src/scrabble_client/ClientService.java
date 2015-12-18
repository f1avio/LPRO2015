package scrabble_client;

import GUI.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JOptionPane;


/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */
public class ClientService {
    ClientProtocol protocol = ClientProtocol.getInstance();
    public Thread clientThread;
    GameGUI gameGui;
    MainFrame mainFrame;
    String name;
    
    private ClientService(){
        clientThread = new Thread(protocol);
        clientThread.start();
    }
    
    private static ClientService instance = null;
    
    /*
    *
    */
    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }
    /**Passes the command and the necessary arguments to the server to perform a sign up
     * @param username The user username within the game
     * @param password The password needed to login into the system
     * @param email The user email, for administrative tasks
     * @return  a standardized status value, notifying the degree of success of the implementation
     */
    public void receiveSignup(int msg){
        switch (msg) {
            case 1: {
                JOptionPane.showMessageDialog(null, "Regist Successful");
                InitialFrame Iframe = new InitialFrame("loginP");
                Iframe.setVisible(true);
                break;
            }
            case 0: {
                JOptionPane.showMessageDialog(null, "Username already in use");
                InitialFrame Iframe = new InitialFrame("signupP");
                Iframe.setVisible(true);
                break;
            }
            default: {
                JOptionPane.showMessageDialog(null, "Error");
                InitialFrame Iframe = new InitialFrame("loginP");
                Iframe.setVisible(true);
                break;
            }
        }
    }
    
    public void signupRequest(String username, String password, String email){
        MD5 hash = new MD5();
        protocol.sendSignup(username, hash.convert(password), email);
    }
    /**
     * Passes the command and the necessary arguments to the server to perform a log in
     * @param username the user's name that needs to log in
     * @param password the user's password
     * @return a standardized status value, notifying the degree of success of the implementation
     */
    public void receiveLogin(String username, int msg){
        switch(msg){
            case 1:{
                MainFrame mainframe = new MainFrame(username);
                name = username;
                mainframe.setVisible(true);
                break;}
            case 0:{
                JOptionPane.showMessageDialog(null, "Username or Password incorrect.");
                InitialFrame Iframe = new InitialFrame("loginP");
                Iframe.setVisible(true);
                break;}
            case 2: {
                JOptionPane.showMessageDialog(null, "You have already logged in.");
                InitialFrame Iframe = new InitialFrame("loginP");
                Iframe.setVisible(true);
                break;
            }
            case -1: {
                JOptionPane.showMessageDialog(null, "Error");
                InitialFrame Iframe = new InitialFrame("loginP");
                Iframe.setVisible(true);
                break;

            }
            case 3:{
                JOptionPane.showMessageDialog(null, "A sua conta está banida indefinidamente! Login não foi efectuado!\n");
                InitialFrame Iframe = new InitialFrame("loginP");
                Iframe.setVisible(true);
                break;}
        }
    }
   
    public void loginRequest(String user, String password){
        MD5 hash= new MD5();
        protocol.sendLogin(user,hash.convert(password) );
    }
    
    /** Verifies if the email address is a valid one
     * @param email the email address to be verified
     * @return a boolean indicating the email's validaty
     */
    public static boolean isValidEmailAddress(String email) {
   boolean result = true;
   try {
      InternetAddress emailAddr = new InternetAddress(email);
      emailAddr.validate();
   } catch (AddressException ex) {
      result = false;
   }
   return result;
}
    
    /**
     * Passes the command and the necessary arguments to the server to perform a logout
    */
    public void receiveLogout(int msg) 
    {
        switch (msg) {
            case 1: {
                instance = null;
                InitialFrame Iframe = new InitialFrame("loginP");                
                Iframe.setVisible(true);

                break;
            }
            case 0: {
                JOptionPane.showMessageDialog(null, "Logout Failed");
                break;
            }
            case -1: {
                JOptionPane.showMessageDialog(null, "Error");
                break;

            }
        }
    }
    
    public void receiveCreateRoom(int msg){
        switch(msg){
            case 1:
                break;
            case 0:
                JOptionPane.showMessageDialog(null, "Servidor atingiu o limite de mesas");
                break;
            case -1:
                JOptionPane.showMessageDialog(null, "Error");
                break;
        }
    }
    
    public void createRoom(int nPlayers){
        protocol.sendCreateRoom(nPlayers);
    }
    
    public void logoutRequest(String username){
        protocol.sendLogout(username);
    }
    
    public void setMainFrame(MainFrame main){
        mainFrame = main;
    }
    
    public void setGameGui(GameGUI gamegui){
        gameGui = gamegui;
    }
}
