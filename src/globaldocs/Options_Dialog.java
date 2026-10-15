/*
 * Options_Dialog.java
 *
 * Created on March 19, 2006, 2:31 PM
 *
 *     Globaldocs -- viewer for Javadocs.
 *
 *     Copyright(C) 2005,2006 Zeev (Vladimir) Belkin. All rights reserved.
 *
   <h1>The Artistic License</h1>

<tt>



<p>Preamble</p>

<p>The intent of this document is to state the conditions under which a
Package may be copied, such that the Copyright Holder maintains some
semblance of artistic control over the development of the package,
while giving the users of the package the right to use and distribute
the Package in a more-or-less customary fashion, plus the right to make
reasonable modifications.</p>

<p>Definitions:</p>

<ul>

<li>	"Package" refers to the collection of files distributed by the
	Copyright Holder, and derivatives of that collection of files
	created through textual modification.</li>

<li>	"Standard Version" refers to such a Package if it has not been
	modified, or has been modified in accordance with the wishes
	of the Copyright Holder.</li>

<li>	"Copyright Holder" is whoever is named in the copyright or
	copyrights for the package.</li>

<li>	"You" is you, if you're thinking about copying or distributing
	this Package.</li>

<li>	"Reasonable copying fee" is whatever you can justify on the
	basis of media cost, duplication charges, time of people involved,
	and so on.  (You will not be required to justify it to the
	Copyright Holder, but only to the computing community at large
	as a market that must bear the fee.)</li>

<li>	"Freely Available" means that no fee is charged for the item
	itself, though there may be fees involved in handling the item.
	It also means that recipients of the item may redistribute it
	under the same conditions they received it.</li>
	
</ul>

<p>1. You may make and give away verbatim copies of the source form of the
Standard Version of this Package without restriction, provided that you
duplicate all of the original copyright notices and associated disclaimers.</p>

<p>2. You may apply bug fixes, portability fixes and other modifications
derived from the Public Domain or from the Copyright Holder.  A Package
modified in such a way shall still be considered the Standard Version.</p>

<p>3. You may otherwise modify your copy of this Package in any way, provided
that you insert a prominent notice in each changed file stating how and
when you changed that file, and provided that you do at least ONE of the
following:</p>

<blockquote>

    <p>a) place your modifications in the Public Domain or otherwise make them
    Freely Available, such as by posting said modifications to Usenet or
    an equivalent medium, or placing the modifications on a major archive
    site such as ftp.uu.net, or by allowing the Copyright Holder to include
    your modifications in the Standard Version of the Package.</p>

    <p>b) use the modified Package only within your corporation or organization.</p>

    <p>c) rename any non-standard executables so the names do not conflict
    with standard executables, which must also be provided, and provide
    a separate manual page for each non-standard executable that clearly
    documents how it differs from the Standard Version.</p>

    <p>d) make other distribution arrangements with the Copyright Holder.</p>
	
</blockquote>

<p>4. You may distribute the programs of this Package in object code or
executable form, provided that you do at least ONE of the following:</p>

<blockquote>

    <p>a) distribute a Standard Version of the executables and library files,
    together with instructions (in the manual page or equivalent) on where
    to get the Standard Version.</p>

    <p>b) accompany the distribution with the machine-readable source of
    the Package with your modifications.</p>

    <p>c) accompany any non-standard executables with their corresponding
    Standard Version executables, giving the non-standard executables
    non-standard names, and clearly documenting the differences in manual
    pages (or equivalent), together with instructions on where to get
    the Standard Version.</p>

    <p>d) make other distribution arrangements with the Copyright Holder.</p>

</blockquote>

<p>5. You may charge a reasonable copying fee for any distribution of this
Package.  You may charge any fee you choose for support of this Package.
You may not charge a fee for this Package itself.  However,
you may distribute this Package in aggregate with other (possibly
commercial) programs as part of a larger (possibly commercial) software
distribution provided that you do not advertise this Package as a
product of your own.</p>

<p>6. The scripts and library files supplied as input to or produced as
output from the programs of this Package do not automatically fall
under the copyright of this Package, but belong to whomever generated
them, and may be sold commercially, and may be aggregated with this
Package.</p>


<p>7. The name of the Copyright Holder may not be used to endorse or promote
products derived from this software without specific prior written permission.</p>

<p>8. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.</p>

<p>The End</p>
 
 */


package globaldocs;

import static globaldocs.Utils.*;
import java.awt.Dimension;
import java.util.*;
import java.io.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.net.*;
import javax.swing.JOptionPane;


/**
 *
 * @author  zeev
 */
public class Options_Dialog extends javax.swing.JDialog {
   
   static File 
       proxyPropsFile=new File(configDirectory,"proxy.properties");
   
   static Properties proxyProps=new Properties() {
     {
        if (proxyPropsFile.exists()) try {
          load(new FileInputStream(proxyPropsFile));
        } catch (Throwable t) {}
        boolean sysProxyPort=false,sysProxyHost=false;
        String s=System.getProperty(PROXY_HOST);
        if (s!=null) setProperty(PROXY_HOST,s); else sysProxyHost=true;
        s=System.getProperty(PROXY_PORT);
        if (s!=null) setProperty(PROXY_PORT,s); else sysProxyPort=true;
        
        if ("true".equals(getProperty(PROXY_SET))) {
          s=nullify(getProperty(PROXY_HOST));
          if (s!=null) System.setProperty(PROXY_HOST,s);
          s=nullify(getProperty(PROXY_PORT));
          if (s!=null) System.setProperty(PROXY_PORT,s);
        }
     }
   };
   
   /**
    * Creates new form Options_Dialog
    */
   public Options_Dialog(java.awt.Frame parent, boolean modal) {
      super(parent, modal);
      initComponents();
      getRootPane().setDefaultButton(jButton_OK);
      jButton_dropAllAuthInfo.setEnabled(Wallet.anyAuthInfo());
      jButton_GiveMasterPassword.setEnabled(Wallet.isMasterPasswordSet());
      jTextField_proxyPort.setDocument(
        new PlainDocument() {

          public void insertString(
            int offset, String stringToInsert,
            AttributeSet attributes
          )  throws BadLocationException {
            try {
              String 
                 s=denullify(jTextField_proxyPort.getText()),
                 s1,s2;
              if (offset<=0) { s1=s; s2=""; } else {
                s1=s.substring(0,offset);
                s2=s.substring(offset);
              }
                      
              int i=Integer.parseInt(s1+stringToInsert+s2);
              if (i<1||i>65535) {
                 defaultToolkit.beep();
                 return;
              }
              
            } catch (NumberFormatException e) {
               defaultToolkit.beep();
               return;
            }
            
            super.insertString(offset, stringToInsert, attributes);
            
          }
        }    
      );
    
      Dimension sz=getSize();
      setLocation(
               (screenDimension.width-sz.width)>>1,
               (screenDimension.height-sz.height)>>1
               );
      String s=nullify(proxyProps.getProperty(PROXY_HOST));
      if (s!=null) jTextField_proxyAddress.setText(s);
      s=nullify(proxyProps.getProperty(PROXY_PORT));
      if (s!=null) jTextField_proxyPort.setText(s);
      boolean checked="true".equals(proxyProps.getProperty(PROXY_SET));
      checkbox_useTheProxy.setSelected(checked);
      jTextField_proxyAddress.setEnabled(checked);
      jTextField_proxyPort.setEnabled(checked);
   }   
   
   /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
   // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
   private void initComponents() {
      java.awt.GridBagConstraints gridBagConstraints;

      jTabbedPane = new javax.swing.JTabbedPane();
      jPanel_Connection = new javax.swing.JPanel();
      jPanel1 = new javax.swing.JPanel();
      checkbox_useTheProxy = new javax.swing.JCheckBox();
      addressPane = new javax.swing.JPanel();
      jLabel_address = new javax.swing.JLabel();
      jTextField_proxyAddress = new javax.swing.JTextField();
      portPane = new javax.swing.JPanel();
      jLabel_port = new javax.swing.JLabel();
      jTextField_proxyPort = new javax.swing.JTextField();
      jPanel_Waller = new javax.swing.JPanel();
      jButton_GiveMasterPassword = new javax.swing.JButton();
      jButton_dropAllAuthInfo = new javax.swing.JButton();
      jPanel2 = new javax.swing.JPanel();
      jButton_OK = new javax.swing.JButton();
      jButton_Cancel = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Options");
      setModal(true);
      setResizable(false);
      jPanel_Connection.setLayout(new java.awt.CardLayout());

      jPanel1.setLayout(new java.awt.BorderLayout());

      jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("HTTP Proxy"));
      jPanel1.setMaximumSize(new java.awt.Dimension(212, 180));
      checkbox_useTheProxy.setFont(new java.awt.Font("Dialog", 0, 12));
      checkbox_useTheProxy.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
      checkbox_useTheProxy.setLabel("Use The Proxy");
      checkbox_useTheProxy.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            checkbox_useTheProxyActionPerformed(evt);
         }
      });

      jPanel1.add(checkbox_useTheProxy, java.awt.BorderLayout.SOUTH);

      addressPane.setLayout(new java.awt.BorderLayout());

      jLabel_address.setFont(new java.awt.Font("Dialog", 0, 12));
      jLabel_address.setText("Address:");
      addressPane.add(jLabel_address, java.awt.BorderLayout.NORTH);

      addressPane.add(jTextField_proxyAddress, java.awt.BorderLayout.SOUTH);

      jPanel1.add(addressPane, java.awt.BorderLayout.NORTH);

      portPane.setLayout(new java.awt.BorderLayout());

      portPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 65, 170));
      portPane.setMaximumSize(new java.awt.Dimension(200, 33));
      jLabel_port.setFont(new java.awt.Font("Dialog", 0, 12));
      jLabel_port.setText("Port:");
      portPane.add(jLabel_port, java.awt.BorderLayout.NORTH);

      jTextField_proxyPort.setMaximumSize(new java.awt.Dimension(200, 18));
      jTextField_proxyPort.setMinimumSize(new java.awt.Dimension(200, 18));
      jTextField_proxyPort.setPreferredSize(new java.awt.Dimension(200, 18));
      portPane.add(jTextField_proxyPort, java.awt.BorderLayout.CENTER);

      jPanel1.add(portPane, java.awt.BorderLayout.CENTER);

      jPanel_Connection.add(jPanel1, "card2");

      jTabbedPane.addTab("Connection", jPanel_Connection);

      jPanel_Waller.setLayout(new java.awt.GridBagLayout());

      jButton_GiveMasterPassword.setFont(new java.awt.Font("Dialog", 0, 12));
      jButton_GiveMasterPassword.setText("Set The Wallet Password");
      jButton_GiveMasterPassword.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_GiveMasterPasswordActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      jPanel_Waller.add(jButton_GiveMasterPassword, gridBagConstraints);

      jButton_dropAllAuthInfo.setFont(new java.awt.Font("Dialog", 0, 12));
      jButton_dropAllAuthInfo.setText("Drop All Authentication Info");
      jButton_dropAllAuthInfo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_dropAllAuthInfoActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 2;
      gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
      jPanel_Waller.add(jButton_dropAllAuthInfo, gridBagConstraints);

      jTabbedPane.addTab("Wallet", jPanel_Waller);

      getContentPane().add(jTabbedPane, java.awt.BorderLayout.CENTER);

      jPanel2.setLayout(new java.awt.GridBagLayout());

      jPanel2.setMinimumSize(new java.awt.Dimension(10, 20));
      jPanel2.setPreferredSize(new java.awt.Dimension(100, 36));
      jButton_OK.setFont(new java.awt.Font("Dialog", 0, 12));
      jButton_OK.setText("OK");
      jButton_OK.setMargin(new java.awt.Insets(2, 24, 2, 24));
      jButton_OK.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_OKActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
      jPanel2.add(jButton_OK, gridBagConstraints);

      jButton_Cancel.setFont(new java.awt.Font("Dialog", 0, 12));
      jButton_Cancel.setText("Cancel");
      jButton_Cancel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_CancelActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
      jPanel2.add(jButton_Cancel, gridBagConstraints);

      getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButton_GiveMasterPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GiveMasterPasswordActionPerformed
     if (
       Wallet.anyAuthInfo()&&   
       JOptionPane.showConfirmDialog(
           this,
           "All the wallet data will be lost.\n"+
           "Are you sure that you want to change the wallet password?",
           "Alert",
           JOptionPane.YES_NO_OPTION,
           JOptionPane.ERROR_MESSAGE
       )==JOptionPane.NO_OPTION
     ) 
       return;
     
     SetMasterPassword_Dialog mpdlg=new SetMasterPassword_Dialog(this); 
     mpdlg.setVisible(true);
     if (mpdlg.isAccepted()) {
        Wallet.setMasterPassword(mpdlg.getPassword());
        jButton_dropAllAuthInfo.setEnabled(false);
     }
   }//GEN-LAST:event_jButton_GiveMasterPasswordActionPerformed

   private void jButton_dropAllAuthInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_dropAllAuthInfoActionPerformed
     if (
       JOptionPane.showConfirmDialog(
           this,
           "Are you sure that you want to destory all the wallet data?",
           "Alert",
           JOptionPane.YES_NO_OPTION,
           JOptionPane.ERROR_MESSAGE
       )==JOptionPane.YES_OPTION
     ) {
       Wallet.dropAllAuthInfo();
       jButton_dropAllAuthInfo.setEnabled(false);
     }
   }//GEN-LAST:event_jButton_dropAllAuthInfoActionPerformed

   private void checkbox_useTheProxyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbox_useTheProxyActionPerformed
      boolean checked=checkbox_useTheProxy.isSelected();
      jTextField_proxyAddress.setEnabled(checked);
      jTextField_proxyPort.setEnabled(checked);
   }//GEN-LAST:event_checkbox_useTheProxyActionPerformed

   
   final static String 
      PROXY_SET="http.proxySet",
      PROXY_HOST="http.proxyHost",
      PROXY_PORT="http.proxyPort"
   ;      
     
   public static void setConnectionOptions() {
     if ("true".equals(proxyProps.getProperty(PROXY_SET))) {
       System.setProperty(PROXY_HOST,proxyProps.getProperty(PROXY_HOST));
       System.setProperty(PROXY_PORT,proxyProps.getProperty(PROXY_PORT));
       System.setProperty("http.proxyType", "4");
       System.setProperty(PROXY_SET,"true");
       TheAuthenticator.install();
     } else {
       Properties sys=System.getProperties();
       if (sys.containsKey(PROXY_HOST)) sys.remove(PROXY_HOST);
       if (sys.containsKey(PROXY_PORT)) sys.remove(PROXY_PORT);
       if (sys.containsKey(PROXY_SET)) sys.remove(PROXY_SET);
       if (sys.containsKey("http.proxyType")) sys.remove("http.proxyType");
       TheAuthenticator.uninstall();
     }
   }
   
   private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed
     boolean checked=checkbox_useTheProxy.isSelected();
     
     String addr=denullify(jTextField_proxyAddress.getText()),
            port=denullify(jTextField_proxyPort.getText());
     
     if (checked) try {
       //vfy that the proxy is valid
       Socket sock=new Socket(addr,Integer.parseInt(port));
       if (sock==null||!sock.isConnected()) 
         throw new Exception();
     } catch (Throwable t) {
       JOptionPane.showMessageDialog(this,"The Specified Proxy is not Available","Alert",JOptionPane.ERROR_MESSAGE);
       return;
     }
     
     proxyProps.setProperty(PROXY_SET,checked?"true":"fase");
     proxyProps.setProperty(PROXY_HOST,addr);
     proxyProps.setProperty(PROXY_PORT,port);
     setConnectionOptions();
     try {
        FileOutputStream os=new FileOutputStream(proxyPropsFile);
        proxyProps.store(os,"proxy settings ...");
        os.close();
        setVisible(false);
     } catch (Throwable t) {}   
   }//GEN-LAST:event_jButton_OKActionPerformed

   private void jButton_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelActionPerformed
// TODO add your handling code here:
      setVisible(false);
   }//GEN-LAST:event_jButton_CancelActionPerformed
 
   
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JPanel addressPane;
   private javax.swing.JCheckBox checkbox_useTheProxy;
   private javax.swing.JButton jButton_Cancel;
   private javax.swing.JButton jButton_GiveMasterPassword;
   private javax.swing.JButton jButton_OK;
   private javax.swing.JButton jButton_dropAllAuthInfo;
   private javax.swing.JLabel jLabel_address;
   private javax.swing.JLabel jLabel_port;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel_Connection;
   private javax.swing.JPanel jPanel_Waller;
   private javax.swing.JTabbedPane jTabbedPane;
   private javax.swing.JTextField jTextField_proxyAddress;
   private javax.swing.JTextField jTextField_proxyPort;
   private javax.swing.JPanel portPane;
   // End of variables declaration//GEN-END:variables
   
}
