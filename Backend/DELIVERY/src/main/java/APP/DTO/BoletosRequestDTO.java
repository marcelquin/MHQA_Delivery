package APP.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BoletosRequestDTO(
        String empresa,
        String cnpj,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataVencimento,
        Double parcelas,
        Double parcelasAtual,
        Double valorTotal
) {
}
