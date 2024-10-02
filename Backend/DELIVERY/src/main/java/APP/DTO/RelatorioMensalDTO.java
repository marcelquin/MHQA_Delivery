package APP.Dto;




import java.util.List;

public record RelatorioMensalDTO(
        String dataReferencia,
        List<VendaRequestFinalziadaDTO> vendasFinalizadas,
        String totalVendasDebito,

        String totalVendasCredito,

        String totalVendasDinheiro,

        String totalVendasPix,

        String totalVendasRecebida,

        String totalDebitos,

        List<BoletosRequestDTO> boletos
        ) {


}
