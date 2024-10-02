package App;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Micro serviço Financeiro",
		version = "1",
		description = "Manipula informações relacionadas a vendas e débitos"))
@EnableFeignClients
public class MsFinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsFinanceiroApplication.class, args);
	}

}
