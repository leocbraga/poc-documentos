package br.com.poc.gestao.documentos.manipuladores;

import br.com.poc.excecoes.GestaoDocumentoException;
import br.com.poc.excecoes.GestaoDocumentoRuntimeException;
import br.com.poc.gestao.documentos.dto.RespostaAbstrata;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GestaoDocumentosExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger LOGGER = Logger.getLogger(GestaoDocumentosExceptionHandler.class);

    @ExceptionHandler
    protected ResponseEntity<RespostaAbstrata>  manipularConstraintViolationException(ConstraintViolationException e){

        //TODO Manipular campos com erro

        LOGGER.warn(e.getMessage(), e);

        return new ResponseEntity<RespostaAbstrata>(new RespostaAbstrata(e), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    protected ResponseEntity<RespostaAbstrata> manipularGestaoDocumentoRuntimeException(GestaoDocumentoRuntimeException e){

        LOGGER.error(e.getMessage(), e);

        return new ResponseEntity<RespostaAbstrata>(new RespostaAbstrata(""), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler
    protected ResponseEntity<RespostaAbstrata> manipularGestaoDocumentoException(GestaoDocumentoException e){

        LOGGER.warn(e.getMessage());

        return new ResponseEntity<>(new RespostaAbstrata(e), HttpStatus.BAD_REQUEST);

    }

}
