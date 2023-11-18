package ru.urfu.container;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerTest {

    /**
     * Тест на добавление Item
     */
    @Test
    public void add() {
        Container container = new Container();
        Item item = new Item(1L);
        boolean result = container.add(item);
        assertTrue(result);
        assertEquals(container.size(), 1);
        assertEquals(container.get(0).getId(), 1L);
    }

    /**
     * Тест на удаление Item
     */
    @Test
    public void remove() {
        Container container = new Container();
        Item item1 = new Item(1L);
        Item item2 = new Item(2L);
        container.add(item1);
        container.add(item2);
        boolean result = container.remove(item1);
        assertTrue(result);
        assertEquals(container.size(), 1);
        assertEquals(container.get(0).getId(), 2L);
    }
}