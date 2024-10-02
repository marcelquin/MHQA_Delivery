package App.Service;

import App.Dto.PagamentoResponseDTO;
import App.Enum.FORMAPAGAMENTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Pagamento-service", url = "http://localhost:8084/upload_Arquivo")
public interface PagamentoService {


    @PostMapping("/NovoPagamento")
    public ResponseEntity<PagamentoResponseDTO> NovoPagamento(@RequestParam FORMAPAGAMENTO formapagamento,
                                                              @RequestParam Double parcelas,
                                                              @RequestParam Double valorPago,
                                                              @RequestParam Double porcentagemDesconto,
                                                              @RequestParam Double valorVenda);
}
