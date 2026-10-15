/*
 * JPanel_Bookmarks.java
 *
 * Created on July 13, 2006, 7:02 PM
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

import java.io.*;
import java.nio.channels.*;
import static globaldocs.Utils.*;
import globaldocs.io.ChannelOutputStream;
import java.util.Vector;
import javax.swing.tree.*;
import java.util.Enumeration;
import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 *
 * @author  zeev
 */
public class JPanel_Bookmarks extends javax.swing.JPanel {
    
    
    static File bookmarksFile=new File(configDirectory,"bookmarks.xml");
    
    static BookmarkFolder bookmarks=null;
    
    public static abstract class BookmarkBase implements TreeNode {
        
        public boolean isDiscendantOf(BookmarkFolder bf) {
          if (this.equals(bf)) return true;
          for (BookmarkFolder p=getParent();p!=null;p=p.getParent())
            if (p.equals(bf)) return true;
          return false;
        }
        
        public int count() {
          if (isLeaf()) return 1;
          Enumeration<BookmarkBase> en=children();
          int c=1;
          while (en.hasMoreElements()) c+=en.nextElement().count();
          return c;
        }
        
        public int getIndex() {
          BookmarkFolder parent=getParent();
          if (parent!=null) return parent._getIndex(this);
          return -1;
        }
        
        
        abstract public void walk(Visitor<Bookmark> v);
        
        boolean visible=true;
        
        public void setVisible(boolean x) { visible=x; }
        
        public boolean isVisible() { return visible; }
        
        abstract public void encode(org.w3c.dom.Element root,org.w3c.dom.Document doc);
        
        /**
         * Holds value of property title.
         */
        private String title=null;

        /**
         * Getter for property title.
         * @return Value of property title.
         */
        public String getTitle() {
            return this.title;
        }
        
        public String toString() {
            return getTitle();
        }

        /**
         * Setter for property title.
         * @param title New value of property title.
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Holds value of property parent.
         */
        private BookmarkFolder parent=null;

        /**
         * Getter for property parent.
         * @return Value of property parent.
         */
        public BookmarkFolder getParent() {
            return this.parent;
        }

        /**
         * Setter for property parent.
         * @param parent New value of property parent.
         */
        public void setParent(BookmarkFolder parent) {
            this.parent = parent;
        }
    };
    
    
    
    private class Context {
        private Set<BookmarkFolder> bmks=getAllExpanded();
        private TreePath sel=jTree1.getSelectionPath();
        
        public void restore() {
            expandThem(bmks);
            if (sel!=null) {
                BookmarkBase b=(BookmarkBase)sel.getLastPathComponent();
                if (!usesVisibility||b.isVisible()) {
                    jTree1.setSelectionPath(sel);
                    jTree1.scrollPathToVisible(sel);
                }
            }
        }
        
        public void update() {
            for (int i=0;i<jTree1.getRowCount();i++) {
                BookmarkBase bb=(BookmarkBase)jTree1.getPathForRow(i).getLastPathComponent();
                if (jTree1.isExpanded(i))
                  bmks.add((BookmarkFolder)bb);
                else if (bb instanceof BookmarkFolder)
                  bmks.remove(bb);
            }
        }
        
        private Set<BookmarkFolder> getAllExpanded() {
            Set<BookmarkFolder> rv=new HashSet<BookmarkFolder>();
            for (int i=0;i<jTree1.getRowCount();i++)
                if (jTree1.isExpanded(i)) {
                rv.add((BookmarkFolder)jTree1.getPathForRow(i).getLastPathComponent());
                }
            return rv;
        }
        
        
        private void expandThem(Set<BookmarkFolder> s) {
            for (BookmarkFolder bf:s) if (!usesVisibility||bf.isVisible()) try {
                jTree1.expandPath(new TreePath(mod.getPathToRoot(bf)));
            } catch (Throwable t) {}
        }
        
    }
       
    Context savedContext=null;
    private void saveContext() {
        savedContext=new Context();
    }
    
    private void restoreContext() {
      savedContext.restore(); //savedContext=null;
    }
    
    
    
    private boolean usesVisibility=false;
    
    private void updateVisibility(boolean v) {
      if (v!=usesVisibility||v) {
        if (usesVisibility) savedContext.update(); else saveContext();
        usesVisibility=v;
        mod.reload(bookmarks);
        restoreContext();
        if (!v) savedContext=null;
      }
    }
    
    public static class Bookmark extends BookmarkBase {
        
        public void setVisible(boolean x) {
          boolean old=isVisible();
          if (old!=x) {
            super.setVisible(x);
            getParent().recalcVisibility(this);
          }
        }
        
        public void walk(Visitor<Bookmark> v) {
          v.visit(this);
        }
        
        public void encode(org.w3c.dom.Element root,org.w3c.dom.Document doc) {
          org.w3c.dom.Element link=doc.createElement("link");
          link.setAttribute("href",getHref());
          link.setAttribute("title",toString());
          root.appendChild(link);
        }
        
        public boolean isLeaf() { return true; }
        public int getChildCount() { return 0; }
        public TreeNode getChildAt(int childIndex) { return null; }
        public int getIndex(TreeNode node) { return -1; }
        public boolean getAllowsChildren() { return false; }
        public Enumeration children() {  return null; }
        /**
         * Holds value of property href.
         */
        private String href=null;

        /**
         * Getter for property href.
         * @return Value of property href.
         */
        public String getHref() {
            return this.href;
        }

        /**
         * Setter for property href.
         * @param href New value of property href.
         */
        public void setHref(String href) {
            this.href = href;
        }
        
    }

  
    class BookmarkFolder extends BookmarkBase {
        
        public void walk(Visitor<Bookmark> v) {
          for (BookmarkBase bb:bmks) bb.walk(v);
        }
        
        BitSet visSet=new BitSet();
        
        public boolean isVisible() { 
          return !visSet.isEmpty(); 
        }
        
        private void recalcParentVisibility() {
          BookmarkFolder p=getParent();
          //if (p!=null) p.recalcVisibility();
          if (p!=null) p.recalcVisibility(this);
        }

        public void recalcVisibility(BookmarkBase el) {
          boolean oldvis=isVisible();
          int i=bmks.indexOf(el);
          if (el.isVisible())
            visSet.set(i);
          else
            visSet.clear(i);
          if (oldvis!=isVisible()) recalcParentVisibility();
        }
        
        
        public void encode(org.w3c.dom.Element root,org.w3c.dom.Document doc) {
          org.w3c.dom.Element folder=doc.createElement("folder");
          folder.setAttribute("title",toString());
          root.appendChild(folder);
          for (BookmarkBase b:bmks) b.encode(folder,doc);
        }
        
        
       public boolean isLeaf() { return false; }
       public boolean getAllowsChildren() { return true; }
       
       public int getChildCount() {
          return
            usesVisibility?visSet.cardinality():bmks.size(); 
       }
       
       public boolean isEmpty() { 
          return
            usesVisibility?visSet.isEmpty():bmks.isEmpty(); 
       }
       
       private int translateChildIndex(int childIndex) {
         if (childIndex<visSet.cardinality()) {
           for (int j=visSet.nextSetBit(0),ix=0;j>=0;j=visSet.nextSetBit(j+1))
             if (ix++==childIndex) return j;
         }
         return -1;
       }
       
       public BookmarkBase getChildAt(int childIndex) {
         return bmks.elementAt(
           usesVisibility?translateChildIndex(childIndex):childIndex
          );
       }
       
       public int _getIndex(TreeNode node) {
         return bmks.indexOf(node);
       }
       
       public int getIndex(TreeNode node) { 
         int ix=bmks.indexOf(node);
         if (!usesVisibility) return ix;
         if (ix<0) return -1;
         return visSet.get(0,ix+1).cardinality()-1;
       }
       public Enumeration<BookmarkBase> children() {
         return new Enumeration<BookmarkBase>() {
            int idx=0,prevVis=-1;
             
            public boolean hasMoreElements() { 
               return usesVisibility?(idx<visSet.cardinality()):(idx<bmks.size()-1);
            }

            public BookmarkBase nextElement() {
              if (hasMoreElements()) {
                if (!usesVisibility) return bmks.elementAt(idx++);
                ++idx;
                return bmks.elementAt(prevVis=visSet.nextSetBit(++prevVis));
              }
              return null;
            }

         };
       }
       
       protected Vector<BookmarkBase> bmks=new Vector<BookmarkBase>();
       
       public void add(BookmarkBase bb) { 
         bb.setParent(this);
         bmks.add(bb);
         recalcVisibility(bb);
       }
       
       public void insertElementAt(BookmarkBase bb,int ix) {
         bb.setParent(this);
         bmks.insertElementAt(bb,ix);
         recalcVisibility();
       }
       
       public void remove(BookmarkBase bb) {
         bmks.remove(bb);
         recalcVisibility();
       }
        
       public void recalcVisibility() {
         boolean old=isVisible();
         visSet.clear();
         for (int i=0;i<bmks.size();i++) if (bmks.get(i).isVisible()) visSet.set(i);
         if (old!=isVisible()) recalcParentVisibility();
       }
        
    }
    
    private static org.w3c.dom.Document encodeBookmarkTree() {
      org.w3c.dom.Document doc=null;
      try {
        DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
        doc=builder.newDocument();
        org.w3c.dom.Element root=doc.createElement("root");
        doc.appendChild(root);
        bookmarks.encode(root,doc);
      } catch (Throwable t) {
        t.printStackTrace();
      }
      return doc;
    }
    
    public static void saveBookmarkTree() {
       try {
         RandomAccessFile raf=new RandomAccessFile(bookmarksFile,"rws");
         FileChannel fc=raf.getChannel();
         FileLock fl=fc.lock();
         try {
           writeXML(encodeBookmarkTree(),new ChannelOutputStream(fc));
           fc.truncate(fc.position());
           fc.force(true);
         } finally {
           fl.release();
         }
      } catch (IOException ex) {
         ex.printStackTrace();
      }
    }
    
    private BookmarkFolder buildBookmarkTree(org.w3c.dom.Document doc) {
      BookmarkFolder root=new BookmarkFolder() {
        public void encode(org.w3c.dom.Element root,org.w3c.dom.Document doc) {
          for (BookmarkBase b:bmks) b.encode(root,doc);
        }
      };
      buildBookmarkTree(root,doc.getDocumentElement());
      return root;
    }
    
    private  void buildBookmarkTree(BookmarkFolder root_folder,org.w3c.dom.Element root_element) {
      org.w3c.dom.NodeList root_nl=root_element.getChildNodes();
      for (int i=0;i<root_nl.getLength();i++) {
        org.w3c.dom.Node n=root_nl.item(i);
        if (!(n instanceof org.w3c.dom.Element)) continue;
        org.w3c.dom.Element el=(org.w3c.dom.Element)n;
        String tag=el.getTagName(),title=el.getAttribute("title");
        if ("folder".equals(tag)) {
           BookmarkFolder f=new BookmarkFolder();
           f.setParent(root_folder);
           f.setTitle(title);
           root_folder.add(f);
           buildBookmarkTree(f,el);
        } else
        if ("link".equals(tag)) {
          Bookmark b=new Bookmark();
          b.setParent(root_folder);
          b.setTitle(title);
          b.setHref(el.getAttribute("href"));
          root_folder.add(b);
        }
      }
    }
    
    void loadBookmarks() {
      try {
         RandomAccessFile raf=new RandomAccessFile(bookmarksFile,"rws");
         FileChannel fc=raf.getChannel();
         FileLock fl=fc.lock();
         try {
            boolean changed=false;
            org.w3c.dom.Document bmr=null;
            if (raf.length()>0) {
              ByteBuffer bb=ByteBuffer.allocate((int)raf.length());
              //new ChannelInputStream(fc).read(bb.array());
              fc.read(bb);
              ByteArrayInputStream bais=new ByteArrayInputStream(bb.array());
              bmr=loadXmlFile( bais );
              //bmr=loadXmlFile(new ChannelInputStream(fc));
            }
            if (bookmarks==null&&bmr==null||bmr.getDocumentElement()==null) {
              bmr=loadXmlFile(getResourceUrl("globaldocs/res/bookmarks.xml"));
              changed=true;
            }
            if (bookmarks==null) {
              bookmarks=buildBookmarkTree(bmr);
            } else {
              // do marge
            
            }
            //writeXML(bookmarks,System.out);
         } finally {
           fl.release();
         }
      } catch (IOException ex) {
         ex.printStackTrace();
      }
    }
    
    Icon icOpened=loadIcon("globaldocs/res/win-explorer-opened-folder.gif");
    Icon icClosed=loadIcon("globaldocs/res/win-explorer-folder.gif");
    
    DefaultTreeModel mod;
    
    /** Creates new form JPanel_Bookmarks */
    public JPanel_Bookmarks() {
        initComponents();

        loadBookmarks();
        jTree1.setModel(mod=new DefaultTreeModel(bookmarks));
        jTree1.setCellRenderer(
          new DefaultTreeCellRenderer() {
            synchronized public Component getTreeCellRendererComponent(JTree tree, Object value,
                    boolean sel,
                    boolean expanded,
                    boolean leaf, int row,
                    boolean hasFocus) {
            
                
                DefaultTreeCellRenderer comp=(DefaultTreeCellRenderer)super.getTreeCellRendererComponent(
                        tree,value, sel,expanded, leaf, row,  hasFocus);
                
                setClosedIcon(icClosed);
                setOpenIcon(icOpened);
                setLeafIcon(null);
                
                comp.setBackgroundNonSelectionColor(jTree1.getBackground());
              //  comp.setBorderSelectionColor(jTree1.getBackground());
               
                return comp;
            }
          }
        );
        searchMaskJPanel1.addDocumentListener(
          new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { refreshTheTree(); }
            public void removeUpdate(DocumentEvent e) { refreshTheTree(); }
            public void changedUpdate(DocumentEvent e) { refreshTheTree(); }
          }
        );
        
        jTree1.setTransferHandler(new TheTransferHandler());
        searchMaskJPanel1.setTooltips("Bookmarks");
        invokeLater(
          new Runnable() {
            public void run() {
              if (bookmarks.count()<=20) theTree.expandAll();
            }
          }
        );
    }
    
    
    private class TheTransferHandler extends TransferHandler {
        
       public boolean importData(JComponent comp,Transferable t) {
         boolean done=false;
         TreePath sp=jTree1.getSelectionPath();
         BookmarkBase target=(sp==null)?null:(BookmarkBase)sp.getLastPathComponent();
         try {
             BookmarkBase bb=null;

             if (t.isDataFlavorSupported(DataFlavors.dfBookmark)) 
                bb=(BookmarkBase)t.getTransferData(DataFlavors.dfBookmark);
             else if (t.isDataFlavorSupported(DataFlavors.dfBookmarkFolder)) 
                bb=(BookmarkBase)t.getTransferData(DataFlavors.dfBookmarkFolder);
             
             boolean targetIsLeafOrClosed=
                  (target!=null)&&(target!=bookmarks)&&
                    ((target instanceof Bookmark)||!jTree1.isExpanded(sp));
             
             if (target==null) target=bookmarks;
             
             if (bb!=null&&!bb.equals(target)) {
                 
               if (bb instanceof BookmarkFolder&&target.isDiscendantOf((BookmarkFolder)bb)) {
                  return false;
               }
                 
               BookmarkFolder oldParent=bb.getParent();
               int oldIndex=bb.getIndex();
                 
               
               if (targetIsLeafOrClosed||(target.equals(oldParent)&&!target.equals(bookmarks))) {
                 BookmarkFolder parent=target.getParent();
                 if (!parent.equals(oldParent)||(oldIndex!=target.getIndex()||target.equals(oldParent))) {
                     oldParent.remove(bb);
                     mod.nodesWereRemoved(oldParent,new int [] {oldIndex},new Object[] {bb});
                     parent.insertElementAt(bb,target.getIndex());
                     mod.nodesWereInserted(parent,new int [] {  parent.getIndex(bb) });
                     done=true;
                 }
               } else {// the target is an opened folder
                 BookmarkFolder parent=(BookmarkFolder)target;
                 oldParent.remove(bb);
                 mod.nodesWereRemoved(oldParent,new int [] {oldIndex},new Object[] {bb});
                 parent.insertElementAt(bb,0);
                 mod.nodesWereInserted(parent,new int [] {0});
                 done=true;
               }
             } else { // a drop from an extearnal application
                 String urlStr=null;
                 if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                     String s=((String)t.getTransferData(DataFlavor.stringFlavor)).trim();
                     URL u=new URL(s);
                     urlStr=s;
                 } else if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    List<File> fls=(List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);
                    if (fls!=null) for (File f:fls) {
                       String s=f.toURL().toString();
                       MainFrame.TheMutableTreeNode node=MainFrame.frame.url2node(s);
                       theTree.createBookmark(target,!targetIsLeafOrClosed,s,node==null?s:node.getTitle());
                       done=true;
                    }
                    if (done) return true;
                 }
                 if (urlStr!=null) {
                   MainFrame.TheMutableTreeNode node=MainFrame.frame.url2node(urlStr);
                   theTree.createBookmark(target,!targetIsLeafOrClosed,urlStr,node==null?urlStr:node.getTitle());
                   return true;
                 }
             }
         } catch(Throwable tr) { tr.printStackTrace(); }
         if (done) saveBookmarkTree();
         return done;
       }
       
       public boolean canImport(JComponent comp,DataFlavor[] transferFlavors) {
         for (DataFlavor d:transferFlavors)
           if (
              d.equals(DataFlavors.dfBookmarkFolder)||
              d.equals(DataFlavor.javaFileListFlavor)||
              DataFlavors.dfList.contains(d)
           ) return true;
         return false;
       }
       protected Transferable createTransferable(JComponent c) {
           //new DataHandler();
         TreePath sp=jTree1.getSelectionPath();
         if (sp!=null) {
           BookmarkBase bb=(BookmarkBase)sp.getLastPathComponent();
           if (bb instanceof BookmarkFolder) {
             final BookmarkFolder bf=(BookmarkFolder)bb;
             return new Transferable() {
               public boolean isDataFlavorSupported(DataFlavor flavor) {
                  return flavor.equals(DataFlavors.dfBookmarkFolder);
               }
               public DataFlavor[] getTransferDataFlavors() {
                 return DataFlavors.dfsFolder;
               }
               public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                  if (flavor.equals(DataFlavors.dfBookmarkFolder)) return bf;
                  throw new UnsupportedFlavorException(flavor);
               }
             };
           } else
           if (bb instanceof Bookmark) {
             final Bookmark b=(Bookmark)bb;
             return new Transferable() {
               public boolean isDataFlavorSupported(DataFlavor flavor) {
                 return DataFlavors.dfList.contains(flavor);
               }
               public DataFlavor[] getTransferDataFlavors() {
                 return DataFlavors.dfs;
               }
               public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                  if (flavor.equals(DataFlavors.dfBookmark)) return b;
                  if (flavor.equals(DataFlavor.stringFlavor)) return b.getHref();
/*
                  if (flavor.equals(DataFlavor.javaFileListFlavor)) {
                    List<File> fl=new Vector<File>();
                    String u=b.getHref();
                    File f=null;
                    if (u.startsWith("file:/")) 
                      f=new File(new URL(u).getPath());
                    else
                      f=new File(u);
                    if (f==null) throw new IOException();
                    fl.add(f);
                    return fl;
                  }
 */
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
                      return new java.io.ByteArrayInputStream((b.getHref()+"\r\n").getBytes());
                  }
                  throw new UnsupportedFlavorException(flavor);
               }
             };
           }
         }
         return super.createTransferable(c);
       }
       public int getSourceActions(JComponent c) {
         return DnDConstants.ACTION_LINK/*|DnDConstants.ACTION_COPY_OR_MOVE*/;
       }
       /*
       public Icon getVisualRepresentation(Transferable t) {
          if (t.isDataFlavorSupported(dfBookmarkFolder)) return icClosed;
          return super.getVisualRepresentation(t);
       }
        */
    };
    
    private Refresher refresher=new Refresher("bookmark_refresher") {
       protected void performRefresh() {
         _refreshTheTree();
       }
    };
    
    public void refreshTheTree() {
      refresher.refresh();
    }
    
    private void _refreshTheTree() {
       if (searchMaskJPanel1.isEmpty()) {
         if (usesVisibility)  updateVisibility(false);
       } else {
         bookmarks.walk(
           new Visitor<Bookmark>() {
             public void visit(Bookmark b) {
               b.setVisible(searchMaskJPanel1.matches(b.getTitle().toLowerCase()));
             }
           }
         );
         updateVisibility( true );
       }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem_navigate = new javax.swing.JMenuItem();
        jMenuItem_drop = new javax.swing.JMenuItem();
        jMenuItem_addNewBookmark = new javax.swing.JMenuItem();
        jMenuItem_createNewFolder = new javax.swing.JMenuItem();
        jMenuItem_rename = new javax.swing.JMenuItem();
        searchMaskJPanel1 = new globaldocs.SearchMaskJPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = theTree;

        jMenuItem_navigate.setMnemonic('N');
        jMenuItem_navigate.setText("Navigate");
        jPopupMenu1.add(jMenuItem_navigate);

        jMenuItem_drop.setMnemonic('D');
        jMenuItem_drop.setText("Drop");
        jPopupMenu1.add(jMenuItem_drop);

        jMenuItem_addNewBookmark.setMnemonic('A');
        jMenuItem_addNewBookmark.setText("Add Bookmark Here");
        jPopupMenu1.add(jMenuItem_addNewBookmark);

        jMenuItem_createNewFolder.setMnemonic('F');
        jMenuItem_createNewFolder.setText("Create Folder Here");
        jPopupMenu1.add(jMenuItem_createNewFolder);

        jMenuItem_rename.setMnemonic('R');
        jMenuItem_rename.setText("Rename");
        jPopupMenu1.add(jMenuItem_rename);

        setLayout(new java.awt.BorderLayout());

        add(searchMaskJPanel1, java.awt.BorderLayout.NORTH);

        jTree1.setBackground(new java.awt.Color(238, 238, 238));
        jTree1.setComponentPopupMenu(jPopupMenu1);
        jTree1.setAutoscrolls(true);
        jTree1.setDragEnabled(true);
        jTree1.setLargeModel(true);
        jTree1.setRootVisible(false);
        jTree1.setShowsRootHandles(true);
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTree1MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTree1MousePressed(evt);
            }
        });

        jScrollPane1.setViewportView(jTree1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents

    private void jTree1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseEntered
      jTree1.grabFocus();
    }//GEN-LAST:event_jTree1MouseEntered

    private class TheTree extends JTree {
        public void expandAll() {
          for (int i=0;i<getRowCount();i++) if (isCollapsed(i)) expandRow(i);
        }
        
        { enableEvents(MouseEvent.MOUSE_EVENT_MASK);
        }
        
        Map<JMenuItem,ActionListener> listeners=new Hashtable<JMenuItem,ActionListener>();
                
        private void setActionListener(JMenuItem mi,ActionListener al) {
          ActionListener old=listeners.get(mi);
          if (old!=null) mi.removeActionListener(old);
          
          mi.addActionListener(al);
          listeners.put(mi,al);
        }
        
        public void createFolder(BookmarkBase b,boolean isOpenedFolder) {
            
            JOptionPane op=
                    new JOptionPane("Folder Name:",JOptionPane.PLAIN_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
            
            op.setWantsInput(true);
            
            JDialog dlg=op.createDialog(
                    JPanel_Bookmarks.this.getTopLevelAncestor(),
                    "Enter The Folder Name"
                    );
            
            dlg.setModal(true);
            dlg.setVisible(true);
            
            Integer val=(Integer)op.getValue();
            if (val==null||val!=JOptionPane.OK_OPTION) return;
            
            String iv=(String)op.getInputValue();
            
            if (iv==null||(iv=iv.trim()).length()==0) return;
            
            
            BookmarkFolder folder=new BookmarkFolder();
            folder.setTitle(iv);
            
            
            if (isOpenedFolder) {
               BookmarkFolder bf=(BookmarkFolder)b;
               bf.insertElementAt(folder,0);
               mod.nodesWereInserted(bf,new int[] {0});
            } else {
               BookmarkFolder parent=b.getParent();
               int ix=parent.getIndex(b);
               /*
               if (ix==parent.bmks.size()-1)
                  parent.add(folder);
               else
                  parent.insertElementAt(folder,ix+1);
                */
               parent.insertElementAt(folder,ix);
               mod.nodesWereInserted(parent,new int[] {parent.getIndex(folder)});
            }
            saveBookmarkTree();
        }
        
        public void createBookmark(BookmarkBase b,boolean isOpenedFolder) {
            MainFrame.History.Item it=MainFrame.frame.currentPosition();
            createBookmark(b,isOpenedFolder,it.toString(),it.getTitle());
        }
        
        public void createBookmark(
           BookmarkBase b,boolean isOpenedFolder,String url,String title
        ) {

            Bookmark bm=new Bookmark();
            bm.setTitle(title);
            bm.setHref(url);
            
            if (isOpenedFolder) {
               BookmarkFolder bf=(BookmarkFolder)b;
               bf.insertElementAt(bm,0);
               mod.nodesWereInserted(bf,new int[] {0});
            } else {
               BookmarkFolder parent=b.getParent();
               int ix=parent.getIndex(b);
               /*
               if (ix==parent.bmks.size()-1)
                  parent.add(bm);
               else
                  parent.insertElementAt(bm,ix+1);
                */
               parent.insertElementAt(bm,ix);
               mod.nodesWereInserted(parent,new int[] {/*bm.getIndex()*/ix});
            }
            saveBookmarkTree();
        }
        
        public void drop(BookmarkBase b) {
          if (
                JOptionPane.showConfirmDialog(
                  JPanel_Bookmarks.this.getTopLevelAncestor(),
                  "Do You Really Want to Remove the "+
                      (b instanceof Bookmark?"Bookmark":"Folder")+": \""+b.getTitle()+"\" ?",
                  "Remove",
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE
                )!=JOptionPane.YES_OPTION
           ) return;

          BookmarkFolder parent=b.getParent();
          int rmx=parent.getIndex(b);
          parent.remove(b);
          mod.nodesWereRemoved(parent,new int[] {rmx},new Object[] {b});
          saveBookmarkTree();
        }
        
        public void rename(BookmarkBase b) {            
           String s=JDialog_BookmarkRename.invoke(b.getTitle());
           if (s!=null) {
             b.setTitle(s);
             mod.nodeChanged(b);
             saveBookmarkTree();
           }
        }
        
        public Point getPopupLocation(MouseEvent evt) {
            final TreePath selPath = getSelectionPath();
            if (selPath==null) {
              jMenuItem_addNewBookmark.setVisible(true);
              jMenuItem_createNewFolder.setVisible(true);
              jMenuItem_drop.setVisible(false);
              jMenuItem_navigate.setVisible(false);
              jMenuItem_rename.setVisible(false);
              
              setActionListener(jMenuItem_createNewFolder,
                  new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      createFolder(bookmarks,true);
                    }
                  }
              );
              setActionListener(jMenuItem_addNewBookmark,
                  new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      createBookmark(bookmarks,true);
                    }
                  }
              );
              
            } else {
              final  BookmarkBase bb=(BookmarkBase)selPath.getLastPathComponent();
              
                setActionListener(jMenuItem_drop,
                  new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      drop(bb);
                    }
                  }
                );
                
                jMenuItem_rename.setVisible(true);
                setActionListener(jMenuItem_rename,
                  new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      rename(bb);
                    }
                  }
                );
                

                if (bb instanceof Bookmark) {
                final Bookmark b=(Bookmark)bb;
                jMenuItem_addNewBookmark.setVisible(true);
                jMenuItem_createNewFolder.setVisible(true);
                jMenuItem_drop.setVisible(true);
                jMenuItem_navigate.setVisible(true);

                setActionListener(jMenuItem_createNewFolder,
                  new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      createFolder(b,false);
                    }
                  }
                );
                setActionListener(jMenuItem_addNewBookmark,
                  new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      createBookmark(b,false);
                    }
                  }
                );
                
                setActionListener(
                  jMenuItem_navigate,
                  new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      MainFrame.frame.navigate(b.getHref());
                    }
                  }
                );
              } else 
              if (bb instanceof BookmarkFolder) {
                jMenuItem_addNewBookmark.setVisible(true);
                jMenuItem_createNewFolder.setVisible(true);
                jMenuItem_drop.setVisible(((BookmarkFolder)bb).isEmpty());
                jMenuItem_navigate.setVisible(false);
                
                setActionListener(jMenuItem_createNewFolder,
                  new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      createFolder(bb,!isCollapsed(selPath));
                    }
                  }
                );
                setActionListener(jMenuItem_addNewBookmark,
                  new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      createBookmark(bb,!isCollapsed(selPath));
                    }
                  }
                );

              }
            }
          return super.getPopupLocation(evt);
        }
    }
    
    private void jTree1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MousePressed
       if ((evt.getButton()==evt.BUTTON1)&&(evt.getClickCount()>1)&&
         (jTree1.getRowForLocation(evt.getX(), evt.getY()) != -1)) {
            TreePath selPath = jTree1.getPathForLocation(evt.getX(), evt.getY());
            if (selPath!=null&&selPath.equals(jTree1.getSelectionPath())) {
              BookmarkBase bb=(BookmarkBase)selPath.getLastPathComponent();
              if (bb instanceof Bookmark) {
                Bookmark b=(Bookmark)bb;
                MainFrame.frame.navigate(b.getHref());
              }
            }
       }
    }//GEN-LAST:event_jTree1MousePressed
    

    private TheTree theTree=new TheTree();
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuItem_addNewBookmark;
    private javax.swing.JMenuItem jMenuItem_createNewFolder;
    private javax.swing.JMenuItem jMenuItem_drop;
    private javax.swing.JMenuItem jMenuItem_navigate;
    private javax.swing.JMenuItem jMenuItem_rename;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    private globaldocs.SearchMaskJPanel searchMaskJPanel1;
    // End of variables declaration//GEN-END:variables
    
}
