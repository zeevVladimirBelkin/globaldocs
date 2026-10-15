#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <process.h>
#include <windows.h>

#include <objbase.h>
#include <shellapi.h>
#include <shlobj.h>
#include <shlguid.h>

#include <unistd.h>

/*
This function was got from here:
  http://msdn.microsoft.com/library/default.asp?url=/library/en-us/shellcc/platform/shell/programmersguide/shell_int/shell_int_programming/shortcuts/shortcut.asp
*/
HRESULT createLink(LPCSTR lpszPathObj, LPCSTR lpszPathLink, LPCSTR lpszDesc) 
{ 
    HRESULT hres; 
    IShellLink* psl; 
 
    // Get a pointer to the IShellLink interface. 
    hres = CoCreateInstance(CLSID_ShellLink, NULL, CLSCTX_INPROC_SERVER, 
                            IID_IShellLink, (LPVOID*)&psl); 
    if (SUCCEEDED(hres)) 
    { 
        IPersistFile* ppf; 
 
        // Set the path to the shortcut target and add the description. 
        psl->SetPath(lpszPathObj); 
        psl->SetDescription(lpszDesc); 
 
        // Query IShellLink for the IPersistFile interface for saving the 
        // shortcut in persistent storage. 
        hres = psl->QueryInterface(IID_IPersistFile, (LPVOID*)&ppf); 
        
        if (SUCCEEDED(hres)) 
        { 
            WCHAR wsz[MAX_PATH]; 
 
            // Ensure that the string is Unicode. 
            MultiByteToWideChar(CP_ACP, 0, lpszPathLink, -1, wsz, MAX_PATH); 
                        
            // Add code here to check return value from MultiByteWideChar 
            // for success.
 
            // Save the link by calling IPersistFile::Save. 
            hres = ppf->Save(wsz, TRUE); 
            ppf->Release(); 
        } 
        psl->Release(); 
    } 
    return hres; 
}

const char *LINK_NAME="Globaldocs -- viewer for javadocs.lnk";

int main(int argc,char **argv)
{
    char *dir=strdup(argv[0]);
    strrchr(dir,'\\')[1]=0;
    puts(dir);
    
    char buf[1024];
    sprintf(buf,"%s\\%s",dir,".java");
    
    
    FILE *f=fopen(buf,"r");
    
    if (!f) {
      fputs("can not open file \".jre\"",stderr);
      return 5;
    }
    
   char *s=fgets(buf,sizeof(buf),f);
   
   fclose(f);
   
   if (!s) {
      fputs("the first line of \".jre\" is empty",stderr);
      return 5;
   }
   
   int ln=strlen(s);
   for (;;) {
    if (!ln) {
      fputs("the first line of \".jre\" is empty",stderr);
      return 5;             
    }
    ln--;
    if (isspace(s[ln])) s[ln]=0; else break; 
   }
   
   char buf1[1024];
   sprintf(buf1,"\"%sglobaldocs.jar\"",dir);
   //strcpy(buf+strlen(buf),"\\bin\\javaw.exe");
   
   if (argc>=2&&!strcmp("/install",argv[1])) {
     HKEY hKey=0;
     RegOpenKey(
       HKEY_CURRENT_USER,
       "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders", 
       &hKey 
     );
     if (hKey) {
       BYTE  buf2[1024]={0};
       DWORD rv=sizeof(buf2);
       DWORD type=0;
       RegQueryValueEx(hKey, "Desktop",0,&type, buf2, &rv);
       char desktopPath[1024],
            programsPath[1024];
       if (*buf2) ExpandEnvironmentStrings((char*)buf2,desktopPath, sizeof(desktopPath));
       rv=sizeof(buf2);
       RegQueryValueEx(hKey, "Programs",0,&type, buf2, &rv);
       
       if (*buf2) ExpandEnvironmentStrings((char*)buf2,programsPath,sizeof(programsPath));
       
       int i=strlen(desktopPath);
       if (desktopPath[i-1]!='\\') strcpy(desktopPath+i,"\\");
       i=strlen(programsPath);
       if (programsPath[i-1]!='\\') strcpy(programsPath+i,"\\");
       
       int dpl;
       
       strcpy(desktopPath+(dpl=strlen(desktopPath)),LINK_NAME);
       strcpy(programsPath+strlen(programsPath),LINK_NAME);
       
       OleInitialize(0);
       
       createLink(argv[0],desktopPath,0);
       createLink(argv[0],programsPath,0);
       
       strcpy(desktopPath+dpl,"Global Javadoc Viewer.lnk");
       
       unlink(desktopPath);
               
 //      printf("%s\n%s\n%s\n",desktopPath,programsPath,argv[0]);
       
       
       RegCloseKey(hKey);
     }
   //  system("pause");
     return 0;
   }
   
   char *args[]={
      "-Xmx100M",
      "-jar",
      buf1,
      0
   };
   
   execvp(buf,args);    
   return 0;
}
