package sp.senac.br.food;

import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
@Theme(value = "my-theme", variant = Lumo.DARK)
public class FoodApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(FoodApplication.class, args);
    }
}
