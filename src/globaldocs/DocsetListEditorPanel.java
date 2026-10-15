/*
 * DocsetListEditorPanel.java
 *
 * Created on February 8, 2006, 12:39 PM
 *
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
import java.awt.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.util.*;
import static globaldocs.MainFrame.*;
import static globaldocs.Utils.*;
import javax.swing.event.*;
import java.net.*;


/**
 *
 * @author  zeev
 */
public class DocsetListEditorPanel extends javax.swing.JPanel {
   
   TheTableModel dataModel;
   
   /** Creates new form DocsetListEditorPanel */
   public DocsetListEditorPanel() {
      initComponents();
         
      jTable_sources.setModel(dataModel=new TheTableModel(frame.assembles));
      ListSelectionModel sm=jTable_sources.getSelectionModel();
      sm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      sm.addListSelectionListener(
         new ListSelectionListener() {
           public void valueChanged(ListSelectionEvent e) {
             int row=jTable_sources.getSelectedRow();
             boolean f=row>=0;
             A sel=dataModel.getSelection();
             if (f) f=!sel.jPackage;
             if (f) {
               if (!sel.isJ2se_docset()||dataModel.j2seDocsetCount()>1) { 
                 jButton_remove.setEnabled(true);
               }
             } else {
               jButton_remove.setEnabled(false); 
             }   
             jButton_edit.setEnabled(f);
           }
         }   
      );
      
      TableColumnModel tcm=jTable_sources.getColumnModel();
      
      DefaultTableCellRenderer tcr=new DefaultTableCellRenderer() {
         public Component getTableCellRendererComponent(
           JTable table, Object value,
           boolean isSelected, boolean hasFocus, int row, int column) 
         {
           Component c=super.getTableCellRendererComponent(
               jTable_sources, value,
               isSelected,
               hasFocus, 
               row,
               column   
            );
           c.setFont(smallBoldFont);
           return c;
         }
      };
      
      JTableHeader th=jTable_sources.getTableHeader();
      
/*    
      th.setBorder(
          new CompoundBorder(
            new javax.swing.border.MatteBorder(0,0,1,0,new Color(122,138,153)),
            new javax.swing.border.MatteBorder(0,0,1,0,Color.WHITE)
          )
      );
  */ 
      th.setReorderingAllowed(false);
      th.setResizingAllowed(false);
     
    
      
      tcr.setBackground(th.getBackground());
      tcr.setForeground(th.getForeground());
      
      tcm.getColumn(0).setHeaderRenderer(tcr);
      tcm.getColumn(0).sizeWidthToFit();
      tcm.getColumn(1).setHeaderRenderer(tcr);
      tcm.getColumn(1).sizeWidthToFit();
      tcm.getColumn(2).setHeaderRenderer(tcr);

 /*     
      JCheckBox cb=new JCheckBox();
      tcm.getColumn(1).setCellEditor(
        new DefaultCellEditor(cb) {        
           public boolean isCellEditable(EventObject anEvent) {
              A a=dataModel.getSelection();
              if (a!=null) 
                 return !(a.jPackage||isLocalUrl(a.urlBase));
              return true;
          }
        }
      );
 */     
      
      tcm.getColumn(2).setCellEditor(
        new DefaultCellEditor(new JTextField()) {
         public boolean isCellEditable(EventObject anEvent) {
            return false;
         }
        }
      );
      
      jButton_dropCaches.setEnabled(anyIndexCache());
   }
   

   
   public class TheTableModel extends DefaultTableModel {
      
      
      
     public int j2seDocsetCount() {
       int cnt=0;
       
       for (Object obj:dataVector)
         if (((A)obj).isJ2se_docset()) ++cnt;
           
       return cnt;
     }
     
      public A getSelection() {
        int row=jTable_sources.getSelectedRow();
        return (row>=0)?(A)dataVector.elementAt(row):null;
      }
      
      
      public String getColumnName(int column) {
        String cn=super.getColumnName(column);
        return cn+"  ";
      }
      
      Vector colIDs=new Vector();
      
      public TheTableModel(Collection<A> coll) {
        DefaultTableModel initialTM=(DefaultTableModel)jTable_sources.getModel();
        for (int i=0;i<initialTM.getColumnCount();i++)
          colIDs.add(initialTM.getColumnName(i));
         setDataVector(new Vector(coll),colIDs);
      }
      
      TreeSet<A> removed=new TreeSet<A>(),added=new TreeSet<A>();
      
      Hashtable<A,A> backup=new Hashtable<A,A>();
      
      void doBackup(A orig) {
        if (!backup.containsKey(orig))
             backup.put(orig,orig.clone());
      }
      
      public void doRestore() {
         assembles.removeAll(backup.keySet());
         assembles.addAll(backup.values());
      }
      
      public void removeRow(int row) {
         A a=(A)dataVector.elementAt(row);
         if (a.isJ2se_docset()&&a.isEnabled())
            updateJ2se_docsets(row,false);
         removed.add(a);
         super.removeRow(row);
      }
      
      public boolean contains(A a) {
        for (A o:(Vector<A>)dataVector) if (a.compareTo(o)==0) return true;
        return false;
      }
      
      public boolean containsMore(A needle,A sel) {
        for (A o:(Vector<A>)dataVector) if (o!=sel&&needle.compareTo(o)==0) return true;
        return false;
      }
      
      public void addNew(A a) {
        TreeSet<A> aset=new TreeSet<A>(dataVector);
        a.setManaged(false);
        aset.add(a);
        added.add(a);
        dataVector=new Vector<A>(aset);
        
        
        int row=-1;
        
        for (int i=0;i<dataVector.size();i++) 
           if (a==dataVector.get(i)) {row=i; break;}

        
        if (row>=0) {
           
  //        if (a.isJ2se_docset()) updateJ2se_docsets(row,true);
           
          fireTableRowsInserted(row,row);
          jTable_sources.getSelectionModel().setSelectionInterval(row,row);
          jTable_sources.scrollRectToVisible(jTable_sources.getCellRect(row,0,true));
        }
        
      }
      
      public A getRow(int row) {
         if (dataVector!=null&&row>=0&&row<dataVector.size())
           return (A)dataVector.elementAt(row);
         return null;
      }
      
      public Object getValueAt(int row, int column) {
        A  a=(A)dataVector.elementAt(row);
        switch (column) {
           case 0: return a.isEnabled(); 
           case 1: return a.isManaged();
           case 2: return a.getVersionedName(); 
        }
        return null;
      }
      
      public void updateJ2se_docsets(A a,boolean v) {
        int row=dataVector.indexOf(a);
        if (row>=0) updateJ2se_docsets(row,v);
      }
      
      public void updateJ2se_docsets(int row,boolean v) {
         for (int k=0;k<dataVector.size();k++) if (k!=row) {
            A x=(A)dataVector.elementAt(k);
            if (x.isJ2se_docset()) {
               if (v&&x.isEnabled()) {
                  doBackup(x);
                  x.setEnabled(false);
                  fireTableCellUpdated(k,0);
               } else if (!v&&!x.isEnabled()) {
                  doBackup(x);
                  x.setEnabled(true);
                  fireTableCellUpdated(k,0);
                  break;
               }
            }
         }
      }
            
      public void setValueAt(Object aValue,int row,int column) {
        A  a=(A)dataVector.elementAt(row);
        boolean v=(Boolean)aValue;
        switch (column) {
           case 0: {
                    boolean oldEnabled=a.isEnabled();
                    if (oldEnabled!=v) {
                      doBackup(a);
                      a.setEnabled(v); 
                      if (a.isJ2se_docset()) updateJ2se_docsets(row,v);
                    }
                    break;
                  } 
           case 1: if (a.isManaged()!=v) doBackup(a);
                   a.setManaged(v);
        }       
      }
      public void setDataVector(Vector dataVector, Vector columnIdentifiers) {
        super.dataVector = dataVector;
        super.columnIdentifiers = columnIdentifiers; 
        fireTableStructureChanged();
      }   
      public Class getColumnClass(int columnIndex) {
         return (columnIndex==2)?String.class:Boolean.class;
      }
   }
   
   static JDialog dlg=null;
   
   static void invokeTheDialog() {
      dlg=new JDialog(frame,"The Javadoc Sources",true);
      dlg.setResizable(false);
      
      DocsetListEditorPanel dlep=new DocsetListEditorPanel();
      
      dlg.setContentPane(dlep);
      
         
      
      dlg.getRootPane().setDefaultButton(dlep.jButton_cancel);
      
      
      dlg.pack();
      Dimension sz=dlg.getSize();
      dlg.setLocation(
        (screenDimension.width-sz.width)>>1,
        (screenDimension.height-sz.height)>>1
      );
      dlg.setVisible(true);
      dlg.dispose();      
      
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
        jTable_sources = new TheTable();
        jButton_OK = new javax.swing.JButton();
        jButton_add = new javax.swing.JButton();
        jButton_edit = new javax.swing.JButton();
        jButton_remove = new javax.swing.JButton();
        jButton_cancel = new javax.swing.JButton();
        jButton_dropCaches = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        setMinimumSize(new java.awt.Dimension(590, 320));
        setPreferredSize(new java.awt.Dimension(600, 310));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(122, 138, 153)));
        jTable_sources.setBackground(new java.awt.Color(238, 238, 238));
        jTable_sources.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Enabled", "Managed", "Dataset"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_sources.setFocusable(false);
        jTable_sources.setIntercellSpacing(new java.awt.Dimension(2, 1));
        jTable_sources.setOpaque(false);
        jTable_sources.setShowHorizontalLines(false);
        jTable_sources.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jTable_sources);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 407;
        gridBagConstraints.ipady = 252;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        add(jScrollPane1, gridBagConstraints);

        jButton_OK.setFont(new java.awt.Font("Dialog", 0, 12));
        jButton_OK.setText("OK");
        jButton_OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OKActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 37;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 350, 6, 0);
        add(jButton_OK, gridBagConstraints);

        jButton_add.setFont(new java.awt.Font("Dialog", 0, 12));
        jButton_add.setText("Add Docset ...");
        jButton_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 9);
        add(jButton_add, gridBagConstraints);

        jButton_edit.setFont(new java.awt.Font("Dialog", 0, 12));
        jButton_edit.setText("Edit ..");
        jButton_edit.setEnabled(false);
        jButton_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_editActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 62;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 9);
        add(jButton_edit, gridBagConstraints);

        jButton_remove.setFont(new java.awt.Font("Dialog", 0, 12));
        jButton_remove.setText("Remove");
        jButton_remove.setEnabled(false);
        jButton_remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_removeActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 47;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 9);
        add(jButton_remove, gridBagConstraints);

        jButton_cancel.setFont(new java.awt.Font("Dialog", 0, 12));
        jButton_cancel.setText("Cancel");
        jButton_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 6, 9);
        add(jButton_cancel, gridBagConstraints);

        jButton_dropCaches.setFont(new java.awt.Font("Dialog", 0, 12));
        jButton_dropCaches.setText("Clear Caches");
        jButton_dropCaches.setMaximumSize(new java.awt.Dimension(130, 25));
        jButton_dropCaches.setMinimumSize(new java.awt.Dimension(130, 25));
        jButton_dropCaches.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_dropCachesActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        add(jButton_dropCaches, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

   private void jButton_dropCachesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_dropCachesActionPerformed
// TODO add your handling code here:
      removeAllIndexCaches();
      jButton_dropCaches.setEnabled(false);
   }//GEN-LAST:event_jButton_dropCachesActionPerformed

   private void jButton_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addActionPerformed
// TODO add your handling code here:
      editDocsetDescription(null,dataModel);
   }//GEN-LAST:event_jButton_addActionPerformed

   
   static class TheDocsetEditor extends DocsetEditor_Dialog {
      private void adjustLocation() {
         Dimension sz=getSize();
         setLocation(
               (screenDimension.width-sz.width)>>1,
               (screenDimension.height-sz.height)>>1
               );
      }
      
      A sel;
      TheTableModel dataModel=null;
      boolean selIsNew,old_j2se;
      
      public void setDataModel(TheTableModel dataModel) {
        this.dataModel=dataModel; 
      }
      
      public TheDocsetEditor(JDialog dlg,A sel,boolean selIsNew) {
        super(dlg,true); 
        adjustLocation();
        this.sel=sel;
        this.selIsNew=selIsNew;
        old_j2se=sel.isJ2se_docset();
      }
      
      public TheDocsetEditor(JFrame frm,A sel,boolean selIsNew) {
        super(frm,true);
        adjustLocation();
        this.sel=sel;
        this.selIsNew=selIsNew;
        old_j2se=sel.isJ2se_docset();
      }
      
      protected boolean onOK(java.awt.event.ActionEvent evt) {
             String 
                baseUrl=nullify(getBaseUrl()),
                version=nullify(getVersion()),
                projectID=nullify(getProjectID())
             ;
             boolean
                baseUrl_isnull=baseUrl==null,
                version_isnull=version==null,
                projectID_isnull=projectID==null
             ;      
             
             if (baseUrl_isnull||version_isnull||projectID_isnull) {
               String msg="";
               if (baseUrl_isnull)   msg+="Javadoc Set Base Should not be Empty\n"; 
               if (version_isnull)   msg+="The Version Should not be Empty\n"; 
               if (projectID_isnull) msg+="The Project ID Should not be Empty\n";
               JOptionPane.showMessageDialog(this,msg,"Alert",JOptionPane.ERROR_MESSAGE);
               
               return false;
             }
             
             if (!baseUrl.endsWith("/")) baseUrl+="/";
             boolean 
               fLastJ2se=dataModel!=null&&(dataModel.j2seDocsetCount()==1&&sel.isJ2se_docset()),
               f=checkDocsetBase(baseUrl);
             
             if (fLastJ2se&&!(f&&isJ2seBase(baseUrl,true))) {
                JOptionPane.showMessageDialog(
                     this,
                     "Can Not Open The Javadoc Set Base.\n"+
                     "The Last J2SE Docset Must Be Valid.",
                     "Alert",
                      JOptionPane.ERROR_MESSAGE
                );
                return false;
             }             
             
             if (!f) {
                  f=JOptionPane.showConfirmDialog(
                    this,
                      "Can Not Open The Javadoc Set Base.\n"+
                      "Are you sure that you want to leave it so?",
                    "Alert",
                    JOptionPane.YES_NO_OPTION,  
                    JOptionPane.ERROR_MESSAGE
                  )==JOptionPane.YES_OPTION;
                  
             }
             if (f) {
                URL u=null;
                
                try {
                  u=mkUrl(getHomeUrl());
                } catch (Throwable t) {}
                
                if (u!=null) f=checkUrl(u);
                
                if (!f) {
                   f=JOptionPane.showConfirmDialog(
                         this,
                         "Can Not Open The Project Documentation Home URL.\n"+
                         "Are you sure that you want to leave it so?",
                         "Alert",
                         JOptionPane.YES_NO_OPTION,
                         JOptionPane.ERROR_MESSAGE
                         )==JOptionPane.YES_OPTION;
                }
                
                if (f) {
                   
                   A needle=new A();
                   
                   needle.setVersion(getVersion());
                   needle.setProjectID(getProjectID());
                   //needle.setBaseUrl(baseUrl);
                   //needle.setHomeUrl(u);
                   
                   f=!(
                     (dataModel==null&&MainFrame.assembles.contains(needle))||
                     (dataModel!=null&&selIsNew&&dataModel.contains(needle))
                   );
                   
                   if (f&&dataModel!=null&&!selIsNew&&dataModel.containsMore(needle,sel)) f=false;
                   
                   if (!f){
                      JOptionPane.showMessageDialog(this,
                         "A Docset With Such ID and Version Already Exists",
                         "Alert",JOptionPane.ERROR_MESSAGE
                      );
                      return false;
                   }
                   
                   if (!selIsNew&&dataModel!=null) dataModel.doBackup(sel);
                   
                   sel.setVersion(getVersion());
                   sel.setProjectID(getProjectID());
                   sel.setBaseUrl(baseUrl);
                   sel.setHomeUrl(u);
                   
                   
                   if (selIsNew&&dataModel!=null) 
                        dataModel.addNew(sel);
                }
             }
             if (f&&(selIsNew||!fLastJ2se)) {
               sel.setJ2se_docset(isJ2seBase(baseUrl,true));
             }
             if (f&&(old_j2se!=sel.isJ2se_docset())&&dataModel!=null)
                dataModel.updateJ2se_docsets(sel,sel.isJ2se_docset());
             accepted=f;
             return f;
      }      
      
      boolean accepted=false;
      
      public boolean isAccepted() {
        return accepted;
      }
      
      public static boolean doEditing(JDialog dlg,A _sel,TheTableModel dataModel) {
         A sel=_sel==null?new A():_sel;
         return doEditing(
               new TheDocsetEditor(dlg,sel,_sel==null),
               _sel,sel,dataModel,false
         );  
      }

      public static boolean doEditing(JFrame frm,A sel) {
         return doEditing(
               new TheDocsetEditor(frm,sel,true),
               null,sel,null,true
         );  
      }

      
      private static boolean doEditing(TheDocsetEditor editDlg,A _sel,A sel,TheTableModel dataModel,boolean doCapture) {
         editDlg.setDataModel(dataModel);
      
         editDlg.setVersion(sel.version);
         editDlg.setBaseUrl(sel.urlBase);
         URL  home=sel.getHome();
         editDlg.setHomeUrl((home==null)?"":home.toString());
         editDlg.setProjectID(sel.fullName);
         if (doCapture) editDlg.captureDocsetNameVersion();
         editDlg.setVisible(true);
         return editDlg.isAccepted();    
      }    
      
      
   };
   
   private boolean editDocsetDescription(A _sel,TheTableModel dataModel) {
     return TheDocsetEditor.doEditing(dlg,_sel,dataModel);
   }

   public class TheTable extends javax.swing.JTable {
      
     public TableCellRenderer getCellRenderer(int row,int column) {
       final TableCellRenderer delegated=super.getCellRenderer(row,column);
       return new TableCellRenderer() {
         
         public java.awt.Component getTableCellRendererComponent(
           JTable table, Object value,
	   boolean isSelected, boolean hasFocus, 
	   int row, int column
         ) {
           java.awt.Component c=delegated.
             getTableCellRendererComponent(
               table,value,
	       isSelected,hasFocus, 
	       row,column    
             )    
           ;
           if (column<2) c.setEnabled(
               isCellEditable(row,column)  
           );
           return c;
         } 
          
       };
     }
      
     public boolean isCellEditable(int row,int column) {
       boolean f=super.isCellEditable(row,column);
       if (f) { 
         A a=DocsetListEditorPanel.this.dataModel.getRow(row);
         if (a!=null) {
            if (a.isLocal()&&column==1) return false;
            if (column==0&&
              a.isJ2se_docset()&&a.isEnabled()&&
              DocsetListEditorPanel.this.dataModel.j2seDocsetCount()==1
            )
              return false;
         }
       }      
       return f;
     }

   }         
   
   private void jButton_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_editActionPerformed
     editDocsetDescription(dataModel.getSelection(),dataModel);
   }//GEN-LAST:event_jButton_editActionPerformed

   private void jButton_removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_removeActionPerformed
// TODO add your handling code here:
      int srow=jTable_sources.getSelectedRow();
      if (srow>=0) {
        dataModel.removeRow(srow);
      }
   }//GEN-LAST:event_jButton_removeActionPerformed

   private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed
// TODO add your handling code here:
      getTopLevelAncestor().setVisible(false);
      frame.assembles.removeAll(dataModel.removed);
      frame.assembles.addAll(dataModel.added);
      frame.adjustTheTree();
      frame.syncTheConfigurationWithTheTree();
      frame.saveConfig(frame.configuration);
   }//GEN-LAST:event_jButton_OKActionPerformed

   private void jButton_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelActionPerformed
// TODO add your handling code here:
     dataModel.doRestore(); 
     getTopLevelAncestor().setVisible(false);
   }//GEN-LAST:event_jButton_cancelActionPerformed
   
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_OK;
    private javax.swing.JButton jButton_add;
    private javax.swing.JButton jButton_cancel;
    private javax.swing.JButton jButton_dropCaches;
    private javax.swing.JButton jButton_edit;
    private javax.swing.JButton jButton_remove;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_sources;
    // End of variables declaration//GEN-END:variables
   
}
