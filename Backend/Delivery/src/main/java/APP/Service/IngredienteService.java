package APP.Service;

import APP.Dto.IngredienteDTO;
import APP.Dto.IngredienteResponseDTO;
import APP.ServiceMs.IngredienteMsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteMsService service;


    public ResponseEntity<List<IngredienteResponseDTO>> ListarIngredientes()
    {
        try
        {
            List<IngredienteResponseDTO> response = service.ListarIngredientes().getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<IngredienteDTO> BuscarIngredientePorId(Long idIngrediente)
    {
        try
        {
            IngredienteDTO response = service.BuscarIngredientePorId(idIngrediente).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<IngredienteDTO> novoIngrediente(Long idProduto,
                                                          String nomeIngrediente,
                                                          Double quantidade)
    {
        try
        {
            IngredienteDTO response = service.novoIngrediente(idProduto, nomeIngrediente, quantidade).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<IngredienteDTO> EditarIngrediente(Long idIngrediente,
                                                            String nomeIngrediente,
                                                            Double quantidade)
    {
        try
        {
            IngredienteDTO response = service.EditarIngrediente(idIngrediente, nomeIngrediente, quantidade).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
