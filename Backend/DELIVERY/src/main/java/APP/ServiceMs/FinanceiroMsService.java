package APP.ServiceMs;

import APP.Dto.*;
import APP.Enum.FORMAPAGAMENTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Financeiro-service", url = "http://localhost:8085/Ms_Financeiro")
public interface FinanceiroMsService {

    @GetMapping("/BuscarRelatorioDiario")
    public ResponseEntity<RelatorioDiarioDTO> BuscarRelatorioDiario();


    @GetMapping("/BuscarRelatorioMensal")
    public ResponseEntity<RelatorioMensalDTO> BuscarRelatorioMensal();

    @GetMapping("/BuscarRelatorioAnual")
    public ResponseEntity<RelatorioAnualDTO> BuscarRelatorioAnual();



    @PostMapping("/NovoLancamentoVendasRealizadas")
    public void NovoLancamentoVendasRealizadas(@RequestParam VendaRequestFinalziadaDTO dto);



    @PostMapping("/NovoLancamentoDebito")
    public void NovoLancamentoDebito(@RequestParam BoletosRequestDTO dto);


    @PutMapping("/NovoPagamentoDebito")
    public void NovoPagamentoDebito(@RequestParam Long idBoleto,
                                    @RequestParam FORMAPAGAMENTO formapagamento,
                                    @RequestParam Double parcelas,
                                    @RequestParam Double descontoPagamentoAntecipado);
}
