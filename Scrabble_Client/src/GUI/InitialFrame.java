/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import scrabble_client.*;

/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */

public class InitialFrame extends javax.swing.JFrame {

    static private String page = "homeP";
    static private String newUsername;
    static String identifier;  //mudar para uma int
    static InitialFrame f = null;
    //static User user = new User();
    
    
    /**
     * Creates new form InicialFrame
     * @param selectedPage Stores the string with the name of the windows do present
     */
    public InitialFrame(String selectedPage) {
        initComponents();
        InitialFrame.page = selectedPage;
        selectPage(page);
        
    }

    public InitialFrame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverConfig = new javax.swing.JDialog();
        portField = new javax.swing.JTextField();
        serverField = new javax.swing.JTextField();
        serverLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        confirmB = new javax.swing.JButton();
        MainPanel = new javax.swing.JPanel();
        MenuBarPanel = new javax.swing.JPanel();
        signupPageB = new javax.swing.JButton();
        homePageB = new javax.swing.JButton();
        loginPageB = new javax.swing.JButton();
        helpPageB = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        pagesPanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        loginPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        passInputText = new javax.swing.JPasswordField();
        userInputText = new javax.swing.JTextField();
        remembermeB = new javax.swing.JCheckBox();
        passLabel = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        loginB = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        signupPanel = new javax.swing.JPanel();
        userInputSignupT = new javax.swing.JTextField();
        userSignupLabel = new javax.swing.JLabel();
        passSignupLabel = new javax.swing.JLabel();
        pass2Label = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        emailInputText = new javax.swing.JTextField();
        passInputPassT = new javax.swing.JPasswordField();
        pass2InputText = new javax.swing.JPasswordField();
        signupB = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        helpPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();

        serverConfig.setAlwaysOnTop(true);
        serverConfig.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        serverConfig.setLocationRelativeTo(null);
        serverConfig.setSize(new Dimension(400,200));

        portField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        portField.setMinimumSize(new java.awt.Dimension(50, 25));
        portField.setPreferredSize(new java.awt.Dimension(50, 25));
        portField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portFieldActionPerformed(evt);
            }
        });
        serverConfig.getContentPane().add(portField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        serverField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        serverField.setMinimumSize(new java.awt.Dimension(350, 25));
        serverField.setPreferredSize(new java.awt.Dimension(350, 25));
        serverField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverFieldActionPerformed(evt);
            }
        });
        serverConfig.getContentPane().add(serverField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        serverLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        serverLabel.setText("Server:");
        serverConfig.getContentPane().add(serverLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        portLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        portLabel.setText("Port:");
        serverConfig.getContentPane().add(portLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        confirmB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        confirmB.setText("Confirm");
        confirmB.setMaximumSize(new java.awt.Dimension(100, 30));
        confirmB.setMinimumSize(new java.awt.Dimension(100, 30));
        confirmB.setPreferredSize(new java.awt.Dimension(100, 30));
        confirmB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBActionPerformed(evt);
            }
        });
        serverConfig.getContentPane().add(confirmB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 115, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));
        setName("MainFrame"); // NOI18N

        MainPanel.setOpaque(false);
        MainPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        MainPanel.setLayout(new java.awt.BorderLayout());

        MenuBarPanel.setMaximumSize(new java.awt.Dimension(800, 200));
        MenuBarPanel.setMinimumSize(new java.awt.Dimension(800, 200));
        MenuBarPanel.setPreferredSize(new java.awt.Dimension(800, 200));
        MenuBarPanel.setLayout(null);

        signupPageB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        signupPageB.setText("SIGN UP");
        signupPageB.setMaximumSize(new java.awt.Dimension(140, 40));
        signupPageB.setMinimumSize(new java.awt.Dimension(140, 40));
        signupPageB.setPreferredSize(new java.awt.Dimension(140, 40));
        signupPageB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupPageBActionPerformed(evt);
            }
        });
        MenuBarPanel.add(signupPageB);
        signupPageB.setBounds(93, 5, 140, 40);

        homePageB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        homePageB.setText("Home");
        homePageB.setMaximumSize(new java.awt.Dimension(140, 40));
        homePageB.setMinimumSize(new java.awt.Dimension(140, 40));
        homePageB.setPreferredSize(new java.awt.Dimension(140, 40));
        homePageB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homePageBActionPerformed(evt);
            }
        });
        MenuBarPanel.add(homePageB);
        homePageB.setBounds(238, 5, 140, 40);

        loginPageB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        loginPageB.setText("Login");
        loginPageB.setMaximumSize(new java.awt.Dimension(140, 40));
        loginPageB.setMinimumSize(new java.awt.Dimension(140, 40));
        loginPageB.setPreferredSize(new java.awt.Dimension(140, 40));
        loginPageB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginPageBActionPerformed(evt);
            }
        });
        MenuBarPanel.add(loginPageB);
        loginPageB.setBounds(383, 5, 140, 40);

        helpPageB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        helpPageB.setText("Help");
        helpPageB.setMaximumSize(new java.awt.Dimension(140, 40));
        helpPageB.setMinimumSize(new java.awt.Dimension(140, 40));
        helpPageB.setPreferredSize(new java.awt.Dimension(140, 40));
        helpPageB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpPageBActionPerformed(evt);
            }
        });
        MenuBarPanel.add(helpPageB);
        helpPageB.setBounds(528, 5, 140, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/barBackground.png"))); // NOI18N
        MenuBarPanel.add(jLabel1);
        jLabel1.setBounds(0, 0, 800, 50);

        MainPanel.add(MenuBarPanel, java.awt.BorderLayout.CENTER);

        pagesPanel.setPreferredSize(new java.awt.Dimension(800, 550));
        pagesPanel.setLayout(new java.awt.CardLayout());

        homePanel.setName(""); // NOI18N
        homePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea2.setRows(5);
        jTextArea2.setText("Welcome to ScrabbleRabble!\n\nIf you have any doubt on how to play , press Help.\n\nHope you enjoy play with us!");
        jTextArea2.setOpaque(false);
        homePanel.add(jTextArea2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 430, 120));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dragonforce.jpg"))); // NOI18N
        homePanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 470, 150));

        jToggleButton1.setText("Server");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        homePanel.add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 0, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pageBackground.png"))); // NOI18N
        homePanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 550));

        pagesPanel.add(homePanel, "homeP");

        loginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/jori.jpg"))); // NOI18N
        loginPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 220, 370));

        passInputText.setMinimumSize(new java.awt.Dimension(200, 40));
        passInputText.setPreferredSize(new java.awt.Dimension(200, 40));
        passInputText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passInputTextActionPerformed(evt);
            }
        });
        loginPanel.add(passInputText, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, -1, -1));

        userInputText.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        userInputText.setMinimumSize(new java.awt.Dimension(200, 40));
        userInputText.setPreferredSize(new java.awt.Dimension(200, 40));
        userInputText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userInputTextActionPerformed(evt);
            }
        });
        loginPanel.add(userInputText, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 200, 40));

        remembermeB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        remembermeB.setText("Remember me");
        remembermeB.setOpaque(false);
        loginPanel.add(remembermeB, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, -1, -1));

        passLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        passLabel.setText("Password");
        passLabel.setMaximumSize(new java.awt.Dimension(110, 30));
        passLabel.setMinimumSize(new java.awt.Dimension(110, 30));
        passLabel.setPreferredSize(new java.awt.Dimension(110, 30));
        loginPanel.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, -1, -1));

        userLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        userLabel.setText("Username");
        userLabel.setMaximumSize(new java.awt.Dimension(110, 30));
        userLabel.setMinimumSize(new java.awt.Dimension(110, 30));
        userLabel.setPreferredSize(new java.awt.Dimension(110, 30));
        loginPanel.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, -1, -1));

        loginB.setFont(new java.awt.Font("Tahoma", 0, 40)); // NOI18N
        loginB.setText("Login");
        loginB.setMaximumSize(new java.awt.Dimension(130, 60));
        loginB.setMinimumSize(new java.awt.Dimension(130, 60));
        loginB.setPreferredSize(new java.awt.Dimension(130, 60));
        loginB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBActionPerformed(evt);
            }
        });
        loginPanel.add(loginB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pageBackground.png"))); // NOI18N
        loginPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 550));

        pagesPanel.add(loginPanel, "loginP");

        signupPanel.setMinimumSize(new java.awt.Dimension(800, 550));
        signupPanel.setPreferredSize(new java.awt.Dimension(800, 550));
        signupPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userInputSignupT.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        userInputSignupT.setMinimumSize(new java.awt.Dimension(140, 40));
        userInputSignupT.setPreferredSize(new java.awt.Dimension(140, 40));
        userInputSignupT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userInputSignupTActionPerformed(evt);
            }
        });
        signupPanel.add(userInputSignupT, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 200, 40));

        userSignupLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        userSignupLabel.setText("Username");
        signupPanel.add(userSignupLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, -1));

        passSignupLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        passSignupLabel.setText("Password");
        signupPanel.add(passSignupLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 150, 30));

        pass2Label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        pass2Label.setText("Confirm Password");
        signupPanel.add(pass2Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 210, 30));

        emailLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        emailLabel.setText("Email");
        signupPanel.add(emailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, 70, 30));
        signupPanel.add(emailInputText, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 430, 200, 40));
        signupPanel.add(passInputPassT, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 200, 40));
        signupPanel.add(pass2InputText, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 200, 40));

        signupB.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        signupB.setText("SIGN UP");
        signupB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupBActionPerformed(evt);
            }
        });
        signupPanel.add(signupB, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 370, 200, 90));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rider.JPG"))); // NOI18N
        signupPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 40, 130, 150));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/youradhere.JPG"))); // NOI18N
        signupPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 330, 120, 210));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pageBackground.png"))); // NOI18N
        signupPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 550));

        pagesPanel.add(signupPanel, "signupP");

        helpPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/projsenior.JPG"))); // NOI18N
        helpPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 160, 320));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("        Frequently Asked Questions (FAQ)\n\nQ1: How to Sign Up/Create Account?\n\nA1: Press Sign Up button above and follow the \nsteps.\n\nQ2: How to Login?\n\nA2: Press Login button above and fill in with your\naccount details (Username and Password).\n\nQ3:How to play?\n\nA3:After the Login is done, press \"Create room\" or \n\"Join room\" and you will be redirected to game board.\n\nQ4: What are the rules ?\n\nA4:\n\n\n");
        jTextArea1.setOpaque(false);
        jScrollPane1.setViewportView(jTextArea1);

        helpPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 500, 460));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pageBackground.png"))); // NOI18N
        helpPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 550));

        pagesPanel.add(helpPanel, "helpP");

        MainPanel.add(pagesPanel, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(MainPanel, java.awt.BorderLayout.PAGE_START);

        getAccessibleContext().setAccessibleName("mainF");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loginPageBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginPageBActionPerformed
        selectPage("loginP");
    }//GEN-LAST:event_loginPageBActionPerformed

    private void userInputTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userInputTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userInputTextActionPerformed

    private void loginBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBActionPerformed
        /*Read what the user inputs*/
        String username = (String)((userInputText.getText()));
        String password = new String(passInputText.getPassword());
        //user.setName(username);
        newUsername = username;
            
        ClientService clientService = ClientService.getInstance();
        clientService.loginRequest(username, password);
            
        this.dispose();     
    }//GEN-LAST:event_loginBActionPerformed

    private void userInputSignupTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userInputSignupTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userInputSignupTActionPerformed

    private void signupPageBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupPageBActionPerformed
        selectPage("signupP");
    }//GEN-LAST:event_signupPageBActionPerformed

    private void homePageBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homePageBActionPerformed
        selectPage("homeP");
    }//GEN-LAST:event_homePageBActionPerformed

    private void helpPageBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpPageBActionPerformed
        selectPage("helpP");
    }//GEN-LAST:event_helpPageBActionPerformed

    private void passInputTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passInputTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passInputTextActionPerformed

    private void signupBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupBActionPerformed
        String username = (String)((userInputSignupT.getText()));
        String password = new String(passInputPassT.getPassword());
        String Cpassword = new String(pass2InputText.getPassword());
        String email = (String)((emailInputText.getText()));
        if(!ClientService.isValidEmailAddress(email)){
            JOptionPane.showMessageDialog(null,"The email must be valid");
        } else if (!password.equals(Cpassword)) {
            JOptionPane.showMessageDialog(null,"The passwords do not match. Please try again!","Wrong password",JOptionPane.WARNING_MESSAGE);
        } else {
            newUsername=username;
            ClientService clientService = ClientService.getInstance();
            clientService.signupRequest(username, password, email);
            this.dispose();
        }
    }//GEN-LAST:event_signupBActionPerformed

    private void serverFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serverFieldActionPerformed

    private void confirmBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBActionPerformed
        try{
            PrintWriter out = new PrintWriter(new FileWriter("config.txt"));
            out.println(portField.getText());
            out.println(serverField.getText());
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        serverConfig.setVisible(false);
        ClientProtocol.readServer();
    }//GEN-LAST:event_confirmBActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        
        BufferedReader inputStream = null;
        int i = 0;
        String aux[] = new String[3];
        String file = "config.txt";
        try {
            inputStream = new BufferedReader(new FileReader(file));
            while ((aux[i] = inputStream.readLine()) != null) {
                i++;
            }
            inputStream.close();
        }catch (FileNotFoundException f){
            System.err.println("Caught FileNotFoundException: " + f.getMessage());
        }catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        serverConfig.setVisible(true);
        portField.setText(aux[0]);
        serverField.setText(aux[1]);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void portFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portFieldActionPerformed

    
    /**
     * Changes GUI front Page (selects card to show from CardLayout)
     * 
     * @param page The page you want to show. Choose between one of these strings: "homeP", "signupP", "loginP", helpP".
     */
    private void selectPage(String page){
        CardLayout card = (CardLayout) pagesPanel.getLayout();
        card.show(pagesPanel, page);   
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InitialFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InitialFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InitialFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InitialFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
                
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                f = new InitialFrame();
                f.setVisible(true);
                
               // new InitialFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel MenuBarPanel;
    private javax.swing.JButton confirmB;
    private javax.swing.JTextField emailInputText;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JButton helpPageB;
    private javax.swing.JPanel helpPanel;
    private javax.swing.JButton homePageB;
    private javax.swing.JPanel homePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton loginB;
    private javax.swing.JButton loginPageB;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPanel pagesPanel;
    private javax.swing.JPasswordField pass2InputText;
    private javax.swing.JLabel pass2Label;
    private javax.swing.JPasswordField passInputPassT;
    private javax.swing.JPasswordField passInputText;
    private javax.swing.JLabel passLabel;
    private javax.swing.JLabel passSignupLabel;
    private javax.swing.JTextField portField;
    private javax.swing.JLabel portLabel;
    private javax.swing.JCheckBox remembermeB;
    private javax.swing.JDialog serverConfig;
    private javax.swing.JTextField serverField;
    private javax.swing.JLabel serverLabel;
    private javax.swing.JToggleButton signupB;
    private javax.swing.JButton signupPageB;
    private javax.swing.JPanel signupPanel;
    private javax.swing.JTextField userInputSignupT;
    private javax.swing.JTextField userInputText;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userSignupLabel;
    // End of variables declaration//GEN-END:variables

}
