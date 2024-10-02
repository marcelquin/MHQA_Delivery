package APP.Controller;

import APP.DTO.IngredienteDTO;
import APP.DTO.IngredienteResponseDTO;
import APP.Entity.IngredienteEntity;
import APP.Service.IngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/msdelivery_Ingrediente")
@Tag(name = "MS_delivery Ingredientes",
        description = "Manipula informações relacionadas a entidade"
)
public class IngredienteController {

    private final IngredienteService service;

    public IngredienteController(IngredienteService service) {
        this.service = service;
    }

    @Operation(summary = "Lista Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/ListarIngredientes")
    public ResponseEntity<List<IngredienteResponseDTO>> ListarIngredientes()
    {return service.ListarIngredientes();}

    @Operation(summary = "Busca Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @GetMapping("/BuscarIngredientePorId")
    public ResponseEntity<IngredienteDTO> BuscarIngredientePorId(@RequestParam Long idIngrediente)
    { return service.BuscarIngredientePorId(idIngrediente);}

    @Operation(summary = "Salva novo Registro na tabela", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PostMapping("/novoIngrediente")
    public ResponseEntity<IngredienteDTO> novoIngrediente(@RequestParam Long idProduto,
                                                          String nomeIngrediente,
                                                          @RequestParam Double quantidade)
    {return  service.novoIngrediente(idProduto, nomeIngrediente, quantidade);}

    @Operation(summary = "Salva novo Registro na tabela", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algoo deu errado"),
    })
    @PutMapping("/EditarIngrediente")
    public ResponseEntity<IngredienteDTO> EditarIngrediente(Long idIngrediente,
                                                            String nomeIngrediente,
                                                            Double quantidade)
    {return service.EditarIngrediente(idIngrediente, nomeIngrediente, quantidade);}

}
