package plaza.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import plaza.libraryapi.controller.dto.UsuarioDTO;
import plaza.libraryapi.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDTO(Usuario usuario);
}
