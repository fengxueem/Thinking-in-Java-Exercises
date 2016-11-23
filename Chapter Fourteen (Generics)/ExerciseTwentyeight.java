import typeinfo.pets.*;

class Generic1<T> {
  public void takeT(T t) {
  	System.out.println(t.getClass().getName());
  }
}

class Generic2<T> {
  private T t;
  public void setT(T t) { this.t = t; } 
  public T returnT() {
  	return t;
  }
}

public class ExerciseTwentyeight {
  static <T> void contravariant(Generic1<? super T> gener, T t) {
  	gener.takeT(t);
  }
  static <T> T covariant(Generic2<? extends T> gener) {
  	return gener.returnT();
  }
  public static void main(String[] args) {
  	Generic2<Pet> pet = new Generic2<Pet>();
  	pet.setT(new Cat());
  	// pet.setT(new Individual()); cannot upcast
  	Generic1<Individual> individual = new Generic1<Individual>();
  	contravariant(individual, new Cat());
  	contravariant(individual, new Pet());
  	System.out.println(covariant(pet));
  }
}