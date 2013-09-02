package ee.tmtu.libludum.util;

import java.util.Iterator;
import java.util.LinkedList;

public class Pool<T extends Poolable> {

    private int size;
    private LinkedList<T> pool;

    public Pool(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void add(T poolable) {
        if (this.pool.size() <= this.size) {
            this.pool.add(poolable);
        }
    }

    public Poolable get() {
        Iterator<T> iterator = this.pool.iterator();
        Poolable node = null;
        while (iterator.hasNext()) {
            node = iterator.next();
            if (node.isDead()) {
                break;
            }
        }
        return node;
    }

}
