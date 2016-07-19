/**
 * Created by varad on 7/19/16.
 */

import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDeque1B {

    @Test
    public void testSize() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<Integer>();
        assertTrue(sad1.isEmpty());
        assertEquals(0, sad1.size());

        FailureSequence fs = new FailureSequence();

        for (int i = 0; i < 10; i++) {
            sad1.addFirst(i);
            DequeOperation dequeOp1 = new DequeOperation("addFirst", i);
            fs.addOperation(dequeOp1);
        }

        assertEquals(10, sad1.size());

        for (int j = 0; j < 11; j++) {
            sad1.removeFirst();
            DequeOperation dequeOp2 = new DequeOperation("removeFirst");
            fs.addOperation(dequeOp2);
        }

        assertTrue(sad1.isEmpty());

        DequeOperation dequeOp3 = new DequeOperation("size");
        fs.addOperation(dequeOp3);

        assertEquals(fs.toString(), 0, sad1.size());   // HURRAY! ERROR!
    }

    @Test
    public void testRemoveFirst() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<Integer>();

        for (int i = 0; i < 2; i++) {
            sad1.addLast(i);
        }

        for (int j = 0; j < 7; j++) {
            sad1.addFirst(j);
        }

        int first = sad1.removeFirst();
        assertEquals(6, first);
    }

    @Test
    public void testRemoveLast() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<Integer>();
        for (int i = 0; i < 10; i++) {
            sad1.addLast(i);
        }

        int last = sad1.removeLast();
        assertEquals(9, last);
        assertEquals(9, sad1.size());
    }

    @Test
    public void testGet() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<Integer>();

        for (int i = 0; i < 2; i++) {
            sad1.addLast(i);
        }

        for (int j = 0; j < 7; j++) {
            sad1.addFirst(j);
        }

        sad1.printDeque();

        int itemAtIndex = sad1.get(7);
        assertEquals(0, itemAtIndex);

    }

    public static void main(String... args) {
        jh61b.junit.TestRunner.runTests("all", TestArrayDeque1B.class);
    }
}
