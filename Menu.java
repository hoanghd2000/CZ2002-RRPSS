import java.util.ArrayList;

public class Menu {
    
    private ArrayList<OrderableItems> orderableItems;

    public Menu() {
        orderableItems = new ArrayList<OrderableItems>();
    }

    public void addMenuItem(MenuItem item) {
        orderableItems.add(item);
    }

    public void addPromotionalSetPackage(PromotionalSetPackage setPackage) {
        orderableItems.add(setPackage);
    }

    // generate javadoc for this method
    public void removeItem(int itemID){
        boolean removed = false;
        for(OrderableItems item : orderableItems){
            if(item.getItemID() == itemID){
                orderableItems.remove(item);
                System.out.println("Item removed");
                removed = true;
                break;
            }
        }
        if(!removed){
            System.out.println("Item not found");
        }
    }
    
    public Double getItemPrice(int itemID) {
        for (OrderableItems item : orderableItems) {
            if (item.getItemID() == itemID) {
                return item.getPrice();
            }
        }
        
        return null;
    }

    public void printMenu(){
        System.out.println("Menu Items");
        System.out.println("Ala Carte");
        for(OrderableItems item : orderableItems){
            if(item.getItemID() < 200) System.out.println(item.getItemID() + " " + item.getName() + " " + item.getPrice());
        }
        System.out.println("================");
        System.out.println("Promotional Set Packages");
        for(OrderableItems item : orderableItems){
            if(item.getItemID() >= 200) System.out.println(item.getItemID() + " " + item.getName() + " " + item.getPrice());
        }
    }
}
