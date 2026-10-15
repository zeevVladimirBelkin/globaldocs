/*
 * DocsetEditor_Dialog.java
 *
 * Created on February 13, 2006, 10:52 PM
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

import javax.swing.*;
import static globaldocs.MainFrame.*;
import static globaldocs.Utils.*;
import java.io.*;
import java.util.zip.*;
import java.util.Enumeration;
import java.net.*;
import static javax.swing.text.html.HTMLEditorKit.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.util.Vector;
import java.util.TreeSet;


/**
 *
 * @author  zeev
 */
public class DocsetEditor_Dialog extends javax.swing.JDialog {

   static class JavadocBaseFileFilter extends javax.swing.filechooser.FileFilter {
    /**
     * Whether the given file is accepted by this filter.
     */
       public boolean accept(File f) {
          if (f.isDirectory()) return true;
          String name=f.getName().toLowerCase();
          return 
            (name.endsWith(".zip")||name.endsWith(".jar"))&&
               isJavadocZip(f);
       }

    /**
     * The description of this filter. For example: "JPG and GIF Images"
     * @see FileView#getName
     */
       public  String getDescription() {
          return  "Docset Directory, ZIP or JAR";
       }
      
   }

   
   
   static class HtmlFileFilter extends javax.swing.filechooser.FileFilter {
    /**
     * Whether the given file is accepted by this filter.
     */
       public boolean accept(File f) {
          if (f.isDirectory()) return true;
          String name=f.getName().toLowerCase();
          return 
            name.endsWith(".html")||name.endsWith(".htm");
       }

    /**
     * The description of this filter. For example: "JPG and GIF Images"
     * @see FileView#getName
     */
       public  String getDescription() {
          return  "HTML file";
       }
      
   }
   
   
   
   /**
    * Creates new form DocsetEditor_Dialog
    */
   public DocsetEditor_Dialog(JDialog parent, boolean modal) {
      super(parent,modal);
      initComponents();
      getRootPane().setDefaultButton(jButton_OK);
   }
   
   public DocsetEditor_Dialog(JFrame parent, boolean modal) {
      super(parent,modal);
      initComponents();
      getRootPane().setDefaultButton(jButton_OK);
   }
   /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
   // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
   private void initComponents() {
      java.awt.GridBagConstraints gridBagConstraints;

      jTextField_base = new javax.swing.JTextField();
      jLabel1 = new javax.swing.JLabel();
      jTextField_projectID = new javax.swing.JTextField();
      jLabel2 = new javax.swing.JLabel();
      jTextField_ver = new javax.swing.JTextField();
      jLabel3 = new javax.swing.JLabel();
      jButton_OK = new javax.swing.JButton();
      jButton1 = new javax.swing.JButton();
      jButton_SelectHomeUrl = new javax.swing.JButton();
      jLabel4 = new javax.swing.JLabel();
      jTextField_homeURL = new javax.swing.JTextField();
      jButton_SelectBaseUrl = new javax.swing.JButton();

      getContentPane().setLayout(new java.awt.GridBagLayout());

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Enter The Attributes");
      setResizable(false);
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 1;
      gridBagConstraints.gridwidth = 20;
      gridBagConstraints.ipadx = 406;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
      getContentPane().add(jTextField_base, gridBagConstraints);

      jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
      jLabel1.setText("Javadoc Set Base URL:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 0;
      gridBagConstraints.gridwidth = 2;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
      getContentPane().add(jLabel1, gridBagConstraints);

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 4;
      gridBagConstraints.gridwidth = 4;
      gridBagConstraints.ipadx = 276;
      gridBagConstraints.ipady = 1;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
      getContentPane().add(jTextField_projectID, gridBagConstraints);

      jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
      jLabel2.setText("Project ID:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 3;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
      getContentPane().add(jLabel2, gridBagConstraints);

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 7;
      gridBagConstraints.gridy = 4;
      gridBagConstraints.gridwidth = 13;
      gridBagConstraints.ipadx = 106;
      gridBagConstraints.ipady = 1;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 0);
      getContentPane().add(jTextField_ver, gridBagConstraints);

      jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
      jLabel3.setText("Version:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 7;
      gridBagConstraints.gridy = 3;
      gridBagConstraints.gridwidth = 6;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(10, 30, 0, 0);
      getContentPane().add(jLabel3, gridBagConstraints);

      jButton_OK.setFont(new java.awt.Font("Dialog", 0, 12));
      jButton_OK.setText("OK");
      jButton_OK.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_OKActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 3;
      gridBagConstraints.gridy = 8;
      gridBagConstraints.gridwidth = 5;
      gridBagConstraints.ipadx = 17;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(10, 27, 12, 0);
      getContentPane().add(jButton_OK, gridBagConstraints);

      jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
      jButton1.setText("Cancel");
      jButton1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 12;
      gridBagConstraints.gridy = 8;
      gridBagConstraints.gridwidth = 7;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(10, 10, 12, 0);
      getContentPane().add(jButton1, gridBagConstraints);

      jButton_SelectHomeUrl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/win-explorer-opened-folder.gif")));
      jButton_SelectHomeUrl.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_SelectHomeUrlActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 20;
      gridBagConstraints.gridy = 5;
      gridBagConstraints.gridheight = 3;
      gridBagConstraints.ipadx = -20;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(24, 10, 0, 16);
      getContentPane().add(jButton_SelectHomeUrl, gridBagConstraints);

      jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
      jLabel4.setText("Project Documentation Home URL:");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 5;
      gridBagConstraints.gridwidth = 3;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
      getContentPane().add(jLabel4, gridBagConstraints);

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 6;
      gridBagConstraints.gridwidth = 20;
      gridBagConstraints.ipadx = 406;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
      getContentPane().add(jTextField_homeURL, gridBagConstraints);

      jButton_SelectBaseUrl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/win-explorer-opened-folder.gif")));
      jButton_SelectBaseUrl.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton_SelectBaseUrlActionPerformed(evt);
         }
      });

      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 20;
      gridBagConstraints.gridy = 0;
      gridBagConstraints.gridheight = 3;
      gridBagConstraints.ipadx = -20;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints.insets = new java.awt.Insets(24, 10, 0, 16);
      getContentPane().add(jButton_SelectBaseUrl, gridBagConstraints);

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButton_SelectHomeUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SelectHomeUrlActionPerformed
     JFileChooser fc=new JFileChooser();
     
     fc.setDialogTitle("Select an HTML File");
     fc.setFileSelectionMode(fc.FILES_ONLY);
     fc.removeChoosableFileFilter(fc.getFileFilter());
     fc.setFileFilter(new HtmlFileFilter());
     if (isAValidDir(lastdir)) fc.setCurrentDirectory(lastdir);
     
     if (fc.showOpenDialog(this)==fc.APPROVE_OPTION) try {
       String cp=fc.getSelectedFile().getCanonicalPath().replace('\\','/');
       setHomeUrl("file:"+cp);
     } catch (Throwable t) {}
     lastdir=fc.getCurrentDirectory();
   }//GEN-LAST:event_jButton_SelectHomeUrlActionPerformed

   
   static File roots[]=null;
   
   static boolean isARoot(File f) {
     if (roots==null) roots=f.listRoots();
     for (File fi:roots) if (fi.equals(f)) return true;
     return false;
   }
   
   public String javadocUrlFromFile(File f) {
     try { 
       if (f.isDirectory()) {
          if (isARoot(f)) return null;
          if (f.exists()&&new File( f,"allclasses-frame.html").exists()) {
            String cp=f.getCanonicalPath().replace('\\','/');
            if (!cp.startsWith("/")) cp="/"+cp;
            return "file:"+cp+'/';
          }
          return null;
       }   
       return javadocUrlFromZip(f);
     } catch (Throwable t) {
     }   
     return null;
   }  
   
   public static Vector<String> javadocUrlsFromZip(File f) {
      Vector<String> rv=new Vector<String>();
      try { 
        ZipFile z=new ZipFile(f,ZipFile.OPEN_READ);
        Enumeration<? extends ZipEntry> en=z.entries();
        while (en.hasMoreElements()) {
          ZipEntry ze=en.nextElement();
          if (!ze.isDirectory()) {
            String fn=ze.getName();
            if (fn.endsWith("allclasses-frame.html")) {
              String cp=f.getCanonicalPath().replace('\\','/');
              if (!cp.startsWith("/")) cp="/"+cp;
              String url="jar:file:"+cp+"!/"+fn;
              rv.add(url.substring(0,url.lastIndexOf('/')+1));
            }  
          }  
        }
        z.close();
      } catch (Exception e) {
      }
      return rv;
   }
   
    public static boolean isJavadocZip(File f) {
      try { 
        ZipFile z=new ZipFile(f,ZipFile.OPEN_READ);
        Enumeration<? extends ZipEntry> en=z.entries();
        while (en.hasMoreElements()) {
          ZipEntry ze=en.nextElement();
          if (!ze.isDirectory()) {
            String fn=ze.getName();
            if (fn.endsWith("allclasses-frame.html")) return true;
          }  
        }
        z.close();
      } catch (Exception e) {
      }
      return false;
   }
   
   public String javadocUrlFromZip(File f) {
      Vector<String> urls=javadocUrlsFromZip(f);
      if (urls.size()==1) return urls.firstElement();
      if (urls.size()>1) {
         SelectZippedDocset_Dialog d=new SelectZippedDocset_Dialog(this,urls);
         d.setVisible(true);
         return d.getSelected();
      }
      return null;
   }
   
   private static File lastdir=null; 
   
   private void jButton_SelectBaseUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SelectBaseUrlActionPerformed
// TODO add your handling code here:
      JFileChooser fc=new JFileChooser() {
         
        public Icon getIcon(File f) {
          if (isJavadocZip(f))
             return MainFrame.frame.icCup;
          return super.getIcon(f); 
        } 
        
        public void approveSelection() {
          File f=getSelectedFile();
          if (f!=null) {
            if (f.isDirectory()) { 
               if (!new File(f,"allclasses-frame.html").exists()) {
                 JOptionPane.showMessageDialog(
                   this,
                   "Javadoc Set Directory Must Contain File \"allclasses-frame.html\"",
                   "Alert",
                   JOptionPane.ERROR_MESSAGE
                 );
                 return;
               } 
            } else {
             // f -- should be a zip/jar file containing  a Javadoc Set Directory
              if (!isJavadocZip(f)) {
                 JOptionPane.showMessageDialog(
                   this,
                   "Javadoc Set JAR/ZIP Must Contain File \"allclasses-frame.html\"",
                   "Alert",
                   JOptionPane.ERROR_MESSAGE
                 );
                 return;
              }  
            }   
          }
          super.approveSelection();
        }
      };
      
      fc.setDialogTitle("Select Directory,JAR or ZIP");
      fc.setFileSelectionMode(fc.FILES_AND_DIRECTORIES);
      fc.removeChoosableFileFilter(fc.getFileFilter());
      fc.setFileFilter(new JavadocBaseFileFilter());
      if (isAValidDir(lastdir)) fc.setCurrentDirectory(lastdir);
      
      if (fc.showOpenDialog(this)==fc.APPROVE_OPTION) {
        String docurl=javadocUrlFromFile(fc.getSelectedFile());
        setBaseUrl(denullify(docurl));
        captureDocsetNameVersion();
      }
      lastdir=fc.getCurrentDirectory();
   }//GEN-LAST:event_jButton_SelectBaseUrlActionPerformed

   public void captureDocsetNameVersion() {
        String docurl=getBaseUrl();
        if (docurl!=null&&nullify(getProjectID())==null) try {
          URL u=new URL(new URL(docurl),"overview-summary.html");
          InputStream is=openStream(u,1000);
          if (is==null) {
            u=new URL(new URL(docurl),"overview-tree.html");
            is=openStream(u,1000);
          }
          if (is!=null) try {
            final Reader r=new InputStreamReader(is);
            
               parser.parse(
                 r,
                 new ParserCallback() {
                   boolean title=false;
                   public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
                     title=t==HTML.Tag.TITLE;
                   }
                   public void handleText(char[] data, int pos) {
                     if (title) try {
                       String s=new String(data)
                         .toLowerCase()
                         .replace("overview","")
                         .replace("javadoc","")
                         .replace("(","")
                         .replace(")","")
                         .replace("generated","")
                         .replace("documentation","")
                         .replace("untitled","")
                         .replace("api","")
                         .replace("reference","")
                         .replace("class","")
                         .replace("hierarchy","")
                         .trim()
                       ;
                       if (s.length()>0) {
                         int i=s.lastIndexOf(' ');
                         if (i>0) {
                           String ss=s.substring(i+1);
                           if (ss.matches("v?\\d+((\\.|-|_)\\w+)*")) {
                             setVersion(ss);
                             s=s.substring(0,i).trim();
                           }
                         }
                         setProjectID(s);
                       }
                       title=false;
                       r.close();
                     } catch (Throwable t) {}
                   }
                 },
                 true
               );
            
          } finally {
            is.close();
          }
        } catch (Throwable t) {
          //System.err.println(t); 
        }     
   }      
   
   private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed
// TODO add your handling code here:
      if (onOK(evt)) setVisible(false);
   }//GEN-LAST:event_jButton_OKActionPerformed

   
   protected boolean onOK(java.awt.event.ActionEvent evt) {
      return true;
   }
   
   private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
      setVisible(false);
   }//GEN-LAST:event_jButton1ActionPerformed
   
   
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton1;
   private javax.swing.JButton jButton_OK;
   private javax.swing.JButton jButton_SelectBaseUrl;
   private javax.swing.JButton jButton_SelectHomeUrl;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JTextField jTextField_base;
   private javax.swing.JTextField jTextField_homeURL;
   private javax.swing.JTextField jTextField_projectID;
   private javax.swing.JTextField jTextField_ver;
   // End of variables declaration//GEN-END:variables

  
   /**
    * Getter for property docset.
    * @return Value of property docset.
    */
   public String getBaseUrl() {
     return jTextField_base.getText();
   }

   /**
    * Setter for property docset.
    * @param docset New value of property docset.
    */
   public void setBaseUrl(String baseUrl) {
     jTextField_base.setText(baseUrl);
   }


   /**
    * Getter for property projectID.
    * @return Value of property projectID.
    */
   public String getProjectID() {
      return jTextField_projectID.getText();
   }

   /**
    * Setter for property projectID.
    * @param projectID New value of property projectID.
    */
   public void setProjectID(String projectID) {
      jTextField_projectID.setText(projectID);
   }


   /**
    * Getter for property version.
    * @return Value of property version.
    */
   public String getVersion() {
      return jTextField_ver.getText();
   }

   /**
    * Setter for property version.
    * @param version New value of property version.
    */
   public void setVersion(String version) {
      jTextField_ver.setText(version);
   }



   /**
    * Getter for property homeUrl.
    * @return Value of property homeUrl.
    */
   public String getHomeUrl() {
      return jTextField_homeURL.getText();
   }

   /**
    * Setter for property homeUrl.
    * @param homeUrl New value of property homeUrl.
    */
   public void setHomeUrl(String homeUrl) {
      jTextField_homeURL.setText(homeUrl);
   }
   
}
