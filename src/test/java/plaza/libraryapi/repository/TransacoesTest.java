package plaza.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plaza.libraryapi.service.TransacaoService;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    /*

     */
    @Test
    void transacaoSimples(){
        transacaoService.executar();
    }

    @Test
    void transacaoManaged(){
        transacaoService.updateManaged();
    }
}
