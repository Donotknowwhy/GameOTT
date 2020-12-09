/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Account;
import model.User;

/**
 *
 * @author BENH VIEN CONG NGHE
 */
public class ListFrm extends javax.swing.JFrame {

    int indexRow;
    DefaultTableModel model;

    /**
     * Creates new form ListFrm
     */
    public ListFrm() {
        initComponents();
//        model = (DefaultTableModel) tableList.getModel();
    }
    public void setActionM(ActionListener al1,ActionListener al,WindowListener wl){
        btnReload.addActionListener(al1);
        btnInvite.addActionListener(al);
        this.addWindowListener(wl);
    }
    public void addRow(ArrayList<User> listUser) {

    }

//        private void showUsersLowToHigh(ArrayList<User> users){
//        DefaultTableModel dtm = (DefaultTableModel)tableList.getModel();
//        dtm.getDataVector().removeAllElements();
//        
//        Collections.sort(users);        
//        users.forEach(u -> {
//            dtm.addRow(u.toObjects());
//        });
//    }
//    
//    private void showUserHighToLow(){
//        DefaultTableModel dtm = (DefaultTableModel)tableList.getModel();
//        dtm.getDataVector().removeAllElements();
//        
//        Collections.sort(users);   
//        Collections.reverse(users);
//        users.forEach(u -> {
//            dtm.addRow(u.toObjects());
//        });
//    }
    public void showUsers(ArrayList<User> users) {
        DefaultTableModel dtm = (DefaultTableModel) tableList.getModel();
        dtm.getDataVector().removeAllElements();
        users.forEach(u -> {
            dtm.addRow(u.toObjects());
        });
    }

    public User choseData(ArrayList<User> users) {
        User user = new User();
        System.out.println("---"+users.size());
        if (indexRow >= 0) {
            user = users.get(indexRow);
//            System.out.println(users.get(indexRow).toString());
//            int rank = user.getRank();
//            Account acc = user.getAccount();
//            int point = user.getPoint();           
//            boolean status = user.isStatus();
        }
//        System.out.println(user.toObjects());
        return user;
    }


    public void thongBao(String f) {
        JOptionPane.showMessageDialog(this, f);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableList = new javax.swing.JTable();
        btnInvite = new javax.swing.JButton();
        back = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("GAME OAN TU TI");

        tableList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rank", "Username", "Point", "Status"
            }
        ));
        tableList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableListMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tableList);

        btnInvite.setText("Invite");
        btnInvite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInviteActionPerformed(evt);
            }
        });

        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        btnReload.setText("Reload");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 853, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(back)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnInvite)
                .addGap(123, 123, 123))
            .addGroup(layout.createSequentialGroup()
                .addGap(399, 399, 399)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReload)
                .addGap(102, 102, 102))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReload))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInvite)
                    .addComponent(back))
                .addGap(66, 66, 66))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_backActionPerformed

    private void btnInviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviteActionPerformed
        // TODO add your handling code here:
//        new GameFrm().setVisible(true);
    }//GEN-LAST:event_btnInviteActionPerformed

    private void tableListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListMouseClicked
      
        indexRow = tableList.getSelectedRow();
    }//GEN-LAST:event_tableListMouseClicked

    private void tableListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListMousePressed
       
    }//GEN-LAST:event_tableListMousePressed
    public int getSelectedRow() {
        return tableList.getSelectedRow();
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
            java.util.logging.Logger.getLogger(ListFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton btnInvite;
    private javax.swing.JButton btnReload;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableList;
    // End of variables declaration//GEN-END:variables
}
