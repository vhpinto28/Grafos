package graphlink;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GraphLink<E> {
    protected ListLinked<Vertex<E>> listVertex;

    public GraphLink() {
        listVertex = new ListLinked<Vertex<E>>();
    }

    public void insertVertex(E data) {
        Vertex<E> vertex = new Vertex<E>(data);
        listVertex.insertLast(vertex);
    }

    public void insertEdge(E verOri, E verDes) {
        Vertex<E> vertexOri = findVertex(verOri);
        Vertex<E> vertexDes = findVertex(verDes);

        if (vertexOri == null || vertexDes == null) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        Edge<E> edge = new Edge<E>(vertexDes, -1);
        vertexOri.listAdj.insertLast(edge);

        // Dado que es un grafo no dirigido, también debemos agregar un borde en la dirección opuesta
        edge = new Edge<E>(vertexOri, -1);
        vertexDes.listAdj.insertLast(edge);
    }
    
    public void insertEdgeWeight(E verOri, E verDes, int weight) {
        Vertex<E> vertexOri = findVertex(verOri);
        Vertex<E> vertexDes = findVertex(verDes);

        if (vertexOri == null || vertexDes == null) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        Edge<E> edge = new Edge<E>(vertexDes, weight);
        vertexOri.listAdj.insertLast(edge);

        // Dado que es un grafo no dirigido, también debemos agregar un borde en la dirección opuesta
        edge = new Edge<E>(vertexOri, weight);
        vertexDes.listAdj.insertLast(edge);
    }

    public boolean searchVertex(E v) {
        Vertex<E> vertex = findVertex(v);
        return vertex != null;
    }

    public boolean searchEdge(E v, E z) {
        Vertex<E> vertexV = findVertex(v);
        Vertex<E> vertexZ = findVertex(z);

        if (vertexV == null || vertexZ == null) {
            return false;
        }

        for (Edge<E> edge : vertexV.listAdj) {
            if (edge.getRefDest().equals(vertexZ)) {
                return true;
            }
        }

        return false;
    }

    public void removeVertex(E v) {
        Vertex<E> vertex = findVertex(v);
        if (vertex == null) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }

        // Eliminar aristas relacionadas con el vértice
        for (Vertex<E> vVertex : listVertex) {
            ListLinked<Edge<E>> adjList = vVertex.listAdj;
            adjList.removeIf(edge -> edge.getRefDest().equals(vertex));
        }

        // Eliminar el vértice de la lista de vértices
        listVertex.remove(vertex);
    }

    public void removeEdge(E v, E z) {
        Vertex<E> vertexV = findVertex(v);
        Vertex<E> vertexZ = findVertex(z);

        if (vertexV == null || vertexZ == null) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        ListLinked<Edge<E>> adjList = vertexV.listAdj;
        adjList.removeIf(edge -> edge.getRefDest().equals(vertexZ));
    }

    public void dfs(E v) {
        Vertex<E> startVertex = findVertex(v);
        if (startVertex == null) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }

        boolean[] visited = new boolean[listVertex.length()];
        dfsUtil(startVertex, visited);
    }

    private void dfsUtil(Vertex<E> vertex, boolean[] visited) {
        visited[listVertex.search(vertex)] = true;
        System.out.println(vertex.getData());

        for (Edge<E> edge : vertex.listAdj) {
            Vertex<E> adjVertex = edge.getRefDest();
            if (!visited[listVertex.search(adjVertex)]) {
                dfsUtil(adjVertex, visited);
            }
        }
    }

    public void bfs(E v) {
        Vertex<E> startVertex = findVertex(v);
        if (startVertex == null) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }

        boolean[] visited = new boolean[listVertex.length()];
        Queue<Vertex<E>> queue = new LinkedList<>();
        visited[listVertex.search(startVertex)] = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Vertex<E> vertex = queue.poll();
            System.out.println(vertex.getData());

            for (Edge<E> edge : vertex.listAdj) {
                Vertex<E> adjVertex = edge.getRefDest();
                if (!visited[listVertex.search(adjVertex)]) {
                    visited[listVertex.search(adjVertex)] = true;
                    queue.add(adjVertex);
                }
            }
        }
    }

    public ArrayList<E> bfsPath(E v, E z) {
        Vertex<E> startVertex = findVertex(v);
        Vertex<E> endVertex = findVertex(z);
        if (startVertex == null || endVertex == null) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        boolean[] visited = new boolean[listVertex.length()];
        int[] parent = new int[listVertex.length()];
        Queue<Vertex<E>> queue = new LinkedList<>();
        visited[listVertex.search(startVertex)] = true;
        parent[listVertex.search(startVertex)] = -1;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Vertex<E> vertex = queue.poll();

            if (vertex == endVertex) {
                return buildPath(parent, vertex);
            }

            for (Edge<E> edge : vertex.listAdj) {
                Vertex<E> adjVertex = edge.getRefDest();
                if (!visited[listVertex.search(adjVertex)]) {
                    visited[listVertex.search(adjVertex)] = true;
                    parent[listVertex.search(adjVertex)] = listVertex.search(vertex);
                    queue.add(adjVertex);
                }
            }
        }

        return null; // No se encontró un camino entre los vértices v y z
    }

    public ArrayList<E> shortPath(E v, E z) {
        Vertex<E> startVertex = findVertex(v);
        Vertex<E> endVertex = findVertex(z);
        if (startVertex == null || endVertex == null) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        boolean[] visited = new boolean[listVertex.length()];
        int[] parent = new int[listVertex.length()];
        int[] distances = new int[listVertex.length()];
        Queue<Vertex<E>> queue = new LinkedList<>();
        visited[listVertex.search(startVertex)] = true;
        parent[listVertex.search(startVertex)] = -1;
        distances[listVertex.search(startVertex)] = 0;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Vertex<E> vertex = queue.poll();

            if (vertex == endVertex) {
                return buildPath(parent, vertex);
            }

            for (Edge<E> edge : vertex.listAdj) {
                Vertex<E> adjVertex = edge.getRefDest();
                int weight = edge.getWeight();
                int currentDistance = distances[listVertex.search(vertex)];
                int newDistance = currentDistance + weight;

                if (!visited[listVertex.search(adjVertex)]) {
                    visited[listVertex.search(adjVertex)] = true;
                    parent[listVertex.search(adjVertex)] = listVertex.search(vertex);
                    distances[listVertex.search(adjVertex)] = newDistance;
                    queue.add(adjVertex);
                } else if (newDistance < distances[listVertex.search(adjVertex)]) {
                    parent[listVertex.search(adjVertex)] = listVertex.search(vertex);
                    distances[listVertex.search(adjVertex)] = newDistance;
                }
            }
        }

        return null; // No se encontró un camino entre los vértices v y z
    }

    private ArrayList<E> buildPath(int[] parent, Vertex<E> endVertex) {
        ArrayList<E> path = new ArrayList<>();
        int index = 0;

        for (Vertex<E> vertex : listVertex) {
            if (vertex.equals(endVertex)) {
                break;
            }
            index++;
        }

        Node<Vertex<E>> currentNode = listVertex.first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }

        while (index != -1) {
            Vertex<E> vertex = currentNode.getData();
            path.add(0, vertex.getData());
            index = parent[index];

            if (index != -1) {
                currentNode = listVertex.first;
                for (int i = 0; i < index; i++) {
                    currentNode = currentNode.getNext();
                }
            }
        }

        return path;
    }

    private Vertex<E> findVertex(E data) {
        for (Vertex<E> vertex : listVertex) {
            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return listVertex.toString();
    }
}





