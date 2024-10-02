package App.Dto.Request;

import App.Dto.Response.ItemVendaResponseDTO;
import App.Enum.FORMAPAGAMENTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record VendaRequestFinalziadaDTO(

        String nomeCLiente,
        String codigo,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataVenda,
        List<ItemVendaRequestDTO> itens,
        Double parcelas,
        Double valorParcelas,
        Double valorTotal,
        FORMAPAGAMENTO formapagamento,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataPagamento
) {

}
