package APP.Entity;

import APP.Enum.STATUSVENDA;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Venda")
@Builder
public class VendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCliente;

    private String documento;

    private String codigo;

    private Double valor;

    @Enumerated(EnumType.STRING)
    private STATUSVENDA statusvenda;

    @OneToMany
    private List<ItemVendaEntity> itemVenda;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataVenda;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;
}
