package APP.Service;

import APP.Dto.ProdutoDTO;
import APP.Dto.ProdutoResponseDTO;
import APP.Enum.UNIDADEMEDIDA;
import APP.ServiceMs.ProdutoMsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoMsService service;

    public ProdutoService(ProdutoMsService service) {
        this.service = service;
    }

    public ResponseEntity<List<ProdutoResponseDTO>> ListarProdutos()
    {
        try
        {
                List<ProdutoResponseDTO> response = service.ListarProdutos().getBody();
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ProdutoDTO> BuscarProdutoPorId(Long idProduto)
    {
        try
        {
            ProdutoDTO response = service.BuscarProdutoPorId(idProduto).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ProdutoDTO> novoProduto(String nome,
                                                  Double quantidade,
                                                  Double unidade,
                                                  UNIDADEMEDIDA unidademedida,
                                                  Double valorCompra)
    {
        try
        {
            ProdutoDTO response = service.novoProduto(nome, quantidade, unidade, unidademedida, valorCompra).getBody();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<ProdutoDTO> EditarProduto(Long idProduto,
                                                    String nome,
                                                    Double quantidade,
                                                    Double unidade,
                                                    UNIDADEMEDIDA unidademedida,
                                                    Double valorCompra)
    {
        try
        {
            ProdutoDTO response = service.EditarProduto(idProduto, nome, quantidade, unidade, unidademedida, valorCompra).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
