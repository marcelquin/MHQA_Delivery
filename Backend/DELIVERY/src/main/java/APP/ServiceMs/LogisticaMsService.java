package APP.ServiceMs;

import APP.Dto.LogisticaDTO;
import APP.Dto.LogisticaRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Logistica-service", url = "http://localhost:8083/Ms_Logistica")
public interface LogisticaMsService {
    @GetMapping("/ListarEntregas")
    public ResponseEntity<List<LogisticaDTO>> ListarEntregas();
    @GetMapping("/ListarEntregasEmAberto")
    public ResponseEntity<List<LogisticaDTO>> ListarEntregasEmAberto();
    @GetMapping("/BuscarEntregaPorId")
    public ResponseEntity<LogisticaDTO> BuscarEntregaPorId(@RequestParam Long id);
    @PostMapping("/NovolancamentoLogistica")
    public void NovolancamentoLogistica(@RequestParam LogisticaRequestDTO dto);
    @PutMapping("/IniciarEntrega")
    public void IniciarEntrega(@RequestParam Long id);
    @PutMapping("/FinalizarEntrega")
    public void FinalizarEntrega(@RequestParam Long id);

    @PutMapping("/CancelarEntrega")
    public void CancelarEntrega(@RequestParam Long id,
                                @RequestParam String motivo);
    @PutMapping("/ReiniciarEntrega")
    public void ReiniciarEntrega(@RequestParam Long id,
                                 @RequestParam String motivo);
}
