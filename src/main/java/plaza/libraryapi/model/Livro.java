package plaza.libraryapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "autor")
@EntityListeners(AuditingEntityListener.class)
public class Livro{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 20, nullable = false)
    private String isbn;
    @Column(length = 150, nullable = false)
    private String titulo;
    @Column
    private LocalDate dataPublicacao;
    @Enumerated(EnumType.STRING )
    private GeneroLivro genero;
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal preco;
    @CreatedDate
    private LocalDateTime dataCadastro;
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;
    private UUID idUsuario;

    @ManyToOne(
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
            )
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
