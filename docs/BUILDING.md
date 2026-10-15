# Building and running the recovered source

The historical application uses Java 5 language features, internal JDK APIs, and
JNLP. Preserve this code when reproducing the archive; porting it to a current
JDK is a separate task.

## Java application

Requirements:

- A legacy-compatible JDK. Zulu OpenJDK 8u504 was verified. Java 11 fails as
  documented in [VERIFICATION.md](VERIFICATION.md).
- Apache Ant 1.8 or later, as required by the surviving generated build script.
- A legally obtained JAR containing the legacy `javax.jnlp` API, such as the JNLP
  API from a historical Java Web Start SDK. It is an external build input, not
  part of this source distribution.

The project metadata also contains old NetBeans library names for Swing Layout
Extensions (`swing-layout`) and AbsoluteLayout. The retained Java sources have
no references to these libraries; they can be set to empty paths for the command
below. Their historical binaries are not included.

Select the JDK explicitly through `JAVA_HOME` and your command search path. From
the repository root, run the `jar` target explicitly:

```powershell
ant -Djnlp.jar=C:/path/to/jnlp.jar `
    -Dlibs.swing-layout.classpath= `
    -Dlibs.absolutelayout.classpath= jar
```

Use a path without spaces, or quote the entire `-Djnlp.jar=...` argument. This
uses the existing build files and creates `dist/globaldocs.jar`. The default
Ant target includes old management/IDE behavior, so the explicit `jar` target
is preferable for reproducing the application.

Run with the JNLP API on the classpath:

```powershell
java -Xmx100M -cp "dist/globaldocs.jar;C:/path/to/jnlp.jar" globaldocs.MainFrame
```

On Unix-like systems, replace the classpath separator `;` with `:`. The old
`java -jar globaldocs.jar` advice assumes the historical runtime/dependency layout;
it does not supply the missing compile-time API for a modern JDK.

Settings, bookmarks, cached indexes, and installation files are normally stored
in `${user.home}/.globaldocs`. An evaluation can use a separate home directory:

```powershell
java -Duser.home=C:/temp/globaldocs-evaluation -Xmx100M `
     -cp "dist/globaldocs.jar;C:/path/to/jnlp.jar" globaldocs.MainFrame
```

The historical first-run dialogs include the license and an offer to integrate
the viewer with the desktop. Desktop integration is optional. For useful
evaluation, configure a local docset using the older framed Javadoc format;
the bundled default network URLs are historical.

The preserved manifest also names `globaldocs.Interceptor` as a premain class,
but no such source is present. The normal entry point is
`globaldocs.MainFrame`; Java-agent use has not been verified.

## Optional Windows launcher

The launcher sources are in `C/`. The historical toolchain was Dev-C++/MinGW;
the required platform interfaces include Win32 Shell/COM, `ole32`, and `uuid`.
`C/run_private.rc` refers to `run.ico`. Despite their names, `run_private.h` and
`run_private.rc` are build source files, not credentials or private IDE settings.

In a suitable MinGW environment, the retained build recipe is:

```text
cd C
mingw32-make -f Makefile.win
```

Before packaging an application with Windows desktop integration, copy the
newly built `C/run.exe` to `src/globaldocs/res/run.exe`, then rerun the Java `jar`
target. Executables are intentionally ignored by Git. The wrapper expects
`globaldocs.jar` and a `.java` sidecar naming the Java executable beside it;
the Java-side installer writes that sidecar. Its `/install` path creates desktop
shortcuts. Those installation effects are outside the verification performed
for this source publication.

## Historical Web Start files

`globaldocs.jnlp` and `web/` preserve old deployment examples. Building a WAR
requires the separately obtained JNLP download-servlet libraries and a compatible
servlet container; these binaries are omitted. The root JNLP codebase is a
placeholder, and no signing key, signing script, hosted update service, or
working Web Start deployment is provided by this repository.
