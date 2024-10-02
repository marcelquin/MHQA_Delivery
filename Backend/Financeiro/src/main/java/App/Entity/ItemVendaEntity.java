package App.Entity;

import App.Dto.Request.ItemVendaRequestDTO;
import App.Dto.Response.ItemVendaResponseDTO;
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
@Table(name = "Item_Venda")
public class ItemVendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private Double quantidade;

    private Double valorUnitario;

    private Double valorTotal;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;

    public ItemVendaEntity(ItemVendaRequestDTO dto, LocalDateTime hora) {
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.quantidade = dto.quantidade();
        this.valorUnitario = dto.valorUnitario();
        this.valorTotal = dto.valorTotal();
        this.timeStamp = hora;
    }
}
