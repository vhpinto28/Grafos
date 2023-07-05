package graphmatrix;

import java.util.Arrays;

public class GraphMat<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int EXPAND_FACTOR = 2;

    private Object[] vertices;  // Cambio en el tipo de datos a Object[]
    private boolean[][] adjacencyMatrix;
    private int vertexCount;

    public GraphMat() {
        vertices = new Object[DEFAULT_CAPACITY];  // Sin la supresión de advertencias
        adjacencyMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        vertexCount = 0;
    }

    public void insertVertex(E v) {
        if (searchVertex(v)) {
            return; // El vértice ya existe en el grafo
        }

        if (vertexCount == vertices.length) {
            expandCapacity();
        }

        vertices[vertexCount] = v;
        vertexCount++;
    }

    public void insertEdge(E v, E z) {
        int indexV = getIndex(v);
        int indexZ = getIndex(z);

        if (indexV == -1 || indexZ == -1) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        adjacencyMatrix[indexV][indexZ] = true;
        adjacencyMatrix[indexZ][indexV] = true;
    }

    public boolean searchVertex(E v) {
        return getIndex(v) != -1;
    }

    public boolean searchEdge(E v, E z) {
        int indexV = getIndex(v);
        int indexZ = getIndex(z);

        if (indexV == -1 || indexZ == -1) {
            return false;
        }

        return adjacencyMatrix[indexV][indexZ];
    }

    public void dfs(E v) {
        int startIndex = getIndex(v);
        if (startIndex == -1) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }

        boolean[] visited = new boolean[vertexCount];
        dfsUtil(startIndex, visited);
    }

    private void dfsUtil(int vertexIndex, boolean[] visited) {
        visited[vertexIndex] = true;
        System.out.println(vertices[vertexIndex]);

        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[vertexIndex][i] && !visited[i]) {
                dfsUtil(i, visited);
            }
        }
    }

    private int getIndex(E v) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].equals(v)) {
                return i;
            }
        }
        return -1;
    }

    private void expandCapacity() {
        int newCapacity = vertices.length * EXPAND_FACTOR;
        vertices = Arrays.copyOf(vertices, newCapacity);
        adjacencyMatrix = Arrays.copyOf(adjacencyMatrix, newCapacity);
        for (int i = 0; i < newCapacity; i++) {
            adjacencyMatrix[i] = Arrays.copyOf(adjacencyMatrix[i], newCapacity);
        }
    }
}
