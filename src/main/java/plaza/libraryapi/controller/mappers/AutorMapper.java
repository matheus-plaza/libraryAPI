package plaza.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import plaza.libraryapi.controller.dto.AutorDTO;
import plaza.libraryapi.model.Autor;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
