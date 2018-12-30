package br.com.skull.core.business.model.converter;

import br.com.skull.core.business.model.AbstractDto;
import br.com.skull.core.service.dao.entity.Entidade;

/**
 * Interface para conversores de DTO e Entidade.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 *
 * @param <D> dto utilizado para conversão
 * @param <E> entidade utilizada para conversão
 */
public interface Converter<D extends AbstractDto, E extends Entidade> {

  public D convert(E entidade);

  public E convert(D dto);

}
