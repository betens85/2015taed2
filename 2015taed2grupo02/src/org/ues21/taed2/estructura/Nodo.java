package org.ues21.taed2.estructura;

/**
 * Clase que define la estructura basica de un nodo
 * @author grupo02
 *
 * @param <T> representacion parametrizada generica de los datos que contendra
 */
public abstract class Nodo<T> {

	private T datos;
	
	public Nodo(T datos) {
		this.datos = datos;
	}
	
	public T getDatos() {
		return datos;
	}
	
	public void setDatos(T datos) {
		this.datos = datos;
	}

	/**
	 * Clase que representa un nodo de un arbol
	 * @author grupo02
	 *
	 */
	public static class NodoArbol<T> extends Nodo<T> {
		
		private NodoArbol<T> izquierdo;
		private NodoArbol<T> derecho;
		private int factorEquilibrio;
		
		public NodoArbol(T datos) {
			super(datos);
		}

		public NodoArbol(T datos, int factorEquilibrio, NodoArbol<T> izquierdo, NodoArbol<T> derecho) {
			super(datos);
			this.factorEquilibrio = factorEquilibrio;
			this.izquierdo = izquierdo;
			this.derecho = derecho;
		}

		public int getFactorEquilibrio() {
			return factorEquilibrio;
		}
		
		public void setFactorEquilibrio(int factorEquilibrio) {
			this.factorEquilibrio = factorEquilibrio;
		}

		public NodoArbol<T> getIzquierdo() {
			return izquierdo;
		}

		public void setIzquierdo(NodoArbol<T> izquierdo) {
			this.izquierdo = izquierdo;
		}

		public NodoArbol<T> getDerecho() {
			return derecho;
		}

		public void setDerecho(NodoArbol<T> derecho) {
			this.derecho = derecho;
		}
	}
	
	/**
	 * Clase que representa un nodo de una lista doble enlazada
	 * @author grupo02
	 */
	public static class NodoLD<T> extends NodoLS<T> {

		public NodoLD(T datos) {
			super(datos);
		}

		private NodoLD<T> anterior;

		public NodoLD<T> getAnterior() {
			return anterior;
		}

		public void setAnterior(NodoLD<T> anterior) {
			this.anterior = anterior;
		}
	}
	
	/**
	 * Clase que representa un nodo de una lista doble enlazada
	 * @author grupo02
	 */
	public static class NodoLS<T> extends Nodo<T> {

		public NodoLS(T datos) {
			super(datos);
		}

		private NodoLD<T> siguiente;

		public NodoLD<T> getSiguiente() {
			return siguiente;
		}

		public void setSiguiente(NodoLD<T> siguiente) {
			this.siguiente = siguiente;
		}
	}
	
	/**
	 * Clase que representa un nodo de una tabla hash
	 * @author grupo02
	 */
	public class NodoTH<T> extends Nodo<T> {

		public NodoTH(T datos) {
			super(datos);
		}
	}
}
