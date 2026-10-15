/*
 * Authenticator.java
 *
 * Created on March 21, 2006, 11:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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

import java.net.*;
import static globaldocs.Utils.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author zeev
 */
public class TheAuthenticator extends Authenticator {
   
  //Wallet wallet=new Wallet();
   
  public static void install() {
    setDefault(new TheAuthenticator());
  }
  
  public static void uninstall() {
    setDefault(null);
  } 
  
  String authStoreKey() {
    return md5digest(
      getRequestingHost(),
      Integer.toString(getRequestingPort()),
      getRequestingProtocol(),
      getRequestingPrompt(),
      rqType()
    ); 
  }
  
  String rqType() {
    return 
      (getRequestorType()==Authenticator.RequestorType.PROXY)
          ?"proxy"
          :"server";
  }
  
  protected boolean acceptMasterPassword(JFrame parent) {
     if (Wallet.isMasterPasswordEntered()) {
        return true;
     } else {       
        JPasswordField pwd = new JPasswordField(20);
        while (
              JOptionPane.showConfirmDialog(parent, pwd,"Enter The Wallet Password",JOptionPane.OK_CANCEL_OPTION)==
              JOptionPane.OK_OPTION
              ) {
           char cMaster[]=pwd.getPassword();
           String master=cMaster==null?null:nullify(new String(cMaster));
           if (master!=null) {
              if (Wallet.enterMasterPassword(master)) {
                 return true;
              } else {
                 JOptionPane.showMessageDialog(
                       parent,
                       "The Entered Password Is not Right",
                       "Alert",
                       JOptionPane.ERROR_MESSAGE
                       );
              }
           } else {
              JOptionPane.showMessageDialog(
                    parent,
                    "The Password Should not be Empty",
                    "Alert",
                    JOptionPane.ERROR_MESSAGE
                    );
           }
        }
     }
    return false;
  }
  
  Hashtable<String,Long> lastReguestTime=new Hashtable<String,Long>();
  
  protected PasswordAuthentication getPasswordAuthentication() {
    String key=authStoreKey();
    
    JFrame parent=MainFrame.frame.isVisible()?MainFrame.frame:MainFrame.splashScreen;
    
    if (Wallet.authInfoPresents(key)&&acceptMasterPassword(parent)) synchronized(lastReguestTime) {
       Long lrt=lastReguestTime.get(key);
       long ct=System.currentTimeMillis();
       if (lrt==null||(ct-lrt>1500)) {
         lastReguestTime.put(key,ct);
         return Wallet.getAuthInfo(key);
       }  
    }
    
    PasswordAuthenticationDialog dlg=new PasswordAuthenticationDialog(
       parent,   
       getRequestingPrompt(),
       getRequestorType()==Authenticator.RequestorType.PROXY
    );
    dlg.setVisible(true);
    if (dlg.isAccepted()) {
      String
         uid=dlg.getUserID(),pass=dlg.getPassword();
      PasswordAuthentication pa=new PasswordAuthentication(uid,pass.toCharArray());
      if (dlg.isShouldRemember()) {
        if (Wallet.isMasterPasswordSet()) {
           if (acceptMasterPassword(parent)) Wallet.storeAuthInfo(key,uid,pass);
        } else {
          SetMasterPassword_Dialog mpdlg=new SetMasterPassword_Dialog(parent); 
          mpdlg.setVisible(true);
          if (mpdlg.isAccepted()) {
            Wallet.setMasterPassword(mpdlg.getPassword());
            Wallet.storeAuthInfo(key,uid,pass); 
          }
        }
      }
      return pa;
    }
    
    return null;
  }
   
}
