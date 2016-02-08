import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by pramod on 2/5/16.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;
    private int lastIndex;

    public RandomizedQueue() {                 // construct an empty randomized queue
        queue = (Item[]) new Object[1];
        size = 0;
        lastIndex = -1;
    }

    public boolean isEmpty() {                 // is the queue empty?
        return size == 0;
    }

    public int size() {                // return the number of items on the queue
        return size;
    }

    public void enqueue(Item item) {           // add the item
        if (item == null) throw new NullPointerException();
        if (lastIndex == queue.length - 1) resize(queue.length * 2);
        queue[++lastIndex] = item;
        size++;
    }

    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        int j = 0;
        for (int i = 0; i <= lastIndex; i++) {
            if (queue[i] != null) copy[j++] = queue[i];
        }
        queue = copy;
        lastIndex = j - 1;
    }

    public Item dequeue() {          // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException();
        int index;
        Item item;
        do {
            index = StdRandom.uniform(lastIndex + 1);
            item = queue[index];
        } while (item == null);
        size--;
        queue[index] = null;
        if (index == lastIndex) lastIndex--;
        if (size > 0 && size == queue.length / 4) resize(queue.length / 2);
        return item;
    }

    public Item sample() {         // return (but do not remove) a random item
        if (isEmpty()) throw new NoSuchElementException();
        int index;
        Item item;
        do {
            index = StdRandom.uniform(lastIndex + 1);
            item = queue[index];
        } while (item == null);
        return item;
    }


    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        RandomizedQueueIterator rqi = new RandomizedQueueIterator();
        Item[] copy = (Item[]) new Object[size];
        int j = 0;
        for (int i = 0; i <= lastIndex; i++) {
            if (queue[i] != null) copy[j++] = queue[i];
        }
        StdRandom.shuffle(copy);
        rqi.rIndex = copy;
        return rqi;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private Item[] rIndex;

        @Override
        public boolean hasNext() {
            return current < rIndex.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return rIndex[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {   // unit testing
        RandomizedQueue<String> list = new RandomizedQueue<>();
        list.enqueue("Hi");
        list.enqueue("There!");
//        System.out.println(list.dequeue());
        list.enqueue("How");
        list.enqueue("are");
//        System.out.println(list.dequeue());
        list.enqueue("you?");
        list.enqueue("Yo!");
//        System.out.println(list.dequeue());
//        System.out.println(list.dequeue());
//        System.out.println(list.dequeue());
//        System.out.println(list.dequeue());
        Iterator<String> listIterator = list.iterator();
        list.enqueue("last!");

        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }
        System.out.println("=================");
        Iterator<String> listIterator1 = list.iterator();
        while (listIterator1.hasNext()) {
            System.out.println(listIterator1.next());
        }
    }
}
