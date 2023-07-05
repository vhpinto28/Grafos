package graphlink;
public class Edge<E> {
    private Vertex<E> refDest;
    private int weight;

    public Edge(Vertex<E> refDest, int weight) {
        this.refDest = refDest;
        this.weight = weight;
    }

    public boolean equals(Object o) {
        if (o instanceof Edge<?>) {
            Edge<?> e = (Edge<?>) o;
            return this.refDest.equals(e.refDest);
        }
        return false;
    }

    public Vertex<E> getRefDest() {
        return refDest;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        if (this.weight > -1) {
            return refDest.getData() + "[" + this.weight + "], ";
        } else {
            return refDest.getData() + ", ";
        }
    }
}




