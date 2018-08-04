package br.com.skull.core.service.dao.enums;

/**
 * Enum que representa o tipo de categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public enum TipoCategoriaEnum {
  CATEGORIA(1L, "Categoria pai"),
  CONTA(2L, "Conta"),
  LANCAMENTO(3L, "Lançamento"),
  LOG(4L, "Log");

  private final long codigo;
  private final String descricao;

  /**
   * Construtor privado.
   *
   * @param codigo código para uso interno
   * @param descricao descrição para exibição
   */
  private TipoCategoriaEnum(long codigoTipo, String descricaoTipo) {
    this.codigo = codigoTipo;
    this.descricao = descricaoTipo;
  }

  public long getCodigo() {
    return this.codigo;
  }

  public String getDescricao() {
    return this.descricao;
  }
}
