package br.com.skull.core.business.model;

import br.com.skull.core.service.dao.enums.TipoLancamentoEnum;

import java.util.Date;

/**
 * DTO para lançamento.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class LancamentoDto extends AbstractDto {

  private CategoriaDto categoria;
  private ContaDto conta;
  private UsuarioDto usuario;
  private TipoLancamentoEnum tipo;
  private Date momento;
  private Date manutencao;

  public CategoriaDto getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaDto value) {
    this.categoria = value;
  }

  public ContaDto getConta() {
    return conta;
  }

  public void setConta(ContaDto value) {
    this.conta = value;
  }

  public UsuarioDto getUsuario() {
    return usuario;
  }

  public void setUsuario(UsuarioDto value) {
    this.usuario = value;
  }

  public TipoLancamentoEnum getTipo() {
    return tipo;
  }

  public void setTipo(TipoLancamentoEnum value) {
    this.tipo = value;
  }

  public Date getMomento() {
    return momento;
  }

  public void setMomento(Date value) {
    this.momento = value;
  }

  public Date getManutencao() {
    return manutencao;
  }

  public void setManutencao(Date value) {
    this.manutencao = value;
  }

}
