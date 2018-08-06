package br.com.skull.core.service.dao.impl;

import br.com.skull.core.service.dao.AbstractServiceRemote;
import br.com.skull.core.service.dao.entity.IEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 * Bean abstrato para controle de entidades.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 *
 * @param <E> entidade do serviço
 * @param <S> DAO do serviço
 */
public class AbstractService<E extends IEntity, S extends AbstractServiceRemote>
        implements AbstractServiceRemote<E> {

  @PersistenceContext(unitName = "core-ejbPU")
  protected EntityManager em;

  protected Logger logger;

  private Class<E> entidadeBase;

  private AbstractService() {
  }

  /**
   * Construtor da classe abstrata.
   *
   * @param entidadeBase classe que representa a entidade base do serviço
   * @param servicoBase classe que representa o serviço
   */
  protected AbstractService(Class<E> entidadeBase, Class<S> servicoBase) {
    this.entidadeBase = entidadeBase;

    setLogger(servicoBase);
  }

  /**
   * Configura o logger da classe.
   */
  private void setLogger(Class<S> servicoBase) {
    this.logger = LoggerFactory.getLogger(servicoBase);
  }

  @Override
  public E persist(E entidade) {
    try {
      logger.info("Iniciando persistência de entidade ("
              .concat(entidade.getClass().toString().concat(")")));
      logger.debug("Entidade: {}", entidade);

      em.persist(entidade);

      em.flush();

      logger.info("Persistência da entidade realizada com sucesso");

    } catch (ConstraintViolationException ex) {
      logger.error("Exceção constraint violation: "
              .concat(ex.getConstraintViolations().toString()));

      throw ex;
    } catch (Exception ex) {
      logger.error("Exceção não mapeada ao tentar persistir: "
              .concat(ex.getMessage()));

      throw ex;
    }

    return entidade;
  }

  @Override
  public void remove(E entidade) {
    logger.info("Iniciando exclusão de entidade ("
            .concat(entidade.getClass().toString().concat(")")));
    logger.debug("Entidade: {}", entidade);

    if (null != entidade.getId()) {
      logger.debug("Id da entidade que será excluída: {}", entidade.getId());
      E item = entidade;

      if (!em.contains(entidade)) {
        item = em.merge(entidade);
      }

      em.remove(item);

      logger.info("Exclusão da entidade realizada com sucesso");
    }

  }

  @Override
  public void remove(long id) {
    logger.info("Recuperando entidade por id");
    logger.debug("ID: {} | Class: {}", id, entidadeBase.toString());

    E entidade;
    entidade = em.find(entidadeBase, id);

    logger.debug("Entidade: {}", entidade);

    this.remove(entidade);
  }
}
