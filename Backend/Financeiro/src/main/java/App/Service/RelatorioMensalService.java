package App.Service;

import App.Dto.Request.BoletosRequestDTO;
import App.Dto.Request.ItemVendaRequestDTO;
import App.Dto.Request.VendaRequestFinalziadaDTO;
import App.Dto.Response.BoletosResponseDTO;
import App.Dto.Response.ItemVendaResponseDTO;
import App.Dto.Response.VendaResponseFinalizadaDTO;
import App.Entity.*;
import App.Enum.FORMAPAGAMENTO;
import App.Enum.StatusPagamento;
import App.Exceptions.EntityNotFoundException;

import App.Dto.*;
import App.Exceptions.IllegalActionException;
import App.Repositry.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class RelatorioMensalService {

    private final VendasRepository vendasRepository;
    private final VendasRealizadaRepository vendasRealizadaRepository;
    private final DebitosRepository debitosRepository;
    private final RelatorioMensalRepository relatorioMensalRepository;
    private final BoletoRepository boletoRepository;
    private final ItemVendaEntityRepository itemVendaEntityRepository;
    private final PagamentoService pagamentoService;
    Locale localBrasil = new Locale("pt", "BR");

    public RelatorioMensalService(VendasRepository vendasRepository, VendasRealizadaRepository vendasRealizadaRepository, DebitosRepository debitosRepository, RelatorioMensalRepository relatorioMensalRepository, BoletoRepository boletoRepository, ItemVendaEntityRepository itemVendaEntityRepository, PagamentoService pagamentoService) {
        this.vendasRepository = vendasRepository;
        this.vendasRealizadaRepository = vendasRealizadaRepository;
        this.debitosRepository = debitosRepository;
        this.relatorioMensalRepository = relatorioMensalRepository;
        this.boletoRepository = boletoRepository;
        this.itemVendaEntityRepository = itemVendaEntityRepository;
        this.pagamentoService = pagamentoService;
    }

    //NumberFormat.getCurrencyInstance(localBrasil).format()

    public ResponseEntity<List<RelatorioMensalEntity>> ListarRelatorios()
    {
        return new ResponseEntity<>(relatorioMensalRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<RelatorioDiarioDTO> BuscarRelatorioDiario()
    {
        try
        {
            int mes = LocalDate.now().getMonth().getValue();
            int ano = LocalDate.now().getYear();
            String dataReferencia = mes+"/"+ano;
            RelatorioMensalEntity entity = relatorioMensalRepository.findBydataReferencia(dataReferencia).orElseThrow(
                    ()-> new EntityNotFoundException()
            );
            VendasEntity vendas = vendasRepository.findById(entity.getVendas().getId()).orElseThrow(
                ()-> new EntityNotFoundException()
            );
            DebitosEntity debitos = debitosRepository.findById(entity.getDebitos().getId()).orElseThrow(
                    ()-> new EntityNotFoundException()
            );
            List<VendaResponseFinalizadaDTO> vendaResponseFinalizadaDTOS = new ArrayList<>();
            List<BoletosResponseDTO> boletosResponseDTOS = new ArrayList<>();
            Double valorTotalBoletos = 0.0;
            Double valorTotalVendaPix = 0.0;
            Double valorTotalVendaDinheiro = 0.0;
            Double valorTotalVendaDebito = 0.0;
            Double valorTotalVendaCredito = 0.0;
            Double valorTotalVendasFinalizadas = 0.0;
            for(VendasRealizdasEntity vendasRealizdas : vendas.getVendasFinalizadas())
            {
                if(vendasRealizdas.getDataVenda().getDayOfMonth() == LocalDate.now().getDayOfMonth())
                {
                    List<ItemVendaResponseDTO> itemVendaResponseDTOS = new ArrayList<>();
                    for(ItemVendaEntity itemVenda : vendasRealizdas.getItems())
                    {
                        ItemVendaResponseDTO itemVendaResponseDTO = new ItemVendaResponseDTO(itemVenda.getNome(),
                                itemVenda.getDescricao(),
                                itemVenda.getQuantidade(),
                                NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorUnitario()),
                                NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorTotal()));
                        itemVendaResponseDTOS.add(itemVendaResponseDTO);
                    }
                    if(vendasRealizdas.getFormapagamento() == FORMAPAGAMENTO.CREDITO)
                    {
                        valorTotalVendaCredito += vendasRealizdas.getValorTotal();
                    }
                    if(vendasRealizdas.getFormapagamento() == FORMAPAGAMENTO.DEBITO)
                    {
                        valorTotalVendaDebito += vendasRealizdas.getValorTotal();
                    }
                    if(vendasRealizdas.getFormapagamento() == FORMAPAGAMENTO.DINHEIRO)
                    {
                        valorTotalVendaDinheiro += vendasRealizdas.getValorTotal();
                    }
                    if(vendasRealizdas.getFormapagamento() == FORMAPAGAMENTO.PIX)
                    {
                        valorTotalVendaPix += vendasRealizdas.getValorTotal();
                    }
                    VendaResponseFinalizadaDTO vendaResponseFinalizadaDTO = new VendaResponseFinalizadaDTO(vendasRealizdas.getId(),
                            vendasRealizdas.getNomeCLiente(),
                            vendasRealizdas.getCodigo(),
                            vendasRealizdas.getDataVenda(),
                            itemVendaResponseDTOS,
                            vendasRealizdas.getParcelas(),
                            NumberFormat.getCurrencyInstance(localBrasil).format(vendasRealizdas.getValorTotal()/vendasRealizdas.getParcelas()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(vendasRealizdas.getValorTotal()),
                            vendasRealizdas.getFormapagamento(),
                            vendasRealizdas.getDataPagamento(),
                            vendasRealizdas.getTimeStamp()
                            );
                    vendaResponseFinalizadaDTOS.add(vendaResponseFinalizadaDTO);
                }
            }
            for(BoletoEntity boleto : debitos.getBoletos())
            {
                if(boleto.getDataVencimento().getDayOfMonth() == LocalDate.now().getDayOfMonth())
                {

                    for(BoletoEntity boletoInterno : debitos.getBoletos())
                    {
                        valorTotalBoletos += boleto.getValorParcela();
                        BoletosResponseDTO boletosResponseDTO = new BoletosResponseDTO(boletoInterno.getId(),
                                boletoInterno.getEmpresa(),
                                boletoInterno.getCnpj(),
                                boletoInterno.getStatusPagamento(),
                                boletoInterno.getDataVencimento(),
                                boletoInterno.getParcelas(),
                                boletoInterno.getParcelaAtual(),
                                NumberFormat.getCurrencyInstance(localBrasil).format(boleto.getValorTotal()),
                                NumberFormat.getCurrencyInstance(localBrasil).format(boleto.getValorParcela()));
                        boletosResponseDTOS.add(boletosResponseDTO);
                    }
                }
            }
            valorTotalVendasFinalizadas = valorTotalVendaDebito + valorTotalVendaCredito + valorTotalVendaDinheiro + valorTotalVendaPix;
            RelatorioDiarioDTO response = new RelatorioDiarioDTO(LocalDate.now().getDayOfMonth(),
                    entity.getDataReferencia(),
                    vendaResponseFinalizadaDTOS,
                    NumberFormat.getCurrencyInstance(localBrasil).format(valorTotalVendaDebito),
                    NumberFormat.getCurrencyInstance(localBrasil).format(valorTotalVendaCredito),
                    NumberFormat.getCurrencyInstance(localBrasil).format(valorTotalVendaDinheiro),
                    NumberFormat.getCurrencyInstance(localBrasil).format(valorTotalVendaPix),
                    NumberFormat.getCurrencyInstance(localBrasil).format(valorTotalVendasFinalizadas),
                    NumberFormat.getCurrencyInstance(localBrasil).format(valorTotalBoletos),
                    boletosResponseDTOS);
            return new ResponseEntity<>(response, HttpStatus.OK);
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
            int mes = LocalDate.now().getMonth().getValue();
            int ano = LocalDate.now().getYear();
            String dataReferencia = mes+"/"+ano;
            RelatorioMensalEntity entity = relatorioMensalRepository.findBydataReferencia(dataReferencia).orElseThrow(
                    ()-> new EntityNotFoundException()
            );
            List<VendaResponseFinalizadaDTO> vendaResponseFinalizadaDTOS = new ArrayList<>();
            List<BoletosResponseDTO> boletosResponseDTOS = new ArrayList<>();
            VendasEntity venda = vendasRepository.findById(entity.getVendas().getId()).orElseThrow(
                    ()-> new EntityNotFoundException()
            );
            for(VendasRealizdasEntity vendasRealizdas : venda.getVendasFinalizadas())
            {
                List<ItemVendaResponseDTO> itemDTOS = new ArrayList<>();
                for(ItemVendaEntity itemVenda : vendasRealizdas.getItems())
                {
                    ItemVendaResponseDTO itemDTO = new ItemVendaResponseDTO(itemVenda.getNome(),
                            itemVenda.getDescricao(),
                            itemVenda.getQuantidade(),
                            NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorUnitario()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorTotal())
                    );
                    itemDTOS.add(itemDTO);
                    VendaResponseFinalizadaDTO vendaResponseFinalizadaDTO = new VendaResponseFinalizadaDTO(vendasRealizdas.getId(),
                            vendasRealizdas.getNomeCLiente(),
                            vendasRealizdas.getCodigo(),
                            vendasRealizdas.getDataVenda(),
                            itemDTOS,
                            vendasRealizdas.getParcelas(),
                            NumberFormat.getCurrencyInstance(localBrasil).format(vendasRealizdas.getValorTotal()/vendasRealizdas.getParcelas()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(vendasRealizdas.getValorTotal()),
                            vendasRealizdas.getFormapagamento(),
                            vendasRealizdas.getDataPagamento(),
                            vendasRealizdas.getTimeStamp());
                    vendaResponseFinalizadaDTOS.add(vendaResponseFinalizadaDTO);
                }
            }
            //setar vendaDTO
            DebitosEntity debitos = debitosRepository.findById(entity.getDebitos().getId()).orElseThrow(
                    ()-> new EntityNotFoundException()
            );
            for(BoletoEntity boleto : debitos.getBoletos())
            {
                BoletosResponseDTO boletosResponseDTO = new BoletosResponseDTO(boleto.getId(),
                        boleto.getEmpresa(),
                        boleto.getCnpj(),
                        boleto.getStatusPagamento(),
                        boleto.getDataVencimento(),
                        boleto.getParcelas(),
                        boleto.getParcelaAtual(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(boleto.getValorTotal()),
                        NumberFormat.getCurrencyInstance(localBrasil).format(boleto.getValorParcela()));
                boletosResponseDTOS.add(boletosResponseDTO);
            }
            RelatorioMensalDTO response = new RelatorioMensalDTO(entity.getDataReferencia(),
                    vendaResponseFinalizadaDTOS,
                    NumberFormat.getCurrencyInstance(localBrasil).format(venda.getTotalVendasDebito()),
                    NumberFormat.getCurrencyInstance(localBrasil).format(venda.getTotalVendasCredito()),
                    NumberFormat.getCurrencyInstance(localBrasil).format(venda.getTotalVendasDinheiro()),
                    NumberFormat.getCurrencyInstance(localBrasil).format(venda.getTotalVendasPix()),
                    NumberFormat.getCurrencyInstance(localBrasil).format(venda.getTotalVendasFinalzadas()),
                    NumberFormat.getCurrencyInstance(localBrasil).format(debitos.getValorTotalBoletos()),
                    boletosResponseDTOS
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
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
            List<RelatorioMensalEntity> relatorioMensalEntities = relatorioMensalRepository.findAll();
            for(RelatorioMensalEntity entity : relatorioMensalEntities)
            {
                if(entity.getAnoReferencia() == LocalDate.now().getYear())
                {
                    List<VendaResponseFinalizadaDTO> vendaResponseFinalizadaDTOS = new ArrayList<>();
                    List<BoletosResponseDTO> boletosResponseDTOS = new ArrayList<>();
                    VendasEntity venda = vendasRepository.findById(entity.getVendas().getId()).orElseThrow(
                            ()-> new EntityNotFoundException()
                    );
                    for(VendasRealizdasEntity vendasRealizdas : venda.getVendasFinalizadas())
                    {
                        List<ItemVendaResponseDTO> itemDTOS = new ArrayList<>();
                        for(ItemVendaEntity itemVenda : vendasRealizdas.getItems())
                        {
                            ItemVendaResponseDTO itemDTO = new ItemVendaResponseDTO(itemVenda.getNome(),
                                    itemVenda.getDescricao(),
                                    itemVenda.getQuantidade(),
                                    NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorUnitario()),
                                    NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorTotal())
                            );
                            itemDTOS.add(itemDTO);
                            VendaResponseFinalizadaDTO vendaResponseFinalizadaDTO = new VendaResponseFinalizadaDTO(vendasRealizdas.getId(),
                                    vendasRealizdas.getNomeCLiente(),
                                    vendasRealizdas.getCodigo(),
                                    vendasRealizdas.getDataVenda(),
                                    itemDTOS,
                                    vendasRealizdas.getParcelas(),
                                    NumberFormat.getCurrencyInstance(localBrasil).format(vendasRealizdas.getValorTotal()/vendasRealizdas.getParcelas()),
                                    NumberFormat.getCurrencyInstance(localBrasil).format(vendasRealizdas.getValorTotal()),
                                    vendasRealizdas.getFormapagamento(),
                                    vendasRealizdas.getDataPagamento(),
                                    vendasRealizdas.getTimeStamp());
                            vendaResponseFinalizadaDTOS.add(vendaResponseFinalizadaDTO);
                        }
                    }
                    //setar vendaDTO
                    DebitosEntity debitos = debitosRepository.findById(entity.getDebitos().getId()).orElseThrow(
                            ()-> new EntityNotFoundException()
                    );
                    for(BoletoEntity boleto : debitos.getBoletos())
                    {
                        BoletosResponseDTO boletosResponseDTO = new BoletosResponseDTO(boleto.getId(),
                                boleto.getEmpresa(),
                                boleto.getCnpj(),
                                boleto.getStatusPagamento(),
                                boleto.getDataVencimento(),
                                boleto.getParcelas(),
                                boleto.getParcelaAtual(),
                                NumberFormat.getCurrencyInstance(localBrasil).format(boleto.getValorTotal()),
                                NumberFormat.getCurrencyInstance(localBrasil).format(boleto.getValorParcela()));
                        boletosResponseDTOS.add(boletosResponseDTO);
                    }
                    RelatorioAnualDTO response = new RelatorioAnualDTO(entity.getAnoReferencia(),
                            vendaResponseFinalizadaDTOS,
                            NumberFormat.getCurrencyInstance(localBrasil).format(venda.getTotalVendasDebito()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(venda.getTotalVendasCredito()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(venda.getTotalVendasDinheiro()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(venda.getTotalVendasPix()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(venda.getTotalVendasFinalzadas()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(debitos.getValorTotalBoletos()),
                            boletosResponseDTOS
                                    );
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public void NovoLancamentoVendasRealizadas(VendaRequestFinalziadaDTO dto)
    {
        try
        {
            if(dto != null) {
                int mes = LocalDate.now().getMonth().getValue();
                int ano = LocalDate.now().getYear();
                VendasRealizdasEntity vendasRealizdas = GeraVendaRealizada(dto);
                RelatorioMensalEntity entity = VerificaRelatorioMensal(mes, ano);
                VendasEntity vendas = vendasRepository.findById(entity.getVendas().getId()).orElseThrow(
                        () -> new EntityNotFoundException()
                );
                if (vendasRealizdas.getFormapagamento() == FORMAPAGAMENTO.CREDITO) {
                    vendas.setTotalVendasCredito(vendas.getTotalVendasCredito() + vendasRealizdas.getValorTotal());
                }
                if (vendasRealizdas.getFormapagamento() == FORMAPAGAMENTO.DEBITO) {
                    vendas.setTotalVendasDebito(vendas.getTotalVendasDebito() + vendasRealizdas.getValorTotal());
                }
                if (vendasRealizdas.getFormapagamento() == FORMAPAGAMENTO.DINHEIRO) {
                    vendas.setTotalVendasDinheiro(vendas.getTotalVendasDinheiro() + vendasRealizdas.getValorTotal());
                }
                if (vendasRealizdas.getFormapagamento() == FORMAPAGAMENTO.PIX) {
                    vendas.setTotalVendasPix(vendas.getTotalVendasPix() + vendasRealizdas.getValorTotal());
                }
                if (vendas.getVendasFinalizadas() == null) {
                    vendas.setVendasFinalizadas(Collections.singletonList(vendasRealizdas));
                } else {
                    vendas.getVendasFinalizadas().add(vendasRealizdas);
                }
                vendas.setTotalVendasFinalzadas(vendas.getTotalVendasCredito() +
                        vendas.getTotalVendasDebito() +
                        vendas.getTotalVendasPix() +
                        vendas.getTotalVendasDinheiro());
                vendasRepository.save(vendas);
                entity.setTimeStamp(LocalDateTime.now());
                relatorioMensalRepository.save(entity);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
            e.getStackTrace();
        }
    }

    public void NovoLancamentoDebito(BoletosRequestDTO dto)
    {
        try
        {
            if(dto != null)
            {
                BoletoEntity boleto = GeraBoleto(dto);
            }
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
            if(parcelas < 0){throw new IllegalActionException("valor invalido");}
            if(descontoPagamentoAntecipado < 0){throw new IllegalActionException("valor invalido");}
            if(idBoleto != null && formapagamento != null && parcelas != null && descontoPagamentoAntecipado != null)
            {
                int mes = LocalDate.now().getMonth().getValue();
                int ano = LocalDate.now().getYear();
                RelatorioMensalEntity relatorioMensal = VerificaRelatorioMensal(mes,ano);
                if(relatorioMensal.getId() != null)
                {
                    DebitosEntity debitos = debitosRepository.findById(relatorioMensal.getDebitos().getId()).orElseThrow(
                            () -> new EntityNotFoundException()
                    );
                    BoletoEntity boleto = boletoRepository.findById(idBoleto).orElseThrow(
                            ()->new EntityNotFoundException()
                    );
                    if(boleto.getStatusPagamento() == StatusPagamento.PAGO){throw new IllegalActionException("Boleto já foi pago");}
                    //chamar ms_Pagamento

                    if(descontoPagamentoAntecipado > 0 && descontoPagamentoAntecipado < 101)
                    {
                        Double desconto =  descontoPagamentoAntecipado /100;
                        Double novoValor = (desconto * boleto.getValorParcela())- boleto.getValorParcela();
                        PagamentoResponseDTO pagamentoResponseDTO = pagamentoService.NovoPagamento(formapagamento,
                                parcelas,
                                novoValor,
                                descontoPagamentoAntecipado,
                                boleto.getValorTotal()).getBody();
                        if(pagamentoResponseDTO.pago() == Boolean.TRUE)
                        {
                            boleto.setStatusPagamento(StatusPagamento.PAGO);
                            boleto.setDataPagamento(LocalDate.now());
                            boletoRepository.save(boleto);
                            debitos.setValorTotalBoletos(debitos.getValorTotalBoletos() - boleto.getValorParcela());
                            debitosRepository.save(debitos);
                        }
                        else
                        {throw new IllegalActionException("Pagamento não autorizado");}
                    }
                    else
                    {
                        PagamentoResponseDTO pagamentoResponseDTO = pagamentoService.NovoPagamento(formapagamento,
                                parcelas,
                                boleto.getValorParcela(),
                                descontoPagamentoAntecipado,
                                boleto.getValorTotal()).getBody();
                        if(pagamentoResponseDTO.pago() == Boolean.TRUE)
                        {
                            boleto.setStatusPagamento(StatusPagamento.PAGO);
                            boleto.setDataPagamento(LocalDate.now());
                            boletoRepository.save(boleto);
                            debitos.setValorTotalBoletos(debitos.getValorTotalBoletos() - boleto.getValorParcela());
                            debitosRepository.save(debitos);
                        }
                        else
                        {throw new IllegalActionException("Pagamento não autorizado");}
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }


    public RelatorioMensalEntity VerificaRelatorioMensal(int mesReferencia,
                                                         int anoReferencia)
    {
        try
        {
            if(mesReferencia < 0 && mesReferencia > 12){throw new IllegalActionException("ação não permitida");}
            if(anoReferencia < 0){throw new IllegalActionException("ação não permitida");}
            if(!relatorioMensalRepository.existsBydataReferencia(mesReferencia+"/"+anoReferencia))
            {
                RelatorioMensalEntity relatorioMensal = new RelatorioMensalEntity();
                relatorioMensal.setTimeStamp(LocalDateTime.now());
                relatorioMensal.setDataReferencia(mesReferencia+"/"+anoReferencia);
                relatorioMensal.setAnoReferencia(anoReferencia);
                VendasEntity vendas = new VendasEntity();
                vendas.setTimeStamp(LocalDateTime.now());
                vendas.setTotalVendasDinheiro(0.0);
                vendas.setTotalVendasCredito(0.0);
                vendas.setTotalVendasDebito(0.0);
                vendas.setTotalVendasPix(0.0);
                vendas.setTotalVendasFinalzadas(0.0);
                DebitosEntity debitos = new DebitosEntity();
                debitos.setTimeStamp(LocalDateTime.now());
                debitos.setValorTotalBoletos(0.0);
                vendasRepository.save(vendas);
                relatorioMensal.setVendas(vendas);
                relatorioMensal.setDebitos(debitos);
                debitosRepository.save(debitos);
                relatorioMensalRepository.save(relatorioMensal);
                return relatorioMensal;
            }
            else
            {
                RelatorioMensalEntity entity = relatorioMensalRepository.findBydataReferencia(mesReferencia+"/"+anoReferencia).get();
                return entity;
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public BoletoEntity GeraBoleto(BoletosRequestDTO dto)
    {
        try
        {
            if(dto != null)
            {
                int mes = LocalDate.now().getMonth().getValue();
                int ano = LocalDate.now().getYear();
                RelatorioMensalEntity relatorioMensal = VerificaRelatorioMensal(mes, ano);
                DebitosEntity debitos = debitosRepository.findById(relatorioMensal.getDebitos().getId()).orElseThrow(
                        () -> new EntityNotFoundException()
                );
                BoletoEntity boleto = new BoletoEntity();
                boleto.setStatusPagamento(StatusPagamento.AGUARDANDO);
                boleto.setEmpresa(dto.empresa());
                boleto.setCnpj(dto.cnpj());
                boleto.setParcelas(dto.parcelas());
                boleto.setValorParcela(dto.valorTotal() / dto.parcelas());
                boleto.setValorTotal(dto.valorTotal());
                boleto.setParcelaAtual(dto.parcelasAtual());
                boleto.setTimeStamp(LocalDateTime.now());
                boleto.setDataVencimento(dto.dataVencimento());
                boletoRepository.save(boleto);

                if (debitos.getBoletos() == null)
                {
                    debitos.setBoletos(Collections.singletonList(boleto));
                }
                else
                {
                    debitos.getBoletos().add(boleto);
                }
                debitos.setValorTotalBoletos(debitos.getValorTotalBoletos() + boleto.getValorParcela());
                debitosRepository.save(debitos);
                relatorioMensalRepository.save(relatorioMensal);
                return boleto;
                }
            }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public VendasRealizdasEntity GeraVendaRealizada(VendaRequestFinalziadaDTO dto)
    {
        try
        {
            if(dto != null)
            {
                int mes = LocalDate.now().getMonth().getValue();
                int ano = LocalDate.now().getYear();
                RelatorioMensalEntity relatorioMensal = VerificaRelatorioMensal(mes, ano);
                VendasEntity vendas = vendasRepository.findById(relatorioMensal.getVendas().getId()).orElseThrow(
                        () -> new EntityNotFoundException()
                );
                VendasRealizdasEntity vendasRealizdasEntity = new VendasRealizdasEntity();
                vendasRealizdasEntity.setTimeStamp(LocalDateTime.now());
                List<ItemVendaEntity> itemVenda = new ArrayList<>();
                for (ItemVendaRequestDTO itemDto : dto.itens())
                {
                    ItemVendaEntity itemVendaInterno = new ItemVendaEntity(itemDto, LocalDateTime.now());
                    itemVendaEntityRepository.save(itemVendaInterno);
                    itemVenda.add(itemVendaInterno);
                }
                vendasRealizdasEntity.setItems(itemVenda);
                vendasRealizdasEntity.setValorTotal(dto.valorTotal());
                vendasRealizdasEntity.setFormapagamento(dto.formapagamento());
                vendasRealizdasEntity.setCodigo(dto.codigo());
                vendasRealizdasEntity.setDataVenda(dto.dataVenda());
                vendasRealizdasEntity.setNomeCLiente(dto.nomeCLiente());
                vendasRealizdasEntity.setStatusPagamento(StatusPagamento.PAGO);
                vendasRealizdasEntity.setDataPagamento(dto.dataPagamento());
                vendasRealizdasEntity.setParcelas(dto.parcelas());
                vendasRealizdasEntity.setTimeStamp(LocalDateTime.now());
                vendasRealizadaRepository.save(vendasRealizdasEntity);
                System.out.println("venda salva");
                return vendasRealizdasEntity;
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }


}
