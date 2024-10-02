package APP.Service;

import APP.Entity.*;
import APP.Exceptions.EntityNotFoundException;
import APP.Exceptions.IllegalActionException;
import APP.Exceptions.NullargumentsException;
import APP.Repositoty.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class FinanceiroService {

    private final CustoRepository custoRepository;
    private final CustoFixoRepository custoFixoRepository;
    private final CustoVariadoRepository custoVariadoRepository;
    private final CardapioRepository cardapioRepository;
    private final FinanceiroRepository financeiroRepository;

    public FinanceiroService(CustoRepository custoRepository, CustoFixoRepository custoFixoRepository, CustoVariadoRepository custoVariadoRepository, CardapioRepository cardapioRepository, FinanceiroRepository financeiroRepository) {
        this.custoRepository = custoRepository;
        this.custoFixoRepository = custoFixoRepository;
        this.custoVariadoRepository = custoVariadoRepository;
        this.cardapioRepository = cardapioRepository;
        this.financeiroRepository = financeiroRepository;
    }

    Locale localBrasil = new Locale("pt", "BR");

    public ResponseEntity<List<CustoFixoEntity>> ListarCustosFixos()
    {
        try
        {
            return new ResponseEntity<>(custoFixoRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }


    public void NovoLancamentoCustoFixo(Long idCardapio,
                                                               String nome,
                                                               Double valor)
    {
        try
        {
            if(valor < 0){throw new IllegalActionException("valor invalido");}
            if(nome != null &&
              valor != null &&
              idCardapio != null)
            {
                CardapioEntity cardapio = cardapioRepository.findById(idCardapio).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                FinanceiroEntity financeiro = financeiroRepository.findById(cardapio.getFinanceiro().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                CustoFixoEntity custoFixo = custoFixoRepository.findById(financeiro.getCustosFixo().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                CustoEntity custoEntity = new CustoEntity();
                custoEntity.setNome(nome);
                custoEntity.setValor(valor);
                custoEntity.setTimeStamp(LocalDateTime.now());
                custoRepository.save(custoEntity);
                if(custoFixo.getCustos() == null)
                {
                    custoFixo.setCustos(Collections.singletonList(custoEntity));
                }
                else
                {
                    custoFixo.getCustos().add(custoEntity);
                }
                custoFixo.setValor(custoFixo.getValor()+custoEntity.getValor());
                custoFixoRepository.save(custoFixo);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public void NovoLancamentoCustoVariavel(Long idCardapio,
                                                                       String nome,
                                                                       Double valor,
                                                                       Double porcentagem)
    {
        try
        {
            if(valor < 0){throw new IllegalActionException("valor invalido");}
            if(porcentagem < 0){throw new IllegalActionException("valor invalido");}
            if(nome != null &&
               idCardapio != null)
            {
                CardapioEntity cardapio = cardapioRepository.findById(idCardapio).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                FinanceiroEntity financeiro = financeiroRepository.findById(cardapio.getFinanceiro().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                CustoVariavelEntity custoVariavel = custoVariadoRepository.findById(financeiro.getCustosVariavel().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                CustoEntity custoEntity = new CustoEntity();
                custoEntity.setNome(nome);
                if(valor != null){custoEntity.setValor(valor);}
                if(porcentagem != null){custoEntity.setValorPorcenagem(porcentagem);}
                custoEntity.setTimeStamp(LocalDateTime.now());
                custoRepository.save(custoEntity);
                if(custoVariavel.getCustos() == null)
                {
                    custoVariavel.setCustos(Collections.singletonList(custoEntity));
                }
                else
                {
                    custoVariavel.getCustos().add(custoEntity);
                }
                if(valor != null)
                {
                    custoVariavel.setValor(custoVariavel.getValor()+custoEntity.getValor());
                }
                if(porcentagem != null)
                {
                    custoVariavel.setValorPorcenagem(custoEntity.getValorPorcenagem()+custoEntity.getValorPorcenagem());
                }
                custoVariadoRepository.save(custoVariavel);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public void EditarCustoVariavel(Long idCusto,
                                String nome,
                                Double valor,
                                Double porcentagem)
    {
        try
        {
            if(valor<0){throw new IllegalActionException("valor invalido");}
            if(porcentagem<0){throw new IllegalActionException("valor invalido");}
            if(idCusto != null &&
                    nome != null)
            {
                CustoEntity custo = custoRepository.findById(idCusto).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                custo.setNome(nome);
                if(valor != null)
                {
                    custo.setValor(valor);
                }
                if(porcentagem != null)
                {
                    custo.setValorPorcenagem(porcentagem);
                }
                custo.setTimeStamp(LocalDateTime.now());
                custoRepository.save(custo);
            }
            else
            { throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public void EditarCustoFixo(Long idCusto,
                                String nome,
                                Double valor,
                                Double porcentagem)
    {
        try
        {
            if(valor<0){throw new IllegalActionException("valor invalido");}
            if(porcentagem<0){throw new IllegalActionException("valor invalido");}
            if(idCusto != null &&
               nome != null)
            {
               CustoEntity custo = custoRepository.findById(idCusto).orElseThrow(
                       ()->new EntityNotFoundException()
               );
               custo.setNome(nome);
               if(valor != null)
               {
                   custo.setValor(valor);
               }
                if(porcentagem != null)
                {
                    custo.setValorPorcenagem(porcentagem);
                }
                custo.setTimeStamp(LocalDateTime.now());
                custoRepository.save(custo);
            }
            else
            { throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public void CalcularMarkup(Long idCardapio)
    {
        try
        {
            if(idCardapio != null)
            {
                CardapioEntity cardapio = cardapioRepository.findById(idCardapio).orElseThrow(
                        ()->new EntityNotFoundException()
                );
                FinanceiroEntity financeiro = financeiroRepository.findById(cardapio.getFinanceiro().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                CustoFixoEntity custoFixo = custoFixoRepository.findById(financeiro.getCustosFixo().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                CustoVariavelEntity custoVariavel = custoVariadoRepository.findById(financeiro.getCustosVariavel().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                Double porcentagemCustoFixo = (custoFixo.getValor()/financeiro.getRendimentoMensal())*100;
                if(custoVariavel.getValor() != null)
                {
                    Double porcentagemCustoVariavel = (custoVariavel.getValor()/financeiro.getRendimentoMensal())*100;
                    custoVariavel.setValorPorcenagem(custoVariavel.getValorPorcenagem()+ porcentagemCustoVariavel);
                }
                custoFixo.setValorPorcenagem(custoFixo.getValorPorcenagem() + porcentagemCustoFixo);
                Double somaPorcentagem = custoFixo.getValorPorcenagem() +
                                         financeiro.getPorcentagemLucro() +
                                         custoVariavel.getValorPorcenagem();
                Double markup = 100-somaPorcentagem;
                markup = 100/markup;
                financeiro.setMarkup(markup);
                custoVariavel.setTimeStamp(LocalDateTime.now());
                custoFixo.setTimeStamp(LocalDateTime.now());
                financeiro.setTimeStamp(LocalDateTime.now());
                custoFixoRepository.save(custoFixo);
                custoVariadoRepository.save(custoVariavel);
                financeiroRepository.save(financeiro);
                System.out.println("Porcentagem custo variavel: "+custoVariavel.getValorPorcenagem());
                System.out.println("Porcentagem custo variavel: "+custoFixo.getValorPorcenagem());
                System.out.println("porcentagem lucro: "+financeiro.getPorcentagemLucro());
                System.out.println("soma de porcentagem: "+somaPorcentagem);
                System.out.println("markup: "+markup);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

}
