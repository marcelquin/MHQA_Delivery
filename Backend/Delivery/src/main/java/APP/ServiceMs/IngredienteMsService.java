package APP.ServiceMs;

import APP.Dto.IngredienteDTO;
import APP.Dto.IngredienteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Ingrediente-service", url = "http://localhost:8082/msdelivery_Ingrediente")
public interface IngredienteMsService {

    @GetMapping("/ListarIngredientes")
    public ResponseEntity<List<IngredienteResponseDTO>> ListarIngredientes();

    @GetMapping("/BuscarIngredientePorId")
    public ResponseEntity<IngredienteDTO> BuscarIngredientePorId(@RequestParam Long idIngrediente);

    @PostMapping("/novoIngrediente")
    public ResponseEntity<IngredienteDTO> novoIngrediente(@RequestParam Long idProduto,
                                                          @RequestParam String nomeIngrediente,
                                                          @RequestParam Double quantidade);

    @PutMapping("/EditarIngrediente")
    public ResponseEntity<IngredienteDTO> EditarIngrediente(@RequestParam Long idIngrediente,
                                                            @RequestParam String nomeIngrediente,
                                                            @RequestParam Double quantidade);




}
