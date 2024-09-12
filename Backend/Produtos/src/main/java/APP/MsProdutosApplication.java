package APP;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "MS_Produtos",
		version = "1",
		description = "Gerencia informações referentes a produtos"))
public class MsProdutosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProdutosApplication.class, args);
	}

}
