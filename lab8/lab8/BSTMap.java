package lab8;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Random;


/**
 * Created by varad on 8/8/16.
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        private K key;
        private V value;
        private Node left, right, parent;

        private Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }

        private boolean isLeaf() {
            return (this.left == null && this.right == null);
        }
    }

    private Node root;
    private int size;

    // Dont use HashSet for keySet

    @Override
    public void clear() {
        this.root = null;   // will this cause loitering of the other nodes in the tree?
        this.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // Helper method for get
    private V treeFindValue(Node node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        V value;

        if (cmp == 0) {
            value = node.value;
        } else if (cmp < 0) {
            value = treeFindValue(node.left, key);
        } else {
            value = treeFindValue(node.right, key);
        }

        return value;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("key argument not provided");
        }

        V value = this.treeFindValue(this.root, key);
        return value;
    }

    private Node getNode(K key) {
        Node node = this.treeFindNode(this.root, key);
        return node;
    }

    // Helper method for get
    private Node treeFindNode(Node node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        Node matchNode;

        if (cmp == 0) {
            matchNode = node;
        } else if (cmp < 0) {
            matchNode = treeFindNode(node.left, key);
        } else {
            matchNode = treeFindNode(node.right, key);
        }

        return matchNode;
    }

    @Override
    public int size() {
        return this.size;
    }

    // Helper method for put
    private Node insertNode(Node node, K key, V value, Node parent) {
        if (node == null) {
            return new Node(key, value, parent);
        }

        int cmp = key.compareTo(node.key);

        if (cmp == 0) {
            node.value = value;
        } else if (cmp < 0) {
            node.left = insertNode(node.left, key, value, node);
        } else {
            node.right = insertNode(node.right, key, value, node);
        }

        return node;
    }

    @Override
    public void put(K key, V value) {

        if (key == null) {
            throw new NullPointerException("key argument is null");
        } else if (value == null) {
            throw new NullPointerException("value argument is null");
        }

        this.root = insertNode(this.root, key, value, null);
        this.size += 1;
    }

    private void printInOrder(Node node) {
        if (node == null) {
            return;
        } else {
            printInOrder(node.left);
            System.out.print(node.key.toString() + " ");
            printInOrder(node.right);
        }
    }

    public void printInOrder() {
        printInOrder(this.root);
    }

    private Set<K> keySet(Node node) {

        Set<K> keys = new HashSet<K>();

        if (node == null) {
            return keys;
        } else {

            Set<K> leftKeys, rightKeys;

            keys.add(node.key);
            leftKeys = keySet(node.left);
            rightKeys = keySet(node.right);

            keys.addAll(leftKeys);
            keys.addAll(rightKeys);

            return keys;
        }
    }

    @Override
    public Set keySet() {

        Set<K> keys = keySet(this.root);
        return keys;
    }

    // Helper methods for remove

    private Node findPredecessor(Node node) {
        Node predecessorNode = node.left;

        if (predecessorNode == null) {
            return null;
        }
        
        while (predecessorNode.right != null) {
            predecessorNode = predecessorNode.right;
        }
        
        return predecessorNode;
    }

    private Node findSuccessor(Node node) {
        Node successorNode = node.right;

        if (successorNode == null) {
            return null;
        }

        while (successorNode.left != null) {
            successorNode = successorNode.left;
        }

        return successorNode;
    }

    private static boolean getRandomBooleanToChooseNewNode(){
        Random random = new Random();
        return random.nextBoolean();
    }

    private void replaceNode(Node node, Node replace) {

        if (node.parent.left == node) {
            node.parent.left = replace;
        } else if (node.parent.right == node) {
            node.parent.right = replace;
        }

    }

    @Override
    public V remove(K key) {

        if (this == null) {
            throw new NullPointerException("cannot remove from empty tree");
        }

        Node nodeToDelete = getNode(key);
        if (nodeToDelete == null) {
            throw new ValueException("key " + key.toString() + " not found in map");
        }

        // Decrement size
        this.size -= 1;

        // Guaranteed to find a replacement node, if there is one
        Node replacementNode;
        if (getRandomBooleanToChooseNewNode()) {
            replacementNode = findPredecessor(nodeToDelete);
            if (replacementNode == null) {
                replacementNode = findSuccessor(nodeToDelete);
            }
        } else {
            replacementNode = findSuccessor(nodeToDelete);
            if (replacementNode == null) {
                replacementNode = findPredecessor(nodeToDelete);
            }
        }

        // Remove node from the BST
        V valueToReturn = nodeToDelete.value;

        if (nodeToDelete.isLeaf()) {
            // Simply setting a node to null does not work,
            // since the GC checks if there are any still any
            // valid references to the node. Thus, all these
            // (in particular the parent's reference to it
            // should be nulled out as well

            replaceNode(nodeToDelete, null);
            return valueToReturn;
        }

        // Only 2 cases for replacementNode's children : 0, 1
        // (refer to lectures). And we're always replacing a
        // root node effectively.

        nodeToDelete.key = replacementNode.key;     // has to be done everywhere
        nodeToDelete.value = replacementNode.value;

        if (replacementNode.left != null && replacementNode.right == null) {
            replaceNode(replacementNode, replacementNode.left);
        } else if (replacementNode.left == null && replacementNode.right != null) {
            replaceNode(replacementNode, replacementNode.right);
        }

//        replaceNode(replacementNode, null);

        // could just set the replacementNode to null,
        // but it wasn't working - GC not happening
//        replacementNode.key = null;
//        replacementNode.value = null;
//        replacementNode.parent = null;

        return valueToReturn;
    }

    @Override
    public V remove(K key, V value) {

        if (this == null) {
            throw new NullPointerException("cannot remove from empty tree");
        }

        V valueToReturn;

        Node nodeToDelete = getNode(key);
        if (nodeToDelete.value.equals(value)) {
            valueToReturn = this.remove(key);
            return valueToReturn;
        } else {
            throw new ValueException("no entry with given key and value");
        }
    }

    private class MapIterator implements Iterator<K> {

        private Iterator<K> setIterator;

        public MapIterator() {
            Set<K> keySet = keySet();
            this.setIterator = keySet.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.setIterator.hasNext();
        }

        @Override
        public K next() {
            return this.setIterator.next();
        }
    }

    @Override
    public Iterator iterator() {
        return new MapIterator();
    }
}
