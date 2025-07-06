package plaza.libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import plaza.libraryapi.controller.dto.CadastroLivroDTO;
import plaza.libraryapi.repository.LivroRepository;

@Service
@RequiredArgsConstructor
public class LivroService {

    public final LivroRepository livroRepository;

}
