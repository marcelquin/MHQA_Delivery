package APP.Dto;

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
