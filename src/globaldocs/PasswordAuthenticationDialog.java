package globaldocs;
/*
 * PasswordAuthenticationDialog.java
 *
 * Created on March 21, 2006, 10:52 PM

 * *
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


import static globaldocs.Utils.*; 
import javax.swing.*;
import java.awt.Dimension;

/**
 *
 * @author  zeev
 */
public class PasswordAuthenticationDialog extends javax.swing.JDialog {
   
   /** Creates new form PasswordAuthenticationDialog */
   public PasswordAuthenticationDialog(JFrame frame,String realm,boolean fProxy) {
      super(frame, true);
      initComponents();
      jLabel_realm.setText(realm);
      String s="Authentication Required";
      if (fProxy) s="Proxy "+s;
      setTitle(s);
      
      Dimension sz=getSize();
      setLocation(
               (screenDimension.width-sz.width)>>1,
               (screenDimension.height-sz.height)>>1
               );
      
      getRootPane().setDefaultButton(jButton_OK);
   //   jTextField_password.set
      
   }
   
   /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
   // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
   private void initComponents() {
      java.awt.GridBagConstraints gridBagConstraints;

      jLabel1 = new javax.swing.JLabel();
      jTextField_user = new javax.swing.JTextField();
      jLabel2 = new javax.swing.JLabel();
      jTextField_password = new javax.swing.JPasswordField();
      jCheckBox_keep = new javax.swing.JCheckBox();
      jLabel_realm = new javax.swing.JLabel();
      jButton_OK = new javax.swing.JButton();
      jButton_Cancel = new javax.swing.JButton();

      getContentPane().setLayout(new java.awt.GridBagLayout());

      setModal(true);
      setResizable(false);
      jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
      jLabel1.setText("User ID:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 1;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.ipadx = 14;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 0);
      getContentPane().add(jLabel1, gridBagConstraints);

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 2;
      gridBagConstraints.gridwidth = 8;
      gridBagConstraints.ipadx = 406;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 11);
      getContentPane().add(jTextField_user, gridBagConstraints);

      jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
      jLabel2.setText("Password:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 3;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
      getContentPane().add(jLabel2, gridBagConstraints);

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 4;
      gridBagConstraints.gridwidth = 8;
      gridBagConstraints.ipadx = 406;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 11);
      getContentPane().add(jTextField_password, gridBagConstraints);

      jCheckBox_keep.setText("keep the authentication info");
      jCheckBox_keep.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
      jCheckBox_keep.setMargin(new java.awt.Insets(0, 0, 0, 0));
      jCheckBox_keep.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jCheckBox_keepActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 5;
      gridBagConstraints.gridwidth = 3;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(21, 10, 0, 0);
      getContentPane().add(jCheckBox_keep, gridBagConstraints);

      jLabel_realm.setFont(new java.awt.Font("Dialog", 0, 12));
      jLabel_realm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel_realm.setText("the realm");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 0;
      gridBagConstraints.gridwidth = 7;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(20, 120, 0, 0);
      getContentPane().add(jLabel_realm, gridBagConstraints);

      jButton_OK.setText("OK");
      jButton_OK.setPreferredSize(new java.awt.Dimension(60, 25));
      jButton_OK.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_OKActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 6;
      gridBagConstraints.gridy = 6;
      gridBagConstraints.ipadx = 27;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(5, 7, 20, 0);
      getContentPane().add(jButton_OK, gridBagConstraints);

      jButton_Cancel.setText("Cancel");
      jButton_Cancel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_CancelActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 7;
      gridBagConstraints.gridy = 6;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.ipadx = 5;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(5, 20, 20, 11);
      getContentPane().add(jButton_Cancel, gridBagConstraints);

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed
      String
        user=nullify(jTextField_user.getText()),
        password=nullify(jTextField_password.getText());
      
      if (user==null||password==null) {
         JOptionPane.showMessageDialog(
            this,
            "The User ID and Password Should not be Empty",
            "Alert",
            JOptionPane.ERROR_MESSAGE
         );
         return;
      }
      accepted=true;
      setVisible(false);
   }//GEN-LAST:event_jButton_OKActionPerformed

   private void jCheckBox_keepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_keepActionPerformed
     shouldRemember=jCheckBox_keep.isSelected();
   }//GEN-LAST:event_jCheckBox_keepActionPerformed

   private void jButton_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelActionPerformed
      setVisible(false);
   }//GEN-LAST:event_jButton_CancelActionPerformed
  
   
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton_Cancel;
   private javax.swing.JButton jButton_OK;
   private javax.swing.JCheckBox jCheckBox_keep;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel_realm;
   private javax.swing.JPasswordField jTextField_password;
   private javax.swing.JTextField jTextField_user;
   // End of variables declaration//GEN-END:variables

   /**
    * Holds value of property accepted.
    */
   private boolean accepted=false;

   /**
    * Getter for property accepted.
    * @return Value of property accepted.
    */
   public boolean isAccepted() {
      return this.accepted;
   }

   /**
    * Holds value of property shouldRemember.
    */
   private boolean shouldRemember=false;

   /**
    * Getter for property shouldRemember.
    * @return Value of property shouldRemember.
    */
   public boolean isShouldRemember() {
      return this.shouldRemember;
   }

   public 
   String getUserID() {
      return jTextField_user.getText().trim();
   }
    
   public String getPassword() {
      return jTextField_password.getText().trim();
   }

}
