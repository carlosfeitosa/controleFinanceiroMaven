package br.com.skull.core.service.dao;

import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.entity.impl.UsuarioConta;

import java.util.List;

/**
 * Interface para o serviço de relação usuário x conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface UsuarioContaServiceBean extends AbstractServiceBean<UsuarioConta> {

  /**
   * Lista relação de usuário com conta por identificador.
   *
   * @param id identificador da relação
   *
   * @return relação
   */
  UsuarioConta getById(long id);

  /**
   * Lista relações de usuário com conta por conta.
   *
   * @param conta conta para listar relações
   *
   * @return lista de contas
   */
  List<UsuarioConta> getByConta(Conta conta);

  /**
   * Lista relações de usuário com conta por usuário.
   *
   * @param usuario usuário para listar relações
   *
   * @return lista de contas
   */
  List<UsuarioConta> getByUsuario(Usuario usuario);
}
