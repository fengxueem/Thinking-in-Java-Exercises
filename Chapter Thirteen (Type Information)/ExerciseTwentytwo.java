import java.lang.reflect.*;
import static net.mindview.util.Print.*;

interface Interface {
  void doSomething();
  void somethingElse(String arg);
}

class RealObject implements Interface {
  public void doSomething() { print("doSomething"); }
  public void somethingElse(String arg) {
    print("somethingElse " + arg);
  }
} 

class DynamicProxyHandler implements InvocationHandler {
  private Object proxied;
  public DynamicProxyHandler(Object proxied) {
    this.proxied = proxied;
  }
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // start counting time
    long startTime = System.nanoTime();
    System.out.println("**** proxy: " + proxy.getClass() + ", method: " + method + ", args: " + args);
    if(args != null)
      for(Object arg : args)
        System.out.println("  " + arg);
    // end of counting, this is a very accurate observation of the method call time, since
    // it ends before the real job getting done.
    System.out.println("Proxy method-call time: " + (System.nanoTime() - startTime) + "ns");
    return method.invoke(proxied, args);
  }
}	

class SimpleDynamicProxy {
  public static void consumer(Interface iface) {
    iface.doSomething();
    iface.somethingElse("bonobo");
  }
  public static void main(String[] args) {
    RealObject real = new RealObject();
    consumer(real);
    // Insert a proxy and call again:
    Interface proxy = (Interface)Proxy.newProxyInstance(
      Interface.class.getClassLoader(),
      new Class[]{ Interface.class },
      new DynamicProxyHandler(real));
    consumer(proxy);
  }
}

public class ExerciseTwentytwo {
  public static void main(String[] args){
    SimpleDynamicProxy.main(args);    
  }  
}