package helpers;

import pojo.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemWrapper {
    private final List<Item> items;

    public ItemWrapper() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}
