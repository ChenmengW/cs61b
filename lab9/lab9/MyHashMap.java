package lab9;

import java.util.*;

/**
 * Created by varad on 8/8/16.
 */

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int INITIAL_CAPACITY = 4;
    private static final int LOAD_FACTOR = 2;
    private static final int RESIZE_FACTOR = 2;

    private double loadFactor;
    private int capacity;
    private int size;

    // To store the actual map

    // Approach 1 : Define Node class and store linked lists of the same in an array.
    //              Didn't work because array of generics was causing issues.
    //
    // Approach 2 : Define an ArrayList and try above. Didn't work because resizing
    //              the ArrayList was causing some weird internal changes


    private ExternalChain<K, V>[] bins;
    private Set<K> keySet;

    public MyHashMap() {
        this.size = 0;
        this.capacity = INITIAL_CAPACITY;
        this.loadFactor = LOAD_FACTOR;
        this.keySet = new HashSet<K>();
        this.bins = (ExternalChain<K, V>[]) new ExternalChain[INITIAL_CAPACITY];

        for (int i = 0; i < this.bins.length; i++) {
            this.bins[i] = new ExternalChain<K, V>();
        }

        // Cannot use this for initialization because it sets each
        // entry to the SAME object (point to same memory). So
        // every element is added in each entry.

        // Arrays.fill(this.bins, new ExternalChain<K, V>());
    }

    public MyHashMap(int initialSize) {
        this.size = 0;
        this.capacity = initialSize;
        this.loadFactor = LOAD_FACTOR;
        this.keySet = new HashSet<K>();
        this.bins = (ExternalChain<K, V>[]) new ExternalChain[initialSize];

        for (int i = 0; i < this.bins.length; i++) {
            this.bins[i] = new ExternalChain<K, V>();
        }
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.size = 0;
        this.capacity = initialSize;
        this.loadFactor = loadFactor;
        this.keySet = new HashSet<K>();
        this.bins = (ExternalChain<K, V>[]) new ExternalChain[initialSize];

        for (int i = 0; i < this.bins.length; i++) {
            this.bins[i] = new ExternalChain<K, V>();
        }
    }

    private int hashCode(K key) {
        return (key.hashCode() & 0x7fffffff) % this.capacity;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.bins.length; i++) {
            this.bins[i] = new ExternalChain<K, V>();
        }

        this.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int hashIndex = this.hashCode(key);
        ExternalChain<K, V> chain = this.bins[hashIndex];
        V value = chain.get(key);
        return value;
    }

    @Override
    public int size() {
        return this.size;
    }

    private void resize() {
        int resizedCapacity = this.capacity * RESIZE_FACTOR;

        MyHashMap<K, V> resizedMap = new MyHashMap<K, V>(resizedCapacity, this.loadFactor);
        for (K key: this) {
            V value = this.get(key);
            resizedMap.put(key, value);
        }

        this.size = resizedMap.size;            // redundant
        this.capacity = resizedMap.capacity;
        this.loadFactor = resizedMap.loadFactor;    // redundant
        this.keySet = resizedMap.keySet;        // redundant
        this.bins = resizedMap.bins;
    }

    @Override
    public void put(K key, V value) {
        int hashIndex = this.hashCode(key);
        ExternalChain<K, V> chain = this.bins[hashIndex];
        chain.put(key, value);
        keySet.add(key);
        this.size += 1;

        double fillFactor = (double) this.size / this.capacity;
        if (fillFactor > this.loadFactor) {
            this.resize();
        }
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {

        if (this.containsKey(key)) {
            int hashIndex = this.hashCode(key);
            ExternalChain<K, V> chain = this.bins[hashIndex];
            V value = chain.remove(key);
            this.size -= 1;
            return value;
        } else {
            return null;
        }

    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    // This is, effectively just a wrapper around the Set iterator for the keys
    private class MapIterator implements Iterator<K> {

        private Iterator<K> setIterator;

        public MapIterator() {
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
    public Iterator<K> iterator() {
        return new MapIterator();
    }

//    public static void main(String[] args) {
//        MyHashMap<Integer, String> mhm = new MyHashMap<Integer, String>();
//    }
}