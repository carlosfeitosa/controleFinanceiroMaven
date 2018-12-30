package br.com.skull.core.business.model.converter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe abstrata para conversor DTO <--> Entidade.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 *
 * @param <C> classe concreta (Conversor)
 */
public abstract class AbstractConverter<C extends AbstractConverter> {

  protected Logger logger;

  /**
   * Construtor da classe abstrata.
   *
   * @param classeBean classe que extende esta classe
   */
  protected AbstractConverter(Class<C> classeBean) {
    this.logger = LoggerFactory.getLogger(classeBean);
  }
}
