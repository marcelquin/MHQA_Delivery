package APP.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ItemVendaDTO(
        String nome,
        String descricao,
        Double quantidade,
        String valorUnitarioItemVenda,
        String valorTotalItemVenda,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataSistema
) {
}
