/*
 * FileRPC.java
 *
 * Created on July 4, 2006, 2:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package globaldocs.ipc;

import java.util.*;
import static globaldocs.Utils.*;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;

/**
 *
 * @author zeev
 */
public class FileRPC  implements InvocationHandler {
   
   public Object invoke(Object proxy, Method method, Object[] args)
      throws Throwable 
   {
      return xchg(
        new Object[] {
          method.getDeclaringClass().getCanonicalName(),
          method.getName(),
          method.getParameterTypes(),
          args
        }
      );
   }
   
   public Object getProxy(Class i) {
      return Proxy.newProxyInstance(i.getClassLoader(),new Class [] {i},this);
   } 
      
   
   private Object xchg(Object arg) throws Throwable  {
     ipc_send_request(arg);
     IPC_Data.Response response=ipc_get_response();
     if (response==null) return null;
     if (response.getException()!=null) throw response.getException();
     return response.getResponse();
   }
   
   private Hashtable handlers=new Hashtable();
   
   public void addHandler(Class i,Object obj) {
     handlers.put(i.getCanonicalName(),obj);
   }
   
   private Object invokeCall(Object args) throws Throwable {
     if (!(args instanceof Object [])) throw new Exception("internal error -- an array is required");
     Object a[]=(Object[])args;
     if (a.length!=4) throw new Exception("internal error -- the array should have 3 elements");
     String interfaceName=(String)a[0],
            methodName=(String)a[1];
     
     Object handler=handlers.get(interfaceName);
     

     if (!(a[2]==null||a[2] instanceof Class[]))
       throw new Exception("internal error -- an array of classes is required");
     
     
     Class cl=getClass().forName(interfaceName),argcs[]=(Class[]) a[2];
     
     //for (Method mm:cl.getMethods()) System.out.println(mm);
     
      Method m=null;
      try {
         m = cl.getDeclaredMethod(methodName, argcs);
      } catch (Throwable t) {
         t.printStackTrace();
      }
     
     if (m==null)
       throw new Exception("internal error -- required method is not found");
     
     return m.invoke(handler,(Object[])a[3]);

   }
   
   Thread worker=null;
   /** Creates a new instance of FileRPC */
   public FileRPC() {
     worker=new Thread() {
       public void run() {
          
         for (;;) {
           try {
             Thread.sleep(200);
           } catch (InterruptedException ex) {
             ex.printStackTrace();
           }
           Object rq=ipc_get_request();
           if (rq!=null) try {
             IPC_Data.Response response=new IPC_Data.Response();
             try {
               response.setResponse(invokeCall(rq));
             } catch (Throwable t) {
               response.setException(t);
             }
             ipc_send_response(response);
           } catch (Throwable t) {
           }
         }
          
       }
     };
     worker.start();

   }
   
}
