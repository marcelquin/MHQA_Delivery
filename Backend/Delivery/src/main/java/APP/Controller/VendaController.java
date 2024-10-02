package APP.Controller;

import APP.Dto.VendaDTO;
import APP.Dto.VendaResponseDTO;
import APP.Entity.VendaEntity;
import APP.Enum.FORMAPAGAMENTO;
import APP.Service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Vendas")
@Tag(name = "MHQA delivery",
        description = "Manipula as informações relacionadas a vendas"
)
public class VendaController {

    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }


    @Operation(summary = "Lista Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarVendas")
    public ResponseEntity<List<VendaResponseDTO>> ListarVendas()
    { return service.ListarVendas();}

    @Operation(summary = "Lista Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarVendasEmAberto")
    public ResponseEntity<List<VendaResponseDTO>> ListarVendasEmAberto()
    {return service.ListarVendasEmAberto();}

    @Operation(summary = "Busca Registro da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarVendaPorId")
    public ResponseEntity<VendaDTO> BuscarVendaPorId(@RequestParam Long id)
    {return service.BuscarVendaPorId(id);}

    @Operation(summary = "Salva Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/NovaVenda")
    public ResponseEntity<VendaDTO> NovaVenda(Long idCliente,
                                              String nomeCliente)
    { return service.NovaVenda(idCliente, nomeCliente);}

    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/AdicionarItemVenda")
    public ResponseEntity<VendaDTO> AdicionarItemVenda(@RequestParam Long idVenda,
                                                       @RequestParam Long idProduto,
                                                       @RequestParam Double quantidade)
    {return service.AdicionarItemVenda(idVenda, idProduto, quantidade);}


    @Operation(summary = "Edita Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/FinalizarVenda")
    public ResponseEntity<VendaDTO> FinalizarVenda(@RequestParam Long idVenda,
                                                   @RequestParam FORMAPAGAMENTO formapagamento,
                                                   @RequestParam Double parcelas,
                                                   @RequestParam Double desconto,
                                                   @RequestParam Double valorPago,
                                                   @RequestParam Boolean entrega,
                                                   String enderecoEntrega)
    {return service.FinalizarVenda(idVenda, formapagamento, parcelas, desconto, valorPago, entrega, enderecoEntrega);}

}
