package br.com.skull.core.business.model.impl;

import br.com.skull.core.business.model.AbstractDto;

import java.util.Date;
import java.util.Objects;

/**
 * DTO para log.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class LogDto extends AbstractDto {

  private CategoriaDto categoria;
  private UsuarioDto usuario;
  private String descricao;
  private Date momento;

  public CategoriaDto getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaDto value) {
    this.categoria = value;
  }

  public UsuarioDto getUsuario() {
    return usuario;
  }

  public void setUsuario(UsuarioDto value) {
    this.usuario = value;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String value) {
    this.descricao = value;
  }

  public Date getMomento() {
    return momento;
  }

  public void setMomento(Date value) {
    this.momento = value;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 11 * hash + Objects.hashCode(this.categoria);
    hash = 11 * hash + Objects.hashCode(this.usuario);
    hash = 11 * hash + Objects.hashCode(this.descricao);
    hash = 11 * hash + Objects.hashCode(this.momento);
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
    final LogDto other = (LogDto) obj;
    if (!Objects.equals(this.descricao, other.descricao)) {
      return false;
    }
    if (!Objects.equals(this.categoria, other.categoria)) {
      return false;
    }
    if (!Objects.equals(this.usuario, other.usuario)) {
      return false;
    }
    if (!Objects.equals(this.momento, other.momento)) {
      return false;
    }
    return true;
  }

}
