package lab9;

import javafx.scene.Node;

import java.util.LinkedList;

/**
 * Created by varad on 8/9/16.
 */
class ExternalChain<K, V> {

    private Node first;

    private class Node {
        private K key;
        private V value;
        private Node next;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    ExternalChain() {
    }

    // recursive helper method for get
    private V get(K key, Node node) {
        V value;

        if (node == null) {
            return null;
        } else if (node.key.equals(key)) {
            value = node.value;
        } else {
            value = this.get(key, node.next);
        }

        return value;
    }

    public V get(K key) {
        if (this.first == null) {
            return null;
        }

        Node node = this.first;
        V value = this.get(key, node);
        return value;
    }

    // recursive helper method for put
    private void put(K key, V value, Node node) {
        if (node.next == null) {
            node.next = new Node(key, value);
        } else if (node.key.equals(key)) {
            node.value = value;
        } else {
            this.put(key, value, node.next);
        }
    }

    public void put(K key, V value) {
        if (this.first == null) {
            this.first = new Node(key, value);
            return;
        }

        Node node = this.first;
        this.put(key, value, node);
    }

    // recursive helper for remove
    private V remove(K key, Node node) {
        if (key.equals(this.first.key)) {
            V value = this.first.value;
            this.first = this.first.next;
            return value;
        }

        if (node.next.key.equals(key)) {
            V value = node.next.value;
            node.next = node.next.next;
            return value;
        } else {
            V value = this.remove(key, node.next);
            return value;
        }
    }

    public V remove(K key) {
        // Due to the containsKey() call, we're sure that the key
        // exists in the map now. So initial null check not required

        Node node = this.first;
        V value = remove(key, node);
        return value;
    }
}
