package APP.DTO;

import APP.Enum.UNIDADEMEDIDA;

import java.time.LocalDateTime;

public record ProdutoResponseDTO(

        Long id,
        String nome,
        String codigo,
        Double quantidade,
        UNIDADEMEDIDA unidademedida,
        String valorPorcao,
        LocalDateTime dataControle
) {
}
