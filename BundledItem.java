public class BundledItem extends Item
{
    private int itemSinglePrice;
    private int remainingItemsIfBundleDisbanded;

    public BundledItem(String itemName, int itemBundlePrice, int itemSinglePrice,
                       int remainingItemsIfBundleDisbanded)
    {
        super(itemName, itemBundlePrice);
        this.itemSinglePrice = itemSinglePrice;
        this.remainingItemsIfBundleDisbanded = remainingItemsIfBundleDisbanded;
    }

    public int getRemainingItemsIfBundleDisbanded()
    {
        return remainingItemsIfBundleDisbanded;
    }

    public int getItemSinglePrice()
    {
        return itemSinglePrice;
    }

}
