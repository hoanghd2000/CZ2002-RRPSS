import java.util.Hashtable;

public class PromotionalSetPackage extends OrderableItems{

    private double price;

    private double description;
    
    // This stores the list of MenuItems and their quantites in the promo set.
    private Hashtable<MenuItem, Integer> set;

    // This stores the types of items and their quantities in the promo set.
    private Hashtable<String, Integer> typeSet;

    // Constructor initializes the promo package.
    public PromotionalSetPackage(){
        this.set = new Hashtable<MenuItem, Integer>();
        this.typeSet = new Hashtable<String, Integer>();
    }

    public PromotionalSetPackage(double price){
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDescription() {
        return description;
    }

    public void setDescription(double description) {
        this.description = description;
    }

    public Hashtable<MenuItem, Integer> getSet() {
        return set;
    }

    public Hashtable<String, Integer> getTypeSet() {
        return typeSet;
    }

    public void addItem(MenuItem item, int quantity){
        // adding the item into the promo set.
        set.put(item, quantity);
    }

    public void addItem(String type, int quantity){
        // adding the "type" of the item into the promo set.
        typeSet.put(type, quantity);
    }

    public boolean removeItem(MenuItem item, int quantity){
        // item not in promo set
        if(!set.containsKey(item)) return false;
        else{
            // quantity of item in promo set less than given quantity
            if(set.get(item) < quantity) return false;
            // if quanity is same, remove from promo set entirely.
            else if(set.get(item) == quantity){
                set.remove(item);
                return true;
            }
        }
        // reduce the quantity.
        set.put(item, set.get(item) - quantity);
        return true;
    }

    public boolean removeItem(String type, int quantity){
        // item not in promo set
        if(!typeSet.containsKey(type)) return false;
        else{
            // quantity of item in promo set less than given quantity
            if(typeSet.get(type) < quantity) return false;
            // if quanity is same, remove from promo set entirely.
            else if(typeSet.get(type) == quantity){
                typeSet.remove(type);
                return true;
            }
        }
        // reduce the quantity.
        typeSet.put(type, typeSet.get(type) - quantity);
        return true;
    }

    public boolean equals(Object o){
        // return true if either hashtable of the classes match.
        return ((PromotionalSetPackage) o).getSet().equals(this.set) || 
                    ((PromotionalSetPackage) o).getTypeSet().equals(this.typeSet);
    }
}
