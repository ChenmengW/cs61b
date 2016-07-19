/**
 * Created by varad on 7/19/16.
 */

import static org.junit.Assert.*;
import org.junit.Test;

public class TestLinkedListDeque1B {

    @Test
    public void testGet() {
        StudentLinkedListDeque<Integer> sad1 = new StudentLinkedListDeque<Integer>();

        FailureSequence fs = new FailureSequence();

        for (int i = 0; i < 2; i++) {
            sad1.addLast(i);
            DequeOperation dequeOp1 = new DequeOperation("addLast", i);
            fs.addOperation(dequeOp1);
        }

        for (int j = 0; j < 7; j++) {
            sad1.addFirst(j);
            DequeOperation dequeOp2 = new DequeOperation("addFirst", j);
            fs.addOperation(dequeOp2);
        }

        int itemAtIndex = sad1.get(10);     // Index out of bounds, yet returns a value.
                                            // HURRAY! ERROR!

        DequeOperation dequeOp3 = new DequeOperation("get");
        fs.addOperation(dequeOp3);

        assertEquals(fs.toString(), null, itemAtIndex);

    }

    public static void main(String... args) {
        jh61b.junit.TestRunner.runTests("all", TestLinkedListDeque1B.class);
    }
}
