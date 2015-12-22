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
            //System.out.println("sendLogin()-> OK");
        } catch (IOException ex) {
            System.err.println("Couldn't get I/O for the connection to " + hostname);
        }
    }
    
    public void sendSignup(String username, String password, String email){
        try{
            String signup = "SIGNUP" + SPACER + username + SPACER + password + SPACER + email + SPACER;
            dataOut.writeUTF(signup);
            System.out.println("sendSignup()-> OK");
        } catch (IOException ex) {
            System.out.println("sendRegist() " + ex);
        }
    }
    
    public void sendLogout(String username){
        try {
            String logout = "LOGOUT" + SPACER + username + SPACER;
            dataOut.writeUTF(logout);
            System.out.println("sendLogout()-> OK");
        } catch (IOException ex) {
            System.out.println(" sendLogout() " + ex);
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
    
    public void sendCreateRoom(int nPlayers, String owner){
        try{
            String create = "CREATEROOM" + SPACER + nPlayers + SPACER + owner + SPACER; 
            dataOut.writeUTF(create);
        } catch (IOException ex) {
            System.out.println("sendCreateRoom() " + ex);
        }
    }
    
    public void sendJoinRoom(String room){
        try{
            String join = "JOINROOM" + SPACER + room + SPACER;
            dataOut.writeUTF(join);
        } catch (IOException ex) {
             System.out.println("sendJoinRoom() " + ex);
        }
    }
    
    public void sendQuitRoom(String username, String room){
        try{
            String quit = "QUITROOM" + SPACER + username + SPACER + room + SPACER;
            dataOut.writeUTF(quit);
        } catch (IOException ex) {
            System.out.println("sendQuitRoom() " + ex);
        }
    }
    
    public void sendViewRooms(){
        try {
            String view = "VIEWROOMS" + SPACER;
            dataOut.writeUTF(view);
        } catch (IOException ex) {
            System.out.println("sendViewRooms() " + ex);
        }
    }
    

    @Override
    public void run() {
        System.out.println("[ClientProtocol -> Thread");
        boolean threadRunnig = true;
        String type = "";     //Comand type
        String ans = "";      //Server response
        ClientService clientService = ClientService.getInstance();
        
        while(threadRunnig){
            
            try{
                String msg = dataIn.readUTF();
                char[] data = msg.toCharArray();
                System.out.println("ClientProtocol dataIn: "+ new String(data));
                type = findType(data);
                System.out.println("ClientProtocol type: " + type);
                switch(type){
                    case "LOGIN":
                        ans = findMessage(data, 6,1);
                        System.out.println("ClientProtocol ans: " + ans);
                        String username="";
                        String aux="";
                        
                        switch(ans){
                            case "OK":
                                aux = findMessage(data, 6, 2);
                                switch(aux){
                                    case "NORMAL":
                                        //System.out.println("ClientProtocol: LOGIN#OK#NORMAL#");
                                        username = findMessage(data, 6, 3);
                                        clientService.receiveLogin(username, 1);
                                        System.out.println("ClientProtocol: User login success" );
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
                        
                    case "LOGOUT":
                        ans = findMessage(data, 7, 1);
                        System.out.println("ClientProtocol "+ans);
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
                    case "SIGNUP":
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
                    case "CREATEROOM":
                        ans = findMessage(data,11,1);
                        System.out.println("ans: "+ans);
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
                    case "JOINROOM":
                        ans = findMessage(data, 9, 1);
                        String room = "";
                        
                        switch(ans){
                            case "OK":
                                String roomName = findMessage(data, 9, 2);
                                clientService.receiveJoin(roomName);
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
                    case "ROOMS":
                        String rooms = findMessage(data,6,1);
                        clientService.receiveRooms(rooms);
                        break;
                    case "QUITROOM":
                        
                        
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("[ClientProtocolThread] IOExcepetion");
            }
        }
        System.out.println("TESTE final thread");
    }
    
    /*
    *
    */

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
