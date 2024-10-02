package APP.Service;

import APP.DTO.CepResultDTO;
import APP.DTO.ClienteDTO;
import APP.DTO.ClienteResponseDTO;
import APP.Entity.ClienteEntity;
import APP.Entity.ContatoEntity;
import APP.Entity.EnderecoEntity;
import APP.Repository.ClienteRepository;
import APP.Repository.ContatoRepository;
import APP.Repository.EnderecoRepository;
import App.Exceptions.EntityNotFoundException;
import App.Exceptions.IllegalActionException;
import App.Exceptions.NullargumentsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ContatoRepository contatoRepository;

    public ClienteService(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, ContatoRepository contatoRepository) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.contatoRepository = contatoRepository;
    }

    Locale localBrasil = new Locale("pt", "BR");

    public ResponseEntity<List<ClienteDTO>> ListarClientes()
    {
        try
        {
            List<ClienteDTO> response = new ArrayList<>();
            List<ClienteEntity> entities = clienteRepository.findAll();
            for(ClienteEntity entity : entities)
            {
                ClienteDTO dto = new ClienteDTO(entity.getId(),
                        entity.getNome(),
                        entity.getSobrenome(),
                        entity.getDataNascimento(),
                        entity.getEndereco().getLogradouro(),
                        entity.getEndereco().getNumero(),
                        entity.getEndereco().getBairro(),
                        entity.getEndereco().getReferencia(),
                        entity.getEndereco().getCep(),
                        entity.getEndereco().getCidade(),
                        entity.getEndereco().getEstado(),
                        "("+entity.getContato().getPrefixo()+") "+entity.getContato().getTelefone(),
                        entity.getContato().getEmail(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getScore()));
                response.add(dto);
            }
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ClienteResponseDTO> BuscarClientesPorId(Long id)
    {
        try
        {
            if(id != null)
            {
                ClienteEntity entity = clienteRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                ClienteResponseDTO response = new ClienteResponseDTO(entity.getNome(),
                        entity.getSobrenome(),
                        entity.getDataNascimento(),
                        entity.getEndereco().getLogradouro(),
                        entity.getEndereco().getNumero(),
                        entity.getEndereco().getBairro(),
                        entity.getEndereco().getReferencia(),
                        entity.getEndereco().getCep(),
                        entity.getEndereco().getCidade(),
                        entity.getEndereco().getEstado(),
                        "("+entity.getContato().getPrefixo()+") "+entity.getContato().getTelefone(),
                        entity.getContato().getEmail(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getScore()));
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
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
            if(score < 0){throw new IllegalActionException("valor do score Invalido");}
            if(nome != null &&
               sobrenome != null &&
               dataNascimento != null &&
               numero != null &&
               cep != null &&
               prefixo != null &&
               telefone != null &&
               email != null)
            {
                ClienteEntity entity = new ClienteEntity();
                ContatoEntity contato = new ContatoEntity();
                EnderecoEntity endereco = new EnderecoEntity();
                CepResultDTO DTO = BuscaEndereco(cep);
                endereco.setLogradouro(logradouro);
                endereco.setBairro(bairro);
                endereco.setNumero(numero);
                if(referencia != null){endereco.setReferencia(referencia);}
                endereco.setCep(DTO.getCep());
                endereco.setCidade(DTO.getLocalidade());
                endereco.setEstado(DTO.getUf());
                endereco.setTimeStamp(LocalDateTime.now());
                contato.setPrefixo(prefixo);
                contato.setTelefone(telefone);
                contato.setEmail(email);
                contato.setTimeStamp(LocalDateTime.now());
                entity.setNome(nome);
                entity.setSobrenome(sobrenome);
                entity.setDataNascimento(dataNascimento);
                if(score != null){entity.setScore(score);}
                entity.setTimeStamp(LocalDateTime.now());
                entity.setEndereco(endereco);
                entity.setContato(contato);
                contatoRepository.save(contato);
                enderecoRepository.save(endereco);
                clienteRepository.save(entity);
                ClienteDTO response = new ClienteDTO(entity.getId(),
                                                    entity.getNome(),
                                                    entity.getSobrenome(),
                                                    entity.getDataNascimento(),
                                                    entity.getEndereco().getLogradouro(),
                                                    entity.getEndereco().getNumero(),
                                                    entity.getEndereco().getBairro(),
                                                    entity.getEndereco().getReferencia(),
                                                    entity.getEndereco().getCep(),
                                                    entity.getEndereco().getCidade(),
                                                    entity.getEndereco().getEstado(),
                                                    "("+entity.getContato().getPrefixo()+") "+entity.getContato().getTelefone(),
                                                    entity.getContato().getEmail(),
                                                    NumberFormat.getCurrencyInstance(localBrasil).format(entity.getScore()));
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public CepResultDTO BuscaEndereco(String cep)
    {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            CepResultDTO DTO = restTemplate
                    .getForEntity(
                            String.format("https://viacep.com.br/ws/%s/json", cep),
                            CepResultDTO.class).getBody();
            return DTO;
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
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
            if(score < 0){throw new IllegalActionException("valor do score Invalido");}
            if(id != null &&
            nome != null &&
            sobrenome != null &&
            dataNascimento != null &&
            numero != null &&
            cep != null &&
            prefixo != null &&
            telefone != null &&
            email != null)
            {
                ClienteEntity entity = clienteRepository.findById(id).orElseThrow(
                    ()-> new EntityNotFoundException()
                );
                EnderecoEntity endereco = enderecoRepository.findById(entity.getEndereco().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                ContatoEntity contato = contatoRepository.findById(entity.getContato().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                entity.setNome(nome);
                entity.setSobrenome(sobrenome);
                entity.setDataNascimento(dataNascimento);
                if(score != null){entity.setScore(score);}
                RestTemplate restTemplate = new RestTemplate();
                CepResultDTO DTO = restTemplate
                        .getForEntity(
                                String.format("https://viacep.com.br/ws/%s/json", cep),
                                CepResultDTO.class).getBody();
                endereco.setLogradouro(logradouro);
                endereco.setBairro(bairro);
                if(referencia != null){endereco.setReferencia(referencia);}
                endereco.setNumero(numero);
                endereco.setCep(cep);
                endereco.setCidade(DTO.getLocalidade());
                endereco.setEstado(DTO.getUf());
                contato.setPrefixo(prefixo);
                contato.setTelefone(telefone);
                contato.setEmail(email);
                entity.setTimeStamp(LocalDateTime.now());
                endereco.setTimeStamp(LocalDateTime.now());
                contato.setTimeStamp(LocalDateTime.now());
                contatoRepository.save(contato);
                enderecoRepository.save(endereco);
                clienteRepository.save(entity);
                ClienteResponseDTO response = new ClienteResponseDTO(entity.getNome(),
                        entity.getSobrenome(),
                        entity.getDataNascimento(),
                        entity.getEndereco().getLogradouro(),
                        entity.getEndereco().getNumero(),
                        entity.getEndereco().getBairro(),
                        entity.getEndereco().getReferencia(),
                        entity.getEndereco().getCep(),
                        entity.getEndereco().getCidade(),
                        entity.getEndereco().getEstado(),
                        "("+entity.getContato().getPrefixo()+") "+entity.getContato().getTelefone(),
                        entity.getContato().getEmail(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getScore()));
                return new ResponseEntity<>(response,HttpStatus.OK);

            }
            else
            { throw  new NullargumentsException();}
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
            if(score < 0){throw new IllegalActionException("valor do score Invalido");}
            if(id != null &&
               score != null)
            {
                ClienteEntity entity = clienteRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                if(entity.getScore() > score){throw new IllegalActionException("valor do score Invalido");}
                entity.setScore(score);
                entity.setTimeStamp(LocalDateTime.now());
                clienteRepository.save(entity);
                ClienteResponseDTO response = new ClienteResponseDTO(entity.getNome(),
                                                                    entity.getSobrenome(),
                                                                    entity.getDataNascimento(),
                                                                    entity.getEndereco().getLogradouro(),
                                                                    entity.getEndereco().getNumero(),
                                                                    entity.getEndereco().getBairro(),
                                                                    entity.getEndereco().getReferencia(),
                                                                    entity.getEndereco().getCep(),
                                                                    entity.getEndereco().getCidade(),
                                                                    entity.getEndereco().getEstado(),
                                                                    "("+entity.getContato().getPrefixo()+") "+entity.getContato().getTelefone(),
                                                                    entity.getContato().getEmail(),
                                                                    NumberFormat.getCurrencyInstance(localBrasil).format(entity.getScore()));
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            else
            { throw  new NullargumentsException();}
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
            if(id != null)
            {
                ClienteEntity entity = clienteRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                enderecoRepository.deleteById(entity.getEndereco().getId());
                contatoRepository.deleteById(entity.getContato().getId());
                clienteRepository.deleteById(id);
            }
            else
            { throw  new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

}
