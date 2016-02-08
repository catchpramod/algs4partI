import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by pramod on 2/5/16.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    public Deque() {                           // construct an empty deque
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {                 // is the deque empty?
        return size == 0;
    }

    public int size() {                        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {          // add the item to the front
        if (item == null) throw new NullPointerException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) {
            size++;
            last = first;
            return;
        }
        size++;
        first.next = oldFirst;
        oldFirst.previous = first;
    }

    public void addLast(Item item) {           // add the item to the end
        if (item == null) throw new NullPointerException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            size++;
            first = last;
            return;
        }
        size++;
        last.previous = oldLast;
        oldLast.next = last;
    }

    public Item removeFirst() {                // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        size--;
        if (first == null) {
            last = first;
            return item;
        }
        first.previous = null;
        return item;
    }

    public Item removeLast() {                 // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        size--;
        if (last == null) {
            first = last;
            return item;
        }
        last.next = null;
        return item;
    }

    public Iterator<Item> iterator() {         // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }


    public static void main(String[] args) {   // unit testing
        Deque<String> list = new Deque<>();
        list.addLast("Hi");
        list.addLast("There!");
        list.addLast("How");
        list.addLast("are");
        list.addLast("you?");
        list.addFirst("Yo!");
        list.removeLast();
        list.removeFirst();
        list.removeLast();
        list.removeLast();
        list.removeLast();
        list.removeLast();
        list.addFirst("sup?");
        Iterator<String> listIterator = list.iterator();
        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }
    }

}