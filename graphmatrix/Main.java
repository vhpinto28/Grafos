package graphmatrix;
public class Main {
    public static void main(String[] args) {
        GraphMat<String> graph = new GraphMat<>();

        // Insertar vértices
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");

        // Insertar aristas
        graph.insertEdge("A", "B");
        graph.insertEdge("B", "C");
        graph.insertEdge("C", "D");
        graph.insertEdge("D", "A");

        // Buscar vértices y aristas
        System.out.println(graph.searchVertex("A"));  // true
        System.out.println(graph.searchVertex("E"));  // false

        System.out.println(graph.searchEdge("A", "B"));  // true
        System.out.println(graph.searchEdge("B", "D"));  // false

        // Realizar el recorrido en profundidad
        System.out.println("DFS:");
        graph.dfs("A");
    }
}
