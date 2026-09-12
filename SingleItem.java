public class SingleItem extends Item
{
    private boolean isBundlePossible;
    private boolean isOnSale;
    private int numItemsNeededForBundle;
    private int itemBundlePrice;


    public SingleItem(String itemName, int costInCents, boolean isBundlePossible, boolean isOnSale,
                      int itemsNeededForBundle, int itemBundlePrice)
    {
        super(itemName, costInCents);
        this.isBundlePossible = isBundlePossible;
        this.isOnSale = isOnSale;
        this.numItemsNeededForBundle = itemsNeededForBundle;
        this.itemBundlePrice = itemBundlePrice;
    }

    public boolean isOnSale()
    {
        return isOnSale;
    }

    public boolean isBundlePossible()
    {
        return isBundlePossible;
    }

    public int getNumItemsNeededForBundle()
    {
        return numItemsNeededForBundle;
    }

    public int getItemBundlePrice()
    {
        return itemBundlePrice;
    }

    public String toString()
    {
        String bundleString = "";
        if (isBundlePossible)
        {
            bundleString = (", Items needed for bundle: " + numItemsNeededForBundle +
                ", Bundle price: " + itemBundlePrice);
        }

        return ("|Item name: " + getItemName() + ", Cost: " + getCostInCents() + "Is on sale: "
            + isOnSale() + ", Is bundle: " + isBundlePossible() + bundleString + "|");
    }

}
