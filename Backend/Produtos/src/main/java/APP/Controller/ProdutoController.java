package APP.Controller;

import APP.DTO.IngredienteDTO;
import APP.DTO.ProdutoDTO;
import APP.DTO.ProdutoResponseDTO;
import APP.Entity.ProdutoEntity;
import APP.Enum.UNIDADEMEDIDA;
import APP.Service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/msdelivery_Produto")
@Tag(name = "MS_delivery produtos",
        description = "Manipula informações relacionadas a entidade"
)
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @Operation(summary = "Lista Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarProdutos")
    public ResponseEntity<List<ProdutoResponseDTO>> ListarProdutos()
    { return service.ListarProdutos();}

    @Operation(summary = "Busca Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarProdutoPorId")
    public ResponseEntity<ProdutoDTO> BuscarProdutoPorId(@RequestParam Long idProduto)
    { return service.BuscarProdutoPorId(idProduto);}

    @Operation(summary = "Salva novo Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/novoProduto")
    public ResponseEntity<ProdutoDTO> novoProduto(@RequestParam String nome,
                                                  @RequestParam Double quantidade,
                                                  @RequestParam Double unidade,
                                                  @RequestParam UNIDADEMEDIDA unidademedida,
                                                  @RequestParam Double valorCompra)
    { return service.novoProduto(nome, quantidade, unidade, unidademedida, valorCompra);}

    @Operation(summary = "Salva novo Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/EditarProduto")
    public ResponseEntity<ProdutoDTO> EditarProduto(@RequestParam Long idProduto,
                                                    @RequestParam String nome,
                                                    @RequestParam Double quantidade,
                                                    @RequestParam Double unidade,
                                                    @RequestParam UNIDADEMEDIDA unidademedida,
                                                    @RequestParam Double valorCompra)
    { return service.EditarProduto(idProduto, nome, quantidade, unidade, unidademedida, valorCompra);}

}
