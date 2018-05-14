package br.com.poc.gestao.documentos.ouvintes;

import br.com.poc.gestao.documentos.kafka.ClienteKafka;
import br.com.poc.gestao.documentos.servicos.EmailServico;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OuvinteEmail implements Ouvinte{

    private Logger LOGGER = Logger.getLogger(OuvinteEmail.class);

    @Autowired
    private EmailServico emailServico;


    @Override
    @KafkaListener(topics = ClienteKafka.TOPICO_INCLUSAO_DOCUMENTO, groupId = "gropo_email")
    public void escutar(String mensagem){

        LOGGER.info("Um documento foi atualziado");

        emailServico.enviar("leonardocepcei@gmail.com", "leonardocepcei@gmail.com", mensagem);

    }

}
