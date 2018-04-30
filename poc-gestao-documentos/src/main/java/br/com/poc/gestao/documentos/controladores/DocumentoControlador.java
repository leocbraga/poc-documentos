package br.com.poc.gestao.documentos.controladores;

import br.com.poc.gestao.documento.dto.DocumentoRequisicao;
import br.com.poc.gestao.documento.dto.RespostaAbstrata;
import br.com.poc.gestao.documentos.servicos.DocumentoServico;
import org.springframework.beans.factory.annotation.Autowired;
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
    public RespostaAbstrata salvar(@RequestBody  DocumentoRequisicao documentoRequisicao){

        return documentoServico.salvar(documentoRequisicao);

    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String teste(){

        return "Leonardo";

    }


}
