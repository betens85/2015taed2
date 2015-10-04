/**
 * 
 */
package org.ues21.taed2.principal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.ues21.taed2.estructura.ArbolAVL;
import org.ues21.taed2.estructura.ArbolBinarioDeBusqueda;
import org.ues21.taed2.estructura.IEstructuraDeDatos;
import org.ues21.taed2.estructura.IEstructuraDeDatos.Metricas;
import org.ues21.taed2.estructura.IEstructuraDeDatos.TipoEstructura;
import org.ues21.taed2.estructura.ListaDobleEnlazada;
import org.ues21.taed2.estructura.TablaHash;

/**
 * Clase que representa el menu principal y sus operaciones
 * 
 * @author grupo02
 *
 */
public class Principal {

	private static Map<TipoEstructura, Metricas> mapaMetricas = new HashMap<>();
	private static List<IEstructuraDeDatos> estructurasDeDatos = new ArrayList<>();
	private static String pathDirectorio = "/home/norberto/taed2";

	public static void main(String[] args) {
		inicializarMetricas();
		inicializarEstructuras();
		Scanner scanner = null;
		int opcion;
		try {
			do {
				scanner = new Scanner(System.in);
				mostrarMenuPrincipal();
				opcion = scanner.nextInt();
				resolverOpcionMenuPrincipal(opcion);
			} while (opcion != 0);
		} catch (Exception ex) {
			System.err.println("Ocurriò un error al procesar la opcion seleccionada.");
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	private static void mostrarMenuPrincipal() {
		System.out.println(" ");
		System.out.println("========================================================");
		System.out.println("|               MENU PRINCIPAL                         |");
		System.out.println("========================================================");
		System.out.println("|                                                      |");
		System.out.println("| 1. Cargar Path de directorio                         |");
		System.out.println("| 2. Cargar archivo CSV                                |");
		System.out.println("| 3. Buscar Valor en estructuras                       |");
		System.out.println("| 4. Consulta automatica de CSV                        |");
		System.out.println("| 5. Mostrar Reporte                                   |");
		System.out.println("| 6. Mostrar Arboles                                   |");
		System.out.println("| 0. SALIR                                             |");
		System.out.print("\nINGRESE UNA OPCION: ");
	}

	public static void resolverOpcionMenuPrincipal(int opcion) {
		switch (opcion) {
		case 1:
			menuPrincipalOpcion1();
			break;
		case 2:
			menuPrincipalOpcion2();
			break;
		case 3:
			// TODO harcoded for testing purposal
			menuPrincipalOpcion3(TipoEstructura.ABB);
			break;
		case 4:
			menuPrincipalOpcion4();
			break;
		case 5:
			menuPrincipalOpcion5();
			break;
		case 6:
			menuPrincipalOpcion6();
			break;

		default:
			break;
		}
	}

	private static void inicializarMetricas() {
		mapaMetricas.clear();
		mapaMetricas.put(TipoEstructura.LISTA_DOB_ENLAZADA, new Metricas());
		mapaMetricas.put(TipoEstructura.TABLA_HASH, new Metricas());
		mapaMetricas.put(TipoEstructura.AAVL, new Metricas());
		mapaMetricas.put(TipoEstructura.ABB, new Metricas());
	}

	private static void inicializarEstructuras() {
		estructurasDeDatos.clear();
		estructurasDeDatos.add(new ListaDobleEnlazada());
		estructurasDeDatos.add(new TablaHash());
		estructurasDeDatos.add(new ArbolBinarioDeBusqueda());
		estructurasDeDatos.add(new ArbolAVL());
	}

	private static void menuPrincipalOpcion1() {
		// TODO preguntar por path directorio y setear en variable
		// 'pathDirectorio'
	}

	private static void menuPrincipalOpcion2() {
		// TODO controlar que este cargado path de directorio, sino esta cargado
		// tirar excepcion o salir
		// TODO datos.csv harcoded, deberia ser cargado por la consola
		String fullPathArchivo = pathDirectorio + File.separator + "datos.csv";
		separador();
		System.out.println("CARGA DE DATOS  - Archivo: " + fullPathArchivo);
		separador();

		inicializarEstructuras();
		inicializarMetricas();

		for (IEstructuraDeDatos estructuraDatos : estructurasDeDatos) {
			Long tiempoCarga = GestorCSV.cargar(estructuraDatos, fullPathArchivo);
			mapaMetricas.get(estructuraDatos.getTipoEstructura()).setTiempoInsercion(tiempoCarga);
		}
	}

	private static void menuPrincipalOpcion3(TipoEstructura tipoEstructura) {
		// TODO hacer submenu que pregunte tipo estructura y dps el codigo a
		// buscar
		// codigo harcoded para prueba ==> 1

		for (IEstructuraDeDatos estructuraDeDatos : estructurasDeDatos) {
			// TODO este
			if (tipoEstructura.equals(estructuraDeDatos.getTipoEstructura())) {
				String resultadoBusqueda = estructuraDeDatos.buscar(1);
				System.out.println();
				separador();
				System.out.println("BUSQUEDA DE VALOR: " + 1);
				separador();
				System.out.println("\t\t" + "Resultado: " + resultadoBusqueda);
				break;
			}
		}
	}

	private static void menuPrincipalOpcion4() {
		// TODO chequear si el directorio fue cargado
		// TODO preguntar como se llama el archivo de consulta
		String fullPathArchivo = pathDirectorio + File.separator + "consulta.csv";
		System.out.println();
		separador();
		System.out.println("CONSULTA AUTOMATICA - archivo: " + fullPathArchivo);
		separador();
		for (IEstructuraDeDatos estructuraDeDatos : estructurasDeDatos) {
			Long tiempoConsulta = GestorCSV.consultar(estructuraDeDatos, fullPathArchivo);
			mapaMetricas.get(estructuraDeDatos.getTipoEstructura()).setTiempoConsulta(tiempoConsulta);
		}
	}

	private static void separador() {
		System.out
				.println("******************************************************************************************");
	}

	private static void menuPrincipalOpcion5() {
		separador();
		System.out.println("REPORTE DE METRICAS DE ESTRUCTURA ");
		separador();
		System.out.println();
		System.out.println("Estructura                       Tiempo Insercion               Tiempo Consulta\n");
		System.out.println(TipoEstructura.LISTA_DOB_ENLAZADA + "\t\t\t"
				+ mapaMetricas.get(TipoEstructura.LISTA_DOB_ENLAZADA).getTiempoInsercion() + "\t\t\t\t"
				+ mapaMetricas.get(TipoEstructura.LISTA_DOB_ENLAZADA).getTiempoConsulta());
		System.out.println(TipoEstructura.TABLA_HASH + "\t\t\t\t"
				+ mapaMetricas.get(TipoEstructura.TABLA_HASH).getTiempoInsercion() + "\t\t\t\t"
				+ mapaMetricas.get(TipoEstructura.TABLA_HASH).getTiempoConsulta());
		System.out.println(TipoEstructura.ABB + "\t\t" + mapaMetricas.get(TipoEstructura.ABB).getTiempoInsercion()
				+ "\t\t\t\t" + mapaMetricas.get(TipoEstructura.ABB).getTiempoConsulta());
		System.out.println(TipoEstructura.AAVL + "\t\t\t\t" + mapaMetricas.get(TipoEstructura.AAVL).getTiempoInsercion()
				+ "\t\t\t\t" + mapaMetricas.get(TipoEstructura.AAVL).getTiempoConsulta());
	}

	private static void menuPrincipalOpcion6() {
		System.out.println(" ");
		separador();
		System.out.println("REPRESENTACION DE ARBOL BINARIO DE BUSQUEDA");
		separador();
		// TODO imprimir arbol binario de busqueda
		System.out.println(" ");
		separador();
		System.out.println("REPRESENTACION DE ARBOL AVL");
		separador();
		// TODO imprimir arbol avl
	}
}
