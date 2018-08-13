package br.com.skull.core.business.model;

/**
 * Classe abstrata para implementação dos DTOs.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public abstract class AbstractDto {

  protected long id;

  public long getId() {
    return id;
  }

  public void setId(long value) {
    this.id = value;
  }
}
