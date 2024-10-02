package APP.ServiceMs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "FinanceiroDelivery-service", url = "http://localhost:8082/msdelivery_Financeiro")
public interface FinanceiroDeliveryMSService {


    @PostMapping("/NovoLancamentoCustoFixo")
    public void NovoLancamentoCustoFixo(@RequestParam Long idCardapio,
                                        @RequestParam String nome,
                                        @RequestParam Double valor);


    @PostMapping("/NovoLancamentoCustoVariavel")
    public void NovoLancamentoCustoVariavel(@RequestParam Long idCardapio,
                                            @RequestParam String nome,
                                            @RequestParam Double valor,
                                            @RequestParam Double porcentagem);


    @PostMapping("/CalcularMarkup")
    public void CalcularMarkup(@RequestParam Long idCardapio);


    @PutMapping("/editarCustoVariavel")
    public void EditarCustoVariavel(@RequestParam Long idCusto,
                                    @RequestParam String nome,
                                    @RequestParam Double valor,
                                    @RequestParam Double porcentagem);

    @PutMapping("/EditarCustoFixo")
    public void EditarCustoFixo(@RequestParam Long idCusto,
                                @RequestParam String nome,
                                @RequestParam Double valor,
                                @RequestParam Double porcentagem);
}
