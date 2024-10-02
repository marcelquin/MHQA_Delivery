package APP.Service;

import APP.Dto.*;
import APP.Entity.ItemVendaEntity;
import APP.Entity.VendaEntity;
import APP.Enum.FORMAPAGAMENTO;
import APP.Enum.STATUSVENDA;
import APP.Exceptions.EntityNotFoundException;
import APP.Exceptions.IllegalActionException;
import APP.Exceptions.NullargumentsException;
import APP.Repository.ItemVendaRepository;
import APP.Repository.VendaRepository;
import APP.ServiceMs.FinanceiroMsService;
import APP.ServiceMs.ItemCardapioMsService;
import APP.ServiceMs.LogisticaMsService;
import APP.ServiceMs.PagamentoMsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class VendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final VendaRepository vendaRepository;
    private final ClienteService clienteService;
    private final ItemCardapioMsService itemCardapioService;
    private final PagamentoMsService pagamentoMsService;
    private final FinanceiroMsService financeiroMsService;
    private final LogisticaMsService logisticaMsService;

    public VendaService(ItemVendaRepository itemVendaRepository, VendaRepository vendaRepository, ClienteService clienteService, ItemCardapioMsService itemCardapioService, PagamentoMsService pagamentoMsService, FinanceiroMsService financeiroMsService, LogisticaMsService logisticaMsService) {
        this.itemVendaRepository = itemVendaRepository;
        this.vendaRepository = vendaRepository;
        this.clienteService = clienteService;
        this.itemCardapioService = itemCardapioService;
        this.pagamentoMsService = pagamentoMsService;
        this.financeiroMsService = financeiroMsService;
        this.logisticaMsService = logisticaMsService;
    }

    Locale localBrasil = new Locale("pt", "BR");

    public ResponseEntity<List<VendaResponseDTO>> ListarVendas()
    {
        try
        {
            List<VendaEntity> entities = vendaRepository.findAll();
            List<VendaResponseDTO> response = new ArrayList<>();
            for(VendaEntity entity : entities)
            {
                List<ItemVendaDTO> itemVendaDTOS = new ArrayList<>();
                for(ItemVendaEntity itemVenda : entity.getItemVenda())
                {
                    ItemVendaDTO itemVendaDTO = new ItemVendaDTO(itemVenda.getNome(),
                            itemVenda.getDescricao(),
                            itemVenda.getQuantidade(),
                            NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorUnitarioItemVenda()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorTotalItemVenda()),
                            itemVenda.getTimeStamp());
                    itemVendaDTOS.add(itemVendaDTO);
                }
                VendaResponseDTO dto = new VendaResponseDTO(entity.getId(),
                        entity.getNomeCliente(),
                        entity.getCodigo(),
                        entity.getDataVenda(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()),
                        entity.getStatusvenda(),
                        itemVendaDTOS,
                        entity.getTimeStamp()
                        );
                response.add(dto);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<VendaResponseDTO>> ListarVendasEmAberto()
    {
        try
        {
            List<VendaEntity> vendaEntityList = vendaRepository.findAll();
            List<VendaResponseDTO> response = new ArrayList<>();

            for(VendaEntity venda : vendaEntityList)
            {
                if(venda.getStatusvenda() != STATUSVENDA.FINALIZADO)
                {
                    List<ItemVendaDTO> itemVendaDTOS = new ArrayList<>();
                    for(ItemVendaEntity itemVenda : venda.getItemVenda())
                    {
                        ItemVendaDTO itemVendaDTO = new ItemVendaDTO(itemVenda.getNome(),
                                itemVenda.getDescricao(),
                                itemVenda.getQuantidade(),
                                NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorUnitarioItemVenda()),
                                NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorTotalItemVenda()),
                                itemVenda.getTimeStamp());
                        itemVendaDTOS.add(itemVendaDTO);
                    }
                    VendaResponseDTO dto = new VendaResponseDTO(venda.getId(),
                            venda.getNomeCliente(),
                            venda.getCodigo(),
                            venda.getDataVenda(),
                            NumberFormat.getCurrencyInstance(localBrasil).format(venda.getValor()),
                            venda.getStatusvenda(),
                            itemVendaDTOS,
                            venda.getTimeStamp()
                    );
                    response.add(dto);
                }
            }
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<VendaDTO> BuscarVendaPorId(Long id)
    {
        try
        {
            if(id != null)
            {
                VendaEntity entity = vendaRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                List<ItemVendaDTO> itemVendaDTOS = new ArrayList<>();
                for(ItemVendaEntity itemVenda : entity.getItemVenda())
                {
                    ItemVendaDTO itemVendaDTO = new ItemVendaDTO(itemVenda.getNome(),
                            itemVenda.getDescricao(),
                            itemVenda.getQuantidade(),
                            NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorUnitarioItemVenda()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorTotalItemVenda()),
                            itemVenda.getTimeStamp());
                    itemVendaDTOS.add(itemVendaDTO);
                }
                VendaDTO response = new VendaDTO(entity.getNomeCliente(),
                        entity.getCodigo(),
                        entity.getDataVenda(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()),
                        entity.getStatusvenda(),
                        itemVendaDTOS,
                        entity.getTimeStamp()
                        );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else
            { throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<VendaDTO> NovaVenda(Long idCliente,
                                              String nomeCliente)
    {
        try
        {
            int cod1 = (int) (111 + Math.random() * 999);
            int cod2 = (int) (11111 + Math.random() * 99999);
            VendaEntity entity = new VendaEntity();
            if(idCliente != null)
            {
                ClienteResponseDTO clienteResponse = clienteService.BuscarClientesPorId(idCliente).getBody();
                entity.setNomeCliente(clienteResponse.nome()+" "+clienteResponse.sobrenome());
            }
            if(nomeCliente != null)
            {
                entity.setNomeCliente(nomeCliente);
            }
            entity.setNomeCliente("Consumidor");
            if(nomeCliente == null && idCliente == null)
            {
                entity.setNomeCliente("Consumidor");
            }
            entity.setTimeStamp(LocalDateTime.now());
            entity.setCodigo(cod1+"."+cod2);
            entity.setDataVenda(LocalDateTime.now());
            entity.setValor(0.0);
            entity.setStatusvenda(STATUSVENDA.AGUARDANDO);
            System.out.println("Cliente: "+entity.getNomeCliente());
            vendaRepository.save(entity);
            List<ItemVendaDTO> itemVendaDTOS = new ArrayList<>();
            for(ItemVendaEntity itemVenda : entity.getItemVenda())
            {
                ItemVendaDTO itemVendaDTO = new ItemVendaDTO(itemVenda.getNome(),
                        itemVenda.getDescricao(),
                        itemVenda.getQuantidade(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorUnitarioItemVenda()),
                        NumberFormat.getCurrencyInstance(localBrasil).format(itemVenda.getValorTotalItemVenda()),
                        itemVenda.getTimeStamp());
                itemVendaDTOS.add(itemVendaDTO);
            }
            VendaDTO response = new VendaDTO(entity.getNomeCliente(),
                    entity.getCodigo(),
                    entity.getDataVenda(),
                    NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()),
                    entity.getStatusvenda(),
                    itemVendaDTOS,
                    entity.getTimeStamp()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<VendaDTO> AdicionarItemVenda(Long idVenda,
                                                       Long idProduto,
                                                       Double quantidade)
    {
        try
        {
            if(quantidade <= 0){throw new IllegalActionException("valor invalido");}
            if(idProduto != null &&
               idVenda != null &&
               quantidade != null)
            {
                VendaEntity entity = vendaRepository.findById(idVenda).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                //busca micro serviço
                ItemCardapioDTO produto = itemCardapioService.BuscarItemCardapioPorId(idProduto).getBody();
                System.out.println("produto: "+produto.nome());
                ItemVendaEntity itemVenda = new ItemVendaEntity();
                itemVenda.setNome(produto.nome());
                itemVenda.setDescricao(produto.descricao());
                itemVenda.setQuantidade(quantidade);
                itemVenda.setValorUnitarioItemVenda(produto.valor());
                itemVenda.setValorTotalItemVenda(produto.valor() * quantidade);
                itemVenda.setTimeStamp(LocalDateTime.now());
                entity.setValor(entity.getValor()+itemVenda.getValorTotalItemVenda());
                entity.setTimeStamp(LocalDateTime.now());
                itemVendaRepository.save(itemVenda);
                entity.getItemVenda().add(itemVenda);
                System.out.println("Venda codigo: "+entity.getCodigo());
                System.out.println("Item venda: "+itemVenda.getNome());
                System.out.println("Valor venda: "+entity.getValor());
                vendaRepository.save(entity);
                List<ItemVendaDTO> itemVendaDTOS = new ArrayList<>();
                for(ItemVendaEntity itemVendainterno : entity.getItemVenda())
                {
                    ItemVendaDTO itemVendaDTO = new ItemVendaDTO(itemVendainterno.getNome(),
                            itemVenda.getDescricao(),
                            itemVendainterno.getQuantidade(),
                            NumberFormat.getCurrencyInstance(localBrasil).format(itemVendainterno.getValorUnitarioItemVenda()),
                            NumberFormat.getCurrencyInstance(localBrasil).format(itemVendainterno.getValorTotalItemVenda()),
                            itemVendainterno.getTimeStamp());
                    itemVendaDTOS.add(itemVendaDTO);
                }
                VendaDTO response = new VendaDTO(entity.getNomeCliente(),
                        entity.getCodigo(),
                        entity.getDataVenda(),
                        NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()),
                        entity.getStatusvenda(),
                        itemVendaDTOS,
                        entity.getTimeStamp()
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else
            {throw  new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<VendaDTO> FinalizarVenda(Long idVenda,
                                                   FORMAPAGAMENTO formapagamento,
                                                   Double parcelas,
                                                   Double desconto,
                                                   Double valorPago,
                                                   Boolean entrega,
                                                   String telefone,
                                                   String enderecoEntrega)
    {
        try
        {
            if(parcelas < 0){throw  new IllegalActionException("Valor Invalido");}
            if(desconto < 0){throw  new IllegalActionException("Valor Invalido");}
            if(valorPago < 0){throw  new IllegalActionException("Valor Invalido");}
            if(idVenda != null)
            {
                VendaEntity entity = vendaRepository.findById(idVenda).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                //micro serviço pagamento
                Double valorPagoInterno = entity.getValor();
                Double valorVenda = entity.getValor();
                Double descontoVenda = 0.0;
                if(descontoVenda > 0)
                {
                    descontoVenda = desconto/100;
                    valorVenda = valorVenda + (descontoVenda * valorVenda);
                    valorPagoInterno = valorVenda;
                }
                if(valorVenda > 0 && formapagamento == FORMAPAGAMENTO.DINHEIRO)
                {
                    valorPagoInterno = valorPago;
                }
                PagamentoResponseDTO pagamentoResponse = pagamentoMsService.NovoPagamento(formapagamento,
                                                                                          parcelas,
                                                                                          valorPagoInterno,
                                                                                          descontoVenda,
                                                                                          valorVenda).getBody();
                //micro serviço financeiro
                if(pagamentoResponse.pago() == Boolean.TRUE)
                {
                    List<ItemVendaRequestDTO> itemVendaRequestDTOS = new ArrayList<>();
                    for(ItemVendaEntity itemVenda : entity.getItemVenda())
                    {
                        ItemVendaRequestDTO itemVendaRequestDTO = new ItemVendaRequestDTO(itemVenda.getNome(),
                                itemVenda.getDescricao(),
                                itemVenda.getQuantidade(),
                                itemVenda.getValorUnitarioItemVenda(),
                                itemVenda.getValorTotalItemVenda());
                        itemVendaRequestDTOS.add(itemVendaRequestDTO);
                    }
                    VendaRequestFinalziadaDTO vendaRequest = new VendaRequestFinalziadaDTO(entity.getNomeCliente(),
                            entity.getCodigo(),
                            entity.getDataVenda(),
                            itemVendaRequestDTOS,
                            pagamentoResponse.parcelas(),
                            pagamentoResponse.valorParcela(),
                            pagamentoResponse.valorTotal(),
                            formapagamento,
                            pagamentoResponse.dataPagamento());
                    financeiroMsService.NovoLancamentoVendasRealizadas(vendaRequest);
                    entity.setStatusvenda(STATUSVENDA.FINALIZADO);
                    entity.setTimeStamp(LocalDateTime.now());
                    if(entrega == true && enderecoEntrega != null && telefone != null)
                    {
                        List<ItemLogisticaDTO> itemLogisticaDTOS = new ArrayList<>();
                        for(ItemVendaEntity itemVenda : entity.getItemVenda())
                        {
                            ItemLogisticaDTO itemLogisticaDTO = new ItemLogisticaDTO(itemVenda.getNome(),
                                    itemVenda.getQuantidade(),
                                    itemVenda.getValorUnitarioItemVenda(),
                                    itemVenda.getValorTotalItemVenda());
                            itemLogisticaDTOS.add(itemLogisticaDTO);
                        }
                        LogisticaRequestDTO logisticaRequest = new LogisticaRequestDTO(entity.getNomeCliente(),
                                entity.getCodigo(),
                                enderecoEntrega,
                                telefone,
                                itemLogisticaDTOS);
                        logisticaMsService.NovolancamentoLogistica(logisticaRequest);
                    }
                    vendaRepository.save(entity);
                    List<ItemVendaDTO> itemVendaDTOS = new ArrayList<>();
                    for(ItemVendaEntity itemVendainterno : entity.getItemVenda())
                    {
                        ItemVendaDTO itemVendaDTO = new ItemVendaDTO(itemVendainterno.getNome(),
                                itemVendainterno.getDescricao(),
                                itemVendainterno.getQuantidade(),
                                NumberFormat.getCurrencyInstance(localBrasil).format(itemVendainterno.getValorUnitarioItemVenda()),
                                NumberFormat.getCurrencyInstance(localBrasil).format(itemVendainterno.getValorTotalItemVenda()),
                                itemVendainterno.getTimeStamp());
                        itemVendaDTOS.add(itemVendaDTO);
                    }
                    VendaDTO response = new VendaDTO(entity.getNomeCliente(),
                            entity.getCodigo(),
                            entity.getDataVenda(),
                            NumberFormat.getCurrencyInstance(localBrasil).format(entity.getValor()),
                            entity.getStatusvenda(),
                            itemVendaDTOS,
                            entity.getTimeStamp()
                    );
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

            }
            else
            { throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
