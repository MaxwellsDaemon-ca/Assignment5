package business;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

import beans.Order;

/**
 * Session Bean implementation class OrdersBusinessService
 */
@Stateless
@Local(OrdersBusinessInterface.class)
@LocalBean
@Alternative
public class OrdersBusinessService implements OrdersBusinessInterface {
	
	List<Order> orders = new ArrayList<>();
	Random r = new Random();

    /**
     * Default constructor. 
     */
    public OrdersBusinessService() {
    	for (int i = 1; i <= 10; i++) {
			orders.add(new Order(String.format("%06d", r.nextInt(i * 100000)), "Product-" + i, (float) ((float) r.nextInt(50) + .99), r.nextInt(10) + 1));
		}
    }

	/**
     * @see OrdersBusinessInterface#test()
     */
    @Override
    public void test() {
        System.out.println("Hello from the OrdersBusinessService");
    }

	@Override
	public List<Order> getOrders() {
		return orders;
	}

	@Override
	public void setOrders(List<Order> orders) {
		this.orders = orders;	
	}

}
