package App.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "Relatorio_Mensal")
public class RelatorioMensalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataReferencia;

    private int anoReferencia;

    @OneToOne
    @JoinColumn(name = "vendasEntity_id", referencedColumnName = "id")
    private VendasEntity vendas;

    @OneToOne
    @JoinColumn(name = "debitosEntity_id", referencedColumnName = "id")
    private DebitosEntity debitos;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;
}
