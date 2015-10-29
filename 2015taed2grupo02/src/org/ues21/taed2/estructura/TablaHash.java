package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.hash.FuncionHash;
import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa una estructura de datos de tipo tabla hash
 * 
 * @author grupo02
 * 
 * @param <T>
 *            representa la clase parametrizada
 */
public class TablaHash<T extends Comparable<T> & Codeable> implements IEstructuraDeDatos<T> {

	private int tamaño;
	private ListaSimple<T> [] items;
	private FuncionHash<T> funcionHash;
	private int colisiones;
	private int cantidadNodos;

	public TablaHash(int tamaño, FuncionHash<T> funcionHash) {
		this.tamaño = tamaño;
		this.items = new ListaSimple[tamaño];
		//se inicializa el array de tamaño pasado por parametro
		for(int i= 0; i< tamaño; i++) {
			items[i] = new ListaSimple<>();
		}
		//define que funcion hash se utilizara
		this.funcionHash = funcionHash;
	}
	
	@Override
	public T buscar(T datos) {
		//a partir de los datos se busca la posicion h
		int h = h(datos);
		//obtenemos el resultado en la posicion h
		T resultado = items[h].buscar(datos);
		return resultado;
	}

	@Override
	public void insertar(T datos) {
		//a partir de los datos se busca la posicion h
		int h = h(datos);	
		//si ya existe al menos un nodo en la posicion h, contamos una colision
		if (items[h].getCantidadNodos() > 0) {
			colisiones++;
		}
		//se insertan los datos en la posicion h
		items[h].insertar(datos);
		cantidadNodos++;
	}
	
	/**
	 * Metodo que calcula la posicion de la tabla tanto para insercion o borrado. Se utiliza el
	 * patron de diseño 'strategy' a traves del atributo 'funcionHash'
	 * @param datos los datos que contienen el codigo
	 * @return la posicion dentro del array interno
	 */
	private int h(T datos) {
		return this.funcionHash.calcularHash(this, datos);
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.TABLA_HASH;
	}

	@Override
	public boolean estaVacia() {
		return false;
	}
	
	public int getTamaño() {
		return tamaño;
	}
	
	public void setFuncionHash(FuncionHash<T> funcionHash) {
		this.funcionHash = funcionHash;
	}
	
	public FuncionHash<T> getFuncionHash() {
		return funcionHash;
	}
	
	public int getColisiones() {
		return colisiones;
	}
	
	@Override
	public int getCantidadNodos() {
		return cantidadNodos;
	}
}
