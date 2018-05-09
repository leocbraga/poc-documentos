package br.com.poc.gestao.documentos.controller;

import br.com.poc.dominio.entidades.Documento;
import br.com.poc.dominio.entidades.Tipo;
import br.com.poc.gestao.documentos.controladores.DocumentoControlador;
import br.com.poc.gestao.documentos.kafka.ClienteKafka;
import br.com.poc.gestao.documentos.repositorio.DocumentoRepositorio;
import br.com.poc.gestao.documentos.servicos.DocumentoServico;
import br.com.poc.gestao.documentos.util.HashUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = DocumentoControlador.class)
public class DocumentoControllerTest {

    private Logger LOGGER = Logger.getLogger(DocumentoControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentoRepositorio documentoRepositorio;

    @MockBean
    private ClienteKafka clienteKafka;

    @Autowired
    private HashUtil hashUtil;

    @Autowired
    private DocumentoServico documentoServico;

    private Gson gson;

    @TestConfiguration
    static class DocumentoServicoTesteContextConfiguration {

        @Bean
        public DocumentoServico getDocumentoServico() {

            return new DocumentoServico();

        }

    }

    @TestConfiguration
    static class HashUtilTesteContextConfiguration {

        @Bean
        public HashUtil getHashUtil() {

            return new HashUtil();

        }

    }

    @Before
    public void iniciarMocks(){

        //Gson
        gson = new Gson();

        //Regras
        Answer<Documento> regra = (invocationOnMock) -> {

            Documento documento = (Documento) invocationOnMock.getArgument(0);

            if(documento.getNome() == null){

                throw new ConstraintViolationException("Nome inválido", new HashSet<>());

            }

            if(documento != null){

                documento.setId(1);

                return documento;

            }

            return null;

        };

        when(documentoRepositorio.save(any(Documento.class))).thenAnswer(regra);

    }

    @Test
    public void testarSalvarDocumentoComSucesso(){

        try {

            String json = gson.toJson(getDocumentoValido());

            LOGGER.info(String.format("Json enviado: %s", json));

            mockMvc.perform(MockMvcRequestBuilders

                    .post("/documentos/")

                    .contentType(MediaType.APPLICATION_JSON)

                    .content(json)

            ).andExpect(status().isOk());

        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

    }

    @Test
    public void testarSalvarDocumentoInexistente(){

        try {

            String json = gson.toJson(getDocumentoArquivoInexistente());

            LOGGER.info(String.format("Json enviado: %s", json));

            mockMvc.perform(MockMvcRequestBuilders

                    .post("/documentos/")

                    .contentType(MediaType.APPLICATION_JSON)

                    .content(json)

            ).andExpect(status().is4xxClientError());

        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

    }

    @Test
    public void testarSalvarDocumentoConstraintViolationException(){

        try {

            String json = gson.toJson(getDocumentoNomeNulo());

            LOGGER.info(String.format("Json enviado: %s", json));

            mockMvc.perform(MockMvcRequestBuilders

                    .post("/documentos/")

                    .contentType(MediaType.APPLICATION_JSON)

                    .content(json)

            ).andExpect(status().is4xxClientError());

        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

    }

    @Test
    public void testarSalvarDocumentoNulo(){

        try {

            String json = gson.toJson(getDocumentoArquivoNulo());

            LOGGER.info(String.format("Json enviado: %s", json));

            mockMvc.perform(MockMvcRequestBuilders

                    .post("/documentos/")

                    .contentType(MediaType.APPLICATION_JSON)

                    .content(json)

            ).andExpect(status().is4xxClientError());

        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

    }

    private Documento getDocumentoValido(){

        Documento documento = new Documento();

        String caminho = getClass().getClassLoader().getResource("teste-geracao-md5.txt").getFile();

        documento.setCaminho(caminho);

        documento.setNome("teste");

        documento.setTipo(Tipo.PDF);

        return documento;

    }

    private Documento getDocumentoArquivoInexistente(){

        Documento documento = getDocumentoValido();

        documento.setCaminho("xpto");

        return documento;

    }

    private Documento getDocumentoArquivoNulo(){

        Documento documento = getDocumentoValido();

        documento.setCaminho(null);

        return documento;

    }

    private Documento getDocumentoNomeNulo(){

        Documento documento = getDocumentoValido();

        documento.setNome(null);

        return documento;

    }

}
