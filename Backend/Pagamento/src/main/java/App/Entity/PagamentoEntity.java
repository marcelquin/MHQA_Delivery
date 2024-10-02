package App.Entity;

import App.Enum.FORMAPAGAMENTO;
import App.Enum.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Pagamento")
@Builder
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FORMAPAGAMENTO formaPagamento;


    private Boolean pago;

    private Double parcelas;

    private Double valorTotal;

    private Double valorParcela;

    private Double valorDesconto;

    private Double valorPago;

    private Double valorTroco;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataPagamento;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;
}
