/**
 * This class is similar to MenuItemType but also records the quanity required 
 * for the promo packages.
 */
public class PromoItemType extends MenuItemType{

    private int quantity;

    /**
     * @param type
     * @param quanity
     */
    public PromoItemType(String type, int quanity) {
        super(type);
        this.quantity = quanity;
    }

    /**
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @param amount
     */
    public void addQuantity(int amount){
        this.quantity += amount;
    }

    /**
     * @param amount
     */
    public void reduceQuantity(int amount){
        if(amount > this.quantity){
            System.out.println("Amount exceeds quanity. Please enter legal value.");
        } else {
            this.quantity -= amount;
        }
    }
}
