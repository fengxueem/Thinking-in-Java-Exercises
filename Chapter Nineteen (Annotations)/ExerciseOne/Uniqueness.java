package ExerciseOne;

public @interface Uniqueness {
  Constraints constraints()
    default @Constraints(unique=true);
} ///:~
