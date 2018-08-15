package br.com.skull.core.service.dao.impl;

import br.com.skull.core.service.dao.UsuarioContaServiceBean;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.entity.impl.UsuarioConta;

import java.util.List;
import javax.ejb.Stateless;

/**
 * Bean para controle de persistência da relação de usuário com conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Stateless
public class UsuarioContaServiceBeanImpl extends
        AbstractServiceBeanImpl<UsuarioConta, UsuarioContaServiceBeanImpl> implements
        UsuarioContaServiceBean {

  private static final String QUERY_POR_ID = "UsuarioConta.findById";
  private static final String QUERY_POR_CONTA = "UsuarioConta.findByConta";
  private static final String QUERY_POR_USUARIO = "UsuarioConta.findByUsuario";

  private static final String ATTR_ID = "id";
  private static final String ATTR_CONTA = "conta";
  private static final String ATTR_USUARIO = "usuario";

  public UsuarioContaServiceBeanImpl() {
    super(UsuarioConta.class, UsuarioContaServiceBeanImpl.class);
  }

  @Override
  public UsuarioConta getById(long id) {
    logger.info("Recuperando relação de usuário com conta por id");
    logger.debug("ID: {}", id);

    return em.createNamedQuery(QUERY_POR_ID, UsuarioConta.class)
            .setParameter(ATTR_ID, id).getSingleResult();
  }

  @Override
  public List<UsuarioConta> getByConta(Conta conta) {
    logger.info("Recuperando relação de usuário com conta por conta");
    logger.debug("Conta: {}", conta);

    return em.createNamedQuery(QUERY_POR_CONTA, UsuarioConta.class)
            .setParameter(ATTR_CONTA, conta).getResultList();
  }

  @Override
  public List<UsuarioConta> getByUsuario(Usuario usuario) {
    logger.info("Recuperando relação de usuário com conta por usuário");
    logger.debug("Usuário: {}", usuario);

    return em.createNamedQuery(QUERY_POR_USUARIO, UsuarioConta.class)
            .setParameter(ATTR_USUARIO, usuario).getResultList();
  }

}
