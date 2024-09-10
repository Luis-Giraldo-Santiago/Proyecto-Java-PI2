package testEjercicios;

import java.util.*;

import ejercicios.Ejercicio4;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEjercicio4 {

	public static void main(String[] args) {
		testEjercicio4Binario();
		System.out.println("\n");
		testEjercicio4Nario();
	}
	
	public static void testEjercicio4Binario() {
		List<String> filas = Files2.streamFromFile("ficheros/Ejercicio4DatosEntradaBinario.txt").toList();
		BinaryTree<String> tree = null;
		for (String fila : filas) {
			tree = BinaryTree.parse(fila);
			System.out.println("Solución para el arbol binario "+tree+" es "+Ejercicio4.vocalesIgualesBinario(tree));
		}
	}
	
	public static void testEjercicio4Nario() {
		List<String> filas = Files2.streamFromFile("ficheros/Ejercicio4DatosEntradaNario.txt").toList();
		Tree<String> tree = null;
		for (String fila : filas) {
			tree = Tree.parse(fila);
			System.out.println("Solución para el arbol n-ario "+tree+" es "+Ejercicio4.vocalesIgualesNario(tree));
		}
	}

}
