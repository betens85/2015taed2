package org.ues21.taed2.principal;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.ues21.taed2.estructura.IEstructuraDeDatos;

/**
 * Clase que permite la carga de un archivo csv en una estructura de datos o una
 * consulta automatica
 * 
 * @author grupo02
 *
 */
public final class GestorCSV {

	private GestorCSV() {
	}

	/**
	 * Metodo que permite la carga de un archivo csv en una estructura de datos
	 * 
	 * @param estructuraDeDatos
	 *            la estructura de datos a ser cargada. Esta puede ser cualquier clase que implemente la {@link IEstructuraDeDatos}
	 * @param fullPathArchivo
	 *            el path completo al archivo que sera cargado
	 * @return el tiempo en milisegundos de toda la carga
	 */
	public static Long cargar(IEstructuraDeDatos<Registro> estructuraDeDatos, String fullPathArchivo) {
		Long tiempoTotalInsercion = null;
		Long tiempoInicio = null; 
		// se carga archivo de disco
		Path file = Paths.get(fullPathArchivo);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			String[] campos;
			tiempoInicio = System.currentTimeMillis();
			// se leer archivo cargado de disco linea a linea
			while (scanner.hasNextLine()) {
				campos = scanner.nextLine().split(",");
				Registro registroAInsertar = new Registro(new Integer(campos[0]), campos[1]);
				//se inserta un registro en la ED
				estructuraDeDatos.insertar(registroAInsertar);
			}
			System.out.println("\t" + estructuraDeDatos.getTipoEstructura() + " =====> carga exitosa (" + estructuraDeDatos.getCantidadNodos() +" registros)");
		}
		catch (IOException ex) {
			System.err.println("Ocurrio una excepcion al procesar el archivo: " + ex.getMessage());
		}
		catch (Exception ex) {
			System.err.println("Ocurrio una excepcion al cargar las estructuras de datos: " + ex.getMessage());
		}finally {
			if (scanner != null) {
				scanner.close();
			}
			// se toma tiempo total de insercion
			tiempoTotalInsercion = System.currentTimeMillis() - tiempoInicio;
		}
		
		return tiempoTotalInsercion;
	}

	/**
	 * Metodo que permite la consulta automatica de un archivo csv en una
	 * estructura de datos
	 * 
	 * @param estructuraDeDatos
	 *            la estructura de datos a ser consultada. Esta puede ser cualquier clase que implemente la {@link IEstructuraDeDatos}
	 * @param fullPathArchivo
	 *            el path completo al archivo a partir del cual se generaran
	 *            consultas
	 * @return el tiempo en milisegundos de toda la consulta
	 */
	public static Long consultar(IEstructuraDeDatos<Registro> estructuraDeDatos, String fullPathArchivo) {
		Long tiempoTotalConsulta = null;
		Long tiempoInicio = null;
		//se carga archivo de disco
		Path file = Paths.get(fullPathArchivo);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			String[] campos;
			StringBuilder sbResultados = new StringBuilder();
			// se toma tiempo de inicio
			tiempoInicio = System.currentTimeMillis();
			
			// se lee archivo cargado de disco, linea a linea
			while (scanner.hasNextLine()) {
				campos = scanner.nextLine().split(",");
				// se crea un filtro de busqueda, usando un objeto como wrapper
				Registro filtroDeBusqueda = new Registro(new Integer(campos[0]), null);
				//se busca dentro de la estructura de datos
				Registro resultado = estructuraDeDatos.buscar(filtroDeBusqueda);
				if (resultado != null) {
					sbResultados.append("\n\tCodigo: " + resultado.getCodigo() + " - Resultado: " + resultado.getNombreCompleto());
				}
				else{
					sbResultados.append("\n\tCodigo: " + filtroDeBusqueda.getCodigo() + " - Resultado: NO ENCONTRADO");
				}
			}
			
			//se muestran los resultados por consola
			if (sbResultados.length() > 0) {
				System.out.println();
				System.out.println(estructuraDeDatos.getTipoEstructura());
				System.out.println(sbResultados);
			}
		} catch (IOException ex) {
			System.err.println("Ocurrio una excepcion al procesar el archivo: " + ex.getMessage());
		} finally {
			if (scanner != null) {
				scanner.close();
			}
			// se toma tiempo total de consulta
			tiempoTotalConsulta = System.currentTimeMillis() - tiempoInicio;
		}
		return tiempoTotalConsulta;
	}
	
	/**
	 * Representa a un registro leido desde un archivo cvs con dos campos
	 * @author grupo02
	 *
	 */
	public static class Registro implements Comparable<Registro>, Codeable {
		private Integer codigo;
		private String nombreCompleto;
		
		public Registro(Integer codigo, String nombreCompleto) {
			this.codigo = codigo;
			this.nombreCompleto = nombreCompleto;
		}
		public Integer getCodigo() {
			return codigo;
		}
		public String getNombreCompleto() {
			return nombreCompleto;
		}
		public void setNombreCompleto(String nombreCompleto) {
			this.nombreCompleto = nombreCompleto;
		}
		
		@Override
		public String toString() {
			return codigo.toString();
		}
		@Override
		public int compareTo(Registro r) {
			return codigo.compareTo(r.getCodigo());
		}
	}
	
	public static interface Codeable {
		Integer getCodigo();
	}
}
