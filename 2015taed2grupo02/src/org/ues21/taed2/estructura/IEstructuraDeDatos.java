package org.ues21.taed2.estructura;

/**
 * Representa la interfaz de una estructura de datos
 * 
 * @author grupo02
 *
 */
public interface IEstructuraDeDatos {

	/**
	 * Permite la busqueda de informacion a partir de un codigo de busqueda
	 * 
	 * @param codigoBusqueda
	 *            el codigo de busqueda
	 * @return el resultado de la busqueda
	 */
	String buscar(Integer codigoBusqueda);

	/**
	 * Permite la insercion de informacion en una estructura de datos
	 * 
	 * @param codigo
	 *            el codigo a ser insertado
	 * @param texto
	 *            el texto a ser insertado
	 */
	void insertar(Integer codigo, String texto);

	/**
	 * Devuelve el tipo de estructura
	 * 
	 * @return el tipo de estructura
	 */
	TipoEstructura getTipoEstructura();

	/**
	 * Representa un enumeracion de los tipos de estructuras
	 * 
	 * @author grupo02
	 *
	 */
	public static enum TipoEstructura {
		LISTA_DOB_ENLAZADA("Lista Doble Enlazada"), TABLA_HASH("Tabla Hash"), ABB("Arbol Binario de Busqueda"), AAVL(
				"Arbol AVL");

		private String descripcion;

		TipoEstructura(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getDescripcion() {
			return descripcion;
		}

		@Override
		public String toString() {
			return this.descripcion;
		}
	}

	/**
	 * Representa las metricas para un tipo de estructura
	 * @author grupo02
	 *
	 */
	public static class Metricas {
		private Long tiempoInsercion = 0L;
		private Long tiempoConsulta = 0L;

		public Long getTiempoInsercion() {
			return tiempoInsercion;
		}

		public void setTiempoInsercion(Long tiempoInsercion) {
			this.tiempoInsercion = tiempoInsercion;
		}

		public Long getTiempoConsulta() {
			return tiempoConsulta;
		}

		public void setTiempoConsulta(Long tiempoConsulta) {
			this.tiempoConsulta = tiempoConsulta;
		}

	}

}
