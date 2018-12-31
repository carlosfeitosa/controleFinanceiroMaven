package br.com.skull.core.business;

import br.com.skull.core.business.model.impl.ContaDto;

import java.util.List;

/**
 * Interface para bean que contra as categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface ContaBusinessBean {

  /**
   * Recupera a conta por id.
   *
   * @param id identificador da conta
   *
   * @return conta
   */
  public ContaDto listarContaPorId(long id);

  /**
   * Recupera contas por um array de ids.
   *
   * @param listaIds lista de ids
   *
   * @return contas
   */
  public List<ContaDto> listarContasPorId(List<Long> listaIds);

  /**
   * Persite uma conta.
   *
   * @param dto conta a ser persistida
   *
   * @return conta
   */
  public ContaDto persistirConta(ContaDto dto);

  /**
   * Remove uma conta.
   *
   * @param dto conta a ser removida
   */
  public void removerConta(ContaDto dto);
}
