/**
 * Created by varad on 7/18/16.
 */
public class ArrayDeque<Item> {

    private Item[] items;
	private int size;
    private int reservedMemLength;
    private int frontPointer;
    private int backPointer;

    // To alter the this.size of the array flexibly
	private static int RESIZE_FACTOR = 2;
    private static double USAGE_FACTOR = 0.25;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        reservedMemLength = 8;
        frontPointer = 0;
        backPointer = 1;
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

    // For test purposes only
    public int getReservedMemLength() { return reservedMemLength; }

    private boolean isFull() {
        if (this.size == this.reservedMemLength) {
            return true;
        }
        return false;
    }

    private int minusOne(int index) {
        int prevIndex = index - 1;

        if (prevIndex < 0) {
            prevIndex += this.reservedMemLength;
        }

        return prevIndex;
    }

    private int plusOne (int index) {
        int nextIndex = index + 1;

        if (nextIndex >= this.reservedMemLength) {
            nextIndex %= this.reservedMemLength;
        }

        return nextIndex;
    }

    private void generateNewArray(int newArrayLength, String flag) {

        Item[] newArray = (Item[]) new Object[newArrayLength];


        if (flag.equals("increase")) {
            // Copy existing array. For clarity, we will simply
            // unroll the circular array and place it as a
            // straight line within newArray

            // This should be plusOne and not + 1 in case
            // frontPointer is at last array position
            int startIndex = this.plusOne(this.frontPointer);   // Array always starts here

            // Copy first portion of array (frontPointer+1 -> end)
            int firstLength = this.size - startIndex;          // size is still the number of elements
                                                               // in the original array

            // Copy to index 1 and onward, so that we can set 0 as
            // frontPointer in newArray
            System.arraycopy(this.items, startIndex,       // A new array is, without exception, only
                            newArray, 1, firstLength);     // created when the original is full. So
                                                           // we can get all elements till the end.


            // Copy next portion of array (start -> backPointer)
            int secondLength = this.backPointer;

            // Earlier step copies firstLength number of items to
            // newArray starting from index 1. Therefore the new copy
            // should start at index firstLength + 1

            System.arraycopy(this.items, 0,
                             newArray, firstLength + 1,
                             secondLength);

        } else if (flag.equals("decrease")){

            // This does not involve circular arrays. So we simply copy
            // straight arrays from a larger to a smaller container.
            // REMEMBER : The array starts at index 1. Index 0 is for frontPointer.

            System.arraycopy(this.items, 1,
                             newArray, 1, this.size);
        }

        // Housekeeping : set all the remaining member variables
        // to their appropriate values.

        this.items = newArray;
        this.reservedMemLength = newArrayLength;
        this.frontPointer = 0;
        this.backPointer = this.size + 1;  // end of the new array


        // NOTE : this.size does not change, since the size is
        // representative of the number of elements in the array,
        // which is still what it was earlier

    }

    public void addFirst(Item x) {
        if (isFull()) {
            // generate new array
            int newArrayLength = this.reservedMemLength * RESIZE_FACTOR;
            this.generateNewArray(newArrayLength, "increase");
        }

        this.items[this.frontPointer] = x;
        this.frontPointer = minusOne(this.frontPointer);
        this.size += 1;
    }

    public void addLast(Item x) {
        if (isFull()) {
            // generate new array
            int newArrayLength = this.reservedMemLength * RESIZE_FACTOR;
            this.generateNewArray(newArrayLength, "increase");
        }

        this.items[this.backPointer] = x;
        this.backPointer = plusOne(this.backPointer);
        this.size += 1;
    }

    private int getTrueIndex(int index) {
        int trueIndex = (index + this.frontPointer + 1) % this.reservedMemLength;
        return trueIndex;
    }

    public Item get(int index) {
        if (index < this.size) {
            int trueIndex = this.getTrueIndex(index);
            return this.items[trueIndex];
        }

        return null;
    }

    private void set(int index, Item x) {
        // living on the edge here. no checks on the index,
        // since we trust that set will be used with valid
        // indices only

        int trueIndex = this.getTrueIndex(index);
        this.items[trueIndex] = x;
    }

    private void maintainUsageFactor() {
        if (this.reservedMemLength >= 16) {
            double usageFactor = ((double) this.size) / this.reservedMemLength;

            if (usageFactor < 0.25) {
                int newArrayLength = this.reservedMemLength / RESIZE_FACTOR;
                this.generateNewArray(newArrayLength, "decrease");
            }
        }
    }

    public Item removeFirst() {
        if (this.size == 0) {
            return null;
        }

        Item first = this.get(0);

        // null out at the index of the item being removed
        // to prevent loitering
        this.set(0, null);

        // Housekeeping
        this.frontPointer = this.plusOne(this.frontPointer);
        this.size -= 1;
        maintainUsageFactor();

        return first;
    }

    public Item removeLast() {
        if (this.size == 0) {
            return null;
        }

        Item last = this.get(this.size - 1);

        // null out at the index of the item being removed
        // to prevent loitering
        this.set(this.size - 1, null);

        // Housekeeping
        this.backPointer = this.minusOne(this.backPointer);
        this.size -= 1;
        maintainUsageFactor();

        return last;
    }

    public void printDeque() {
        for (int i = 0; i < this.size; i++) {
            System.out.print(this.get(i) + " ");
        }
    }

//    private boolean checkValidIndex(int index) {
//        if (index < 0) {
//            return false;
//        } else if (this.frontPointer > this.backPointer) {
//            if (index >= this.backPointer && index <= this.frontPointer) {
//                return false;
//            }
//        } else if (this.backPointer > this.frontPointer) {
//            if (index >= this.backPointer) {
//                return false;
//            }
//        }
//
//        return true;
//    }

}
