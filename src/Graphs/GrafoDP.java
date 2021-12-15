package Graphs;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GrafoDP {
	private double err=1e-6;
	private int n;
	private int capacidad;
	private Double[][] ma;
	private String[] id;
	
	public GrafoDP(int capacidad) {
		n=0;
		this.capacidad=capacidad;
		ma=new Double[capacidad][capacidad];
		id=new String[capacidad];
	}
	public int capacidad() {
		return this.capacidad;
	}
	public int orden() {
		return this.n;
	}

	public boolean esVacio() {
		boolean esVacio=true;
		int i=0, j=0;
		while(esVacio && i<capacidad) {
			while(esVacio && j<capacidad) {
				if(ma[i][j]!=null)
					esVacio=false;
				j++;						
			}
			i++;
		}
		return esVacio;
	}
	public boolean esNulo() throws grafoInconsistenteException {
		if(n==0) {
			if(esVacio()) {
				return true;
			}else {
				throw new grafoInconsistenteException("El grafo no tienen nodos, pero existen aristas");
			}
		}else
			return false;
	}

	public boolean esLleno() {
		return n==capacidad;
	}
	public boolean existeVertice(String nombre) {
		nombre=nombre.toUpperCase();
		boolean existe=false;
		int i=0;
		while(!existe && i< id.length) {
			if(nombre.equals(id[i]))
				existe=true;
			i++;
		}
		return existe;
	}
	public boolean insertarVertice(String nombre) {
		if(this.esLleno() || this.existeVertice(nombre))
			return false;
		else {
			id[n]=nombre.toUpperCase();
			n++;
			return true;
		}
	}
	
	private int indiceVertice(String nombre) {
		nombre=nombre.toUpperCase();
		boolean encontrado=false;
		int i=0;
		while(!encontrado && i< id.length) {
			if(nombre.equals(id[i]))
				encontrado=true;
			i++;
		}
		if(encontrado)
			return i-1;
		else
			return -1;
	}
	
	public boolean reemplazarNombreVertice(String anterior, String actual) {
		int ind=indiceVertice(anterior);
		if(ind>-1) {
			id[ind]=actual.toUpperCase();
			return true;
		}else
			return false;
	}
	
	public boolean eliminarVertice(String nombre) {
		if(!existeVertice(nombre))
			return false;
		else {
			int ind=indiceVertice(nombre);
			for(int i=ind; i<n-1; i++){
				id[i]=id[i+1];
			}
			for(int j=ind; j<n-1; j++) {
				for(int i=0; i<n; i++) {
					ma[i][j]=ma[i][j+1];
				}
			}
			for(int i=ind; i<n-1; i++) {
				for(int j=0; j<n; j++) {
					ma[i][j]=ma[i+1][j];
				}
			}
			for(int i=0; i<n; i++) {
				ma[i][n-1]=null;
				ma[n-1][i]=null;
			}
			n--;
			return true;
		}
	}
	public LinkedList verticesAdyacentes(String nombre) {
		if(!existeVertice(nombre))
			return null;
		else {
			LinkedList l=new LinkedList();
			int ind=indiceVertice(nombre);
			for(int i=0; i<n; i++) {
				if(ma[ind][i]!=null)
					l.add(id[i]);
			}
			return l;
		}
	}
	public LinkedList verticesIncidentes(String nombre) {
		if(!existeVertice(nombre))
			return null;
		else {
			LinkedList l=new LinkedList();
			int ind=indiceVertice(nombre);
			for(int i=0; i<n; i++) {
				if(ma[i][ind]!=null)
					l.add(id[i]);
			}
			return l;
		}
	}
	//m�todo para borrar
	public void imprimirArreglos() {
		for(int i=0; i<n; i++)
			System.out.print(id[i]+" ");
		System.out.println();
		System.out.println();
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++)
				System.out.printf("%6.1f ",ma[i][j]);
			System.out.println();
		}
		System.out.println();
			
	}

	public boolean insertarArista(String origen, String destino, double peso) {
		if(existeVertice(origen) && existeVertice(destino)){
			ma[indiceVertice(origen)][indiceVertice(destino)]=peso;
			return true;
		}else
			return false;
	}
	public boolean existeArista(String origen, String destino) {
		if(existeVertice(origen) && existeVertice(destino)){
			return ma[indiceVertice(origen)][indiceVertice(destino)]!=null;
		}else
			return false;
	}
	public boolean eliminarArista(String origen, String destino) {
		if(existeVertice(origen) && existeVertice(destino)){
			if(ma[indiceVertice(origen)][indiceVertice(destino)]!=null) {
				ma[indiceVertice(origen)][indiceVertice(destino)]=null;
				return true;
			}else
				return false;	
		}else
			return false;
	}
	public LinkedList aristasAdyacentes(String nombre) {
		if(!existeVertice(nombre))
			return null;
		else {
			LinkedList l=new LinkedList();
			int ind=indiceVertice(nombre);
			for(int i=0; i<n; i++) {
				if(ma[i][ind]!=null)
					l.add(id[i]);
			}
			return l;
		}
	}

	public int grado(String nombre) {
		if(!existeVertice(nombre))
			return -1;
		else {
			int cont=0;
			int ind=indiceVertice(nombre);
			for(int i=0; i<n; i++) {
				if(ma[i][ind]!=null)
					cont++;
			}
			return cont;
		}
	}
	public int grado() {
		if(n==0)
			return -1;
		else {
			int grado=0;
			for(int i=0; i<n; i++) {
				int g=grado(id[i]);
				if(g>grado)
					grado=g;
			}
			return grado;
		}
	}
	public String toString() {
		String cad="";
		for(int i=0; i<n; i++)
			cad=cad+id[i]+" ";
		cad=cad+"\n";
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				if(ma[i][j]!=null)
					cad=cad+"("+id[i]+", "+id[j]+")\n";
		return cad;
	}
	
	public void profundidad(String nombre) {
		nombre=nombre.toUpperCase();
		if(existeVertice(nombre)) {
			boolean visitados[]=new boolean[n];
			Stack pila=new Stack();
			pila.push(nombre);
			while(!pila.empty()) {
				String vertice=(String) pila.pop();
				System.out.println(vertice);
				visitados[indiceVertice(vertice)]=true;
				LinkedList l=verticesAdyacentes(vertice);
				while(!l.isEmpty()) {
					vertice=(String) l.remove();
					if(!visitados[indiceVertice(vertice)]) {
						pila.push(vertice);
						visitados[indiceVertice(vertice)]=true;
					}
						
				}
				
			}
		}
	}
	
	public void amplitud (String nombre) {
		nombre=nombre.toUpperCase();
		if(existeVertice(nombre)) {
			boolean visitados[]=new boolean[n];
			Queue cola=new LinkedList();
			cola.offer(nombre);
			while(!cola.isEmpty()) {
				String vertice=(String) cola.poll();
				System.out.println(vertice);
				visitados[indiceVertice(vertice)]=true;
				LinkedList l=verticesAdyacentes(vertice);
				while(!l.isEmpty()) {
					vertice=(String) l.remove();
					if(!visitados[indiceVertice(vertice)]) {
						cola.offer(vertice);
						visitados[indiceVertice(vertice)]=true;
					}
						
				}
				
			}
		}
	}

	public void dijkstra(String origen) {
		if(existeVertice(origen)) {
			boolean marcados[]=new boolean[n];
			Double distancias[] = new Double[n];
			for(int i=0; i<n; i++)
				distancias[i]=Double.MAX_VALUE;
			int rutas[] = new int[n];
			int ind=indiceVertice(origen);
			marcados[ind]=true;
			distancias[ind]=0.;
			rutas[ind]=ind;
			for(int i=0; i<n-1; i++) {
				LinkedList l=verticesAdyacentes(id[ind]);
				while(!l.isEmpty()) {
					int ind1=indiceVertice((String)l.remove());
					if(!marcados[ind1] && distancias[ind]+ma[ind][ind1]<distancias[ind1]) {
						distancias[ind1]=distancias[ind]+ma[ind][ind1];
						rutas[ind1]=ind;
					}
				}
				int indice=-1;
				Double distancia=Double.MAX_VALUE;
				for(int j=0; j<n; j++) {
					if(distancias[j]< distancia && !marcados[j]) {
						indice=j;
						distancia=distancias[j];
					}
				}
				if(indice!=-1)
					ind=indice;
				marcados[ind]=true;
			}
			for(int i=0; i<n; i++) {
				if(distancias[i]==Integer.MAX_VALUE)
					System.out.println("No existe camino");
				else
					System.out.println(distancias[i]);
			}
		}
	}
}
