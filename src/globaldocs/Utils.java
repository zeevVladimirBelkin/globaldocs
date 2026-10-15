/*
 * Utils.java
 *
 * Created on March 2, 2006, 7:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 *     Globaldocs -- viewer for Javadocs
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

import java.security.*;
import javax.xml.parsers.DocumentBuilderFactory;        
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.net.*;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.jnlp.*;
import java.util.Vector;
import java.util.logging.*;
import java.lang.reflect.*;
import java.util.prefs.*;
import java.nio.channels.*;
import java.util.Properties;
import java.beans.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author zeev
 */
public class Utils {
    
    public static void invokeAndWait(Runnable r) {
      try {
        if (EventQueue.isDispatchThread())
          r.run();
        else
          SwingUtilities.invokeAndWait(r);
      } catch (Throwable t) {
          System.err.println(t);
      }
  }
  
  public static void invokeLater(Runnable r) {
      try {
          SwingUtilities.invokeLater(r);
      } catch (Throwable t) {
          System.err.println(t);
      }
  }
   
  public static String os_name=System.getProperty("os.name");
  public static boolean mswin=os_name.toLowerCase().startsWith("windows");
  
  /**
   * See original example:
   * <a href="http://jroller.com/page/lenkite?entry=use_pure_java_to_access">
   * http://jroller.com/page/lenkite?entry=use_pure_java_to_access
   * </a>
   *
   * See also:
   *<a href="http://www.kickjava.com/src/java/util/prefs/WindowsPreferences.java.htm">
   *  java.util.prefs.WindowsPreferences
   *</a>
   * 
   * @author Zeev Belkin
   *
   */
  static class M$Reg  {
     
//     public static final int HKEY_CURRENT_USER = 0x80000001;
//     public static final int HKEY_LOCAL_MACHINE = 0x80000002;
//     public static final int KEY_QUERY_VALUE = 1;
//     public static final int KEY_SET_VALUE = 2;
     public static final int KEY_READ = 0x20019;
     
     private Method
         mOpenKey=null,mCloseKey=null,mWinRegQueryValue=null,
         mWinRegEnumValue=null,mWinRegQueryInfo=null,
         mWindowsRegEnumKeyEx1=null,
         mOpenKey_subKey=null
     ;
     
     private static M$Reg m$reg=null;
     
     private final static Preferences root = Preferences.systemRoot();
     private final static Class clz = root.getClass();
       
     private M$Reg() throws Throwable {
        
        mOpenKey = clz.getDeclaredMethod("openKey", byte[].class, int.class, int.class);
        mOpenKey.setAccessible(true);
        
        mCloseKey = clz.getDeclaredMethod("closeKey",int.class);
        mCloseKey.setAccessible(true);
        
        mWinRegQueryValue = clz.getDeclaredMethod(
              "WindowsRegQueryValueEx",int.class, byte[].class
              );
        mWinRegQueryValue.setAccessible(true);
        
        Method mWinRegEnumValue = clz.getDeclaredMethod(
              "WindowsRegEnumValue1", int.class, int.class, int.class
              );
        mWinRegEnumValue.setAccessible(true);
        
        mWinRegQueryInfo = clz.getDeclaredMethod("WindowsRegQueryInfoKey1", int.class);
        mWinRegQueryInfo.setAccessible(true);
        
        mWindowsRegEnumKeyEx1 = clz.getDeclaredMethod("WindowsRegEnumKeyEx1",int.class,int.class,int.class);
        mWindowsRegEnumKeyEx1.setAccessible(true);
        
        mOpenKey_subKey=clz.getDeclaredMethod("openKey",int.class ,byte[].class, int.class, int.class);
        mOpenKey_subKey.setAccessible(true);
     }
     
     public byte[] WindowsRegEnumKeyEx1(int hKey, int subKeyIndex,int maxKeyLength) {
       try {
          return (byte[])mWindowsRegEnumKeyEx1.invoke(
            root,
            new Integer(hKey),
            new Integer(subKeyIndex),
            new Integer(maxKeyLength)
         );
       } catch (Throwable t) {
       }
       return null;
     }
    
     public Integer WindowsRegFlushKey1(int hKey) {
       try {
         return (Integer)mWinRegQueryInfo.invoke(root,new Integer(hKey));
       } catch (Throwable t) {
       }
       return null;
     }
     
     public Integer openKey(byte[] subKey,int mask1,int mask2) {
       try {
         return (Integer)mOpenKey.invoke(root,subKey,new Integer(mask1),new Integer(mask2));
       } catch (Throwable t) {
       }
       return null;
     }

     public Integer openKey(int hKey,byte[] subKey,int mask1,int mask2) {
       try {
         return (Integer)mOpenKey_subKey.invoke(root,new Integer(hKey),subKey,new Integer(mask1),new Integer(mask2));
       } catch (Throwable t) {
       }
       return null;
     }

     
     public void closeKey(int hKey) {
       try {
         mCloseKey.invoke(root,new Integer(hKey));
       } catch (Throwable t) {
       }
     }
     
     public byte[] WindowsRegQueryValueEx(int hKey,byte[] valueName) {
       try {
         return (byte[])mWinRegQueryValue.invoke(root,new Integer(hKey),valueName);
       } catch (Throwable t) {
       }
       return null;
     }
     
     public byte[] WindowsRegEnumValue1(int hKey, int valueIndex,int maxValueNameLength) {
       try {
         return (byte[])mWinRegEnumValue.invoke(
           root,new Integer(hKey),new Integer(valueIndex),new Integer(maxValueNameLength)
         );
       } catch (Throwable t) {
       }
       return null; 
     }
     
     public static synchronized M$Reg getInstance() {
       if (m$reg==null&&mswin) try {
         m$reg=new M$Reg(); 
       } catch (Throwable t) {}
       return m$reg;
     }
     
  }
  
  M$Reg m$reg=M$Reg.getInstance();

  static public Vector<String> getExternalAppPathes() {
    Vector<String> apps=new Vector<String>();
    if (mswin) {
       M$Reg m$reg=M$Reg.getInstance();
       Integer hKey=m$reg.openKey(
         "Software\\Microsoft\\Windows\\CurrentVersion\\App Paths".getBytes(),
         M$Reg.KEY_READ, M$Reg.KEY_READ
       );
       if (hKey!=null) try {
         byte buf[],empty[]={};
         for (int i=0;(buf=m$reg.WindowsRegEnumKeyEx1(hKey,i,255))!=null;i++) {
            Integer subkey=m$reg.openKey(hKey,buf,M$Reg.KEY_READ, M$Reg.KEY_READ);
            if (subkey!=null) try {
              // System.err.println(new String(buf));
               buf=m$reg.WindowsRegQueryValueEx(subkey,empty);
               if (buf!=null) apps.add(new String(buf).trim());
            } finally {
              m$reg.closeKey(subkey);
            }
         }
       } finally {
         m$reg.closeKey(hKey);
       }
    }
    return apps;
  }
   
  static private String _imageExts[]={
    ".jpg",".jpe",".jpeg",
    ".gif",".png",".xbm",".xpm"
  };
   
  static public boolean isAnImage(URL u) {
    return u==null?false:isAnImage(u.toString());
  }
  
  static public boolean isAnImage(String s) {
    if (s!=null) {
      s=s.toLowerCase();
      for (String x:_imageExts) if (s.endsWith(x)) return true;
    }
    return false;
  }
  
  static private Logger _logger=null;

   
  public static Logger logger() {
     if (_logger==null) try {
       _logger=Logger.getAnonymousLogger();
       File logdir=new File(configDirectory,"logs");
       if (!logdir.exists()) logdir.mkdirs();
       _logger.addHandler(new FileHandler(new File(logdir,"log.xml").getCanonicalPath(),true));
     } catch (Throwable t) {}
     return _logger;
  }

  public static boolean isAValidDir(File f) {
    return f!=null&&f.isDirectory()&&f.exists();
  } 
   
  public final static String java_home=System.getProperty("java.home");
  
  public static String javaExecutable() {
    String java=java_home+"/bin/"+(mswin?"javaw":"java");
    if (new File(java).exists()) return java.toString();
    java+=".exe";
    if (new File(java).exists()) return java.toString();
    return null;
  }
   
  public static void respawnIfCanAndNeed(String args[]) {
      try {
         
        String sns[]=ServiceManager.getServiceNames();
        if (sns==null||sns.length==0) return;

      //  if ("yes".equals(System.getProperty("globaldocs.respawn"))) 
      //     return;
        
        
        
        // yes, we need to respawn
        String java=javaExecutable();
        File jar=getRunningJarFile();
        
        if (jar!=null) logger().info("jar="+jar.getCanonicalPath());
        
        if (jar!=null&&jar.exists()&&java!=null) {
            Vector<String> spargs=new Vector<String>();
            
            spargs.add(java);
      //      spargs.add("-Dcom.sun.management.jmxremote");
            spargs.add("-Dglobaldocs.respawn=yes");
            spargs.add("-jar");
            spargs.add("-Xmx100M");
            spargs.add(jar.getCanonicalPath());

            for (String arg:args) spargs.add(arg);
            
            Runtime.getRuntime().exec(
               spargs.toArray(new String [] {})
            );
            //System.out.println(jardir);
            System.exit(0);
        }
      } catch (Throwable t) {
      }   
  }
  
  private static String appInstanceId=null;
  public static Toolkit defaultToolkit=Toolkit.getDefaultToolkit();
  public static Dimension screenDimension=defaultToolkit.getScreenSize();
  public static File user_home=new File(System.getProperty("user.home"));
  public static File configDirectory=subDirectory(user_home,".globaldocs");
  public static File lockDirectory=subDirectory(subDirectory(configDirectory,"lock"),getDisplayID());
  public static FileLock appLock=createAppLock();
  public static File insDirectory=subDirectory(configDirectory,"ins");
  private static File localApps=new File(user_home,".local/share/applications");
  
  static abstract class Installer {
     abstract public void installGlobaldocs();
     
     abstract String sectionHeader();
     
     abstract String iconName();
           
     void saveDesktopEntry(Properties dskEP,File f) {
       try {

          FileOutputStream fos=new FileOutputStream(f);
          PrintStream ps=new PrintStream(fos);
          
          ps.println("["+sectionHeader()+"]");
          dskEP.store(ps,"");
          
          ps.flush();
          fos.close();
          
       } catch(Throwable t) {
         t.printStackTrace();
       }
     }
     
     void copyIcon() throws IOException {
       InputStream is=getResourceUrl("globaldocs/res/"+iconName()).openStream();
       File icf=new File(insDirectory,iconName());
       copy(is,icf);
       is.close();
     }
     
     void copyJar() throws IOException {
       File jar=getRunningJarFile();
       if (jar!=null) {
         FileInputStream is=new FileInputStream(jar);
         copy(is,installedJar());
         is.close();        
       }
     }
  };
  
  
  static class FreedesktopInstaller extends  Installer {
     
    String iconName() { return "cup.gif"; }
    
    String sectionHeader() { return "Desktop Entry"; }
    
    final static String menuEntry="ZeevBelkin-globaldocs.desktop";
    
    public void installGlobaldocs() {
       try {
          URL dskEU=getResourceUrl("globaldocs/res/globaldocs.desktop");
          Properties dskEP=new Properties();
          dskEP.load(dskEU.openStream());
          
          
          dskEP.setProperty("Icon",new File(insDirectory,"cup.gif").toString());
          
          String java=defaultJvmIsUsed()?"java":"\""+javaExecutable()+"\"";
          
          dskEP.setProperty("Exec",
                java+" -jar -Xmx100M "+
                "\""+installedJar().toString()+"\" %U"
                );
          
          File dsk=new File(user_home,"Desktop");
          
          saveDesktopEntry(dskEP,new File(dsk,"globaldocs.desktop"));
        //  saveDesktopEntry(dskEP,new File(localApps,"globaldocs.desktop"));
          
          saveDesktopEntry(dskEP,new File(insDirectory,"globaldocs.desktop"));
          
          //dskEP.store(System.out,"");

          copyIcon();
          copyJar();
          
          if (!localApps.exists()) localApps.mkdirs();

          try {
            Process pp=Runtime.getRuntime().exec(
               new String[] {
                  "desktop-file-install",
                  "--vendor=ZeevBelkin",
                  "--dir="+localApps.toString(),
                  new File(insDirectory,"globaldocs.desktop").toString()
               }
            );
            
            BufferedReader 
                   es=new BufferedReader(new InputStreamReader(pp.getErrorStream())),
                   os=new BufferedReader (new InputStreamReader(pp.getInputStream()));
           Thread.sleep(800);
            do {
              if (es.ready()) logger().info(es.readLine());
              if (os.ready()) logger().info(os.readLine());
              Thread.sleep(20);
            } while (es.ready()||os.ready());
            pp.waitFor();
            Thread.sleep(300);
          } catch (Throwable t) {}
         // Runtime.getRuntime().exec("kbuildsycoca");
          
          File me=new File(localApps,menuEntry);
          if (!me.exists()||me.length()==0) saveDesktopEntry(dskEP,me);
             

          //dskEP.store(System.out,"");
       } catch (IOException ex) {
          ex.printStackTrace();
       }
    }
  };
  
  static class WindowsInstaller extends  Installer {
     String iconName() { return "run.exe"; }
     
     String sectionHeader() { return null; }
     
     public void installGlobaldocs() {
       try {
         copyIcon();
         copyJar();
         
         BufferedWriter w=new BufferedWriter(new FileWriter(new File(insDirectory,".java")));
         w.write(
            defaultJvmIsUsed()?
              "javaw":
              javaExecutable().replace('/','\\')
         );
         w.close();
         
         Process pp=Runtime.getRuntime().exec(
               new String[] {
                 new File(insDirectory,"run.exe").toString(),
                 "/install"
               }
            );
         try {
           Thread.sleep(1000);
         } catch (InterruptedException ex) {}
         
       } catch (IOException ex) {
          ex.printStackTrace();
       }        
     }
  };

  
  public static void installGlobaldocs() {
    if (isFeedesktopPlatform())
      new FreedesktopInstaller().installGlobaldocs();
    else if (mswin)
      new WindowsInstaller().installGlobaldocs();
  }
  
  public static boolean needToInstall() {
     if (!installedJar().equals(getRunningJarFile())) 
        return isNewerThenInstalled()&&(isFeedesktopPlatform()||mswin);
     return false;
  }
  
  public static void copy(InputStream is,File of,long timeout) throws FileNotFoundException {
    FileOutputStream fos=new FileOutputStream(of);
    try {
    byte buf[]=new byte[4096];
      for (;;) {
        if (timeout>0) Thread.sleep(timeout);
        int cnt=is.read(buf);
        if (cnt>0) fos.write(buf,0,cnt); else break;
      }
      fos.close();
    } catch (Throwable t) {}
  }
  
  public static void copy(InputStream is,File of) throws FileNotFoundException {
     copy(is,of,0);
  }
  
  public static File installedJar() {
/////////////////////////////////////////////////////////////////////////////
//    javax.swing.JOptionPane.showMessageDialog(null,insDirectory);
/////////////////////////////////////////////////////////////////////////////
    return new File(insDirectory,"globaldocs.jar");
  }
  
  private static RandomAccessFile lockFile=null;
  
  public static class IPC_Data  {
     
     public static class Void {}
     
     public static class Response {
        Object response=null;
        Throwable exception=null;
        
        public Throwable getException() {
          return exception;
        }
        public void setException(Throwable t) {
           exception=t;
        }
        
        public Object getResponse() {
          return response;
        }
        public void setResponse(Object r) {
          response=r;
        }
     }
     
     final static int ST_READY=0;
     final static int ST_RQ_SENT=1;
     final static int ST_GOT_FOR_PROCESSING=2;
     final static int ST_RESPONSE=3;
     final static int TIMEOUT=70000;

     public int status=ST_READY;
    
     public Object request=null;
     public Response response=null;
     public String requestor_id=null,responser_id=null;
     public long request_ts=0,response_ts=0,ts=0;

     public void setResponse_ts(long t) {
        response_ts=t;
     }
     public long getResponse_ts() {
       return response_ts;
     }
     
     public long getRequest_ts() {
       return request_ts;
     }
     public void setRequest_ts(long t) {
       request_ts=ts;
     }
     
     public long getTs() {
       return ts;
     }
     public void setTs(long t) {
       ts=t;
     }
     
     
     public String getResponser_id() {
       return responser_id;
     }
     public void setResponser_id(String id) {
        responser_id=id;
     }
     
     public String getRequestor_id() {
       return requestor_id;
     }
     public void setRequestor_id(String id) {
       requestor_id=id;
     }
     
     public int getStatus() {
       return status;
     }
     
     public void setStatus(int st) {
       status=st;
     }
     
     public void setRequest(Object rq) {
        request=rq;
     }
     public Object getRequest() {
        return request;
     }
     
     public void setResponse(Response rs) {
        response=rs;
     }
     public Response getResponse() {
        return response;
     }
  };
  
  private static long prev_rs_ts=0,prev_rq_ts=0;
  
  
  public static void ipc_send_response(IPC_Data.Response response) {
     FileLock lock=ipc_Lock();
     try {
        IPC_Data ipc_data=null;
        try {
           if (lockFile.length()>0) {
              byte buf[]=new byte[(int)lockFile.length()];
              lockFile.seek(0);
              lockFile.read(buf);
              ByteArrayInputStream bais=new ByteArrayInputStream(buf);
              XMLDecoder dec = new XMLDecoder(bais);
              Object obj=dec.readObject();
              if (obj!=null&&obj instanceof IPC_Data) {
                 ipc_data=(IPC_Data)obj;
              }
           }
        } catch (Throwable t) {}
        if (ipc_data!=null&&
            ipc_data.status==IPC_Data.ST_GOT_FOR_PROCESSING&&
            ipc_data.ts+IPC_Data.TIMEOUT>System.currentTimeMillis()&&
            getAppInstanceId().equals(ipc_data.responser_id)
           ) 
        {
           ipc_data.status=IPC_Data.ST_RESPONSE;
           ipc_data.response=response;
           ipc_data.response_ts=ipc_data.ts=System.currentTimeMillis();
           
             ByteArrayOutputStream baos=new ByteArrayOutputStream();
             XMLEncoder enc=new XMLEncoder(baos);
             enc.writeObject(ipc_data);
             enc.close();
             byte buf[]=baos.toByteArray();
             try {
                lockFile.seek(0);
                lockFile.write(buf);
                lockFile.setLength(lockFile.getFilePointer());
             } catch (Throwable t) {}
        }
     } finally {
        try {
           lock.release();
        } catch (IOException ex) {}
     }
  }
  
  
  public static Object ipc_get_request() {
     FileLock lock=ipc_Lock();
     try {
        IPC_Data ipc_data=null;
        try {
           if (lockFile.length()>0) {
              byte buf[]=new byte[(int)lockFile.length()];
              lockFile.seek(0);
              lockFile.read(buf);
              ByteArrayInputStream bais=new ByteArrayInputStream(buf);
              XMLDecoder dec = new XMLDecoder(bais);
              Object obj=dec.readObject();
              if (obj!=null&&obj instanceof IPC_Data) {
                 ipc_data=(IPC_Data)obj;
              }
           }
        } catch (Throwable t) {}
        if (ipc_data!=null&&
            ipc_data.status==IPC_Data.ST_RQ_SENT&&
            ipc_data.ts+IPC_Data.TIMEOUT>System.currentTimeMillis()&&
            !getAppInstanceId().equals(ipc_data.requestor_id)
           ) 
        {
           ipc_data.status=IPC_Data.ST_GOT_FOR_PROCESSING;
           ipc_data.responser_id=getAppInstanceId();
           ipc_data.ts=System.currentTimeMillis();
           
             ByteArrayOutputStream baos=new ByteArrayOutputStream();
             XMLEncoder enc=new XMLEncoder(baos);
             enc.writeObject(ipc_data);
             enc.close();
             byte buf[]=baos.toByteArray();
             try {
                lockFile.seek(0);
                lockFile.write(buf);
                lockFile.setLength(lockFile.getFilePointer());
             } catch (Throwable t) {}
           
           
           return ipc_data.request;
        }
     } finally {
        try {
           lock.release();
        } catch (IOException ex) {}
     }
     return null;
  }

  public static IPC_Data.Response ipc_get_response() {
    long start_ts=System.currentTimeMillis();
    for (;start_ts+IPC_Data.TIMEOUT>System.currentTimeMillis();) {
      try {
        Thread.sleep(200);
      } catch (InterruptedException ex) {}
      FileLock lock=ipc_Lock();
      try {
        IPC_Data ipc_data=null;
        try {
           if (lockFile.length()>0) {
              byte buf[]=new byte[(int)lockFile.length()];
              lockFile.seek(0);
              lockFile.read(buf);
              ByteArrayInputStream bais=new ByteArrayInputStream(buf);
              XMLDecoder dec = new XMLDecoder(bais);
              Object obj=dec.readObject();
              if (obj!=null&&obj instanceof IPC_Data) {
                 ipc_data=(IPC_Data)obj;
              }
           }
        } catch (Throwable t) {}
        if (ipc_data!=null&&ipc_data.response_ts!=prev_rs_ts&&ipc_data.status==IPC_Data.ST_RESPONSE) {
          prev_rs_ts=ipc_data.response_ts;
          if (getAppInstanceId().equals(ipc_data.requestor_id)) {
             ipc_data.status=IPC_Data.ST_READY;
             
             ByteArrayOutputStream baos=new ByteArrayOutputStream();
             XMLEncoder enc=new XMLEncoder(baos);
             enc.writeObject(ipc_data);
             enc.close();
             byte buf[]=baos.toByteArray();
             try {
                lockFile.seek(0);
                lockFile.write(buf);
                lockFile.setLength(lockFile.getFilePointer());
             } catch (Throwable t) {}
             return ipc_data.response;
          } else
             break;
        }
        
        try {
           if (lock!=null) lock.release();
        } catch (IOException ex) {}
        lock=null;
        
      } finally {
         try {
           if (lock!=null) lock.release();
         } catch (IOException ex) {}
      }
    }
    return null;
  }

  
  public static void ipc_send_request(Object rq) {
    aa:for (;;) {
        FileLock lock=ipc_Lock();
        try {
           IPC_Data ipc_data=null;
           try {
              if (lockFile.length()>0) {
                 byte buf[]=new byte[(int)lockFile.length()];
                 lockFile.seek(0);
                 lockFile.read(buf);
                 ByteArrayInputStream bais=new ByteArrayInputStream(buf);
                 XMLDecoder dec = new XMLDecoder(bais);
                 Object obj=dec.readObject();
                 if (obj!=null&&obj instanceof IPC_Data) {
                    ipc_data=(IPC_Data)obj;
                    if (ipc_data.status!=IPC_Data.ST_READY&&ipc_data.ts+IPC_Data.TIMEOUT>System.currentTimeMillis()) {
                       lock.release(); lock=null;
                       Thread.sleep(200);
                       continue aa;
                    }
                    prev_rs_ts=ipc_data.response_ts;
                 }
              }
           } catch (Throwable t) {}
           if (ipc_data==null) ipc_data=new IPC_Data();
           ipc_data.request=rq;
           ipc_data.requestor_id=getAppInstanceId();
           ipc_data.request_ts=ipc_data.ts=System.currentTimeMillis();
           ipc_data.response=null;
           ipc_data.status=IPC_Data.ST_RQ_SENT;
           ByteArrayOutputStream baos=new ByteArrayOutputStream();
           XMLEncoder enc=new XMLEncoder(baos);
           enc.writeObject(ipc_data);
           enc.close();
           byte buf[]=baos.toByteArray();
           try {
             lockFile.seek(0);
             lockFile.write(buf);
             lockFile.setLength(lockFile.getFilePointer());
           } catch (Throwable t) {}
        } finally {
           try {
             if (lock!=null) lock.release();
           } catch (IOException ex) {}
        }
        break;
     }
  }
  
  public static synchronized FileLock ipc_Lock() {
    for (;;)
      try {
        if (lockFile==null)
          lockFile=new RandomAccessFile(new File(lockDirectory,"ipc"),"rws");
        return
          lockFile
           .getChannel()
           .lock();
     } catch(OverlappingFileLockException ox) {
       try {
         Thread.sleep(10);
       } catch (InterruptedException ex) {}
     } catch(Throwable t) {
       t.printStackTrace();
       return null;
     }
 
  }
  
  /*
  public static FileLock ipc_Lock() {
    try {
        return 
          new FileOutputStream(new File(lockDirectory,"ipc"))
           .getChannel()
           .lock();
    } catch(Throwable t) {}
    return null;
  }
  */
  
  public static Vector<String> getActiveAppInstancesIds() {
    Vector<String> rv=new Vector<String>();
    String id=getAppInstanceId();
    
    for (File f:lockDirectory.listFiles())
         if (!(f.getName().equals(id)||f.getName().equals("ipc"))) try {
            RandomAccessFile lcs=new RandomAccessFile(f,"rws");
            FileChannel lcc=lcs.getChannel();
            if (lcc.tryLock()==null) rv.add(f.getName());
            lcc.close();
         } catch (Throwable t) {}
    return rv;
  }
  
  public static boolean isAppInstanceActive(String id) {
     try {
        return 
          new FileOutputStream(new File(lockDirectory,id))
           .getChannel()
           .tryLock()==null;
 
     } catch(Throwable t) {}
     return false;
  }
  
  private static FileLock createAppLock() {
    try {
      String id=getAppInstanceId();
      FileOutputStream fos=new FileOutputStream(new File(lockDirectory,id));
      FileChannel fosc=fos.getChannel();
      FileLock lc=fosc.lock();
      for (File f:lockDirectory.listFiles()) 
         if (!(f.getName().equals(id)||f.getName().equals("ipc"))) try {
            RandomAccessFile lcs=new RandomAccessFile(f,"rws");
            FileChannel lcc=lcs.getChannel();
            if (lcc.tryLock()!=null)
              f.delete();
            lcc.close();
         } catch (Throwable t) {}
      return lc;
    } catch (Throwable t) {}
    return null;
  }
  
   
  public static String withoutSearch(String s) {
      if (s!=null) {
         int i=s.lastIndexOf('?');
         if (i>=0) s=s.substring(0,i);
      }
      return s;
  }

  public static String withoutRef(String s) {
      if (s!=null) {
         int i=s.lastIndexOf('#');
         if (i>=0) s=s.substring(0,i);
      }
      return s;
  }
   
  public static Integer getInt(String s) {
     try {
        return new Integer(s);
     } catch (Throwable t) {}
     return null;
  } 
  
  public static boolean isNewerThenInstalled() {
    File ij=installedJar();
    if (ij.exists()) try {
      URL pu=new URL("jar:"+ij.toURL().toString()+"!/globaldocs/res/properties.properties");
      Properties pps=new Properties();
      pps.load(pu.openStream());
      String v=pps.getProperty("version");
      if (v!=null) return verIsGreater(VERSION,v);
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return true;
  }
   
 public static boolean verIsGreater(String a,String b) {
     a=denullify(a);
     b=denullify(b);
     String
        aa[]=a.split("\\."),
        bb[]=b.split("\\.");
 
     for (int i=0;i<Math.min(aa.length,bb.length);i++) {
       Integer x=getInt(aa[i]), y=getInt(bb[i]);
       boolean strcmp=(x==null)||(y==null);
       
       Comparable xx=strcmp?aa[i]:x,
                  yy=strcmp?bb[i]:y;
       
       int cmpres=xx.compareTo(yy);
       if (cmpres<0) return false;
       if (cmpres>0) return true;
     }
     return aa.length>bb.length;
  }   
   
   
  public static String denullify(String a) {
     return a==null?"":a.trim();
  }
  
  public static String nullify(String a) {
     if (a!=null) {
       a=a.trim();
       if (a.length()==0) a=null;
     }
     return a;
  }
   
  static File subDirectory(File master,String sub) {
    File dir=new File(master,sub);
    if (!dir.exists()) dir.mkdirs();
    return dir;
  }
   
   
  public static URL getResourceUrl(String path) {
     return  Utils.class.getClassLoader().getResource(path);
  }
  
  public static String getResourceBase() {
     String s=getResourceUrl("globaldocs/Utils.class").toString();
     return s.substring(0,s.lastIndexOf('/')+1)+"res/";
  }
  
  private static long _nano=System.nanoTime();
  
  public static String getAppInstanceId() {
    if (appInstanceId==null)
       appInstanceId=md5digest(getResourceBase(),"%",Long.toString(_nano),"%",Double.toHexString(Math.random()));
    return appInstanceId;
  }
  
  private final static String JAR_URL_PFX="jar:",
                              JAR_URL_SUFFIX="!/globaldocs/Utils.class";
  
  public static String getRunningJar() { 
    String s=getResourceUrl("globaldocs/Utils.class").toString();
    int i;
    if (s.startsWith(JAR_URL_PFX)&&s.endsWith(JAR_URL_SUFFIX))
      return s.substring(JAR_URL_PFX.length(),s.length()-JAR_URL_SUFFIX.length());
    return null;
  }
  
  static File _runningJarFile=null;
  
  public static File getRunningJarFile() {
    if (_runningJarFile!=null) return _runningJarFile;
    String  jar=getRunningJar();
    if (jar!=null)
      try {
        if (jar.startsWith("file:"))
          return _runningJarFile=new File(URLDecoder.decode( jar.substring(jar.indexOf(':')+1),"Utf8"));
        else {
          File tmp=File.createTempFile("gdx_tmp",".jar");
          copy(new URL(jar).openStream(),tmp,60);
          return _runningJarFile=tmp;
        }
      } catch (Throwable t) {}
    return null;
  } 
  
  public static ImageIcon loadIcon(String path) {
      return new ImageIcon(getResourceUrl(path));
  }   
  
  static public void writeXML(org.w3c.dom.Document xml,OutputStream os) {
      try { 
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
            
        DOMSource source = new DOMSource(xml);
                
        StreamResult result=new StreamResult(os);
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.transform(source, result);
     } catch (Throwable t) {}    
  }
  
   static public byte [] md5(String ... ss) {
      if (ss!=null&&ss.length>0) try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        int cc=0;
        for (String s:ss) if (s!=null) { md.update(s.getBytes("Utf8")); ++cc;}
        
        if (cc>0) return md.digest();
        
      } catch (Throwable t) {
        System.err.println(t);
      }
      return null;
   }
   
   static public String hexencode(byte bytes[]) {
     if (bytes==null) return null;
     String rv="";
     for (byte b:bytes) {
        byte lo=(byte)(b&0xf),
              hi=(byte)((b>>4)&0xf);
        
        char clo=(char)((lo<10)?(lo+'0'):(lo-10+'a')),
              chi=(char)((hi<10)?(hi+'0'):(hi-10+'a'))
              ;
        rv+=clo;
        rv+=chi;
     }
     return rv;
   }
   
   static public String md5digest(String ... ss) {
      if (ss!=null&&ss.length>0) try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        int cc=0;
        for (String s:ss) if (s!=null) { md.update(s.getBytes("Utf8")); ++cc;}
        if (cc>0) return hexencode(md.digest()); 
      } catch (Throwable t) {
        System.err.println(t);
      }
      return null;
   }
   
   

  public static boolean isSet(Object o) {
     return o!=null&&o.toString().length()>0;
  }

  public static String  jreDisplayId() {
    return new java.awt.Frame().getGraphicsConfiguration().getDevice().getIDstring();
  }
  
  public static String getDisplayID() {
    String disp=System.getenv("DISPLAY"),jdid=jreDisplayId();
    if (disp!=null&&disp.length()==2&&disp.charAt(0)==':') disp+=".0";
  //  System.out.println(disp+" "+jdid);
     return Utils.md5digest(jdid+(disp==null?"":disp));
  }
  
  public static boolean isFeedesktopPlatform() {
    return (localApps.exists()&&localApps.isDirectory())||
           System.getenv("XDG_CONFIG_DIRS")!=null;
  }
  
  
 final static Properties app_props=new java.util.Properties() {
     {
       try {
        load(getResourceUrl("globaldocs/res/properties.properties").openStream());
       } catch (Throwable t) {}
     }
  };
  final static String VERSION=app_props.getProperty("version");
  
  public static String defaultJavaVersion() {
    try {
       Process pp=Runtime.getRuntime().exec("java -version");
       BufferedReader es=new BufferedReader(new InputStreamReader(pp.getErrorStream()));
       String rv=null;
       Thread.sleep(800);
       if (es.ready()) rv=es.readLine().trim();
       es.close();
       if (rv!=null&&rv.startsWith("java version")) {
         rv=rv.substring(rv.lastIndexOf(' ')).replace('\"',' ').trim();
         return rv;
       }
    } catch (Throwable t) {}
    return null;
  }
   
  public static boolean defaultJvmIsUsed() {
     return System.getProperty("java.version").equals(defaultJavaVersion());
  }
  
  public  static org.w3c.dom.Document loadXmlFile(File  src) {
     org.w3c.dom.Document doc=null;
     try {
        DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream is=new FileInputStream(src);
        if (is!=null) try {
          doc=builder.parse(is);
        } finally {
          is.close();
        }
     } catch (Throwable t) {}
     return doc;
  }

  public static org.w3c.dom.Document loadXmlFile(URL src) {
     return loadXmlFile(src,URL_STREAM_TIMEOUT);
  }

  public static org.w3c.dom.Document loadXmlFile(URL src,long timeout) {
     org.w3c.dom.Document doc=null;
     try {
        DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream is=openStream(src,timeout);
        if (is!=null) try {
          doc=builder.parse(is);
        } finally {
          is.close();
        }
     } catch (Throwable t) {}
     return doc;
  }
  
   public static org.w3c.dom.Document loadXmlFile(InputStream is) {
     org.w3c.dom.Document doc=null;
     try {
        DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
        doc=builder.parse(is);
     } catch (Throwable t) {
       t.printStackTrace();
     }
     return doc;
  }
  
  
    public static InputStream openStream(final URL u,final long millis) throws java.io.IOException {
    try {
          URLConnection uc=u.openConnection();
          if (uc!=null) {
             uc.setConnectTimeout((int)millis);
             uc.connect();
             if (uc instanceof HttpURLConnection) {
               HttpURLConnection hc=(HttpURLConnection)uc;
               if (hc.getResponseCode()/ 100 != 2) return null;
             }
             return uc.getInputStream();
          }
    } catch (Throwable t) {}
    return null;
     /*
    final InputStream rv[]={null};
    Thread t=new Thread() {
      public void run() {
        try {
          URLConnection uc=u.openConnection();
          if (uc!=null) {
             uc.setConnectTimeout((int)millis);
             uc.connect();
             if (uc instanceof HttpURLConnection) {
               HttpURLConnection hc=(HttpURLConnection)uc;
    //           System.out.println(hc.getResponseMessage());
               if (hc.getResponseCode()/ 100 != 2) return;
             }
             rv[0]=uc.getInputStream();
          }
        } catch (Throwable t) {}
      }
    };
    t.start();
    try {
     t.join(millis);
    } catch (Throwable tr) {
    }
    return rv[0];
      */
  }

  static final int URL_STREAM_TIMEOUT=5500;
}
