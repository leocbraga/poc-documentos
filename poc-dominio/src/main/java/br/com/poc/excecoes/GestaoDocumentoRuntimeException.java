package br.com.poc.excecoes;

public class GestaoDocumentoRuntimeException extends RuntimeException {

    public GestaoDocumentoRuntimeException(String mensagem){

        super(mensagem);

    }

    public GestaoDocumentoRuntimeException(Exception e){

        super(e);

    }

}
