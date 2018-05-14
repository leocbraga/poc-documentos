package br.com.poc.gestao.documentos.controladores;

import br.com.poc.excecoes.GestaoDocumentoException;
import br.com.poc.gestao.documentos.dto.DocumentoRequisicao;
import br.com.poc.gestao.documentos.dto.RespostaAbstrata;
import br.com.poc.gestao.documentos.servicos.DocumentoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "documentos", produces = "application/json", consumes = "application/json")
public class DocumentoControlador {

    @Autowired
    private DocumentoServico documentoServico;


    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<RespostaAbstrata> salvar(@RequestBody  DocumentoRequisicao documentoRequisicao) throws GestaoDocumentoException {

        return ResponseEntity.ok(documentoServico.salvar(documentoRequisicao));

    }

}
