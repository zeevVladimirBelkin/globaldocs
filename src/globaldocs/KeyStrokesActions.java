/*
 * KeyStrokesActions.java
 *
 * Created on September 17, 2006, 10:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package globaldocs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

/**
 *
 * @author zeev
 */
public abstract class KeyStrokesActions {
    
    /** Creates a new instance of KeyStrokesActions */
    public KeyStrokesActions(JTextComponent c,KeyStroke ... kss) {
      for (final KeyStroke ks:kss) {
         new KeyStrokeAction(ks) {
           public void actionPerformed(ActionEvent e) {
             KeyStrokesActions.this.actionPerformed(e,ks);
           }
         }.bindTo(c);
      } 
    }
    abstract public void actionPerformed(ActionEvent e,KeyStroke ks);
}
