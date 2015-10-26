package org.ues21.taed2.principal;

import java.io.File;
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

	private static final String CONSULTA_CSV = "consulta.csv";// nombre por default de archivo de consulta
	private static final String CARGA_CSV = "datos.csv"; // nombre por default de archivo de carga
	private static Map<TipoEstructura, Metricas> mapaMetricas = new HashMap<>(); // mapa para guardar metricas para cada estructura de datos
	private static Map<TipoEstructura, IEstructuraDeDatos<Registro>> mapaEstructuraDatos = new HashMap<>(); // mapa estructuras de datos a ser cargadas y consultadas
	private static Map<String, FuncionHash<Registro>> mapaFuncionesHash = new HashMap<>(); //mapa de funciones hash a ser elegidas en ejecucion para una tabla hash
	private static Map<String, TipoEstructura> mapaTipoEstructura = new HashMap<>(); // mapa tipo estructura
	private static String pathDirectorio;//guarda el path de directorio
	private static boolean pathDirectorioCargado;// flag para saber si se cargo o no el directorio
	private static boolean estructurasCargadas;// flag para saber si se cargaron o no las estructuras de datos

	/**
	 * Metodo principal que inicio a la ejecucion del programa
	 * @param args argumentos opcionales
	 */
	public static void main(String[] args) {
		// precarga de mapas de datos
		inicializarFuncionesHash();
		inicializarTiposEstructuras();
		inicializarMetricas();

		Scanner scanner = null;
		int opcion = 0;
		try {
			do {
				scanner = new Scanner(System.in);
				mostrarMenuPrincipal();
				if(scanner.hasNextInt()){
					opcion = scanner.nextInt();
					resolverOpcionMenuPrincipal(opcion, scanner);
				}else{
					System.err.println("* Ingrese un numero entero. Revise el texto ingresado");
				}
				
			} while (opcion != 9);
		}catch(InputMismatchException ex) {
			System.err.println("* Ingrese un numero entero. Revise el texto ingresado");
		}
		catch (Exception ex) {
			System.err.println("* Ocurriò un error al procesar la opcion seleccionada.");
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
		mapaEstructuraDatos.clear();
		mapaEstructuraDatos.put(TipoEstructura.LISTA_DOB_ENLAZADA, new ListaDobleEnlazada<Registro>());
		mapaEstructuraDatos.put(TipoEstructura.TABLA_HASH, new TablaHash<Registro>(tamañoTablaHash, funcionHash));
		mapaEstructuraDatos.put(TipoEstructura.ABB, new ArbolBinarioDeBusqueda<Registro>());
		mapaEstructuraDatos.put(TipoEstructura.AAVL, new ArbolAVL<Registro>());
	}
	
	/**
	 * Inicializa las funciones hash posibles de ser seleccionadas y asignadas a una tabla hash.
	 */
	private static void inicializarFuncionesHash() {
		mapaFuncionesHash.put("A", new FuncionHashModulo<Registro>());
		mapaFuncionesHash.put("B", new FuncionHashMultiplicacion<Registro>());
	}
	
	/**
	 * Inicializa los tipos de estructuras a ser seleccionadas y utilizadas para busqueda.
	 */
	private static void inicializarTiposEstructuras() {
		mapaTipoEstructura.put("A", TipoEstructura.LISTA_DOB_ENLAZADA);
		mapaTipoEstructura.put("B", TipoEstructura.TABLA_HASH);
		mapaTipoEstructura.put("C", TipoEstructura.ABB);
		mapaTipoEstructura.put("D", TipoEstructura.AAVL);
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
		System.out.println("| 9. SALIR                                             |");
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
			if(new File(fullPathArchivo).exists()){//se valida si existe archivo
			
				mostrarTituloSeparador("CARGA DE DATOS  - Archivo: " + fullPathArchivo);
				System.out.print("\n- Ingrese el tamaño de tabla hash: \n");
				Integer tamañoTablaHash = null;
				do{
					if (!scanner.hasNextInt()) {
						System.err.println("* El tamaño de tabla hash debe ser numerico.");
						return;
					}
					tamañoTablaHash = scanner.nextInt();
				}while(tamañoTablaHash == null);
				
				System.out.println("- Seleccione una funcion hash a utilizar");
				System.out.println("\n\t A - Funcion Modulo");
				System.out.println("\t B - Funcion Multiplicacion");
				System.out.print("\nSeleccione una funcion hash: \n");
				
				String seleccionDeFuncionHash = null;
				do{
					seleccionDeFuncionHash = scanner.nextLine();
				}while(seleccionDeFuncionHash.trim().isEmpty());
				
				FuncionHash<Registro> funcionHashElegida = mapaFuncionesHash.get(seleccionDeFuncionHash);	
				
				if (funcionHashElegida == null) {
					System.err.println("\nEl tipo de funcion hash seleccionada no existe (" + seleccionDeFuncionHash + ")\n");
					System.out.println(" ");
					return ;
				}
				
				inicializarEstructuras(tamañoTablaHash, funcionHashElegida);
				inicializarMetricas();
	
				// se obtiene un set de entradas clave/valor de las ED a ser cargadas (mapa de ED vacias)
				Set<Entry<TipoEstructura,IEstructuraDeDatos<Registro>>> entradasED = mapaEstructuraDatos.entrySet();
				
				for (Entry<TipoEstructura, IEstructuraDeDatos<Registro>> entrada : entradasED) {
					IEstructuraDeDatos<Registro> estructuraDatos = entrada.getValue(); // obtenemos la estructura de datos
					Metricas metricas = mapaMetricas.get(estructuraDatos.getTipoEstructura()); // obtenemos metricas para tipo de ED
					Long tiempoCargaEstructuraDatos = GestorCSV.cargar(estructuraDatos, fullPathArchivo); // se carga el archivo en la ED y se obtiene el tiempo total
					metricas.setTiempoInsercion(tiempoCargaEstructuraDatos); // se guarda la metrica tiempo de insercion para el tipo de ED
				}
	
				estructurasCargadas = true; // se cargaron las ED
			}
			else
			{
				System.out.println("\n** El archivo '" + CARGA_CSV + "' no existe en el directorio '" + pathDirectorio +"' **");
			}
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
			
			//se obtiene tipo ED, de acuerdo a opcion ingresada por consola
			TipoEstructura tipoEstructura = mapaTipoEstructura.get(seleccionEstructuraDatos);
			
			if (tipoEstructura == null) {
				System.err.println("\nEl tipo de estructura seleccionado es incorrecto (" + seleccionEstructuraDatos + ")\n");
				return ;
			}
			//una vez que tenemos el tipo de ED, se obtiene la ED para buscar dentro de ella
			IEstructuraDeDatos<Registro> estructuraDeDatos = mapaEstructuraDatos.get(tipoEstructura);
			
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
			
			// se obtiene el resultado pasandole al metodo 'buscar' de la ED el codigo ingresado por consola
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
			
			//se obtiene un set de entradas clave/valor ('tipo ED' / ED)
			Set<Entry<TipoEstructura,IEstructuraDeDatos<Registro>>> entradasED = mapaEstructuraDatos.entrySet();
			
			for (Entry<TipoEstructura, IEstructuraDeDatos<Registro>> entrada : entradasED) {
				IEstructuraDeDatos<Registro> estructuraDatos = entrada.getValue();// se obtiene la ED sobre la que se realizaran las consultas
				TipoEstructura tipoEstructuraActual = estructuraDatos.getTipoEstructura();// se obtiene el tipo de ED 
				Metricas metricasParaTipoED = mapaMetricas.get(tipoEstructuraActual); // metricas a ser completadas para el tipo de ED actual
				Long tiempoConsultaPorED = GestorCSV.consultar(estructuraDatos, fullPathArchivo);// se realizan las consultas sobre la ED
				metricasParaTipoED.setTiempoConsulta(tiempoConsultaPorED);// se graba la metrica de tiempo de consulta para el tipo de ED actual
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
			mostrarTituloSeparador("REPORTE DE METRICAS DE ESTRUCTURA (tiempo en milisegundos) - " + mapaEstructuraDatos.get(TipoEstructura.AAVL).getCantidadNodos() + " registros" );
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
			TablaHash<Registro> tablaHash = (TablaHash<Registro>) mapaEstructuraDatos.get(TipoEstructura.TABLA_HASH);
			System.out.println("*  Tamaño tabla hash: " + tablaHash.getTamaño() + " - Funcion hash: " + tablaHash.getFuncionHash().getNombre() + " - Cant. colisiones: " + tablaHash.getColisiones());
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
}
