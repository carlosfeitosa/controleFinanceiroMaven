package br.com.skull.core.business.model;

/**
 * Interface para os DTOs.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface GenericDto {

  /**
   * Recupera o identificador do dto.
   *
   * @return identificador
   */
  public long getId();

  /**
   * Configura o identificador do dto.
   *
   * @param value identificador
   */
  public void setId(long value);
}
