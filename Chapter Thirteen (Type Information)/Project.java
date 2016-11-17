import java.lang.reflect.*;
import java.util.*;

interface Transactable {
  void transfer(long num, Transactable payee) throws Exception;
  void receive(Transactable payer, long num) throws Exception;
  void commit();
  void rollback();
}

class Account implements Transactable {
  private String name;
  private long principal;
  private long tempPrincipal;
  public Account(long principal, String name) {
  	this.principal = principal;
  	tempPrincipal = principal;
  	this.name = name;
  }
  public void transfer(long num, Transactable payee) throws Exception {
  	if (principal < num) {return;}
  	principal -= num;
  	payee.receive(this, num);
  }
  public void receive(Transactable payer, long num) throws Exception {
  	principal += num;
  	Random random = new Random();
  	if (random.nextBoolean()) {
      System.out.println("Fail to receive!");
  	  throw new Exception("Fail to receive!");
  	}
  }
  public void rollback() {principal = tempPrincipal; System.out.println(this + " rollback: " + principal);}
  public void commit() {tempPrincipal = principal; System.out.println(this + " commit: " + principal);}
  public String toString() {return name;}
  public long getPrincipal() {return principal;}
}

class DynamicProxyHandler implements InvocationHandler {
  private Object proxied;
  public DynamicProxyHandler(Object proxied) {
    this.proxied = proxied;
  }
  private void callCommit(Object proxy) {
    try {
	  Class c = Class.forName("Account");
	  Method m = c.getDeclaredMethod("commit");
	  m.invoke(proxy);
	} catch(Exception e) {}
  }
  private void callRollback(Object proxy) {
  	try {
	  Class c = Class.forName("Account");
	  Method m = c.getDeclaredMethod("rollback");
	  m.invoke(proxy);
	} catch(Exception e) {}
  }
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object o = null;
    try {
      o = method.invoke(proxied, args);
      callCommit(proxied);
      return o;
    } catch (InvocationTargetException e) {
      callRollback(proxied);
      if (!method.getName().equals("transfer")) 
      	throw new Exception("from receive");
      return o;
    }
  }
}

public class Project {
  public static void consumer(Transactable payer, Transactable payee) throws Exception {
	payer.transfer(100, payee);
  }
  public static void main(String[] args) throws Exception {
	Account realA = new Account(1000, "A");
	Account realB = new Account(1000, "B");
	// Insert a proxy and call again:
	Transactable proxyA = (Transactable)Proxy.newProxyInstance(
		Transactable.class.getClassLoader(),
		new Class[]{ Transactable.class },
		new DynamicProxyHandler(realA));
	Transactable proxyB = (Transactable)Proxy.newProxyInstance(
		Transactable.class.getClassLoader(),
		new Class[]{ Transactable.class },
		new DynamicProxyHandler(realB));
	for (int i = 0; i < 10; i++) {
	  System.out.println("Before transaction:");  		
	  System.out.println("A has: " + realA.getPrincipal());
	  System.out.println("B has: " + realB.getPrincipal());
	  consumer(proxyA, proxyB);
	  System.out.println("After transaction");
	  System.out.println("A has: " + realA.getPrincipal());
	  System.out.println("B has: " + realB.getPrincipal());
	}
  }
}