package br.com.poc.gestao.documentos.servicos;

import br.com.poc.dominio.entidades.Documento;
import br.com.poc.excecoes.GestaoDocumentoException;
import br.com.poc.gestao.documentos.dto.DocumentoRequisicao;
import br.com.poc.gestao.documentos.dto.DocumentoResposta;
import br.com.poc.gestao.documentos.dto.RespostaAbstrata;
import br.com.poc.gestao.documentos.kafka.ClienteKafka;
import br.com.poc.gestao.documentos.repositorio.DocumentoRepositorio;
import br.com.poc.gestao.documentos.util.HashUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentoServico extends Servico<Documento, Integer>{

    private Logger LOGGER = Logger.getLogger(DocumentoServico.class);

    @Autowired
    private DocumentoRepositorio repositorio;

    @Autowired
    private HashUtil hashUtil;

    @Autowired
    private ClienteKafka clienteKafka;

    @Transactional
    public RespostaAbstrata salvar(DocumentoRequisicao documentoRequisicao) throws GestaoDocumentoException {

        LOGGER.info("Convertendo documento");

        Documento documento = converterDTO(Documento.class, documentoRequisicao);

        LOGGER.info("Gerando Hash do documento");

        documento.setHash(hashUtil.gerarHashMD5(documentoRequisicao.getCaminho()));

        LOGGER.info("Salvando documento");

        Documento documentoSalvo = repositorio.save(documento);

        LOGGER.info("Notificando no Kafka");

        clienteKafka.notificarCadastroDocumento(documentoSalvo);

        LOGGER.info("Convertendo resposta");

        DocumentoResposta documentoResposta = converterDTO(DocumentoResposta.class, documentoSalvo);

        return new RespostaAbstrata(documentoResposta);

    }


    @Override
    public DocumentoRepositorio getRepositorio(){

        return repositorio;

    }

}
