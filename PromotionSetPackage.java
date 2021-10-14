import java.util.ArrayList;

public class PromotionSetPackage {
    
    private ArrayList<PromoItemType> set;

    public PromotionSetPackage(){
        set = new ArrayList<PromoItemType>();
    }

    /**
     * Adding a new menu item to the promo package
     * For example, a burger, drink, dessert, etc.
     * @param type
     */
    public void addItem(String type, int quanity){
        set.add(new PromoItemType(type, quanity));
    }

    /**
     * Method to remove items of type "type" from the 
     * promo set. It also removes orders partially. For example, 
     * if the user wants to remove 5000 burgers from a promo set containing only 5, 
     * it will remove those 5 and print a message saying limit reached.
     * @param type
     * @return
     */
    public String removeItem(String type, int quanity){
        int itemsLeftToRemove = quanity;
        for(int i = 0 ; i < set.size() ; i++){
            if(set.get(i).getType().equals(type)){
                if(set.get(i).getQuantity() > 0){
                    int q = set.get(i).getQuantity();
                    if(itemsLeftToRemove <= q){
                        set.get(i).reduceQuantity(quanity);
                        System.out.println("Items removed successfully.");
                        break;
                    } else {
                        set.get(i).reduceQuantity(itemsLeftToRemove - q);
                        System.out.println("Successfully removed " + q + " instances. Looking for more in promo package.");
                        itemsLeftToRemove -= q;
                    }
                }
            }
        }
        if(itemsLeftToRemove == 0) return ("Removal sucessful.");
        return ("Successfully removed " + (quanity - itemsLeftToRemove) + " instances. The promo package has no more left.");
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
        System.out.println("The items for this promo set are as follows: ");
        for(int i = 0 ; i < set.size() ; i++){
            System.out.println(set.get(i).getType() + " : " + set.get(i).getQuantity());
        }
    }
    
}
