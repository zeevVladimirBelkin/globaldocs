/*
 * SearchMaskJPanel.java
 *
 * Created on January 29, 2006, 10:16 PM
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
import javax.swing.event.*;
import javax.swing.*;
import java.awt.Rectangle;
import javax.swing.text.*;
import java.awt.Dimension;
import static java.lang.Math.*;
import static globaldocs.Utils.*;

/**
 *
 * @author  zeev
 */
public class SearchMaskJPanel extends javax.swing.JPanel {
    
    /** Creates new form SearchMaskJPanel */
    public SearchMaskJPanel() {
        initComponents();
        MainFrame.createPressedIcon(clear_jButton);
        startsWithArea.getDocument().addDocumentListener(dl);
        endsWithArea.getDocument().addDocumentListener(dl);
        containsArea.getDocument().addDocumentListener(dl);
        
        //java.awt.Component cs[]=startsWithArea.getComponents();
        
        FocusGrabber.set(containsArea,startsWithArea,endsWithArea);
    }
    
    public void setTooltips(String entitiesName) {
      startsWithArea.setToolTipText("Show Only "+entitiesName+" Those Begin With ...");
      containsArea.setToolTipText("Show Only "+entitiesName+" Those Contain  ...");
      endsWithArea.setToolTipText("Show Only "+entitiesName+" Those End With ...");
    }
    
    public void doLayout() {
      super.doLayout();
      if (initial_contains==null) {
        Rectangle rc=getBounds();
        rc.width-=100;
        
        initial_contains=rc;//containsArea.getBounds();
        initial_starts=startsWithArea.getBounds();
        initial_ends=endsWithArea.getBounds();
      }
    }

    
    static int getTextWidth(JTextField tc) {
        try {
            int sz=tc.getDocument().getLength();
            if (sz>0) {
                Rectangle rc=tc.modelToView(sz);
                return (int)(rc.getWidth()+rc.getX())+
                        tc.getScrollOffset();           
            }
        } catch (Throwable t) { }
        return 0;
    }
    
    private void doResizeIfNeed() {
      int
       sz_contains=getTextWidth(containsArea),
       ws_contains=containsArea.getWidth(),
       sz_starts=getTextWidth(startsWithArea),
       ws_starts=startsWithArea.getWidth(),
       sz_ends=getTextWidth(endsWithArea),
       ws_ends=endsWithArea.getWidth(),
       ws_all=ws_starts+ws_contains+ws_ends;
      
      
      int
       w3=ws_all/3,
       pp_starts=(sz_starts==0)?(int)initial_starts.getWidth():w3,    
       pp_ends=(sz_ends==0)?(int)initial_ends.getWidth():w3;
      
      
      int
       pp_contains=ws_all-pp_starts-pp_ends,
       delta
              
       ;
      
       Dimension r;
       boolean rev=false,
               fc=pp_contains!=ws_contains,
               fs=pp_starts!=ws_starts,
               fe=pp_ends!=ws_ends;
       
       int delta_s=pp_starts-ws_starts;
       

       if (fc) {
         rev=true;
         delta=pp_contains-ws_contains;
         r=containsArea.getSize();
         r.width+=delta;
         containsArea.setPreferredSize(r.getSize());
       }           
       
       if (fe) {
         rev=true;
         delta=pp_ends-ws_ends;
         r=endsWithArea.getSize();
         r.width+=delta;
         endsWithArea.setPreferredSize(r.getSize());
       }
       
       if (fs) {
         rev=true;
         r=startsWithArea.getSize();        
         r.width+=delta_s;
         startsWithArea.setPreferredSize(r.getSize());
       }      
       
       if (rev) revalidate();

    }
    
    DocumentListener dl=new DocumentListener() {
        
        /**
         * Gives notification that there was an insert into the document.  The
         * range given by the DocumentEvent bounds the freshly inserted region.
         *
         * @param e the document event
         */
        public void insertUpdate(DocumentEvent e) {
          doResizeIfNeed();
        }
        
        /**
         * Gives notification that a portion of the document has been
         * removed.  The range is given in terms of what the view last
         * saw (that is, before updating sticky positions).
         *
         * @param e the document event
         */
        public void removeUpdate(DocumentEvent e) {
          doResizeIfNeed();
        }
        
        /**
         * Gives notification that an attribute or set of attributes changed.
         *
         * @param e the document event
         */
        public void changedUpdate(DocumentEvent e) {
          doResizeIfNeed();
        }
        
        
    };
    
    public String getConatins() {
        return get(containsArea);
    }
    public String getStartsWith() {
        return get(startsWithArea);
    }
    public String getEndsWith() {
        return get(endsWithArea);
    }
    
    private String get(JTextField f) {
        String tx=f.getText();
        if (tx!=null) tx=tx.trim().toLowerCase();
        if (tx.length()==0) tx=null;
        return tx;
    }
    
    
    
    public void addDocumentListener(DocumentListener dl) {
        containsArea.getDocument().addDocumentListener(dl);
        startsWithArea.getDocument().addDocumentListener(dl);
        endsWithArea.getDocument().addDocumentListener(dl);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
   // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
   private void initComponents() {
      clear_jButton = new javax.swing.JButton();
      startsWithArea = new javax.swing.JTextField();
      containsArea = new javax.swing.JTextField();
      endsWithArea = new javax.swing.JTextField();

      setMinimumSize(new java.awt.Dimension(140, 29));
      addComponentListener(new java.awt.event.ComponentAdapter() {
         public void componentResized(java.awt.event.ComponentEvent evt) {
            formComponentResized(evt);
         }
      });

      clear_jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/blackcross.png")));
      clear_jButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
      clear_jButton.setBorderPainted(false);
      clear_jButton.setContentAreaFilled(false);
      clear_jButton.setDoubleBuffered(true);
      clear_jButton.setPreferredSize(new java.awt.Dimension(17, 17));
      clear_jButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            clear_jButtonActionPerformed(evt);
         }
      });

      add(clear_jButton);

      startsWithArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
      startsWithArea.setToolTipText("Show Only Classes Those Begin With ...");
      startsWithArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
      startsWithArea.setPreferredSize(new java.awt.Dimension(20, 19));
      add(startsWithArea);

      containsArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
      containsArea.setToolTipText("Show Only Classes Those Contain  ...");
      containsArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
      containsArea.setPreferredSize(new java.awt.Dimension(200, 19));
      add(containsArea);

      endsWithArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
      endsWithArea.setToolTipText("Show Only Classes Those End With ...");
      endsWithArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
      endsWithArea.setPreferredSize(new java.awt.Dimension(20, 19));
      add(endsWithArea);

   }// </editor-fold>//GEN-END:initComponents

   private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized

      Rectangle rc=getBounds();
      int w=rc.width-80,m;
      
      boolean sta=isSet(startsWithArea.getText()),
              ena=isSet(endsWithArea.getText());
      
      rc=containsArea.getBounds();
      rc.width=w;
      if (sta) rc.width-=w/3; else rc.width-=20;
      if (ena) rc.width-=w/3; else rc.width-=20;
      containsArea.setPreferredSize(rc.getSize());
      
      rc=endsWithArea.getBounds();
      rc.width=ena?w/3:20;
      endsWithArea.setPreferredSize(rc.getSize());
      
      rc=startsWithArea.getBounds();
      rc.width=sta?w/3:20;
      startsWithArea.setPreferredSize(rc.getSize());
      
      
      revalidate();

   }//GEN-LAST:event_formComponentResized

    Rectangle initial_contains,initial_starts,initial_ends;
    
  
    private void clear_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_jButtonActionPerformed
// TODO add your handling code here:
      synchronized(getTreeLock()) {
        containsArea.setText("");
        startsWithArea.setText("");
        endsWithArea.setText("");
        
        
        Rectangle rc=getBounds();
        rc.width-=80;
        rc.height=initial_contains.height;
      
        containsArea.setBounds(//initial_contains
          rc
        );
        
        
        
        startsWithArea.setBounds(initial_starts);
        endsWithArea.setBounds(initial_ends);
        
        revalidate();
      }
    }//GEN-LAST:event_clear_jButtonActionPerformed
    
    public boolean isEmpty() {
      String contains=nullify(getConatins()),
             starts=nullify(getStartsWith()),
             ends=nullify(getEndsWith());

      return contains==null&&starts==null&&ends==null;
    }

    public boolean matches(String s) {
      String contains=nullify(getConatins()),
             starts=nullify(getStartsWith()),
             ends=nullify(getEndsWith());

      return
         (starts==null||s.startsWith(starts))&&
         (contains==null||s.indexOf(contains)>=0)&&
         (ends==null||s.endsWith(ends));

    }
    
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton clear_jButton;
   private javax.swing.JTextField containsArea;
   private javax.swing.JTextField endsWithArea;
   private javax.swing.JTextField startsWithArea;
   // End of variables declaration//GEN-END:variables
    
}
