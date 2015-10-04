package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoLD;

/**
 * @author grupo02
 *
 */
public class ListaDobleEnlazada<T> implements IEstructuraDeDatos<T> {

	@Override
	public T buscar(Integer codigoBusqueda) {
		NodoLD<T> nodoResultado = new NodoLD<>();
		return nodoResultado.getDatos();
	}

	@Override
	public void insertar(T datos) {
		// TODO Auto-generated method stub

	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.LISTA_DOB_ENLAZADA;
	}
}
