package br.com.poc.gestao.documentos.kafka;

import br.com.poc.dominio.entidades.Documento;
import br.com.poc.excecoes.GestaoDocumentoRuntimeException;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClienteKafka {

    private Logger LOGGER = Logger.getLogger(ClienteKafka.class);

    public static final String TOPICO_INCLUSAO_DOCUMENTO = "topico_documento";

    @Autowired
    private KafkaTemplate<Integer, String> template;

    public boolean notificarCadastroDocumento(Documento documento){

        return notificar(TOPICO_INCLUSAO_DOCUMENTO, documento);

    }

    public boolean notificar(String topico, Documento documento){

        if(topico == null){

            throw new GestaoDocumentoRuntimeException("O tópico informado é inválido");

        }

        try{

            template.send(topico, documento.toString());

            return Boolean.TRUE;

        }catch(TimeoutException | InterruptException e){

            LOGGER.error(e.getMessage(), e);

            throw new GestaoDocumentoRuntimeException(e);

        }

    }

}
