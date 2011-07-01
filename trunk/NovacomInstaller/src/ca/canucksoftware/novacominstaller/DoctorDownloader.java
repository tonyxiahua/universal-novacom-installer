/*
 * WebOSQuickInstallAboutBox.java
 */

package ca.canucksoftware.novacominstaller;

import ca.canucksoftware.novacom.NovacomDrivers;
import java.util.Timer;
import java.util.TimerTask;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;


public class DoctorDownloader extends javax.swing.JDialog {
    private final String NEWEST = "http://universal-novacom-installer.googlecode.com/svn/trunk/" +
            "NovacomInstaller/doctor.txt";
    private Timer t;
    private boolean downloadStarted;
    private String url;
    private NovacomDrivers driver;

    public DoctorDownloader(java.awt.Frame parent) {
        super(parent);
        initComponents();
        t = new Timer();
        downloadStarted = false;
        url = getURL();
        driver = null;
    }

    public String getURL() {
        String result = null;
        URLConnection urlCon = null;
        try {
            urlCon = new URL(NEWEST).openConnection();
            urlCon.setRequestProperty("Content-Type", "text/plain");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new BufferedInputStream(urlCon.getInputStream())));
            String line = br.readLine();
            if(line!=null) {
                result = line.trim();
            }
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ca.canucksoftware.novacominstaller.NovacomInstallerApp.class).getContext().getResourceMap(DoctorDownloader.class);
        setBackground(resourceMap.getColor("transfer.background")); // NOI18N
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setForeground(resourceMap.getColor("transfer.foreground")); // NOI18N
        setIconImage(null);
        setModal(true);
        setName("transfer"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLayeredPane1.setBackground(resourceMap.getColor("jLayeredPane1.background")); // NOI18N
        jLayeredPane1.setForeground(resourceMap.getColor("jLayeredPane1.foreground")); // NOI18N
        jLayeredPane1.setName("jLayeredPane1"); // NOI18N
        jLayeredPane1.setOpaque(true);

        jProgressBar1.setFont(jProgressBar1.getFont());
        jProgressBar1.setMaximum(1);
        jProgressBar1.setName("jProgressBar1"); // NOI18N
        jProgressBar1.setString(resourceMap.getString("jProgressBar1.string")); // NOI18N
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setBounds(50, 60, 200, 30);
        jLayeredPane1.add(jProgressBar1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+1f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setBounds(30, 10, 240, 40);
        jLayeredPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(!downloadStarted) {
            t.schedule(new DoDownload(), 100);
            downloadStarted = true;
        }
    }//GEN-LAST:event_formWindowActivated

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables

    class DoDownload extends TimerTask  {
        public void run() {
            t.cancel();
            NovacomDrivers driver = new NovacomDrivers(url);
            driver.setGUI(jLabel1, jProgressBar1);
            if(driver.install()) {
                JOptionPane.showMessageDialog(rootPane, "Driver installed successfully.");
            } else {
                JOptionPane.showMessageDialog(rootPane, "ERROR: Driver installation failed");
            }
            System.gc();
            dispose();
        }
    }
}
