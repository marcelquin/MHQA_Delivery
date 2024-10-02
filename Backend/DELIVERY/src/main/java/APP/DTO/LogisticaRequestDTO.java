package APP.Dto;

import APP.Enum.STATUSENTREGA;

import java.util.List;

public record LogisticaRequestDTO(
        String nomeCliente,
        String codigoVenda,
        String enderecoEntrega,
        String telefoneContato,
        List<ItemLogisticaDTO> produtos
) {
}
