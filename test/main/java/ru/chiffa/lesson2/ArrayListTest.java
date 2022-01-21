package main.java.ru.chiffa.lesson2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListTest {
    @Test
    public void addElementTest() {
        List<Integer> list = prepareList(1);

        assertEquals(1, list.size());
        assertThrows(NullPointerException.class, () -> {list.add(null);});
        assertIterableEquals(Arrays.asList(0), list);
    }

    @Test
    public void addAndIncreaseTest() {
        List<Integer> list = prepareList(10);

        assertEquals(10, list.size());
        assertIterableEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), list);
    }

    @Test
    public void addByIndexTest() {
        List<Integer> list = prepareList(10);
        list.add(5, 100);

        assertEquals(11, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(-1, 0);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add(100, 0);});
        assertDoesNotThrow(() -> {list.add(list.size(), 0);});
        assertIterableEquals(Arrays.asList(0, 1, 2, 3, 4, 100, 5, 6, 7, 8, 9), list);
    }

    @Test
    public void addAllTest() {
        List<Integer> list = prepareList(10);
        list.addAll(0, Arrays.asList(12, 13, 14));

        assertEquals(13, list.size());
        assertThrows(NullPointerException.class, () -> {list.addAll(null);});
        assertThrows(NullPointerException.class, () -> {list.addAll(Arrays.asList(0, null));});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.addAll(-1, Arrays.asList(0 ,1));});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.addAll(100, Arrays.asList(0 ,1));});
        assertDoesNotThrow(() -> {list.addAll(list.size(), Arrays.asList(0 ,1));});
        assertIterableEquals(Arrays.asList(12, 13, 14, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
    }

    @Test
    public void containsTest() {
        List<Integer> list = prepareList(10);
        assertTrue(list.contains(3));
        assertTrue(list.containsAll(Arrays.asList(0, 2)));
        assertFalse(list.containsAll(Arrays.asList(14, 2)));
    }

    @Test
    public void removeByIndexTest() {
        List<Integer> list = prepareList(10);
        list.remove(2);

        assertEquals(9, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> {list.remove(27);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.remove(-27);});
        assertThrows(IndexOutOfBoundsException.class, () -> {list.remove(list.size());});
        assertIterableEquals(Arrays.asList(0, 1, 3, 4, 5, 6, 7, 8, 9), list);
    }

    @Test
    public void removeByValueTest() {
        List<Integer> list = prepareList(10);
        list.add(2);
        list.remove(new Integer(2));

        assertEquals(10, list.size());
        assertTrue(list.remove(new Integer(0)));
        assertFalse(list.remove(new Integer(0)));
        assertIterableEquals(Arrays.asList(0, 1, 3, 4, 5, 6, 7, 8, 9, 2), list);
    }

    @Test
    public void removeAllTest() {
        List<Integer> list = prepareList(10);
        list.add(2);
        list.removeAll(Arrays.asList(2, 4));

        assertIterableEquals(Arrays.asList(0, 1, 3, 5, 6, 7, 8, 9), list);
        assertTrue(list.removeAll(Arrays.asList(0, 1)));
        assertFalse(list.removeAll(Arrays.asList(0, 1)));
        assertThrows(NullPointerException.class, () -> {list.removeAll(Arrays.asList(0, null));});
        assertThrows(NullPointerException.class, () -> {list.removeAll(null);});
        assertEquals(9, list.size());
    }

    @Test
    public void indexOfTest() {
        List<Integer> list = prepareList(10);

        assertEquals(2, list.indexOf(2));
        assertEquals(-1, list.indexOf(10));
    }

    @Test
    public void clearTest() {
        List<Integer> list = prepareList(10);
        list.clear();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void subListTest() {
        List<Integer> list = prepareList(10);

        assertThrows(IndexOutOfBoundsException.class, () ->{list.subList(-1, 2);});
        assertThrows(IndexOutOfBoundsException.class, () ->{list.subList(0, 20);});
        assertThrows(IndexOutOfBoundsException.class, () ->{list.subList(0, list.size());});
        assertThrows(IllegalArgumentException.class, () ->{list.subList(8, 0);});
        assertIterableEquals(Arrays.asList(1, 2), list.subList(1, 3));
    }

    @Test
    public void getTest() {
        List<Integer> list = prepareList(10);

        assertEquals(2, list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () ->{list.get(-1);});
        assertThrows(IndexOutOfBoundsException.class, () ->{list.get(20);});
        assertThrows(IndexOutOfBoundsException.class, () ->{list.get(list.size());});
    }

    @Test
    public void setTest() {
        List<Integer> list = prepareList(10);
        list.set(2, 100);

        assertEquals(10, list.size());
        assertThrows(IndexOutOfBoundsException.class, () ->{list.set(-1, 0);});
        assertThrows(IndexOutOfBoundsException.class, () ->{list.set(20, 0);});
        assertThrows(IndexOutOfBoundsException.class, () ->{list.set(list.size(), 0);});
        assertIterableEquals(Arrays.asList(0, 1, 100, 3, 4, 5, 6, 7, 8, 9), list);
    }

    @Test
    public void toArrayTest() {
        List<Integer> list = prepareList(10);

        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, list.toArray());
        //todo toArray(t1)
    }

    @Test
    public void retainAll() {
        List<Integer> list = prepareList(10);
        list.retainAll(Arrays.asList(0, 1, 2));

        assertEquals(3, list.size());
        assertIterableEquals(Arrays.asList(0, 1, 2), list);

        list.retainAll(Arrays.asList(0, 1, 5, 8));

        assertEquals(2, list.size());
        assertIterableEquals(Arrays.asList(0, 1), list);
        assertThrows(NullPointerException.class, () -> {list.retainAll(Arrays.asList(0, null));});
        assertThrows(NullPointerException.class, () -> {list.retainAll(null);});
    }

    //retainAll
    //iterator
    //listIterator

    private List<Integer> prepareList(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }
}
