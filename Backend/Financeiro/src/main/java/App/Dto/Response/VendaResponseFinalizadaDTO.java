package App.Dto.Response;

import App.Enum.FORMAPAGAMENTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record VendaResponseFinalizadaDTO(
        Long id,
        String nomeCLiente,
        String codigo,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataVenda,
        List<ItemVendaResponseDTO> itens,
        Double parcelas,
        String valorParcelas,
        String valorTotal,
        FORMAPAGAMENTO formapagamento,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataPagamento,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataSistema
) {

}
