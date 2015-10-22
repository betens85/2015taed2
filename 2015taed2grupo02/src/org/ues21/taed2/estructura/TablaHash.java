package org.ues21.taed2.estructura;

import org.ues21.taed2.principal.GestorCSV.Codeable;

/**
 * Clase que representa una estructura de datos de tipo tabla hash
 * @author grupo02
 */
public class TablaHash<T extends Comparable<T> & Codeable> implements IEstructuraDeDatos<T> {

	private int tamaño;
	private ListaSimple<T> [] items;

	public TablaHash(int tamaño) {
		this.tamaño = tamaño;
		this.items = new ListaSimple[tamaño];
		for(int i= 0; i< tamaño; i++) {
			items[i] = new ListaSimple<>();
		}
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
		items[h].insertar(datos);
	}
	
	private int h(T datos) {
		Integer k = datos.getCodigo();
		return k % this.tamaño;
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.TABLA_HASH;
	}

	@Override
	public boolean estaVacia() {
		return false;
	}
}
