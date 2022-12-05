package client.gameObjects;

import client.utilities.Iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameObjectIterator extends Iterator<GameObject> {

    List<GameObject> list = null;
    int index;

    public GameObjectIterator(Collection<GameObject> list) {
        this.list = new ArrayList<GameObject>();
        this.list.addAll(list);
        index = -1;
    }

    @Override
    public boolean hasNext() {
        return index + 1 < list.size();
    }

    @Override
    public GameObject next() {
        return list.get(++index);
    }

    @Override
    public GameObject current() {
        return list.get(index);
    }
}
