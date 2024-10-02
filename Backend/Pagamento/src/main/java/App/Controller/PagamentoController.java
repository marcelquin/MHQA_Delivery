package App.Controller;

import App.Dto.PagamentoResponseDTO;
import App.Entity.PagamentoEntity;
import App.Enum.FORMAPAGAMENTO;
import App.Service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Ms_pagamento")
@Tag(name = "Micro serviço pagamento",
        description = "Responsavel por efetuar, salvar e retornar informação sobre o pagamento"
)
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @Operation(summary = "Lista Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarPagamentos")
    public ResponseEntity<List<PagamentoEntity>> ListarPagamentos()
    {return service.ListarPagamentos();}

    @Operation(summary = "Salva novo Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/NovoPagamento")
    public ResponseEntity<PagamentoResponseDTO> NovoPagamento(@RequestParam FORMAPAGAMENTO formapagamento,
                                                              @RequestParam Double parcelas,
                                                              @RequestParam Double valorPago,
                                                              @RequestParam Double porcentagemDesconto,
                                                              @RequestParam Double valorVenda)
    {return service.NovoPagamento(formapagamento, parcelas, valorPago, porcentagemDesconto, valorVenda);}
}
