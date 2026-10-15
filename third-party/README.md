# Third-party provenance and notices

This inventory was prepared on 15 October 2026. The Artistic License in the
repository covers the original Globaldocs package as historically supplied; this
inventory does not claim to relicense separately owned code, artwork, or logos.

## Identified NetBeans graphics

The following files in `src/globaldocs/res/` are byte-identical to files in the
official Apache NetBeans source at commit
[`0104eeb3da7094338212ea8a37a32a73354c7574`](https://github.com/apache/netbeans/tree/0104eeb3da7094338212ea8a37a32a73354c7574):

| Globaldocs resource | NetBeans upstream path |
| --- | --- |
| `helpviewer_back.png` | `platform/javahelp/src/org/netbeans/modules/javahelp/resources/helpviewer_back.png` |
| `helpviewer_forward.png` | `platform/javahelp/src/org/netbeans/modules/javahelp/resources/helpviewer_forward.png` |
| `win-explorer-folder.gif` | `platform/o.n.swing.plaf/src/org/netbeans/swing/plaf/resources/win-explorer-folder.gif` |
| `win-explorer-opened-folder.gif` | `platform/o.n.swing.plaf/src/org/netbeans/swing/plaf/resources/win-explorer-opened-folder.gif` |
| `class.gif` | `java/java.source/src/org/netbeans/modules/java/source/resources/icons/class.gif` |
| `interface.gif` | `java/java.source/src/org/netbeans/modules/java/source/resources/icons/interface.gif` |

The corresponding NetBeans `licenseinfo.xml` files identify Apache-2.0-ASF for
these image resources. The unmodified upstream [LICENSE](netbeans-LICENSE.txt)
and [NOTICE](netbeans-NOTICE.txt) are included. The older project files are
preserved; this exact-byte comparison is not a claim that historical NetBeans
releases used the current Apache license.

## Execution-profile build support

`nbproject/executionprofile-impl.xml` contains its own **Sun Public License 1.0**
notice, crediting **Iván Rivera Rodríguez**, copyright 2005. That notice is
unchanged. The [SPL 1.0 text](SPL-1.0.txt) is included from the
[SPDX license-text collection](https://spdx.org/licenses/SPL-1.0.html).
The original upstream project is named in the file's notice; no binary NetBeans
execution-profile module is redistributed here.

## Microsoft shortcut sample

`C/run.cpp` explicitly attributes its `createLink` function to the Microsoft
Shell Links sample. The corresponding
[Microsoft Win32 documentation source](https://github.com/MicrosoftDocs/win32/blob/docs/desktop-src/shell/links.md)
and its [code license](https://github.com/MicrosoftDocs/win32/blob/docs/LICENSE-CODE)
identify Microsoft sample code under the MIT license. The copyright and
permission notice is preserved in [MS-LICENSE-CODE.txt](MS-LICENSE-CODE.txt).
The original source attribution remains in `run.cpp`.

## Historical artwork with incomplete provenance

The original About page credits the globe artwork to the former FU Berlin
`geog.fu-berlin.de/globes/` collection. An
[archived collection information page](https://web.archive.org/web/20060420102445/http://www.geog.fu-berlin.de/globes/bilder.html)
says that the authors of most images were unknown; it does not establish a
redistribution license. The GIF metadata in `9kb8.gif` names **Michael Jones**
and `meshglobe1w.avi`, but does not contain a license grant.

The original images, including `9kb8.gif`, `globe.gif`, and `globeDisabled.gif`,
are retained unchanged at the project author's explicit request. Their licensing
has **not** been independently established. They should not be described as
newly licensed under Artistic or Apache. The About page's original credit is
preserved.

The remaining small icons, Java/NetBeans-related artwork, and the historical
screenshot come from the original project materials. The NetBeans logo is used
in the old About page to acknowledge the IDE. No separate provenance/license
records were found for the remaining images; no broader third-party rights or
trademark permissions are asserted by this source publication.

## Dependencies not redistributed

The original `dist/lib/` contained `swing-layout-1.0.1.jar`,
`swing-layout-1.0.3.jar`, `AbsoluteLayout.jar`, and an Apple-built `javaws.jar`.
The web skeleton also used `jnlp.jar`, `jnlp-servlet.jar`, and `jardiff.jar`.
Those local JARs contained no standalone LICENSE/NOTICE file.

The [upstream Swing Layout 1.0.3 POM](https://repo.maven.apache.org/maven2/org/swinglabs/swing-layout/1.0.3/swing-layout-1.0.3.pom)
identifies LGPL. That metadata does not establish the provenance of every local
binary, and current Apache NetBeans licensing is not automatically assigned to
the old AbsoluteLayout JAR. The Apple/Sun Web Start binaries likewise need their
original distribution terms. All of these binary dependencies are therefore
excluded. Build instructions explain the externally supplied JNLP API input.
