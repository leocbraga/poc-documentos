package br.com.poc.dominio.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


@Entity
@Table(name = "documento", schema = "public")
public class Documento implements Entidade<Integer> {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;

    @NotNull
    @Size(min = 5, max = 60)
    private String nome;

    @NotNull
    @Enumerated
    private Tipo tipo;

    @NotNull
    @Column(unique = true)
    private String caminho;

    @NotNull
    private String hash;

    @Override
    public String toString(){

        StringBuilder builder = new StringBuilder();

        builder.append("ID=")

                .append(getId())

                .append("|")

                .append("NOME=")

                .append(getNome())

                .append("|");

        return builder.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Documento documento = (Documento) o;
        return Objects.equals(id, documento.id) &&
                Objects.equals(nome, documento.nome) &&
                tipo == documento.tipo &&
                Objects.equals(caminho, documento.caminho) &&
                Objects.equals(hash, documento.hash);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nome, tipo, caminho, hash);
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
