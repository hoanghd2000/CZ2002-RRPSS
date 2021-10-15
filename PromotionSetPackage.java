import java.util.ArrayList;

public class PromotionSetPackage {
    
    private ArrayList<PromoItemType> set;

    private double price;

    /**
     * @param price
     */
    public PromotionSetPackage(double price){
        set = new ArrayList<PromoItemType>();
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Adding a new menu item to the promo package
     * For example, a burger, drink, dessert, etc.
     * @param type
     */
    public void addItem(String type, int quanity){
        for(int i = 0 ; i < set.size() ; i++){
            if(set.get(i).getType().equals(type)){
                set.get(i).addQuantity(quanity);
                return;
            }
        }
        set.add(new PromoItemType(type, quanity));
    }

    /**
     * Method to remove items of type "type" from the 
     * promo set. It also removes orders partially. For example, from a set of 5 burgers,
     * if we want to remove 3, we can do so.
     * @param type
     * @return
     */
    public String removeItem(String type, int quanity){
        for(int i = 0 ; i < set.size() ; i++){
            if(set.get(i).getType().equals(type)){
                if(set.get(i).getQuantity() < quanity) return "Cannot delete more quantity than what already exists.";
                else if(set.get(i).getQuantity() == quanity){
                    set.remove(i);
                    return "Items of type, " + type + ", removed from this promo package.";
                }
                else{
                    set.get(i).reduceQuantity(quanity);
                    return "Operation successful.";
                }
            }
        }
        return "Item of type, " + type + ", not found in this promo package.";
    }

    /**
     * This method takes as input, an array of Strings to check if the types 
     * included in it are part of this promo set.
     * @param inputSet
     * @return
     */
    public boolean isPromoSet(String[] inputSet){
        // this list is a copy of the original set list
        ArrayList<PromoItemType> copyList = new ArrayList<>(set);
        for(int i = 0 ; i < inputSet.length ; i++){
            String type = inputSet[i];
            for(int a = 0 ; a < copyList.size() ; a++){
                // remove item from set.
                if(type.equals(copyList.get(i).getType()) && copyList.get(i).getQuantity() > 0){
                    copyList.get(i).reduceQuantity(1);
                    break;
                }
            }
        }
        for(int i = 0 ; i < copyList.size() ; i++){
            if(copyList.get(i).getQuantity() > 0) return false;
        }
        return true;
    }

    public void printPromoSet(){
        System.out.println("Promo Set \t\t\t " + this.getPrice());
        for(int i = 0 ; i < set.size() ; i++){
            System.out.println(set.get(i).getType() + " : " + set.get(i).getQuantity());
        }
        System.out.println();
    }
}
