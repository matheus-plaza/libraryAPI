package plaza.libraryapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    private String login;

    private String senha;

    @Type(ListArrayType.class)
    @Column(columnDefinition = "varchar[]")
    private List<String> roles;
}
