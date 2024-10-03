package APP.ServiceMs;

import APP.Dto.ProdutoDTO;
import APP.Dto.ProdutoResponseDTO;
import APP.Enum.UNIDADEMEDIDA;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Produto-service", url = "http://localhost:8082/msdelivery_Produto")
public interface ProdutoMsService {

    @GetMapping("/ListarProdutos")
    public ResponseEntity<List<ProdutoResponseDTO>> ListarProdutos();

    @GetMapping("/BuscarProdutoPorId")
    public ResponseEntity<ProdutoDTO> BuscarProdutoPorId(@RequestParam Long idProduto);

    @PostMapping("/novoProduto")
    public ResponseEntity<ProdutoDTO> novoProduto(@RequestParam String nome,
                                                  @RequestParam Double quantidade,
                                                  @RequestParam Double unidade,
                                                  @RequestParam UNIDADEMEDIDA unidademedida,
                                                  @RequestParam Double valorCompra);

    @PutMapping("/EditarProduto")
    public ResponseEntity<ProdutoDTO> EditarProduto(@RequestParam Long idProduto,
                                                    @RequestParam String nome,
                                                    @RequestParam Double quantidade,
                                                    @RequestParam Double unidade,
                                                    @RequestParam UNIDADEMEDIDA unidademedida,
                                                    @RequestParam Double valorCompra);
}
