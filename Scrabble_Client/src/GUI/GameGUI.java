/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import game_model.Board;
import game_model.Scrabble;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import scrabble_client.ClientService;

/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */ 
public class GameGUI extends javax.swing.JFrame {
    
    public static Scrabble s;
    public static GameGUI frame;
    private Canvas boardCanvas;
    
    /**
     * Creates new form GameGUI
     */
    public GameGUI() {
        initComponents();
        ClientService clientService = ClientService.getInstance();
        clientService.setGameGui(this);
        boardCanvas = new Canvas()
        {
            private static final long serialVersionUID = 3678971088393809762L;
            public void paint(Graphics g)
            {
                drawBoard(g);	
                //drawPlayers(g);
            }
        };
        boardCanvas.setBackground(Color.WHITE);
        boardCanvas.setBounds(3, 3, 518, 518);
        boardPanel.add(boardCanvas);
        //drawPlayers(g);
        s = new Scrabble(1);
        s.firstLetters();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        ChatP = new javax.swing.JPanel();
        TabsTableRank = new javax.swing.JTabbedPane();
        chatPanel = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        chatarea = new javax.swing.JTextArea();
        chatinput = new javax.swing.JTextField();
        playersPanel = new javax.swing.JPanel();
        profsOptPanel = new javax.swing.JPanel();
        inviteB = new javax.swing.JButton();
        sendmessageB = new javax.swing.JButton();
        PlayerScrollPane = new javax.swing.JScrollPane();
        playerList = new javax.swing.JTable();
        contentPane = new javax.swing.JPanel();
        tilePanel = new javax.swing.JPanel();
        boardPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        quitGameB = new javax.swing.JButton();

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(null);

        ChatP.setMinimumSize(new java.awt.Dimension(270, 600));
        ChatP.setLayout(null);

        TabsTableRank.setMinimumSize(new java.awt.Dimension(235, 260));
        TabsTableRank.setPreferredSize(new java.awt.Dimension(235, 260));

        chatPanel.setLayout(new java.awt.GridBagLayout());

        chatarea.setEditable(false);
        chatarea.setColumns(20);
        chatarea.setRows(5);
        chatarea.setWrapStyleWord(true);
        jScrollPane.setViewportView(chatarea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        chatPanel.add(jScrollPane, gridBagConstraints);

        chatinput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                chatinputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                chatinputFocusLost(evt);
            }
        });
        chatinput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatinputActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        chatPanel.add(chatinput, gridBagConstraints);

        TabsTableRank.addTab("Chat", chatPanel);

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
        PlayerScrollPane.setViewportView(playerList);

        playersPanel.add(PlayerScrollPane, java.awt.BorderLayout.CENTER);

        TabsTableRank.addTab("Players", playersPanel);

        ChatP.add(TabsTableRank);
        TabsTableRank.setBounds(30, 0, 220, 380);

        getContentPane().add(ChatP);
        ChatP.setBounds(530, 140, 270, 460);

        contentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setMinimumSize(new java.awt.Dimension(530, 600));
        contentPane.setLayout(null);

        tilePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tilePanel.setMinimumSize(new java.awt.Dimension(518, 60));
        tilePanel.setLayout(null);
        contentPane.add(tilePanel);
        tilePanel.setBounds(3, 530, 518, 60);

        boardPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        boardPanel.setFocusable(false);
        boardPanel.setMinimumSize(new java.awt.Dimension(518, 518));
        boardPanel.setLayout(null);
        contentPane.add(boardPanel);
        boardPanel.setBounds(3, 3, 518, 518);

        getContentPane().add(contentPane);
        contentPane.setBounds(0, 0, 530, 600);

        menuPanel.setPreferredSize(new java.awt.Dimension(270, 140));
        menuPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        quitGameB.setText("QUIT");
        quitGameB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitGameBActionPerformed(evt);
            }
        });
        menuPanel.add(quitGameB, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, -1));

        getContentPane().add(menuPanel);
        menuPanel.setBounds(530, 0, 270, 140);
    }// </editor-fold>//GEN-END:initComponents

    private void playerListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playerListMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_playerListMouseReleased

    private void sendmessageBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendmessageBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendmessageBActionPerformed

    private void inviteBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviteBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inviteBActionPerformed

    private void chatinputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatinputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chatinputActionPerformed

    private void chatinputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chatinputFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_chatinputFocusLost

    private void chatinputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chatinputFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_chatinputFocusGained

    private void quitGameBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitGameBActionPerformed
        ClientService clientService = ClientService.getInstance();
        clientService.exitGame();
    }//GEN-LAST:event_quitGameBActionPerformed

    static void drawBoard(Graphics g)
    {
        for(int y=0; y<15; y++)
            for(int x=0; x<15; x++)
            {
		if(s.board.getLetter(x, y) != null)
		{
                    g.setColor (Color.yellow);
                    g.fill3DRect(4+x*34, 4+y*34, 30, 30, true);

                    g.setColor (Color.black);
                    g.drawString(s.board.getLetter(x, y).toString(), 17+x*30, 28+y*30);
		}
		else
		{
                    if (s.board.getPremiumColor(x, y) == Board.Pcolor.NO)
                    {
                        g.setColor (new Color(220, 220, 220));
                        g.fill3DRect(4+x*34, 4+y*34, 30, 30, true);
                    }
                    else if (s.board.getPremiumColor(x, y) == Board.Pcolor.TW)
                    {
                        g.setColor (new Color(255, 90, 90));
                        g.fill3DRect(4+x*34, 4+y*34, 30, 30, true);
                    }						
                    else if (s.board.getPremiumColor(x, y) == Board.Pcolor.DW)
                    {
                        g.setColor (new Color(255,187,163));
                        g.fill3DRect(4+x*34, 4+y*34, 30, 30, true);
                    }
                    else if (s.board.getPremiumColor(x, y) == Board.Pcolor.TL)
                    {
                        g.setColor (new Color(90, 90, 255));
                        g.fill3DRect(4+x*34, 4+y*34, 30, 30, true);
                    }
                    else if (s.board.getPremiumColor(x, y) == Board.Pcolor.DL)
                    {
                        g.setColor (new Color(200, 200, 255));
                        g.fill3DRect(4+x*34, 4+y*34, 30, 30, true);
                    }
                }
            }
    }
    
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
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameGUI().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChatP;
    private javax.swing.JScrollPane PlayerScrollPane;
    private javax.swing.JTabbedPane TabsTableRank;
    private javax.swing.JPanel boardPanel;
    public javax.swing.JPanel chatPanel;
    public javax.swing.JTextArea chatarea;
    private javax.swing.JTextField chatinput;
    private javax.swing.JPanel contentPane;
    private javax.swing.JButton inviteB;
    public javax.swing.JScrollPane jScrollPane;
    private javax.swing.JPanel menuPanel;
    public javax.swing.JTable playerList;
    private javax.swing.JPanel playersPanel;
    private javax.swing.JPanel profsOptPanel;
    private javax.swing.JButton quitGameB;
    private javax.swing.JButton sendmessageB;
    private javax.swing.JPanel tilePanel;
    // End of variables declaration//GEN-END:variables
}
