package br.com.skull.core.business.model;

import java.util.Date;

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

}
