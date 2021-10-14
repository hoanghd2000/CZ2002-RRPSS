import java.util.ArrayList;

public class Order {
    
    private ArrayList<MenuItem> alaCarte;

    private ArrayList<PromotionSetPackage> promoSet;

    public Order(){
        alaCarte = new ArrayList<MenuItem>();
        promoSet = new ArrayList<PromotionSetPackage>();
    }
}
