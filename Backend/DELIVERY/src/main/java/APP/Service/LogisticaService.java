package APP.Service;

import APP.Dto.LogisticaDTO;
import APP.Dto.LogisticaRequestDTO;
import APP.ServiceMs.LogisticaMsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class LogisticaService {

    private final LogisticaMsService service;

    public LogisticaService(LogisticaMsService service) {
        this.service = service;
    }

    public ResponseEntity<List<LogisticaDTO>> ListarEntregas()
    {
        try
        {
            List<LogisticaDTO> response = service.ListarEntregasEmAberto().getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<LogisticaDTO>> ListarEntregasEmAberto()
    {
        try
        {
           List<LogisticaDTO> response = service.ListarEntregasEmAberto().getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<LogisticaDTO> BuscarEntregaPorId(@RequestParam Long id)
    {
        try
        {
            if(id != null)
            {
                LogisticaDTO response = service.BuscarEntregaPorId(id).getBody();
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public void IniciarEntrega(Long id)
    {
        try
        {
            if(id != null)
            {
                service.IniciarEntrega(id);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }
    @PutMapping("/FinalizarEntrega")
    public void FinalizarEntrega(Long id)
    {
        try
        {
            if(id != null)
            {
                service.FinalizarEntrega(id);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public void CancelarEntrega(Long id,
                                String motivo)
    {
        try
        {
            if(id != null && motivo != null)
            {
                service.CancelarEntrega(id, motivo);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public void ReiniciarEntrega(Long id,
                                 String motivo)
    {
        try
        {
           if(id != null &&
           motivo != null)
           {
               service.ReiniciarEntrega(id, motivo);
           }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }
}
