/*
 * KeyStrokeAction.java
 *
 * Created on September 17, 2006, 9:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package globaldocs;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;

/**
 *
 * @author zeev
 */
public abstract class KeyStrokeAction extends AbstractAction {
    KeyStroke keyStroke;
    
    /** Creates a new instance of KeyStrokeAction */
    public KeyStrokeAction(KeyStroke ks) {
      keyStroke=ks;
    }
    
    public void bindTo(JTextComponent c) {
      Keymap km=c.getKeymap();
      km.addActionForKeyStroke(keyStroke,this);
    }
    
}
