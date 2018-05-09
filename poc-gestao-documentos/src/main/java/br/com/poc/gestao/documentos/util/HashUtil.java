package br.com.poc.gestao.documentos.util;

import br.com.poc.excecoes.GestaoDocumentoException;
import br.com.poc.excecoes.GestaoDocumentoRuntimeException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashUtil {

    private Logger LOGGER = Logger.getLogger(HashUtil.class);

    public String gerarHashMD5(String caminhoArquivo) throws GestaoDocumentoException {

        if(caminhoArquivo == null || caminhoArquivo.isEmpty()){

            throw new GestaoDocumentoException("O caminho do documento é nulo");

        }

        Path caminho = Paths.get(caminhoArquivo);

        if(!Files.exists(caminho)){

            throw new GestaoDocumentoException("O arquivo informado não existe");

        }


        MessageDigest md = null;

        InputStream is = null;

        DigestInputStream dis = null;

        try{

            md = MessageDigest.getInstance("MD5");

            is = Files.newInputStream(caminho);

            dis = new DigestInputStream(is, md);

            return DatatypeConverter.printHexBinary(md.digest());

        } catch(NoSuchAlgorithmException | IOException e){

            LOGGER.error(e.getMessage(), e);

            throw new GestaoDocumentoRuntimeException(e);

        } finally {

            if(is != null){

                try {

                    is.close();

                }catch(IOException e){

                    LOGGER.error(e.getMessage(), e);

                }

            }

            if(dis != null){

                try {

                    dis.close();

                }catch(IOException e){

                    LOGGER.error(e.getMessage(), e);

                }

            }

        }

    }

}
