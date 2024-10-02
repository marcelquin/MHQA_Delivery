package APP.Entity;

import APP.Enum.UNIDADEMEDIDA;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String codigo;

    private Double quantidade;  //quantidade -- unidades 4kg de mussarela 2

    @Enumerated(EnumType.STRING)
    private UNIDADEMEDIDA unidademedida;

    //valor por grama ou ml
    private Double valorPorcao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;
}
