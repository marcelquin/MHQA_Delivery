package APP.Controller;

import APP.Dto.RelatorioAnualDTO;
import APP.Dto.RelatorioDiarioDTO;
import APP.Dto.RelatorioMensalDTO;
import APP.Enum.FORMAPAGAMENTO;
import APP.Service.FinanceiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/Financeiro")
@Tag(name = "Financeiro",
        description = "Manipula as informações relacionadas a entidade trafefando dados para o micro serviço responsavel"
)
public class FinanceiroController {

    private final FinanceiroService service;

    public FinanceiroController(FinanceiroService service) {
        this.service = service;
    }

    @Operation(summary = "Busca Relatório Diario de vendas e débitos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarRelatorioDiario")
    public ResponseEntity<RelatorioDiarioDTO> BuscarRelatorioDiario()
    {return service.BuscarRelatorioDiario();}

    @Operation(summary = "Busca Relatório Mensal de vendas e débitos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarRelatorioMensal")
    public ResponseEntity<RelatorioMensalDTO> BuscarRelatorioMensal()
    {return service.BuscarRelatorioMensal();}

    @Operation(summary = "Busca Relatório Anual de vendas e débitos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarRelatorioAnual")
    public ResponseEntity<RelatorioAnualDTO> BuscarRelatorioAnual()
    {return service.BuscarRelatorioAnual();}


    @Operation(summary = "Salva novo Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/NovoLancamentoCustoFixo")
    public void NovoLancamentoCustoFixo(@RequestParam Long idCardapio,
                                                                   @RequestParam String nome,
                                                                   @RequestParam Double valor)
    { service.NovoLancamentoCustoFixo(idCardapio, nome, valor);}

    @Operation(summary = "Salva novo Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/NovoLancamentoCustoVariavel")
    public void NovoLancamentoCustoVariavel(@RequestParam Long idCardapio,
                                                                           @RequestParam String nome,
                                                                           Double valor,
                                                                           Double porcentagem)
    { service.NovoLancamentoCustoVariavel(idCardapio, nome, valor, porcentagem);}

    @Operation(summary = "Salva novo Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/CalcularMarkup")
    public void CalcularMarkup(@RequestParam Long idCardapio)
    {service.CalcularMarkup(idCardapio);}

    @Operation(summary = "Salva novo Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/editarCustoVariavel")
    public void EditarCustoVariavel(@RequestParam Long idCusto,
                                    @RequestParam String nome,
                                    Double valor,
                                    Double porcentagem)
    { service.EditarCustoVariavel(idCusto, nome, valor, porcentagem);}

    @Operation(summary = "Salva novo Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/EditarCustoFixo")
    public void EditarCustoFixo(@RequestParam Long idCusto,
                                @RequestParam String nome,
                                Double valor,
                                Double porcentagem)
    {service.EditarCustoFixo(idCusto, nome, valor, porcentagem);}

    @Operation(summary = "Salva novo Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/NovoBoleto")
    public void NovoBoleto(@RequestParam String empresa,
                           @RequestParam String cnpj,
                           @RequestParam LocalDate dataVencimento,
                           @RequestParam Double parcelas,
                           @RequestParam Double valorTotal)
    {service.NovoBoleto(empresa, cnpj, dataVencimento, parcelas, valorTotal);}

    @Operation(summary = "Altera novo Registro na tabela", method = "PUT")
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
    {service.NovoPagamentoDebito(idBoleto, formapagamento, parcelas, descontoPagamentoAntecipado);}


}
