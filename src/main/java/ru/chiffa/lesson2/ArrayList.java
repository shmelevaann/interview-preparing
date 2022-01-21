package main.java.ru.chiffa.lesson2;

import java.util.*;

//не успела LinkedList, но раньше его писала без имплементации List на курсе по алгоритмам
//если нужно могу найти на него ссылку
//в настоящий ArrayList не подглядывала, поэтому получился фаршик)
public class ArrayList<T> implements List<T> {
    private static final int MIN_ARRAY_LENGTH = 10;
    private static final double COMMON_INCREASE_RATE = 1.5;

    private T[] array;
    private int size = 0;

    public ArrayList() {
        array = (T[]) new Object[MIN_ARRAY_LENGTH];
    }

    public ArrayList(T[] array) {
        Objects.requireNonNull(array, "Array can't be null");
        for (T element : array) {
            Objects.requireNonNull(element, "Element can't be null");
        }

        this.array = array;
        size = array.length;
    }

    @Override
    public int indexOf(Object o) {
        Objects.requireNonNull(o, "Element can't be null");

        for (int index = 0; index < size; index++) {
            if (array[index].equals(o)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Objects.requireNonNull(o, "Element can't be null");

        for (int index = size - 1; index >= 0; index--) {
            if (array[index].equals(o)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Objects.requireNonNull(o, "Element can't be null");

        return indexOf(o) >= 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c, "Collection can't be null");

        return c.stream().allMatch(this::contains);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOfRange(array, 0, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        Objects.requireNonNull(a, "Specified array can't be null");

        if (a.length > size || a.length == 0) {
            return (T1[]) toArray();
        } else {
            System.arraycopy(array, 0, a, 0, a.length);
            return a;
        }
    }

    @Override
    public boolean add(T t) {
        add(size, t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        checkAddingIndex(index);
        Objects.requireNonNull(element, "Element can't be null");

        ensureCapacity(1);
        moveElements(index, 1);
        addElement(index, element);
    }

    private void addElement(int index, T element) {
        array[index] = element;
        size++;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkAddingIndex(index);
        Objects.requireNonNull(c, "Specified collection can't be null");
        checkNullElements(c);

        ensureCapacity(c.size());
        moveElements(index, c.size());
        for (T element : c) {
            addElement(index, element);
            index++;
        }
        return true;
    }

    private void moveElements(int index, int offset) {
        if (index != size) {
            System.arraycopy(array, index, array, index + offset, size - index);
        }
    }

    private void increaseArray(int extraIncrease) {
        array = Arrays.copyOf(
                array,
                (int) (array.length * COMMON_INCREASE_RATE) + extraIncrease);
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        return index >= 0 && removeElement(index);
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        T removed = array[index];
        removeElement(index);
        return removed;
    }

    private boolean removeElement(int index) {
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return subtractOrIntersection(c, true);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return subtractOrIntersection(c, false);
    }

    private boolean subtractOrIntersection(Collection<?> c, boolean isSubtract) {
        Objects.requireNonNull(c, "Specified collection can't be null");
        checkNullElements(c);

        boolean isModified = false;
        for (int index = size - 1; index >= 0; index--) {
            if (c.contains(array[index]) == isSubtract) {
                remove(index);
                isModified = true;
            }
        }
        return isModified;
    }

    private void ensureCapacity(int elementQuantity) {
        if (elementQuantity > array.length - size) {
            increaseArray(size + elementQuantity > array.length * COMMON_INCREASE_RATE ?
                    elementQuantity : 0);
        }
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        Objects.requireNonNull(element, "Element can't be null");

        T previous = array[index];
        array[index] = element;
        return previous;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        if (toIndex < 0) {
            throw new IndexOutOfBoundsException("toIndex can't be less than 0");
        }

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("Value of fromIndex can't be more than value of toIndex");
        }

        return new ArrayList<T>(Arrays.copyOfRange(array, fromIndex, Math.min(toIndex, size)));
    }

    private void checkAddingIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index can't be less than 0 or more than list size");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index can't be less than 0 or more than list size");
        }
    }

    private void checkNullElements(Collection<?> c) {
        for (Object element : c) {
            Objects.requireNonNull(element, "Element can't be null");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ArrayListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ArrayListIterator(index);
    }

    private enum IteratorState {
        MOVED_NEXT, MOVED_PREVIOUS, UNMODIFIABLE
    }


    private class ArrayListIterator implements ListIterator<T> {
        private int index = -1;
        private IteratorState state = IteratorState.UNMODIFIABLE;

        private ArrayListIterator() {
        }

        private ArrayListIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return size - index > 1;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element");
            }
            state = IteratorState.MOVED_NEXT;
            return array[++index];
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("There is no previous element");
            }
            state = IteratorState.MOVED_PREVIOUS;
            return array[--index];
        }

        @Override
        public int nextIndex() {
            return hasNext() ? index + 1 : size;
        }

        @Override
        public int previousIndex() {
            return hasPrevious() ? index - 1 : -1;
        }

        @Override
        public void remove() {
            if (state != IteratorState.UNMODIFIABLE) {
                ArrayList.this.remove(index);
            } else {
                throw new IllegalStateException(
                        "Element can be removed only after calling next() or previous()");
            }
            state = IteratorState.UNMODIFIABLE;
        }

        @Override
        public void set(T t) {
            if (state != IteratorState.UNMODIFIABLE) {
                ArrayList.this.set(index, t);
            } else {
                throw new IllegalStateException(
                        "Element can be set only after calling next() or previous()");
            }
        }

        @Override
        public void add(T t) {
            if (isEmpty()) {
                ArrayList.this.add(t);
                index++;
            } else {
                switch (state) {
                    case MOVED_NEXT:
                        ArrayList.this.add(--index, t);
                        state = IteratorState.UNMODIFIABLE;
                    case MOVED_PREVIOUS:
                        ArrayList.this.add(++index, t);
                        state = IteratorState.UNMODIFIABLE;
                    case UNMODIFIABLE:
                        throw new IllegalStateException(
                                "Element can be added only after calling next() or previous()");
                }
            }
        }
    }
}
