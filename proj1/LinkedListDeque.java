/**
 * Created by varad on 7/18/16.
 */
public class LinkedListDeque<Item> {

    public class Node {

        public Node prev;
        public Item item;
        public Node next;

        public Node(Node p, Item i, Node n) {
            this.prev = p;
            this.item = i;
            this.next = n;
        }
    }

    private Node sentinel;
    private int size;
    
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(this.sentinel, null, this.sentinel);
    }

    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item x) {
        Node newFrontNode;

        if (this.isEmpty()) {
            newFrontNode = new Node(this.sentinel, x, this.sentinel);
            this.sentinel.prev = newFrontNode;
        } else {
            Node oldFrontNode = this.sentinel.next;
            newFrontNode = new Node(this.sentinel, x, oldFrontNode);
        }

        this.sentinel.next = newFrontNode;
        this.size += 1;
    }

    public void addLast(Item x) {
        Node newLastNode;

        if (this.isEmpty()) {
            newLastNode = new Node(this.sentinel, x, this.sentinel);
            this.sentinel.next = newLastNode;
        } else {
            Node oldLastNode = this.sentinel.prev;
            newLastNode = new Node(oldLastNode, x, this.sentinel);
            oldLastNode.next = newLastNode;         // SO. MUCH. PAIN.
        }

        this.sentinel.prev = newLastNode;
        this.size += 1;
    }

    public Item get(int index) {
        if (index >= this.size) {
            return null;
        }

        Node p = this.sentinel.next;
        int count = 0;

        while (count != index) {
            p = p.next;
            count += 1;
        }

        return p.item;
    }

//    private Item recursiveHelper()

//    public Item getRecursive(int index) {
//        if (index >= this.size) {
//            return null;
//        }
//
//
//    }

    public Item removeFirst() {
        if (this.isEmpty()) {
            return null;
        }

        Node firstNode = this.sentinel.next;
        Item itemToReturn = firstNode.item;

        // re-wire to second node
        this.sentinel.next = firstNode.next;
        firstNode.next.prev = this.sentinel;

        this.size -= 1;
        firstNode = null;
        return itemToReturn;
    }

    public Item removeLast() {
        if (this.isEmpty()) {
            return null;
        }

        Node lastNode = this.sentinel.prev;
        Item itemToReturn = lastNode.item;

        this.sentinel.prev = lastNode.prev;
        lastNode.prev.next = this.sentinel;

        this.size -= 1;
        lastNode = null;
        return itemToReturn;
    }

    public void printDeque() {
        Node p = this.sentinel.next;

        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }
}
