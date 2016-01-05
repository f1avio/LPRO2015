package scrabble_client;

import GUI.*;
import java.util.Arrays;
import java.util.Collections;
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
    public String[] messages;
    
    /**
     * Starts a new thread to the client.
     */
    private ClientService(){
        clientThread = new Thread(protocol);
        clientThread.start();
    }
    
    private static ClientService instance = null;
    
    /**
     * Creates a new instance of this class.
     * @return The new instance.
     */
    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }
    /**
     * Receives and warns the client about the status of the operation.
     * @param msg The status of the operation of sign up.
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
    /**
     * Creates a new request to sign up a new user.
     * @param username A unique username he wants to use in the game.
     * @param password His password.
     * @param email His email.
     */
    public void signupRequest(String username, String password, String email){
        MD5 hash = new MD5();
        protocol.sendSignup(username, hash.convert(password), email);
    }
    
    /**
     * Transmit to the client the results of his attempt lo log into the system.
     * @param username The username that identifies the client, if his attempt to login is successful.
     * @param msg The status of the operation.
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
                JOptionPane.showMessageDialog(null, "You'r account is banned indefinitely! Access denied!\n");
                InitialFrame Iframe = new InitialFrame("loginP");
                Iframe.setVisible(true);
                break;}
        }
    }
    
   /**
    * Sends a request to the system to log the user.
    * @param user The username that identifies him.
    * @param password His password.
    */
    public void loginRequest(String user, String password){
        MD5 hash= new MD5();
        protocol.sendLogin(user,hash.convert(password) );
    }
    
    /** Verifies if the email address is a valid one.
     * @param email the email address to be verified.
     * @return a boolean indicating the email's validaty.
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
     * Transmit to the client the results of his attempt lo log into the system.
     * @param msg The status code of the operation.
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
    
    /**
     * Transmit to the client the results of his attempt change his password.
     * @param state The status code of the operation. 
     */
    public void receiveChange(String state){
        switch(state){
            case "OK":
                JOptionPane.showMessageDialog(null, "Changes Done");
                mainFrame.selectPage("mainP");
                break;
            case "FAIL":
                JOptionPane.showMessageDialog(null, "Wrong Password");
                break;
            case "ERROR":
                JOptionPane.showMessageDialog(null, "Error, try again");
                break;
        }
    }
    
    /**
     * Transmit to the client the results of his attempt to create a new room.
     * @param msg The status code of the operation.
     */
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
    
    /**
     * Return the username of the client.
     * @return The username.
     */
    public String getUsername(){
        return name;
    }
    
    /**
     * Transforms a list into a table.
     * @param list The list that will be inserted into a table.
     */
    public void receiveList(String list){
        String[] users = list.split("/");
        DefaultTableModel table = (DefaultTableModel) mainFrame.playerList2.getModel();
        for(int k=0; k < users.length; k++){
            table.addRow(new Object[]{users[k]});//missing get points
            table.fireTableRowsInserted(table.getRowCount(),table.getRowCount());
            }
    }
    
    /**
     * Shows the available rooms that exist in the server.
     */
    public void viewRooms(){
        protocol.sendViewRooms();
    }
    
    /**
     * Receives the available rooms and structures them in order to be displayed.
     * @param rooms The rooms available.
     */
    public void receiveRooms(String rooms){
        int rows = 0;
        int i;
        int j = 0;
        int z;
        int aux;
        int r;
        
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
    
    /**
     * Updates the players that are inside a room.
     * @param players An array of string with all the players.
     */
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

    /**
     * Returns the state of a certain player.
     * @param data An array with all the players data.
     * @param index The position of the player in the array.
     * @return The player state.
     */
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
    
    /**
     * Sends a new request to change the password of a user.
     * @param actualPassword The actual password of the user.
     * @param password The password he intends to use.
     * @param email His email.
     * @param username The username that identifies the user.
     */
    public void changeRequest(String actualPassword, String password, String email, String username){
        MD5 hash = new MD5();
        protocol.sendChange(hash.convert(actualPassword), hash.convert(password), email, username);
    }
    
    /**
     * Transmits to the client the results of his attempt to join a new room.
     * @param room The status code of the operation.
     */
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
    /**
     * Transmits to the client the results of his attempt to delete a room.
     * @param ans The status code of the operation.
     */
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
    
    /**
     * Routes the message of the user
     * @param user The user that sends the message.
     * @param msg The message that will be transmitted.
     */
    public void sendChat(String user, String msg) {
        protocol.sendChat(user, msg);
    }
    
    /**Transmits to the client the results of his attempt to change its state in the room.
     * @param ans The player's username.
     */
    public void receiveReady(String ans){
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
    /**
     * Sends a new request to vizualize the rankings.
     */
    public void requestRanking(){
        protocol.sendRanking();
    }
    /**
     * Shows the ranking to the user.
     * @param msg The full list of registered users.
     */
    public void receiveRanking(String msg){
        String[] columnNames = new String[]{"Position", "Username", "Points", "Wins", "Loses"};  
        DefaultTableModel tableModel = new DefaultTableModel(null,columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        msg = msg.substring(1, msg.length()-1);
        String player[] = msg.split(",");
        int registedPlayers = Integer.parseInt(player[0]);
        for(int i = 1; i < registedPlayers+1; i++){  
            String row[] = player[i].split("/", 5);
            tableModel.addRow(row);
        }
        mainFrame.ranking.setModel(tableModel);
    }
    /**
     * Signals the system that the player is ready to start the game.
     * @param username The player's username.
     * @param room The room where he is placed.
     */
    public void ready(String username, String room){
        protocol.sendReady(username, room);
    }

    
    public void receiveChat(String user, String msg) {
        mainFrame.chatarea.append(user + " :" + msg + "\n");
    }
    /**
     * Signals the system that the intends to join a certain room.
     * @param roomName The room where the player will join.
     */
    public void joinRoom(String roomName){
        protocol.sendJoinRoom(roomName);
    }
    /**
     * Signals that user attempt to create a new room.
     * @param nPlayers The number of players the room will have.
     * @param owner The player's username, who will be the room owner/host.
     * @param room The name that identifies the room.
     */
    public void createRoom(int nPlayers, String owner, String room){
        protocol.sendCreateRoom(nPlayers, owner, room);
    }
    /**
     * Signals the user attempt to quit a room.
     * @param username The user identifier.
     * @param room The which he wants to quit.
     */
    public void quitRoom(String username, String room){
        protocol.sendQuitRoom(username, room);
    }
    /**
     * Signals the system  the user's attempt to log out.
     * @param username The user's identifier.
     */
    public void logoutRequest(String username){
        protocol.sendLogout(username);
    }
    /**
     * Sets the MainFrame instance of to which a instance will be connected to.
     * @param main The MainFrame instance.
     */
    public void setMainFrame(MainFrame main){
        mainFrame = main;
    }
    /**
     * Sets the Gamegui instance of to which a instance will be connected to.
     * @param gamegui The GameGui instance.
     */
    public void setGameGui(GameGUI gamegui){
        gameGui = gamegui;
    }
    
   /**
    * Sends message to start the game.
    * @param room The room where the game will start.
    */ 
   public void sendStart(String room){
        protocol.sendStart(room);
   }
   
    /**
     * Receives the condition to see if it can start a game.
     * @param status The condition to start the game.
     */
    public void receiveGame(int status)
    {
        if(-1 == status)
            JOptionPane.showMessageDialog(null,"Someone wasn't ready at time!","Error",JOptionPane.ERROR_MESSAGE);
        else
            startRoom();
    }
   
    /**
     * Starts the graphical interface of a room.
     */
    public void startRoom(){
    //public void startRoom(String room){
        GameGUI gameG = new GameGUI();
        gameG.setVisible(true);
    }
    
    /**
     * Exits the graphical interface of the game instance.
     */
    public void exitGame(){
        gameGui.setVisible(false);
    }
    
    /**
     * Requests the system to send a message on the user behalf.
     * @param user The user identifier.
     * @param friend The user that will receive the message.
     * @param text The content of the message.
     */
    public void sendMessageRequest(String user, String friend, String text) {        
        protocol.sendMessage(user, friend,text);
    }
    
    public void display(){
        protocol.requestDisplay();
    }

    public void requestMsglist(String username) {
        protocol.sendMsgList(username);
    }
    /**
     * Receives the list of messages.
     * @param msgList The list of messages.
     */
    void receiveMsgList(String msgList) {
        String Nr_text= mainFrame.msgsLabel.getText();
        int nr_msgs= Integer.parseInt(Nr_text);
        if(nr_msgs>0){
            messages = new String[nr_msgs];        
            messages = msgList.split("«", nr_msgs);
            messages[nr_msgs-1] = messages[nr_msgs-1].substring(0, messages[nr_msgs-1].length()-1); // remove « from the last message
            Collections.reverse(Arrays.asList(messages));       // reverse of the array
        }
    }
    
    void receiveNRMSGS(String NR) {
        mainFrame.msgsLabel.setText(NR);   
    }
    
    public void NrMsgRequest(String user) {
          protocol.sendNrMsg(user); //To change body of generated methods, choose Tools | Templates.
    }
}
