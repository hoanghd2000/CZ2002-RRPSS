/**
 * This class represents an orderable item.
 * 
 * @author SS10 G5
 * @version 1.0
 * @since 2021-10-25
 */

import java.io.Serializable;

abstract class OrderableItems implements Serializable{

    // returns the price of the item
    public abstract double getPrice();
    
    // helps set the price of the item
    public abstract void setPrice(double price);

    // helps get the itemID of the item
    public abstract int getItemID();

    public abstract String getName();
}
