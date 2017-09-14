package com.spittr.learn;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

/**
 * Created by xjshi.
 */
public class TestShoppingCart extends TestCase {
    double unitPrice = 5;
    double quantity = 6;
    double discount = 0.2;

    @Before
    public void setUp() throws Exception {
        System.out.println("Up");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Down");
    }

    public void testPay() {
        double total = unitPrice * quantity;
        assertEquals(30.0, total);
    }

    public void testPayWithDiscount() {
        double total = unitPrice * quantity * (1 - discount);
        assertEquals(24.0, total);
    }
}
