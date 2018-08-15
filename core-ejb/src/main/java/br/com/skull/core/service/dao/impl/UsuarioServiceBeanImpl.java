package br.com.skull.core.service.dao.impl;

import br.com.skull.core.service.dao.UsuarioServiceBean;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;

import java.util.List;
import javax.ejb.Stateful;

/**
 * Bean para controle de persistência de usuário.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Stateful
public class UsuarioServiceBeanImpl extends AbstractServiceBeanImpl<Usuario, UsuarioServiceBeanImpl>
        implements UsuarioServiceBean {

  private static final String QUERY_ALL = "Usuario.findAll";
  private static final String QUERY_POR_ID = "Usuario.findById";
  private static final String QUERY_POR_NOME = "Usuario.findByNome";
  private static final String QUERY_POR_NOME_APROXIMADO = "Usuario.findByNomeAproximado";
  private static final String QUERY_POR_EMAIL = "Usuario.findByEmail";
  private static final String QUERY_POR_TIPO = "Usuario.findByTipo";

  private static final String ATTR_ID = "id";
  private static final String ATTR_NOME = "nome";
  private static final String ATTR_EMAIL = "email";
  private static final String ATTR_TIPO = "tipo";

  public UsuarioServiceBeanImpl() {
    super(Usuario.class, UsuarioServiceBeanImpl.class);
  }

  @Override
  public List<Usuario> getTodos() {
    logger.info("Recuperando todas os usuários");

    return em.createNamedQuery(QUERY_ALL, Usuario.class).getResultList();
  }

  @Override
  public Usuario getById(long id) {
    logger.info("Recuperando usuário por id");
    logger.debug("ID: {}", id);

    return em.createNamedQuery(QUERY_POR_ID, Usuario.class)
            .setParameter(ATTR_ID, id).getSingleResult();
  }

  @Override
  public List<Usuario> getByNome(String nome) {
    logger.info("Recuperando usuários por nome");
    logger.debug("Nome: {}", nome);

    return em.createNamedQuery(QUERY_POR_NOME, Usuario.class)
            .setParameter(ATTR_NOME, nome).getResultList();
  }

  @Override
  public List<Usuario> getByNomeAproximado(String nome) {
    logger.info("Recuperando usuários por nome aproximado");
    logger.debug("Nome aproximado: {}", nome);

    return em.createNamedQuery(QUERY_POR_NOME_APROXIMADO, Usuario.class)
            .setParameter(ATTR_NOME, "%" + nome + "%").getResultList();
  }

  @Override
  public Usuario getByEmail(String email) {
    logger.info("Recuperando usuário por email");
    logger.debug("Email: {}", email);

    return em.createNamedQuery(QUERY_POR_EMAIL, Usuario.class)
            .setParameter(ATTR_EMAIL, email).getSingleResult();
  }

  @Override
  public List<Usuario> getByTipo(TipoUsuarioEnum tipo) {
    logger.info("Recuperando usuários por tipo");
    logger.debug("Tipo (enum): {}", tipo);

    return this.getByTipo(tipo.getCodigo());
  }

  @Override
  public List<Usuario> getByTipo(long codigoTipo) {
    logger.info("Recuperando usuários por código de tipo");
    logger.debug("Tipo: {}", codigoTipo);

    return em.createNamedQuery(QUERY_POR_TIPO, Usuario.class)
            .setParameter(ATTR_TIPO, codigoTipo).getResultList();
  }

}
