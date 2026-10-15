# Verification of this archive

Performed on Windows on 15 October 2026. These are bounded preservation/build
checks and a small functional smoke test, not a full compatibility or security
certification.

## Preservation and publication contents

- Recorded SHA-256 hashes for all **580 files** in the original project before
  preparing the archive, then rechecked them: **no original files changed**.
- Compared all **75 retained Java/form/native-source/image files** in the relevant
  source directories against the originals: byte-identical. This includes all
  33 application Java sources, all 11 forms, and the original image resources.
- Kept the original Artistic License and author notices, plus the identified
  third-party notices. Compared six NetBeans image resources to exact upstream
  Git blob hashes; see the [provenance inventory](../third-party/README.md).
- Excluded signing material, private IDE/profile files, CVS administration,
  backups, compiled outputs, and third-party JARs before staging the initial
  commit. Inspected the selected text files for credential literals without
  publishing secret values. The JNLP/build paths were sanitized as described in
  [ARCHIVE.md](ARCHIVE.md).
- Parsed all **21 XML/form files** with external DTD resolution disabled.
- Preserved original line endings rather than reformatting old source files.

## Java 8 build: passed

Environment: **Azul Zulu 8.96.0.205-CA, OpenJDK 1.8.0_504-b01, Windows x64**,
and **Apache Ant 1.10.15**. The portable JDK download was verified against Azul's
published SHA-256:

```text
510d00a6814ee576205cdfec7efd0c9f93b066b34bf3f841b8854594dc97476b
```

The `jar` target in the preserved `build.xml` completed with **BUILD SUCCESSFUL**
and exit code 0, compiling the unmodified application sources with their original
Java 5 source/target settings. There were nine warnings about obsolete target
settings and internal `sun.misc` / `sun.net` APIs, plus unchecked/deprecated API
notes. The output contains `globaldocs.MainFrame` as its entry point and the
application resources.

The sole external compile input was the original local JNLP API JAR from the
historical Web Start SDK files. Neither that JAR nor the JDK or generated
application JAR is committed here. The old Swing Layout and AbsoluteLayout
classpath properties were explicitly empty; the retained sources compiled
without those libraries. The build command is in [BUILDING.md](BUILDING.md).

## Java 8 functional smoke test: passed

The helper in [`verification/ArchiveSmoke.java`](../verification/ArchiveSmoke.java)
was compiled separately against the built application JAR. It launched the real
`MainFrame.main` in a new disposable application profile. It prepared synthetic
first-run state to avoid license/desktop-installation dialogs and blocked all
external networking; the HTTP fixture was served only on `127.0.0.1`.

Verified assertions:

1. The viewer window became visible and rendered a local HTML page.
2. A local directory, a ZIP with an `api/` prefix, and a loopback HTTP docset
   produced three correctly named classes in one tree/index.
3. A bookmark/folder fixture was loaded into the bookmark tree.
4. The remote index was written as gzip data with its expected end marker.
5. Reading that index again reused the cache with no further HTTP request.

The helper exited with code 0. The loopback server received one request. The
deliberately blocked historical update check produced network-denial diagnostics;
these were expected test restrictions. Windows browser discovery also emitted
a registry warning from the old private Preferences API. The filesystem sandbox
initially prevented that registry initialization, so the successful run used
normal host permissions while retaining the disposable profile and network block.
No application source changes were needed.

To repeat after building, use Java 8 and a JNLP API JAR. In PowerShell:

```powershell
javac -encoding UTF-8 -cp "dist/globaldocs.jar;C:/path/to/jnlp.jar" `
      -d build/verification verification/ArchiveSmoke.java
java -cp "build/verification;dist/globaldocs.jar;C:/path/to/jnlp.jar" `
     globaldocs.ArchiveSmoke C:/temp/new-globaldocs-smoke-profile
```

The profile path must not already exist. The helper requires a graphical session
and has a 20-second watchdog. It does not test native desktop installation,
real authentication, public websites, or drag-and-drop interactions.

## Java 11 build: failed, as expected for this historical source

Microsoft OpenJDK **11.0.16.1** rejects `-source 5 -target 5` before compilation.
A diagnostic compilation with `-source 8 -target 8` and the external JNLP API
found **17 errors**, including:

- Missing `sun.misc.BASE64Encoder` and `sun.misc.BASE64Decoder` in `Wallet.java`.
- Changed `DefaultMutableTreeNode.children()` generic return types in the main
  tree and bookmarks code.
- Changed `DefaultTableModel.dataVector` generic types in `DocsetListEditorPanel`.

The custom HTTP handler also depends on internal `sun.net.www.protocol.http`
classes. A current-Java port needs actual source changes and a separate review;
changing the compiler target alone is insufficient.

## Native launcher and other unverified areas

`g++ -fsyntax-only C/run.cpp` passed under the available **Cygwin** toolchain,
with two warnings about string literals assigned to `char*`. Cygwin is not the
historical MinGW target: linking a standalone MinGW launcher, packaging it, and
running its desktop/shortcut installer were **not** verified.

Modern Javadoc generation/layouts, Java 5/6 execution on current operating
systems, real remote docsets, HTTPS/authentication, the JMX/FileRPC interaction
between separate instances, Web Start/servlet deployment, and every GUI action
remain unverified. In particular, the original HTTPS code disables certificate
and hostname validation; it is preserved here as historical code.
