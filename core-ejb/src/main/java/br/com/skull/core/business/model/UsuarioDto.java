package br.com.skull.core.business.model;

import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;

import java.util.Date;
import java.util.Objects;

/**
 * DTO para usuário.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class UsuarioDto extends AbstractDto {

  private TipoUsuarioEnum tipo;
  private String nome;
  private String email;
  private Date manutencao;
  private Date ultimoLogon;

  public TipoUsuarioEnum getTipo() {
    return tipo;
  }

  public void setTipo(TipoUsuarioEnum value) {
    this.tipo = value;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String value) {
    this.nome = value;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String value) {
    this.email = value;
  }

  public Date getManutencao() {
    return manutencao;
  }

  public void setManutencao(Date value) {
    this.manutencao = value;
  }

  public Date getUltimoLogon() {
    return ultimoLogon;
  }

  public void setUltimoLogon(Date value) {
    this.ultimoLogon = value;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 23 * hash + Objects.hashCode(this.tipo);
    hash = 23 * hash + Objects.hashCode(this.nome);
    hash = 23 * hash + Objects.hashCode(this.email);
    hash = 23 * hash + Objects.hashCode(this.manutencao);
    hash = 23 * hash + Objects.hashCode(this.ultimoLogon);
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
    final UsuarioDto other = (UsuarioDto) obj;
    if (!Objects.equals(this.nome, other.nome)) {
      return false;
    }
    if (!Objects.equals(this.email, other.email)) {
      return false;
    }
    if (this.tipo != other.tipo) {
      return false;
    }
    if (!Objects.equals(this.manutencao, other.manutencao)) {
      return false;
    }
    if (!Objects.equals(this.ultimoLogon, other.ultimoLogon)) {
      return false;
    }
    return true;
  }

}
