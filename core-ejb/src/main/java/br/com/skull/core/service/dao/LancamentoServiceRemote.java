package br.com.skull.core.service.dao;

import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Lancamento;
import br.com.skull.core.service.dao.enums.TipoLancamentoEnum;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interface para o serviço de lançamentos.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Remote
public interface LancamentoServiceRemote extends AbstractServiceRemote<Lancamento> {

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
   * 
   * @throws java.text.ParseException caso hajam problemas de conversão de data
   */
  List<Lancamento> getByConta(Conta conta, Date inicio, Date termino) throws ParseException;

  /**
   * Lista lançamentos por conta e tipo de lançamento.
   *
   * @param conta conta relacionada
   * @param tipo tipo de lançamento
   * @param inicio data de início do lançamento
   * @param termino data de término do lançamento
   *
   * @return lista de lançamentos
   * 
   * @throws java.text.ParseException caso hajam problemas de conversão de data
   */
  List<Lancamento> getByContaTipo(Conta conta, TipoLancamentoEnum tipo, 
          Date inicio, Date termino) throws ParseException;

  /**
   * Lista lançamentos por conta e tipo de lançamento.
   *
   * @param conta conta relacionada
   * @param tipo tipo de lançamento
   * @param inicio data de início do lançamento
   * @param termino data de término do lançamento
   *
   * @return lista de lançamentos
   * 
   * @throws java.text.ParseException caso hajam problemas de conversão de data
   */
  List<Lancamento> getByContaTipo(Conta conta, long tipo, 
          Date inicio, Date termino) throws ParseException;

  /**
   * Lista lançamentos por conta e categoria.
   *
   * @param conta conta relacionada
   * @param categoria categoria relacionada
   * @param inicio data de início do lançamento
   * @param termino data de término do lançamento
   *
   * @return lista de lançamentos
   * 
   * @throws java.text.ParseException caso hajam problemas de conversão de data
   */
  List<Lancamento> getByContaCategoria(Conta conta, Categoria categoria, 
          Date inicio, Date termino) throws ParseException;

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
   * 
   * @throws java.text.ParseException caso hajam problemas de conversão de data
   */
  List<Lancamento> getByContaTipoCategoriaMomento(Conta conta, TipoLancamentoEnum tipo,
          Categoria categoria, Date inicio, Date termino) throws ParseException;

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
   * 
   * @throws java.text.ParseException caso hajam problemas de conversão de data
   */
  List<Lancamento> getByContaTipoCategoriaMomento(Conta conta, long codigoTipo,
          Categoria categoria, Date inicio, Date termino) throws ParseException;
}
