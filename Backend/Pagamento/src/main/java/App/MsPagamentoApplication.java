package App;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Ms_Pagamento",
		version = "1",
		description = "Micro serviço responsavel pela autorização de pagamento e retorno de informação"))
public class MsPagamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPagamentoApplication.class, args);
	}

}
