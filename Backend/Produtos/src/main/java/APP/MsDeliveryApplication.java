package APP;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Micro serviço delivery",
		version = "1",
		description = "Micro serviço de gerencia de informações relacionado a produtos voltados a delivery"))
public class MsDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDeliveryApplication.class, args);
	}

}
