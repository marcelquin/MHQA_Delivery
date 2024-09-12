package APP.DTO;

import java.time.LocalDateTime;

public record EstoqueResponseDTO(
        String nome,
        String descricao,
        String codigo,
        Double quantidade,
        String valor
) {
}
