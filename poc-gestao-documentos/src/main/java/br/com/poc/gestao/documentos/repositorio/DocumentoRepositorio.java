package br.com.poc.gestao.documentos.repositorio;

import br.com.poc.dominio.entidades.Documento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepositorio extends CrudRepository<Documento, Integer> {
}
