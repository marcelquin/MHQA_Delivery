package APP.ServiceMs;

import APP.Dto.ClienteDTO;
import APP.Dto.ClienteResponseDTO;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@FeignClient(name = "cliente-service", url = "http://localhost:8081/ms_Cliente")
public interface ClienteMSService {

    @GetMapping("/ListarClientes")
    public ResponseEntity<List<ClienteDTO>> ListarClientes();
    @GetMapping("/BuscarClientesPorId")
    public ResponseEntity<ClienteResponseDTO> BuscarClientesPorId(@RequestParam Long id);

    @PostMapping("/NovoCliente")
    public ResponseEntity<ClienteDTO> NovoCliente(@RequestParam String nome,
                                                  @RequestParam String sobrenome,
                                                  @RequestParam LocalDate dataNascimento,
                                                  @RequestParam String logradouro,
                                                  @RequestParam String numero,
                                                  @RequestParam String bairro,
                                                  @RequestParam String referencia,
                                                  @RequestParam String cep,
                                                  @RequestParam Long prefixo,
                                                  @RequestParam Long telefone,
                                                  @RequestParam String email,
                                                  @RequestParam Double score);

    @PutMapping("/EditarCliente")
    public ResponseEntity<ClienteResponseDTO> EditarCliente(@RequestParam Long id,
                                                            @RequestParam String nome,
                                                            @RequestParam String sobrenome,
                                                            @RequestParam LocalDate dataNascimento,
                                                            @RequestParam String logradouro,
                                                            @RequestParam String numero,
                                                            @RequestParam String bairro,
                                                            @RequestParam String referencia,
                                                            @RequestParam String cep,
                                                            @RequestParam Long prefixo,
                                                            @RequestParam Long telefone,
                                                            @RequestParam String email,
                                                            @RequestParam Double score);

    @PutMapping("/AlterarScoreClientes")
    public ResponseEntity<ClienteResponseDTO> AlterarScoreClientes(@RequestParam Long id,
                                                                   @RequestParam Double score);
    @DeleteMapping("/DeletarClientesPorId")
    public void DeletarClientesPorId(@RequestParam Long id);


}
