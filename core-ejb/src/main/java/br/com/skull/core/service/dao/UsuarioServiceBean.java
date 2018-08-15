package br.com.skull.core.service.dao;

import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;

import java.util.List;

/**
 * Interface para o serviço de usuário.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface UsuarioServiceBean extends AbstractServiceBean<Usuario> {

  /**
   * Lista todos os usuários.
   *
   * @return lista de todos os usuários
   */
  List<Usuario> getTodos();

  /**
   * Lista usuário por identificador.
   *
   * @param id identificador do usuário
   *
   * @return usuário
   */
  Usuario getById(long id);

  /**
   * Lista usuários por nome.
   *
   * @param nome nome do usuário
   *
   * @return lista de usuários
   */
  List<Usuario> getByNome(String nome);

  /**
   * Lista de usuários por nome aproximado.
   *
   * @param nome nome aproximado do usuário
   *
   * @return lista de usuários
   */
  List<Usuario> getByNomeAproximado(String nome);

  /**
   * Lista usuário por e-mail.
   *
   * @param email e-mail do usuário
   *
   * @return usuário dono do e-mail
   */
  Usuario getByEmail(String email);

  /**
   * Lista usuários por tipo.
   *
   * @param tipo tipo de usuário
   *
   * @return lista de usuários por tipo
   */
  List<Usuario> getByTipo(TipoUsuarioEnum tipo);

  /**
   * Lista usuários por tipo.
   *
   * @param codigoTipo tipo de usuário
   *
   * @return lista de usuários por tipo
   */
  List<Usuario> getByTipo(long codigoTipo);
}
