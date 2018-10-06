package br.com.skull.core.business.model;

import java.util.Date;
import java.util.Objects;

/**
 * DTO para conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class ContaDto extends AbstractDto {

  private CategoriaDto categoria;
  private String nome;
  private String descricao;
  private Date manutencao;

  public CategoriaDto getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaDto value) {
    this.categoria = value;
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

  public Date getManutencao() {
    return manutencao;
  }

  public void setManutencao(Date value) {
    this.manutencao = value;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 53 * hash + Objects.hashCode(this.categoria);
    hash = 53 * hash + Objects.hashCode(this.nome);
    hash = 53 * hash + Objects.hashCode(this.descricao);
    hash = 53 * hash + Objects.hashCode(this.manutencao);
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
    final ContaDto other = (ContaDto) obj;
    if (!Objects.equals(this.nome, other.nome)) {
      return false;
    }
    if (!Objects.equals(this.descricao, other.descricao)) {
      return false;
    }
    if (!Objects.equals(this.categoria, other.categoria)) {
      return false;
    }
    if (!Objects.equals(this.manutencao, other.manutencao)) {
      return false;
    }
    return true;
  }
}
