import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by pramod on 1/27/16.
 */
public class PercolationTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsOpen() {
        Percolation percolation = new Percolation(6);

//        percolation.open(1, 1);
        assertTrue(percolation.isFull(1, 1));

//
//        percolation.open(1,6);
//        percolation.open(2,6);
//        percolation.open(3,6);
//        percolation.open(4,6);
//        percolation.open(5,6);
//        percolation.open(5,5);
//        percolation.open(4,4);
//        percolation.open(3,4);
//        percolation.open(2,4);
//        percolation.open(2,3);
//        percolation.open(2,2);
//        percolation.open(2,1);
//        percolation.open(3,1);
//        percolation.open(4,1);
//        percolation.open(5,1);
//        percolation.open(5,2);
//        percolation.open(6,2);
//        percolation.open(5,4);
//        assertFalse(percolation.isFull(1,1));
    }

    @Test
    public void testIsOpenInput3() {
        Percolation percolation = new Percolation(3);

        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 1);
        assertFalse(percolation.isFull(3, 1));
//        percolation.open(2, 1);
//        percolation.open(1, 1);
//        assertTrue(percolation.isFull(1, 1));

    }

}