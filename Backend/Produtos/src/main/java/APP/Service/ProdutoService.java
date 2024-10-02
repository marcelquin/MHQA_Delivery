package APP.Service;

import APP.DTO.IngredienteDTO;
import APP.DTO.ProdutoDTO;
import APP.DTO.ProdutoResponseDTO;
import APP.Entity.IngredienteEntity;
import APP.Entity.ProdutoEntity;
import APP.Enum.UNIDADEMEDIDA;
import APP.Exceptions.EntityNotFoundException;
import APP.Exceptions.IllegalActionException;
import APP.Exceptions.NullargumentsException;
import APP.Repositoty.IngredienteRepository;
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
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final IngredienteRepository ingredienteRepository;
    Locale localBrasil = new Locale("pt", "BR");
    public ProdutoService(ProdutoRepository produtoRepository, IngredienteRepository ingredienteRepository) {
        this.produtoRepository = produtoRepository;
        this.ingredienteRepository = ingredienteRepository;
    }

    public ResponseEntity<List<ProdutoResponseDTO>> ListarProdutos()
    {
        try
        {
            List<ProdutoResponseDTO> response = new ArrayList<>();
            List<ProdutoEntity> entities = produtoRepository.findAll();
            for(ProdutoEntity entity : entities)
            {
                ProdutoResponseDTO dto = new ProdutoResponseDTO(entity.getId(),
                        entity.getNome(),
                        entity.getCodigo(),
                        entity.getQuantidade(),
                        entity.getUnidademedida(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValorPorcao()),
                        entity.getTimeStamp());
                response.add(dto);
            }
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ProdutoDTO> BuscarProdutoPorId(Long idProduto)
    {
        try
        {
            if(idProduto != null)
            {
                ProdutoEntity entity = produtoRepository.findById(idProduto).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                ProdutoDTO response = new ProdutoDTO(entity.getNome(),
                        entity.getQuantidade()+""+entity.getUnidademedida(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValorPorcao()));
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            else
            {throw  new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ProdutoDTO> novoProduto(String nome,
                                                  Double quantidade,
                                                  Double unidade,
                                                  UNIDADEMEDIDA unidademedida,
                                                  Double valorCompra)
    {
        try
        {
            if(quantidade < 0){throw new IllegalActionException("valor invalido");}
            if(unidade < 0){throw new IllegalActionException("valor invalido");}
            if(valorCompra < 0){throw new IllegalActionException("valor invalido");}
            if(nome != null &&
            quantidade != null &&
            unidade != null &&
            unidademedida != null &&
            valorCompra != null)
            {
                int cod1 = (int) (111 + Math.random() * 999);
                ProdutoEntity entity = new ProdutoEntity();
                entity.setNome(nome);
                entity.setQuantidade(quantidade * unidade);
                entity.setCodigo("pd_"+cod1);
                Double valorUnidade = 0.0;
                if(unidademedida == UNIDADEMEDIDA.UNIDADE)
                {
                    valorUnidade = valorCompra/ entity.getQuantidade();
                }
                if(unidademedida != UNIDADEMEDIDA.UNIDADE)
                {
                    valorUnidade = entity.getQuantidade() * 1000;
                    valorUnidade = valorCompra/ valorUnidade;
                }
                System.out.println(valorUnidade);
                entity.setValorPorcao(valorUnidade);
                entity.setTimeStamp(LocalDateTime.now());
                entity.setUnidademedida(unidademedida);
                produtoRepository.save(entity);
                ProdutoDTO response = new ProdutoDTO(entity.getNome(),
                                                     entity.getQuantidade()+""+unidademedida,
                                                      NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValorPorcao()));
                return new ResponseEntity<>(response, HttpStatus.CREATED);
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

    public ResponseEntity<ProdutoDTO> EditarProduto(Long idProduto,
                                                    String nome,
                                                    Double quantidade,
                                                    Double unidade,
                                                    UNIDADEMEDIDA unidademedida,
                                                    Double valorCompra)
    {
        try
        {
            if(quantidade < 0){throw new IllegalActionException("valor invalido");}
            if(unidade < 0){throw new IllegalActionException("valor invalido");}
            if(valorCompra < 0){throw new IllegalActionException("valor invalido");}
            if(nome != null &&
                    quantidade != null &&
                    unidade != null &&
                    unidademedida != null &&
                    valorCompra != null &&
            idProduto != null)
            {
                int cod1 = (int) (111 + Math.random() * 999);
                ProdutoEntity entity = produtoRepository.findById(idProduto).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                entity.setNome(nome);
                entity.setQuantidade(quantidade * unidade);
                entity.setCodigo("pd_"+cod1);
                Double valorUnidade = 0.0;
                if(unidademedida == UNIDADEMEDIDA.UNIDADE)
                {
                    valorUnidade = valorCompra/ entity.getQuantidade();
                }
                if(unidademedida != UNIDADEMEDIDA.UNIDADE)
                {
                    valorUnidade = entity.getQuantidade() * 1000;
                    valorUnidade = valorCompra/ valorUnidade;
                }
                System.out.println(valorUnidade);
                entity.setValorPorcao(valorUnidade);
                entity.setTimeStamp(LocalDateTime.now());
                entity.setUnidademedida(unidademedida);
                produtoRepository.save(entity);
                ProdutoDTO response = new ProdutoDTO(entity.getNome(),
                        entity.getQuantidade()+""+unidademedida,
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValorPorcao()));
                return new ResponseEntity<>(response, HttpStatus.CREATED);
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
