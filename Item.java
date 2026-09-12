public abstract class Item
{
    private int costInCents;
    private String itemName;

    public Item(String itemName, int costInCents)
    {
        this.itemName = itemName;
        this.costInCents = costInCents;
    }

    public int getCostInCents()
    {
        return costInCents;
    }

    public void setCostInCents(int costInCents)
    {
        this.costInCents = costInCents;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String toString()
    {
        return ("|Item name: " + itemName + ", Cost: " + costInCents + "|");
    }

}
