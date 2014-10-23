package com.gammadevs.inverview.samples.concurrency;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Naive CopyOnWriteArrayList implementation
 * Created by Anton on 10/22/2014.
 */
public class CopyOnWriteArrayList<E> implements List<E> {

    private final transient ReentrantLock lock = new ReentrantLock();
    private List<E> underlying = new ArrayList<E>();

    @Override
    public int size() {
        return underlying.size();
    }

    @Override
    public boolean isEmpty() {
        return underlying.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return underlying.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new UnmodifiableIterator<E>(underlying.iterator());
    }

    @Override
    public Object[] toArray() {
        return underlying.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return underlying.toArray(a);
    }

    @Override
    public boolean add(E e) {
        lock.lock();
        boolean result;
        try {
            List<E> temp = new ArrayList<E>(underlying);
            result = temp.add(e);
            underlying = temp;
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        lock.lock();
        boolean result;
        try {
            List<E> temp = new ArrayList<E>(underlying);
            result = temp.remove(o);
            underlying = temp;
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return underlying.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        lock.lock();
        boolean result;
        try {
            List<E> temp = new ArrayList<E>(underlying);
            result = temp.addAll(c);
            underlying = temp;
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        lock.lock();
        boolean result;
        try {
            List<E> temp = new ArrayList<E>(underlying);
            result = temp.addAll(index, c);
            underlying = temp;
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        lock.lock();
        boolean result;
        try {
            List<E> temp = new ArrayList<E>(underlying);
            result = temp.removeAll(c);
            underlying = temp;
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        lock.lock();
        boolean result;
        try {
            List<E> temp = new ArrayList<E>(underlying);
            result = temp.retainAll(c);
            underlying = temp;
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public void clear() {
        underlying = new ArrayList<E>();
    }

    @Override
    public E get(int index) {
        return underlying.get(index);
    }

    @Override
    public E set(int index, E element) {
        lock.lock();
        E result;
        try {
            List<E> temp = new ArrayList<E>(underlying);
            result = temp.set(index, element);
            underlying = temp;
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public void add(int index, E element) {
        lock.lock();
        try {
            List<E> temp = new ArrayList<E>(underlying);
            temp.add(index, element);
            underlying = temp;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E remove(int index) {
        lock.lock();
        E result;
        try {
            List<E> temp = new ArrayList<E>(underlying);
            result = temp.remove(index);
            underlying = temp;
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public int indexOf(Object o) {
        return underlying.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return underlying.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new UnmodifiableListIterator<E>(underlying.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new UnmodifiableListIterator<E>(underlying.listIterator(index));
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return underlying.subList(fromIndex, toIndex);
    }

    private static class UnmodifiableIterator<E> implements Iterator<E> {

        private final Iterator<E> iterator;

        public UnmodifiableIterator(Iterator<E> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            return iterator.next();
        }

    }

    private static class UnmodifiableListIterator<E> implements ListIterator<E> {

        private final ListIterator<E> iterator;

        private UnmodifiableListIterator(ListIterator<E> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            return iterator.next();
        }

        @Override
        public boolean hasPrevious() {
            return iterator.hasPrevious();
        }

        @Override
        public E previous() {
            return iterator.previous();
        }

        @Override
        public int nextIndex() {
            return iterator.nextIndex();
        }

        @Override
        public int previousIndex() {
            return iterator.previousIndex();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("set");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("add");
        }
    }

}
