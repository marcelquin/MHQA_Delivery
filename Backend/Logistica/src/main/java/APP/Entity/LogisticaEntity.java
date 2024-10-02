package APP.Entity;

import APP.DTO.LogisticaRequestDTO;
import APP.Enum.STATUSENTREGA;
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
@Builder
@Table(name = "Logistica")
public class LogisticaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCliente;

    private String codigoVenda;

    private String enderecoEntrega;

    @OneToMany
    private List<ItemLogisticaEntity> Produtos;

    private String telefoneContato;

    @Enumerated(EnumType.STRING)
    private STATUSENTREGA statusEntrega;

    private String notificacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataEntrega;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataCancelamento;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;

    public LogisticaEntity(LogisticaRequestDTO dto) {
        this.nomeCliente = dto.nomeCliente();
        this.codigoVenda = dto.codigoVenda();
        this.enderecoEntrega = dto.enderecoEntrega();
        this.telefoneContato = dto.telefoneContato();
    }
}
