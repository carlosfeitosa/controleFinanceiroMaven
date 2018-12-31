package br.com.skull.core.business.model;

/**
 * Classe abstrata para implementação dos DTOs.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public abstract class AbstractDto implements GenericDto {

  protected long id;

  @Override
  public long getId() {
    return id;
  }

  @Override
  public void setId(long value) {
    this.id = value;
  }
}
