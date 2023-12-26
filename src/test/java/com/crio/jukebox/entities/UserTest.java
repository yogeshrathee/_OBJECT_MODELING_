package com.crio.jukebox.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        user = new User("1", "Alice");
    }

    @Test
    void testGetName() {
        assertEquals("Alice", user.getName());
    }

    @Test
    void testHashCode() {
        User user1 = new User("1", "Alice");
        User user2 = new User("2", "Bob");
        assertEquals(user1.hashCode(), user.hashCode());
        assertNotEquals(user2.hashCode(), user.hashCode());
    }

    @Test
    void testEqualsObject() {
        User user1 = new User("1", "Alice");
        User user2 = new User("2", "Bob");
        assertTrue(user1.equals(user));
        assertFalse(user2.equals(user));
    }

    @Test
    void testToString() {
        assertEquals("User [id=1, name=Alice]", user.toString());
    }

}