import java.util.ArrayList;

/**
 * A Generic heap class. Unlike Java's priority queue, this heap doesn't just
 * store Comparable objects. Instead, it can store any type of object
 * (represented by type T), along with a priority value.
 */
public class ArrayHeap<T> {
	private ArrayList<Node> contents = new ArrayList<Node>();

    public ArrayHeap() {
        Node zeroNode = new Node(null, Double.NEGATIVE_INFINITY);   // not to bother about 0th entry
        this.setNode(0, zeroNode);
//        this.contents.add(zeroNode);
    }

	/**
	 * Inserts an item with the given priority value. This is enqueue, or offer.
	 */
	public void insert(T item, double priority) {
        Node nodeToAdd = new Node(item, priority);
        int indexToAdd = this.contents.size();

        // Always inserted at the end of the list. Index : size()
        this.setNode(indexToAdd, nodeToAdd);
        bubbleUp(indexToAdd);

        // Better to use size() rather than indexOf() because whatever the
        // underlying implementation, indexOf will be O(N), but size O(1)
        // bubbleUp(contents.indexOf(toAdd));
	}

	/**
	 * Returns the Node with the smallest priority value, but does not remove it
	 * from the heap.
	 */
	public Node peek() {

        return this.getNode(1);// least priority element will always be @ index 1

        // v1.0 : w/o using get/setNode methods
//        if (this.contents.size() < 2) {
//            return null;
//        }
//
//		return this.contents.get(1);    // least priority element will always be @ index 1
	}

	/**
	 * Returns the Node with the smallest priority value, and removes it from
	 * the heap. This is dequeue, or poll.
	 */
	public Node removeMin() {
        // v1.0 : w/o using get/setNode methods
		int indexLast = this.contents.size() - 1;

        swap(1, indexLast);     // swap min and bottom right-most

        Node nodeToReturn = this.contents.remove(indexLast);    // pain to use get
        bubbleDown(1);

        return nodeToReturn;
	}

	/**
	 * Change the node in this heap with the given item to have the given
	 * priority. For this method only, you can assume the heap will not have two
	 * nodes with the same item. Check for item equality with .equals(), not ==
	 */
	public void changePriority(T item, double priority) {
        // v1.0 : w/o using get/setNode methods
        for (Node node: this.contents) {
            if (node.item().equals(item)) {
                double oldPriority = node.priority();
                if (oldPriority == priority) {
                    return;
                }

                int nodeIndex = this.contents.indexOf(node);

                // No method to set priority explicitly in Node,
                // so put in a new Node
                Node newNode = new Node(item, priority);
                this.setNode(nodeIndex, newNode);

                if (oldPriority < priority) {
                    bubbleUp(nodeIndex);
                } else {
                    bubbleDown(nodeIndex);
                }
            }
        }
	}

	/**
	 * Prints out the heap sideways.
	 */
	@Override
	public String toString() {
		return toStringHelper(1, "");
	}

	/* Recursive helper method for toString. */
	private String toStringHelper(int index, String soFar) {
		if (getNode(index) == null) {
			return "";
		} else {
			String toReturn = "";
			int rightChild = getRightOf(index);
			toReturn += toStringHelper(rightChild, "        " + soFar);
			if (getNode(rightChild) != null) {
				toReturn += soFar + "    /";
			}
			toReturn += "\n" + soFar + getNode(index) + "\n";
			int leftChild = getLeftOf(index);
			if (getNode(leftChild) != null) {
				toReturn += soFar + "    \\";
			}
			toReturn += toStringHelper(leftChild, "        " + soFar);
			return toReturn;
		}
	}

	private Node getNode(int index) {
		if (index >= contents.size()) {
			return null;
		} else {
			return contents.get(index);
		}
	}

	private void setNode(int index, Node n) {
		// In the case that the ArrayList is not big enough
		// add null elements until it is the right size
		while (index + 1 > contents.size()) {      // why >= ? adds 2 places, edited to make >
			contents.add(null);
		}
		contents.set(index, n);
	}

	/**
	 * Swap the nodes at the two indices.
	 */
	private void swap(int index1, int index2) {
		Node node1 = getNode(index1);
		Node node2 = getNode(index2);
		this.contents.set(index1, node2);
		this.contents.set(index2, node1);
	}

	/**
	 * Returns the index of the node to the left of the node at i.
	 */
	private int getLeftOf(int i) {
		return 2*i;
	}

	/**
	 * Returns the index of the node to the right of the node at i.
	 */
	private int getRightOf(int i) {
		return 2*i + 1;
	}

	/**
	 * Returns the index of the node that is the parent of the node at i.
	 */
	private int getParentOf(int i) {
		int parentIndex = (int) i / 2;  // redundant cast, but for clarity
		return parentIndex;
	}

	/**
	 * Adds the given node as a left child of the node at the given index.
	 */
	private void setLeft(int index, Node n) {
        int leftChildIndex = this.getLeftOf(index);
		this.setNode(leftChildIndex, n);
	}

	/**
	 * Adds the given node as the right child of the node at the given index.
	 */
	private void setRight(int index, Node n) {
        int rightChildIndex = this.getLeftOf(index);
        this.setNode(rightChildIndex, n);
	}

	/**
	 * Bubbles up the node currently at the given index.
	 */
	private void bubbleUp(int index) {
		int parentIndex = this.getParentOf(index);

        while (parentIndex != this.min(parentIndex, index)) {
            swap(parentIndex, index);
            index = parentIndex;
            parentIndex = this.getParentOf(index);
        }
	}

	/**
	 * Bubbles down the node currently at the given index.
	 */
	private void bubbleDown(int index) {
		int leftChildIndex = this.getLeftOf(index);
        int rightChildIndex = this.getRightOf(index);
        int childToSwapIndex = this.min(leftChildIndex, rightChildIndex);

        while (childToSwapIndex < this.contents.size() &&
                childToSwapIndex == this.min(index, childToSwapIndex)) {

            swap(index, childToSwapIndex);
            index = childToSwapIndex;

            // repetitive but will change that later
            leftChildIndex = this.getLeftOf(index);
            rightChildIndex = this.getRightOf(index);
            childToSwapIndex = this.min(leftChildIndex, rightChildIndex);
        }
	}

	/**
	 * Returns the index of the node with smaller priority. Precondition: Not
	 * both of the nodes are null.
	 */
	private int min(int index1, int index2) {
		Node node1 = getNode(index1);
		Node node2 = getNode(index2);
		if (node1 == null) {
			return index2;
		} else if (node2 == null) {
			return index1;
		} else if (node1.myPriority < node2.myPriority) {
			return index1;
		} else {
			return index2;
		}
	}

	public class Node {
		private T myItem;
		private double myPriority;

		private Node(T item, double priority) {
			myItem = item;
			myPriority = priority;
		}

		public T item() {
			return myItem;
		}

		public double priority() {
			return myPriority;
		}

		@Override
		public String toString() {
			return item().toString() + ", " + priority();
		}
	}

	public static void main(String[] args) {
		ArrayHeap<String> heap = new ArrayHeap<String>();
		heap.insert("c", 3);
		heap.insert("i", 9);
		heap.insert("g", 7);
		heap.insert("d", 4);
		heap.insert("a", 1);
		heap.insert("h", 8);
		heap.insert("e", 5);
		heap.insert("b", 2);
		heap.insert("c", 3);
		heap.insert("d", 4);
		System.out.println(heap);
//        System.out.println(heap.contents.toString());
	}

}
