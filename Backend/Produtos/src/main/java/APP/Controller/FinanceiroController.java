package APP.Controller;

import APP.Entity.CustoFixoEntity;
import APP.Entity.CustoVariavelEntity;
import APP.Service.FinanceiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/msdelivery_Financeiro")
@Tag(name = "MS_delivery Financeiro",
        description = "Manipula informações relacionadas a entidade"
)
public class FinanceiroController {

    private final FinanceiroService service;

    public FinanceiroController(FinanceiroService service) {
        this.service = service;
    }

    @Operation(summary = "Lista Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarCustosFixos")
    public void ListarCustosFixos()
    { service.ListarCustosFixos();}

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



}
