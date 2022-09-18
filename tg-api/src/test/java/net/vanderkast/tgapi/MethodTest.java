package net.vanderkast.tgapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MethodTest {

    static class MockMethod implements Method {}

    @Test
    void getName() {
        var method = new MockMethod();
        assertEquals("mockMethod", method.getName());
    }

}
