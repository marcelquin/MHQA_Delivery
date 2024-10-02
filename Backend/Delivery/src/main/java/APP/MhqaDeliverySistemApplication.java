package APP;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "MHQA delivery",
		version = "1",
		description = "Fluxo principa de informações relacionado a vendas"))
@EnableFeignClients
public class MhqaDeliverySistemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MhqaDeliverySistemApplication.class, args);
	}

}
