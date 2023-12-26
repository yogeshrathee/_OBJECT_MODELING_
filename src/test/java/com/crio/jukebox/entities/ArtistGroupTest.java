package com.crio.jukebox.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArtistGroupTest {
    private ArtistGroup artist1;
    private ArtistGroup artist2;

    @BeforeEach
    void setUp() {
        artist1 = new ArtistGroup("1", "John Doe");
        artist2 = new ArtistGroup("2", "Jane Smith");
    }

    @Test
    void testGetName() {
        Assertions.assertEquals("John Doe", artist1.getName());
        Assertions.assertEquals("Jane Smith", artist2.getName());
    }

    @Test
    void testEquals() {
        ArtistGroup artist3 = new ArtistGroup("3", "John Doe");
        ArtistGroup artist4 = new ArtistGroup("4", "Jane Smith");

        Assertions.assertEquals(artist1, artist3);
        Assertions.assertNotEquals(artist1, artist2);
        Assertions.assertEquals(artist2, artist4);
    }

    @Test
    void testHashCode() {
        ArtistGroup artist3 = new ArtistGroup("3", "John Doe");
        ArtistGroup artist4 = new ArtistGroup("4", "Jane Smith");

        Assertions.assertEquals(artist1.hashCode(), artist3.hashCode());
        Assertions.assertNotEquals(artist1.hashCode(), artist2.hashCode());
        Assertions.assertEquals(artist2.hashCode(), artist4.hashCode());
    }

    @Test
    void testToString() {
        Assertions.assertEquals("ArtistGroup [id=1, name=John Doe]", artist1.toString());
        Assertions.assertEquals("ArtistGroup [id=2, name=Jane Smith]", artist2.toString());
    }
}