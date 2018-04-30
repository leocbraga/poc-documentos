package br.com.poc.gestao.documentos.servicos;

import br.com.poc.dominio.entidades.Entidade;
import org.dozer.DozerBeanMapper;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Servico<T extends Entidade, J extends Serializable> {

    private DozerBeanMapper mapper =  new DozerBeanMapper();

    public Servico(){

    }

    protected <T> T converterDTO(Class<T> destino, Object objeto){

        return this.mapper.map(objeto, destino);

    }

    protected <T> List<T> converterListaDTO(Class<T> destino, List<?> objetos) {
        List<T> lista = new ArrayList<T>();
        for (Object obj : objetos) {
            lista.add(this.mapper.map(obj, destino));
        }
        return lista;
    }

    protected <T> List<T> converterListaDTO(Class<T> destino, List<?> objetos, String mapId) {
        List<T> lista = new ArrayList<T>();
        for (Object obj : objetos) {
            lista.add(this.mapper.map(obj, destino, mapId));
        }
        return lista;
    }

    protected abstract CrudRepository<T, J> getRepositorio();

}


