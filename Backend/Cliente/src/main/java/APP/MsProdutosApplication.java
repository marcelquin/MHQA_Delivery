package APP;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "MS Cliente",
		version = "1",
		description = "Manipula informações relacionados a clientes"))
public class MsProdutosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProdutosApplication.class, args);
	}

}
