package br.com.skull.core.service.dao;

import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;

import java.util.List;

/**
 * Interface para o serviço de conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface ContaServiceBean extends AbstractServiceBean<Conta> {

  /**
   * Lista todas as contas.
   *
   * @return lista de contas
   */
  List<Conta> getTodas();

  /**
   * Lista conta por identificador.
   *
   * @param id identificador
   *
   * @return conta
   */
  Conta getById(long id);

  /**
   * Lista contas pelo nome.
   *
   * @param nome nome da conta
   *
   * @return lista de contas
   */
  List<Conta> getByNome(String nome);

  /**
   * Lista contas por categoria.
   *
   * @param categoria categoria da conta
   *
   * @return lista de contas
   */
  List<Conta> getByCategoria(Categoria categoria);

}
