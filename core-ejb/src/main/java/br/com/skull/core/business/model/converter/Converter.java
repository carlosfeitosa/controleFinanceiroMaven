package br.com.skull.core.business.model.converter;

import br.com.skull.core.business.model.GenericDto;
import br.com.skull.core.service.dao.entity.Entidade;

import java.util.List;

/**
 * Interface para conversores de DTO e Entidade.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 *
 * @param <D> dto utilizado para conversão
 * @param <E> entidade utilizada para conversão
 */
public interface Converter<D extends GenericDto, E extends Entidade> {

  /**
   * Converte um dto em entidade.
   *
   * @param entidade entidade
   *
   * @return dto
   */
  public D convert(E entidade);

  /**
   * Converte uma entidade em dto.
   *
   * @param dto dto
   *
   * @return entidade
   */
  public E convert(D dto);

  /**
   * Converte lista de Entidade em lista de DTO.
   *
   * @param listaEntidade lista de entidade (Categoria)
   *
   * @return lista de DTO
   */
  public List<D> convert(List<E> listaEntidade);

}
