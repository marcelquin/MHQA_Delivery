package APP.ServiceMs;

import APP.Dto.ItemCardapioDTO;
import APP.Dto.ItemCardapioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ItemCardapio-service", url = "http://localhost:8082/msdelivery_ItemCardapio")
public interface ItemCardapioMsService {

    @GetMapping("/ListarItemCardapio")
    public ResponseEntity<List<ItemCardapioResponseDTO>> ListarItemCardapio();

    @GetMapping("/BuscarItemCardapioPorId")
    public ResponseEntity<ItemCardapioDTO> BuscarItemCardapioPorId(@RequestParam Long idItemCardapio);

    @PostMapping("/NovoItemCardapio")
    public ResponseEntity<ItemCardapioDTO> NovoItemCardapio(@RequestParam Long idCardapio,
                                                            @RequestParam String nomeItemCardapio,
                                                            @RequestParam String descricao);


    @PutMapping("/EditarItemCardapio")
    public ResponseEntity<ItemCardapioDTO> EditarItemCardapio(@RequestParam Long idItemCardapio,
                                                              @RequestParam String nomeItemCardapio,
                                                              @RequestParam String descricao);

    @PutMapping("/AdicionarIngredienteItemCardapio")
    public ResponseEntity<ItemCardapioDTO> AdicionarIngredienteItemCardapio(@RequestParam Long idItemCardapio,
                                                                            @RequestParam Long idIngredientes);

    @PutMapping("/LimparIngredientesItemCardapio")
    public ResponseEntity<ItemCardapioDTO> LimparIngredientesItemCardapio(@RequestParam Long idItemCardapio);

    @PutMapping("/FinalizarItemCardapio")
    public ResponseEntity<ItemCardapioDTO> FinalizarItemCardapio(@RequestParam Long idItemCardapio);

    @PutMapping("/AlterarValorItemCardapio")
    public ResponseEntity<ItemCardapioDTO> AlterarValorItemCardapio(@RequestParam Long idItemCardapio,
                                                                    @RequestParam Double valor);

    @PutMapping("/ReajusteValorItemCardapio")
    public ResponseEntity<ItemCardapioDTO> ReajusteValorItemCardapio(@RequestParam Long idItemCardapio,
                                                                     @RequestParam Double porcentagemReajuste);

    @PutMapping("/DescontoValorItemCardapio")
    public ResponseEntity<ItemCardapioDTO> DescontoValorItemCardapio(@RequestParam Long idItemCardapio,
                                                                     @RequestParam Double porcentagemDesconto);

}
