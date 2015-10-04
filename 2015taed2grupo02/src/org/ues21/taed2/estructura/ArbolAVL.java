package org.ues21.taed2.estructura;

import org.ues21.taed2.estructura.Nodo.NodoAVL;

/**
 * @author grupo02
 *
 */
public class ArbolAVL<T> extends ArbolBinarioDeBusqueda<T> {

	@Override
	public void insertar(T datos) {
		NodoAVL<T> nuevoNodo = new NodoAVL<>();
		nuevoNodo.setDatos(datos);
	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.AAVL;
	}
}
