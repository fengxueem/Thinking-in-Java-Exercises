import java.lang.reflect.*;
import java.lang.annotation.*;

public class ExerciseTwenty {
	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Usage: name of class");
			System.exit(0);
		}
		Class<?> c = null;
		try {
			c = Class.forName(args[0]);
		} catch(ClassNotFoundException e) {
			System.out.println("No such class: " + e);
		}
		System.out.println("Class: " + c);
		System.out.println("Class.getAnnotations(): ");
		if(c.getAnnotations().length == 0) System.out.println("none");
		for(Annotation a : c.getAnnotations())
			System.out.println(a);
		System.out.println("Class.getCanonicalName(): " + c.getCanonicalName());
		System.out.println("Class.getClasses(): ");
		if(c.getClasses().length == 0) System.out.println("none");
		for(Class cl : c.getClasses())
			System.out.println(cl);
		System.out.println("Class.getClassLoader(): " + c.getClassLoader());
		System.out.println("Class.getConstructors(): ");
		if(c.getConstructors().length == 0) System.out.println("none");
		for(Constructor ctor : c.getConstructors())
			System.out.println(ctor);
		System.out.println("Class.getDeclaredAnnotations(): ");
		if(c.getDeclaredAnnotations().length == 0) System.out.println("none");
		for(Annotation a : c.getDeclaredAnnotations())
			System.out.println(a);
		System.out.println("Class.getDeclaredClasses(): ");
		if(c.getDeclaredClasses().length == 0) System.out.println("none");
		for(Class cl : c.getDeclaredClasses())
			System.out.println(cl);
		System.out.println("Class.getDeclaredConstructors(): ");
		if(c.getDeclaredConstructors().length == 0) System.out.println("none");
		for(Constructor ctor : c.getDeclaredConstructors())
			System.out.println(ctor);
		System.out.println("Class.getDeclaredFields(): ");
		if(c.getDeclaredFields().length == 0) System.out.println("none");
		for(Field f : c.getDeclaredFields())
			System.out.println(f);
		System.out.println("Class.getDeclaredMethods(): ");
		if(c.getDeclaredMethods().length == 0) System.out.println("none");
		for(Method m : c.getDeclaredMethods())
			System.out.println(m);
		System.out.println("Class.getDeclaringClass(): " + c.getDeclaringClass());
		System.out.println("Class.getEnclosingConstructor(): " + c.getEnclosingConstructor());
		System.out.println("Class.getEnclosingMethod(): " + c.getEnclosingMethod());
		System.out.println("Class.getEnumConstants(): " + c.getEnumConstants());
		System.out.println("Class.getDeclaredFields(): ");
		if(c.getFields().length == 0) System.out.println("none");
		for(Field f : c.getFields())
			System.out.println(f);
		System.out.println("Class.getGenericInterfaces(): ");
		if(c.getGenericInterfaces().length == 0) System.out.println("none");
		for(Type t : c.getGenericInterfaces())
			System.out.println(t);
		System.out.println("Class.getGenericSuperclass(): " + c.getGenericSuperclass());
		System.out.println("Class.getInterfaces(): ");
		if(c.getInterfaces().length == 0) System.out.println("none");
		for(Class i : c.getInterfaces())
			System.out.println(i);
		System.out.println("Class.getMethods(): ");
		if(c.getMethods().length == 0) System.out.println("none");
		for(Method m : c.getMethods())
			System.out.println(m);
		System.out.println("Class.getModifiers(): " + c.getModifiers());
		System.out.println("Class.getName(): " + c.getName());
		System.out.println("Class.getPackage(): " + c.getPackage());
		System.out.println("Class.getProtectionDomain(): " + c.getProtectionDomain());
		System.out.println("Class.getSigners(): ");
		if(c.getSigners() == null)
			System.out.println(c.getSigners());
		if(c.getSigners() != null) {
			System.out.println();
			if(c.getSigners().length == 0) System.out.println("none");
			if(c.getSigners().length > 0) {
				for(Object s : c.getSigners()) 
					System.out.println(s);
			}
		}
		System.out.println("Class.getSimpleName(): " + c.getSimpleName());
		System.out.println("Class.getSuperclass(): " + c.getSuperclass());
		System.out.println("Class.getTypeParameters(): " + c.getTypeParameters());
		System.out.println("Class.isAnnotation(): " + c.isAnnotation());
		System.out.println("Class.isAnnotationPresent(Documented.class): " + c.isAnnotationPresent(Documented.class));
		System.out.println("Class.isAnonymousClass(): " + c.isAnonymousClass());
		System.out.println("Class.isArray(): " + c.isArray());
		System.out.println("Class.isAssignableFrom(Object.class): " + c.isAssignableFrom(Object.class));
		System.out.println("Class.isEnum(): " + c.isEnum());
		System.out.println("Class.isInstance(Object.class): " + c.isInstance(Object.class));
		System.out.println("Class.isInterface(): " + c.isInterface());
		System.out.println("Class.isLocalClass(): " + c.isLocalClass());
		System.out.println("Class.isMemberClass(): " + c.isMemberClass());
		System.out.println("Class.isPrimitive(): " + c.isPrimitive());
		System.out.println("Class.isSynthetic(): " + c.isSynthetic());		
	}
}