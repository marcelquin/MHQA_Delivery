package APP.Service;


import APP.DTO.ItemLogisticaDTO;
import APP.DTO.LogisticaDTO;
import APP.DTO.LogisticaRequestDTO;
import APP.Entity.ItemLogisticaEntity;
import APP.Entity.LogisticaEntity;
import APP.Enum.STATUSENTREGA;
import APP.Exceptions.EntityNotFoundException;
import APP.Exceptions.NullargumentsException;
import APP.Repository.ItemLogisticaRepository;
import APP.Repository.LogisticaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogisticaService {

    private final LogisticaRepository logisticaRepository;
    private final ItemLogisticaRepository itemLogisticaRepository;

    public LogisticaService(LogisticaRepository logisticaRepository, ItemLogisticaRepository itemLogisticaRepository) {
        this.logisticaRepository = logisticaRepository;
        this.itemLogisticaRepository = itemLogisticaRepository;
    }


    public ResponseEntity<List<LogisticaDTO>> ListarEntregas()
    {
        try
        {
            List<LogisticaDTO> response = new ArrayList<>();
            List<LogisticaEntity> resultado = logisticaRepository.findAll();
            for(LogisticaEntity entity : resultado)
            {
                List<ItemLogisticaDTO> itemLogisticaDTOS = new ArrayList<>();
                for(ItemLogisticaEntity itemLogistica : entity.getProdutos())
                {
                    ItemLogisticaDTO dto = new ItemLogisticaDTO(itemLogistica.getProduto(),
                            itemLogistica.getQuantidade(),
                            itemLogistica.getValorUnitario(),
                            itemLogistica.getValorTotal());
                    itemLogisticaDTOS.add(dto);
                }
                LogisticaDTO dto = new LogisticaDTO(entity.getId(),
                        entity.getNomeCliente(),
                        entity.getEnderecoEntrega(),
                        entity.getTelefoneContato(),
                        itemLogisticaDTOS,
                        entity.getStatusEntrega());
                response.add(dto);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<List<LogisticaDTO>> ListarEntregasEmAberto()
    {
        try
        {
            List<LogisticaDTO> response = new ArrayList<>();
            List<LogisticaEntity> resultado = logisticaRepository.findAll();
            for(LogisticaEntity entity: resultado)
            {
                if(entity.getStatusEntrega() != STATUSENTREGA.ENTREGUE &&
                   entity.getStatusEntrega() != STATUSENTREGA.CANCELADA )
                {
                    List<ItemLogisticaDTO> itemLogisticaDTOS = new ArrayList<>();
                    for(ItemLogisticaEntity itemLogistica : entity.getProdutos())
                    {
                        ItemLogisticaDTO dto = new ItemLogisticaDTO(itemLogistica.getProduto(),
                                itemLogistica.getQuantidade(),
                                itemLogistica.getValorUnitario(),
                                itemLogistica.getValorTotal());
                        itemLogisticaDTOS.add(dto);
                    }
                    LogisticaDTO dto = new LogisticaDTO(entity.getId(),
                            entity.getNomeCliente(),
                            entity.getEnderecoEntrega(),
                            entity.getTelefoneContato(),
                            itemLogisticaDTOS,
                            entity.getStatusEntrega());
                    response.add(dto);
                }
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<LogisticaDTO> BuscarEntregaPorId(Long id)
    {
        try
        {
            LogisticaEntity entity = logisticaRepository.findById(id).orElseThrow(
                    ()-> new EntityNotFoundException()
            );
            List<ItemLogisticaDTO> itemLogisticaDTOS = new ArrayList<>();
            for(ItemLogisticaEntity itemLogistica : entity.getProdutos())
            {
                ItemLogisticaDTO dto = new ItemLogisticaDTO(itemLogistica.getProduto(),
                        itemLogistica.getQuantidade(),
                        itemLogistica.getValorUnitario(),
                        itemLogistica.getValorTotal());
                itemLogisticaDTOS.add(dto);
            }
            LogisticaDTO response = new LogisticaDTO(entity.getId(),
                                                entity.getNomeCliente(),
                                                entity.getEnderecoEntrega(),
                                                entity.getTelefoneContato(),
                                                itemLogisticaDTOS,
                                                entity.getStatusEntrega());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public void NovolancamentoLogistica(LogisticaRequestDTO dto)
    {
        try
        {
            if(dto != null)
            {
                List<ItemLogisticaEntity> itemLogisticaEntities = new ArrayList<>();
                for(ItemLogisticaDTO itemLogistica : dto.produtos())
                {
                    ItemLogisticaEntity itemLogisticaInterno = new ItemLogisticaEntity(itemLogistica);
                    itemLogisticaInterno.setTimeStamp(LocalDateTime.now());
                    itemLogisticaRepository.save(itemLogisticaInterno);
                    itemLogisticaEntities.add(itemLogisticaInterno);
                }
                LogisticaEntity entity = new LogisticaEntity(dto);
                entity.setProdutos(itemLogisticaEntities);
                entity.setTimeStamp(LocalDateTime.now());
                entity.setStatusEntrega(STATUSENTREGA.AGUARDANDO);
                logisticaRepository.save(entity);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public void IniciarEntrega(Long id)
    {
        try
        {
            if(id != null)
            {
                LogisticaEntity entrega = logisticaRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                if(entrega.getStatusEntrega() == STATUSENTREGA.AGUARDANDO &&
                        entrega.getStatusEntrega() != STATUSENTREGA.EM_ROTA &&
                        entrega.getStatusEntrega() != STATUSENTREGA.ENTREGUE)
                {
                    entrega.setDataEntrega(LocalDateTime.now());
                    entrega.setTimeStamp(LocalDateTime.now());
                    entrega.setStatusEntrega(STATUSENTREGA.EM_ROTA);
                    logisticaRepository.save(entrega);
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

    public void FinalizarEntrega(Long id)
    {
        try
        {
            if(id != null)
            {
                LogisticaEntity entrega = logisticaRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                if(entrega.getStatusEntrega() != STATUSENTREGA.AGUARDANDO &&
                   entrega.getStatusEntrega() == STATUSENTREGA.EM_ROTA &&
                   entrega.getStatusEntrega() != STATUSENTREGA.ENTREGUE)
                {
                    entrega.setDataEntrega(LocalDateTime.now());
                    entrega.setTimeStamp(LocalDateTime.now());
                    entrega.setStatusEntrega(STATUSENTREGA.ENTREGUE);
                    logisticaRepository.save(entrega);
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

    public void CancelarEntrega(Long id, String motivo)
    {
        try
        {
            if(id != null)
            {
                LogisticaEntity entrega = logisticaRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                    entrega.setDataEntrega(LocalDateTime.now());
                    entrega.setTimeStamp(LocalDateTime.now());
                    entrega.setStatusEntrega(STATUSENTREGA.CANCELADA);
                    entrega.setNotificacao(motivo);
                    logisticaRepository.save(entrega);
                }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public void ReiniciarEntrega(Long id, String motivo)
    {
        try
        {
            if(id != null)
            {
                LogisticaEntity entrega = logisticaRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                entrega.setDataEntrega(LocalDateTime.now());
                entrega.setTimeStamp(LocalDateTime.now());
                entrega.setStatusEntrega(STATUSENTREGA.AGUARDANDO);
                entrega.setNotificacao(motivo);
                logisticaRepository.save(entrega);
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
