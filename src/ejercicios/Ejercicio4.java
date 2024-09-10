package ejercicios;

import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.*;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.*;

public class Ejercicio4 {
	
	public static Boolean vocalesIgualesBinario(BinaryTree<String> tree) {
		return vocalesIgualesBinario(tree, false);
	}
	
	public static Boolean vocalesIgualesBinario(BinaryTree<String> tree, Boolean b) {

		return switch (tree) {
		case BEmpty<String>  t -> b;
		case BLeaf<String>  t -> b;
		case BTree<String>  t -> {
			if(cuentaVocales(t.left().toString())==cuentaVocales(t.right().toString())) {
				b = vocalesIgualesBinario(t.left(),true);
				b = vocalesIgualesBinario(t.right(),true);
			}
			yield b;
		}
		};
	}
	
	public static Boolean vocalesIgualesNario(Tree<String> tree) {
		return vocalesIgualesNario(tree,false);
	}

	private static Boolean vocalesIgualesNario(Tree<String> tree, Boolean b) {
		return switch (tree) {
		case TEmpty<String>  t -> b;
		case TLeaf<String>  t -> b;
		case TNary<String>  t -> {
			String palabra = t.label().toString();
			Integer numVocales = cuentaVocales(palabra);
			Integer total = 0;
			List<Tree<String>> hijo = t.elements();
			if(t.height()==1) {
				for(Tree<String> raiz:t.elements()) {
					numVocales = cuentaVocales(palabra) + numVocales;
					total = cuentaVocales(t.elements().toString());
					if(total==numVocales) {
						b = vocalesIgualesNario(raiz,true);
					}
				}
			}
			for(Tree<String> hijos: hijo) {
				numVocales = cuentaVocales(palabra) + numVocales;
				total = cuentaVocales(hijos.elements().toString());
				if(total==numVocales) {
					b = vocalesIgualesNario(hijos,true);
				}
			}
			yield b;
		}
		};
	}
	
	public static Integer cuentaVocales(String s) {
		Integer ac = 0;
		for(int i =0;i<s.length();i++) {
			if(s.charAt(i)== 'a') {
				ac += 1;
			}else if(s.charAt(i)== 'e') {
				ac += 1;
			}else if(s.charAt(i)== 'i') {
				ac += 1;
			}else if(s.charAt(i)== 'o') {
				ac += 1;
			}else if(s.charAt(i)== 'u') {
				ac += 1;
			}
		}
		return ac;
	}

}
