import java.util.HashMap;

public class Items
{
    private Item[] allItem = new Item[10];
    private int size = 0;
    // Counts purchased units, not array entries. A bundle contributes its full quantity.
    public HashMap<String, Integer> itemCounts = new HashMap<>();

    // Adds one single or all units represented by a bundle.
    public void addItem(Item item)
    {
        int newCount = itemCounts.getOrDefault(item.getItemName(), 0) + quantityOf(item);

        addAndSortItem(item);
        if (item instanceof SingleItem)
        {
            formBundleIfPossible((SingleItem) item);
        }
        itemCounts.put(item.getItemName(), newCount);
    }

    private void formBundleIfPossible(SingleItem item)
    {
        if (!item.isBundlePossible() || item.getNumItemsNeededForBundle() < 2)
        {
            return;
        }
        int singles = 0;
        for (int i = 0; i < size; i++)
        {
            if (allItem[i] instanceof SingleItem &&
                allItem[i].getItemName().equals(item.getItemName()))
            {
                singles++;
            }
        }
        int bundleQuantity = item.getNumItemsNeededForBundle();
        if (singles < bundleQuantity)
        {
            return;
        }

        // Conversion changes array entries, never the purchased quantity.
        int remaining = bundleQuantity;
        for (int i = size - 1; i >= 0 && remaining > 0; i--)
        {
            if (allItem[i] instanceof SingleItem &&
                allItem[i].getItemName().equals(item.getItemName()))
            {
                removeItem(i);
                remaining--;
            }
        }
        addAndSortItem(new BundledItem(item.getItemName(), item.getItemBundlePrice(),
            item.getCostInCents(), bundleQuantity - 1, item.isOnSale()));
    }

    /** Removes one purchased unit, preferring a single before breaking one bundle. */
    public void removeItem(String itemName)
    {
        int bundleIndex = -1;
        for (int i = 0; i < size; i++)
        {
            if (!allItem[i].getItemName().equals(itemName))
            {
                continue;
            }
            if (allItem[i] instanceof SingleItem)
            {
                removeItem(allItem[i]);
                return;
            }
            if (bundleIndex == -1)
            {
                bundleIndex = i;
            }
        }
        if (bundleIndex == -1)
        {
            return;
        }

        BundledItem bundle = (BundledItem) allItem[bundleIndex];
        removeItem(bundleIndex);
        for (int i = 0; i < bundle.getRemainingItemsIfBundleDisbanded(); i++)
        {
            // These units already exist in the count; do not add or bundle them again.
            addAndSortItem(new SingleItem(bundle.getItemName(), bundle.getItemSinglePrice(),
                true, bundle.isOnSale(), bundle.getRemainingItemsIfBundleDisbanded() + 1,
                bundle.getCostInCents()));
        }
        subtractUnits(itemName, 1);
    }

    /**
     * Removes one occurrence of this exact object. Removing a bundle removes all
     * its units without restoring singles. An object not in the list is a no-op.
     */
    public void removeItem(Item item)
    {
        for (int i = 0; i < size; i++)
        {
            if (allItem[i] == item)
            {
                removeItem(i);
                subtractUnits(item.getItemName(), quantityOf(item));
                return;
            }
        }
    }

    private void subtractUnits(String name, int quantity)
    {
        int remaining = itemCounts.get(name) - quantity;
        if (remaining == 0)
        {
            itemCounts.remove(name);
        }
        else
        {
            itemCounts.put(name, remaining);
        }
    }

    private static int quantityOf(Item item)
    {
        return item instanceof BundledItem ? ((BundledItem) item).getRemainingItemsIfBundleDisbanded() + 1 : 1;
    }

    private void addAndSortItem(Item item)
    {
        if (size == allItem.length)
        {
            allItem = resize(allItem);
        }
        int index = size;
        while (index > 0 && allItem[index - 1].getCostInCents() < item.getCostInCents())
        {
            allItem[index] = allItem[index - 1];
            index--;
        }
        allItem[index] = item;
        size++;
    }

    private void removeItem(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        for (int i = index; i < size - 1; i++)
        {
            allItem[i] = allItem[i + 1];
        }
        size--;
        allItem[size] = null;
    }

    public boolean containsItem(Item item)
    {
        for (int i = 0; i < size; i++)
        {
            if (allItem[i] == item)
            {
                return true;
            }
        }
        return false;
    }

    public boolean containsItem(String itemName)
    {
        for (int i = 0; i < size; i++)
        {
            if (allItem[i].getItemName().equals(itemName))
            {
                return true;
            }
        }

        return false;
    }

    private static Item[] resize(Item[] currItems)
    {
        Item[] newItems = new Item[currItems.length * 2];

        for (int i = 0; i < currItems.length; i++)
        {
            newItems[i] = currItems[i];
        }
        return  newItems;
    }

    public String toString()
    {
        String str = "Items size: " + size + "\n";
        for (int i = 0; i < size; i++)
        {
            str += ("item " + (i + 1) + ".) ") + allItem[i].toString() + "\n";
        }
        str += "Max potential size: " + allItem.length + "\n";
        return str;
    }
}
