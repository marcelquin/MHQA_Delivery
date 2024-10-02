package APP.Service;

import APP.DTO.IngredienteResponseDTO;
import APP.DTO.ItemCardapioDTO;
import APP.DTO.ItemCardapioResponseDTO;
import APP.Entity.CardapioEntity;
import APP.Entity.IngredienteEntity;
import APP.Entity.ItemCardapioEntity;
import APP.Exceptions.EntityNotFoundException;
import APP.Exceptions.IllegalActionException;
import APP.Exceptions.NullargumentsException;
import APP.Repositoty.CardapioRepository;
import APP.Repositoty.IngredienteRepository;
import APP.Repositoty.ItemCardapioRepository;
import APP.Repositoty.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ItemCardapioService {

    private final ProdutoRepository produtoRepository;
    private final IngredienteRepository ingredienteRepository;
    private final ItemCardapioRepository itemCardapioRepository;
    private final CardapioRepository cardapioRepository;

    public ItemCardapioService(ProdutoRepository produtoRepository, IngredienteRepository ingredienteRepository, ItemCardapioRepository itemCardapioRepository, CardapioRepository cardapioRepository) {
        this.produtoRepository = produtoRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.itemCardapioRepository = itemCardapioRepository;
        this.cardapioRepository = cardapioRepository;
    }

    Locale localBrasil = new Locale("pt", "BR");

    public ResponseEntity<List<ItemCardapioResponseDTO>> ListarItemCardapio()
    {
        try
        {
            List<ItemCardapioResponseDTO> response = new ArrayList<>();
            List<ItemCardapioEntity> entities = itemCardapioRepository.findAll();
            for(ItemCardapioEntity entity : entities)
            {
                List<IngredienteResponseDTO> ingredienteList = new ArrayList<>();
                for(IngredienteEntity ingrediente : entity.getIngredientes())
                {
                    IngredienteResponseDTO ingredienteDto = new IngredienteResponseDTO(ingrediente.getId(),
                            ingrediente.getNome(),
                            ingrediente.getQuantidade(),
                            NumberFormat.getCurrencyInstance(localBrasil).format(ingrediente.getValor()),
                            ingrediente.getTimeStamp());
                    ingredienteList.add(ingredienteDto);
                }
                ItemCardapioResponseDTO itemCardapioResponseDTO = new ItemCardapioResponseDTO(entity.getId(),
                        entity.getNome(),
                        entity.getDescricao(),
                        entity.getCodigo(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()),
                        ingredienteList,
                        entity.getTimeStamp());
                response.add(itemCardapioResponseDTO);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ItemCardapioDTO> BuscarItemCardapioPorId(Long idItemCardapio)
    {
        try
        {
            if(idItemCardapio != null)
            {
                ItemCardapioEntity entity = itemCardapioRepository.findById(idItemCardapio).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                System.out.println("produto: "+entity.getNome());
                ItemCardapioDTO response = new ItemCardapioDTO(entity.getNome(),
                                                               entity.getDescricao(),
                                                               entity.getCodigo(),
                                                               entity.getValor());
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ItemCardapioDTO> NovoItemCardapio(Long idCardapio,
                                                               String nomeItemCardapio,
                                                               String descricao)
    {
        try
        {
           if(nomeItemCardapio != null &&
              descricao != null &&
              idCardapio != null)
           {
               CardapioEntity cardapio = cardapioRepository.findById(idCardapio).orElseThrow(
                       ()-> new EntityNotFoundException()
               );
               if(cardapio.getFinanceiro().getMarkup() == null){throw new IllegalActionException("ação não permitida, Markup não calculado");}
               if(cardapio.getFinanceiro().getPorcentagemLucro() == null){throw new IllegalActionException("ação não permitida, margem de lucro não cadastrada");}
               ItemCardapioEntity itemCardapio = new ItemCardapioEntity();
               itemCardapio.setNome(nomeItemCardapio);
               itemCardapio.setDescricao(descricao);
               int cod = (int) (111111 + Math.random() * 999999);
               itemCardapio.setCodigo(cardapio.getNome()+"_"+cod);
               itemCardapio.setCodigoCardapio(cardapio.getCodigo());
               itemCardapio.setValor(0.0);
               itemCardapio.setTimeStamp(LocalDateTime.now());
               itemCardapioRepository.save(itemCardapio);
               cardapio.getItemCardapio().add(itemCardapio);
               ItemCardapioDTO response = new ItemCardapioDTO(itemCardapio.getNome(),
                                                               itemCardapio.getDescricao(),
                                                               itemCardapio.getCodigo(),
                                                               itemCardapio.getValor());
               return new ResponseEntity<>(response,HttpStatus.CREATED);
           }
           else
           { throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ItemCardapioDTO> EditarItemCardapio(Long idItemCardapio,
                                                                 String nomeItemCardapio,
                                                                 String descricao)
    {
        try
        {
            if(idItemCardapio != null &&
              nomeItemCardapio != null &&
              descricao != null)
            {
                ItemCardapioEntity entity = itemCardapioRepository.findById(idItemCardapio).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                entity.setNome(nomeItemCardapio);
                entity.setDescricao(descricao);
                entity.setTimeStamp(LocalDateTime.now());
                itemCardapioRepository.save(entity);
                ItemCardapioDTO response = new ItemCardapioDTO(entity.getNome(),
                        entity.getDescricao(),
                        entity.getCodigo(),
                        entity.getValor());
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ItemCardapioDTO> AdicionarIngredienteItemCardapio(Long idItemCardapio,
                                                                            Long idIngredientes)
    {
        try
        {
            if(idIngredientes != null &&
            idItemCardapio != null)
            {
                ItemCardapioEntity entity = itemCardapioRepository.findById(idItemCardapio).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                IngredienteEntity ingrediente = ingredienteRepository.findById(idIngredientes).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                Double valorParcial = entity.getValor()+ ingrediente.getValor();
                entity.getIngredientes().add(ingrediente);
                Double porcentagemSeguranca = 10.0/100;
                valorParcial = (valorParcial * porcentagemSeguranca) + valorParcial;
                CardapioEntity cardapio = cardapioRepository.findBycodigo(entity.getCodigoCardapio()).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                entity.setValor(valorParcial);
                entity.setTimeStamp(LocalDateTime.now());
                itemCardapioRepository.save(entity);
                cardapio.getItemCardapio().add(entity);
                ItemCardapioDTO response = new ItemCardapioDTO(entity.getNome(),
                        entity.getDescricao(),
                        entity.getCodigo(),
                        entity.getValor());
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ItemCardapioDTO> FinalizarItemCardapio(Long idItemCardapio)
    {
            try
            {
                if(idItemCardapio != null)
                {
                    if(idItemCardapio != null) {
                        ItemCardapioEntity entity = itemCardapioRepository.findById(idItemCardapio).orElseThrow(
                                () -> new EntityNotFoundException()
                        );
                        CardapioEntity cardapio = cardapioRepository.findBycodigo(entity.getCodigoCardapio()).orElseThrow(
                                () -> new EntityNotFoundException()
                        );
                        entity.setValor(entity.getValor() * cardapio.getFinanceiro().getMarkup());
                        entity.setTimeStamp(LocalDateTime.now());
                        itemCardapioRepository.save(entity);
                        ItemCardapioDTO response = new ItemCardapioDTO(entity.getNome(),
                                entity.getDescricao(),
                                entity.getCodigo(),
                                entity.getValor());
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }
                }
                else
                {throw new NullargumentsException();}
            }
            catch (Exception e)
            {
                e.getMessage();
            }
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    public ResponseEntity<ItemCardapioDTO> LimparIngredientesItemCardapio(Long idItemCardapio)
    {
        try
        {
            if(idItemCardapio != null)
            {
                if(idItemCardapio != null) {
                    ItemCardapioEntity entity = itemCardapioRepository.findById(idItemCardapio).orElseThrow(
                            () -> new EntityNotFoundException()
                    );
                    entity.setDescricao("aguardando nova informação");
                    entity.setIngredientes(null);
                    entity.setValor(0.0);
                    entity.setTimeStamp(LocalDateTime.now());
                    itemCardapioRepository.save(entity);
                    ItemCardapioDTO response = new ItemCardapioDTO(entity.getNome(),
                            entity.getDescricao(),
                            entity.getCodigo(),
                            entity.getValor());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ItemCardapioDTO> AlterarValorItemCardapio(Long idItemCardapio,
                                                                       Double valor)
    {
        try
        {
            if(valor < 0){throw new IllegalActionException("Valor invalido");}
            if(idItemCardapio != null &&
               valor != null)
            {
                ItemCardapioEntity entity = itemCardapioRepository.findById(idItemCardapio).orElseThrow(
                        () -> new EntityNotFoundException()
                );
                entity.setValor(valor);
                entity.setTimeStamp(LocalDateTime.now());
                itemCardapioRepository.save(entity);
                ItemCardapioDTO response = new ItemCardapioDTO(entity.getNome(),
                        entity.getDescricao(),
                        entity.getCodigo(),
                        entity.getValor());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ItemCardapioDTO> ReajusteValorItemCardapio(Long idItemCardapio,
                                                                       Double porcentagemReajuste)
    {
        try
        {
            if(porcentagemReajuste < 0){throw new IllegalActionException("Valor invalido");}
            if(idItemCardapio != null &&
               porcentagemReajuste != null)
            {
                ItemCardapioEntity entity = itemCardapioRepository.findById(idItemCardapio).orElseThrow(
                        () -> new EntityNotFoundException()
                );
                Double porcentagem = porcentagemReajuste/100;
                entity.setValor((entity.getValor()*porcentagem)+entity.getValor());
                entity.setTimeStamp(LocalDateTime.now());
                itemCardapioRepository.save(entity);
                ItemCardapioDTO response = new ItemCardapioDTO(entity.getNome(),
                        entity.getDescricao(),
                        entity.getCodigo(),
                        entity.getValor());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ItemCardapioDTO> DescontoValorItemCardapio(Long idItemCardapio,
                                                                        Double porcentagemDesconto)
    {
        try
        {
            if(porcentagemDesconto < 0){throw new IllegalActionException("Valor invalido");}
            if(idItemCardapio != null &&
               porcentagemDesconto != null)
            {
                ItemCardapioEntity entity = itemCardapioRepository.findById(idItemCardapio).orElseThrow(
                        () -> new EntityNotFoundException()
                );
                Double porcentagem = porcentagemDesconto/100;
                entity.setValor((entity.getValor()*porcentagem)-entity.getValor());
                itemCardapioRepository.save(entity);
                ItemCardapioDTO response = new ItemCardapioDTO(entity.getNome(),
                        entity.getDescricao(),
                        entity.getCodigo(),
                        entity.getValor());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

}
