package App.Dto;


import App.Dto.Response.BoletosResponseDTO;
import App.Dto.Response.VendaResponseFinalizadaDTO;

import java.util.List;

public record RelatorioMensalDTO(
        String dataReferencia,
        List<VendaResponseFinalizadaDTO> vendasFinalizadas,
        String totalVendasDebito,

        String totalVendasCredito,

        String totalVendasDinheiro,

        String totalVendasPix,

        String totalVendasRecebida,

        String totalDebitos,

        List<BoletosResponseDTO> boletos
        ) {


}
