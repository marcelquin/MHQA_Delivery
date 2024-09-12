package APP.DTO;

import java.time.LocalDateTime;

public record ProdutoDTO(
        Long id,
        String nome,
        String descricao,
        String codigo,
        String codigoEstoque,
        int quantidade,
        String valor,
        String notificacao,
        LocalDateTime dataEntrada
) {
}
