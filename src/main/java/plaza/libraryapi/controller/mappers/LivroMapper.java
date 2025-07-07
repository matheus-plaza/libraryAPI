package plaza.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import plaza.libraryapi.controller.dto.CadastroLivroDTO;
import plaza.libraryapi.controller.dto.PesquisaLivroDto;
import plaza.libraryapi.model.Livro;
import plaza.libraryapi.repository.AutorRepository;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    AutorMapper autorMapper;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract PesquisaLivroDto toDTO(Livro livro);
}
