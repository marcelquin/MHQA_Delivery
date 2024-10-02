package App.Controller;

import App.Dto.RelatorioAnualDTO;
import App.Dto.RelatorioDiarioDTO;
import App.Dto.RelatorioMensalDTO;
import App.Dto.Request.BoletosRequestDTO;
import App.Dto.Request.VendaRequestFinalziadaDTO;
import App.Entity.RelatorioMensalEntity;
import App.Enum.FORMAPAGAMENTO;
import App.Service.RelatorioMensalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Ms_Financeiro")
@Tag(name = "Relatorios Financeiros",
        description = "Manipula informações referentes a entidade"
)
@CrossOrigin(origins = "*")
public class relatoriosController {

    private final RelatorioMensalService service;

    public relatoriosController(RelatorioMensalService service) {
        this.service = service;
    }

    @Operation(summary = "Lista Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarRelatorios")
    public ResponseEntity<List<RelatorioMensalEntity>> ListarRelatorios()
    { return service.ListarRelatorios();}


    @Operation(summary = "Busca Registro na tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarRelatorioDiario")
    public ResponseEntity<RelatorioDiarioDTO> BuscarRelatorioDiario()
    { return service.BuscarRelatorioDiario();}


    @Operation(summary = "Busca Registro na tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarRelatorioMensal")
    public ResponseEntity<RelatorioMensalDTO> BuscarRelatorioMensal()
    { return service.BuscarRelatorioMensal();}
    
    @Operation(summary = "Busca Registro na tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarRelatorioAnual")
    public ResponseEntity<RelatorioAnualDTO> BuscarRelatorioAnual()
    {return service.BuscarRelatorioAnual();}


    @Operation(summary = "Salva Registros na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/NovoLancamentoVendasRealizadas")
    public void NovoLancamentoVendasRealizadas(@RequestParam VendaRequestFinalziadaDTO dto)
    {service.NovoLancamentoVendasRealizadas(dto);}



    @Operation(summary = "Salva Registros na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/NovoLancamentoDebito")
    public void NovoLancamentoDebito(@RequestParam BoletosRequestDTO dto)
    {service.NovoLancamentoDebito(dto);}

    @Operation(summary = "Salva Registros na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/NovoPagamentoDebito")
    public void NovoPagamentoDebito(@RequestParam Long idBoleto,
                                    @RequestParam FORMAPAGAMENTO formapagamento,
                                    @RequestParam Double parcelas,
                                    @RequestParam Double descontoPagamentoAntecipado)
    {service.NovoPagamentoDebito(idBoleto, formapagamento, parcelas,descontoPagamentoAntecipado);}



}
