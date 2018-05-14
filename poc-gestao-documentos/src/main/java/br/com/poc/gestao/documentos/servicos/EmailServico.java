package br.com.poc.gestao.documentos.servicos;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServico {

    private Logger LOGGER = Logger.getLogger(EmailServico.class);

    @Autowired
    private JavaMailSender enviadorEmail;

    public boolean enviar(String remetente, String destinatario, String texto){

        if(texto == null || texto.isEmpty()){

            throw new IllegalArgumentException("O texto do email está nulo");

        }

        if(destinatario == null || destinatario.isEmpty()){

            throw new IllegalArgumentException("O destinatario do email está nulo");

        }

        if(remetente == null || remetente.isEmpty()){

            throw new IllegalArgumentException("O remetente do email está nulo");

        }

        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setText(texto);

        mensagem.setTo(destinatario);

        mensagem.setFrom(remetente);

        try{

            enviadorEmail.send(mensagem);

            return Boolean.TRUE;

        } catch(Exception e){

            LOGGER.error(e.getMessage(), e);

            return Boolean.FALSE;

        }

    }

}
