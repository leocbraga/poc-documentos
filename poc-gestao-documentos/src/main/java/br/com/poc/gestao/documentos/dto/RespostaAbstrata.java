package br.com.poc.gestao.documentos.dto;

public class RespostaAbstrata {

    private Object retorno;

    public RespostaAbstrata(Object retorno){

        this.retorno = retorno;

    }


    public Object getRetorno(){

        return this.retorno;

    }

}
