package APP.ServiceMs;

import APP.Dto.PagamentoResponseDTO;
import APP.Enum.FORMAPAGAMENTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Pagamento-service", url = "http://localhost:8084/Ms_pagamento")
public interface PagamentoMsService {

    @PostMapping("/NovoPagamento")
    public ResponseEntity<PagamentoResponseDTO> NovoPagamento(@RequestParam FORMAPAGAMENTO formapagamento,
                                                              @RequestParam Double parcelas,
                                                              @RequestParam Double valorPago,
                                                              @RequestParam Double porcentagemDesconto,
                                                              @RequestParam Double valorVenda);
}
