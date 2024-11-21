/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUIClasses;

import EDD.Queue;
import Functions.ImageUtils;
import MainClasses.CharacterTv;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author angel
 */
public class QueueUI extends javax.swing.JPanel {

    /**
     * Creates new form QueueUI
     */
    private JPanel queuePanel; // Panel para agregar los JLabel
    private final ImageUtils imageUtils = new ImageUtils();

    public QueueUI() {
        initComponents();
        myInitComponents();
    }

    public QueueUI(String title) {
        initComponents();
        myInitComponents();
        this.titleQueueUI.setText(title);
    }

    private void myInitComponents() {
        queuePanel = new JPanel();
        getQueuePanel().setLayout(new FlowLayout(FlowLayout.LEFT));
        jScrollPaneQueue.setViewportView(getQueuePanel());

        jScrollPaneQueue.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPaneQueue.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    }

    public void addCard(String urlImage) {
        ImageIcon imgIcon = imageUtils.loadScaledImage(urlImage, 60, 80);
        JLabel etiqueta = new JLabel(imgIcon);
        getQueuePanel().add(etiqueta);
    }

    public void updateQueueUI(Queue queue) {
        getQueuePanel().removeAll();
        Queue auxQueue = queue.cloneQueue();

        while (!auxQueue.isEmpty()) {
            CharacterTv character = (CharacterTv) auxQueue.dequeue();
            this.addCard(character.getUrlSource());
        }
        queuePanel.revalidate();
        queuePanel.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RegularShowHighPriorityTitle = new javax.swing.JLabel();
        backgroundQueue = new javax.swing.JPanel();
        titleQueueUI = new javax.swing.JLabel();
        jScrollPaneQueue = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        RegularShowHighPriorityTitle.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        RegularShowHighPriorityTitle.setForeground(new java.awt.Color(0, 0, 0));
        RegularShowHighPriorityTitle.setText("COLA PRIORIDAD: ALTA");

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backgroundQueue.setBackground(new java.awt.Color(81, 130, 154));
        backgroundQueue.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, null, null));
        backgroundQueue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleQueueUI.setBackground(new java.awt.Color(255, 255, 255));
        titleQueueUI.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        titleQueueUI.setForeground(new java.awt.Color(255, 255, 255));
        titleQueueUI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleQueueUI.setText("COLA PRIORIDAD:");
        backgroundQueue.add(titleQueueUI, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, -1));

        jScrollPaneQueue.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPaneQueue.setForeground(new java.awt.Color(204, 204, 204));
        backgroundQueue.add(jScrollPaneQueue, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 333, 105));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Montserrat", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Tope de la cola");
        backgroundQueue.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 112, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Montserrat", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Final de la cola");
        backgroundQueue.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 120, -1));

        add(backgroundQueue, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 150));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel RegularShowHighPriorityTitle;
    private javax.swing.JPanel backgroundQueue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPaneQueue;
    private javax.swing.JLabel titleQueueUI;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the queuePanel
     */
    public JPanel getQueuePanel() {
        return queuePanel;
    }

    /**
     * @return the titleQueueUI
     */
    public javax.swing.JLabel getTitleQueueUI() {
        return titleQueueUI;
    }

}