package br.com.skull.core.business.model;

import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;

import java.util.Date;

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

}
