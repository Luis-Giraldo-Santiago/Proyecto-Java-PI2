package ejercicios;

import java.util.*;
import java.util.stream.Collectors;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.*;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.*;

public class Ejercicio3 {
	public static List<String> todasCadenasBinario(BinaryTree<String> tree, String c){
		String cadena = null;
		return switch (tree) {
		case BEmpty<String>  t-> new ArrayList<>();
		case BLeaf<String>  t -> {
			List<String> ls = new ArrayList<>();
			if(!t.label().equals(c)) {
				ls.add(t.label());
			}
			yield ls;
		}
		case BTree<String> t -> {
			List<String> ls = todasCadenasBinario(t.left(), c);
			ls.addAll(todasCadenasBinario(t.right(), c));
			for(int i = 0; i<ls.size();i++) {
				if(!t.label().equals(c)) {
					if(!ls.isEmpty()) {
						cadena = t.label()+ls.get(i);
						ls.set(i, cadena);
						
					}
				}else {
					ls.removeAll(ls);
				}
			}
			yield ls;
		}
		};

	}
	
	public static List<String> todasCadenasNario(Tree<String> tree, String c){
		String cadena = null;
		return switch (tree) {
		case TEmpty<String> t -> new ArrayList<>();
		case TLeaf<String> t -> {
			List<String> ls = new ArrayList<>();
			if(!t.label().equals(c)) {
				ls.add(t.label());
			}
			yield ls;
		}
		case TNary<String> t ->{
			List<String> ls = t.elements().stream().flatMap(x->todasCadenasNario(x,c).stream()).collect(Collectors.toList());
			for(int i = 0; i<ls.size();i++) {
				if(!t.label().equals(c)) {
					if(!ls.isEmpty()) {
						cadena = t.label()+ls.get(i);
						ls.set(i, cadena);
					}
				}else {
					ls.removeAll(ls);
				}
			}
			yield ls;
		}
		};
	}
}
