package APP.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record ItemCardapioResponseDTO(
        Long id,
        String nome,
        String descricao,
        String codigo,
        String valor,
        List<IngredienteResponseDTO>ingredientes,
        LocalDateTime dataControle
) {
}
