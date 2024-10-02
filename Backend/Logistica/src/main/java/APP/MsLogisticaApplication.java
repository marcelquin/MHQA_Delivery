package APP;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Micro serviço Logistica",
		version = "1",
		description = "Manipula informações relacionadas a logistica processando e retornando informação ao fluxo principal."))
public class MsLogisticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsLogisticaApplication.class, args);
	}

}
