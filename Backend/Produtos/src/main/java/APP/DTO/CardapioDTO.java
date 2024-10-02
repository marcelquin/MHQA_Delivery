package APP.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record CardapioDTO(
        String nome,
        String codigo,
        String margemLucro,
        String markup,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataSistema,
        List<ItemCardapioDTO> items
) {
}
