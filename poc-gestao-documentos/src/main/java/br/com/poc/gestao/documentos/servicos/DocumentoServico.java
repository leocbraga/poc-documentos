package br.com.poc.gestao.documentos.servicos;

import br.com.poc.dominio.entidades.Documento;
import br.com.poc.gestao.documento.dto.DocumentoRequisicao;
import br.com.poc.gestao.documento.dto.DocumentoResposta;
import br.com.poc.gestao.documento.dto.RespostaAbstrata;
import br.com.poc.gestao.documentos.repositorio.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentoServico extends Servico<Documento, Integer>{

    @Autowired
    private DocumentoRepositorio repositorio;

    @Transactional
    public RespostaAbstrata salvar(DocumentoRequisicao documentoRequisicao){

        Documento documento = converterDTO(Documento.class, documentoRequisicao);

        Documento documentoSalvo = repositorio.save(documento);

        DocumentoResposta documentoResposta = converterDTO(DocumentoResposta.class, documentoSalvo);

        return new RespostaAbstrata(documentoResposta);

    }


    @Override
    public DocumentoRepositorio getRepositorio(){

        return repositorio;

    }

}
