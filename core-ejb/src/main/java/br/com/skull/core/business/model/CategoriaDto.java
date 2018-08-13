package br.com.skull.core.business.model;

import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import java.util.Date;

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
}
