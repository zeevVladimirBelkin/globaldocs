/*
 * SelectZippedDocset.java
 *
 * Created on March 24, 2006, 5:32 PM
 
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

package globaldocs;

import javax.swing.*;
import static globaldocs.Utils.*;
import java.awt.Dimension;
import java.util.*;

/**
 *
 * @author  zeev
 */
public class SelectZippedDocset_Dialog extends javax.swing.JDialog {
   
   
   /** Creates new form SelectZippedDocset */
   public SelectZippedDocset_Dialog(JDialog parent,Vector<String> urls) {
      super(parent, true);
      initComponents();
       
      jList_docsets.setListData(urls);
      jList_docsets.setSelectedIndex(0);
      
      getRootPane().setDefaultButton(jButton_OK);
      
      Dimension sz=getSize();
      setLocation(
               (screenDimension.width-sz.width)>>1,
               (screenDimension.height-sz.height)>>1
               );
   }
   
   /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
   // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
   private void initComponents() {
      java.awt.GridBagConstraints gridBagConstraints;

      jScrollPane1 = new javax.swing.JScrollPane();
      jList_docsets = new javax.swing.JList();
      jButton_OK = new javax.swing.JButton();

      getContentPane().setLayout(new java.awt.GridBagLayout());

      setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
      setTitle("Select One From The Docsets");
      setModal(true);
      setResizable(false);
      jList_docsets.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
      jScrollPane1.setViewportView(jList_docsets);

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 0;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints.ipadx = 428;
      gridBagConstraints.ipady = 158;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.weighty = 1.0;
      gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 13);
      getContentPane().add(jScrollPane1, gridBagConstraints);

      jButton_OK.setText("OK");
      jButton_OK.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_OKActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 1;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.ipadx = 37;
      gridBagConstraints.insets = new java.awt.Insets(10, 0, 7, 0);
      getContentPane().add(jButton_OK, gridBagConstraints);

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed
// TODO add your handling code here:
    setVisible(false);
   }//GEN-LAST:event_jButton_OKActionPerformed
   
   
   public String getSelected() {
     return  (String)jList_docsets.getSelectedValue();
   }
   
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton_OK;
   private javax.swing.JList jList_docsets;
   private javax.swing.JScrollPane jScrollPane1;
   // End of variables declaration//GEN-END:variables
   
}
