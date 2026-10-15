package globaldocs;

import globaldocs.JPanel_Bookmarks.Bookmark;
import globaldocs.JPanel_Bookmarks.BookmarkFolder;
import java.awt.datatransfer.DataFlavor;
import java.util.List;


public class DataFlavors {
    
    private static DataFlavor _df(String mime) {
        try {
            return new DataFlavor(mime);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    public final static DataFlavor
            dfBookmark=new DataFlavor(Bookmark.class,"Bookmark"),
            dfBookmarkFolder=new DataFlavor(BookmarkFolder.class,"Bookmark Folder"),
            dfUriList=_df("text/uri-list"),
            dfs[]={dfBookmark,dfUriList,DataFlavor.stringFlavor},
            dfsFolder[]={dfBookmarkFolder}
    ;
    public final static List<DataFlavor> dfList=java.util.Arrays.asList(dfs);
}