package sp.senac.br.food.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;
import sp.senac.br.food.observer.DeliveryListener;
import sp.senac.br.food.observer.KitchenListener;
import sp.senac.br.food.order.Order;
import sp.senac.br.food.order.OrderService;
import sp.senac.br.food.order.OrderStatus;

@Route("")
public class HomeView extends VerticalLayout {
    private final VerticalLayout orderPane;
    private final VerticalLayout kitchenPane;
    private final VerticalLayout deliveryPane;
    private final TextArea logs;

    private final OrderService orderService;
    private final TextField input = new TextField();

    public HomeView(OrderService orderService) {
        this.orderService = orderService;
        orderService.setView(this);

        var panes = new HorizontalLayout();
        panes.setSizeFull();
        setSizeFull();
        setPadding(true);
        setJustifyContentMode(JustifyContentMode.BETWEEN);

        orderPane = createOrderPane();
        kitchenPane = createKitchenPane();
        deliveryPane = createDeliveryPane();

        panes.add(orderPane);
        panes.add(kitchenPane);
        panes.add(deliveryPane);

        logs = new TextArea();
        logs.setReadOnly(true);
        logs.setWidthFull();
        add(panes, logs);

        loadOrders();
    }

    private VerticalLayout createOrderPane(){
        VerticalLayout layout = new VerticalLayout();
        layout.add(VaadinIcon.CART_O.create());
        layout.add(new H1("Pedidos"));

        input.setPlaceholder("produto");
        input.addKeyDownListener(Key.ENTER, (e) -> handleOrderAdd(input.getValue()));

        var button = new Button("Adicionar", VaadinIcon.PLUS.create());
        button.addClickListener((e) -> handleOrderAdd(input.getValue()));
        layout.add(new HorizontalLayout(input, button));

        return layout;
    }

    private VerticalLayout createKitchenPane(){
        VerticalLayout layout = new VerticalLayout();
        layout.add(VaadinIcon.CUTLERY.create());
        layout.add(new H1("Cozinha"));
        return layout;
    }

    private VerticalLayout createDeliveryPane(){
        VerticalLayout layout = new VerticalLayout();
        layout.add(VaadinIcon.TRUCK.create());
        layout.add(new H1("Entrega"));
        return layout;
    }

    private void handleOrderAdd(String product){
        var order = orderService.createOrder(product);
        orderPane.add(orderCard(order));
        input.clear();
    }

    private void loadOrders(){
        var orders = orderService.getAllOrders();
        orders.forEach(order -> {
            if(order.getStatus().equals(OrderStatus.PENDING)){
                orderPane.add(orderCard(order));
            }
            if(order.getStatus().equals(OrderStatus.PREPARING)){
                kitchenPane.add(orderCard(order));
            }
            if(order.getStatus().equals(OrderStatus.IN_TRANSIT) || order.getStatus().equals(OrderStatus.DELIVERED)){
                deliveryPane.add(orderCard(order));
            }
        });
    }

    private Card orderCard(Order order) {
        Card card = new Card();
        card.setTitle(order.getProduct());
        card.setWidthFull();
        card.addThemeVariants(CardVariant.LUMO_OUTLINED);
        card.addThemeVariants(CardVariant.LUMO_ELEVATED);

        Span badge = new Span(order.getStatus().name());
        badge.getElement().getThemeList().add(getStatusColor(order.getStatus()));
        card.setHeaderSuffix(badge);

        Button button = new Button(VaadinIcon.ARROW_RIGHT.create());
        button.addClickListener(e -> handleAdvanceOrderStatus(order, badge, card));

        HorizontalLayout footer = new HorizontalLayout();
        footer.setWidthFull();
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        footer.add(button);

        card.addToFooter(footer);
        return card;
    }

    private void handleAdvanceOrderStatus(Order order, Span badge, Card card) {
        card.removeFromParent();
        orderService.advanceOrderStatus(order);
        badge.setText(order.getStatus().name());
        badge.getElement().getThemeList().clear();
        badge.getElement().getThemeList().add(getStatusColor(order.getStatus()));
    }

    private String getStatusColor(OrderStatus status) {
        return switch (status) {
            case PENDING -> "badge success";
            case PREPARING -> "badge error";
            case IN_TRANSIT -> "badge warning";
            case DELIVERED -> "badge contrast";
        };
    }

    public void addToKitchen(Order order) {
        kitchenPane.add(orderCard(order));
    }

    public void addToDeliveryPane(Order order) {
        deliveryPane.add(orderCard(order));
    }

    public void showNotification(String message) {
        Notification.show(message, 3000, Notification.Position.TOP_END);
    }

    public void addLog(String logMessage) {
        logs.setValue(logs.getValue() + "\n" + logMessage);
    }
}
