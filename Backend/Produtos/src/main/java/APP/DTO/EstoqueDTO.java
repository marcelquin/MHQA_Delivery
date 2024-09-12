package APP.DTO;

import java.time.LocalDateTime;

public record EstoqueDTO(
        Long id,
        String nome,
        String descricao,
        String codigo,
        int quantidade,
        String valor
) {
}
