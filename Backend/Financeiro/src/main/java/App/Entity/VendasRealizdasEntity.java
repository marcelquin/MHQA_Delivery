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
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "VendasRealizadas")
public class VendasRealizdasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCLiente;

    private String codigo;

    @OneToMany
    private List<ItemVendaEntity> items;

    private Double valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @Enumerated(EnumType.STRING)
    private FORMAPAGAMENTO formapagamento;

    private Double parcelas;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataVenda;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataPagamento;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;

}
