package sp.senac.br.food.observer;

import sp.senac.br.food.order.Order;
import sp.senac.br.food.order.OrderStatus;
import sp.senac.br.food.views.HomeView;

public class DeliveryListener implements OrderStatusChangeListener{

    private final HomeView homeView;

    public DeliveryListener(HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void onOrderStatusChanged(Order order) {
        if(order.getStatus().equals(OrderStatus.IN_TRANSIT)){
            homeView.addToDeliveryPane(order);
            homeView.showNotification("Order " + order.getId() + " is out for delivery!");
        }
    }
}
