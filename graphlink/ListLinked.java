package graphlink;

import java.util.Iterator;
import java.util.function.Predicate;

public class ListLinked<T> implements TDAList<T>, Iterable<T> {
    protected Node<T> first;
    protected int count;

    public ListLinked() {
        this.first = null;
        this.count = 0;
    }

    public boolean isEmptyList() {
        return this.first == null;
    }

    public int length() {
        return this.count;
    }

    public void destroyList() {
        this.first = null;
        this.count = 0;
    }

    public int search(T x) {
        Node<T> aux = this.first;
        for (int i = 0; aux != null; aux = aux.getNext(), i++) {
            if (aux.getData().equals(x)) {
                return i;
            }
        }
        return -1;
    }

    public void insertFirst(T x) {
        this.first = new Node<T>(x, this.first);
        this.count++;
    }

    public void insertLast(T x) {
        if (this.isEmptyList()) {
            this.insertFirst(x);
        } else {
            Node<T> lastNode = getLastNode();
            lastNode.setNext(new Node<T>(x));
            this.count++;
        }
    }

    private Node<T> getLastNode() {
        Node<T> aux = this.first;
        while (aux.getNext() != null) {
            aux = aux.getNext();
        }
        return aux;
    }

    public void remove(T x) {
        Node<T> aux = this.first;
        Node<T> prev = null;
        while (aux != null) {
            if (aux.getData() == x) {
                if (aux == this.first) {
                    this.first = this.first.getNext();
                } else {
                    prev.setNext(aux.getNext());
                }
                break;
            }
            prev = aux;
            aux = aux.getNext();
        }
        this.count--;
    }

    public void removeIf(Predicate<T> condition) {
        Node<T> current = this.first;
        Node<T> prev = null;

        while (current != null) {
            if (condition.test(current.getData())) {
                if (prev == null) {
                    // El elemento a eliminar es el primero de la lista
                    this.first = current.getNext();
                } else {
                    // El elemento a eliminar no es el primero de la lista
                    prev.setNext(current.getNext());
                }
                this.count--;
            } else {
                prev = current;
            }
            current = current.getNext();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Node<T> aux = this.first;
        for (int i = 0; aux != null; aux = aux.getNext(), i++) {
            str.append("[").append(i).append("] = ").append(aux.getData()).append("\n");
        }
        return str.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Node<T> current;

        public ListIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T data = current.getData();
            current = current.getNext();
            return data;
        }
    }
}


