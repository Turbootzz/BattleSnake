package nl.hu.bep;

import org.junit.jupiter.api.Test;
import nl.hu.bep.battlesnek.model.SnakeAppearance;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeAppearanceTest {
    @Test
    void testDefaultConstructorSetsDefaultValues() {
        SnakeAppearance appearance = new SnakeAppearance();

        assertEquals("1", appearance.getApiversion());
        assertEquals("turbootzz", appearance.getAuthor());
        assertEquals("#00FF00", appearance.getColor());
        assertEquals("default", appearance.getHead());
        assertEquals("default", appearance.getTail());
        assertEquals("1.0", appearance.getVersion());
    }

    @Test
    void testGettersAndSetters() {
        SnakeAppearance appearance = new SnakeAppearance();

        appearance.setColor("#13588");
        appearance.setHead("evil");
        appearance.setTail("round-bum");
        appearance.setAuthor("henk");
        appearance.setApiversion("2");
        appearance.setVersion("2.5");

        assertEquals("#13588", appearance.getColor());
        assertEquals("evil", appearance.getHead());
        assertEquals("round-bum", appearance.getTail());
        assertEquals("henk", appearance.getAuthor());
        assertEquals("2", appearance.getApiversion());
        assertEquals("2.5", appearance.getVersion());
    }
}
