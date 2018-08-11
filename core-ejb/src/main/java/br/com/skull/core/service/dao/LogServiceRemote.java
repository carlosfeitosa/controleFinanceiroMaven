package br.com.skull.core.service.dao;

import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Log;
import br.com.skull.core.service.dao.entity.impl.Usuario;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interface para o serviço de log.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Remote
public interface LogServiceRemote extends AbstractServiceRemote<Log> {

  /**
   * Lista log por identificador.
   *
   * @param id identificador
   *
   * @return log
   */
  Log getById(long id);

  /**
   * Lista log por momento.
   *
   * @param inicio momento inicial
   * @param termino momento final
   *
   * @return lista de logs
   */
  List<Log> getByMomento(Date inicio, Date termino);

  /**
   * Lista log por categoria e momento.
   *
   * @param categoria categoria de log
   * @param inicio momento inicial
   * @param termino momento final
   *
   * @return lista de logs
   */
  List<Log> getByCategoria(Categoria categoria, Date inicio, Date termino);

  /**
   * Lista log por usuário e momento.
   *
   * @param usuario usuário do log
   * @param inicio momento inicial
   * @param termino momento final
   *
   * @return lista de logs
   */
  List<Log> getByUsuario(Usuario usuario, Date inicio, Date termino);

  /**
   * Lista log por categoria, usuário e momento.
   *
   * @param categoria categoria de log
   * @param usuario usuário do log
   * @param inicio momento inicial
   * @param termino momento final
   *
   * @return lista de logs.
   */
  List<Log> getByCategoriaUsuario(Categoria categoria, Usuario usuario,
          Date inicio, Date termino);
}
