package APP.DTO;

import APP.Enum.STATUSENTREGA;

import java.util.List;

public record LogisticaDTO(
        Long id,
        String nomeCliente,
        String enderecoEntrega,
        String telefoneContato,
        List<ItemLogisticaDTO> produtos,
        STATUSENTREGA statusentrega
) {
}
