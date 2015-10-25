/**
 * 
 */
package org.ues21.taed2.principal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.ues21.taed2.estructura.ArbolAVL;
import org.ues21.taed2.estructura.ArbolBinarioDeBusqueda;
import org.ues21.taed2.estructura.IEstructuraDeDatos;
import org.ues21.taed2.estructura.IEstructuraDeDatos.Metricas;
import org.ues21.taed2.estructura.IEstructuraDeDatos.TipoEstructura;
import org.ues21.taed2.estructura.ListaDobleEnlazada;
import org.ues21.taed2.estructura.TablaHash;
import org.ues21.taed2.estructura.hash.FuncionHash;
import org.ues21.taed2.estructura.hash.FuncionHashModulo;
import org.ues21.taed2.estructura.hash.FuncionHashMultiplicacion;
import org.ues21.taed2.principal.GestorCSV.Registro;

/**
 * Clase que representa el menu principal y sus operaciones
 * 
 * @author grupo02
 *
 */
public class Principal {

	private static final String CONSULTA_CSV = "consulta.csv";
	private static final String CARGA_CSV = "datos.csv";
	private static Map<TipoEstructura, Metricas> mapaMetricas = new HashMap<>();
	private static Map<TipoEstructura, IEstructuraDeDatos<Registro>> estructurasMap = new HashMap<>();
	private static Map<String, FuncionHash<Registro>> funcionesHashMap = new HashMap<>();
	private static Map<String, TipoEstructura> tiposEstructurasMap = new HashMap<>();
	private static String pathDirectorio;
	private static boolean pathDirectorioCargado;
	private static boolean estructurasCargadas;

	/**
	 * Metodo principal que inicio a la ejecucion del programa
	 * @param args argumentos opcionales
	 */
	public static void main(String[] args) {
		inicializarFuncionesHash();
		inicializarTiposEstructuras();
		inicializarMetricas();

		Scanner scanner = null;
		int opcion;
		try {
			do {
				scanner = new Scanner(System.in);
				mostrarMenuPrincipal();
				opcion = scanner.nextInt();
				resolverOpcionMenuPrincipal(opcion, scanner);
			} while (opcion != 0);
		}catch(InputMismatchException ex) {
			System.err.println("Ingrese una opcion valida. Revise el texto ingresado");
		}
		catch (Exception ex) {
			System.err.println("Ocurriò un error al procesar la opcion seleccionada.");
			ex.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}
	
	/**
	 * Inicializa los contenedores de metricas por estructuras de datos
	 */
	private static void inicializarMetricas() {
		mapaMetricas.clear();
		mapaMetricas.put(TipoEstructura.LISTA_DOB_ENLAZADA, new Metricas());
		mapaMetricas.put(TipoEstructura.TABLA_HASH, new Metricas());
		mapaMetricas.put(TipoEstructura.AAVL, new Metricas());
		mapaMetricas.put(TipoEstructura.ABB, new Metricas());
	}

	private static void inicializarEstructuras(int tamañoTablaHash, FuncionHash<Registro> funcionHash) {
		estructurasMap.clear();
		estructurasMap.put(TipoEstructura.LISTA_DOB_ENLAZADA, new ListaDobleEnlazada<Registro>());
		estructurasMap.put(TipoEstructura.TABLA_HASH, new TablaHash<Registro>(tamañoTablaHash, funcionHash));
		estructurasMap.put(TipoEstructura.ABB, new ArbolBinarioDeBusqueda<Registro>());
		estructurasMap.put(TipoEstructura.AAVL, new ArbolAVL<Registro>());
	}
	
	/**
	 * Inicializa las funciones hash posibles de ser seleccionadas y asignadas a una tabla hash.
	 */
	private static void inicializarFuncionesHash() {
		funcionesHashMap.put("A", new FuncionHashModulo<Registro>());
		funcionesHashMap.put("B", new FuncionHashMultiplicacion<Registro>());
	}
	
	/**
	 * Inicializa los tipos de estructuras a ser seleccionadas y utilizadas para busqueda.
	 */
	private static void inicializarTiposEstructuras() {
		tiposEstructurasMap.put("A", TipoEstructura.LISTA_DOB_ENLAZADA);
		tiposEstructurasMap.put("B", TipoEstructura.TABLA_HASH);
		tiposEstructurasMap.put("C", TipoEstructura.ABB);
		tiposEstructurasMap.put("D", TipoEstructura.AAVL);
	}
	
	private static void mostrarMenuPrincipal() {
		System.out.println(" ");
		System.out.println("========================================================");
		System.out.println("|               MENU PRINCIPAL                         |");
		System.out.println("========================================================");
		System.out.println(" Path directorio: " + ((pathDirectorio == null || pathDirectorio.isEmpty()) ? "No cargado" : pathDirectorio));
		System.out.println("|                                                      |");
		System.out.println("|                                                      |");
		System.out.println("| 1. Cargar Path de directorio                         |");
		System.out.println("| 2. Cargar archivo CSV                                |");
		System.out.println("| 3. Buscar Valor en estructuras                       |");
		System.out.println("| 4. Consulta automatica de CSV                        |");
		System.out.println("| 5. Mostrar Reporte                                   |");
		System.out.println("| 6. Mostrar Arboles                                   |");
		System.out.println("| 0. SALIR                                             |");
		System.out.print("\nINGRESE UNA OPCION: ");
		System.out.println();
	}

	public static void resolverOpcionMenuPrincipal(int opcion, Scanner scanner) {
		switch (opcion) {
		case 1:
			menuPrincipalOpcion1(scanner);
			break;
		case 2:
			menuPrincipalOpcion2(scanner);
			break;
		case 3:
			menuPrincipalOpcion3(scanner);
			break;
		case 4:
			menuPrincipalOpcion4(scanner);
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

	private static void menuPrincipalOpcion1(Scanner scanner) {
		mostrarTituloSeparador("CARGA DE PATH DIRECTORIO");
		System.out.print("\nIngrese el path del directorio: ");
		do {
			pathDirectorio = scanner.nextLine();
		} while (pathDirectorio.trim().isEmpty());
		pathDirectorioCargado = true;
	}
	
	private static void menuPrincipalOpcion2(Scanner scanner) {
		if (pathDirectorioCargado) {
			String fullPathArchivo = pathDirectorio + File.separator + CARGA_CSV;
			mostrarTituloSeparador("CARGA DE DATOS  - Archivo: " + fullPathArchivo);
			System.out.print("\nIngrese el tamaño de tabla hash: \n");
			Integer tamañoTablaHash = null;
			do{
				tamañoTablaHash = scanner.nextInt();
			}while(tamañoTablaHash == null);
			
			System.out.println("Seleccione una funcion hash a utilizar");
			System.out.println("\n\t A - Funcion Modulo");
			System.out.println("\t B - Funcion Multiplicacion");
			System.out.print("\nSeleccione una funcion hash: \n");
			
			String seleccionFuncionHash = null;
			do{
				seleccionFuncionHash = scanner.nextLine();
			}while(seleccionFuncionHash.trim().isEmpty());
			
			FuncionHash<Registro> funcionHash = funcionesHashMap.get(seleccionFuncionHash);	
			
			if ( funcionHash == null) {
				System.err.println("\nEl tipo de funcion hash seleccionada no existe (" + seleccionFuncionHash + ")\n");
				System.out.println(" ");
				return ;
			}
			
			inicializarEstructuras(tamañoTablaHash, funcionHash);
			inicializarMetricas();

			Set<Entry<TipoEstructura,IEstructuraDeDatos<Registro>>> entrySet = estructurasMap.entrySet();
			for (Entry<TipoEstructura, IEstructuraDeDatos<Registro>> entry : entrySet) {
				IEstructuraDeDatos<Registro> estructuraDatos = entry.getValue();
				mapaMetricas.get(estructuraDatos.getTipoEstructura()).setTiempoInsercion(GestorCSV.cargar(estructuraDatos, fullPathArchivo));
			}

			estructurasCargadas = true;
		}else{
			System.out.println("\n** Debe cargar el path del directorio (Menu principal Opcion 1) **");
		}
	}

	private static void menuPrincipalOpcion3(Scanner scanner) {
		if (estructurasCargadas) {
			mostrarTituloSeparador("BUSCAR VALOR EN ESTRUCTURAS");
			System.out.println("\n\t A - Lista Doble Enlazada");
			System.out.println("\t B - Tabla Hash");
			System.out.println("\t C - Arbol Binario de Busqueda");
			System.out.println("\t D - Arbol AVL\n");
			System.out.print("\nSeleccione una estructura de datos: \n");
			String seleccionEstructuraDatos = null;
			do{
				seleccionEstructuraDatos = scanner.nextLine();
			}while(seleccionEstructuraDatos.trim().isEmpty());
			
			TipoEstructura tipoEstructura = tiposEstructurasMap.get(seleccionEstructuraDatos);
			
			if ( tipoEstructura == null) {
				System.err.println("\nEl tipo de estructura seleccionado es incorrecto (" + seleccionEstructuraDatos + ")\n");
				return ;
			}
			IEstructuraDeDatos<Registro> estructuraDeDatos = estructurasMap.get(tipoEstructura);
			
			System.out.println();
			System.out.print("Ingrese el codigo de busqueda: ");
			Integer codigoBuscado =  null;
			
			do{
				try{ 
					codigoBuscado = scanner.nextInt();
				} catch (InputMismatchException ex) {
					System.err.println("Debe ingresar un codigo de busqueda numerico. Reingrese el codigo de busqueda");
					scanner.nextLine();
					continue;
				}
			} while (codigoBuscado == null);
			
			Long tiempoInicio = System.currentTimeMillis();
			Registro registroResultado = estructuraDeDatos.buscar(new Registro(codigoBuscado, ""));
			Long tiempoTotalBusqueda = System.currentTimeMillis() - tiempoInicio;
			
			System.out.println();
			mostrarTituloSeparador("BUSQUEDA DE VALOR: " + codigoBuscado + "\t\tTipo ED: " + tipoEstructura);
	
			System.out.println("\t\t" + "Resultado ==>" + "\tCodigo: " + (registroResultado != null ? registroResultado.getCodigo() : "NO ENCONTRADO") + "\t Nombre: "
					+ (registroResultado != null ? registroResultado.getNombreCompleto() : "NO ENCONTRADO") + "\tTiempo (ms): " + tiempoTotalBusqueda);
		}
		else{
			System.out.println("\n** Debe cargar las estructuras de datos (Menu principal Opcion 2) **");
		}
	}

	private static void menuPrincipalOpcion4(Scanner scanner) {
		if (estructurasCargadas) {
			String fullPathArchivo = pathDirectorio + File.separator + CONSULTA_CSV;
			System.out.println();
			mostrarTituloSeparador("CONSULTA AUTOMATICA - archivo: " + fullPathArchivo);
			
			Set<Entry<TipoEstructura,IEstructuraDeDatos<Registro>>> entrySet = estructurasMap.entrySet();
			for (Entry<TipoEstructura, IEstructuraDeDatos<Registro>> entry : entrySet) {
				IEstructuraDeDatos<Registro> estructuraDatos = entry.getValue();
				mapaMetricas.get(estructuraDatos.getTipoEstructura()).setTiempoConsulta(GestorCSV.consultar(estructuraDatos, fullPathArchivo));
			}
		}
		else{
			System.out.println("\n** Debe cargar las estructuras de datos (Menu principal Opcion 2) **");
		}
	}

	private static void separador() {
		System.out
				.println("******************************************************************************************");
	}

	private static void menuPrincipalOpcion5() {
		if (estructurasCargadas) {
			System.out.println();
			mostrarTituloSeparador("REPORTE DE METRICAS DE ESTRUCTURA (tiempo en milisegundos)");
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
			System.out.println(
					TipoEstructura.AAVL + "\t\t\t\t" + mapaMetricas.get(TipoEstructura.AAVL).getTiempoInsercion()
							+ "\t\t\t\t" + mapaMetricas.get(TipoEstructura.AAVL).getTiempoConsulta());
			System.out.println(" ");
			TablaHash<Registro> tablaHash = (TablaHash<Registro>) estructurasMap.get(TipoEstructura.TABLA_HASH);
			System.out.println("Tamaño tabla hash: " + tablaHash.getTamaño() + " - Funcion hash: " + tablaHash.getFuncionHash().getNombre() + " - Cant. colisiones: " + tablaHash.getColisiones());
		}
		else{
			System.out.println("\n** Debe cargar las estructuras de datos (Menu principal Opcion 2) **");
		}
	}

	private static void menuPrincipalOpcion6() {
		if (estructurasCargadas) {
			System.out.println();
			mostrarTituloSeparador("REPRESENTACION DE ARBOL BINARIO DE BUSQUEDA");
			ArbolBinarioDeBusqueda<Registro> abb = (ArbolBinarioDeBusqueda<Registro>) estructurasMap
					.get(TipoEstructura.ABB);
			String abbFullPath = pathDirectorio + File.separator + "abb.txt";
			//grabarTexto(abbFullPath,abb.imprimirArbol());
			System.out.println("Se grabò exitosamente, el path del archivo es: " + abbFullPath);
			
			mostrarTituloSeparador("REPRESENTACION DE ARBOL AVL");
			ArbolBinarioDeBusqueda<Registro> avl = (ArbolBinarioDeBusqueda<Registro>) estructurasMap
					.get(TipoEstructura.AAVL);
			String avlFullPath = pathDirectorio + File.separator  + "avl.txt";
			//grabarTexto(avlFullPath, avl.imprimirArbol());
			System.out.println("Se grabò exitosamente, el path del archivo es: " + avlFullPath);
		}
		else{
			System.out.println("\n** Debe cargar las estructuras de datos (Menu principal Opcion 2) **");
		}
	}
	
	private static void mostrarTituloSeparador(String titulo) {
		System.out.println();
		separador();
		System.out.println(titulo);
		separador();
	}
	
	public static void grabarTexto(String fullPathArchivo, String contenido) {
		 try {
			Files.write(Paths.get(fullPathArchivo), contenido.getBytes());
		} catch (IOException e) {
			System.err.println("Ocurrio un error tratando de grabar archivo " + fullPathArchivo);
		}
	}
}
