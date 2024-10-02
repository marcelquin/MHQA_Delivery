package APP.Dto;

public record ItemVendaRequestDTO(
        String nome,
        String descricao,
        Double quantidade,
        Double valorUnitario,
        Double valorTotal
) {
}
