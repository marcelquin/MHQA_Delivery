package App.Dto.Response;

import App.Enum.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record BoletosResponseDTO(
        Long id,
        String empresa,
        String cnpj,
        StatusPagamento statusPagamento,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataVencimento,
        Double parcelas,
        Double parcelaAtual,
        String valorTotal,
        String valorParcela

) {
}
