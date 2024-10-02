package App.Entity;

import App.Enum.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Boleto")
@Builder
public class BoletoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String empresa;

    private String cnpj;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    private double parcelas;

    private double parcelaAtual;

    private double valorTotal;

    private double valorParcela;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPagamento;


    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;
}
