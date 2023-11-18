package ru.urfu.note;

import org.junit.Test;

import static org.junit.Assert.*;

public class NoteLogicTest {

    /**
     * Протестировать функции /add и /notes
     */
    @Test
    public void testAddAndNotes() {
        NoteLogic logic = new NoteLogic();
        logic.handleMessage("/add Buy milk");
        assertEquals("Your notes:\n1. Buy milk", logic.handleMessage("/notes"));
    }

    /**
     * Протестировать функцию /edit
     */
    @Test
    public void testEdit() {
        NoteLogic logic = new NoteLogic();
        logic.handleMessage("/add Buy apples");
        logic.handleMessage("/edit 1 Buy fruits");
        assertEquals("Your notes:\n1. Buy fruits", logic.handleMessage("/notes"));
    }

    /**
     * Протестировать функцию /del
     */
    @Test
    public void testDel() {
        NoteLogic logic = new NoteLogic();
        logic.handleMessage("/add Food my cat");
        logic.handleMessage("/add Food my dog");
        logic.handleMessage("/del 2");
        assertEquals("Your notes:\n1. Food my cat", logic.handleMessage("/notes"));
    }
}