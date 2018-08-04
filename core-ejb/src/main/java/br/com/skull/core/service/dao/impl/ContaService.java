package br.com.skull.core.service.dao.impl;

import br.com.skull.core.service.dao.ContaServiceRemote;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;

import java.util.List;
import javax.ejb.Stateless;

/**
 * Bean para controle de persistência de conta
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Stateless
public class ContaService extends AbstractService<Conta, ContaService>
        implements ContaServiceRemote {

  private static final String QUERY_ALL = "Conta.findAll";
  private static final String QUERY_POR_ID = "Conta.findById";
  private static final String QUERY_POR_NOME = "Conta.findByNome";
  private static final String QUERY_POR_CATEGORIA_PAI = "Conta.findByCategoria";

  private static final String ATTR_ID = "id";
  private static final String ATTR_NOME = "nome";
  private static final String ATTR_CATEGORIA = "categoria";

  public ContaService() {
    super(Conta.class, ContaService.class);
  }

  @Override
  public List<Conta> getTodas() {
    logger.info("Recuperando todas as contas");

    return em.createNamedQuery(QUERY_ALL, Conta.class).getResultList();
  }

  @Override
  public Conta getById(long id) {
    logger.info("Recuperando conta por id");
    logger.debug("ID: {}", id);

    return em.createNamedQuery(QUERY_POR_ID, Conta.class)
            .setParameter(ATTR_ID, id).getSingleResult();
  }

  @Override
  public List<Conta> getByNome(String nome) {
    logger.info("Recuperando contas por nome");
    logger.debug("Nome: {}", nome);

    return em.createNamedQuery(QUERY_POR_NOME, Conta.class)
            .setParameter(ATTR_NOME, nome).getResultList();
  }

  @Override
  public List<Conta> getByCategoria(Categoria categoria) {
    logger.info("Recuperando contas por categoria");
    logger.debug("Categoria: {}", categoria);

    return em.createNamedQuery(QUERY_POR_CATEGORIA_PAI, Conta.class)
            .setParameter(ATTR_CATEGORIA, categoria).getResultList();
  }

}
