package br.com.skull.core.service.dao.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum que representa o tipo de lançamento.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public enum TipoLancamentoEnum {
  CREDITO(1L, "Crédito efetuado"),
  CREDITO_PROGRAMADO(2L, "Crédito programado"),
  DEBITO(3L, "Débito efetuado"),
  DEBITO_PROGRAMADO(4L, "Débito programado");

  private final long codigo;
  private final String descricao;

  private static final Map<Long, TipoLancamentoEnum> map = new HashMap<Long, TipoLancamentoEnum>();

  static {
    for (TipoLancamentoEnum tipoEnum : TipoLancamentoEnum.values()) {
      map.put(tipoEnum.codigo, tipoEnum);
    }
  }

  /**
   * Construtor privado.
   *
   * @param codigo código para uso interno
   * @param descricao descrição para exibição
   */
  private TipoLancamentoEnum(long codigoTipo, String descricaoTipo) {
    this.codigo = codigoTipo;
    this.descricao = descricaoTipo;
  }

  public long getCodigo() {
    return this.codigo;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public static TipoLancamentoEnum valueOf(Long codigo) {
    return map.get(codigo);
  }
}
