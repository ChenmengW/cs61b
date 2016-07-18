/**
 * Created by varad on 7/18/16.
 */
public class ArrayDequeTest {

	/* Utility method for printing out empty checks. */
	public static boolean checkEmpty(boolean expected, boolean actual) {
		if (expected != actual) {
			System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

    public static boolean checkSize(int expected, int actual) {
		if (expected != actual) {
			System.out.println("size() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }

	public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");
		
		ArrayDeque<String> ad = new ArrayDeque<String>();
		boolean passed = checkEmpty(true, ad.isEmpty());

		ad.addFirst("front");
		passed = checkSize(1, ad.size()) && passed;
		passed = checkEmpty(false, ad.isEmpty()) && passed;

		ad.addLast("middle");
		passed = checkSize(2, ad.size()) && passed;

		ad.addLast("back");
		passed = checkSize(3, ad.size()) && passed;

		System.out.println("Printing out deque: ");
		ad.printDeque();

		printTestStatus(passed);
	}

	public static void addRemoveTest() {

		System.out.println("Running add/remove test.");
        
		ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, ad.isEmpty());

		ad.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, ad.isEmpty()) && passed;

		ad.removeFirst();
		// should be empty 
		passed = checkEmpty(true, ad.isEmpty()) && passed;

		printTestStatus(passed);
	}

    public static void resizeTest() {
        System.out.println("Running resize test.");

        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        boolean passed = checkEmpty(true, ad.isEmpty());
        passed = checkSize(8, ad.getReservedMemLength()) && passed;

        for (int i = 0; i < 10; i++) {
            ad.addLast(i);
        }

        passed = checkSize(10, ad.size()) && passed;
        passed = checkSize(16, ad.getReservedMemLength()) && passed;

        for (int i = 0; i < 30; i++) {
            ad.addLast(i);
        }

        passed = checkSize(40, ad.size()) && passed;
        passed = checkSize(64, ad.getReservedMemLength()) && passed;

        for (int i = 0; i < 25; i++) {
            ad.removeLast();
        }

        passed = checkSize(15, ad.size()) && passed;
        passed = checkSize(32, ad.getReservedMemLength()) && passed;

        for (int i = 0; i < 300; i++) {
            ad.addLast(i);
        }

        passed = checkSize(315, ad.size()) && passed;
        passed = checkSize(512, ad.getReservedMemLength()) && passed;

        for (int i = 0; i < 252; i++) {
            ad.removeLast();
        }

        passed = checkSize(63, ad.size()) && passed;        // Corner Case
        passed = checkSize(128, ad.getReservedMemLength()) && passed;

        printTestStatus(passed);
    }


    public static void main(String[] args) {
		System.out.println("Running tests.\n");
		addIsEmptySizeTest();
		addRemoveTest();
        resizeTest();
	}
}
