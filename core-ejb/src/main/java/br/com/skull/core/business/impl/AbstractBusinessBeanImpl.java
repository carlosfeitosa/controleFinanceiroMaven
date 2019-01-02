package br.com.skull.core.business.impl;

import br.com.skull.core.business.model.GenericDto;
import br.com.skull.core.business.model.converter.Converter;
import br.com.skull.core.service.dao.entity.Entidade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean abstrato.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 *
 * @param <B> Bean que extende esta classe
 * @param <D> Dto relacionada a classe de negócio
 * @param <E> Entidade relacionada a classe de negócio
 * @param <C> Converter para conversão de dto <--> entidade
 */
public abstract class AbstractBusinessBeanImpl<B extends AbstractBusinessBeanImpl, 
        D extends GenericDto, E extends Entidade, C extends Converter> {

  protected Logger logger;

  protected Converter<D, E> converter;

  /**
   * Construtor abstrato dos beans de negócio (instancia logger e converter).
   *
   * @param classeImplBusinessBean classe que representa o bean de negócio
   * @param classeImplConverter classe que representa o conversor (dto/entity)
   */
  public AbstractBusinessBeanImpl(Class<B> classeImplBusinessBean, Class<C> classeImplConverter) {
    this.logger = LoggerFactory.getLogger(classeImplBusinessBean);

    try {
      this.converter = classeImplConverter.newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      this.logger.error(ex.getMessage(), ex);
    }

  }

}
