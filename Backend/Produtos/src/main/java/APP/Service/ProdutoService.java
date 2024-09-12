package APP.Service;

import APP.DTO.EstoqueDTO;
import APP.DTO.EstoqueResponseDTO;
import APP.DTO.ProdutoDTO;
import APP.Entity.EstoqueEntity;
import APP.Entity.ProdutoEntity;
import APP.Exceptions.EntityNotFoundException;
import APP.Exceptions.IllegalActionException;
import APP.Exceptions.NullargumentsException;
import APP.Repository.EstoqueRepository;
import APP.Repository.ProdutoRepository;
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

    private final EstoqueRepository estoqueRepository;

    public ProdutoService(ProdutoRepository produtoRepository, EstoqueRepository estoqueRepository) {
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }
    Locale localBrasil = new Locale("pt", "BR");

    public ResponseEntity<List<ProdutoDTO>> ListarProdutos()
    {
        try
        {
            List<ProdutoDTO> response = new ArrayList<>();
            List<ProdutoEntity> list = produtoRepository.findAll();
            for(ProdutoEntity produto : list)
            {
                ProdutoDTO dto = new ProdutoDTO(produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getCodigo(),
                        produto.getEstoque().getCodigo(),
                        produto.getEstoque().getQuantidade().intValue(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(produto.getValor()),
                        produto.getNotificacao(),
                        produto.getDataEntrada());
                response.add(dto);
            }
            return new ResponseEntity<> (response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<EstoqueDTO>> ListarEstoque()
    {
        try
        {
            List<EstoqueDTO> response = new ArrayList<>();
            List<ProdutoEntity> list = produtoRepository.findAll();
            for(ProdutoEntity produto : list)
            {
                EstoqueDTO dto = new EstoqueDTO(
                        produto.getEstoque().getId(),
                        produto.getEstoque().getNome(),
                        produto.getEstoque().getDescricao(),
                        produto.getEstoque().getCodigo(),
                        produto.getEstoque().getQuantidade().intValue(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(produto.getValor()));
                response.add(dto);
            }
            return new ResponseEntity<> (response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ProdutoDTO> BuscarProdutoPorId(Long id)
    {
        try
        {
            if(id != null)
            {
                ProdutoEntity produto = produtoRepository.findById(id).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                ProdutoDTO response = new ProdutoDTO(produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getCodigo(),
                        produto.getEstoque().getCodigo(),
                        produto.getEstoque().getQuantidade().intValue(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(produto.getValor()),
                        produto.getNotificacao(),
                        produto.getDataEntrada()
                );
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<EstoqueDTO> BuscarEstoquePorId(Long id)
    {
        try
        {
            if(id != null)
            {
                EstoqueEntity estoque = estoqueRepository.findById(id).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                EstoqueDTO response = new EstoqueDTO(
                        estoque.getId(),
                        estoque.getNome(),
                        estoque.getDescricao(),
                        estoque.getCodigo(),
                        estoque.getQuantidade().intValue(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(estoque.getValor())
                );
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<EstoqueResponseDTO> NovoProduto(String nome,
                                                          String descricao,
                                                          Double valorCompra,
                                                          Double estoque,
                                                          Double porcentagemLucro)
    {
        try
        {
            if(valorCompra < 0){throw new IllegalActionException("Valor Invalido");}
            if(estoque < 0){throw new IllegalActionException("Valor Invalido");}
            if(porcentagemLucro < 0){throw new IllegalActionException("Valor Invalido");}
            if(nome != null &&
               descricao != null &&
               valorCompra != null &&
               estoque != null &&
               porcentagemLucro != null)
            {
               Double porcentagem = porcentagemLucro/100;
               Double valorTotal = (valorCompra * porcentagem)+ valorCompra;
               Double valorUnitario = valorTotal/estoque;
               int codProduto = (int) (1111 + Math.random() * 9999);
               int codEstoque1 = (int) (1111 + Math.random() * 9999);
               int codEstoque2 = (int) (11111 + Math.random() * 99991);
               ProdutoEntity produto = new ProdutoEntity();
               produto.setNome(nome);
               produto.setDescricao(descricao);
               produto.setCodigo("p_"+codProduto);
               produto.setTimeStamp(LocalDateTime.now());
               produto.setDataEntrada(LocalDateTime.now());
               produto.setValor(valorUnitario);
               EstoqueEntity entity = new EstoqueEntity();
               entity.setNome(produto.getNome());
               entity.setDescricao(produto.getDescricao());
               entity.setCodigo(codEstoque1+"_"+codEstoque2);
               entity.setQuantidade(estoque);
               entity.setValor(produto.getValor());
               entity.setTimeStamp(LocalDateTime.now());
               estoqueRepository.save(entity);
               produto.setEstoque(entity);
               produtoRepository.save(produto);
               EstoqueResponseDTO response = new EstoqueResponseDTO(entity.getNome(),
                       entity.getDescricao(),
                       entity.getCodigo(),
                       entity.getQuantidade(),
                       NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()));
               return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<EstoqueResponseDTO> EditarProduto(Long id,
                                                            String nome,
                                                            String descricao,
                                                            Double valorCompra,
                                                            Double estoque,
                                                            Double porcentagemLucro)
    {
        try
        {
            if(valorCompra < 0){throw new IllegalActionException("Valor Invalido");}
            if(estoque < 0){throw new IllegalActionException("Valor Invalido");}
            if(porcentagemLucro < 0){throw new IllegalActionException("Valor Invalido");}
            if(nome != null &&
                    descricao != null &&
                    valorCompra != null &&
                    estoque != null &&
                    porcentagemLucro != null)
            {
                Double porcentagem = porcentagemLucro/100;
                Double valorTotal = (valorCompra * porcentagem)+ valorCompra;
                Double valorUnitario = valorTotal/estoque;
                ProdutoEntity produto = produtoRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                produto.setNome(nome);
                produto.setDescricao(descricao);
                produto.setTimeStamp(LocalDateTime.now());
                produto.setValor(valorUnitario);
                EstoqueEntity entity = estoqueRepository.findById(produto.getEstoque().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                entity.setNome(produto.getNome());
                entity.setDescricao(produto.getDescricao());
                entity.setValor(produto.getValor());
                entity.setTimeStamp(LocalDateTime.now());
                estoqueRepository.save(entity);
                produtoRepository.save(produto);
                EstoqueResponseDTO response = new EstoqueResponseDTO(entity.getNome(),
                                                                    entity.getDescricao(),
                                                                    entity.getCodigo(),
                                                                    entity.getQuantidade(),
                                                                    NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()));
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<EstoqueResponseDTO> AdicionarEstoque(Long id,
                                                       Double valorCompra,
                                                       Double estoque,
                                                       Double porcentagemLucro)
    {
        try
        {
            if(valorCompra < 0){throw new IllegalActionException("Valor Invalido");}
            if(estoque < 0){throw new IllegalActionException("Valor Invalido");}
            if(porcentagemLucro < 0){throw new IllegalActionException("Valor Invalido");}
            if(id != null && valorCompra != null &&
               estoque != null &&
               porcentagemLucro != null)
            {
                Double porcentagem = porcentagemLucro / 100;
                Double valorTotal = (valorCompra * porcentagem) + valorCompra;
                Double valorUnitario = valorTotal / estoque;
                ProdutoEntity produto = produtoRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                EstoqueEntity entity = estoqueRepository.findById(produto.getEstoque().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                if(valorUnitario > produto.getValor())
                {
                    produto.setValor(valorUnitario);
                    entity.setValor(produto.getValor());
                    entity.setQuantidade(entity.getQuantidade()+estoque);
                }
                entity.setQuantidade(entity.getQuantidade()+estoque);
                produto.setTimeStamp(LocalDateTime.now());
                entity.setTimeStamp(LocalDateTime.now());
                estoqueRepository.save(entity);
                produtoRepository.save(produto);
                EstoqueResponseDTO response = new EstoqueResponseDTO(entity.getNome(),
                        entity.getDescricao(),
                        entity.getCodigo(),
                        entity.getQuantidade(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<EstoqueResponseDTO> NovoValorProduto(Long id,
                                                               Double novoValor)
    {
        try
        {
            if(novoValor < 0){throw new IllegalActionException("valor Invalido");}
            if(id != null &&
               novoValor != null)
            {
                ProdutoEntity produto = produtoRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                EstoqueEntity entity = estoqueRepository.findById(produto.getEstoque().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                produto.setValor(novoValor);
                entity.setValor(produto.getValor());
                produto.setTimeStamp(LocalDateTime.now());
                entity.setTimeStamp(LocalDateTime.now());
                estoqueRepository.save(entity);
                produtoRepository.save(produto);
                EstoqueResponseDTO response = new EstoqueResponseDTO(entity.getNome(),
                                                                    entity.getDescricao(),
                                                                    entity.getCodigo(),
                                                                    entity.getQuantidade(),
                                                                    NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<EstoqueResponseDTO> reajusteValorProduto(Long id,
                                                                   Double porcentagemReajuste)
    {
        try
        {
            if(porcentagemReajuste < 0){throw new IllegalActionException("valor Invalido");}
            if(id != null && porcentagemReajuste != null)
            {
                Double porcentagem =  porcentagemReajuste/ 100;
                ProdutoEntity produto = produtoRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                EstoqueEntity entity = estoqueRepository.findById(produto.getEstoque().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                Double valorUnitario = (produto.getValor() * porcentagem) + produto.getValor();
                produto.setValor(valorUnitario);
                produto.setTimeStamp(LocalDateTime.now());
                entity.setValor(produto.getValor());
                entity.setTimeStamp(LocalDateTime.now());
                estoqueRepository.save(entity);
                produtoRepository.save(produto);
                EstoqueResponseDTO response = new EstoqueResponseDTO(entity.getNome(),
                        entity.getDescricao(),
                        entity.getCodigo(),
                        entity.getQuantidade(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<EstoqueResponseDTO> DescontoValorProduto(Long id,
                                                                   Double porcentagemDesconto)
    {
        try
        {
            if(porcentagemDesconto < 0){throw new IllegalActionException("valor Invalido");}
            if(id != null && porcentagemDesconto != null)
            {
                Double porcentagem =  porcentagemDesconto/ 100;
                ProdutoEntity produto = produtoRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                EstoqueEntity entity = estoqueRepository.findById(produto.getEstoque().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                Double valorUnitario = (produto.getValor() * porcentagem) - produto.getValor();
                produto.setValor(valorUnitario);
                produto.setTimeStamp(LocalDateTime.now());
                entity.setValor(produto.getValor());
                entity.setTimeStamp(LocalDateTime.now());
                estoqueRepository.save(entity);
                produtoRepository.save(produto);
                EstoqueResponseDTO response = new EstoqueResponseDTO(entity.getNome(),
                        entity.getDescricao(),
                        entity.getCodigo(),
                        entity.getQuantidade(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public void deletarProduto(Long id)
    {
        try
        {

            if(id != null)
            {
                ProdutoEntity entity = produtoRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                if(entity.getEstoque() != null)
                {
                    estoqueRepository.deleteById(entity.getEstoque().getId());
                    produtoRepository.deleteById(entity.getId());
                }
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }
}
