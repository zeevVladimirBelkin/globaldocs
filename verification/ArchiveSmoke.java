/* Archive verification helper, 2026-10-15. Not part of the historical application. */
package globaldocs;

import com.sun.net.httpserver.HttpServer;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Permission;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JEditorPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;

public final class ArchiveSmoke {
    private static void require(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }

    private static void write(Path path, String text) throws Exception {
        Files.createDirectories(path.getParent());
        Files.write(path, text.getBytes(StandardCharsets.UTF_8));
    }

    private static String overview(String name, String href) {
        return "<html><body><ul><li><a href=\"" + href + "\">" + name
            + "</a></li></ul></body></html>";
    }

    private static String allclasses(String name, String href) {
        return "<html><body><a target=\"classFrame\" href=\"" + href + "\">"
            + name + "</a></body></html>";
    }

    private static boolean treeContains(TreeModel model, Object node, String text) {
        String label = node == null ? null : node.toString();
        if (label != null && label.contains(text)) return true;
        for (int i = 0; i < model.getChildCount(node); ++i)
            if (treeContains(model, model.getChild(node, i), text)) return true;
        return false;
    }

    private static boolean componentContains(Component c, String text, boolean tree) {
        if (tree && c instanceof JTree) {
            TreeModel model = ((JTree)c).getModel();
            if (treeContains(model, model.getRoot(), text)) return true;
        }
        if (!tree && c instanceof JEditorPane
            && ((JEditorPane)c).getText().contains(text)) return true;
        if (c instanceof Container)
            for (Component child : ((Container)c).getComponents())
                if (componentContains(child, text, tree)) return true;
        return false;
    }

    public static void main(String[] args) throws Exception {
        require(args.length == 1, "Supply a new temporary profile directory");
        final Path root = Paths.get(args[0]).toAbsolutePath();
        require(!Files.exists(root), "The smoke profile must not already exist");
        Files.createDirectories(root);
        System.setProperty("user.home", root.toString());

        final Path local = root.resolve("local-docs");
        final String alpha = "archive/local/Alpha.html";
        write(local.resolve("overview-tree.html"), overview("Alpha", alpha));
        write(local.resolve("allclasses-frame.html"), allclasses("Alpha", alpha));
        write(local.resolve(alpha), "<html><body>Archive fixture Alpha</body></html>");

        final Path archive = root.resolve("fixture.zip");
        final String beta = "archive/zipped/Beta.html";
        try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(archive))) {
            String[][] entries = {
                {"api/overview-tree.html", overview("Beta", beta)},
                {"api/allclasses-frame.html", allclasses("Beta", beta)},
                {"api/" + beta, "<html><body>Archive fixture Beta</body></html>"}
            };
            for (String[] entry : entries) {
                zip.putNextEntry(new ZipEntry(entry[0]));
                zip.write(entry[1].getBytes(StandardCharsets.UTF_8));
                zip.closeEntry();
            }
        }

        final AtomicInteger requests = new AtomicInteger();
        final HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        final String gamma = "archive/remote/Gamma.html";
        server.createContext("/api/", exchange -> {
            requests.incrementAndGet();
            String path = exchange.getRequestURI().getPath();
            String content = path.endsWith("overview-tree.html") ? overview("Gamma", gamma)
                : path.endsWith("allclasses-frame.html") ? allclasses("Gamma", gamma)
                : "<html><body>Archive fixture Gamma</body></html>";
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        });
        server.start();
        final String remoteBase = "http://127.0.0.1:" + server.getAddress().getPort() + "/api/";
        final Path profile = root.resolve(".globaldocs");
        write(profile.resolve("config.xml"), "<root><source>"
            + "<docset project=\"local\" ver=\"1\" managed=\"no\" base=\""
            + local.toUri() + "\"/>"
            + "<docset project=\"zipped\" ver=\"1\" managed=\"no\" base=\"jar:"
            + archive.toUri() + "!/api/\"/>"
            + "<docset project=\"remote\" ver=\"1\" managed=\"no\" base=\""
            + remoteBase + "\"/></source></root>");
        write(profile.resolve("bookmarks.xml"), "<root><folder title=\"Fixtures\">"
            + "<link title=\"Fixture bookmark\" href=\"" + local.resolve(alpha).toUri()
            + "\"/></folder></root>");
        // Synthetic first-run state applies only to this disposable test profile.
        write(profile.resolve(".lic"), "version=1.9.4\n");
        Files.createDirectories(profile.resolve("ins"));
        Path jar = Paths.get(MainFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        require(Files.isRegularFile(jar), "Run against the built application JAR");
        Files.copy(jar, profile.resolve("ins/globaldocs.jar"));

        // Block the historical update check and all other external network access.
        System.setSecurityManager(new SecurityManager() {
            @Override public void checkPermission(Permission permission) { }
            @Override public void checkPermission(Permission permission, Object context) { }
            @Override public void checkConnect(String host, int port) {
                if (!"127.0.0.1".equals(host) && !"localhost".equals(host) && !"::1".equals(host))
                    throw new SecurityException("Smoke test permits loopback networking only");
            }
        });

        int exitCode = 1;
        try {
            Thread watchdog = new Thread(() -> {
                try {
                    Thread.sleep(20000);
                    for (java.util.Map.Entry<Thread, StackTraceElement[]> item : Thread.getAllStackTraces().entrySet()) {
                        if (item.getKey().getName().equals("main") || item.getKey().getName().startsWith("AWT")) {
                            System.err.println("WATCHDOG " + item.getKey().getName() + " " + item.getKey().getState());
                            for (StackTraceElement line : item.getValue()) System.err.println("    " + line);
                        }
                    }
                    System.exit(2);
                } catch (InterruptedException ignored) { }
            }, "smoke-watchdog");
            watchdog.setDaemon(true);
            watchdog.start();
            MainFrame.main(new String[] { local.resolve(alpha).toUri().toString() });
            long deadline = System.currentTimeMillis() + 30000;
            final boolean[] ready = {false};
            do {
                SwingUtilities.invokeAndWait(() -> ready[0] = MainFrame.frame != null
                    && MainFrame.frame.isShowing()
                    && MainFrame.frame.classListDataModel.getSize() == 3
                    && componentContains(MainFrame.frame, "Archive fixture Alpha", false));
                if (ready[0]) break;
                Thread.sleep(100);
            } while (System.currentTimeMillis() < deadline);
            require(ready[0], "Viewer window, index, or local page did not become ready");
            require(MainFrame.assembles.size() == 3, "Expected three configured docsets");
            require(MainFrame.classes.size() == 3, "Expected three merged classes");
            java.util.Set<String> names = new java.util.TreeSet<String>();
            for (MainFrame.A.C c : MainFrame.classes) names.add(c.getFullName());
            require(names.contains("archive.local.Alpha")
                && names.contains("archive.zipped.Beta")
                && names.contains("archive.remote.Gamma"), "Incorrect parsed class names: " + names);
            final boolean[] bookmark = {false};
            SwingUtilities.invokeAndWait(() -> bookmark[0] = componentContains(MainFrame.frame, "Fixture bookmark", true));
            require(bookmark[0], "Bookmark fixture was not loaded");

            MainFrame.A remote = null;
            for (MainFrame.A a : MainFrame.assembles)
                if (remoteBase.equals(a.urlBase)) remote = a;
            require(remote != null, "Remote docset not found");
            File cache = new File(MainFrame.cacheDirectory, remote.getId());
            require(cache.isFile() && cache.length() > 0, "Remote index cache was not written");
            ByteArrayOutputStream cacheText = new ByteArrayOutputStream();
            try (GZIPInputStream input = new GZIPInputStream(Files.newInputStream(cache.toPath()))) {
                byte[] bytes = new byte[512];
                int count;
                while ((count = input.read(bytes)) != -1) cacheText.write(bytes, 0, count);
            }
            String cached = new String(cacheText.toByteArray(), StandardCharsets.UTF_8);
            require(cached.contains("archive.remote.Gamma") && cached.contains("*END*"), "Invalid cache content");
            int previous = requests.get();
            remote.parseClassList();
            require(requests.get() == previous, "Cached index unexpectedly fetched the remote docset");
            System.out.println("PASS: visible viewer; local HTML rendered; directory, ZIP, and loopback HTTP docsets merged");
            System.out.println("PASS: three-class index; bookmark fixture loaded; gzip index cache written and reused");
            System.out.println("VERSION=" + Utils.VERSION + "; HTTP_REQUESTS=" + requests.get());
            exitCode = 0;
        } catch (Throwable failure) {
            failure.printStackTrace();
        } finally {
            server.stop(0);
            SwingUtilities.invokeAndWait(() -> {
                for (Window window : Window.getWindows()) window.dispose();
            });
            System.exit(exitCode);
        }
    }
}
