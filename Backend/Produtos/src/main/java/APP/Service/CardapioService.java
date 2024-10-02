package APP.Service;

import APP.DTO.*;
import APP.Entity.*;
import APP.Exceptions.EntityNotFoundException;
import APP.Exceptions.IllegalActionException;
import APP.Exceptions.NullargumentsException;
import APP.Repositoty.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CardapioService {

    private final CardapioRepository cardapioRepository;
    private final IngredienteRepository ingredienteRepository;
    private final ProdutoRepository produtoRepository;
    private final FinanceiroRepository financeiroRepository;
    private final CustoRepository custoRepository;
    private final CustoFixoRepository custoFixoRepository;
    private final CustoVariadoRepository custoVariadoRepository;

    public CardapioService(CardapioRepository cardapioRepository, IngredienteRepository ingredienteRepository, ProdutoRepository produtoRepository, FinanceiroRepository financeiroRepository, CustoRepository custoRepository, CustoFixoRepository custoFixoRepository, CustoVariadoRepository custoVariadoRepository) {
        this.cardapioRepository = cardapioRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.produtoRepository = produtoRepository;
        this.financeiroRepository = financeiroRepository;
        this.custoRepository = custoRepository;
        this.custoFixoRepository = custoFixoRepository;
        this.custoVariadoRepository = custoVariadoRepository;
    }

    Locale localBrasil = new Locale("pt", "BR");


    public ResponseEntity<List<CardapioResponseDTO>> ListarCardapios()
    {
        try
        {
            List<CardapioResponseDTO> response = new ArrayList<>();
            List<CardapioEntity> entities = cardapioRepository.findAll();
            for(CardapioEntity entity : entities)
            {
                List<ItemCardapioResponseDTO> itemCardapioResponseDTOS = new ArrayList<>();
                for(ItemCardapioEntity itemCardapio : entity.getItemCardapio())
                {
                    List<IngredienteResponseDTO> ingredienteResponseDTOS = new ArrayList<>();
                    for(IngredienteEntity ingrediente : itemCardapio.getIngredientes())
                    {
                        IngredienteResponseDTO ingredienteDto = new IngredienteResponseDTO(
                                ingrediente.getId(),
                                ingrediente.getNome(),
                                ingrediente.getQuantidade(),
                                NumberFormat.getCurrencyInstance(localBrasil).format(ingrediente.getValor()),
                                ingrediente.getTimeStamp()
                        );
                    ingredienteResponseDTOS.add(ingredienteDto);
                    }
                    ItemCardapioResponseDTO dto = new ItemCardapioResponseDTO(itemCardapio.getId(),
                            itemCardapio.getNome(),
                            itemCardapio.getDescricao(),
                            itemCardapio.getCodigo(),
                            NumberFormat.getCurrencyInstance(localBrasil).format(itemCardapio.getValor()),
                            ingredienteResponseDTOS,
                            itemCardapio.getTimeStamp());
                    itemCardapioResponseDTOS.add(dto);
                }
                CardapioResponseDTO responseDTO = new CardapioResponseDTO(entity.getId(),
                        entity.getNome(),
                        entity.getCodigo(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getFinanceiro().getPorcentagemLucro()),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getFinanceiro().getMarkup()),
                        entity.getTimeStamp(),
                        itemCardapioResponseDTOS
                        );
                response.add(responseDTO);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<CardapioDTO> BuscarCardapioPorId(Long idCardapio)
    {
        try
        {
            if(idCardapio != null)
            {
                CardapioEntity entity = cardapioRepository.findById(idCardapio).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                List<ItemCardapioDTO> itemCardapioDTOList = new ArrayList<>();
                for(ItemCardapioEntity itemCardapio : entity.getItemCardapio())
                {
                    ItemCardapioDTO dto = new ItemCardapioDTO(itemCardapio.getNome(),
                                                              itemCardapio.getDescricao(),
                                                              itemCardapio.getCodigo(),
                                                              itemCardapio.getValor());
                    itemCardapioDTOList.add(dto);
                }
                CardapioDTO response = new CardapioDTO(entity.getNome(),
                                                       entity.getCodigo(),
                                                       NumberFormat.getCurrencyInstance(localBrasil).format(entity.getFinanceiro().getPorcentagemLucro()),
                                                       NumberFormat.getCurrencyInstance(localBrasil).format(entity.getFinanceiro().getMarkup()),
                                                       entity.getTimeStamp(),
                                                       itemCardapioDTOList);
                return new ResponseEntity<>(response, HttpStatus.OK);
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

    public ResponseEntity<CardapioDTO> NovoCardapio(String nome,
                                                       Double porcetagemLucro,
                                                       Double rendimentoMensal)
    {
        try
        {
            if(porcetagemLucro < 0){throw new IllegalActionException("Valor Invalido");}
            if(rendimentoMensal < 0){throw new IllegalActionException("Valor Invalido");}
            if(nome != null &&
            porcetagemLucro != null &&
            rendimentoMensal != null)
            {
                CardapioEntity entity = new CardapioEntity();
                int cod = (int) (111111 + Math.random() * 999999);
                int cod1 = (int) (111 + Math.random() * 999);
                entity.setNome(nome);
                entity.setCodigo(cod+"-"+cod1);
                FinanceiroEntity financeiro = new FinanceiroEntity();
                financeiro.setMarkup(0.0);
                financeiro.setPorcentagemLucro(porcetagemLucro);
                financeiro.setRendimentoMensal(rendimentoMensal);
                financeiro.setTimeStamp(LocalDateTime.now());
                CustoFixoEntity custoFixo = new CustoFixoEntity();
                custoFixo.setTimeStamp(LocalDateTime.now());
                custoFixo.setValor(0.0);
                custoFixo.setValorPorcenagem(0.0);
                custoFixoRepository.save(custoFixo);
                financeiro.setCustosFixo(custoFixo);
                CustoVariavelEntity custoVariavel = new CustoVariavelEntity();
                custoVariavel.setTimeStamp(LocalDateTime.now());
                custoVariavel.setValor(0.0);
                custoVariavel.setValorPorcenagem(0.0);
                custoVariadoRepository.save(custoVariavel);
                financeiro.setCustosVariavel(custoVariavel);
                financeiroRepository.save(financeiro);
                entity.setFinanceiro(financeiro);
                entity.setTimeStamp(LocalDateTime.now());
                cardapioRepository.save(entity);
                List<ItemCardapioDTO> itemCardapioDTOList = new ArrayList<>();
                for(ItemCardapioEntity itemCardapio : entity.getItemCardapio())
                {
                    ItemCardapioDTO dto = new ItemCardapioDTO(itemCardapio.getNome(),
                            itemCardapio.getDescricao(),
                            itemCardapio.getCodigo(),
                            itemCardapio.getValor());
                    itemCardapioDTOList.add(dto);
                }
                CardapioDTO response = new CardapioDTO(entity.getNome(),
                        entity.getCodigo(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getFinanceiro().getPorcentagemLucro()),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getFinanceiro().getMarkup()),
                        entity.getTimeStamp(),
                        itemCardapioDTOList);
                return new ResponseEntity<>(response, HttpStatus.OK);
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

    public ResponseEntity<CardapioDTO> EditarCardapio(Long idCardapio,
                                                         String nome,
                                                         Double porcetagemLucro,
                                                         Double rendimentoMensal)
    {
        try
        {
            if(porcetagemLucro < 0){throw new IllegalActionException("Valor Invalido");}
            if(rendimentoMensal < 0){throw new IllegalActionException("Valor Invalido");}
            if(nome != null &&
                    porcetagemLucro != null &&
                    rendimentoMensal != null &&
            idCardapio != null)
            {
                CardapioEntity entity = cardapioRepository.findById(idCardapio).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                entity.setNome(nome);
                FinanceiroEntity financeiro = financeiroRepository.findById(entity.getFinanceiro().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                financeiro.setPorcentagemLucro(porcetagemLucro);
                financeiro.setRendimentoMensal(rendimentoMensal);
                financeiro.setTimeStamp(LocalDateTime.now());
                financeiroRepository.save(financeiro);
                entity.setTimeStamp(LocalDateTime.now());
                cardapioRepository.save(entity);
                List<ItemCardapioDTO> itemCardapioDTOList = new ArrayList<>();
                for(ItemCardapioEntity itemCardapio : entity.getItemCardapio())
                {
                    ItemCardapioDTO dto = new ItemCardapioDTO(itemCardapio.getNome(),
                            itemCardapio.getDescricao(),
                            itemCardapio.getCodigo(),
                            itemCardapio.getValor());
                    itemCardapioDTOList.add(dto);
                }
                CardapioDTO response = new CardapioDTO(entity.getNome(),
                        entity.getCodigo(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getFinanceiro().getPorcentagemLucro()),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getFinanceiro().getMarkup()),
                        entity.getTimeStamp(),
                        itemCardapioDTOList);
                return new ResponseEntity<>(response, HttpStatus.OK);
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


}
