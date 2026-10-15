/*
 * MainFrame.java
 *
 * Created on December 12, 2005, 1:00 PM
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

import java.awt.dnd.DnDConstants;
import javax.security.auth.callback.LanguageCallback;
import javax.swing.text.html.*;
import java.awt.Component;
import java.awt.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.tree.*;
import javax.swing.*;
import static javax.swing.text.html.HTMLEditorKit.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.awt.datatransfer.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Insets;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
import javax.xml.xpath.*;
//import javax.jnlp.ServiceManager;
import static globaldocs.Utils.*;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.nio.channels.*;
import java.util.zip.*;
import java.beans.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.ref.*;
import javax.net.ssl.*;
import java.security.cert.*;
import javax.management.*; 
import java.lang.management.*; 
import java.lang.reflect.*;
import java.security.ProtectionDomain;
import javax.swing.plaf.*;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

/**
 *
 * @author  Zeev Belkin
 */
public class MainFrame extends javax.swing.JFrame {
    
    /*
    static class Editor extends JEditorPane {
       public Editor() {
         System.err.println("xxxxxxxxxxxxxxxxxxxx");
       }
    }
    
    static Class editorClass=Editor.class;
    */
    
  private class TheTransferHandler extends TransferHandler implements DragGestureListener {
      
      public void dragGestureRecognized(DragGestureEvent dge) {
        if (linkUnderTheMouse!=null)
          exportAsDrag((JComponent)dge.getComponent(),dge.getTriggerEvent(),dge.getDragAction());
      }
     
      public boolean importData(JComponent comp,Transferable t) {
          boolean done=false;
          try {
              
              // final JEditorPane ed=(JEditorPane)comp;
              
              String urlStr=null;
              if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                  String s=((String)t.getTransferData(DataFlavor.stringFlavor)).trim();
                  URL u=new URL(s);
                  urlStr=s;
              } else if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                  List<File> fls=(List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);
                  if (fls!=null) for (File f:fls) {
                      navigateLater(f.toURL().toString());
                      done=true;
                  }
                  if (done) return true;
              }
              if (urlStr!=null) {
                  navigateLater(urlStr);
                  return true;
              }
          } catch(Throwable tr) { /*tr.printStackTrace();*/ }
          return done;
      }
     
      public int getSourceActions(JComponent c) {
        return DnDConstants.ACTION_LINK/*|DnDConstants.ACTION_COPY_OR_MOVE*/;
      }
      
      
     public boolean canImport(JComponent comp,DataFlavor[] transferFlavors) {
         for (DataFlavor d:transferFlavors)
           if (
              d.equals(DataFlavor.javaFileListFlavor)||
              d.equals(DataFlavors.dfUriList)||
              d.equals(DataFlavor.stringFlavor)
           ) return true;
         return false;
     }
     
     protected Transferable createTransferable(final JComponent c) {
             return new Transferable() {
               public boolean isDataFlavorSupported(DataFlavor flavor) {
                 if (linkUnderTheMouse==null) 
                     return flavor.equals(DataFlavor.stringFlavor);
                 return flavor.equals(DataFlavors.dfUriList)||
                        flavor.equals(DataFlavor.stringFlavor);
               }
               public DataFlavor[] getTransferDataFlavors() {
                 if (linkUnderTheMouse==null) return new DataFlavor[] {DataFlavor.stringFlavor};
                 return new DataFlavor[] {DataFlavors.dfUriList,DataFlavor.stringFlavor};
               }
               public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                  if (flavor.equals(DataFlavor.stringFlavor)) {
                     if (linkUnderTheMouse==null) {
                       JEditorPane ed=(JEditorPane)c;
                       return ed.getSelectedText();
                     }
                     return linkUnderTheMouse;
                  }
                  if (flavor.equals(DataFlavors.dfUriList)) {
/*
The text/uri-list format
text/uri-list is the standard file listing format for drag and drop. It is specified as follows: 
 A list of files, each file per line. 
 Each line ends with a \r\n, carriage return and newline. 
 File names have to be valid, url-encoded URIs (Unique resource identifier). 
 
Example 4.4. Example text/uri-list string
file://localhost/usr/bin/opera[\r\n]
file://localhost/c:/programs/myfile.txt[\r\n]
file://server/data/software.xml[\r\n]
http://gtk.php.net/apps/[\r\n]
 */
                      return new java.io.ByteArrayInputStream((linkUnderTheMouse+"\r\n").getBytes());
                  }
                  throw new UnsupportedFlavorException(flavor);
               }
             };
     }
  };
  
  TheTransferHandler theTransferHandler=new TheTransferHandler();
    
    
  static class A implements Comparable<A>, Home, Cloneable {

        public A clone() {
          try {
             return (A)super.clone();
          } catch (Throwable t) {}
          return null;
        }



	String fullName,version,urlBase;


        public String getId() {
          return Utils.md5digest(fullName,version,urlBase,"#1.8.3+");
        }

        URL homeUrl=null;

        int jPackageId=-1;

        boolean jPackage=false;

        public URL jPackageHome() {
          if (jPackageId>=0) try {
            return new URL(
              "http://jpackage.org"+JPACKAGE_URL_PREFIX+jPackageId
            //"http://jpackage.org/rpm.php?id="+jPackageId
            );
          } catch (Throwable t) {}
          return null;
        }



        public synchronized URL getHome() {
          if (homeUrl==null&&jPackageId>=0) try {
            InputStream is=openStream(jPackageHome(),URL_STREAM_TIMEOUT);
            if (is!=null) try {
              final InputStreamReader isr=new InputStreamReader(is);
              parser.parse(
                isr,
                new ParserCallback() {
                   boolean urlPrefixFound=false;

         	   public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		     if (urlPrefixFound&&t==HTML.Tag.A) try {
  		        String href=(String)a.getAttribute(HTML.Attribute.HREF);
                        homeUrl=mkUrl(href);
                        urlPrefixFound=false;
                        isr.close(); // abort the parsing
		      } catch (Throwable tr) {
                      }
	           }

                   public void handleText(char[] data, int pos) {
                     if (data!=null&&data.length>0) {
                       urlPrefixFound=new String(data).trim().equalsIgnoreCase("url:");
                     }
	           }
                }
                ,
                true
              );
            } finally {
              is.close();
            }
          } catch (Throwable t) {}
          return homeUrl;
        }

        public boolean hasHome() {
          return homeUrl!=null||jPackageId>-1;
        }


        public static A add(String fn,String v,String urlBase) {
          return add(fn,v,urlBase,null);
        }

	public static A add(String fn,String v,String urlBase,String home) {
	   A lastAssemble=new A();
           lastAssemble.urlBase=urlBase;
           lastAssemble.j2se_docset=isJ2seBase(urlBase,false);
	   lastAssemble.fullName=fn; lastAssemble.version=v;
           try {
             lastAssemble.homeUrl=new URL(home);
           } catch(Throwable t) {}
	   assembles.add(lastAssemble);
	   return lastAssemble;
	}

	public int compareTo(A o) {
	    int i=fullName.compareTo(o.fullName);
	    if (i==0) i=version.compareTo(o.version);
	    return i;
	}

	class C implements Comparable<C>, Home {

            public URL jPackageHome() {
              return A.this.jPackageHome();
            }

            public URL getHome() {
              return A.this.getHome();
            }

            public boolean hasHome() {
              return A.this.hasHome();
            }


            private boolean _isInterface=false;

            boolean isInterface() {
                return _isInterface;
            }

            protected void setInterface(boolean b) {
                _isInterface=b;
            }

	    public A envelope() {
		return A.this;
	    }

	    private String fullName,url;

            public String getFullName() {
              return fullName;
            }
            /*
            public boolean equals(C o) {
              return compareTo(o)==0;
            }
 */
	    public int compareTo(C o) {
      	      int i=fullName.compareTo(o.fullName);
	      if (i==0) i=A.this.compareTo(o.envelope());
	      return i;
	    }

	    public String getUrl() {
                String ub=urlBase;
                if (!ub.endsWith("/")) ub=ub+"/";
                return ub+url;
	    }

            public String getPackageUrl() throws MalformedURLException {
               String t=getUrl().toString();
               int i=t.lastIndexOf('/');
               return (i>=0)
                 ?(t.substring(0, i+1)+"package-summary.html")
                 :null;
            }


	};

        private boolean j2se_docset=false;

        public boolean isJ2se_docset() {
          return j2se_docset;
        }

        public void setJ2se_docset(boolean f) {
          j2se_docset=f;
        }

	public C addClass(String u,boolean isInterface) {

          if (u.endsWith(".java.html")) return null;
	  C c=new C();
          c.setInterface(isInterface);
	  c.url=u;
          
	  String s=u.substring(0,u.lastIndexOf('.')).replace('/','.');
          
          int i=s.indexOf('.'),j=s.indexOf('-');
          if (i>=0&&j>=0&&j<i) s=s.substring(i+1);
           
          c.fullName=s;
                  
          if (c.fullName.startsWith("java.")) j2se_docset=true;

          if (!classes.contains(c))
	    classes.add(c);
	  return c;
	}

        public String getVersionedName() {
           return fullName+'-'+version;
        }
        
        class TheParserCallback_overviewTree extends ParserCallback {
           String href=null,title=null,target=null,text=null;
           boolean inthier=false;
           
           
           public void handleText(char[] data, int pos) {
             if (data!=null) {
               String s=new String(data).trim();
               if (href!=null)
                 text = s;
               else
                 if (s.toLowerCase().indexOf("interface hierarchy")>=0) 
                   inthier=true;
             }
           }

           public void handleEndTag(HTML.Tag t, int pos) {
              if (t==HTML.Tag.A) {
                 if (href!=null&&text!=null&&href.endsWith(".html")&&
                       href.charAt(0)!='/'&&
                       href.indexOf(":/")<0
                       )  {
                    int i=href.lastIndexOf('/'),j=href.lastIndexOf('.');
                    if (i>0&&text.equals(href.substring(i+1,j))) {
                       //System.out.println(text+" "+href);
                       boolean isInterface=inthier||li_count>0||(title!=null&&title.startsWith("interface"));
                       addClass(href,isInterface);
                       ++li_count;
                    }
                 }
                 href=null;
              }
           }

           int li_count=0;

           public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
              if (t==HTML.Tag.LI) li_count=0; else
              if (t==HTML.Tag.A) {
                 
                 href=null;title=null;target=null;text=null;
                 
                 href=(String)a.getAttribute(HTML.Attribute.HREF);
                 if (href!=null) {
                    if (href.startsWith("./")) href=href.substring(2);
                    title=(String)a.getAttribute(HTML.Attribute.TITLE);
                    target=(String)a.getAttribute(HTML.Attribute.TARGET);
                 }
              }
           }
        }


        class TheParserCallback_allClasses extends ParserCallback {
                boolean italic=false,a2=false;

    	        String href=null,title=null,target=null;


          	public void handleEndTag(HTML.Tag t, int pos) {
                  if (t==HTML.Tag.I) {
                    if (!a2) italic=false;
                  } else
                  if (t==HTML.Tag.A) {
                     if ("classFrame".equals(target)||"content".equals(target)) {
                        //System.out.println(href+" "+title+" "+target);
                        boolean isInterface=italic||(title!=null&&title.startsWith("interface "));
		        addClass(href,isInterface);
                        a2=false;
                        italic=false;
		     }
                  }
	        }

         	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
                  if (t==HTML.Tag.I) {
                     italic=true;
                  } else
		  if (t==HTML.Tag.A) {
                     
                     href=null;title=null;target=null;

		     href=(String)a.getAttribute(HTML.Attribute.HREF);
                     if (href!=null) {
                       a2=true;
                       if (href.startsWith("./")) href=href.substring(2);
		       title=(String)a.getAttribute(HTML.Attribute.TITLE);
		       target=(String)a.getAttribute(HTML.Attribute.TARGET);
                     }
		  }
	        }
	    };


         public void parseClassList_allClasses(Reader r) {
             try {
                parser.parse(r,new TheParserCallback_allClasses(),true);
             } catch (Throwable t) {
             }
         }
         
         public void parseClassList_overviewTree(Reader r) {
             try {
                parser.parse(r,new TheParserCallback_overviewTree(),true);
             } catch (Throwable t) {
             }
         }

         public void parseClassList_fromUrlBase() {
             try {
                URL u=mkUrl(urlBase+"overview-tree.html");
                splashScreen.add("opening of "+u.toString());
                InputStream is=openStream(u,URL_STREAM_TIMEOUT);
                
                if (is!=null) try {
                  splashScreen.add("parsing of "+u.toString());
                  int cnt=classes.size();
                  parseClassList_overviewTree(new InputStreamReader(is));
                  if (cnt<classes.size())  return;              
                } finally {
                  is.close();
                }
                
                
                u=mkUrl(urlBase+"allclasses-frame.html");
                splashScreen.add("opening of "+u.toString());
                is=openStream(u,URL_STREAM_TIMEOUT);
                if (is==null) {
                  URL uu=mkUrl(urlBase+"all-classes.html");
                  is=openStream(uu,URL_STREAM_TIMEOUT);
                  if (is!=null) {
                     u=uu;
                     splashScreen.add("opening of "+u.toString());
                  }
                }
                if (is!=null) {
                  splashScreen.add("parsing of "+u.toString());
                  parseClassList_allClasses(new InputStreamReader(is));
                  is.close();
                } else {
                  splashScreen.add("the opening is failed ("+u.toString()+")");
                }
             } catch (Throwable t) {
                System.err.println(t);
             }
         }

         public void parseClassList() {
            try {
               if (!isLocalUrl(urlBase)) {
                  String id=getId();
                  File cached=new File(cacheDirectory,id);
                  RandomAccessFile cacheFile=new RandomAccessFile(cached,"rw");
                  FileChannel fc=cacheFile.getChannel();
                  FileLock lc=fc.lock();
                  try {
                     boolean cacheLoadSuccess=false;
                     TreeSet<C> oldClasses=new TreeSet<C>(classes);
                     if (cached.exists()&&cached.length()>0) {
                        try {
                           byte buf[]=new byte [(int)cached.length()];
                           cacheFile.read(buf);
                           GZIPInputStream gz=new GZIPInputStream(new ByteArrayInputStream(buf));
                           BufferedReader br=new BufferedReader(new InputStreamReader(gz));
                           int cc=0;
                           try {
                             HashSet<String> clss=new HashSet<String>();
                             String s=null;
                             while(br.ready()) {
                               s=br.readLine();
                               if (s!=null&&s.charAt(0)!='*') {
                                 String fname=s.substring(1),xname=fname;
                                 int z=0;
                                 while ((z=xname.lastIndexOf('.'))>0) {
                                   String x1=xname.substring(0,z),x2=xname.substring(z+1);
                                   if (clss.contains(x1)) xname=x1+':'+x2; else break;
                                 }

                                 if (addClass(xname.replace('.','/').replace(':','.')+".html",
                                   s.startsWith("i"))!=null
                                 ) { ++cc; clss.add(fname); }
                               }
                             }
                             cacheLoadSuccess=cc>0&&"*END*".equals(s);
                           } finally {
                              br.close();
                           }
                        } catch (Throwable t) {
                          System.err.println(t);
                        }
                        if (!cacheLoadSuccess ) classes=new TreeSet<C>(oldClasses);
                     }
                     if (!cacheLoadSuccess) {
                        fc.truncate(0);
                        int s0=classes.size();
                        parseClassList_fromUrlBase();
                        int s1=classes.size();
                        if (s1>s0) {
                           TreeSet<C> newclasses=new TreeSet<C>(classes);
                           newclasses.removeAll(oldClasses);
                           GZIPOutputStream gz=new GZIPOutputStream(new FileOutputStream(cacheFile.getFD()));
                           try {
                              BufferedWriter b=new BufferedWriter(new OutputStreamWriter(gz));
                              for (C c:newclasses) {
                                 String pfx=c.isInterface()?"i":"c";
                                 b.write(pfx+/*c.fullName*/c.url.substring(0,c.url.lastIndexOf('.')).replace('/','.'));
                                 b.newLine();
                              }
                              b.write("*END*");
                              b.newLine();
                              b.flush();
                           } finally {
                              gz.flush();
                              gz.finish();
                           }
                        }
                     }
                  } finally {
                     lc.release();
                     cacheFile.close();
                  }
               } else
                 parseClassList_fromUrlBase();
               changed=false;
            } catch (Throwable t) {
              System.err.println(t);
            }
         }

      public boolean isLocal() {
        return isLocalUrl(urlBase);
      }

      /**
       * Holds value of property enabled.
       */
      private boolean enabled=true;

      /**
       * Getter for property enabled.
       * @return Value of property enabled.
       */
      public boolean isEnabled() {
         return this.enabled;
      }

      /**
       * Setter for property enabled.
       * @param enabled New value of property enabled.
       */
      public void setEnabled(boolean enabled) {
         if (!this.enabled&&enabled) changed=true;
         this.enabled = enabled;
      }

      /**
       * Holds value of property managed.
       */
      private boolean managed=true;

      /**
       * Getter for property managed.
       * @return Value of property managed.
       */
      public boolean isManaged() {
         return this.managed;
      }

      /**
       * Setter for property managed.
       * @param managed New value of property managed.
       */
      public void setManaged(boolean managed) {
         this.managed = managed;
      }

      /**
       * Holds value of property changed.
       */
      private boolean changed=true;

      /**
       * Getter for property changed.
       * @return Value of property changed.
       */
      public boolean isChanged() {
         return this.changed;
      }

      /**
       * Setter for property changed.
       * @param changed New value of property changed.
       */
      public void setChanged(boolean changed) {
         this.changed = changed;
      }

      public void setVersion(String v) {
        v=denullify(v);
        if (!v.equals(version)) {
          version=v; changed=true;
        }
      }

      public void setBaseUrl(String baseUrl) {
        baseUrl=denullify(baseUrl);
        if (!baseUrl.equals(urlBase)) {
          urlBase=baseUrl; changed=true;
        }
      }

      public void setHomeUrl(URL homeUrl) {
        if (!eq(this.homeUrl,homeUrl)) {
           this.homeUrl=homeUrl; changed=true;
        }
      }

      public void setProjectID(String projectID) {
        projectID=denullify(projectID);
        if (!projectID.equals(fullName)) {
          fullName=projectID; changed=true;
        }
      }
  }

  public static boolean eq(Object a,Object b) {
    if (a==b) return true;
    if (a!=null&&b!=null) return a.equals(b);
    return false;
  }

  static Parser parser=new javax.swing.text.html.parser.ParserDelegator(); //null;

  static TreeSet<A.C> classes=new TreeSet<A.C>();
  static TreeSet<A> assembles=new TreeSet<A>();
  static HashMap<String,Integer> shortNameCounters=new HashMap<String,Integer>();

  static A assemblyByURL(URL key) {
    try {
       if (assembles!=null) for (A a:assembles) {
          URL u=new URL(a.urlBase);
          if (key.equals(u))
             return a;
       }
    } catch (Throwable t) {}
    return null;
  }

  //static A lastAssemble=null;

  static Vector<File> findAllClasses(File f) {
      Vector<File> rv=new Vector<File>();
      File a=new File(f,"allclasses-frame.html"),b=new File(f,"all-classes.html");
      if (a.exists()) {
        rv.add(a);
      } else if (b.exists()) {
        rv.add(b);
      } else
        for (File d:f.listFiles()) if (d.isDirectory()&&!d.getName().startsWith(".")) {
          rv.addAll(findAllClasses(d));
        }
      return rv;
  }

  static final int CHECK_URL_MILLIS=10000;


  public static boolean checkDocsetBase(String base) {
    if (base!=null) try {
       base=base.trim();
       if (!base.endsWith("/")) base+='/';
       return
         checkUrl(new URL(base+"allclasses-frame.html"))||
         checkUrl(new URL(base+"all-classes.html"))
       ;
    } catch (Throwable t) {}
    return false;
  }

  public static boolean checkUrl(URL u) {
     try {
       InputStream is=openStream(u,CHECK_URL_MILLIS);
       if (is!=null) {
          is.close();
          return true;
       }
     } catch (Throwable t) {}
     return false;
  }

  public static void removeUnusedIndexCaches() {
    HashSet<String> ids=new HashSet<String>();
    for (A a:assembles) if (a.isEnabled()) ids.add(a.getId());
    removeUnusedIndexCaches(ids);
  }

  public static void removeUnusedIndexCaches(Set<String> used) {
    File fls[]=cacheDirectory.listFiles();
    if (fls!=null)
     for (File f:fls)
      if (!(f.isDirectory()||used.contains(f.getName()))) f.delete();
  }

  public static void removeAllIndexCaches() {
    File fls[]=cacheDirectory.listFiles();
    if (fls!=null)
     for (File f:fls)
      if (!f.isDirectory()) f.delete();
  }

   public static boolean anyIndexCache() {
    File fls[]=cacheDirectory.listFiles();
    if (fls!=null)
     for (File f:fls)
      if (!f.isDirectory()) return true;
     return false;
   }

   public static void setUserAgentProperty() {
      System.setProperty(
            "http.agent",
            "Global Javadoc Viewer "+VERSION+" ("+
            System.getProperty("java.vm.vendor")+" , "+
            System.getProperty("java.runtime.name")+" "+
            System.getProperty("java.vm.version")+" , "+
            System.getProperty("java.vm.name")+" ; "+
            System.getProperty("os.name")+" "+
            System.getProperty("os.arch")+
            ")"
            );
   }
 /*
  public static void setUserAgentInfo(URLConnection uc) {
     if (uc!=null&&uc instanceof HttpURLConnection) {
        HttpURLConnection huc=(HttpURLConnection)uc;

        huc.setRequestProperty(
              "User-Agent","Global Javadoc Viewer "+VERSION+" ("+
              System.getProperty("java.vm.vendor")+" , "+
              System.getProperty("java.runtime.name")+" "+
              System.getProperty("java.vm.version")+" , "+
              System.getProperty("java.vm.name")+" ; "+
              System.getProperty("os.name")+" "+
              System.getProperty("os.arch")+
              ")"
        );
     }
  }
  */


  static final String JPACKAGE_URL_PREFIX="/browser/rpm.php?jppversion=1.6&id=";

  void handleJPackages() {
    try {
      InputStream is=openStream(
        new URL(
          //"http://jpackage.org/"
          "http://www.jpackage.org/browser/browse.php?jppversion=1.6"
        ),
        URL_STREAM_TIMEOUT
      );
      
      if (is!=null) try {
        InputStreamReader isr=new InputStreamReader(is);

       	ParserCallback pc=new ParserCallback() {
                boolean prevTagFits=false;
                int id;

                public void handleText(char[] data, int pos) {
                  if (prevTagFits&&data.length>0) {

                     String s=new String(data);
                     if (s.endsWith("jpp")) {
                       int i=s.lastIndexOf('-');
                       if (i>0) s=s.substring(0, i);
                     }

                     int i=s.lastIndexOf('-');
                     if (i>0&&s.substring(i+1).matches("(\\d|\\.)*\\d"))
                        s=s.substring(0,i);
                     //System.out.println("&&&&&&&&&&&");

                     //System.out.println(id+" "+s);

                     for (A a:assembles) if (a.jPackage&&a.fullName.equals(s)) {
                       a.jPackageId=id;
   //                    System.out.println(id+" "+s);
                       break;
                     }

                     prevTagFits=false;
                  }
	        }

         	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {

                  prevTagFits=false;
                  if (t==HTML.Tag.A) {
		     String href=(String)a.getAttribute(HTML.Attribute.HREF);
                     if (href!=null&&href.startsWith(JPACKAGE_URL_PREFIX)) try {
                        id=Integer.parseInt(href.substring(JPACKAGE_URL_PREFIX.length()));
                        prevTagFits=true;
                     } catch (Throwable exception) {}
		  }
	        }
	    };

         parser.parse(isr,pc,true);

      } finally {
        is.close();
      }
    } catch (Throwable t) {
      System.err.println(t);
    }
  }

  static File
              configFile=new File(configDirectory,"config.xml"),
              licFile=new File(configDirectory,".lic"),
              cacheDirectory=subDirectory(configDirectory,"cache");
  
 

  static public void saveConfig(org.w3c.dom.Document configuration) {
     try {
        FileOutputStream fos=new FileOutputStream( configFile);
        writeXML(configuration,fos);
        fos.close();
     } catch (Throwable t) {}
  }

   static org.w3c.dom.Document configuration=null;//loadConfiguration();

   void syncTheConfigurationWithTheTree() {
     org.w3c.dom.Element source=
        (org.w3c.dom.Element)configuration.getElementsByTagName("source").item(0);
     org.w3c.dom.Node n=source.getFirstChild();
     while ((n=source.getFirstChild())!=null)
       source.removeChild(n);
     for (A a:assembles) {
       org.w3c.dom.Element docset=configuration.createElement("docset");
       source.appendChild(docset);
       docset.setAttribute("base",a.urlBase);
       if (!a.jPackage&&a.getHome()!=null) docset.setAttribute("home",a.getHome().toExternalForm());
       docset.setAttribute("project",a.fullName);
       docset.setAttribute("ver",a.version);
       if (!a.enabled) docset.setAttribute("disable","yes");
       docset.setAttribute("managed",a.managed?"yes":"no");
       docset.setAttribute("j2se",a.isJ2se_docset()?"yes":"no");
     }
   }

   static boolean patchTheConfiguration(
         org.w3c.dom.Document conf,
         org.w3c.dom.Document patch,
         boolean doPatch
         ) {

      boolean anyChanges=false;

      try {
         XPath xpath = XPathFactory.newInstance().newXPath();

         // find the docsets replacements
         org.w3c.dom.NodeList docsets =
               (org.w3c.dom.NodeList) xpath.evaluate(
               "/root/source/docset/replaces/docset",
               patch, XPathConstants.NODESET
               );
         
         org.w3c.dom.Element srcs=
                 (org.w3c.dom.Element) xpath.evaluate(
                 "/root/source",
                 conf, XPathConstants.NODE
                );

         if (docsets!=null) for (int di=0;di<docsets.getLength();di++) {
            // find the node to be replaced
            org.w3c.dom.Element
              replace_pattern=(org.w3c.dom.Element)docsets.item(di),
              rp=(org.w3c.dom.Element)replace_pattern.getParentNode().getParentNode();


            String rp_base=replace_pattern.getAttribute("base");

            if (rp_base==null) continue;

            org.w3c.dom.Element toReplace=
               (org.w3c.dom.Element) xpath.evaluate(
               "/root/source/docset[@base='"+rp_base+"']",
               conf, XPathConstants.NODE
               );
            if (toReplace!=null) {


              String disable=toReplace.getAttribute("disable"),
                     managed=toReplace.getAttribute("managed");

              boolean
                f_managed=(managed==null||!"no".equalsIgnoreCase(managed)),
                f_disable=(disable!=null&&"yes".equalsIgnoreCase(disable));


              org.w3c.dom.Element replacement=
                 (org.w3c.dom.Element) xpath.evaluate(
                 "/root/source/docset[@ver='"+rp.getAttribute("ver")+
                    "' and @project='"+rp.getAttribute("project")+
                 "']",
                 conf, XPathConstants.NODE
                );


                if (doPatch) {
                  if (f_managed&&!f_disable) toReplace.setAttribute("disable", "yes");
                  if (replacement==null) {
                     org.w3c.dom.Element repl=(org.w3c.dom.Element)conf.importNode(rp,false);
                     if (f_disable) repl.setAttribute("disable", "yes");
                     srcs.appendChild(repl);
                  } else
                     if (f_managed&&!f_disable) replacement.setAttribute("disable", "no");
                }
                if (replacement==null) anyChanges=true;

            }
           }
         // do append
         
         org.w3c.dom.NodeList appends =
               (org.w3c.dom.NodeList) xpath.evaluate(
               "/root/source/docset",
               patch, XPathConstants.NODESET
               );
          
         if (appends!=null) for (int di=0;di<appends.getLength();di++) {
            org.w3c.dom.Element append=(org.w3c.dom.Element)appends.item(di);
            String project=append.getAttribute("project"),ver=append.getAttribute("ver");
            if (project!=null&&ver!=null) {
              org.w3c.dom.Element existing=
                 (org.w3c.dom.Element) xpath.evaluate(
                 "/root/source/docset[@ver='"+ver+
                    "' and @project='"+project+
                 "']",
                 conf, XPathConstants.NODE
                );
              if (existing!=null) continue;
         //     System.out.println(project+" "+ver);
              
              if (doPatch) {
                org.w3c.dom.Node ap=conf.importNode(append,false);
                srcs.appendChild(ap);
              }
              anyChanges=true;
              
            }
         }
         
         //writeXML(conf,System.out);
      } catch (Throwable t) {
         System.err.println(t);
      }

      return anyChanges;
   }

   static org.w3c.dom.Document loadConfiguration(boolean doOfferUpdate) {
      boolean cfe=configFile.exists();
      org.w3c.dom.Document doc=
          cfe
         ?loadXmlFile(configFile)
         :loadXmlFile(getResourceUrl("globaldocs/res/config.xml"))
       ;
      if (cfe&&doOfferUpdate&&
          patchTheConfiguration(doc,loadXmlFile(getResourceUrl("globaldocs/res/config.xml")),false)) 
      {
         doOfferUpdate=
            JOptionPane.showConfirmDialog(
                  null,
                  "Do You Want to Upgrade Your Dataset List?",
                  "Some Updated/Addition Datasets were Found",
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE
            )==JOptionPane.YES_OPTION;
      }
      if (!cfe||
          (doOfferUpdate&&patchTheConfiguration(doc,loadXmlFile(getResourceUrl("globaldocs/res/config.xml")),true))
      )
         saveConfig(doc);
      return doc;
   }

 
  boolean isUpgradeAvailable() {
     try {
       org.w3c.dom.Document doc=loadXmlFile(mkUrl("http://globaldocs.zeevbelkin.com/version.xml"),2000);
       if (doc!=null) {
         org.w3c.dom.NodeList nl=doc.getElementsByTagName("version-id");
         if (nl!=null&&nl.getLength()>0) {
           org.w3c.dom.Element el=(org.w3c.dom.Element)nl.item(0);
           if (el!=null) {
             org.w3c.dom.Node n=el.getFirstChild();
             if (n!=null) {
                String ver=n.getNodeValue();
                if (ver!=null) {
                  return verIsGreater(ver,VERSION);
                }
             }
           }
         }
       }
     } catch (Throwable t) {}
     return false;
  }

  void buildGblTree() {
      try {

          String v=null;

          File usrShareJavadoc=new File("/usr/share/javadoc");
          if (usrShareJavadoc.exists()) for (File f:usrShareJavadoc.listFiles())
              if (f.isDirectory()&&f.getCanonicalPath().length()!=f.getAbsolutePath().length())  {
              Vector<File> allPathes=findAllClasses(f);
              if (allPathes.size()==0) continue;
              v=f.getCanonicalPath().substring(f.getAbsolutePath().length());
              if (!Character.isDigit(v.charAt(0))) v=v.substring(1);
                /*
                System.out.println(f.getName()+
                   ' '+v
                );
                 */
              for (File aPath:allPathes) {
                 A a=A.add(f.getName(),v, "file:"+aPath.getParent()+'/');
                 a.setManaged(false);
                 a.jPackage=true;
              }
          }


          if (configuration!=null) {


            XPath xpath = XPathFactory.newInstance().newXPath();

            org.w3c.dom.NodeList docsets =
               (org.w3c.dom.NodeList) xpath.evaluate(
                  "/root/source/docset",
                  configuration, XPathConstants.NODESET
               );

            if (docsets!=null) for (int di=0;di<docsets.getLength();di++) {
              org.w3c.dom.Element docset=(org.w3c.dom.Element)docsets.item(di);

              String
                 project=docset.getAttribute("project"),
                 base=docset.getAttribute("base"),
                 ver=docset.getAttribute("ver"),
                 home=docset.getAttribute("home"),
                 disable=docset.getAttribute("disable"),
                 managed=docset.getAttribute("managed"),
                 j2se=docset.getAttribute("j2se")
                 ;
              boolean
                  fDisable="yes".equalsIgnoreCase(disable),
                  fJ2se="yes".equalsIgnoreCase(j2se),
                  fManaged=!("no".equalsIgnoreCase(managed));

              if (base!=null) try {
                //if (base.indexOf("jflex")>=0)
                //   System.out.println("xxxxxxxx");
                A a=assemblyByURL(new URL(base));
                if (a==null) {
                   if (!(isSet(project)&&isSet(ver)&&isSet(base))) continue;

                   if (base.startsWith("file:/usr/share/javadoc/")
                        &&!isJpackageDir(new File(base.substring(5)))) {
                           // a JPackage docset was removed

                           continue;
                   }

                  a=A.add(
                     project,
                     ver,
                     base,
                     home
                  );


                } //else
                  a.setManaged(
                     !a.isLocal()&& fManaged
                  );
                a.setEnabled(!fDisable);
                if (fJ2se) a.setJ2se_docset(true);

              } catch(Throwable x) {
                System.err.println(x);
              }
            }
          }
          for (A a:assembles) if (a.isEnabled()) a.parseClassList();

          if (!classes.isEmpty()) handleJPackages();

          System.gc();

          addNewClasses(classes);

          // we don't need these tables more
          //for (A.C cls:classes) cls.fullName=null;
          //classes=null;
          //assembles=null;
         // System.gc();
         removeUnusedIndexCaches();
      } catch (Throwable t) {
          System.err.println(t);
      }
  }
  
  boolean isJpackageDir(File dir) {
    return 
     dir.exists()&&
     dir.isDirectory()&&
     (
       new File(dir,"allclasses-frame.html").exists()||
       new File(dir,"overview-tree.html").exists()
     );
  }

  void adjustTheTree() {
    new Thread() {
     public void run() {
      splashScreen=new SplashScreen();
      invokeAndWait(
        new Runnable() {
          public void run() {
            splashScreen.setVisible(true);
            rootNode.removeAllChildren();
          }
        }
      );
      url2node.clear();
      TreeSet<A.C> toRemove=new TreeSet<A.C>();
      for (A.C cl:classes) {
        A a=cl.envelope();
        if (!(assembles.contains(a)&&a.isEnabled())) toRemove.add(cl);
      }
      classes.removeAll(toRemove);
      for (A a:assembles) if (a.isEnabled()&&a.isChanged()) a.parseClassList();
      addNewClasses(classes);

      invokeAndWait(
        new Runnable() {
          public void run() {
            synchronized(jTree.getTreeLock()) {
              jTree.updateUI();
              syncTreeSelection(jHtmlEditorPane);
            }
          }
        }
      );
      //removeUnusedIndexCaches();
      frame.rebuildClassesList();
      splashScreen.thePorcessingEnd();
     }
    }.start();
  }

  static void addNewClasses(Collection<A.C> classes) {

     for (A.C cl:classes) {

        TheMutableTreeNode nodeFor=
              rootNode.getNodeFor(cl.fullName);

        Object payload=nodeFor.getUserObject();

        if (payload!=null) {
           if (payload instanceof A.C && nodeFor.findNamedChild(ASSEMBLES)==null) {
              A.C theClass=(A.C)payload;
              TheMutableTreeNode
                    n=new TheMutableTreeNode(DOCSET),
                    a=new TheMutableTreeNode(theClass.envelope().getVersionedName());
              nodeFor.add(n);
              n.setUserObject(ASSEMBLES);
              //?  nodeFor.setUserObject("");
              a.setUserObject(theClass);
              n.add(a);
              a=new TheMutableTreeNode(cl.envelope().getVersionedName());
              a.setUserObject(cl);
              n.add(a);
           } else {
              TheMutableTreeNode n=nodeFor.findNamedChild(ASSEMBLES);
              if (n!=null) {
                 TheMutableTreeNode
                       a=new TheMutableTreeNode(cl.envelope().getVersionedName());
                 a.setUserObject(cl);
                 n.add(a);
              }
           }
        } else {
           nodeFor.setUserObject(cl);
        }
        // Packages

        TreeNode tn=nodeFor.getParent();
        if (tn instanceof PackageTreeNode) try {
           PackageTreeNode
                 pack=(PackageTreeNode)tn;

           String asname=cl.envelope().getVersionedName();
           String purl=cl.getPackageUrl(),oldpurl=pack.getUrl();

           TheMutableTreeNode n=pack.findNamedChild(ASSEMBLES);
           if (n!=null) {
              PackageTreeNode
                    p=(PackageTreeNode)n.findNamedChild(cl.envelope());
              if (p==null) {
                 p=new PackageTreeNode(asname);
                 p.setUrl(purl);
                 p.setUserObject(cl.envelope());
                 n.add(p);
              }
           } else if (oldpurl==null) {
              pack.setUrl(purl);
              pack.setUserObject(cl.envelope());
           } else if (!purl.equals(oldpurl)) {
              //? pack.setUrl(null);
              TheMutableTreeNode asnode=new TheMutableTreeNode(DOCSET);
              A asm=(A)pack.getUserObject();
              asnode.setUserObject(ASSEMBLES);
              pack.insert(asnode,0);

              PackageTreeNode
                    p1=new PackageTreeNode(asm.getVersionedName()),
                    p2=new PackageTreeNode(asname);

              p1.setUrl(oldpurl);
              p1.setUserObject(asm);
              p2.setUrl(purl);
              p2.setUserObject(cl.envelope());

              asnode.add(p1);
              asnode.add(p2);
           }
        } catch (Throwable t) {}
        //        System.out.println(cl.getUrl());
     }
  }
  
  static String resourcesRoot=null;


  static boolean isResource(URL u) {
     if (u==null) return false;
     String s=u.toString();
     return s.startsWith("about:")||s.startsWith(resourcesRoot);
  }
  
  
  class ActivePane {
     WeakReference<JEditorPane> activePane=null;

     public JEditorPane get() {
        synchronized(this) {
           if (activePane!=null) {
              JEditorPane pane=activePane.get();
              if (pane!=null) return pane;
           }
        }
        return history.currentPane();
     }
     
     public void set(JEditorPane pane) {
        synchronized(this) {
          activePane=new WeakReference<JEditorPane>(pane);
        }
     }
  };
  
  ActivePane activePane=new ActivePane();
  
  final static String THE_PANE="THE_PANE";
  
  public static JEditorPane getPane(AbstractDocument hdoc) {
    return (JEditorPane)hdoc.getProperty(THE_PANE);
  }
  
  SimpleAttributeSet copy(AttributeSet as) {
    SimpleAttributeSet sas=new SimpleAttributeSet();
    Enumeration en=as.getAttributeNames();
    while (en.hasMoreElements()) {
      Object j=en.nextElement();
      Object a=as.getAttribute(j);
      if (a instanceof AttributeSet) {
        sas.addAttribute(j,copy((AttributeSet)a));
      } else {
        sas.addAttribute(j,a);
      }
    }
    return sas;
  }

  class TheEditorKit extends HTMLEditorKit {
      
     public boolean isAutoFormSubmission() {
       return false;
     }
      
     class TheViewFactory implements ViewFactory {
       public View create(Element elem) {
         ViewFactory vf=TheEditorKit.super.getViewFactory();
         View v=vf.create(elem);
         if (v!=null&&v instanceof FormView) {
           FormView fv=new FormView(elem) {
             protected Component createComponent() {
               Component c=super.createComponent();
               if (c!=null&&c instanceof JTextComponent) {
                 FocusGrabber.set(c);
               }
               return c;
             }
           };
           return fv;
         }
         return v;
       }
     };
     
     TheViewFactory tvf=new TheViewFactory();
      
     public ViewFactory getViewFactory() {
      return tvf;
     }
    
     /* Handle this later
     public ViewFactory getViewFactory() {
       final ViewFactory superVF=super.getViewFactory();
       return new ViewFactory() {
          public View create(Element elem) {
            View v=superVF.create(elem);
            
            if (
              v instanceof InlineView && 
              "javax.swing.text.html.InlineView".equals(v.getClass().getCanonicalName())
            ) {
              Class c=v.getClass();
              
              System.out.println(c.getCanonicalName());
              
              
              return new InlineView(elem) {
                 
                public void changedUpdate(DocumentEvent e, java.awt.Shape a, ViewFactory f) {
                   if (sas!=null) {
                     sas.removeAttributes(sas);
                     sas.addAttributes(copy(getElement().getAttributes()));
                   }
                   super.changedUpdate(e,a,f);
                }
                
                SimpleAttributeSet sas=null;
                
                public AttributeSet  getAttributes() {
                  if (sas==null) {
                    AttributeSet as=super.getAttributes();
                    sas=copy(as);
                  }
                  return sas;
                }
              };
            }
            return v;
          }
       };
     }
      */
     
     DragGestureRecognizer dgr=null;
     
     public void install(final JEditorPane c) {
         
       dgr=DragSource
         .getDefaultDragSource()
         .createDefaultDragGestureRecognizer(
            c,
            DnDConstants.ACTION_LINK,
            theTransferHandler
         );
         
       c.setDragEnabled(true);
       c.setTransferHandler(theTransferHandler);
       
       c.addHyperlinkListener(
         new HyperlinkListener() {
           public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
              if (evt instanceof FormSubmitEvent) 
                jHtmlEditorPaneHyperlinkSubmit(c,(FormSubmitEvent)evt);
           }
         }
       );
        
   //    System.out.println(c.getContentType());

       c.addPropertyChangeListener(
         new PropertyChangeListener() {
           public void propertyChange(PropertyChangeEvent evt) {
             if ("document".equals(evt.getPropertyName())&&(evt.getNewValue()!=null)) {
                AbstractDocument doc=(AbstractDocument)evt.getNewValue();
                doc.getDocumentProperties().put(THE_PANE,c);
             }
           }
         }
       );
  
        
       if (c!=jHtmlEditorPane) {
          c.addPropertyChangeListener(framePropertyChangeListener);
       }

       // we need use this as a workaround to the JEditorPane bag ...
       new KeyStrokesActions(
         c,
         KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),
         KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0)
       ) {
            public void actionPerformed(ActionEvent e,KeyStroke ks) {
              JViewport vp=(JViewport)(((JComponent)e.getSource()).getParent());
              JScrollPane sp=(JScrollPane)vp.getParent();
              if (sp!=null) {
                 ActionListener al=sp.getActionForKeyStroke(ks);
                 if (al!=null) {
                    ActionEvent ee=new ActionEvent(sp,e.getID(),e.getActionCommand(),e.getWhen(),e.getModifiers());
                    al.actionPerformed(ee);
                 }
              }
            }
       };

       
       c.addMouseListener(
          new MouseAdapter() {
            void handleFocus() {
               c.grabFocus();
            }
            public void mouseReleased(MouseEvent e) {
              if (c==jHtmlEditorPane) miFrame.setVisible(false);
              activePane.set(null);
              handleFocus();
            }
            public void mouseEntered(MouseEvent e) {
              activePane.set(c);
              if (c==jHtmlEditorPane) {
                miFrame.setVisible(false);
              } else {
                URL url=c.getPage();
                configureLinkMenu(miFrame,url.toString(),false);
                
                if (isSecure(c)) {
                   JMenuItem mi=new JMenuItem("CipherSuite: "+getCipherSuite(url));
                   mi.setEnabled(false);
                   miFrame.insert(mi,1);
                }
                
                miFrame.setVisible(true);
              }
              handleFocus();
            }
          }
       );
       super.install(c);
     }


     PagePreprocessorParser mp=null;

     protected Parser getParser() {
       if (mp==null) 
           mp=new PagePreprocessorParser(
//               new javax.swing.text.html.parser.ParserDelegator() {
//               },
               super.getParser(),
               MacroProcessor.class
           );
       return mp;
     }

     public synchronized Document createDefaultDocument() {
        mp=null;

        final HTMLDocument hdoc=(HTMLDocument)super.createDefaultDocument();

        final StyleSheet ss=hdoc.getStyleSheet();
        
        ss.addRule("table { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("img { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("code { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("body { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("li { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("b { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("a { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("p { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("div { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("span { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("br { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("center { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("i { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("u { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("td { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("pre { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("code { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("strong { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("abbr { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("tt { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("th { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("strike { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("s { font-size:"+DEFAULT_FONT_SIZE+" }");
        ss.addRule("blockquote { font-size:"+DEFAULT_FONT_SIZE+" }");
        
        ss.addRule("small { font-size:"+2*DEFAULT_FONT_SIZE/3+" }");
        
        ss.addRule("h1 { font-size:"+20*DEFAULT_FONT_SIZE/7+" }");
        ss.addRule("h2 { font-size:"+2*DEFAULT_FONT_SIZE+" }");
        ss.addRule("h3 { font-size:"+5*DEFAULT_FONT_SIZE/3+" }");
        ss.addRule("big { font-size:"+5*DEFAULT_FONT_SIZE/3+" }");
        ss.addRule("h4 { font-size:"+3*DEFAULT_FONT_SIZE/2+" }");
        ss.addRule("h5 { font-size:"+4*DEFAULT_FONT_SIZE/3+" }");

        hdoc.addDocumentListener(
          new DocumentListener() {
           
            public void insertUpdate(DocumentEvent e) {
              JEditorPane pane=(JEditorPane)hdoc.getProperty(THE_PANE);
               el_handleAllStyles(pane,false,e.getDocument());
            }

            public void removeUpdate(DocumentEvent e) { }
           
            public void changedUpdate(DocumentEvent e) { 
              // JEditorPane pane=(JEditorPane)hdoc.getProperty(THE_PANE);
              // el_handleAllStyles(pane,false);
            }
          }
        );
        
        if (ss!=null) ss.addChangeListener(
          new ChangeListener() {
             public void stateChanged(ChangeEvent e) {
              // StyleContext.NamedStyle st=(StyleContext.NamedStyle)e.getSource();
               //handleStyle(st,hdoc);
              //      System.out.println(st.getClass()+" "+st);
                
                    JEditorPane pane=(JEditorPane)hdoc.getProperty(THE_PANE);
                    if (pane!=null)
                       handleAllStyles(pane,false,false,null);               
             }
          }
        );     

        mp.setDocument(hdoc);


        return hdoc;
     }
     
      public void read(Reader in, Document doc, int pos) throws IOException, BadLocationException {
        try {
          super.read(in,doc,pos);  
        } catch(IOException t) {
          t.printStackTrace();
        }        
      }
      
  };
  
  TheEditorKit 
     editorKit=new TheEditorKit(),
     imagesKit=new TheEditorKit() {
       // a hack to support image URL's
       public void read(Reader in, Document doc, int pos) throws IOException, BadLocationException {
         URL u=(URL)doc.getProperty(Document.StreamDescriptionProperty);
         ByteArrayInputStream bais=new ByteArrayInputStream(
                 ("<html><body><img src=\""+u.toString()+"\"></body></html>")
                 .getBytes()
                 );
          super.read(new InputStreamReader(bais),doc,pos);
       }
       public String getContentType() {
          return "image";
       }
     }
  ;

  static class ClassTreeNode extends TheMutableTreeNode {
      public ClassTreeNode(String _name) { super(_name); }


      private boolean _isInterface=false;

      public boolean isInterface() {
          return _isInterface;
      }


      public void setUserObject(Object uo) {
          if (uo!=null&&uo instanceof A.C) {
              _isInterface=((A.C)uo).isInterface();
          }
          super.setUserObject(uo);
      }

      public String getUniqName() {
          synchronized(shortNameCounters) {
              String sname=toString();
              Integer cnt=shortNameCounters.get(sname);
              if (cnt!=null&&cnt>1)
                  sname=getClassPath();

              String lp[]=getListOfProjects();

              if (lp!=null) {
                 sname+=" (";
                 for (int i=0;i<lp.length;i++) {
                   sname+=lp[i];
                   if (i<lp.length-1) sname+=",";
                 }
                 sname+=")";
              }

              return sname;
          }
      }
  }

  static class PkTreeNode extends TheMutableTreeNode {
      public PkTreeNode(String _name) { super(_name); }
  }

  static class PackageTreeNode extends PkTreeNode {

      public PackageTreeNode(String _name) { super(_name); }

      /**
       * Holds value of property url.
       */
      private String url=null;

      /**
       * Getter for property url.
       * @return Value of property url.
       */
      public String getUrl() {

          return this.url;
      }

      /**
       * Setter for property url.
       * @param url New value of property url.
       */
      public void setUrl(String url) {



          if (this.url!=null)
              if (url2node.containsKey(this.url))
                  url2node.remove(this.url);

          this.url = url;

          if (url!=null)
              url2node.put(url,this);

      }

      public String toString() {
          Object uo=getUserObject();
          if (uo!=null && uo instanceof A) {
              A a=(A)uo;
              if (ASSEMBLES.equals(getParentUserObject())/*||(findNamedChild(ASSEMBLES)!=null)*/)
                  return super.toString();

              TheMutableTreeNode tn=(TheMutableTreeNode)findNamedChild(ASSEMBLES);
              if (tn!=null) {
                 String s=super.toString()+" (";
                 for (int i=0;i<tn.getChildCount();i++) {
                   TreeNode n=tn.getChildAt(i);
                   s+=n.toString();
                   if (i<tn.getChildCount()-1) s+=",";
                }
                return s+")";
              }

              return super.toString()+" ("+a.getVersionedName()+")";
          }
          return super.toString();
      }


      public String getVersionedName() {
          Object uo=getUserObject();
          if (uo!=null && uo instanceof A) {
              A a=(A)uo;
              return a.getVersionedName();
          }
          return "";
      }

  };

  public static class TheMutableTreeNode
          extends DefaultMutableTreeNode
          implements Home {

      public String[] getListOfProjects() {
        TheMutableTreeNode asn=findNamedChild(ASSEMBLES);
        if (asn!=null) {
          int cc=asn.getChildCount();
          if (cc>0) {
            String rv[]=new String[cc];
            for (int i=0;i<cc;i++) rv[i]=asn.getChildAt(i).toString();
            return rv;
          }
        }
        Object uo=this.getUserObject();
        if (uo!=null&& uo instanceof A.C) {
          A a=((A.C)uo).envelope();
          return new String [] {a.getVersionedName()};
        }
        return null;
      }

      public void setUserObject(Object userObject) {
          Object oldUO=getUserObject();

          if (oldUO!=null&&oldUO instanceof A.C &&!oldUO.equals(userObject)) {
              A.C c=(A.C)oldUO;
              String oldUrl=c.getUrl();
              if (url2node.containsKey(oldUrl))
                  url2node.remove(oldUrl);
          }

          super.setUserObject(userObject);
          if ((userObject!=null)&&userObject instanceof A.C) {
              url2node.put(getUrl(),this);
          }
      }

      TheMutableTreeNode getPParent() {
          if (getParent()!=null) {
              TreeNode tn=getParent().getParent();
              if (tn!=null&& tn instanceof TheMutableTreeNode)
                  return (TheMutableTreeNode)tn;
          }
          return null;
      }

      Object getParentUserObject() {
          if (getParent()!=null) {
              DefaultMutableTreeNode tn=(DefaultMutableTreeNode)getParent();
              return tn.getUserObject();
          }
          return null;
      }

      String getTitle() {
          if (ASSEMBLES.equals(getParentUserObject()))
              return getPParent().getClassPath()+" ("+toString()+")";
          return getClassPath();
      }

      String getClassPath() {
          String r=name;
          TreeNode n=this;
          while (((n=n.getParent())!=null)&&(n!=rootNode)) {
              TheMutableTreeNode m=(TheMutableTreeNode)n;
              r=m.name+'.'+r;
          }
          return r;
      }


      TheMutableTreeNode findNamedChild(Object key) {
          Enumeration<TheMutableTreeNode> en=children();
          while (en.hasMoreElements())  {
              TheMutableTreeNode n=en.nextElement();
              if (key.equals(n.getUserObject())) return n;
          }
          return null;
      }

   //   void removeAssembly(A a) {

   //   }


      private String name=null;

      private TheMutableTreeNode allocatePath(String path) {
          return allocatePath(rootNode,path);
      }

      private TheMutableTreeNode allocatePath(DefaultMutableTreeNode p,String path) {
          TheMutableTreeNode r=null;

          int i=path.indexOf('.');

          if (i<0)
              p.add((r=new ClassTreeNode(path)));
          else {
              String tail=path.substring(i+1);
              if (tail.indexOf('.')>=0)
                  r=new PkTreeNode(path.substring(0, i));
              else
                  r=new PackageTreeNode(path.substring(0, i));
              p.add(r);
              r=allocatePath(r, tail);
          }

          return r;
      }

      TheMutableTreeNode getNodeFor(String path) {
          //       System.out.println(">"+path);


          TheMutableTreeNode r=findBestChoise(path);
          if (r==null) return allocatePath(path);

          String cp=r.getClassPath();

//        System.out.println(cp);

          int rpl=cp.length();
          if (rpl==path.length())
              return r;
          return allocatePath(r,path.substring(rpl+1));
      }

      protected TheMutableTreeNode findBestChoise(String path) {
          int i=path.indexOf('.');
          if (i<0) {
              return path.equals(name)?this:null;
          }
          String head=path.substring(0, i),tail=path.substring(i+1);
          if (head.equals(name)) {
              TheMutableTreeNode theBest=null;
              if (children!=null) for (Object obj:children) if (obj instanceof TheMutableTreeNode) {
                  TheMutableTreeNode
                          n=(TheMutableTreeNode)obj,
                          b=n.findBestChoise(tail);
                  if (b!=null)
                      if ((theBest==null)||(theBest.getDepth()<b.getDepth()))
                          theBest=b;
              }
              return (theBest==null)?this:theBest;
          }
          if (this==rootNode) {
              if (children!=null) for (Object obj:children) if (obj instanceof TheMutableTreeNode) {
                  TheMutableTreeNode
                          n=((TheMutableTreeNode)obj).findBestChoise(path);
                  if (n!=null) return n;
              }
          }
          return null;
      }



      public String getVersionedName() {
          Object uo=getUserObject();
          if (uo!=null && uo instanceof A.C) {
              A.C c=(A.C)uo;
              return c.envelope().getVersionedName();
          }
          return "";
      }


      public String toString() {
          return name==null?"":name;
      }

      public TheMutableTreeNode() {}

      public TheMutableTreeNode(String _name) { name=_name; }

      public String getUrl() {
          Object payload=getUserObject();
          if (payload!=null && payload instanceof A.C) try {
              return ((A.C)payload).getUrl();
          } catch (Throwable t) {}
          return null;
      }


      public boolean hasHome() {
          Object payload=getUserObject();
          if (payload!=null&&payload instanceof Home)
              return ((Home)payload).hasHome();
          return false;
      }

      public URL getHome() {
          Object payload=getUserObject();
          if (payload!=null&&payload instanceof Home)
              return ((Home)payload).getHome();
          return null;
      }

      public URL jPackageHome() {
          Object payload=getUserObject();
          if (payload!=null&&payload instanceof Home)
              return ((Home)payload).jPackageHome();
          return null;
      }

  };

  static TheMutableTreeNode rootNode=new TheMutableTreeNode();

  static final String GJV_LastSearchEnd="GJV_LastSearchEnd";
  class LastSearchEnd {
     public void set(int pos) {
       JEditorPane pane=activePane.get();
       Document doc=pane.getDocument();
       if (doc!=null) doc.putProperty(GJV_LastSearchEnd,new Integer(pos));
     }
     public int get() {
       JEditorPane pane=activePane.get();
       Document doc=pane.getDocument();
       if (doc!=null) {
          Integer i=(Integer)doc.getProperty(GJV_LastSearchEnd);
          if (i!=null) return i;
       }
       return 0;
     }
  };
  
  LastSearchEnd lastSearchEnd=new LastSearchEnd();
  
  //int lastSearchEnd=0;
  
  
  String needle="";

  void findThenMove(String needle) {
      Document doc=jHtmlEditorPane.getDocument();
      Position
              start=doc.getStartPosition(),
              end=doc.getEndPosition();

      int so=start.getOffset(),eo=end.getOffset();

      if (eo>so) try {
          String  txt=doc.getText(so,eo-so).toUpperCase();
          int ix=txt.indexOf(needle.toUpperCase());

          if (ix>=0) {
              // jHtmlEditorPane.select(ix,ix+needle.length());
              jHtmlEditorPane.setCaretPosition(ix);
          }

      } catch (Throwable t) {
      }
  }

  void execFindAgain() {
      if (needle.length()==0) execFindDialog();
      else {
          JEditorPane jHtmlEditorPane=activePane.get();
          jHtmlEditorPane.grabFocus();
          Document doc=jHtmlEditorPane.getDocument();
          Position
                  start=doc.getStartPosition(),
                  end=doc.getEndPosition();

          int so=start.getOffset(),eo=end.getOffset();

          if (eo>so) try {
              String  txt=doc.getText(so,eo-so).toUpperCase(),
                      N=needle.toUpperCase();
              int ix=txt.indexOf(N, lastSearchEnd.get());

              if (ix>=0) {
                  lastSearchEnd.set(ix+needle.length());
                  jHtmlEditorPane.select(ix,lastSearchEnd.get());
              } else {
                  lastSearchEnd.set(0);
                  ix=txt.indexOf(N);
                  
                  if (ix>=0) {
                     lastSearchEnd.set(ix+needle.length());
                     jHtmlEditorPane.select(ix,lastSearchEnd.get());
                  }
              }

          } catch (Throwable t) {
          }
      }
  }


  void execFindDialog() {
      //String
      JOptionPane op=
              new JOptionPane("Find What:",JOptionPane.PLAIN_MESSAGE,JOptionPane.OK_CANCEL_OPTION);

      op.setWantsInput(true);
      op.setInitialSelectionValue(needle);

      JDialog dlg=op.createDialog(this, "Find");

      dlg.setModal(true);
      dlg.setVisible(true);



      Integer val=(Integer)op.getValue();
      if (val==null||val!=JOptionPane.OK_OPTION) return;

      String n=(String)op.getInputValue();

      if (n==null) return;


      n=n.trim();
      if (n.length()>0) {
          needle=n;
          
          JEditorPane jHtmlEditorPane=activePane.get();

          Document doc=jHtmlEditorPane.getDocument();
          Position
                  start=doc.getStartPosition(),
                  end=doc.getEndPosition();

          int so=start.getOffset(),eo=end.getOffset();

          if (eo>so) try {
              jHtmlEditorPane.grabFocus();


              String  txt=doc.getText(so,eo-so).toUpperCase(),
                      N=needle.toUpperCase();
              int ix=txt.indexOf(N, lastSearchEnd.get());

              if (ix>=0) {
                  lastSearchEnd.set(ix+needle.length());
                  jHtmlEditorPane.select(ix,lastSearchEnd.get());
              } else {
                  lastSearchEnd.set(0);
                  ix=txt.indexOf(N);
                  if (ix>=0) {
                     lastSearchEnd.set(ix+needle.length());
                     jHtmlEditorPane.select(ix,lastSearchEnd.get());
                  }
              }

          } catch (Throwable t) {
            System.err.println(t);
          }
      }
  }

  Icon icClass=loadIcon("globaldocs/res/class.gif");
  Icon icInterface=loadIcon("globaldocs/res/interface.gif");
  ImageIcon icRa=loadIcon("globaldocs/res/helpviewer_forward.png");
  Icon icRightArrow=scaleIcon(icRa,icRa.getIconWidth(),12);
  ImageIcon icNul=loadIcon("globaldocs/res/unvisible_arrow.png");
  Icon icNull=scaleIcon(icNul,icNul.getIconWidth(),12);

  ImageIcon icPackage_closed=loadIcon("globaldocs/res/p_closed.gif");
  ImageIcon icPackage_open=loadIcon("globaldocs/res/p_open.gif");
  ImageIcon icPackages_closed=loadIcon("globaldocs/res/pks_closed.gif");
  ImageIcon icPackages_open=loadIcon("globaldocs/res/pks_open.gif");
  ImageIcon icCup=loadIcon("globaldocs/res/cup.gif");


  static ImageIcon scaleIcon(ImageIcon ic,int w,int h) {
      BufferedImage img=new BufferedImage(
              w,h,BufferedImage.TYPE_4BYTE_ABGR
              );

      int hh=(ic.getIconHeight()-h)>>1,
              ww=(ic.getIconWidth()-w)>>1;

      Graphics g=img.getGraphics();
      g.drawImage(
              ic.getImage(),
              0,0,ic.getIconWidth()-1-ww*2,ic.getIconHeight()-1-hh*2,
              0,0,ic.getIconWidth()-1,ic.getIconHeight()-1,
              null
              );
      g.dispose();
      return new ImageIcon(img);
  }



  static ImageIcon pressedIcon(ImageIcon ic) {
      BufferedImage img=new BufferedImage(
              ic.getIconWidth(),ic.getIconHeight(),BufferedImage.TYPE_4BYTE_ABGR
              );
      Graphics g=img.getGraphics();
      g.drawImage(ic.getImage(),2,2,ic.getIconWidth()-1,ic.getIconHeight()-1,
              0,0,ic.getIconWidth()-1,ic.getIconHeight()-1,null);
      g.dispose();
      return new ImageIcon(img);
  }

  static void createPressedIcon(JButton b) {
      b.setPressedIcon(pressedIcon((ImageIcon)b.getIcon()));
  }

  static void createPressedIcon(JButton ... bs) {
      for (JButton b:bs) createPressedIcon(b);
  }


  TreePath oldPath=null;

  JMenu miNavigate=null,miLink=null,miPage=null,miFrame=null,miMembers=null;

  static SplashScreen splashScreen=null;

  class SplashScreen extends JFrame {
      private DefaultListModel model=new DefaultListModel();
      private JList list=new JList(model);
      private JScrollPane scrollingList = new JScrollPane(list);

      ImageIcon ic9kb8=loadIcon("globaldocs/res/9kb8.gif");

      private JLabel globe=new JLabel(ic9kb8);


      SplashScreen() {
          super(TITLE);
          setUndecorated(true);
          setIconImage(icCup.getImage());

          Dimension ssSize=new Dimension(430,240);
          setPreferredSize(ssSize);

          setSize(ssSize);
          setLocation((screenDimension.width-ssSize.width)>>1,(screenDimension.height-ssSize.height)>>1);
          JLabel lbl=new JLabel(TITLE,JLabel.CENTER);

          lbl.setForeground(Color.BLUE);
          lbl.setFont(new Font("Serif", Font.ITALIC|Font.BOLD,30));
          lbl.setBorder(new EmptyBorder(20,0,0,0));

          getRootPane().setBorder(new BevelBorder(BevelBorder.RAISED));

          getContentPane().add(lbl,BorderLayout.NORTH);


          globe.setBorder(new EmptyBorder(0,0,10,0));

          getContentPane().add(globe,BorderLayout.CENTER);
          getContentPane().add(scrollingList,BorderLayout.SOUTH);
          scrollingList.setPreferredSize(new Dimension(38, 80));
          //     list.setAutoscrolls(true);
          list.setFont(smallFont);

          getContentPane().setBackground(new Color(0xe0,0xf0,0xff));

          setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

          Dimension d=vs.getPreferredSize();
          d.width=12;
          vs.setPreferredSize(d);
          d=hs.getPreferredSize();
          d.height=12;
          hs.setPreferredSize(d);

          //      scrollingList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

          list.setBackground(new Color(0,0xff,0xff));
          list.setCellRenderer(
                  new DefaultListCellRenderer() {
              public void setText(String s) {
                  super.setText(s);
                  setForeground(
                          (s.indexOf("fail")>=0)
                          ?Color.RED
                          :Color.BLACK
                          );
              }
          }
          );

          pack();


      }

      public void thePorcessingEnd() {
          if (anyError)  {

              getContentPane().remove(scrollingList);
              getContentPane().remove(globe);
              getContentPane().add(scrollingList,BorderLayout.CENTER);
              JPanel pan=new JPanel();
              getContentPane().add(pan,BorderLayout.SOUTH);
              JLabel lbl=new JLabel("Processing of One or More Sources Has Been Failed");
              lbl.setForeground(Color.RED);
              lbl.setFont(smallFont);
              lbl.setHorizontalAlignment(JLabel.CENTER);
              pan.setLayout(new BorderLayout());
              pan.add(lbl,BorderLayout.NORTH);
              JButton btn=new JButton("Close");
              btn.setMnemonic(KeyEvent.VK_C);
              btn.addActionListener(
                      new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                      SplashScreen.this.dispose();
                  }
              }
              );

              pan.add(btn,BorderLayout.SOUTH);
              pack();
              setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
              setAlwaysOnTop(true);
          } else
              dispose();
      }

      JScrollBar vs=scrollingList.getVerticalScrollBar(),
              hs=scrollingList.getHorizontalScrollBar();

      boolean anyError=false;

      public void add(final Object obj) {
          invokeAndWait(
                  new Runnable() {
              public void run() {

                  if (!anyError)
                      anyError=obj.toString().indexOf("fail")>0;

                  model.addElement(obj);
                  vs.setValue(vs.getMaximum());
              }
          }
          );
      }
  };


  static final Color panelBackgroundColor=(Color)UIManager.get("Panel.background");

  static MainFrame frame;
  static String args[];

  /** Creates new form MainFrame */
  public MainFrame() {
      setupRC();

      resourcesRoot=getResourceUrl("globaldocs").toString();

      frame=this;

      //    this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);

      //Dimension sd=getToolkit().getScreenSize();
      vfyThatBrowsersExist();
      setIconImage(icCup.getImage());

      splashScreen=new SplashScreen();

      invokeAndWait(
              new Runnable() {
          public void run() {
              splashScreen.setVisible(true);
          }
      }
      );

      try {
          // Wait some time to have the SplashScreen well painted
          java.lang.Thread.currentThread().sleep(300);
      } catch (Throwable t) {}


      buildGblTree();

      invokeAndWait(
              new Runnable() {
          public void run() {
              splashScreen.thePorcessingEnd();
              
            //  Thread.currentThread().setContextClassLoader(classloader);
          }
      }
      );
      
     // Thread.currentThread().setContextClassLoader(classloader);

      initComponents();

        invokeAndWait(
              new Runnable() {
          public void run() {
      jComboBox__url.setUI(
        new javax.swing.plaf.metal.MetalComboBoxUI() {
          protected ComboBoxEditor createEditor() {
            javax.swing.plaf.metal.MetalComboBoxEditor ed=
               new javax.swing.plaf.metal.MetalComboBoxEditor() {
                  public Component getEditorComponent() {
                    JComponent c=(JComponent)super.getEditorComponent();
                    c.setBorder(new EmptyBorder(1,1,1,1));
                    FocusGrabber.set(c);
                    return c;
                  }
               };
            return ed;
          }
          protected JButton createArrowButton() {
            JButton b=super.createArrowButton();
            b.setIcon(
              new javax.swing.plaf.metal.MetalComboBoxIcon() {
                public int getIconWidth() {
                  return 6;
                }
              }
            );
            b.setMargin(new Insets(0,0,1,0));
            return b;
          }
        }

      );


                }
      }
      );

      jHtmlEditorPane.setEditorKitForContentType("text/html",editorKit);
      jHtmlEditorPane.setEditorKitForContentType("image/jpeg",imagesKit);
      jHtmlEditorPane.setEditorKitForContentType("image/gif",imagesKit);
      jHtmlEditorPane.setEditorKitForContentType("image/png",imagesKit);
      jHtmlEditorPane.setEditorKitForContentType("image/x-xbitmap",imagesKit);
      jHtmlEditorPane.setEditorKitForContentType("image/x-xpixmap",imagesKit);

      createPressedIcon(
          jButtonNext,jButtonPrev,jButtonUp,jButtonStop,jButtonHome,jButtonJPackage,
          jButton_ZoomIn,jButton_ZoomOut,jButtonReload,jButton_Docsets,
          jButton_clearUrl,jButton__capture
      );

     // jButton__capture.setVisible(false);

      viewer_hs=jScrollBrowserPane.getHorizontalScrollBar();
      viewer_vs=jScrollBrowserPane.getVerticalScrollBar();

      invokeLater(
        new Runnable() {
          public void run() {
             setExtendedState(MAXIMIZED_BOTH);
             jSplitPane1.setDividerLocation(300);
          }
        }
      );


      jTree.setCellRenderer(
              new DefaultTreeCellRenderer() {
          synchronized public Component getTreeCellRendererComponent(JTree tree, Object value,
                  boolean sel,
                  boolean expanded,
                  boolean leaf, int row,
                  boolean hasFocus) {

              Icon iclo=getClosedIcon(),iope=getOpenIcon(),icleaf=getLeafIcon();

              if (row==0) {
                  setClosedIcon(null);
                  setOpenIcon(null);
                  setLeafIcon(null);
              } else if (value instanceof ClassTreeNode) {
                  ClassTreeNode tn=(ClassTreeNode)value;
                  if (tn.isInterface()) {
                      setClosedIcon(icInterface);
                      setOpenIcon(icInterface);
                      setLeafIcon(icInterface);
                  } else {
                      setClosedIcon(icClass);
                      setOpenIcon(icClass);
                      setLeafIcon(icClass);
                  }
              } else if (value instanceof PackageTreeNode) {
                  setClosedIcon(icPackage_closed);
                  setOpenIcon(icPackage_open);
                  setLeafIcon(icCup);
              } else if (value instanceof PkTreeNode) {
                  setClosedIcon(icPackages_closed);
                  setOpenIcon(icPackages_open);
              } else {
                  setLeafIcon(icCup);
              }


              DefaultTreeCellRenderer comp=(DefaultTreeCellRenderer)super.getTreeCellRendererComponent(
                      tree,value, sel,expanded, leaf, row,  hasFocus);

              setClosedIcon(iclo);
              setOpenIcon(iope);
              setLeafIcon(icleaf);

              comp.setBackgroundNonSelectionColor(
                      panelBackgroundColor
                      );

              return comp;
          }
      }
      );

      EmptyBorder gap=new EmptyBorder(5,5,5,5);
      /*
      filterTextField.setBorder(
              new CompoundBorder(gap, new BevelBorder(BevelBorder.LOWERED))
              );
      */
      classesListScroller.setBorder(
              new CompoundBorder(gap, classesListScroller.getBorder())
              );
      classesList.setCellRenderer(
              new DefaultListCellRenderer() {
          public Component getListCellRendererComponent(
                  JList list,
                  Object value,
                  int index,
                  boolean isSelected,
                  boolean cellHasFocus
                  ) {
              Object obj=value;
              if (obj instanceof ClassTreeNode)
                  obj=((ClassTreeNode)obj).getUniqName();
              Component comp=
                      super.getListCellRendererComponent(
                      list,obj,
                      index,
                      false,
                      false
                      );
              comp.setBackground(panelBackgroundColor);
              return comp;
          }
      }
      );
      classesList.setModel(classListDataModel=new ClassListDataModel());

      searchMaskJPanel1.addDocumentListener(
              new DocumentListener() {
//
          /**
           * Gives notification that there was an insert into the document.  The
           * range given by the DocumentEvent bounds the freshly inserted region.
           *
           * @param e the document event
           */
          public void insertUpdate(DocumentEvent e) {
            rebuildClassesList();
          }

          /**
           * Gives notification that a portion of the document has been
           * removed.  The range is given in terms of what the view last
           * saw (that is, before updating sticky positions).
           *
           * @param e the document event
           */
          public void removeUpdate(DocumentEvent e) {
            rebuildClassesList();
          }

          /**
           * Gives notification that an attribute or set of attributes changed.
           *
           * @param e the document event
           */
          public void changedUpdate(DocumentEvent e) {}


//
      }
      );

      boolean ua=isUpgradeAvailable();
      jLabel_Upgrade.setVisible(ua);
      jLabel_Upgrade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

      if (!ua) jPanel_tips.setPreferredSize(jLabel_tip.getPreferredSize());

      buildMainMenu();
      
      jHtmlEditorPane.setTransferHandler(theTransferHandler);
      
      if (initialUrl!=null) navigate(initialUrl); else {
        if (args.length>0) 
          navigate(args[0]);
        else
          goAbout();
      }
      
      
      initalizationCompleled=true;
  }
  
  boolean initalizationCompleled=false;
  String  initialUrl=null;
  
  
  private void setupRC() {
    try {

       final String rcAppId=getDisplayID();
//       String rcLocator="//127.0.0.1/Globaldocs_"+rcAppId;

       RemoteControl rc=
           new RemoteControl() {
                public String getDisplayId() {
                  return rcAppId;  //getGraphicsConfiguration().getDevice().getIDstring();
                }
                public void navigate(final String s) {
                   if (initalizationCompleled) invokeAndWait(
                      new Runnable() {
                        public void run() {
                         MainFrame.this.navigate(s);
                         if (getState()==JFrame.ICONIFIED)
                            setState(JFrame.NORMAL);
                        }
                      }
                    );
                   else
                    initialUrl=s;
                }
             };
  
      //globaldocs.ipc.PrefRPC pRPC=globaldocs.ipc.PrefRPC.open();
      globaldocs.ipc.FileRPC pRPC=new globaldocs.ipc.FileRPC();
      
      
      RemoteControlMBean px=(RemoteControlMBean)pRPC.getProxy(RemoteControlMBean.class);
      pRPC.addHandler(RemoteControlMBean.class,rc);
      
      if (!getActiveAppInstancesIds().isEmpty()) {
          //System.out.println(px.getAppInstanceId());
        if (args.length>0) {
           px.navigate(args[0]);
           System.exit(1);
        }
      }

      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
      ObjectName name = new ObjectName("globaldocs:type=RemoteControl");
      mbs.registerMBean(rc,name);   

     } catch (Throwable t ) {
        t.printStackTrace();
     } 
  }

  public void rebuildClassesList() {
     classListDataModel.rebuildTheList();
  }

  ClassListDataModel classListDataModel=null;

  class ClassListDataModel extends AbstractListModel {
      ArrayList<ClassTreeNode> filtered=new ArrayList<ClassTreeNode>();

      void walk(TheMutableTreeNode n) {
          if (n instanceof ClassTreeNode) {
              String sname=n.toString(),snameLc=sname.toLowerCase();
              if (!(tx!=null&&snameLc.indexOf(tx)<0||
                  txs!=null&&!snameLc.startsWith(txs)||
                  txe!=null&&!snameLc.endsWith(txe))) {

                    filtered.add((ClassTreeNode)n);
                    Integer cnt=shortNameCounters.get(sname);
                    shortNameCounters.put(sname,(cnt==null)?1:(cnt+1));
              }
          }
          for (int i=0;i<n.getChildCount();i++)  walk((TheMutableTreeNode)n.getChildAt(i));
      }

      private String tx=null,  // the middle search substring
                     txs=null, // the start search substring
                     txe=null; // the end search substring
    
     private Refresher refresher=new Refresher("classlist_refresher") {
       protected void performRefresh() {
          synchronized(classesList.getTreeLock()) {
            _rebuildTheList();
          }
          classesList.updateUI();
       }
     };

      
      public void rebuildTheList() {
          refresher.refresh();
      }

      private void _rebuildTheList() {
          synchronized(shortNameCounters) {
              filtered.clear();
              tx=searchMaskJPanel1.getConatins();
              txs=searchMaskJPanel1.getStartsWith();
              txe=searchMaskJPanel1.getEndsWith();
              shortNameCounters.clear();
              walk(rootNode);
              java.util.Collections.sort(
                      filtered,
                      new Comparator<ClassTreeNode>() {
                  public int compare(ClassTreeNode o1, ClassTreeNode o2) {
                      return o1.toString().compareTo(o2.toString());
                  }
                  public boolean equals(Object obj) {
                      return false;
                  }
              }
              );
          }
      }

      public ClassListDataModel() {
          rebuildTheList();
      }

      public Object getElementAt(int index) {
          synchronized(shortNameCounters) {
              return filtered.get(index);
          }
      }

      public int getSize() {
          synchronized(shortNameCounters) {
              return filtered.size();
          }
      }
  };

  static final int SCT_KeyMask=java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

  void buildMainMenu() {

      JMenuItem mi=jPopupMenu1.add("Copy");
      mi.setMnemonic(KeyEvent.VK_C);
      mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,SCT_KeyMask));

      mi.addActionListener(
              new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              if (jHtmlEditorPane.getSelectedText()==null)
                  jHtmlEditorPane.selectAll();
              jHtmlEditorPane.copy();
          }
      }
      );

      mi=jPopupMenu1.add("Find");
      mi.setMnemonic(KeyEvent.VK_F);
      mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,SCT_KeyMask));

      mi.addActionListener(
              new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              execFindDialog();
          }
      }
      );

      mi=jPopupMenu1.add("Find again");
      mi.setMnemonic(KeyEvent.VK_G);
      mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));

      mi.addActionListener(
              new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              execFindAgain();
          }
      }
      );

      miLink=new JMenu("Link");
      jPopupMenu1.add(miLink);
      miLink.setMnemonic(KeyEvent.VK_L);
      miLink.setVisible(false);
      /////////////////////////////////////////////

      miMembers=new JMenu("Members");
      jPopupMenu1.add(miMembers);
      miMembers.setMnemonic(KeyEvent.VK_M);
      miMembers.setVisible(false);

      miFrame=new JMenu("Frame");
      jPopupMenu1.add(miFrame);
      miFrame.setMnemonic(KeyEvent.VK_R);
      miFrame.setVisible(false);

      miPage=new JMenu("Page");
      jPopupMenu1.add(miPage);
      miPage.setMnemonic(KeyEvent.VK_P);

      miNavigate=new JMenu("Navigate");
      jPopupMenu1.add(miNavigate);
      miNavigate.setMnemonic(KeyEvent.VK_N);

      JMenu me=new JMenu("Zoom");
      jPopupMenu1.add(me);
      me.setMnemonic(KeyEvent.VK_Z);

      Integer zooms[]=new Integer[] {50,100,150,200,250,300,350,400};

      for (final Integer z:zooms) {
         String title=z.toString()+'%';
         if (z<100) title=' '+title;
         mi=me.add(title);
         mi.addActionListener(
           new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 font_size=(DEFAULT_FONT_SIZE*z)/100;
                 handleAllStyles();
              }
           }
         );
      }


      mi=jPopupMenu1.add("Options");
      mi.setMnemonic(KeyEvent.VK_O);

      mi.addActionListener(
              new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            new Options_Dialog(frame,true).setVisible(true);
          }
      }
      );

      mi=jPopupMenu1.add("Quit");
      mi.setMnemonic(KeyEvent.VK_Q);
      mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,SCT_KeyMask));

      mi.addActionListener(
              new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              System.exit(1);
          }
      }
      );

      jPopupMenu1.addSeparator();

//
      mi=jPopupMenu1.add("Help");
      mi.setMnemonic(KeyEvent.VK_H);
      mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));

      mi.addActionListener(
              new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            navigate("about:help");
          }
      }
      );

//
      mi=jPopupMenu1.add("About");
      mi.setMnemonic(KeyEvent.VK_A);

      mi.addActionListener(
              new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              goAbout();
          }
      }
      );

      InputMap am=jHtmlEditorPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);


      for (Component c:jPopupMenu1.getComponents())
          if (c instanceof JMenuItem ) {
          final JMenuItem mim=(JMenuItem)c;
          KeyStroke ac=mim.getAccelerator();
          if (ac!=null) {
              am.put(ac,
                      new AbstractAction() {
                  public void actionPerformed(ActionEvent e) {
                      mim.doClick();
                  }
              }
              );
          }
          }

      jHtmlEditorPane.getKeymap().addActionForKeyStroke(
              KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,0),
              new AbstractAction() {
          public void actionPerformed(ActionEvent e) {
              if (jButtonPrev.isEnabled())
                  jButtonPrev.doClick();
          }
      }
      );


  }

  void goAbout() {
      try {
          InputStream is=openStream(new URL("http://globaldocs.zeevbelkin.com/portal/"), 2000);
          if (is==null)
              navigate(
                      "about:about_frame.html"
                      //getResourceUrl("globaldocs/res/about_frame.html")
                      );
          else {
              is.close();
              navigate(
                      "about:"
                      //getResourceUrl("globaldocs/res/about.html")
                      );
          }
      } catch (Throwable t) {}
  }

  void changeUpButtonState() {
      TheMutableTreeNode tn=null;
      TreePath tp=jTree.getSelectionPath();
      if (tp!=null) {
          tn=(TheMutableTreeNode)tp.getLastPathComponent();
          if (tn!=null) {
              jButtonUp.setEnabled(tn.getLevel()>1);
              return;
          }
      }
      jButtonUp.setEnabled(false);
  }

  static String TITLE="Globaldocs -- The Viewer";
  static String TITLE_PREFIX="Global Javadoc View -- ";
  static String DOCSET="#docset";
  static String ASSEMBLES="assembles";

  final static String externalBrowsers[][]={
      {"[default handler]","xdg-open"},
      {"Safari","/Applications/Safari.app"},
      {"Firefox","firefox"},
      {"Mozilla","mozilla"},
      {"Opera","opera"},
      {"Konqueror","konqueror"},
      {"Galeon","galeon"},
      {"Kazehakase","kazehakase"},
      {"Epiphany","epiphany"},
      {"Internet Explorer","explorer"},
      {"Internet Explorer","ie6"},
      {"Dillo","dillo"},

      {"KAte","kate"},

      {"Bluefish","bluefish"},
      {"Quanta+","quanta"},

      {"Vim","gvim"},
      {"Gedit","gedit"},
      {"KWrite","kwrite"},
      {"Open Office Writer","oowriter"},
      {"KWord","kword"},

      {"KGet","kget"},
      {"GWget","gwget"},
      {"WebDownload For X","d4x"}
      
  };

  static Map<String,String> externalBrowser=new LinkedHashMap<String,String>();

  static void openUrlWith(String browser,String url) {
      String path=externalBrowser.get(browser);
      try {
          if (browser.equals("Safari"))
              Runtime.getRuntime().exec( new String[] { "open" , "-a", "Safari", url }) ;
          else {
              if (browser.equals("Konqueror")&&url.startsWith("jar:file:")) {
                 url=url.replaceFirst("jar:file:","zip:")
                        .replaceFirst(".jar!/",".jar/")
                        .replaceFirst(".zip!/",".zip/")
                 ;
              }
              Runtime.getRuntime().exec(
                  new String[] {
                    path,url
                  }
              );
          }
      } catch (Throwable t) {}
  }

  static void vfyThatBrowsersExist() {
      String path=System.getenv("PATH");
      
      String pathes[]=null;
      
      if (path!=null) pathes=path.split(File.pathSeparator);
      
      Vector<String> appPaths=getExternalAppPathes();
      
      //        macos=os.startsWith("mac os");

      a:for (String decl[]:externalBrowsers)
          if (!externalBrowser.containsKey(decl[0])) {
            String app=mswin?(decl[1]+".exe"):decl[1];
            if (app.charAt(0)=='/') {
              if (new File( app ).exists()) externalBrowser.put(decl[0],app);
            } else {
               if (pathes!=null) for (String p:pathes) if (p.length()>0) {
                  if (p.charAt(p.length()-1)!=File.separatorChar) p+=File.separatorChar;
                  if (new File(p+app).exists()) {
                    externalBrowser.put(decl[0],p+app);
                    continue a;
                  }
                }
           
              if (mswin) for (String appPath:appPaths) {
                 if (appPath.endsWith("\\"+app)&&new File(appPath).exists()) {
                 //   System.out.println("!! "+appPath);
                    externalBrowser.put(decl[0],appPath);
                    continue a;
                 }
              }
            }
          }
  }

  static Hashtable<String,TheMutableTreeNode> url2node=new
          Hashtable<String,TheMutableTreeNode>();


  TheMutableTreeNode url2node(String url) {
      TheMutableTreeNode r=url==null?null:url2node.get(url);
      if (r==null&&url!=null)
          r=url2node.get(withoutRef(url));
      return r;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jSplitPane1 = new javax.swing.JSplitPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        treeScrollPane = new javax.swing.JScrollPane();
        jTree = new JTree(rootNode);
        jPanel2 = new javax.swing.JPanel();
        searchMaskJPanel1 = new globaldocs.SearchMaskJPanel();
        classesListScroller = new javax.swing.JScrollPane();
        classesList = new javax.swing.JList();
        jPanel_Bookmarks1 = new globaldocs.JPanel_Bookmarks();
        jScrollBrowserPane = new javax.swing.JScrollPane();
        jHtmlEditorPane = new javax.swing.JEditorPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel_tips = new javax.swing.JPanel();
        jLabel_Upgrade = new javax.swing.JLabel();
        jLabel_tip = new javax.swing.JLabel();
        jPanel_tools = new javax.swing.JPanel();
        jButtonPrev = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jButtonUp = new javax.swing.JButton();
        jButtonStop = new javax.swing.JButton();
        jButtonReload = new javax.swing.JButton();
        jButtonHome = new javax.swing.JButton();
        jButtonJPackage = new javax.swing.JButton();
        jButton_ZoomIn = new javax.swing.JButton();
        jButton_ZoomOut = new javax.swing.JButton();
        jButton_Docsets = new javax.swing.JButton();
        jButton__capture = new javax.swing.JButton();
        jButton_clearUrl = new javax.swing.JButton();
        jComboBox__url = new javax.swing.JComboBox();

        jPopupMenu1.setInvoker(jHtmlEditorPane);
        jPopupMenu1.setLabel("");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jSplitPane1.setDividerSize(6);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(750, 550));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setFocusable(false);
        jTree.setBackground(new java.awt.Color(238, 238, 238));
        jTree.setComponentPopupMenu(jPopupMenu1);
        jTree.setLargeModel(true);
        jTree.setOpaque(false);
        jTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeValueChanged(evt);
            }
        });
        jTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTreeMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTreeMousePressed(evt);
            }
        });

        treeScrollPane.setViewportView(jTree);

        jTabbedPane1.addTab("Tree", treeScrollPane);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setComponentPopupMenu(jPopupMenu1);
        jPanel2.add(searchMaskJPanel1, java.awt.BorderLayout.NORTH);

        classesListScroller.setComponentPopupMenu(jPopupMenu1);
        classesList.setBackground(new java.awt.Color(238, 238, 238));
        classesList.setComponentPopupMenu(jPopupMenu1);
        classesList.setFont(new java.awt.Font("Dialog", 0, 12));
        classesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        classesList.setOpaque(false);
        classesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                classesListValueChanged(evt);
            }
        });
        classesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                classesListMouseEntered(evt);
            }
        });

        classesListScroller.setViewportView(classesList);

        jPanel2.add(classesListScroller, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Index", jPanel2);

        jTabbedPane1.addTab("Bookmarks", jPanel_Bookmarks1);

        jTabbedPane1.setSelectedComponent(treeScrollPane);

        jSplitPane1.setLeftComponent(jTabbedPane1);

        jHtmlEditorPane.setComponentPopupMenu(jPopupMenu1);
        jHtmlEditorPane.setEditable(false);
        jHtmlEditorPane.setDragEnabled(true);
        jHtmlEditorPane.setMinimumSize(new java.awt.Dimension(100, 100));
        jHtmlEditorPane.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
                jHtmlEditorPaneHyperlinkUpdate(evt);
            }
        });
        jHtmlEditorPane.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jHtmlEditorPanePropertyChange(evt);
            }
        });

        jScrollBrowserPane.setViewportView(jHtmlEditorPane);

        jSplitPane1.setRightComponent(jScrollBrowserPane);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setMinimumSize(new java.awt.Dimension(293, 20));
        jPanel3.setPreferredSize(new java.awt.Dimension(293, 20));
        jPanel_tips.setLayout(new java.awt.BorderLayout());

        jPanel_tips.setMinimumSize(new java.awt.Dimension(0, 20));
        jPanel_tips.setPreferredSize(new java.awt.Dimension(260, 20));
        jLabel_Upgrade.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel_Upgrade.setForeground(new java.awt.Color(0, 204, 51));
        jLabel_Upgrade.setText(" upgrade is available");
        jLabel_Upgrade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_UpgradeMouseClicked(evt);
            }
        });

        jPanel_tips.add(jLabel_Upgrade, java.awt.BorderLayout.WEST);

        jLabel_tip.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabel_tip.setText(" tip: use the contextual menu");
        jLabel_tip.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 3));
        jLabel_tip.setMinimumSize(new java.awt.Dimension(0, 13));
        jPanel_tips.add(jLabel_tip, java.awt.BorderLayout.EAST);

        jPanel3.add(jPanel_tips, java.awt.BorderLayout.EAST);

        jPanel_tools.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 1));

        jPanel_tools.setMinimumSize(new java.awt.Dimension(10, 20));
        jPanel_tools.setPreferredSize(new java.awt.Dimension(10, 20));
        jPanel_tools.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel_toolsComponentResized(evt);
            }
        });

        jButtonPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/helpviewer_back.png")));
        jButtonPrev.setBorder(null);
        jButtonPrev.setBorderPainted(false);
        jButtonPrev.setContentAreaFilled(false);
        jButtonPrev.setEnabled(false);
        jButtonPrev.setFocusPainted(false);
        jButtonPrev.setPreferredSize(new java.awt.Dimension(20, 14));
        jButtonPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrevActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButtonPrev);

        jButtonNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/helpviewer_forward.png")));
        jButtonNext.setBorder(null);
        jButtonNext.setBorderPainted(false);
        jButtonNext.setContentAreaFilled(false);
        jButtonNext.setEnabled(false);
        jButtonNext.setFocusPainted(false);
        jButtonNext.setPreferredSize(new java.awt.Dimension(20, 14));
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButtonNext);

        jButtonUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/up.png")));
        jButtonUp.setBorder(null);
        jButtonUp.setBorderPainted(false);
        jButtonUp.setContentAreaFilled(false);
        jButtonUp.setEnabled(false);
        jButtonUp.setFocusPainted(false);
        jButtonUp.setPreferredSize(new java.awt.Dimension(20, 14));
        jButtonUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButtonUp);

        jButtonStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/globe.gif")));
        jButtonStop.setBorder(null);
        jButtonStop.setBorderPainted(false);
        jButtonStop.setContentAreaFilled(false);
        jButtonStop.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/unvisible_arrow.png")));
        jButtonStop.setEnabled(false);
        jButtonStop.setFocusPainted(false);
        jButtonStop.setPreferredSize(new java.awt.Dimension(20, 14));
        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButtonStop);

        jButtonReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/reload.png")));
        jButtonReload.setBorder(null);
        jButtonReload.setBorderPainted(false);
        jButtonReload.setContentAreaFilled(false);
        jButtonReload.setFocusPainted(false);
        jButtonReload.setPreferredSize(new java.awt.Dimension(20, 14));
        jButtonReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReloadActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButtonReload);

        jButtonHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/home.png")));
        jButtonHome.setBorder(null);
        jButtonHome.setBorderPainted(false);
        jButtonHome.setContentAreaFilled(false);
        jButtonHome.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/globeDisabled.gif")));
        jButtonHome.setEnabled(false);
        jButtonHome.setFocusPainted(false);
        jButtonHome.setPreferredSize(new java.awt.Dimension(20, 14));
        jButtonHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHomeActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButtonHome);

        jButtonJPackage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/jpackage.png")));
        jButtonJPackage.setBorder(null);
        jButtonJPackage.setBorderPainted(false);
        jButtonJPackage.setContentAreaFilled(false);
        jButtonJPackage.setEnabled(false);
        jButtonJPackage.setFocusPainted(false);
        jButtonJPackage.setPreferredSize(new java.awt.Dimension(20, 14));
        jButtonJPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJPackageActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButtonJPackage);

        jButton_ZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/zoom_in.png")));
        jButton_ZoomIn.setBorder(null);
        jButton_ZoomIn.setBorderPainted(false);
        jButton_ZoomIn.setContentAreaFilled(false);
        jButton_ZoomIn.setFocusPainted(false);
        jButton_ZoomIn.setPreferredSize(new java.awt.Dimension(20, 14));
        jButton_ZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ZoomInActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButton_ZoomIn);

        jButton_ZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/zoom_out.png")));
        jButton_ZoomOut.setBorder(null);
        jButton_ZoomOut.setBorderPainted(false);
        jButton_ZoomOut.setContentAreaFilled(false);
        jButton_ZoomOut.setFocusPainted(false);
        jButton_ZoomOut.setPreferredSize(new java.awt.Dimension(20, 14));
        jButton_ZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ZoomOutActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButton_ZoomOut);

        jButton_Docsets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/books.gif")));
        jButton_Docsets.setToolTipText("Edit The Javadoc Set List");
        jButton_Docsets.setBorder(null);
        jButton_Docsets.setBorderPainted(false);
        jButton_Docsets.setContentAreaFilled(false);
        jButton_Docsets.setFocusPainted(false);
        jButton_Docsets.setPreferredSize(new java.awt.Dimension(20, 14));
        jButton_Docsets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DocsetsActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButton_Docsets);

        jButton__capture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/plus.gif")));
        jButton__capture.setToolTipText("");
        jButton__capture.setBorder(null);
        jButton__capture.setBorderPainted(false);
        jButton__capture.setContentAreaFilled(false);
        jButton__capture.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/unvisible_arrow.png")));
        jButton__capture.setEnabled(false);
        jButton__capture.setFocusPainted(false);
        jButton__capture.setPreferredSize(new java.awt.Dimension(20, 14));
        jButton__capture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton__captureActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButton__capture);

        jButton_clearUrl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/globaldocs/res/blackcross.png")));
        jButton_clearUrl.setToolTipText("");
        jButton_clearUrl.setBorder(null);
        jButton_clearUrl.setBorderPainted(false);
        jButton_clearUrl.setContentAreaFilled(false);
        jButton_clearUrl.setFocusPainted(false);
        jButton_clearUrl.setPreferredSize(new java.awt.Dimension(20, 14));
        jButton_clearUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearUrlActionPerformed(evt);
            }
        });

        jPanel_tools.add(jButton_clearUrl);

        jComboBox__url.setEditable(true);
        jComboBox__url.setFont(new java.awt.Font("Dialog", 0, 12));
        jComboBox__url.setMaximumRowCount(30);
        jComboBox__url.setMinimumSize(new java.awt.Dimension(68, 15));
        jComboBox__url.setPreferredSize(new java.awt.Dimension(68, 15));
        jComboBox__url.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox__urlActionPerformed(evt);
            }
        });

        jPanel_tools.add(jComboBox__url);

        jPanel3.add(jPanel_tools, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

   private void classesListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classesListMouseEntered
// TODO add your handling code here:
     classesListScroller.grabFocus();
   }//GEN-LAST:event_classesListMouseEntered

   private void jTreeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeMouseEntered
// TODO add your handling code here:
      treeScrollPane.grabFocus();
   }//GEN-LAST:event_jTreeMouseEntered

   private void jButton__captureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton__captureActionPerformed
// TODO add your handling code here:
      A a=new A();
      String u=withoutSearch(jHtmlEditorPane.getPage().toString());
      u=u.substring(0,u.lastIndexOf('/')+1);
      a.setBaseUrl(u);
      if (DocsetListEditorPanel.TheDocsetEditor.doEditing(frame,a)) {
          
        jButton__capture.setEnabled(false);

        if (a.isJ2se_docset())
          for (A x:assembles)
            if (x.isJ2se_docset()) x.setEnabled(false);

        assembles.add(a);
        adjustTheTree();
        syncTheConfigurationWithTheTree();
        saveConfig(frame.configuration);
      }
   }//GEN-LAST:event_jButton__captureActionPerformed

   private void jButton_clearUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearUrlActionPerformed
// TODO add your handling code here:
      jComboBox__url.setSelectedItem("");
   }//GEN-LAST:event_jButton_clearUrlActionPerformed

   int initial_ComboBox__url_pos=0;

   private void jPanel_toolsComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel_toolsComponentResized
// TODO add your handling code here:
     Dimension psz=jPanel_tools.getSize();
     Rectangle r=jComboBox__url.getBounds();
     if (r.x>initial_ComboBox__url_pos)
       initial_ComboBox__url_pos=r.x;
     int right=initial_ComboBox__url_pos+r.width,minwidth=jComboBox__url.getMinimumSize().width;
     if (right!=psz.width) {
       r.width=psz.width-initial_ComboBox__url_pos;
       if (r.width<minwidth) r.width=minwidth;
       jComboBox__url.setPreferredSize(r.getSize());
       jComboBox__url.setBounds(r);
       jComboBox__url.updateUI();
     }
   }//GEN-LAST:event_jPanel_toolsComponentResized

   private void jComboBox__urlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox__urlActionPerformed
// TODO add your handling code here:
     Object sel=jComboBox__url.getSelectedItem();
     if (sel instanceof History.Item) {
       ((History.Item)sel).navigate();
     } else {
       String s=sel.toString().trim();
       if (s.length()>0) {
         if (s.indexOf(":/")<0&&!s.startsWith("about:")) {
           if (s.indexOf('.')>0&&s.indexOf(' ')<0&&Character.isLetter(s.charAt(s.length()-1)))
             s="http://"+s;
           else try {
             s="http://search.msn.com/results.aspx?q=\""+java.net.URLEncoder.encode(s,"Utf8")+"\"&FORM=QBRE";
           } catch (Throwable t) {}
         }
         navigate(s);
       }
     }
   }//GEN-LAST:event_jComboBox__urlActionPerformed


   private void jLabel_UpgradeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_UpgradeMouseClicked
// TODO add your handling code here:
      if (
        JOptionPane.showConfirmDialog(
          this,
          "Do You Want to Upgrade The Viewer Now?",
          "Upgrade",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE
        )==JOptionPane.YES_OPTION
      ) try {
        Runtime.getRuntime().exec(
          new String[]{
            java_home+"/bin/javaws",
            "http://globaldocs.zeevbelkin.com/globaldocs.jnlp"
          }
        );
        System.exit(0);
      } catch (Throwable t) {}
   }//GEN-LAST:event_jLabel_UpgradeMouseClicked

   private void jButton_DocsetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DocsetsActionPerformed
// TODO add your handling code here:
      DocsetListEditorPanel.invokeTheDialog();
   }//GEN-LAST:event_jButton_DocsetsActionPerformed

   private void jButtonReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReloadActionPerformed
// TODO add your handling code here
      /*
      String u=getPage(jHtmlEditorPane);
      try {
        //JViewport v=(JViewport)jHtmlEditorPane.getParent();
        //Point vp=v.getViewPosition();
        jHtmlEditorPane.setPage(emptyUrl);
        jHtmlEditorPane.setPage(mkUrl(u));
      } catch (Throwable t) {}
       */
      history.reload();
   }//GEN-LAST:event_jButtonReloadActionPerformed

    private void jButton_ZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ZoomOutActionPerformed
// TODO add your handling code here:
      if (font_size>MIN_FONT_SIZE) {
        font_size-=4;
        handleAllStyles();
      }
    }//GEN-LAST:event_jButton_ZoomOutActionPerformed

    private void jButton_ZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ZoomInActionPerformed
// TODO add your handling code here:
      font_size+=4;
       handleAllStyles();
    }//GEN-LAST:event_jButton_ZoomInActionPerformed

    private void classesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_classesListValueChanged
// TODO add your handling code here:


        int idx=evt.getFirstIndex();
        if (idx<0) return;

        Object selected=classesList.getModel().getElementAt(idx);

        jTabbedPane1.setSelectedComponent(treeScrollPane);
        classesList.clearSelection();

        navigate(rewrite(((TheMutableTreeNode)selected).getUrl()));
    }//GEN-LAST:event_classesListValueChanged

    boolean jPackageRequest=false;

    private void jButtonJPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJPackageActionPerformed
// TODO add your handling code here:
      TreePath tp=jTree.getSelectionPath();
      if (tp!=null) try {
        TheMutableTreeNode tn=(TheMutableTreeNode)tp.getLastPathComponent();
        URL home=tn.jPackageHome();
        if (home!=null) {
            jPackageRequest=true;
            navigate(home);
        }
      } catch (Throwable t) {}
    }//GEN-LAST:event_jButtonJPackageActionPerformed

    private void jTreeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeMousePressed
// TODO add your handling code here:
        if ((evt.getButton()==evt.BUTTON1)&&
                (jTree.getRowForLocation(evt.getX(), evt.getY()) != -1)) {
            TreePath
                    selPath = jTree.getPathForLocation(evt.getX(), evt.getY());
            if (selPath.equals(jTree.getSelectionPath())) {
                Object selected=selPath.getLastPathComponent();
                boolean noUrl=true;
                String u=null;
                if (selected!=null && selected instanceof TheMutableTreeNode) {
                    TheMutableTreeNode n=(TheMutableTreeNode)selected;
                    try {
                        u=n.getUrl();
                        if (u!=null) {
                            if (u.equals(getPage(jHtmlEditorPane))) //????
                                noUrl=false;
                        }
                    } catch (Throwable t) {
                        System.err.println(t);
                    }
                }
                if (noUrl&&u!=null&&!isNavigatingTo(u,jHtmlEditorPane)) {
                  navigate(u);
                  changeUpButtonState();
                }
            }
        }
    }//GEN-LAST:event_jTreeMousePressed

    private void jTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeValueChanged
// TODO add your handling code here:
        oldPath=evt.getOldLeadSelectionPath();

        if (evt.getNewLeadSelectionPath()==null) return;

        Object selected=evt.getPath().getLastPathComponent();
        boolean noUrl=true;
        if (selected!=null && selected instanceof TheMutableTreeNode) {
            TheMutableTreeNode n=(TheMutableTreeNode)selected;
            try {
                String u=n.getUrl();
                if (u!=null) {
                  if (url2node(getPage(history.currentPane()))!=n)
                    navigate(u);
                  noUrl=false;
                }
                /*
                URL u=n.getUrl();
                if (u!=null) {
                    if (!u.equals(jHtmlEditorPane.getPage()))
                        jHtmlEditorPane.setPage(u);
                    noUrl=false;
                }
                 */
            } catch (Throwable t) {
                System.err.println("== jTreeValueChanged ==");
                t.printStackTrace();
            }
        }
        if (noUrl) {
            navigate(emptyUrl);
        }
        changeUpButtonState();

    }//GEN-LAST:event_jTreeValueChanged

    private void jButtonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHomeActionPerformed
// TODO add your handling code here:
      TreePath tp=jTree.getSelectionPath();
      if (tp!=null) {
        TheMutableTreeNode tn=(TheMutableTreeNode)tp.getLastPathComponent();
        URL home=tn.getHome();
        if (home!=null) navigate(home);
      }
    }//GEN-LAST:event_jButtonHomeActionPerformed

    private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopActionPerformed
// TODO add your handling code here:
        if (prevUrl!=null) {
          JEditorPane ed=jHtmlEditorPane;
          if (prevUrlPane!=null) {
            JEditorPane e=prevUrlPane.get();
            if (e!=null) ed=e;
          }
          _stop(ed);
          //navigate(prevUrl,ed);
          jButtonStop.setEnabled(false);
          jButtonReload.setEnabled(true);
        }
    }//GEN-LAST:event_jButtonStopActionPerformed

    private void jButtonUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpActionPerformed
// TODO add your handling code here:
        TheMutableTreeNode tn=null;
        TreePath tp=jTree.getSelectionPath(),tp1=null,tp2=null;
        if (tp!=null) {
            tn=(TheMutableTreeNode)tp.getLastPathComponent();
            if (tn!=null&&tn.getLevel()>1) {
              jTree.collapsePath(tp);
              tp=tp.getParentPath();
              jTree.collapsePath(tp);
              if (ASSEMBLES.equals(tn.getUserObject())) {
                tp1=tp.getParentPath();
                if (tn.getLevel()>2) jTree.setSelectionPath(tp1);
                jTree.collapsePath(tp1);
              } else
              if (ASSEMBLES.equals(tn.getParentUserObject())) {
                tp1=tp.getParentPath();
                tp2=tp1.getParentPath();
                jTree.setSelectionPath(tp2);
                jTree.collapsePath(tp1);
                jTree.collapsePath(tp2);
              } else
                jTree.setSelectionPath(tp);
            }
        }
    }//GEN-LAST:event_jButtonUpActionPerformed

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
// TODO add your handling code here:
      history.forward();
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void jButtonPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrevActionPerformed
// TODO add your handling code here:
      history.back();
    }//GEN-LAST:event_jButtonPrevActionPerformed

    JScrollBar  viewer_hs=null,viewer_vs=null;

    // Very pervertive way to specify a user agent to the URL instance ...
/*
    static URLStreamHandler streamHandler=new URLStreamHandler() {
       protected URLConnection openConnection(URL u) throws IOException {
         URLConnection con=new URL(u.toString()).openConnection();
         if (con!=null) setUserAgentInfo(con);
         return con;
       }
    };
  */
    public static boolean isJpackageSetUrl(String u) {
      if (u!=null)
        return
          u.startsWith("file:/usr/share/javadoc/")||
          u.startsWith("file:///usr/share/javadoc/");
      return false;
    }

    public static boolean isLocalUrl(String u) {
       if (u!=null)
         return
           u.startsWith("file:")||
           u.startsWith("jar:file:")||
           u.startsWith("res:")||
           u.startsWith("about:")   
           ;
       return false;
    }

    public static URL mkUrl(String s) throws java.net.MalformedURLException {
      if (s==null) return null;
      return new URL(s.trim());
     // return new URL(null,s,streamHandler);
    }


    public String getPage(JEditorPane jHtmlEditorPane) {
       URL page=jHtmlEditorPane.getPage();
       return page==null?null:page.toString();
    }
    
    boolean canceled=false;
 
    class Navigator extends Thread {
	boolean cancelled=false;
        
      Cancelable slave=null;
        
      public synchronized void cancel() {
	  cancelled = true;
          if (slave!=null) slave.cancel();
          try {
            join();
          } catch (Throwable t) {}
      }
      
      class PageStream extends FilterInputStream {

	
	public PageStream(InputStream i) {
	    super(i);
	}

	/**
	 * Cancel the loading of the stream by throwing
	 * an IOException on the next request.
	 */

        protected synchronized void checkCanceled() throws IOException {
	    if (cancelled) {
		throw new IOException("page canceled");
	    }
            /*
            try {
              System.out.println("read request");
              Thread.sleep(10);
            } catch (Throwable t) {
            }*/
            
	}

        public int read() throws IOException {
	    checkCanceled();
            return super.read();
	}

        public long skip(long n) throws IOException {
	    checkCanceled();
	    return super.skip(n);
	}

        public int available() throws IOException {
	    checkCanceled();
	    return super.available();
	}

        public void reset() throws IOException {
	    checkCanceled();
	    super.reset();
	}

      }

    
        
        
        
        public Navigator(URL u,JEditorPane ed,String data,String method) {
         super("Navigator");
         requstedURL=u;
         this.ed=ed;
         this.data=data;

         this.method=(method==null)?"get":method.toLowerCase();
       }
       
       private URL requstedURL=null;
       private String data=null;
       private String method=null;
       private JEditorPane ed=null;
       private String effectiveUrl=null;
       
       boolean killable=true;
       
       public boolean isNavigatingTo(String u) {
         return isAlive()&&!isInterrupted()&&
               u.equals(effectiveUrl);
       }
        
       public void run() {
           boolean post="post".equalsIgnoreCase(method);
           
           effectiveUrl=requstedURL.toString();
           
           if (!(post||data==null)) effectiveUrl+="?"+data;
           
           URL oldUrl=ed.getPage();
           
           
           oldDoc=ed.getDocument();
           
           for (;;) try { // there will be several iterations if there are any redircats
               
              URL __url=mkUrl(effectiveUrl);
              try {
                __url=__url.toURI().normalize().toURL();
              } catch (Throwable tt) {}
              
              final URL url=__url;
              
/////////////////////////////////////////////////////////////////
              prevUrl=oldUrl;
              prevUrlPane=new java.lang.ref.WeakReference<JEditorPane>(ed);
              jButtonStop.setEnabled(true);
              jButtonReload.setEnabled(false);
/////////////////////////////////////////////////////////////////
           
              if (!(post||oldUrl==null)) {
                String s_old=oldUrl.toString();
                int i=s_old.indexOf('#');
                if (i>=0) s_old=s_old.substring(0,i);
                int j=effectiveUrl.indexOf('#');
                if (j>=0&&s_old.equals(effectiveUrl.substring(0,j))) {
                  killable=false;
                  scrollThenNotify(url,oldUrl);
                  /*
                  synchronized(this) {
                    notify();
                  }
                   */
                  return;
                }
              }
              /*
              synchronized(this) {
                notify();
              }
               */
               
               URLConnection uc=url.openConnection();
               uc.setConnectTimeout(URL_STREAM_TIMEOUT);
               
               if (uc instanceof HttpURLConnection) {
                 HttpURLConnection huc=(HttpURLConnection)uc;
                 huc.setFollowRedirects(false);
                 if (oldUrl!=null) {
                   String refr=oldUrl.toString();
                   int i=refr.indexOf('#'); if (i>=0) refr=refr.substring(0,i);
                   uc.setRequestProperty("Referer",refr);
                 }
                 if (post) {
                     huc.setRequestMethod("POST");
                     uc.setDoOutput(true);
                 }
               }               
               
               if (cancelled) return;
               
               try {
                 slave=(uc instanceof Cancelable)?(Cancelable)uc:null; 
                 uc.connect();
               } catch (Throwable t) {  
                 if (!cancelled) {
                   effectiveUrl="about:url_open_error";
                   continue;
                 }
               }
               if (cancelled) return;
               
               if (post) {
                   OutputStream os=uc.getOutputStream();
                   os.write(data.getBytes());
                   os.flush();
                   os.close();
               }
               if (cancelled) return;
               
               String context_type=uc.getContentType(),mime_type=context_type;
             //  System.out.println(url);
             //  System.out.println( mime_type);
               
               if (mime_type!=null) {
                int i=mime_type.indexOf(';');
                if (i>=0) mime_type=mime_type.substring(0,i);
               }
               
               if (nullify(mime_type)==null) mime_type="text/plain";
               if (nullify(context_type)==null) context_type="text/plain";
               
               
               ed.setContentType(context_type);
               ed.setEditorKit(ed.getEditorKitForContentType(mime_type));
               
               final Document doc=ed.getEditorKit().createDefaultDocument();
               
               if (cancelled) return;

               invokeAndWait(
                       new Runnable() {
                   public void run() {
                     synchronized(ed.getTreeLock()) {
                         ed.setDocument(doc);
                     }
                   }
               }
               );
              
               if (uc instanceof HttpURLConnection) {
                 HttpURLConnection huc=(HttpURLConnection)uc;
                   
                 int response = huc.getResponseCode();
                 if (response >= 300 && response <= 399) {
                   // System.out.println("redirect");
                   
                   // do redirect
                   String loc = uc.getHeaderField("Location");
                   if (loc.indexOf("://")>0) {
                      effectiveUrl=loc;
                   } else  {
                      effectiveUrl=new URL(url,loc).toString();
                   }
                   continue;
                 }
               }
               
               try {
                 InputStream is=new PageStream(uc.getInputStream());
                 synchronized(ed.getTreeLock()) {
                   ed.read(is,url);
                 }
                 is.close();
               } catch(Throwable t) {
                 effectiveUrl="about:url_open_error";
                 continue;
               }
               
               killable=false;

               scrollThenNotify(url,oldUrl);

               break;
           } catch (Throwable t) {
               t.printStackTrace(); 
           }
           
       }
       
       private void scrollThenNotify(final URL url,URL oldUrl) {
           final PropertyChangeEvent pce=new PropertyChangeEvent(ed,"page",oldUrl,url);
           
           invokeLater(
             new Runnable() {
                   public void run() {
                       String hash=url.getRef();
                       if (hash!=null) synchronized(ed.getTreeLock()) { ed.scrollToReference(hash); }
                       
                      
                       for (PropertyChangeListener pcl:ed.getPropertyChangeListeners()) try {
                           pcl.propertyChange(pce);
                       } catch (Throwable t) {}
                       
                   }
             }
           );
           oldDoc=null;
       }
       
       Document oldDoc=null;
       /*
       public void stopNavigation() {
         if (killable) {
           kill();
           if (oldDoc!=null) synchronized(ed.getTreeLock()) {
              ed.setDocument(oldDoc);
           }
         }
       }*/
       
       boolean isKilling=false;
     
       public void kill() {
         if (killable&&!isKilling)  {
          isKilling=true;
          cancel();
          
          if (oldDoc!=null) invokeAndWait(
            new Runnable() {
              public void run() {
                synchronized(ed.getTreeLock()) {
                 //   System.out.append("== olddoc ==");
                  ed.setDocument(oldDoc);
                }
              }
            }
          );
         }
       }
       
    };
    
    
    WeakHashMap<JEditorPane,Navigator> updaters=new WeakHashMap<JEditorPane,Navigator>();
    
    public void _stop(JEditorPane ed) {
      synchronized(updaters) {
        Navigator worker=updaters.get(ed);
        if (worker!=null) {
           System.out.append("== stop ==");
           worker.kill();
           System.out.append("== killed ==");
           //while (updaters.get(ed)!=null)
           //  try { Thread.sleep(10); } catch (Throwable t) {}
        }
      }
    }
    
    public void _navigate(URL u,JEditorPane ed,String data,String method) {
       if (!isNavigatingTo(u.toString(),ed)) synchronized(updaters) {
        {
              Navigator worker=updaters.get(ed);
              if (worker!=null) {
                 if (worker.isNavigatingTo(u.toString())) return;
                 worker.kill();           
              }
        }
        System.runFinalization();
        //while (updaters.get(ed)!=null)
        //     try { Thread.sleep(10); } catch (Throwable t) {}
        Navigator worker;
        updaters.put(ed,worker=new Navigator(u,ed,data,method));
        synchronized(worker) {
          worker.start();
          /*
                try {
                 worker.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
           */
         // System.out.println(u);
        }
      }
    }
    
    public  void _navigate(URL u,JEditorPane ed) {
       _navigate(u,ed,null,null);
    }
    
    public boolean isNavigatingTo(String u,JEditorPane ed) {
      synchronized(updaters) {
        Navigator worker=updaters.get(ed);
        if (worker!=null) return worker.isNavigatingTo(u);
      }      
      return false;
    }
    

    void navigate(URL u,JEditorPane ed) {
      try {
        history.saveThePosition();
        requestedUrl=null;
        requestedUrlPane=new java.lang.ref.WeakReference<JEditorPane>(ed);
        //ed.setPage(u);
        _navigate(u,ed);
      } catch (Throwable t) {
        System.err.println(t);
      }
    }


    void navigate(URL u) {
      try {
        history.saveThePosition();
        requestedUrl=null;
        requestedUrlPane=new java.lang.ref.WeakReference<JEditorPane>(jHtmlEditorPane);
        //jHtmlEditorPane.setPage(u);
         _navigate(u,jHtmlEditorPane);
      } catch (Throwable t) {
        System.err.println(t);
      }
    }
    
    void navigateLater(final String s) {
      invokeLater(
        new Runnable() {
          public void run() {
            navigate(s);
          }
        }
      );
    }

    void navigate(String  s) {
      try {
        history.saveThePosition();
        requestedUrl=null;
        requestedUrlPane=new java.lang.ref.WeakReference<JEditorPane>(jHtmlEditorPane);
        //jHtmlEditorPane.setPage(mkUrl(s));
        _navigate(mkUrl(s),jHtmlEditorPane);
      } catch (Throwable t) {
        //System.err.println(t);
        t.printStackTrace();
      }
    }

    void navigate(String  s,JEditorPane ed) {
      try {
        history.saveThePosition();
        requestedUrl=null;
        requestedUrlPane=new java.lang.ref.WeakReference<JEditorPane>(ed);
        //ed.setPage(mkUrl(s));
        _navigate(mkUrl(s),ed);
      } catch (Throwable t) {
        System.err.println(t);
      }
    }


    URL prevUrl=null;
    java.lang.ref.WeakReference<JEditorPane>
      prevUrlPane=null,
      requestedUrlPane=null;

    String requestedUrl=null;

    private void jHtmlEditorPanePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jHtmlEditorPanePropertyChange

      JEditorPane src=(JEditorPane)evt.getSource();

     
      if ("document".equals(evt.getPropertyName())
          &&(evt.getNewValue()!=null)
          &&src!=jHtmlEditorPane
      ) {
          Document olddoc=(Document)evt.getOldValue();
          URL u=(URL)olddoc.getProperty(Document.StreamDescriptionProperty);

          Document newdoc=(Document)evt.getNewValue();
          if (newdoc.getProperty(Document.StreamDescriptionProperty)!=null) {

              prevUrl=u;
              prevUrlPane=new java.lang.ref.WeakReference<JEditorPane>(src);
              jButtonStop.setEnabled(true);
              jButtonReload.setEnabled(false);
          }
//          lastSearchEnd=0;
      }

      if ("page".equals(evt.getPropertyName())) {
         //handleAllStyles(src,false);
         
        //  if (font_size!=DEFAULT_FONT_SIZE)
        //    handleAllStyles();
          // the page load is completed
          URL u=(URL)evt.getNewValue();
          TheMutableTreeNode tn=u==null?null:url2node(u.toString());

          if (tn==null) {
              jButtonHome.setEnabled(false);
              jButtonJPackage.setEnabled(false);
              Document doc=src.getDocument();
              String title=(String)doc.getProperty("title");
              setTitle(TITLE_PREFIX+(title==null?"":title));
              history.add(u,title,src);
              processTheFrames();
            //  jTree.clearSelection();
          } else {
              boolean homeIsEnabled=tn.hasHome(),
                      jpIsEnabled=tn.jPackageHome()!=null;
              jButtonHome.setEnabled(homeIsEnabled);
              if (homeIsEnabled)
                jButtonHome.setToolTipText(tn.getVersionedName()+" -- none API help pages");
              jButtonJPackage.setEnabled(jpIsEnabled);
              if (jpIsEnabled)
                jButtonJPackage.setToolTipText(tn.getVersionedName()+" -- JPackage info");

              setTitle(TITLE_PREFIX+tn.getTitle());
              history.add(u,tn.getTitle(),src);
              processTheFrames();

              syncTreeSelection(src);

          }
          
          if (src==jHtmlEditorPane) {
             boolean f=detectADocset();
             jButton__capture.setEnabled(f);
             jButton__capture.setToolTipText(f?"Capture This Docset":null);
          }

          handleMembers(src);

          if (jPackageRequest) {
            jPackageRequest=false;
            findThenMove("RPM Information for");
          }

          jButtonStop.setEnabled(false);
          jButtonReload.setEnabled(true);

      }
    }//GEN-LAST:event_jHtmlEditorPanePropertyChange

    void syncTreeSelection(JEditorPane ed) {
       String u=getPage(ed);
       if (u!=null) {
         TheMutableTreeNode tn=url2node(u);
         if (tn!=null) {

              TreePath selpath=jTree.getSelectionPath();

              TheMutableTreeNode sel=
                 (selpath==null)
                   ?null
                   :(TheMutableTreeNode)selpath.getLastPathComponent();

              if (tn!=sel) {
                TreePath tp=new TreePath(tn.getPath());
                jTree.setSelectionPath(tp);
                jTree.scrollPathToVisible(tp);
              }


         }
       }
    }

    static final int
      DEFAULT_FONT_SIZE=12,
      MIN_FONT_SIZE=6;

    int font_size=DEFAULT_FONT_SIZE;

    final static String ORIG_STYLE_SHEET_COPY="orig-style-sheet-copy";
    
    static class DocEx {
    
       private static  Method dhWriteLock=null,dhWriteUnlock=null;
       private static  void init() {
         if (dhWriteLock==null) try {
           dhWriteLock=AbstractDocument.class.getDeclaredMethod("writeLock");
           dhWriteUnlock=AbstractDocument.class.getDeclaredMethod("writeUnlock");
           dhWriteLock.setAccessible(true);
           dhWriteUnlock.setAccessible(true);
         } catch (Throwable t) {
            t.printStackTrace();
         }
       }
       
       static boolean writeLock(AbstractDocument doc) {
         try {
           init();
           dhWriteLock.invoke(doc);
           return true;
         } catch (InvocationTargetException t) {
           t.getTargetException().printStackTrace();
         } catch (Throwable t) {
           t.printStackTrace();
         }
         return false;
       }
       
       static void writeUnlock(AbstractDocument doc) {
         try {
           init();
           dhWriteUnlock.invoke(doc);
         } catch (Throwable t) {
           t.printStackTrace();
         }
       }
    };

    void handleAllStyles() {
       handleAllStyles(true);
    }

    void handleAllStyles(boolean recursive) {
       handleAllStyles(jHtmlEditorPane,recursive,null);
    }
    
    final static String GLOBALDOCS_FONT_SIZE="GLOBALDOCS_FONT_SIZE";
    
    void el_handleAllStyles(final JEditorPane ed,final boolean recursive,final Document doc) {
       //if (SwingUtilities.isEventDispatchThread())
       //  handleAllStyles(ed,recursive);
       //else
         invokeLater(
           new Runnable() {
             public void run() {
               handleAllStyles(ed,recursive,doc);
             }
           }
         );
    }
    
    void handleAllStyles(JEditorPane ed,boolean recursive,Document ddoc) {
       handleAllStyles(ed,recursive,true,ddoc);
    }

    void handleAllStyles(JEditorPane ed,boolean recursive,boolean doScale,Document doc) {
       jButton_ZoomOut.setEnabled(font_size>MIN_FONT_SIZE);
       int delta=font_size-DEFAULT_FONT_SIZE;
       
      // if (true) return;

       
          if (doc==null) doc=ed.getDocument();
          if (doc!=null&&doc instanceof HTMLDocument)
             /*zzzzsynchronized(ed.getTreeLock())*/ {
             
             HTMLDocument hdoc=(HTMLDocument)doc;
             
           //  DocEx.writeLock(hdoc);
             
             
             Integer doc_font_size=(Integer)hdoc.getProperty(GLOBALDOCS_FONT_SIZE);
             
           //  if (doc_font_size!=null&&doc_font_size.intValue()==font_size) return;
          //   hdoc.putProperty(GLOBALDOCS_FONT_SIZE,new Integer(font_size));
             
             if (ed==jHtmlEditorPane)  ed.setUI(null);
             

             boolean styleChanged=false;
             

             Map origSS=(Map)hdoc.getProperty(ORIG_STYLE_SHEET_COPY);

             if (origSS==null) {
                origSS=Collections.synchronizedMap(new WeakHashMap());
                hdoc.putProperty(ORIG_STYLE_SHEET_COPY,origSS);
             }
             
             {
                Enumeration rules = hdoc.getStyleNames();
                while (rules.hasMoreElements()) {
                   String name = (String) rules.nextElement();
                   Style rule = hdoc.getStyle(name);
                   
                   Integer origFS=(Integer)origSS.get(name);
                   
                   if (name.charAt(0)=='.') {
               //      System.out.println(name);
               //      System.out.println(rule);
                   }
                   
                   
                   if (origFS==null) {
                      //origFS=(Integer)rule.getAttribute(StyleConstants.FontSize);
                      //if (origFS==null) {
                      origFS=StyleConstants.getFontSize(rule);
                      //continue;
                      //}
                      origSS.put(name,origFS);
                   }
                   
                   
                  
                   if (origFS!=null) {
                      int fs=(origFS*font_size)/DEFAULT_FONT_SIZE;
                      if (fs==StyleConstants.getFontSize(rule)) continue;
                      
                      //if (rule.getAttribute(StyleConstants.FontSize)!=null)
                      //    rule.removeAttribute(StyleConstants.FontSize);
                      styleChanged=true;
                      
                    //  System.out.println("{++  "+rule);
                      StyleConstants.setFontSize(rule,fs);
                     // System.out.println(rule+" ++}");

                      
                      if (name.charAt(0)=='.') {
                  //      System.out.println(rule);
                      }
                   }
                }
                
             }
             
             /*
             String elsToSkip[]={
                "p-implied","html","frameset","script","meta",
                "comment","content","head","link","title","frame"
             };
             */
             if (/*doScale*/false) {
                
                ElementIterator it=new ElementIterator(hdoc);
                A:for (
                   AbstractDocument.AbstractElement el=(AbstractDocument.AbstractElement)it.first();
                   el!=null;
                   el=(AbstractDocument.AbstractElement)it.next()
                )  {
                   String name=el.getName();

                  // for (String s:elsToSkip) if (s.equals(name)) continue A;
                   
                     /*
                     Integer fos=(Integer)el.getAttribute(StyleConstants.FontSize);
                     if (fos!=null) {
                       Integer origFS=(Integer)origSS.get(el);
                       if (origFS==null) {
                         origFS=fos;
                         origSS.put(el,fos);
                       }
                       fs=(origFS*font_size)/DEFAULT_FONT_SIZE;
                       StyleConstants.setFontSize(el,fs);
                     }*/
                    System.out.println(name);
                }

               // scaleDocElements(hdoc,origSS);
             }
            

            if (recursive)  processTheFrames_walker(ed,
                new Visitor<JEditorPane>() {
                   public void visit(JEditorPane ep) {
                      handleAllStyles(ep,true,null);
                   }
                }
              );


              
              
            //  hdoc.getDocumentProperties().put(GLOBALDOCS_FONT_SIZE,new Integer(font_size));
           //   DocEx.writeUnlock(hdoc);
              
              
              if (ed==jHtmlEditorPane) synchronized(ed.getTreeLock()) {
                 //BasicEditorPaneUI ui=(BasicEditorPaneUI)ed.getUI();
                 ed.setUI((TextUI)BasicEditorPaneUI.createUI(ed));
                 processTheFrames();
                 //ed.updateUI();

              }
              
                            
              
              //if (ed==jHtmlEditorPane) SwingUtilities.updateComponentTreeUI(ed);
             } // treelock

    }


    void processTheFrames_walker(java.awt.Container cont,Visitor vis) {
       if (cont instanceof JScrollPane) {
          Component com=((JScrollPane)cont).getViewport().getView();
          if (com instanceof JEditorPane) {
             JEditorPane ep=(JEditorPane)com;
             vis.visit(ep);
             return;
          }
       }
       Component comps[]=cont.getComponents();
       if (comps!=null)
          for (Component comp:comps)
             if (comp instanceof java.awt.Container)
                processTheFrames_walker((java.awt.Container)comp,vis);
    }

    void processTheFrames() {
       Document doc=jHtmlEditorPane.getDocument();
       if (doc instanceof HTMLDocument) synchronized(jHtmlEditorPane.getTreeLock()) {
          processTheFrames_walker(jHtmlEditorPane,
            new Visitor<JEditorPane>() {
               public void visit(JEditorPane ep) {
                 ep.setComponentPopupMenu(jPopupMenu1);
               }
            }
          );
       }
    }

    void configureLinkMenu(JMenu miLink,final String u,boolean topframe) {
       miLink.removeAll();
       JMenuItem mi=miLink.add(u.toString());
       mi.setEnabled(false);
       miLink.addSeparator();
       mi=miLink.add("Copy URL");
       mi.setMnemonic(KeyEvent.VK_R);

       mi.addActionListener(
             new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             StringSelection ss=new StringSelection(u.toString());
             clipboard.setContents(ss,ss);
          }
       }
       );

       if (!topframe) {
         mi=miLink.add("open in the top frame");
         mi.addActionListener(
           new ActionListener() {
             public void actionPerformed(ActionEvent e) {
               navigate(u.toString());
             }
           }
         );
         mi.setMnemonic(KeyEvent.VK_F);
       }

       if (!(externalBrowser.isEmpty()||u.startsWith("about:"))) {

          for (final String browser:externalBrowser.keySet()) {
             mi=miLink.add("open with "+browser);

             mi.addActionListener(
                   new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                   openUrlWith(browser,u.toString());
                }
             }
             );

          }
       }

    }

    void configureMembersMenuItem(JMenu miMembers,final JEditorPane ed,final String href) {
       int i=href.lastIndexOf('#');
       String ref=href.substring(i+1);
       String label=ref.endsWith("()")?(ref.substring(0,ref.length()-2)+"( )"):ref;
       miMembers.add(label).addActionListener(
          new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             int i=href.lastIndexOf('#');
             String ref=href.substring(i+1);
             if (i==0) {
                ed.scrollToReference(ref);
             } else if (href.indexOf(':')>=0) {
                navigate(rewrite(href),ed);
             } else try {
                URL u=ed.getPage();
                String base=u.getPath(),pfx=href.substring(0,i);

                String bs=base.substring(0,base.lastIndexOf('/')+1);

                int j=bs.indexOf(':');

                boolean jar=j>=0;

                String proto=null;

                if (jar) {
                   proto=bs.substring(0,j+1);
                   bs=bs.substring(j+1);
                }


                String newpath=
                  (pfx.charAt(0)=='/')
                      ?pfx
                      :URI.create(bs).resolve(pfx).toString();


                if (jar) newpath=proto+newpath;

                if (newpath.equals(base))
                   ed.scrollToReference(ref);
                else {
                   // System.out.println(newpath+'#'+ref);
                   navigate(
                         rewrite(new URL(u.getProtocol(),u.getHost(),u.getPort(),newpath+'#'+ref)),
                         ed
                         );
                }
             } catch (Throwable t) {
                System.err.println(t);
             }
          }
           }
       );
    }

    static String refLiteral(String href) {
       int i=href.lastIndexOf('#');
       String ref=href.substring(i+1);
       i=ref.indexOf('(');
       if (i>0) return ref.substring(0,i);
       return ref;
    }

    void handleMembers(JEditorPane ed) {
       synchronized(miMembers.getTreeLock()) {
          miMembers.removeAll();

          Document doc=ed.getDocument();

          if (doc!=null && doc instanceof HTMLDocument) {

             LinkedHashSet<String> members=new LinkedHashSet<String>();
             HashSet<String> refs=new HashSet<String>();

             ElementIterator it=new ElementIterator(doc);
             Element el=it.first();
             while (el!=null) {
                AttributeSet aset=el.getAttributes();
                if (aset!=null&&aset.isDefined(HTML.Tag.A) ) {
                   /*
                  {Enumeration en=aset.getAttributeNames();
                   while (en.hasMoreElements()) {
                     Object key=en.nextElement();
                     System.out.println(key+" "+aset.getAttribute(key));
                    }
                   System.out.println("--------------------");
                  }*/

                   aset=(AttributeSet)aset.getAttribute(HTML.Tag.A);

                   String href=(String)aset.getAttribute(HTML.Attribute.HREF);

                   if (href!=null) {

                     int i=href.lastIndexOf('#');
                     if (i>=0) {

                        String val=null;
                        try {
                           val=doc.getText(el.getStartOffset(),el.getEndOffset()-el.getStartOffset());
                        } catch (BadLocationException ex) {}

                       String ref=href.substring(i+1);
                       if (refLiteral(href).equals(val)&&!(members.contains(href)||refs.contains(ref))) {
                         members.add(href); refs.add(ref);
                       }
                     }
                   }
                }
                el=it.next();
             }
             if (!members.isEmpty()) {
               LinkedHashMap<String,Vector<String>> nests=new LinkedHashMap<String,Vector<String>>();
               for (String x:members) {
                  String lit=refLiteral(x);
                  Vector<String> lst=nests.get(lit);
                  if (lst==null) nests.put(lit,lst=new Vector<String>());
                  lst.add(x);
                  //configureMembersMenuItem(miMembers,ed,x);
               }
               if (nests.size()<=MAX_MENU_ITEMS)
                 configureMembersMenuCluster(miMembers,ed,nests);
               else {
                 LinkedHashMap<String,   LinkedHashMap<String,Vector<String>> > firstLevel=
                    new LinkedHashMap<String,   LinkedHashMap<String,Vector<String>>  >();
                 for (String id:nests.keySet()) {
                   String mp=memberPrefix(id);
                   LinkedHashMap<String,Vector<String>> v=firstLevel.get(mp);
                   if (v==null) firstLevel.put(mp,v=new LinkedHashMap<String,Vector<String>>());
                   v.put(id,nests.get(id));
                 }
                 for (String key:firstLevel.keySet()) {
                    LinkedHashMap<String,Vector<String>> v=firstLevel.get(key);
                   if (v.size()==1)
                     configureMembersMenuCluster(miMembers,ed,v);
                   else {
                     JMenu m=new JMenu(key+" ...");
                     miMembers.add(m);
                     configureMembersMenuCluster(m,ed,v);
                   }
                 }
               }
             }
          }
          miMembers.setVisible(miMembers.getItemCount()>0);
       }
    }

    String memberPrefix(String s) {
      int i=findMemberBreak(s);
      return (i>0)?s.substring(0,i):s;
    }

    int findMemberBreak(String s) {
      int v=-1;
      if (s!=null&&s.length()>3) {
        if (Character.isLowerCase(s.charAt(0))) {
          for (int i=0;i<s.length();i++)
            if (!Character.isLowerCase(s.charAt(i))) return Math.min(i,5);
        }
        if (Character.isUpperCase(s.charAt(0))) {
          for (int i=0;i<s.length();i++)
            if (!Character.isUpperCase(s.charAt(i))) return Math.min(i,5);
        }
        //if (s.length()>=4) return 3;
        return 3;
      }
      return v;
    }

    final static int MAX_MENU_ITEMS=20;

    void configureMembersMenuCluster(JMenu miMembers,JEditorPane ed,LinkedHashMap<String,Vector<String>> nests) {
       HashMap<String,JMenu> menus=new HashMap<String,JMenu>();
       for (String id:nests.keySet()) {
          Vector<String> lst=nests.get(id);
          if (lst.size()==1)
             configureMembersMenuItem(miMembers,ed,lst.firstElement());
          else for (String sid:lst) {
             JMenu m=menus.get(id);
             if (m==null) {
                menus.put(id,m=new JMenu(id));
                miMembers.add(m);
             }
             configureMembersMenuItem(m,ed,sid);
          }
       }
    }

    boolean detectADocset() {
      Document doc=jHtmlEditorPane.getDocument();
      if (doc!=null && doc instanceof HTMLDocument) {
        ElementIterator it=new ElementIterator(doc);
        Element el=it.first();
        while (el!=null) {
          AttributeSet aset=el.getAttributes();
          if (aset!=null&&(aset.isDefined(HTML.Tag.FRAME)||
                        aset.getAttribute(StyleConstants.NameAttribute) == HTML.Tag.FRAME)) {

             String src=(String)aset.getAttribute(HTML.Attribute.SRC);
             if ("allclasses-frame.html".equals(src)) {
                // Yes, this is a docset, but is it a new one?
                String s=withoutSearch(jHtmlEditorPane.getPage().toString());
                s=s.substring(0,s.lastIndexOf('/')+1);
                for (A a:assembles) if (s.equals(a.urlBase)) return false;
                return true;
             }
          }
          el=it.next();
        }
      }
      return false;
    }

    class FramePropertyChangeListener implements PropertyChangeListener {
       public void propertyChange(PropertyChangeEvent evt) {
          if (requestedUrlPane!=null&&
                requestedUrlPane.get()==evt.getSource()
                ) {
             jHtmlEditorPanePropertyChange(evt);
             return;
          }


          if (requestedUrl!=null){
             if ("document".equals(evt.getPropertyName())&&(evt.getNewValue()!=null)) {
                Document newdoc=(Document)evt.getNewValue();
                URL u=(URL)newdoc.getProperty(Document.StreamDescriptionProperty);
                if (u!=null&&u.toString().equals(requestedUrl))
                   jHtmlEditorPanePropertyChange(evt);

                return;
             }
             if ("page".equals(evt.getPropertyName())) {
                URL u=(URL)evt.getNewValue();
                if (u!=null&&u.toString().equals(requestedUrl)) {
                   jHtmlEditorPanePropertyChange(evt);
                }
             }
          }
       }
    }

    FramePropertyChangeListener framePropertyChangeListener=new FramePropertyChangeListener();

    private void jHtmlEditorPaneHyperlinkSubmit(JEditorPane pane,FormSubmitEvent evt) {
       _navigate(
         evt.getURL(),
         pane,
         evt.getData(),
         (String)evt.getSourceElement().getAttributes().getAttribute(HTML.Attribute.METHOD)
       );

    }
    
    String linkUnderTheMouse=null;

    private void jHtmlEditorPaneHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {//GEN-FIRST:event_jHtmlEditorPaneHyperlinkUpdate

        if (evt instanceof FormSubmitEvent) return;

        if (evt.getEventType() == HyperlinkEvent.EventType.ENTERED) {
          //System.out.println(evt.getURL());
          synchronized(miLink.getTreeLock()) {

            boolean topframe=false;
            if (evt.getSourceElement() instanceof HTMLDocument.RunElement) {
              HTMLDocument.RunElement r=(HTMLDocument.RunElement)evt.getSourceElement();
              if (r.getDocument()==jHtmlEditorPane.getDocument())
                topframe=true;
            }

            URL u=evt.getURL();
            if (u!=null) {
              linkUnderTheMouse=rewrite(u.toString());
              configureLinkMenu(miLink,linkUnderTheMouse,topframe);
              miLink.setVisible(true);
            }
          }
        } else
        if (evt.getEventType() == HyperlinkEvent.EventType.EXITED) {
          miLink.setVisible(false);
          linkUnderTheMouse=null;
        } else
        if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane) evt.getSource();
            if (evt instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent
                   ev = (HTMLFrameHyperlinkEvent)evt;
                URL u=ev.getURL();
                requestedUrl=u==null?null:u.toString();
                if (u!=null) try {
                   if (ev instanceof FormSubmitEvent) {
/*                    
                     FormSubmitEvent fse=(FormSubmitEvent)ev;
                     ev=new FormSubmitEvent(
                       fse.getSource(),
                       fse.getEventType(),
                       u, 
                       fse.getSourceElement(),
                       fse.getTarget(),
                       fse.getMethod(),
                       fse.getData()
                     );
                      */
                   } else {
                     requestedUrl=rewrite(requestedUrl);
                     u=mkUrl(requestedUrl);
                     ev=new HTMLFrameHyperlinkEvent(
                       ev.getSource(),
                       ev.getEventType(),
                       u,
                       ev.getSourceElement(),
                       ev.getTarget()
                     );
                   }
                } catch (Throwable t) {}
                //navigate(rewrite(ev.getURL()),"_top".equals(ev.getTarget())?jHtmlEditorPane:pane);
                //navigate(rewrite(ev.getURL()),pane);
                HTMLDocument doc = (HTMLDocument)pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(ev);
            } else {
              String u=rewrite(evt.getURL());
              if (u!=null) navigate(u);
            }
        }

    }//GEN-LAST:event_jHtmlEditorPaneHyperlinkUpdate

    Clipboard clipboard = getToolkit().getSystemClipboard();


    static A getActiveJ2seDocset() {
       for (A a:assembles)
         if (a.isJ2se_docset()&&a.isEnabled()) return a;
       return null;
    }

    /*
    static int j2seDocsetCount() {
      int cnt=0;
      for (A a:assembles)
        if (a.isJ2se_docset()) {
         ++cnt;
        }
      return cnt;
    }
     */

    static final
       String J2SE1_6_PREFIX="http://download.java.net/jdk6/", //"http://java.sun.com/j2se/1.5.0/";
              DOCS_API="/docs/api/";


    static String getJ2sePrefix() {
       A a=getActiveJ2seDocset();
       if (a!=null) {
         String ub=a.urlBase;
         if (ub.endsWith(DOCS_API)) ub=ub.substring(0,ub.length()-(DOCS_API.length()-1));
         if (ub.charAt(ub.length()-1)!='/') ub=ub+'/';
         return ub;
       }
       return J2SE1_6_PREFIX;
    }

    static final Pattern J2SE14=Pattern.compile("/j2se/1\\.(1|2|3|4|5)(\\.\\d){0,1}/");
    static final Pattern J2SE14API=Pattern.compile("/j2se/1\\.(1|2|3|4|5)(\\.\\d){0,1}/api/");
    static final String  JAVA_LOC_REL="file:/usr/share/javadoc/java/";

    static String rewrite(URL u) {
       return u==null?null:rewrite(u.toString());
    }

    static String rewrite(String s) {

      if (s==null) return null;
      try {
          String j2se_prefix=getJ2sePrefix();
          if (s.startsWith(JAVA_LOC_REL))
             return
               j2se_prefix+"docs/api/"+s.substring(JAVA_LOC_REL.length());
          Matcher m=J2SE14API.matcher(s);
          if (m.find()) {
             s=j2se_prefix+"docs/api/"+s.substring(m.end());
          } else {
              m=J2SE14.matcher(s);
              if (m.find()) {
                  s=j2se_prefix+s.substring(m.end());
              }
          }
      } catch (Throwable t) {}
      if (isLocalUrl(s)) {
        int i=s.lastIndexOf(".html?");
        if (i>0) s=s.substring(0,i+5);
      }
      return s;
    }

    static boolean isJ2seBase(String s,boolean testremote) {
      if (s!=null) {
        if (s.charAt(s.length()-1)!='/') s=s+'/';
        if (s.endsWith(DOCS_API)) {
          if (s.startsWith("http://download.java.net/jdk6/")) return true;
          Matcher m=J2SE14API.matcher(s);
          if (m.find()) return true;
          m=J2SE14.matcher(s);
          if (m.find()) return true;
          if (testremote||isLocalUrl(s)) try {
            if (checkUrl(new URL(s+"java/lang/Object.html"))) return true;
          } catch (Throwable t) {}
          }
      }
      return false;
    }


    URL emptyUrl=getResourceUrl("globaldocs/res/empty.html");

    final static Font
          smallFont=new Font("Sans Serif",Font.PLAIN, 10),
          smallBoldFont=smallFont.deriveFont(Font.BOLD);

    /*
    class TheURLStreamHandlerFactory implements URLStreamHandlerFactory {
       public URLStreamHandler createURLStreamHandler(String protocol) {
          return null;
       }
    }*/
    
    
    static WeakHashMap<SSLSession,String> sslSessions=new WeakHashMap<SSLSession,String>();
    
    static String getCipherSuite(URL u) {
      if (u!=null) {
         int port=u.getPort();
         if (port<0) port=443;
         String host=u.getHost();
         synchronized(sslSessions) {
            for (SSLSession ses:sslSessions.keySet())
               if (ses.getPeerPort()==port&&host.equals(sslSessions.get(ses)))
                  return ses.getCipherSuite();
         }
      }
      return null;
    }
    
    
    
   static void checkLicense() {
      boolean shouldCheck=true;
      Properties p=new Properties();
      if (licFile.exists()) {
        try {
           FileInputStream fis=new FileInputStream(licFile);
           try {
             p.load(fis);
             String v=p.getProperty("version");
             if (v!=null&&!verIsGreater(VERSION,v)) shouldCheck=false;
           } finally {
             fis.close();
           }
        } catch  (Throwable t) {}
      }
      if (shouldCheck) {
        LicDialog d=new LicDialog();
        d.setVisible(true);
        if (!d.isAccepted()) System.exit(200); // the user has not accepted the license
        p.setProperty("version",VERSION);
        try {
          FileOutputStream fos=new FileOutputStream(licFile);
          try {
            p.store(fos,null);
          } finally {
            fos.close(); 
          }  
        } catch (Throwable t) {}  
      }
   }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        System.setProperty("java.protocol.handler.pkgs","globaldocs.protocols");

        checkLicense();

        respawnIfCanAndNeed(args);

        CookieHandler.setDefault(new TheCookieHandler());



        try {
           SSLContext sslContext=SSLContext.getInstance("SSL");
           sslContext.init(
              null,
              new X509TrustManager[] {
                new X509TrustManager() {
                   public void checkClientTrusted(X509Certificate[]
                       chain, String authType) {
                   }
                   public void checkServerTrusted(X509Certificate[]
                       chain, String authType) {
                   }
                   public X509Certificate[] getAcceptedIssuers() {
                      return null;
                   }
                }
              },
              null
           );
           HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (Throwable t) {
           t.printStackTrace();
        }

        HttpsURLConnection.setDefaultHostnameVerifier(
          new HostnameVerifier() {
           public boolean verify(String hostname, SSLSession session) {
             synchronized(sslSessions) {
               if (!sslSessions.containsKey(session)) sslSessions.put(session,hostname);
             }
      //       System.out.println(hostname+":"+session.getPeerPort()+" : "+session.getCipherSuite());
             return true;
           }
          }
        );

        setUserAgentProperty();
        Options_Dialog.setConnectionOptions();


        System.setProperty("swing.aatext", "true");
        System.setProperty("apple.awt.textantialiasing","on");
        System.setProperty("apple.awt.antialiasing","on");
        UIManager.put("TabbedPane.font",smallFont);
        UIManager.put("TitledBorder.font",smallFont);

        UIManager.put("Button.font",smallFont);
        UIManager.put("Label.font",smallFont);


        //UIManager.put("TableHeader.font",smallBoldFont);
      //  UIManager.put("Tree.backgroundNonSelectionColor", Color.RED);
        URLConnection.setDefaultAllowUserInteraction(true);

    //    System.out.println(getRunningJar());
        
        boolean doOfferUpdate=needToInstall();
        
        if (doOfferUpdate)
            if (
                JOptionPane.showConfirmDialog(
                  null,
                  "Do You Want to Integrate Recent Version of The Viewer to the Desktop?",
                  "Installation",
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE
                )==JOptionPane.YES_OPTION
            )  installGlobaldocs();
        
        configuration=loadConfiguration(doOfferUpdate);
 
        ////////////////////////////////////////////////
        MainFrame.args=args;
        
        
        frame=new MainFrame();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              frame.setVisible(true);
            }
        });

    }

    class History  {
        Point requestedPos=null;
        public class Item {
            String url;
            String title;
            int  font_size=DEFAULT_FONT_SIZE;
            Point pos=new Point(0,0);
            java.lang.ref.WeakReference<JEditorPane> pane;

            Item(String u,String t,int fsz,JEditorPane pane) {
                url=u; title=t; font_size=fsz;
                this.pane=new java.lang.ref.WeakReference<JEditorPane>(pane);
            }
            public void navigate() {
               System.gc();
               JEditorPane ed=pane.get();
               if (ed==null)
                 MainFrame.this.navigate(url);
               else
                 MainFrame.this.navigate(url,ed);
            }
            public void saveThePosition() {
              JEditorPane ed=pane.get();

              font_size=MainFrame.this.font_size;

              if (ed!=null) {
                JViewport vp=(JViewport)ed.getParent();
                JScrollPane sp=(JScrollPane)vp.getParent();
                JScrollBar viewer_hs=sp.getHorizontalScrollBar(),
                           viewer_vs=sp.getVerticalScrollBar();

                pos.x=viewer_hs.isVisible()?viewer_hs.getValue():0;
                pos.y=viewer_vs.isVisible()?viewer_vs.getValue():0;

              } else {
                pos.x=viewer_hs.isVisible()?viewer_hs.getValue():0;
                pos.y=viewer_vs.isVisible()?viewer_vs.getValue():0;
              }
            }
            public String getTitle() {
              if (title!=null&&title.length()>0) return title;
              return url;
            }
            public String toString() {
              if (emptyUrl.toString().equals(url)) return "";
              return url;
            }
        };

        private int position=0,toSkip=0;

        private Vector<Item> history=new Vector<Item>();

        public void back() {
          synchronized(history) {
            if (position>1) {
                saveThePosition();
                Item it=history.elementAt(--position-1);
                requestedPos=(Point)it.pos.clone();
                font_size=it.font_size;
                it.navigate();
                ++toSkip;
            }
          }
        }

        public void forward() {
          synchronized(history) {
            if (position<history.size()) {
                saveThePosition();
                Item it=history.elementAt(position++);
                requestedPos=(Point)it.pos.clone();
                font_size=it.font_size;
                it.navigate();
                ++toSkip;
            }
          }
        }
        
        public void reload() {
          synchronized(history) {

               saveThePosition();
               Item it=history.elementAt(position-1);
               requestedPos=(Point)it.pos.clone();
               try {
                  JEditorPane pane=it.pane.get();
                  if (pane==null) pane=jHtmlEditorPane;
                  pane.setPage(emptyUrl);
                  ++toSkip;
               } catch (IOException ex) {
                  //ex.printStackTrace();
               }
               it.navigate();
               ++toSkip;
            }
          
        }
        
        public Item currentPosition() {
          return history.elementAt(position-1);
        }

        public JEditorPane currentPane() {
          synchronized(history) {
            if (!history.isEmpty()) {
              JEditorPane rv=history.elementAt(position-1).pane.get();
              if (rv!=null) return rv;
            }
          }
          return jHtmlEditorPane;
        }

        public void saveThePosition() {
          synchronized(history) {
            if (!history.isEmpty())
              history.elementAt(position-1).saveThePosition();
          }
        }

        public synchronized void add(URL url,String title,JEditorPane pane) {
            if (toSkip>0) --toSkip;
            else synchronized(history) {

                if (history.size()>position&&url.equals(history.elementAt(position).url)) {
                  ++position;
                } else {
                  if (history.size()>position) history.setSize(position);
                  if (position>0&&emptyUrl.equals(history.lastElement().url)) {
                    --position; history.setSize(position);
                  }
                  history.add(new Item(url.toString(),title,font_size,pane)); ++position;
                }
            }

            DefaultComboBoxModel model=new DefaultComboBoxModel();
            for (int i=history.size()-1;i>=0;i--) {
              Item ii=history.get(i);
              if (!(ii.toString().length()==0||ii.url.equals(emptyUrl))) model.addElement(ii);
            }
            model.setSelectedItem(history.get(position-1));
            jComboBox__url.setModel(model);

            boolean issec=isSecure(pane);
            
            jComboBox__url
              .getEditor()
              .getEditorComponent()
              .setBackground(issec?secureColor:Color.WHITE);

            if (requestedPos!=null) {
                JViewport vp=(JViewport)pane.getParent();
                JScrollPane sp=(JScrollPane)vp.getParent();
                JScrollBar viewer_hs=sp.getHorizontalScrollBar(),
                           viewer_vs=sp.getVerticalScrollBar();

               viewer_hs.setValue(requestedPos.x);
               viewer_vs.setValue(requestedPos.y);
               requestedPos=null;
            }
            changeButtonState();
            synchronized(miPage.getTreeLock()) {
              configureLinkMenu(miPage,rewrite(url),jHtmlEditorPane==pane);
              
              if (issec) {
                 JMenuItem mi=new JMenuItem("CipherSuite: "+getCipherSuite(url));
                 mi.setEnabled(false);
                 miPage.insert(mi,1);
              }
            }
        }

        private void changeButtonState() {
            synchronized(history) {
                jButtonNext.setEnabled(
                        position<history.size()&&
                        !emptyUrl.equals(history.elementAt(position).url)
                        );
                jButtonPrev.setEnabled(position>1);
                synchronized(miNavigate.getTreeLock()) {
                    miNavigate.removeAll();

                    Set<String> urlset=new HashSet<String>();
                    for (int ix=history.size();ix>0;ix--) {
                        final Item it=history.elementAt(ix-1);
                        final int ixx=ix;
                        String wr=withoutRef(it.url);
                        if (it.url.equals(emptyUrl)||urlset.contains(wr))
                          continue;
                        urlset.add(wr);
                        JMenuItem mi=miNavigate.add(it.getTitle());
                        mi.setIcon((ixx==position)?icRightArrow:icNull);
                        mi.addActionListener(
                                new ActionListener() {
                            public synchronized void actionPerformed(ActionEvent e) {
                                saveThePosition();
                                position=ixx;
                                requestedPos=(Point)it.pos.clone();
                                it.navigate();
                                ++toSkip;
                            }
                        }
                        );
                    }
                }
            }
        }
    };
    
    static Color secureColor=new Color(0xff,0xff,0xdc);
    
    static boolean isSecure(JEditorPane pane) {
       if (pane!=null) {
         URL u=pane.getPage();
         if (u!=null) return u.toString().startsWith("https:");
       }
       return false;
    }

    History history=new History();
    
    public History.Item currentPosition() {
      return history.currentPosition();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList classesList;
    private javax.swing.JScrollPane classesListScroller;
    private javax.swing.JButton jButtonHome;
    private javax.swing.JButton jButtonJPackage;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPrev;
    private javax.swing.JButton jButtonReload;
    private javax.swing.JButton jButtonStop;
    private javax.swing.JButton jButtonUp;
    private javax.swing.JButton jButton_Docsets;
    private javax.swing.JButton jButton_ZoomIn;
    private javax.swing.JButton jButton_ZoomOut;
    private javax.swing.JButton jButton__capture;
    private javax.swing.JButton jButton_clearUrl;
    private javax.swing.JComboBox jComboBox__url;
    private javax.swing.JEditorPane jHtmlEditorPane;
    private javax.swing.JLabel jLabel_Upgrade;
    private javax.swing.JLabel jLabel_tip;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private globaldocs.JPanel_Bookmarks jPanel_Bookmarks1;
    private javax.swing.JPanel jPanel_tips;
    private javax.swing.JPanel jPanel_tools;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollBrowserPane;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTree jTree;
    private globaldocs.SearchMaskJPanel searchMaskJPanel1;
    private javax.swing.JScrollPane treeScrollPane;
    // End of variables declaration//GEN-END:variables

}
