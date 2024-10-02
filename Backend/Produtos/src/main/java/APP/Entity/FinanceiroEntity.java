package APP.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceiroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "custoFixoEntity_id", referencedColumnName = "id")
    private CustoFixoEntity custosFixo;

    @OneToOne
    @JoinColumn(name = "custoVariadosEntity_id", referencedColumnName = "id")
    private CustoVariavelEntity custosVariavel;

    private Double porcentagemLucro;

    private Double markup;

    private Double RendimentoMensal;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;
}
