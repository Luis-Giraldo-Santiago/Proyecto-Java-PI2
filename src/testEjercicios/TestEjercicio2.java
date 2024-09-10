package testEjercicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import ejercicios.Ejercicio2;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataCurveFitting;
import utils.GraficosAjuste;
import utils.Resultados;
import utils.TipoAjuste;
import utils.FicherosListas.PropiedadesListas;

public class TestEjercicio2 {
	
	public static void main(String[] args) {
		generaFicherosTiempoEjecucion();
		muestraGraficas();
	}
	// Parámetros de generación de las listas
	private static Integer sizeMin = 1; // tamaño mínimo de lista	
	private static Integer sizeMax = 200; // tamaño máximo de lista
	private static Integer numSizes = 25; // número de tamaños de listas
	private static Integer minValue = 0	; 
	private static Integer maxValue = 50;
	private static Integer numListPerSize = 1; // número de listas por cada tamaño  (UTIL???) 
	private static Integer numCasesPerList = 1; // número de casos (elementos a buscar) por cada lista 
	private static Random rr = new Random(System.nanoTime()); // para inicializarlo una sola vez y compartirlo con los métodos que lo requieran

	private static List<Integer> umbrales = List.of(4, 25, 100, 500);
	// Parámetros de medición
	private static Integer numMediciones = 5; // número de mediciones de tiempo de cada caso (número de experimentos)
	private static Integer numIter = 50; // número de iteraciones para cada medición de tiempo
	private static Integer numIterWarmup = 1000; // número de iteraciones para warmup	
	private static List<Trio<BiFunction<List<Integer>, Integer, Void>,TipoAjuste, String>> metodos = 
			List.of(Trio.of(Ejercicio2::quickSort,TipoAjuste.NLOGN_0,"QuickSort4"),
					Trio.of(Ejercicio2::quickSort,TipoAjuste.NLOGN_0,"QuickSort25"),
					Trio.of(Ejercicio2::quickSort,TipoAjuste.NLOGN_0,"QuickSort100"),
					Trio.of(Ejercicio2::quickSort,TipoAjuste.NLOGN_0,"QuickSort500"));
		
	public static void generaFicherosTiempoEjecucion() {
		for (Trio<BiFunction<List<Integer>, Integer, Void>,TipoAjuste, String> metodo: metodos) { 
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv", metodo.third());
			testTiemposEjecucion(sizeMin, sizeMax, umbrales.get(metodos.indexOf(metodo)),metodo.first(),ficheroSalida);
		}
		
	}
	
	
	
	private static void testTiemposEjecucion(Integer nMin, Integer nMax, Integer umbral,
			BiFunction<List<Integer>, Integer, Void> funcion, String fichero) {
		Map<Problema, Double> tiempos = new HashMap<>();
		for(int i = 0; i<numMediciones; i++) {
			for(int j = 0; j<numSizes; j++) {
				double r = (nMax-nMin)/(numSizes-1);
				Integer tam = (Integer.MAX_VALUE/nMax > j) ? nMin + j*(nMax-nMin)/(numSizes-1) : nMin + (int) (r*j);
				Problema p= Problema.of(tam);
				PropiedadesListas props = PropiedadesListas.of(sizeMin, sizeMax, numSizes, 
						minValue, maxValue,numListPerSize, numCasesPerList, rr);
				List<Integer> lista = generaListaEntero(tam, props);
				warmup(funcion,lista,umbral);
				Void[] res = new Void[numIter];
				Long t0 = System.nanoTime();
				for(int k = 0; k<numIter; k++) {
					List<Integer> listaMedicion = new ArrayList<>(lista);
					res[k] = funcion.apply(listaMedicion, umbral);
				}
				Long t1 = System.nanoTime();
				actualizaTiempos(tiempos, p, Double.valueOf((t1-t0)/numIter));
				System.out.println(j);
			}
		}
		Resultados.toFile(tiempos.entrySet().stream().map(x -> TamTiempo.of(x.getKey().tam(), x.getValue()))
				.map(TamTiempo::toString), fichero, true);
	}
	
	private record Problema(Integer tam) {
		public static Problema of(Integer tam) {
			return new Problema(tam);
		}
	}
	
	private static void  warmup(BiFunction<List<Integer>, Integer, Void> fun, List<Integer> ls, Integer umbral) {
		for(int i = 0; i<numIterWarmup; i++){
			List<Integer> warmupLista = new ArrayList<>(ls);
			fun.apply(warmupLista, umbral);
		}
	}
	
	public static List<Integer> generaListaEntero(Integer sizeLista, PropiedadesListas p){
		List<Integer> ls = new ArrayList<>();
		for(int i = 0; i<sizeLista; i++) {
			ls.add(p.minValue() + p.rr().nextInt(p.maxValue()- p.minValue()));
		}
		return ls;
	}
		
	private static void actualizaTiempos(Map<Problema, Double> tiempos, Problema p, Double tiempo) {
		if (!tiempos.containsKey(p) || tiempos.get(p) > tiempo) {
			tiempos.put(p, tiempo);
		}
	}
	
	private record TamTiempo(Integer tam, Double t) {
		public static TamTiempo of(Integer tam, Double t) {
			return new TamTiempo(tam, t);
		}
	
		public String toString() {
			return String.format("%d,%.0f", tam,t);
		}
	}
	
	public static void muestraGraficas() {
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		for(int i = 0; i<metodos.size();i++) {
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv", metodos.get(i).third());
			ficherosSalida.add(ficheroSalida);
			String label = metodos.get(i).third();
			System.out.println(label);
			TipoAjuste tipoAjuste = metodos.get(i).second();
			GraficosAjuste.show(ficheroSalida, tipoAjuste, label);
			
			Pair<Function<Double,Double>,String> parCurve= 
					GraficosAjuste.fitCurve(DataCurveFitting.points(ficheroSalida), tipoAjuste);
			String ajusteString = parCurve.second();
			labels.add(String.format("%s    %s", label, ajusteString));
		}
		GraficosAjuste.showCombined("QuickSort", ficherosSalida, labels);
		
	}
}
