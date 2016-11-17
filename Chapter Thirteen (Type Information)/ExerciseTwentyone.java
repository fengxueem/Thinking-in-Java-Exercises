import static net.mindview.util.Print.*;

interface Interface {
  void doSomething();
  void somethingElse(String arg);
}

class RealObject implements Interface {
  public void doSomething() {
    long startTime = System.nanoTime();
    print("doSomething");
    System.out.println("RealObject.doSomething() costs: " + (System.nanoTime() - startTime) + "ns");
  }
  public void somethingElse(String arg) {
    long startTime = System.nanoTime();
    print("somethingElse " + arg);
    System.out.println("RealObject.somethingElse() costs: " + (System.nanoTime() - startTime) + "ns");
  }
} 

class SimpleProxy implements Interface {
  private Interface proxied;
  public SimpleProxy(Interface proxied) {
    this.proxied = proxied;
  }
  public void doSomething() {
    long startTime = System.nanoTime();
    print("SimpleProxy doSomething");
    proxied.doSomething();
    System.out.println("SimpleProxy.doSomething() costs: " + (System.nanoTime() - startTime) + "ns");
  }
  public void somethingElse(String arg) {
    long startTime = System.nanoTime();
    print("SimpleProxy somethingElse " + arg);
    proxied.somethingElse(arg);
    System.out.println("SimpleProxy.somethingElse() costs: " + (System.nanoTime() - startTime) + "ns");
  }
}	

class SimpleProxyDemo {
  public static void consumer(Interface iface) {
    iface.doSomething();
    iface.somethingElse("bonobo");
  }
  public static void main(String[] args) {
    consumer(new RealObject());
    consumer(new SimpleProxy(new RealObject()));
  }
}

public class ExerciseTwentyone {
  public static void main(String[] args){
    SimpleProxyDemo.main(args);    
  }  
}