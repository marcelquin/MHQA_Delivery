package APP.Controller;

import APP.DTO.EstoqueDTO;
import APP.DTO.EstoqueResponseDTO;
import APP.DTO.ProdutoDTO;
import APP.Service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("msProduto")
@Tag(name = "msProduto",
        description = "Gerencia informações referente a entidade")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService service;


    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @Operation(summary = "Busca Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarProdutos")
    public ResponseEntity<List<ProdutoDTO>> ListarProdutos()
    {return service.ListarProdutos();}

    @Operation(summary = "Busca Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarEstoque")
    public ResponseEntity<List<EstoqueDTO>> ListarEstoque()
    {return service.ListarEstoque();}

    @Operation(summary = "Busca Registro na tabela Por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarProdutoPorId")
    public ResponseEntity<ProdutoDTO> BuscarProdutoPorId(@RequestParam Long id)
    {return service.BuscarProdutoPorId(id);}

    @Operation(summary = "Busca Registro na tabela Por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarEstoquePorId")
    public ResponseEntity<EstoqueDTO> BuscarEstoquePorId(@RequestParam Long id)
    {return service.BuscarEstoquePorId(id);}

    @Operation(summary = "Salva novo Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/NovoProduto")
    public ResponseEntity<EstoqueResponseDTO> NovoProduto(@RequestParam String nome,
                                                          @RequestParam String descricao,
                                                          @RequestParam Double valorCompra,
                                                          @RequestParam Double estoque,
                                                          @RequestParam Double porcentagemLucro)
    { return service.NovoProduto(nome, descricao, valorCompra, estoque, porcentagemLucro);}

    @Operation(summary = "Altera Informação do Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/EditarProduto")
    public ResponseEntity<EstoqueResponseDTO> EditarProduto(@RequestParam Long id,
                                                            @RequestParam String nome,
                                                            @RequestParam String descricao,
                                                            @RequestParam Double valorCompra,
                                                            @RequestParam Double estoque,
                                                            @RequestParam Double porcentagemLucro)
    {return service.EditarProduto(id, nome, descricao, valorCompra, estoque, porcentagemLucro);}

    @Operation(summary = "Altera Informação do Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/AdicionarEstoque")
    public ResponseEntity<EstoqueResponseDTO> AdicionarEstoque(@RequestParam Long id,
                                                               @RequestParam Double valorCompra,
                                                               @RequestParam Double estoque,
                                                               @RequestParam Double porcentagemLucro)
    {return service.AdicionarEstoque(id, valorCompra, estoque, porcentagemLucro);}

    @Operation(summary = "Altera Informação do Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/NovoValorProduto")
    public ResponseEntity<EstoqueResponseDTO> NovoValorProduto(@RequestParam Long id,
                                                               @RequestParam Double novoValor)
    {return service.NovoValorProduto(id, novoValor);}

    @Operation(summary = "Altera Informação do Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/reajusteValorProduto")
    public ResponseEntity<EstoqueResponseDTO> reajusteValorProduto(@RequestParam Long id,
                                                                   @RequestParam Double porcentagemReajuste)
    {return  service.reajusteValorProduto(id, porcentagemReajuste);}

    @Operation(summary = "Altera Informação do Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/DescontoValorProduto")
    public ResponseEntity<EstoqueResponseDTO> DescontoValorProduto(@RequestParam Long id,
                                                                   @RequestParam Double porcentagemDesconto)
    { return  service.DescontoValorProduto(id, porcentagemDesconto);}

    @Operation(summary = "Deleta Registro na tabela", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @DeleteMapping("/deletarProduto")
    public void deletarProduto(@RequestParam Long id)
    { service.deletarProduto(id);}
}
