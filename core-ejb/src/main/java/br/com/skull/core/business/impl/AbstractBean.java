package br.com.skull.core.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean abstrato.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 *
 * @param <B> Bean que extende esta classe
 */
public abstract class AbstractBean<B extends AbstractBean> {

  protected Logger logger;

  /**
   * Construtor da classe abstrata.
   *
   * @param classeBean classe que extende esta classe
   */
  protected AbstractBean(Class<B> classeBean) {
    this.logger = LoggerFactory.getLogger(classeBean);
  }

}
