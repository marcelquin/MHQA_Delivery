package APP.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ClienteDTO(
        Long id,
        String nome,
        String sobrenome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,
        String logradouro,
        String numero,
        String bairro,
        String referencia,
        String  cep,
        String  cidade,
        String  estado,
        String telefone,
        String email,
        String score

) {
}
