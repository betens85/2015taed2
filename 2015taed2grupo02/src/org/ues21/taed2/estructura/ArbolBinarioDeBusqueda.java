package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoArbol;

/**
 * @author grupo02
 *
 */
public class ArbolBinarioDeBusqueda<T> implements IEstructuraDeDatos<T> {

	@Override
	public T buscar(Integer codigoBusqueda) {
		NodoArbol<T> resultado = new NodoArbol<>();
		return resultado.getDatos();
	}

	@Override
	public void insertar(T datos) {
		NodoArbol<T> nuevoNodo = new NodoArbol<>();
		nuevoNodo.setDatos(datos);
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.ABB;
	}
}


