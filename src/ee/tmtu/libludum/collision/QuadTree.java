package ee.tmtu.libludum.collision;

import java.util.ArrayList;
import java.util.List;

public class QuadTree<T extends Collidable> {

    private static final int MAX_DEPTH = 10;
    private static final int MAX_NODES = 10;

    public final int level;
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final QuadTree[] nodes;
    public final ArrayList<T> children;

    public boolean split;

    public QuadTree(int x, int y, int width, int height, int level) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.level = level;
        this.children = new ArrayList<>(QuadTree.MAX_NODES);
        this.nodes = new QuadTree[4];
    }

    public void add(T collidable) {
        int index = this.index(collidable);
        if (index != -1) {
            this.nodes[index].add(collidable);
            return;
        }
        this.children.add(collidable);

        if (this.children.size() >= QuadTree.MAX_NODES && this.level < QuadTree.MAX_DEPTH) {
            if (!this.split) {
                this.split();
            }
            Collidable c;
            for (int i = 0; i < this.children.size(); i++) {
                c = this.children.remove(i);
                int idx = this.index(c);
                if (idx != -1) {
                    this.nodes[idx].add(c);
                }
            }
        }
    }

    public void clear() {
        this.children.clear();

        for (int i = 0; i < this.nodes.length; i++) {
            if (this.nodes[i] != null) {
                this.nodes[i].clear();
                this.nodes[i] = null;
            }
        }
        this.split = false;
    }

    public void split() {
        if (this.level > QuadTree.MAX_DEPTH) return;

        int hw = this.width / 2;
        int hh = this.height / 2;

        this.nodes[0] = new QuadTree<T>(this.x, this.y, hw, hh, this.level + 1);
        this.nodes[1] = new QuadTree<T>(this.x, this.y + hh, hw, hh, this.level + 1);
        this.nodes[2] = new QuadTree<T>(this.x + hw, this.y + hh, hw, hh, this.level + 1);
        this.nodes[3] = new QuadTree<T>(this.x + hw, this.y, hw, hh, this.level + 1);

        this.split = true;
    }

    public int index(Collidable collidable) {
        int horizontal = this.y + this.height / 2;
        int vertical = this.x + this.width / 2;
        boolean top = collidable.getY() + collidable.getHeight() < horizontal;
        boolean bottom = collidable.getY() > horizontal;
        if (collidable.getX() + collidable.getWidth() < vertical) {
            if (top)
                return 0;
            else if (bottom)
                return 1;
        } else if (collidable.getX() > vertical) {
            if (top)
                return 3;
            else if (bottom)
                return 2;
        }
        return -1;
    }

    public void fetch(List<Collidable> lhs, Collidable collidable) {
        int index = this.index(collidable);
        if (index != -1 && this.split) {
            this.nodes[index].fetch(lhs, collidable);
        }
        lhs.addAll(this.children);
    }

    public void update() {

    }

}
