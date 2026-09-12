import java.util.HashMap;

public class Items
{
    private Item[] allItem = new Item[10];
    private int size = 0;
    public HashMap<String, Integer> itemCounts = new HashMap<>();


    public void addItem(Item item)
    {
        Item queuedItem;
        if (item instanceof SingleItem)
        {
            addToItemCount(item);
            queuedItem = convertIfBundlePossible((SingleItem) item);

            if (queuedItem instanceof BundledItem)
            {
                for (int i = 0; i < ((BundledItem) queuedItem).getRemainingItemsIfBundleDisbanded(); i++)
                {
                    removeItem(queuedItem.getItemName());
                }
            }
        }

        else
        {
            queuedItem = item;
        }

        if (size == 0)
        {
            allItem[0] = queuedItem;
            size++;
        }
        else if (size == allItem.length)
        {
            allItem = resize(allItem);
            addItem(queuedItem);
        }
        else
        {
            for (int i = 0; i < size; i++)
            {
                addAndSortItem(queuedItem);
            }
            size++;
        }
    }

    private void addAndSortItem(Item item)
    {
        int insertSize = size;

        while (insertSize > 0 && allItem[insertSize - 1].getCostInCents() > item.getCostInCents())
        {
            allItem[insertSize] = allItem[insertSize - 1];
            insertSize--;
        }

        allItem[insertSize] = item;
    }

    private Item convertIfBundlePossible(SingleItem item)
    {
        if (item.isBundlePossible())
        {
            if (itemCounts.get(item.getItemName()) != 0
                && itemCounts.get(item.getItemName()) % item.getNumItemsNeededForBundle() == 0)
            {
                String name = item.getItemName();
                int bundledCostInCents = item.getItemBundlePrice();
                int singleCostInCents = item.getCostInCents();
                int numOfRemovedItems = item.getNumItemsNeededForBundle() - 1;
                return new BundledItem(name, bundledCostInCents,
                    singleCostInCents, numOfRemovedItems);
            }
        }

        return item;
    }

    private void addToItemCount(Item item)
    {
        if (itemCounts.get(item.getItemName()) == null)
        {
            itemCounts.put(item.getItemName(), 1);
        }
        else
        {
            itemCounts.put(item.getItemName(), itemCounts.get(item.getItemName()) + 1);
        }
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

    //TODO: streamline both remove methods and add more overloaded ones as see fit. Also fix any instance of between single and bundled items
    //TODO: IMPORTANT: need to code bundle remover using hashmap data - which already updates

    public void removeSingleItems(SingleItem targetItem)
    {
        int itemsToRemove = targetItem.getNumItemsNeededForBundle() - 1;
        for (int i = 0; i < size && itemsToRemove > 0; i++)
        {
            if (allItem[i] instanceof SingleItem &&
                allItem[i].getItemName().equals(targetItem.getItemName()))
            {
                removeItem(i);
                i--;
                itemsToRemove--;
            }
        }
    }

    public void removeBundledItem(BundledItem targetItem)
    {
        if (!(targetItem instanceof BundledItem))
        {
            throw new IllegalArgumentException("Item is not a BundledItem. It is a " +
                targetItem.getClass().getName() + ".");
        }

        for (int i = 0; i < size && itemCounts.get(targetItem.getItemName()) > 0; i++)
        {
            if (allItem[i] instanceof BundledItem)
            {
                removeItem(i);
                size--;
                for (int j = targetItem.getRemainingItemsIfBundleDisbanded(); j > 0; j--)
                {
                    SingleItem tempSingle = new SingleItem(targetItem.getItemName(), targetItem.getItemSinglePrice(),
                        true, false, targetItem.getRemainingItemsIfBundleDisbanded() + 1,
                        targetItem.getCostInCents());
                    addItem(tempSingle);
                }
                break;
            }
        }
    }

    public void removeItem(int index)
    {
        Item currentItem = allItem[index];
        if (currentItem instanceof SingleItem)
        {
            if (itemCounts.get(allItem[index].getItemName()) != 0)
            {
                if (itemCounts.get(allItem[index].getItemName()) == 1)
                {
                    itemCounts.remove(allItem[index].getItemName());
                }
                else
                {
                    itemCounts.put(currentItem.getItemName(), itemCounts.get(currentItem.getItemName()) - 1);
                }
            }
        }

        for (int i = index; i < size - 1; i++)
        {
            allItem[i] = allItem[i + 1];
        }

        size--;
        allItem[size] = null;
    }

    //removes the first index/one item only from the array
    public void removeItem(Item item)
    {
        for (int i = 0; i < size; i++)
        {
            if (allItem[i].getItemName().equals(item.getItemName()))
            {
                removeItem(i);
                break;
            }
        }
    }

    public void removeItem(String itemName)
    {
        for (int i = 0; i < size; i++)
        {
            if (allItem[i].getItemName().equals(itemName))
            {
                removeItem(i);
            }
        }
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
