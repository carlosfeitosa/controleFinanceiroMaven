package br.com.skull.core.service.dao;

import br.com.skull.core.service.dao.entity.IEntity;

/**
 * Interface abstrata para EntityManager.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 *
 * @param <E> entidade do serviço
 */
public interface AbstractServiceBean<E extends IEntity> {

  /**
   * Persiste uma entidade.
   *
   * @param entidade entidade para manter
   * 
   * @return entidade após ser persistida
   */
  E persist(E entidade);

  /**
   * Remove uma entidade.
   *
   * @param entidade entidade para remover
   */
  void remove(E entidade);

  /**
   * Remove uma entidade.
   *
   * @param id identificador da entidade
   */
  void remove(long id);
}
