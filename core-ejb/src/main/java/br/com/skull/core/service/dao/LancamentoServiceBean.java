package br.com.skull.core.service.dao;

import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Lancamento;
import br.com.skull.core.service.dao.enums.TipoLancamentoEnum;

import java.util.Date;
import java.util.List;

/**
 * Interface para o serviço de lançamentos.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface LancamentoServiceBean extends AbstractServiceBean<Lancamento> {

  /**
   * Lista lançamento por identificador.
   *
   * @param id identificador
   *
   * @return lançamento
   */
  Lancamento getById(long id);

  /**
   * Lista lançamentos por conta.
   *
   * @param conta conta relacionada
   * @param inicio data de início do lançamento
   * @param termino data de término do lançamento
   *
   * @return lista de lançamentos
   */
  List<Lancamento> getByConta(Conta conta, Date inicio, Date termino);

  /**
   * Lista lançamentos por conta e tipo de lançamento.
   *
   * @param conta conta relacionada
   * @param tipo tipo de lançamento
   * @param inicio data de início do lançamento
   * @param termino data de término do lançamento
   *
   * @return lista de lançamentos
   */
  List<Lancamento> getByContaTipo(Conta conta, TipoLancamentoEnum tipo, Date inicio, Date termino);

  /**
   * Lista lançamentos por conta e tipo de lançamento.
   *
   * @param conta conta relacionada
   * @param tipo tipo de lançamento
   * @param inicio data de início do lançamento
   * @param termino data de término do lançamento
   *
   * @return lista de lançamentos
   */
  List<Lancamento> getByContaTipo(Conta conta, long tipo, Date inicio, Date termino);

  /**
   * Lista lançamentos por conta e categoria.
   *
   * @param conta conta relacionada
   * @param categoria categoria relacionada
   * @param inicio data de início do lançamento
   * @param termino data de término do lançamento
   *
   * @return lista de lançamentos
   */
  List<Lancamento> getByContaCategoria(Conta conta, Categoria categoria, Date inicio, Date termino);

  /**
   * Lista lançamentos por conta, tipo de lançamento e categoria.
   *
   * @param conta conta relacionada
   * @param tipo tipo de lançamento
   * @param categoria categoria relacionada
   * @param inicio data de início do lançamento
   * @param termino data de termino do lançamento
   *
   * @return lista de lançamentos
   */
  List<Lancamento> getByContaTipoCategoria(Conta conta, TipoLancamentoEnum tipo,
          Categoria categoria, Date inicio, Date termino);

  /**
   * Lista lançamentos por conta, tipo de lançamento e categoria.
   *
   * @param conta conta relacionada
   * @param codigoTipo tipo de lançamento
   * @param categoria categoria relacionada
   * @param inicio data de início do lançamento
   * @param termino data de termino do lançamento
   *
   * @return lista de lançamentos
   */
  List<Lancamento> getByContaTipoCategoria(Conta conta, long codigoTipo,
          Categoria categoria, Date inicio, Date termino);
}
