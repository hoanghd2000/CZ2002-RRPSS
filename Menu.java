import java.util.ArrayList;

public class Menu {

    private ArrayList<MenuItem> menuItems;

    private ArrayList<PromotionSetPackage> promoItems;

    public Menu(){
        this.menuItems = new ArrayList<MenuItem>();
        this.promoItems = new ArrayList<PromotionSetPackage>();
    }

    public void printMenu(){
        System.out.println("Welcome to our restaurant!");
        for(int i = 0 ; i < menuItems.size() ; i++){
            System.out.println(menuItems.get(i).getName() + "\t\t\t" + menuItems.get(i).getPrice());
            System.out.println(menuItems.get(i).getDescription());
            System.out.println();
        }
        for(int i = 0 ; i < promoItems.size() ; i++){
            promoItems.get(i).printPromoSet();
        }
    }
}
