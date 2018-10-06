package br.com.skull.core.business.model;

import br.com.skull.core.service.dao.enums.TipoLancamentoEnum;

import java.util.Date;
import java.util.Objects;

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

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 53 * hash + Objects.hashCode(this.categoria);
    hash = 53 * hash + Objects.hashCode(this.conta);
    hash = 53 * hash + Objects.hashCode(this.usuario);
    hash = 53 * hash + Objects.hashCode(this.tipo);
    hash = 53 * hash + Objects.hashCode(this.momento);
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
    final LancamentoDto other = (LancamentoDto) obj;
    if (!Objects.equals(this.categoria, other.categoria)) {
      return false;
    }
    if (!Objects.equals(this.conta, other.conta)) {
      return false;
    }
    if (!Objects.equals(this.usuario, other.usuario)) {
      return false;
    }
    if (this.tipo != other.tipo) {
      return false;
    }
    if (!Objects.equals(this.momento, other.momento)) {
      return false;
    }
    if (!Objects.equals(this.manutencao, other.manutencao)) {
      return false;
    }
    return true;
  }

}
