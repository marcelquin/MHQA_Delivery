package APP.DTO;

import java.time.LocalDateTime;

public record ProdutoResponseDTO(
        String nome,
        String descricao,
        String codigo,
        String codigoEstoque,
        Double quantidade,
        String valor,
        String notificacao,
        LocalDateTime dataEntrada
) {
}
