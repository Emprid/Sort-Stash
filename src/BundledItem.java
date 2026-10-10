public class BundledItem extends Item
{
    private int itemSinglePrice;
    private int remainingItemsIfBundleDisbanded;
    private boolean isOnSale;

    public BundledItem(String itemName, int itemBundlePrice, int itemSinglePrice,
                       int remainingItemsIfBundleDisbanded)
    {
        this(itemName, itemBundlePrice, itemSinglePrice, remainingItemsIfBundleDisbanded, false);
    }

    public BundledItem(String itemName, int itemBundlePrice, int itemSinglePrice,
                       int remainingItemsIfBundleDisbanded, boolean isOnSale)
    {
        super(itemName, itemBundlePrice);
        this.itemSinglePrice = itemSinglePrice;
        this.remainingItemsIfBundleDisbanded = remainingItemsIfBundleDisbanded;
        this.isOnSale = isOnSale;
    }

    public int getRemainingItemsIfBundleDisbanded()
    {
        return remainingItemsIfBundleDisbanded;
    }

    public int getItemSinglePrice()
    {
        return itemSinglePrice;
    }

    public boolean isOnSale()
    {
        return isOnSale;
    }

}
