/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author HUGUETA
 */
public class ClientProtocol implements Runnable {
    
    private Socket clientSocket = null;
    private DataOutputStream dataOut = null;
    private DataInputStream dataIn = null;
    static String hostname = "localhost";
    static int port = 1513;
    private static ClientProtocol instance = null;
    private final String SPACER = "#";
    
    
    
    private ClientProtocol(){
        final JPanel frame = new JPanel();
        readServer();
        try {
            System.out.println(hostname);
            System.out.println(port);
            clientSocket = new Socket(hostname, port);
            dataOut = new DataOutputStream(clientSocket.getOutputStream());
            dataIn = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Error: Server or Port");
        }
        
    }
    
    public static ClientProtocol getInstance(){
        if(instance == null){
            instance = new ClientProtocol();
        }
        return instance;
    }
    
    public void sendLogin(String username, String password){
        try{
            String login = "LOGIN" + SPACER + username + SPACER + password + SPACER;
            dataOut.writeUTF(login);
            System.out.println("<< Sending: " + login);
        } catch (IOException ex) {
            System.err.println("Couldn't get I/O for the connection to " + hostname);
        }
    }
    
    public void sendSignup(String username, String password, String email){
        try{
            String signup = "SIGNUP" + SPACER + username + SPACER + password + SPACER + email + SPACER;
            dataOut.writeUTF(signup);
            System.out.println("<< Sending: " + signup);
        } catch (IOException ex) {
            System.out.println("sendRegist() " + ex);
        }
    }
    
    public void sendLogout(String username){
        try {
            String logout = "LOGOUT" + SPACER + username + SPACER;
            dataOut.writeUTF(logout);
            System.out.println("<< Sending: " + logout);
        } catch (IOException ex) {
            System.out.println(" sendLogout() " + ex);
        }
    }
    
    public void sendChange(String actualPassword, String newPass, String email, String username){        
        try {
            String send = "CHANGE#"+actualPassword+SPACER+newPass+SPACER+email+SPACER+username+SPACER;
            System.out.println("<< Sending: " + send);
            dataOut.writeUTF(send);
        } catch (IOException ex) {
            System.out.println(" sendChange() " + ex);
        }
    }
    
    /**
    * Constructor that reads the config.txt file to set parameters to the connection
    */
    public static void readServer(){
        /*Attempts to read a configuration file */
        /*Step 0: Initialize the files*/
        BufferedReader inputStream = null;
        int i = 0;
        String aux[] = new String[3];
        String file = "config.txt";
        try {
            inputStream = new BufferedReader(new FileReader(file));
            while ((aux[i] = inputStream.readLine()) != null) {
                i++;
            }
            port = Integer.parseInt(aux[0]);
            hostname = aux[1];
            
        } catch (FileNotFoundException f){
            System.err.println("Caught FileNotFoundException: " + f.getMessage());
        } catch (IOException ex) {
            System.err.println("Caught IOException: " + ex.getMessage());
        } 
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    System.err.println("Caught IOException: " + ex.getMessage());
                }
            }
        }
    }
    
    public void sendCreateRoom(int nPlayers, String owner, String room){
        try{
            String create = "CREATEROOM" + SPACER + nPlayers + SPACER + owner + SPACER + room + SPACER;
            dataOut.writeUTF(create);
            System.out.println("<< Sending: " + create);
        } catch (IOException ex) {
            System.out.println("sendCreateRoom() " + ex);
        }
    }
    
    public void sendJoinRoom(String room){
        try{
            String join = "JOINROOM" + SPACER + room + SPACER;
            dataOut.writeUTF(join);
            System.out.println("<< Sending: " + join);
        } catch (IOException ex) {
             System.out.println("sendJoinRoom() " + ex);
        }
    }
    
    public void sendQuitRoom(String username, String room){
        try{
            String quit = "QUITROOM" + SPACER + username + SPACER + room + SPACER;
            dataOut.writeUTF(quit);
            System.out.println("<< Sending: " + quit);
        } catch (IOException ex) {
            System.out.println("sendQuitRoom() " + ex);
        }
    }
    
    public void sendViewRooms(){
        try {
            String view = "VIEWROOMS" + SPACER;
            dataOut.writeUTF(view);
            System.out.println("<< Sending: " + view);
        } catch (IOException ex) {
            System.out.println("sendViewRooms() " + ex);
        }
    }
    
    public void sendChat(String username, String msg) {
        try {
            String chat = "CHAT" + SPACER + username + SPACER + msg + SPACER;
            dataOut.writeUTF(chat);
            System.out.println("<< Sending: " + chat);
        } catch (IOException ioe) {
            System.out.println("[Client][Protocol]" + " sendChat " + ioe);
        }
    }

    public void sendUpdatePlayers(String room){
        try{
            String update = "UPLAYERS" + SPACER + room + SPACER;
            dataOut.writeUTF(update);
            System.out.println("<< Sending: " + update);
        } catch (IOException ex){
            System.out.println("sendUpdatePlayers() " + ex);
        }
    }
    
    public void sendReady(String username, String room){ 
        try {
            String ready = "READY" + SPACER + username + SPACER + room + SPACER;
            dataOut.writeUTF(ready);
            System.out.println("<< Sending: " + ready);
        } catch (IOException ex) {
            System.out.println("sendReady() " + ex);
        }
    }
    
    public void sendRanking() {
        try {
            String rank = "RANKING" + SPACER;
            System.out.println("<< Sending: " + rank);
            dataOut.writeUTF(rank);            
        } catch(IOException ex) {
            System.out.println("sendRanking() " + ex);
        }
    }
    
    public void sendMessage(String username, String friend, String texto){
     try {
            String log = "PRIVATE" + SPACER + username + SPACER + friend + SPACER + texto + SPACER;
            dataOut.writeUTF(log);
        } catch (IOException ioe) {
            System.out.println("[Client][Protocol]" + " sendMessage " + ioe);
        }    
    }
    
    public void requestDisplay(){
        String msg = "DISPLAY#";
        try{
            dataOut.writeUTF(msg);
            System.out.println("<< Sending: " + msg);
        } catch(IOException ex){
            System.out.println("requestDisplay() " + ex);
        } 
    }
    
    public void sendMsgList(String user) {

        try {
            String log = "MSGLIST" + SPACER + user + SPACER;
            System.out.println("<< Sending: " + log);
            dataOut.writeUTF(log);
        } catch(IOException ioe) {
            System.out.println("sendMsgList() " + ioe);
        }
    }
    
    public void sendNrMsg(String user) {
        try {
            String log = "MSGNR" + SPACER + user + SPACER;
            System.out.println("<< Sending: " + log);
            dataOut.writeUTF(log);
        } catch(IOException ex) {
            System.out.println("sendNrMsg() " + ex);
        }
    }
    
    @Override
    public void run() {
        System.out.println("[ClientProtocol -> Thread");
        boolean threadRunnig = true;
        String type = "";     //Comand type
        String ans = "";      //Server response
        String usernameChat = "";
        String messageChat = "";
        ClientService clientService = ClientService.getInstance();
        
        while(threadRunnig){
            
            try{
                String msg = dataIn.readUTF();
                char[] data = msg.toCharArray();
                System.out.println(">> Received: "+ new String(data));
                type = findType(data);
                //System.out.println("ClientProtocol type: " + type);
                switch(type){
                    case "LOGIN":{
                        ans = findMessage(data, 6,1);
                        //System.out.println("ClientProtocol ans: " + ans);
                        String username="";
                        String aux="";
                        
                        switch(ans){
                            case "OK":
                                aux = findMessage(data, 6, 2);
                                switch(aux){
                                    case "NORMAL":
                                        username = findMessage(data, 6, 3);
                                        clientService.receiveLogin(username, 1);
                                        break;
                                    case "ADMIN":
                                        break;
                                }
                                break;
                            case "FAIL":
                                aux = findMessage(data, 6, 2);
                                switch(aux){
                                    case "NORMAL":
                                        clientService.receiveLogin(username, 0);
                                        break;
                                    case "BAN":
                                        clientService.receiveLogin(username, 3);
                                        break;
                                }
                                break;
                            case "EXIST":
                                clientService.receiveLogin(username, 2);
                                break;
                            default:
                                clientService.receiveLogin(username, 0);
                        }
                        break;
                    }   
                    case "LOGOUT":{
                        ans = findMessage(data, 7, 1);
                        //System.out.println("ClientProtocol "+ans);
                        switch(ans){
                            case "OK":
                                instance = null;
                                threadRunnig = false;
                                clientService.receiveLogout(1);
                                break;
                            case "FAIL":
                                clientService.receiveLogout(0);
                                break;
                            default:
                                clientService.receiveLogout(0);
                                break;     
                        }
                        break;
                    }
                    case "SIGNUP":{
                        ans = findMessage(data, 7, 1);
                        switch(ans){
                            case "OK":
                                clientService.receiveSignup(1);
                                break;
                            case "FAIL":
                                clientService.receiveSignup(0);
                                break;
                            default:
                                clientService.receiveSignup(2);
                        }
                        break;
                    }
                    case "CHANGE":{
                        String state = findMessage(data, 7, 1);
                        clientService.receiveChange(state);
                        break;}
                    case "CREATEROOM":{
                        ans = findMessage(data,11,1);
                        //System.out.println("[CREATEROOM] ans: "+ans);
                        switch(ans){
                            case "OK":
                                clientService.receiveCreateRoom(1);
                                break;
                            case "FAIL":
                                clientService.receiveCreateRoom(0);
                                break;
                            default:
                                clientService.receiveCreateRoom(2);
                        }
                        break;
                    }
                    case "JOINROOM":{
                        ans = findMessage(data, 9 ,1);
                        String room = "";
                        switch(ans){
                            case "OWNER":
                                room = findMessage(data, 9, 2);
                                clientService.receiveJoin(room+"#"); // +# distinguish the Owner
                                break;
                            case "OK":
                                room = findMessage(data, 9, 2);
                                clientService.receiveJoin(room);
                                break;
                            case "FAIL":
                                clientService.receiveJoin("");
                                break;
                            case "ADMIN":
                                clientService.receiveJoin("KICK");
                                break;
                            default:
                                clientService.receiveJoin("");
                                
                        }
                        break;
                    }
                    case "ROOMS":{
                        String rooms = findMessage(data,6,1);
                        clientService.receiveRooms(rooms);
                        break;
                    }
                    case "CHAT":{
                        usernameChat = findMessage(data,5,1);
                        messageChat = findMessage(data,5, 2);
                        clientService.receiveChat(usernameChat, messageChat);
                        break; 
                    }
                    case "UPLAYERS":{        //UPDATE room players
                        String[] players = new String[8];
                        for(int h=1; h<9; h++){
                            if(!"NULL".equals(findMessage(data,9,h))){
                                players[h-1]=findMessage(data,9,h);
                            }
                            else{
                                players[h-1]="";
                            }
                        }
                        //System.out.println("players: "+Arrays.toString(players));
                        clientService.updatePlayers(players);
                        break;
                    }
                    case "QUITROOM":{                       
                        ans = findMessage(data, 9, 1);
                        clientService.roomDeleted(ans);
                        break;
                    }
                    case "READY":{
                        ans = findMessage(data, 6, 1);
                        String username = findMessage(data, 6, 2);
                        clientService.receiveReady(ans, username);
                        break; 
                    }
                    case "RANKING":{
                        ans = findMessage(data, 8, 1);
                        clientService.receiveRanking(ans);
                        break;
                    }
                    case "DISPLAY":{
                        ans = findMessage(data, 8, 1);                        
                        clientService.receiveList(ans);
                        break;
                    }
                    case "MSGLIST":{
                       String msgList=findMessage(data,8,1);
                       clientService.receiveMsgList(msgList);
                       break;}
                    case "MSGNR":{
                        String NR = findMessage(data,6, 1);
                        clientService.receiveNRMSGS(NR);
                        break;}
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("[ClientProtocolThread] IOExcepetion");
            }
        }
        System.out.println("TESTE final thread");
    }
    
    /**
     *
     * @param data contains the data to obtain the message type
     * @return
     */

    public String findType(char data[]){
        int i= 0;
        String type="";
        while (data[i] != '#') {
            type = type + data[i];
            i++;
        }
        
        return type;
    }
    
    public String findMessage(char data[],int i,int arg ){
        String message ="";
        int j=1;
        
        if(arg>1){
        while(j!=arg){
        while (data[i] != '#') {
            i++;
        }
        j++;
        i++;
        }
        }
        while (data[i] != '#') {
            message = message + data[i];
            i++;
        }
        return message;
    }
}
