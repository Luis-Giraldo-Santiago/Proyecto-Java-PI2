package testEjercicios;

import java.util.*;

import ejercicios.Ejercicio3;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEjercicio3 {

	public static void main(String[] args) {
		
		testEjercicio3Binario();
		System.out.println("\n");
		testEjercicio3Nario();
		

	}
	
	public static void testEjercicio3Binario() {
		List<String> filas = Files2.streamFromFile("ficheros/Ejercicio3DatosEntradaBinario.txt").toList();
		BinaryTree<String> tree = null;
		for (String fila : filas) {
			List<String> ls = parseaLineas(fila);
			tree = BinaryTree.parse(ls.get(0));
			System.out.println("Solución para el arbol binario "+tree+" y Character "+ls.get(1).charAt(0)+" es "+
					Ejercicio3.todasCadenasBinario(tree,ls.get(1)));
		}
	}
	
	public static void testEjercicio3Nario() {
		List<String> filas = Files2.streamFromFile("ficheros/Ejercicio3DatosEntradaNario.txt").toList();
		Tree<String> tree = null;
		for (String fila : filas) {
			List<String> ls = parseaLineas(fila);
			tree = Tree.parse(ls.get(0));
			System.out.println("Solución para el arbol n-ario "+tree+" y Character "+ls.get(1).charAt(0)+" es "+
					Ejercicio3.todasCadenasNario(tree,ls.get(1)));
		}
	}
	
	public static List<String> parseaLineas(String fila){
		List<String> ls = new ArrayList<>();
		String[] trozos = fila.split("#");
		String arbol = trozos[0];
		String letra = trozos[1];
		ls.add(arbol);
		ls.add(letra);
		return ls;
	}

}
