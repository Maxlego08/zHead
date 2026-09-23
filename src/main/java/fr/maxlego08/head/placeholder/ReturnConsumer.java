package fr.maxlego08.head.placeholder;
@FunctionalInterface
public interface ReturnConsumer<T, G> {

	G accept(T t);
	
}