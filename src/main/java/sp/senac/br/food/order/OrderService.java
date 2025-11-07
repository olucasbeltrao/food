package sp.senac.br.food.order;

import org.springframework.stereotype.Service;
import sp.senac.br.food.views.HomeView;

import java.util.List;

@Service
public class OrderService {

    private HomeView view;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(String product) {
        var order = Order.builder()
                .id(null)
                .product(product)
                .status(OrderStatus.PENDING)
                .build();
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void advanceOrderStatus(Order order) {
        var status = switch (order.getStatus()) {
            case PENDING -> OrderStatus.PREPARING;
            case PREPARING ->  OrderStatus.IN_TRANSIT;
            case IN_TRANSIT, DELIVERED -> OrderStatus.DELIVERED;
        };
        order.setStatus(status);
        orderRepository.save(order);
    }


    public void setView(HomeView view) {
        this.view = view;
    }
}
