package br.com.skull.core.service.dao.enums;

/**
 * Enum que representa o tipo de usuário.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public enum TipoUsuarioEnum {
  ADMIN(1L, "Administrador"),
  REGULAR(2L, "Usuário convencional");

  private final long codigo;
  private final String descricao;

  /**
   * Construtor privado.
   *
   * @param codigo código para uso interno
   * @param descricao descrição para exibição
   */
  private TipoUsuarioEnum(long codigoTipo, String descricaoTipo) {
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
