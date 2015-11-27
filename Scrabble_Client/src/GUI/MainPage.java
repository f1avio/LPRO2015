/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author Portatil
 */
public class MainPage extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public MainPage() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        usernameL = new javax.swing.JLabel();
        welcome = new javax.swing.JLabel();
        joinB = new javax.swing.JButton();
        createB = new javax.swing.JButton();
        settingsB = new javax.swing.JButton();
        logoutB = new javax.swing.JButton();
        rankingB = new javax.swing.JButton();
        helpB = new javax.swing.JButton();
        homeB = new javax.swing.JButton();
        TabsTable2 = new javax.swing.JTabbedPane();
        chatPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        chatarea2 = new javax.swing.JTextArea();
        chatinput2 = new javax.swing.JTextField();
        playersPanel = new javax.swing.JPanel();
        profsOptPanel = new javax.swing.JPanel();
        inviteB = new javax.swing.JButton();
        sendmessageB = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        playerList = new javax.swing.JTable();
        roomScrollPane = new javax.swing.JScrollPane();
        roomTable = new javax.swing.JTable();
        backgroung = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameL.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameL.setText("username");
        getContentPane().add(usernameL, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 80, 30));

        welcome.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        welcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcome.setText("Welcome");
        getContentPane().add(welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 80, 30));

        joinB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        joinB.setText("Join Room");
        joinB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinBActionPerformed(evt);
            }
        });
        getContentPane().add(joinB, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 180, 40));

        createB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        createB.setText("Create Room");
        createB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBActionPerformed(evt);
            }
        });
        getContentPane().add(createB, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, 180, 40));

        settingsB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        settingsB.setText("Settings");
        settingsB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsBActionPerformed(evt);
            }
        });
        getContentPane().add(settingsB, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 120, 40));

        logoutB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        logoutB.setText("Logout");
        logoutB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBActionPerformed(evt);
            }
        });
        getContentPane().add(logoutB, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 110, 40));

        rankingB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        rankingB.setText("Ranking");
        getContentPane().add(rankingB, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 120, 40));

        helpB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        helpB.setText("Help");
        getContentPane().add(helpB, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 100, 40));

        homeB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        homeB.setText("Home");
        getContentPane().add(homeB, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 100, 40));

        TabsTable2.setMinimumSize(new java.awt.Dimension(235, 260));
        TabsTable2.setPreferredSize(new java.awt.Dimension(235, 260));

        chatPanel2.setLayout(new java.awt.GridBagLayout());

        chatarea2.setEditable(false);
        chatarea2.setColumns(20);
        chatarea2.setRows(5);
        chatarea2.setWrapStyleWord(true);
        jScrollPane3.setViewportView(chatarea2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        chatPanel2.add(jScrollPane3, gridBagConstraints);

        chatinput2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                chatinput2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                chatinput2FocusLost(evt);
            }
        });
        chatinput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatinput2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        chatPanel2.add(chatinput2, gridBagConstraints);

        TabsTable2.addTab("Chat", chatPanel2);

        playersPanel.setLayout(new java.awt.BorderLayout());

        profsOptPanel.setMinimumSize(new java.awt.Dimension(222, 25));
        profsOptPanel.setOpaque(false);
        profsOptPanel.setPreferredSize(new java.awt.Dimension(232, 35));
        profsOptPanel.setLayout(new java.awt.BorderLayout());

        inviteB.setText("Invite To Play");
        inviteB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inviteBActionPerformed(evt);
            }
        });
        profsOptPanel.add(inviteB, java.awt.BorderLayout.CENTER);

        sendmessageB.setText("Send Message");
        sendmessageB.setOpaque(false);
        sendmessageB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendmessageBActionPerformed(evt);
            }
        });
        profsOptPanel.add(sendmessageB, java.awt.BorderLayout.LINE_START);

        playersPanel.add(profsOptPanel, java.awt.BorderLayout.PAGE_END);

        playerList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        playerList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                playerListMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(playerList);

        playersPanel.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        TabsTable2.addTab("Jogadores", playersPanel);

        getContentPane().add(TabsTable2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, 220, 380));

        roomTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room", "Players", "Joinable", "Room Owner"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        roomTable.getTableHeader().setReorderingAllowed(false);
        roomScrollPane.setViewportView(roomTable);

        getContentPane().add(roomScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 370, 180));

        backgroung.setBackground(new java.awt.Color(255, 255, 255));
        backgroung.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backgroung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/scrabbleRabble.jpg"))); // NOI18N
        getContentPane().add(backgroung, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void settingsBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_settingsBActionPerformed

    private void logoutBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutBActionPerformed

    private void chatinput2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chatinput2FocusGained
        //
    }//GEN-LAST:event_chatinput2FocusGained

    private void chatinput2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chatinput2FocusLost
        //
    }//GEN-LAST:event_chatinput2FocusLost

    private void chatinput2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatinput2ActionPerformed
        //
    }//GEN-LAST:event_chatinput2ActionPerformed

    private void sendmessageBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendmessageBActionPerformed

        //Codigo para enviar mensagem
    }//GEN-LAST:event_sendmessageBActionPerformed

    private void playerListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playerListMouseReleased
       //Codigo para selecionar jogador da lista
    }//GEN-LAST:event_playerListMouseReleased

    private void inviteBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviteBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inviteBActionPerformed

    private void createBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createBActionPerformed

    private void joinBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_joinBActionPerformed

    
    
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
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabsTable2;
    private javax.swing.JLabel backgroung;
    public javax.swing.JPanel chatPanel2;
    public javax.swing.JTextArea chatarea2;
    private javax.swing.JTextField chatinput2;
    private javax.swing.JButton createB;
    private javax.swing.JButton helpB;
    private javax.swing.JButton homeB;
    private javax.swing.JButton inviteB;
    public javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton joinB;
    private javax.swing.JButton logoutB;
    public javax.swing.JTable playerList;
    private javax.swing.JPanel playersPanel;
    private javax.swing.JPanel profsOptPanel;
    private javax.swing.JButton rankingB;
    private javax.swing.JScrollPane roomScrollPane;
    private javax.swing.JTable roomTable;
    private javax.swing.JButton sendmessageB;
    private javax.swing.JButton settingsB;
    private javax.swing.JLabel usernameL;
    private javax.swing.JLabel welcome;
    // End of variables declaration//GEN-END:variables
}
