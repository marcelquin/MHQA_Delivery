package APP.Service;

import APP.DTO.IngredienteDTO;
import APP.DTO.IngredienteResponseDTO;
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
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;
    private final ProdutoRepository produtoRepository;

    Locale localBrasil = new Locale("pt", "BR");

    public IngredienteService(IngredienteRepository ingredienteRepository, ProdutoRepository produtoRepository) {
        this.ingredienteRepository = ingredienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public ResponseEntity<List<IngredienteResponseDTO>> ListarIngredientes()
    {
        try
        {
            List<IngredienteResponseDTO> response = new ArrayList<>();
            List<IngredienteEntity> entities = ingredienteRepository.findAll();
            for(IngredienteEntity entity : entities)
            {
                IngredienteResponseDTO dto = new IngredienteResponseDTO(entity.getId(),
                        entity.getNome(),
                        entity.getQuantidade(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()),
                        entity.getTimeStamp());
                response.add(dto);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<IngredienteDTO> BuscarIngredientePorId(Long idIngrediente)
    {
        try
        {
            if(idIngrediente != null)
            {
                IngredienteEntity entity = ingredienteRepository.findById(idIngrediente).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                ProdutoEntity produto = produtoRepository.findBycodigo(entity.getCodigoProduto()).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                String medida = "";
                if(produto.getUnidademedida() == UNIDADEMEDIDA.KG)
                { medida = "g";}
                if(produto.getUnidademedida() == UNIDADEMEDIDA.L)
                { medida = "ml";}
                if(produto.getUnidademedida() == UNIDADEMEDIDA.UNIDADE)
                { medida = "u";}
                IngredienteDTO response = new IngredienteDTO(entity.getNome(),
                        entity.getQuantidade()+""+medida,
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()));
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

    public ResponseEntity<IngredienteDTO> novoIngrediente(Long idProduto,
                                                          String nomeIngrediente,
                                                          Double quantidade)
    {
        try
        {
            if(quantidade < 0){throw new IllegalActionException("valor invalido");}
            if(idProduto != null &&
               quantidade != null)
            {
                ProdutoEntity produto = produtoRepository.findById(idProduto).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                IngredienteEntity entity = new IngredienteEntity();
                if(nomeIngrediente != null)
                {
                    entity.setNome(nomeIngrediente);
                }
                else
                {
                    entity.setNome(produto.getNome());
                }
                entity.setQuantidade(quantidade);
                entity.setCodigoProduto(produto.getCodigo());
                entity.setValor(produto.getValorPorcao() * quantidade);
                entity.setProduto(produto);
                entity.setTimeStamp(LocalDateTime.now());
                ingredienteRepository.save(entity);
                String medida = "";
                if(produto.getUnidademedida() == UNIDADEMEDIDA.KG)
                { medida = "g";}
                if(produto.getUnidademedida() == UNIDADEMEDIDA.L)
                { medida = "ml";}
                if(produto.getUnidademedida() == UNIDADEMEDIDA.UNIDADE)
                { medida = "u";}
                IngredienteDTO response = new IngredienteDTO(entity.getNome(),
                        entity.getQuantidade()+""+medida,
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()));
                return new ResponseEntity<>(response,HttpStatus.CREATED);
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

    public ResponseEntity<IngredienteDTO> EditarIngrediente(Long idIngrediente,
                                                            String nomeIngrediente,
                                                            Double quantidade)
    {
        try
        {
            if(quantidade < 0){throw new IllegalActionException("valor invalido");}
            if(idIngrediente != null &&
                    quantidade != null)
            {
                IngredienteEntity entity = ingredienteRepository.findById(idIngrediente).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                entity.setNome(nomeIngrediente);
                entity.setQuantidade(quantidade);
                ProdutoEntity produto = produtoRepository.findBycodigo(entity.getCodigoProduto()).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                entity.setValor(produto.getValorPorcao() * quantidade);
                entity.setProduto(produto);
                entity.setTimeStamp(LocalDateTime.now());
                ingredienteRepository.save(entity);
                String medida = "";
                if(produto.getUnidademedida() == UNIDADEMEDIDA.KG)
                { medida = "g";}
                if(produto.getUnidademedida() == UNIDADEMEDIDA.L)
                { medida = "ml";}
                if(produto.getUnidademedida() == UNIDADEMEDIDA.UNIDADE)
                { medida = "u";}
                IngredienteDTO response = new IngredienteDTO(entity.getNome(),
                        entity.getQuantidade()+""+medida,
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()));
                return new ResponseEntity<>(response,HttpStatus.CREATED);
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
