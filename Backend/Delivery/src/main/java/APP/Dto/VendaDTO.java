package APP.Dto;

import APP.Enum.STATUSVENDA;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public record VendaDTO(
        String nomeCliente,
        String codigoVenda,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataVenda,
        String valorAtual,
        STATUSVENDA statusAtual,
        List<ItemVendaDTO> items,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataControle
) {
}
