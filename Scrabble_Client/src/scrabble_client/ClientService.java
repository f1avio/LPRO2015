package scrabble_client;

import GUI.*;
import java.util.Arrays;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


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
            case 1:{
                break;
            }
            case 0:
                JOptionPane.showMessageDialog(null, "The server is full");
                break;
            case -1:
                JOptionPane.showMessageDialog(null, "Error");
                break;
        }
    }
    
    public void viewRooms(){
        protocol.sendViewRooms();
    }
    
    public void receiveRooms(String rooms){
        int rows = 0;
        int i = 0;
        int j = 0;
        int z = 0;
        int aux = 0;
        int r = 0;
        
        char Array[] = rooms.toCharArray();
        for(i=0; i< rooms.length(); i++){
            if(Array[i] == '/') rows++;
        }
        i=0;
        aux = rows;
        r =rows;
        
        String[] line = new String[rows];
        String[] field = new String[rows*4];
        
        while(rows != 0){
            line[j] = "";
            while(Array[i] != '/'){
                line[j] = line[j] + Array[i];
                i++;
            }
            line[j] = line[j] + "/";
            j++;
            i++;
            rows--;
        }
        j=0;
        i=0;
        z=0;
        while(aux != 0){
            field[i]= "";
            
            while(line[j].toCharArray()[z] != '&'){
                field[i] = field[i] + line[j].toCharArray()[z];
                z++;
            }
            z++;
            //System.out.println("Field[" + i + "] : " + field[i]);
            i++;
            
            field[i] = "";
            
            while(line[j].toCharArray()[z] != '&'){
                field[i] = field[i] + line[j].toCharArray()[z];
                z++;
            }
            z++;
            //System.out.println("Field[" + i + "] : " + field[i]);
            i++;
            
            field[i] = "";
            
            while(line[j].toCharArray()[z] != '&'){
                field[i] = field[i] + line[j].toCharArray()[z];
                z++;
            }
            z++;
            //System.out.println("Field[" + i + "] : " + field[i]);
            i++;
            
            field[i] = "";
            
            while(line[j].toCharArray()[z] != '/')
            {
                field[i] = field[i] + line[j].toCharArray()[z];
                z++;
            }
            //System.out.println("Field[" + i + "] : " + field[i]);
            i++;
            
            j++;
            z=0;
            aux--;
        }
        
        String[][] data = null;
        String[] colName = new String[4];
        
        colName = new String[] {"Room", "Players","Joinable","Room Owner"};
        data = new String[r][4];
        j=0;
        
        for(i=0;i < r ; i++)
        {
            data[i][0] = field[j];
            j++;
            
            if(Integer.parseInt(field[j]) > Integer.parseInt(field[j+1])){
                data[i][2] = "YES";}
            else data[i][2] = "NO";
            j++;
            
            data[i][1] = field[j];
            j++;
            
            data[i][3] = field[j];
            
            j++;
        }
        mainFrame.roomTable.setModel(new DefaultTableModel(data, colName));
    }
    
    public void updatePlayers(String[] players){
        mainFrame.player1.setText(players[0]);
        mainFrame.player2.setText(players[1]);
        mainFrame.player3.setText(players[2]);
        mainFrame.player4.setText(players[3]);
        mainFrame.player5.setText(players[0]);
        mainFrame.player6.setText(players[1]);
        mainFrame.player7.setText(players[2]);
        mainFrame.player8.setText(players[3]);
        //mainFrame.player1Status.setText(players[4]);
        mainFrame.player2Status.setText(players[5]);
        mainFrame.player3Status.setText(players[6]);
        mainFrame.player4Status.setText(players[7]);
        //mainFrame.player5Status.setText(players[4]);
        mainFrame.player6Status.setText(players[5]);
        mainFrame.player7Status.setText(players[6]);
        mainFrame.player8Status.setText(players[7]);
    }

    public String getPlayerState(char[] data, int index){
        int i = 0;
        int count = 0;
        String ret = "";
        
        while((index) != count){
            while(data[i] != '#'){
                i++;
            }
            i++;
            count++;  
        }
        while(data[i]!='#'){
        ret = ret + data[i];
            i++;
        }
        return ret; 
    }
        
    
    public void receiveJoin(String room){
        //System.out.println("receiveJoin() msg: "+msg);
        switch(room){
            case "":
                JOptionPane.showMessageDialog(null, "The room is full");
                break;
            case "KICK":
                JOptionPane.showMessageDialog(null, "KICK! You can't enter rooms!", "System Message", JOptionPane.ERROR_MESSAGE);
                break;    
            default:
                
                if(room.charAt(room.length() - 1) == '#'){
                    room = room.substring(0, room.length() - 1); // remove de final #
                    mainFrame.selectPage("roomOwnerP");   
                    protocol.sendUpdatePlayers(room);
                }
                else{
                    mainFrame.selectPage("roomP");
                    protocol.sendUpdatePlayers(room);
                }
        }
    }
    
    public void roomDeleted(String ans){
        switch(ans){
            case "ERROR":
                JOptionPane.showMessageDialog(null, "Quit Room Error: try again.");
                break;
            default:
                mainFrame.selectPage("mainP");
                viewRooms();
        }
    }
    
    public void sendChat(String user, String msg) {
        protocol.sendChat(user, msg);
    }
    
    public void receiveReady(String ans, String username){
        switch(ans){
            case "ERROR":
                JOptionPane.showMessageDialog(null, "Ready Error: try again!");
                break;
            case "ready":{
                if(ans.equals(mainFrame.player2.getText())){
                    mainFrame.player2Status.setText("Ready");
                    mainFrame.player6Status.setText("Ready");
                }
                if(ans.equals(mainFrame.player3.getText())){
                    mainFrame.player3Status.setText("Ready");
                    mainFrame.player7Status.setText("Ready");
                }
                if(ans.equals(mainFrame.player4.getText())){
                    mainFrame.player5Status.setText("Ready");
                    mainFrame.player8Status.setText("Ready");
                }
                break;
            }
            case "wait":{
                if(ans.equals(mainFrame.player2.getText())){
                    mainFrame.player2Status.setText("Wait");
                    mainFrame.player6Status.setText("Wait");
                }
                if(ans.equals(mainFrame.player3.getText())){
                    mainFrame.player3Status.setText("Wait");
                    mainFrame.player7Status.setText("Wait");
                }
                if(ans.equals(mainFrame.player4.getText())){
                    mainFrame.player5Status.setText("Wait");
                    mainFrame.player8Status.setText("Wait");
                }
                break;
            }
        }               
    }
    
    public void requestRanking(){
        protocol.sendRanking();
    }
    
    public void receiveRanking(String msg){
        String[] columnNames = new String[]{"Position", "Username", "Points", "Wins", "Loses"};  
        DefaultTableModel tableModel = new DefaultTableModel(null,columnNames);
        String player[] = msg.split(",");
        for(int i = 1; i < Integer.parseInt(player[0]); i++){
            tableModel.addRow(player[i].split("|"));
        }
        
        mainFrame.ranking.setModel(tableModel);
    }
    public void ready(String username, String room){
        protocol.sendReady(username, room);
    }    
    public void receiveChat(String user, String msg) {
        mainFrame.chatarea.append(user + " :" + msg + "\n");
    }
    
    public void joinRoom(String roomName){
        protocol.sendJoinRoom(roomName);
    }
    
    public void createRoom(int nPlayers, String owner, String room){
        protocol.sendCreateRoom(nPlayers, owner, room);
    }

    public void quitRoom(String username, String room){
        protocol.sendQuitRoom(username, room);
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
