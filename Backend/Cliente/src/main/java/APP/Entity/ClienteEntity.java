package APP.Entity;

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
@Builder
@Table(name = "Cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sobrenome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EnderecoEntity_id", referencedColumnName = "id")
    private EnderecoEntity endereco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contatoEntity_id", referencedColumnName = "id")
    private ContatoEntity contato;

    private String notificacao;

    private Double score;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timeStamp;
}
