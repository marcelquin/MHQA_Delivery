package APP.ServiceMs;

import APP.Dto.CardapioDTO;
import APP.Dto.CardapioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Cardapio-service", url = "http://localhost:8082/msdelivery_Cardapio")
public interface CardapioMsService {


    @GetMapping("/ListarCardapios")
    public ResponseEntity<List<CardapioResponseDTO>> ListarCardapios();

    @GetMapping("/BuscarCardapioPorId")
    public ResponseEntity<CardapioDTO> BuscarCardapioPorId(@RequestParam Long idCardapio);

    @PostMapping("/NovoCardapio")
    public ResponseEntity<CardapioDTO> NovoCardapio(@RequestParam String nome,
                                                    @RequestParam Double porcetagemLucro,
                                                    @RequestParam Double rendimentoMensal);
    @PutMapping("/EditarCardapio")
    public ResponseEntity<CardapioDTO> EditarCardapio(@RequestParam Long idCardapio,
                                                      @RequestParam String nome,
                                                      @RequestParam Double porcetagemLucro,
                                                      @RequestParam Double rendimentoMensal);




}
