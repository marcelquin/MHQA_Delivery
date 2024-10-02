package APP.Service;

import APP.Dto.ItemCardapioDTO;
import APP.Dto.ItemCardapioResponseDTO;
import APP.Dto.ProdutoDTO;
import APP.ServiceMs.ItemCardapioMsService;
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
public class ItemCardapioService {
    @Autowired
    private ItemCardapioMsService service;

    public ResponseEntity<List<ItemCardapioResponseDTO>> ListarItemCardapio()
    {
        try
        {
            List<ItemCardapioResponseDTO> responseDTO = service.ListarItemCardapio().getBody();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ItemCardapioDTO> BuscarItemCardapioPorId(Long idItemCardapio)
    {
        try
        {
            ItemCardapioDTO responseDTO = service.BuscarItemCardapioPorId(idItemCardapio).getBody();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<ItemCardapioDTO> NovoItemCardapio(Long idCardapio,
                                                            String nomeItemCardapio,
                                                            String descricao)
    {
        try
        {
            ItemCardapioDTO responseDTO = service.NovoItemCardapio(idCardapio, nomeItemCardapio, descricao).getBody();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<ItemCardapioDTO> EditarItemCardapio(Long idItemCardapio,
                                                              String nomeItemCardapio,
                                                              String descricao)
    {
        try
        {
            ItemCardapioDTO responseDTO = service.EditarItemCardapio(idItemCardapio, nomeItemCardapio, descricao).getBody();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ItemCardapioDTO> AdicionarIngredienteItemCardapio(Long idItemCardapio,
                                                                            Long idIngredientes)
    {
        try
        {
            ItemCardapioDTO responseDTO = service.AdicionarIngredienteItemCardapio(idItemCardapio, idIngredientes).getBody();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<ItemCardapioDTO> LimparIngredientesItemCardapio(Long idItemCardapio)
    {
        try
        {
            ItemCardapioDTO responseDTO = service.LimparIngredientesItemCardapio(idItemCardapio).getBody();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ItemCardapioDTO> FinalizarItemCardapio(Long idItemCardapio)
    {
        try
        {
            ItemCardapioDTO responseDTO = service.FinalizarItemCardapio(idItemCardapio).getBody();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<ItemCardapioDTO> AlterarValorItemCardapio(Long idItemCardapio,
                                                                    Double valor)
    {
        try
        {
            ItemCardapioDTO responseDTO = service.AlterarValorItemCardapio(idItemCardapio, valor).getBody();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ItemCardapioDTO> ReajusteValorItemCardapio(Long idItemCardapio,
                                                                     Double porcentagemReajuste)
    {
        try
        {
            ItemCardapioDTO responseDTO = service.ReajusteValorItemCardapio(idItemCardapio, porcentagemReajuste).getBody();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ItemCardapioDTO> DescontoValorItemCardapio(Long idItemCardapio,
                                                                     Double porcentagemDesconto)
    {
        try
        {
            ItemCardapioDTO responseDTO = service.DescontoValorItemCardapio(idItemCardapio, porcentagemDesconto).getBody();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
