package APP.Service;

import APP.Dto.CardapioDTO;
import APP.Dto.CardapioResponseDTO;
import APP.Dto.IngredienteDTO;
import APP.ServiceMs.CardapioMsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CardapioService {

    @Autowired
    private CardapioMsService service;

    public ResponseEntity<List<CardapioResponseDTO>> ListarCardapios()
    {
        try
        {
            List<CardapioResponseDTO> response = service.ListarCardapios().getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<CardapioDTO> BuscarCardapioPorId(Long idCardapio)
    {
        try
        {
            CardapioDTO response = service.BuscarCardapioPorId(idCardapio).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<CardapioDTO> NovoCardapio(String nome,
                                                    Double porcetagemLucro,
                                                    Double rendimentoMensal)
    {
        try
        {
            CardapioDTO response = service.NovoCardapio(nome, porcetagemLucro, rendimentoMensal).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<CardapioDTO> EditarCardapio(Long idCardapio,
                                                      String nome,
                                                      Double porcetagemLucro,
                                                      Double rendimentoMensal)
    {
        try
        {
            CardapioDTO response = service.EditarCardapio(idCardapio, nome, porcetagemLucro, rendimentoMensal).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
