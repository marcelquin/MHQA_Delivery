package APP.Controller;

import APP.DTO.ItemCardapioDTO;
import APP.DTO.ItemCardapioResponseDTO;
import APP.Entity.ItemCardapioEntity;
import APP.Service.ItemCardapioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/msdelivery_ItemCardapio")
@Tag(name = "MS_delivery Item Cardápio",
        description = "Manipula informações relacionadas a entidade"
)
public class ItemCardapioController {

    private final ItemCardapioService service;

    public ItemCardapioController(ItemCardapioService service) {
        this.service = service;
    }

    @Operation(summary = "Lista Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarItemCardapio")
    public ResponseEntity<List<ItemCardapioResponseDTO>> ListarItemCardapio()
    { return service.ListarItemCardapio();}

    @Operation(summary = "Busca Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarItemCardapioPorId")
    public ResponseEntity<ItemCardapioDTO> BuscarItemCardapioPorId(@RequestParam Long idItemCardapio)
    { return service.BuscarItemCardapioPorId(idItemCardapio);}

    @Operation(summary = "Salva novo Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/NovoItemCardapio")
    public ResponseEntity<ItemCardapioDTO> NovoItemCardapio(@RequestParam Long idCardapio,
                                                               @RequestParam String nomeItemCardapio,
                                                               @RequestParam String descricao)
    { return service.NovoItemCardapio(idCardapio, nomeItemCardapio, descricao);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/EditarItemCardapio")
    public ResponseEntity<ItemCardapioDTO> EditarItemCardapio(@RequestParam Long idItemCardapio,
                                                              @RequestParam String nomeItemCardapio,
                                                              @RequestParam String descricao)
    { return service.EditarItemCardapio(idItemCardapio, nomeItemCardapio, descricao);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/AdicionarIngredienteItemCardapio")
    public ResponseEntity<ItemCardapioDTO> AdicionarIngredienteItemCardapio(@RequestParam Long idItemCardapio,
                                                                            @RequestParam Long idIngredientes)
    { return service.AdicionarIngredienteItemCardapio(idItemCardapio, idIngredientes);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/LimparIngredientesItemCardapio")
    public ResponseEntity<ItemCardapioDTO> LimparIngredientesItemCardapio(@RequestParam Long idItemCardapio)
    {return service.LimparIngredientesItemCardapio(idItemCardapio);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/FinalizarItemCardapio")
    public ResponseEntity<ItemCardapioDTO> FinalizarItemCardapio(@RequestParam Long idItemCardapio)
    { return service.FinalizarItemCardapio(idItemCardapio);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/AlterarValorItemCardapio")
    public ResponseEntity<ItemCardapioDTO> AlterarValorItemCardapio(@RequestParam Long idItemCardapio,
                                                                    @RequestParam Double valor)
    { return service.AlterarValorItemCardapio(idItemCardapio, valor);}


    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/ReajusteValorItemCardapio")
    public ResponseEntity<ItemCardapioDTO> ReajusteValorItemCardapio(@RequestParam Long idItemCardapio,
                                                                     @RequestParam Double porcentagemReajuste)
    {return service.ReajusteValorItemCardapio(idItemCardapio, porcentagemReajuste);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/DescontoValorItemCardapio")
    public ResponseEntity<ItemCardapioDTO> DescontoValorItemCardapio(@RequestParam Long idItemCardapio,
                                                                     @RequestParam Double porcentagemDesconto)
    {return service.DescontoValorItemCardapio(idItemCardapio, porcentagemDesconto);}

}
