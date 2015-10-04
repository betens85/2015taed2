package org.ues21.taed2.estructura;

/**
 * @author grupo02
 *
 */
public class ListaDobleEnlazada implements IEstructuraDeDatos {

	@Override
	public String buscar(Integer codigoBusqueda) {
		return "dummy result";
	}

	@Override
	public void insertar(Integer codigo, String texto) {
		// TODO Auto-generated method stub

	}

	@Override
	public TipoEstructura getTipoEstructura() {
		return TipoEstructura.LISTA_DOB_ENLAZADA;
	}
	
	/**
	 * Representa un nodo para una lista doble enlazada
	 * 
	 * @author grupo02
	 *
	 */
	private static class NodoLD {

	}

}
