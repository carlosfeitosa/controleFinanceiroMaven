package br.com.skull.core.service.dao.enums;

import java.util.HashMap;
import java.util.Map;

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

  private static final Map<Long, TipoUsuarioEnum> map = new HashMap<Long, TipoUsuarioEnum>();

  static {
    for (TipoUsuarioEnum tipoEnum : TipoUsuarioEnum.values()) {
      map.put(tipoEnum.codigo, tipoEnum);
    }
  }

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

  public static TipoUsuarioEnum valueOf(Long codigo) {
    return map.get(codigo);
  }
}
