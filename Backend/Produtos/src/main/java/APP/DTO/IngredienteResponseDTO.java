package APP.DTO;

import java.time.LocalDateTime;

public record IngredienteResponseDTO(
        Long id,
        String nome,
        Double quantidade,
        String valor,
        LocalDateTime dataControle
) {
}
