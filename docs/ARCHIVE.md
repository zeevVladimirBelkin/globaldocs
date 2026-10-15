# About this source archive

Prepared on 15 October 2026 from the author's surviving local Globaldocs project.
The application's version resource and both inspected application JARs report
1.9.4. The handwritten release log ends on 6 January 2007. Some NetBeans build
metadata was regenerated later, so this repository describes a recovered source
snapshot, rather than an authenticated checkout of a particular historical tag.

## Scope and preservation

All 33 Java source files under `src/globaldocs/`, all 11 associated `.form` files,
and the original application resource files are retained. The Java sources,
forms, C++ launcher source, images, copyright notices, and license texts have
not been modernized. The image assets remain as found in the project, including
the credited globe animations. The optional native launcher must be rebuilt.

Two small programs in `src/tests/` were exploratory editor/class-loader demos,
not an automated test suite or a dependency of the application. They are omitted,
along with the empty `test/`, `common/`, and `includes/` directories. Unused
advertising images and the server-specific PHP docset helper are also omitted.
The historical Web Start descriptors and web skeleton remain for context; the
servlet/runtime JARs and old deployment binaries do not.

Excluded before the first Git commit: CVS/CVSROOT administration, compiled Java
classes, application/dependency JARs, native executable/object/resource outputs,
signed releases/signatures, signing keys and certificates, signing/deployment
scripts, private NetBeans files, machine-specific execution profiles, IDE
databases, editor lock files, and backups. No original CVS/Git history was imported.

The following non-application files were adjusted for publication, with dated
notices in the files themselves:

- `nbproject/project.properties`: replaced the machine-specific JNLP library
  location with a `jnlp.jar` property supplied by the builder.
- `C/Makefile.win`: removed absolute Dev-C++ installation paths; the compiler's
  configured search paths are used instead.
- `globaldocs.jnlp`: replaced the author's local filesystem codebase with an
  explicitly non-working `example.invalid` placeholder.

The new Markdown documents, `.gitignore`, and `.gitattributes` explain the archive
and preserve the recovered files' original line endings. `readme.txt` is kept as
a historical document; the new README and build notes supersede its old Web Start
installation advice and old tool requirements.

## Feature evidence

| Capability | Source evidence |
| --- | --- |
| Combined tree and class index | `MainFrame.A`, `ClassTreeNode`, package tree construction, and `ClassListDataModel` in `MainFrame.java` |
| Remote/local docsets | URL loading in `MainFrame.A.loadClasses`, directory selection and URL editing in `DocsetEditor_Dialog.java` |
| JAR/ZIP documentation | `ZipFile` inspection and `jar:` URL construction in `DocsetEditor_Dialog.java` and `MainFrame.java` |
| Bookmarks and folders | `JPanel_Bookmarks.java`, including XML persistence and its transfer handler |
| Index caches | `MainFrame.A` reads/writes compressed index files; `DocsetListEditorPanel` exposes Clear Caches |
| Search and navigation | `SearchMaskJPanel.java`, `ClassListDataModel`, and the viewer history/navigation code |
| Inter-instance communication | `ipc/FileRPC.java`, `RemoteControlMBean.java`, and registration in `MainFrame.java` |
| Native desktop integration | `Utils.Installer`, `WindowsInstaller`, `FreedesktopInstaller`, and `C/run.cpp` |

This is code-based capability evidence. The narrower runtime checks are described
separately in [VERIFICATION.md](VERIFICATION.md).
