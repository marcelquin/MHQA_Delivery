package APP.Service;

import APP.Dto.BoletosRequestDTO;
import APP.Dto.RelatorioAnualDTO;
import APP.Dto.RelatorioDiarioDTO;
import APP.Dto.RelatorioMensalDTO;
import APP.Enum.FORMAPAGAMENTO;
import APP.Exceptions.IllegalActionException;
import APP.Exceptions.NullargumentsException;
import APP.ServiceMs.FinanceiroDeliveryMSService;
import APP.ServiceMs.FinanceiroMsService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Service
public class FinanceiroService {

    @Autowired
    private FinanceiroDeliveryMSService service;

    @Autowired
    private FinanceiroMsService financeiroMsService;

    public void NovoLancamentoCustoFixo(Long idCardapio,
                                        String nome,
                                        Double valor) {
        try {
            service.NovoLancamentoCustoFixo(idCardapio, nome, valor);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void NovoLancamentoCustoVariavel(Long idCardapio,
                                            String nome,
                                            Double valor,
                                            Double porcentagem) {
        try {
            service.NovoLancamentoCustoVariavel(idCardapio, nome, valor, porcentagem);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void CalcularMarkup(Long idCardapio) {
        try {
            service.CalcularMarkup(idCardapio);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void EditarCustoVariavel(Long idCusto,
                                    String nome,
                                    Double valor,
                                    Double porcentagem) {
        try {
            service.EditarCustoVariavel(idCusto, nome, valor, porcentagem);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void EditarCustoFixo(Long idCusto,
                                String nome,
                                Double valor,
                                Double porcentagem) {
        try {
            service.EditarCustoFixo(idCusto, nome, valor, porcentagem);
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public ResponseEntity<RelatorioDiarioDTO> BuscarRelatorioDiario()
    {
        try
        {
            RelatorioDiarioDTO response = financeiroMsService.BuscarRelatorioDiario().getBody();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<RelatorioMensalDTO> BuscarRelatorioMensal()
    {
        try
        {
            RelatorioMensalDTO response = financeiroMsService.BuscarRelatorioMensal().getBody();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<RelatorioAnualDTO> BuscarRelatorioAnual()
    {
        try
        {
            RelatorioAnualDTO response = financeiroMsService.BuscarRelatorioAnual().getBody();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //débitos
    public void NovoBoleto(String empresa,
                           String cnpj,
                           LocalDate dataVencimento,
                           Double parcelas,
                           Double valorTotal)
    {
        try
        {
            if(parcelas < 0){throw new IllegalActionException("Valor Invalido");}
            if(valorTotal < 0){throw new IllegalActionException("Valor Invalido");}
            if(empresa != null &&
            cnpj != null &&
            dataVencimento != null &&
            parcelas != null &&
            valorTotal != null)
            {
                Double parcelaAtual = 0.0;
                for(int i=0; i < parcelas; i++)
                {
                    parcelaAtual ++;
                    Double valorParcela = valorTotal/parcelas;
                    BoletosRequestDTO boletoRequest = new BoletosRequestDTO(empresa,
                                                                            cnpj,
                                                                            dataVencimento,
                                                                            parcelas,
                                                                            parcelaAtual,
                                                                            valorTotal);
                    financeiroMsService.NovoLancamentoDebito(boletoRequest);
                }
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }


    public void NovoPagamentoDebito(Long idBoleto,
                                    FORMAPAGAMENTO formapagamento,
                                    Double parcelas,
                                    Double descontoPagamentoAntecipado)
    {
        try
        {
            if(parcelas < 0){throw new IllegalActionException("Valor Invalido");}
            if(descontoPagamentoAntecipado < 0){throw new IllegalActionException("Valor Invalido");}
            if(idBoleto != null &&
            formapagamento != null &&
            parcelas != null &&
            descontoPagamentoAntecipado != null)
            {
                financeiroMsService.NovoPagamentoDebito(idBoleto, formapagamento, parcelas, descontoPagamentoAntecipado);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }

    }

}
