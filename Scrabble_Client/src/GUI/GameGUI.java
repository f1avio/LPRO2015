/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author HUGUETA
 */
public class GameGUI extends javax.swing.JFrame {

    /**
     * Creates new form GameGUI
     */
    public GameGUI() {
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

        ChatP = new javax.swing.JPanel();
        TabsTableRank1 = new javax.swing.JTabbedPane();
        chatPanel1 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        chatarea4 = new javax.swing.JTextArea();
        chatinput4 = new javax.swing.JTextField();
        playersPanel2 = new javax.swing.JPanel();
        profsOptPanel2 = new javax.swing.JPanel();
        inviteB2 = new javax.swing.JButton();
        sendmessageB2 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        playerList2 = new javax.swing.JTable();
        contentPane = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        boardPanel = new javax.swing.JPanel();
        boadCanvas = new java.awt.Canvas(){
            @Override
            public void paint(Graphics g){
                drawBoard(g);
                drawPlayers(g);
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ScrabbleRubble");
        setMinimumSize(new java.awt.Dimension(800, 600));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        ChatP.setMinimumSize(new java.awt.Dimension(295, 600));
        ChatP.setPreferredSize(new java.awt.Dimension(295, 600));
        ChatP.setLayout(null);

        TabsTableRank1.setMinimumSize(new java.awt.Dimension(235, 260));
        TabsTableRank1.setPreferredSize(new java.awt.Dimension(235, 260));

        chatPanel1.setLayout(new java.awt.GridBagLayout());

        chatarea4.setEditable(false);
        chatarea4.setColumns(20);
        chatarea4.setRows(5);
        chatarea4.setWrapStyleWord(true);
        jScrollPane7.setViewportView(chatarea4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        chatPanel1.add(jScrollPane7, gridBagConstraints);

        chatinput4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                chatinput4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                chatinput4FocusLost(evt);
            }
        });
        chatinput4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatinput4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        chatPanel1.add(chatinput4, gridBagConstraints);

        TabsTableRank1.addTab("Chat", chatPanel1);

        playersPanel2.setLayout(new java.awt.BorderLayout());

        profsOptPanel2.setMinimumSize(new java.awt.Dimension(222, 25));
        profsOptPanel2.setOpaque(false);
        profsOptPanel2.setPreferredSize(new java.awt.Dimension(232, 35));
        profsOptPanel2.setLayout(new java.awt.BorderLayout());

        inviteB2.setText("Invite To Play");
        inviteB2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inviteB2ActionPerformed(evt);
            }
        });
        profsOptPanel2.add(inviteB2, java.awt.BorderLayout.CENTER);

        sendmessageB2.setText("Send Message");
        sendmessageB2.setOpaque(false);
        sendmessageB2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendmessageB2ActionPerformed(evt);
            }
        });
        profsOptPanel2.add(sendmessageB2, java.awt.BorderLayout.LINE_START);

        playersPanel2.add(profsOptPanel2, java.awt.BorderLayout.PAGE_END);

        playerList2.setModel(new javax.swing.table.DefaultTableModel(
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
        playerList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                playerList2MouseReleased(evt);
            }
        });
        jScrollPane8.setViewportView(playerList2);

        playersPanel2.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        TabsTableRank1.addTab("Jogadores", playersPanel2);

        ChatP.add(TabsTableRank1);
        TabsTableRank1.setBounds(60, 120, 220, 380);

        contentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setMinimumSize(new java.awt.Dimension(505, 600));
        contentPane.setPreferredSize(new java.awt.Dimension(505, 600));
        contentPane.setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setLayout(null);
        contentPane.add(jPanel1);
        jPanel1.setBounds(3, 520, 500, 60);

        boardPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        boardPanel.setFocusable(false);
        boardPanel.setMinimumSize(new java.awt.Dimension(500, 500));
        boardPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        boardPanel.setLayout(null);

        boadCanvas.setBackground(new java.awt.Color(255, 255, 255));
        boadCanvas.setMinimumSize(new java.awt.Dimension(494, 494));
        boardPanel.add(boadCanvas);
        boadCanvas.setBounds(3, 3, 0, 0);

        contentPane.add(boardPanel);
        boardPanel.setBounds(3, 3, 500, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(contentPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ChatP, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(ChatP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        contentPane.getAccessibleContext().setAccessibleParent(contentPane);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playerList2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playerList2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_playerList2MouseReleased

    private void sendmessageB2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendmessageB2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendmessageB2ActionPerformed

    private void inviteB2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviteB2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inviteB2ActionPerformed

    private void chatinput4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatinput4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chatinput4ActionPerformed

    private void chatinput4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chatinput4FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_chatinput4FocusLost

    private void chatinput4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chatinput4FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_chatinput4FocusGained

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

    static void drawBoard(Graphics g)
    {
        for(int y=0; y<15; y++)
            for(int x=0; x<15; x++)
            {
		if(s.board.getLetter(x, y) != null)
		{
                    g.setColor (Color.yellow);
                    g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);

                    g.setColor (Color.black);
                    g.drawString(s.board.getLetter(x, y).toString(), 17+x*30, 28+y*30);
		}
		else
		{
                    if (s.board.getPremiumColor(x, y) == Board.PC.NO)
                    {
                        g.setColor (new Color(220, 220, 220));
                        g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);
                    }
                    else if (s.board.getPremiumColor(x, y) == Board.PC.TW)
                    {
                        g.setColor (new Color(255, 90, 90));
                        g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);
                    }						
                    else if (s.board.getPremiumColor(x, y) == Board.PC.DW)
                    {
                        g.setColor (new Color(255,187,163));
                        g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);
                    }
                    else if (s.board.getPremiumColor(x, y) == Board.PC.TL)
                    {
                        g.setColor (new Color(90, 90, 255));
                        g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);
                    }
                    else if (s.board.getPremiumColor(x, y) == Board.PC.DL)
                    {
                        g.setColor (new Color(200, 200, 255));
                        g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);
                    }
                }
            }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChatP;
    private javax.swing.JTabbedPane TabsTableRank1;
    private java.awt.Canvas boadCanvas;
    private javax.swing.JPanel boardPanel;
    public javax.swing.JPanel chatPanel1;
    public javax.swing.JTextArea chatarea4;
    private javax.swing.JTextField chatinput4;
    private javax.swing.JPanel contentPane;
    private javax.swing.JButton inviteB2;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    public javax.swing.JTable playerList2;
    private javax.swing.JPanel playersPanel2;
    private javax.swing.JPanel profsOptPanel2;
    private javax.swing.JButton sendmessageB2;
    // End of variables declaration//GEN-END:variables
}
