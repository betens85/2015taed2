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
		for(int i= 0; i< tamaño; i++) {
			items[i] = new ListaSimple<>();
		}
		this.funcionHash = funcionHash;
	}
	
	@Override
	public T buscar(T datos) {
		int h = h(datos);
		T resultado = items[h].buscar(datos);
		return resultado;
	}

	@Override
	public void insertar(T datos) {
		int h = h(datos);		
		if (items[h].getCantidadNodos() > 0) {
			colisiones++;
		}
		
		items[h].insertar(datos);
		cantidadNodos++;
	}
	
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
