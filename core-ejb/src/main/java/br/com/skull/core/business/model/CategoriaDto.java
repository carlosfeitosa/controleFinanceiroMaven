package br.com.skull.core.business.model;

import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import java.util.Date;
import java.util.Objects;

/**
 * DTO para categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaDto extends AbstractDto {

  private long idCategoriaPai;
  private String descricaoCategoriaPai;
  private String nome;
  private String descricao;
  private TipoCategoriaEnum tipo;
  private Date manutencao;

  public long getIdCategoriaPai() {
    return idCategoriaPai;
  }

  public void setIdCategoriaPai(long value) {
    this.idCategoriaPai = value;
  }

  public String getDescricaoCategoriaPai() {
    return descricaoCategoriaPai;
  }

  public void setDescricaoCategoriaPai(String value) {
    this.descricaoCategoriaPai = value;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String value) {
    this.nome = value;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String value) {
    this.descricao = value;
  }

  public TipoCategoriaEnum getTipo() {
    return tipo;
  }

  public void setTipo(TipoCategoriaEnum value) {
    this.tipo = value;
  }

  public Date getManutencao() {
    return manutencao;
  }

  public void setManutencao(Date value) {
    this.manutencao = value;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 11 * hash + (int) (this.idCategoriaPai ^ (this.idCategoriaPai >>> 32));
    hash = 11 * hash + Objects.hashCode(this.descricaoCategoriaPai);
    hash = 11 * hash + Objects.hashCode(this.nome);
    hash = 11 * hash + Objects.hashCode(this.descricao);
    hash = 11 * hash + Objects.hashCode(this.tipo);
    hash = 11 * hash + Objects.hashCode(this.manutencao);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CategoriaDto other = (CategoriaDto) obj;
    if (this.idCategoriaPai != other.idCategoriaPai) {
      return false;
    }
    if (!Objects.equals(this.descricaoCategoriaPai, other.descricaoCategoriaPai)) {
      return false;
    }
    if (!Objects.equals(this.nome, other.nome)) {
      return false;
    }
    if (!Objects.equals(this.descricao, other.descricao)) {
      return false;
    }
    if (this.tipo != other.tipo) {
      return false;
    }
    if (!Objects.equals(this.manutencao, other.manutencao)) {
      return false;
    }
    return true;
  }
}
