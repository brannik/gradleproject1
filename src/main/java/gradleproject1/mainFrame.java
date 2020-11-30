/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradleproject1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


public class mainFrame extends javax.swing.JFrame {
    static Socket s;
    static ServerSocket ss;
    static InputStreamReader isr;
    static BufferedReader br;
    static String message;
    
    static Socket respS;
    static PrintWriter respW;
    
   
    
    
    
    public mainFrame() throws IOException, IOException {
        initComponents();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        textPanel = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jScrollPane3.setViewportView(textPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

 
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
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new mainFrame().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        try {
            ss = new ServerSocket(6000);
            while(true){
                
                s = ss.accept();
                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                message = br.readLine();
                
                String str= message;   
                str = str.replaceAll("[^a-zA-Z0-9_.-]", " ");  
                //System.out.println(str); 
                
                String inputString = str; 
                
                
                String[] arraySplit_3 = inputString.split("\\s+");
                String[] arr = new String[arraySplit_3.length];
                for (int i=0; i < arraySplit_3.length; i++){
                    System.out.println(arraySplit_3[i]);
                    arr[i] = arraySplit_3[i];
                }
                String to = null;
                if(arr[2].equals("MESSAGE")){
                    to = "FROM -> "+arr[6] + " -> " + arr[4];
                }
                if(arr[2].equals("LOGIN")){
                    to = arr[4] + " -> LOG IN REQUEST -> " + arr[6];
                    loginRequest(arr[4],arr[6]);
                    //jList1.getItems().add("some new element");
                }
                if(arr[2].equals("DISCONNECT")){
                    to = arr[4] + " -> DISCONECTED";
                    s.close();
                }
                if(arr[2].equals("GET_ONLINE")){
                    getOnline(arr);
                }
                
                
                
                if(jTextArea1.getText().equals("")){
                    jTextArea1.setText(to);
                    
                }else{
                    jTextArea1.setText(jTextArea1.getText() + "\n" + to);
                }
              
            }
        } catch (IOException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void getOnline(String [] array){
        // send online users
    }
    public static void loginRequest(String userIP,String devId){
        
        try {
            // connct to mysql and send back socket to sender
            String url = "http://"+userIP; 
            respS = new Socket(userIP,6001);
            respW = new PrintWriter(respS.getOutputStream());
            // check credentials and return true or false
            char[] arr = {'t','2'};
            respW.write("true");
            respW.flush();
            respW.close();
            respS.close();
        } catch (IOException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(textPanel.getText().equals("")){
            textPanel.setText("ONLINE IP: " + userIP + " ID: " + devId);
        }else{
            textPanel.setText(textPanel.getText() + "\nONLINE IP: " + userIP + " ID: " + devId);
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTextArea jTextArea1;
    private static javax.swing.JTextPane textPanel;
    // End of variables declaration//GEN-END:variables
}
        
