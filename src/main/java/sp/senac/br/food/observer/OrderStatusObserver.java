package sp.senac.br.food.observer;

import sp.senac.br.food.order.Order;

public interface OrderStatusObserver {
    void update(Order order);
}
