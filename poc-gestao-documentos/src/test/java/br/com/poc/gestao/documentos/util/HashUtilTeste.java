package br.com.poc.gestao.documentos.util;

import br.com.poc.excecoes.GestaoDocumentoException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class HashUtilTeste {

    @Autowired
    private HashUtil hashUtil;

    @TestConfiguration
    public static class HashUtilApplicationContext{

        @Bean
        public HashUtil getHashUtil(){

            return new HashUtil();

        }

    }

    @Test
    public void testarMd5ComArquivo() throws GestaoDocumentoException {

        String caminho = getClass().getClassLoader().getResource("teste-geracao-md5.txt").getFile();

        String hashMd5 = hashUtil.gerarHashMD5(caminho);

        assertEquals("D41D8CD98F00B204E9800998ECF8427E", hashMd5);

    }

    @Test(expected = GestaoDocumentoException.class)
    public void testarArquivoNaoExistente() throws GestaoDocumentoException{

        hashUtil.gerarHashMD5("caminho-invalido.txt");

    }

    @Test(expected = GestaoDocumentoException.class)
    public void testarArquivoNulo() throws GestaoDocumentoException{

        hashUtil.gerarHashMD5(null);

    }

}
