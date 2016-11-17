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
  public Object
  invoke(Object proxy, Method method, Object[] args)
  throws Throwable {
    // print("proxy: " + proxy);
    // Calling proxy.toString() here will fxxk the whole program up.
    // Since, calls through the interface are redirected throuhgh the proxy,
    // which causes an infinite loop.
    print("**** proxy: " + proxy.getClass() +
      ", method: " + method + ", args: " + args);
    if(args != null)
      for(Object arg : args)
        print("  " + arg);
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
    // notice here in main, the proxy.toString() returns the address of RealObject
    // that is passed into the DynamicProxyHandler constructor in line 46. But, this
    // real object is not the proxy itself, just the puppet it controls
    print("proxy: " + proxy + ";real object: " + real);
  }
}

public class ExerciseTwentythree {
  public static void main(String[] args){
    SimpleDynamicProxy.main(args);
  }  
}