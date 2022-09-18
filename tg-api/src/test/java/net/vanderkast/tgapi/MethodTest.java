package net.vanderkast.tgapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodTest {

    @Test
    void getName() {
        var method = new MockMethod();
        assertEquals("mockMethod", method.getName());
    }

    static class MockMethod implements Method {
    }

}
