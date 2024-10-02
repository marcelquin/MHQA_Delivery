package APP.Controller;

import APP.Dto.CardapioDTO;
import APP.Dto.CardapioResponseDTO;
import APP.Service.CardapioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Cardapio")
@Tag(name = "Cardapio",
        description = "Manipula as informações relacionadas a entidade trafefando dados para o micro serviço responsavel"
)
public class CardapioController {

    private final CardapioService service;

    public CardapioController(CardapioService service) {
        this.service = service;
    }
    @Operation(summary = "Lista Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarCardapios")
    public ResponseEntity<List<CardapioResponseDTO>> ListarCardapios()
    {return service.ListarCardapios();}

    @Operation(summary = "Busca Registro na tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarCardapioPorId")
    public ResponseEntity<CardapioDTO> BuscarCardapioPorId(@RequestParam Long idCardapio)
    {return service.BuscarCardapioPorId(idCardapio);}

    @Operation(summary = "Salva Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/NovoCardapio")
    public ResponseEntity<CardapioDTO> NovoCardapio(@RequestParam String nome,
                                                    @RequestParam Double porcetagemLucro,
                                                    @RequestParam Double rendimentoMensal)
    {return service.NovoCardapio(nome, porcetagemLucro, rendimentoMensal);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/EditarCardapio")
    public ResponseEntity<CardapioDTO> EditarCardapio(@RequestParam Long idCardapio,
                                                      @RequestParam String nome,
                                                      @RequestParam Double porcetagemLucro,
                                                      @RequestParam Double rendimentoMensal)
    {return service.EditarCardapio(idCardapio, nome, porcetagemLucro, rendimentoMensal);}
}
