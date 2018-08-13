package br.com.skull.core.business.model;

import java.util.Date;

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
}
