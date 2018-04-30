package br.com.poc.dominio.entidades;

import java.io.Serializable;

public interface Entidade<T extends Serializable> {

    public T getId();

}
