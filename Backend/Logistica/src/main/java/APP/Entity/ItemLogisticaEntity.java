package APP.Entity;

import APP.DTO.ItemLogisticaDTO;
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
@Builder
@Table(name = "Item_Logistica")
public class ItemLogisticaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String produto;
    private Double Quantidade;
    private Double valorUnitario;
    private Double ValorTotal;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;

    public ItemLogisticaEntity(ItemLogisticaDTO dto) {
        this.produto = dto.produto();
        Quantidade = dto.Quantidade();
        this.valorUnitario = dto.valorUnitario();
        ValorTotal = dto.ValorTotal();
    }
}
