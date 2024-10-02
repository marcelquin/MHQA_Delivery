package APP.Service;

import APP.Dto.ClienteDTO;
import APP.Dto.ClienteResponseDTO;
import APP.ServiceMs.ClienteMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteMSService service;

    public ResponseEntity<List<ClienteDTO>> ListarClientes()
    {
        try
        {
            List<ClienteDTO> response = service.ListarClientes().getBody();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ClienteResponseDTO> BuscarClientesPorId(@RequestParam Long id)
    {
        try
        {
            ClienteResponseDTO response = service.BuscarClientesPorId(id).getBody();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ClienteDTO> NovoCliente(String nome,
                                                  String sobrenome,
                                                  LocalDate dataNascimento,
                                                  String logradouro,
                                                  String numero,
                                                  String bairro,
                                                  String referencia,
                                                  String cep,
                                                  Long prefixo,
                                                  Long telefone,
                                                  String email,
                                                  Double score)
    {
        try
        {
            ClienteDTO response = service.NovoCliente(nome, sobrenome, dataNascimento, logradouro, numero, bairro, referencia, cep, prefixo, telefone, email, score).getBody();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ClienteResponseDTO> EditarCliente(Long id,
                                                            String nome,
                                                            String sobrenome,
                                                            LocalDate dataNascimento,
                                                            String logradouro,
                                                            String numero,
                                                            String bairro,
                                                            String referencia,
                                                            String cep,
                                                            Long prefixo,
                                                            Long telefone,
                                                            String email,
                                                            Double score)
    {
        try
        {
            ClienteResponseDTO response = service.EditarCliente(id, nome, sobrenome, dataNascimento, logradouro, numero, bairro, referencia, cep, prefixo, telefone, email, score).getBody();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ClienteResponseDTO> AlterarScoreClientes(Long id,
                                                                   Double score)
    {
        try
        {
            ClienteResponseDTO response = service.AlterarScoreClientes(id, score).getBody();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public void DeletarClientesPorId(Long id)
    {
        try
        {
            service.DeletarClientesPorId(id);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }


}
