package sp.senac.br.food.observer;

import sp.senac.br.food.order.Order;
import sp.senac.br.food.order.OrderStatus;
import sp.senac.br.food.views.HomeView;


public class KitchenListener implements OrderStatusChangeListener {

    private HomeView homeView;

    public KitchenListener(HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void onOrderStatusChanged(Order order) {
        if (order.getStatus() == OrderStatus.PREPARING) {
            homeView.addToKitchen(order);
            homeView.addLog("Pedido adicionado a cozinha: " + order.getId() + " " + order.getProduct());
        }
    }

}
