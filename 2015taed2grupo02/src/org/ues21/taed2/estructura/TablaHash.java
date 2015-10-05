package org.ues21.taed2.estructura;

/**
 * @author grupo02
 *
 */
public class TablaHash<T extends Comparable<T>> implements IEstructuraDeDatos<T> {

	@Override
	public T buscar(T datos) {
		return null;
	}

	@Override
	public void insertar(T datos) {
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
