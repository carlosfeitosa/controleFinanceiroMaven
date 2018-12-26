package br.com.skull.core.service.dao.impl;

import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import java.util.List;
import javax.ejb.Stateless;

/**
 * Bean para controle de persistência de categoria
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Stateless
public class CategoriaServiceBeanImpl
        extends AbstractServiceBeanImpl<Categoria, CategoriaServiceBeanImpl> implements
        CategoriaServiceBean {

  private static final String QUERY_ALL = "Categoria.findAll";
  private static final String QUERY_POR_ID = "Categoria.findById";
  private static final String QUERY_POR_NOME = "Categoria.findByNome";
  private static final String QUERY_POR_NOME_APROXIMADO = "Categoria.findByNomeAproximado";
  private static final String QUERY_POR_TIPO = "Categoria.findByTipo";
  private static final String QUERY_POR_CATEGORIA_PAI = "Categoria.findByCategoriaPai";

  private static final String ATTR_ID = "id";
  private static final String ATTR_NOME = "nome";
  private static final String ATTR_TIPO = "tipo";
  private static final String ATTR_CATEGORIA_PAI = "categoriaPai";

  public CategoriaServiceBeanImpl() {
    super(Categoria.class, CategoriaServiceBeanImpl.class);
  }

  @Override
  public List<Categoria> getTodas() {
    logger.info("Recuperando todas as categorias");

    return em.createNamedQuery(QUERY_ALL, Categoria.class).getResultList();
  }

  @Override
  public Categoria getById(long id) {
    logger.info("Recuperando categoria por id");
    logger.debug("ID: {}", id);

    return em.createNamedQuery(QUERY_POR_ID, Categoria.class)
            .setParameter(ATTR_ID, id).getSingleResult();
  }

  @Override
  public List<Categoria> getByNome(String nome) {
    logger.info("Recuperando categorias por nome");
    logger.debug("Nome: {}", nome);

    return em.createNamedQuery(QUERY_POR_NOME, Categoria.class)
            .setParameter(ATTR_NOME, nome).getResultList();
  }

  @Override
  public List<Categoria> getByNomeAproximado(String nome) {
    logger.info("Recuperando categorias por nome aproximado");
    logger.debug("Nome aproximado: {}", nome);

    return em.createNamedQuery(QUERY_POR_NOME_APROXIMADO, Categoria.class)
            .setParameter(ATTR_NOME, "%" + nome + "%").getResultList();
  }

  @Override
  public List<Categoria> getByTipo(long codigoTipo) {
    logger.info("Recuperando categorias por tipo");
    logger.debug("Tipo: {}", codigoTipo);

    return em.createNamedQuery(QUERY_POR_TIPO, Categoria.class)
            .setParameter(ATTR_TIPO, codigoTipo).getResultList();
  }

  @Override
  public List<Categoria> getByTipo(TipoCategoriaEnum tipo) {
    logger.info("Recuperando categorias por tipo (enum)");
    logger.debug("Tipo (enum): {}", tipo);

    return this.getByTipo(tipo.getCodigo());
  }

  @Override
  public List<Categoria> getFilhas(Categoria categoriaPai) {
    logger.info("Recuperando categorias por categoria pai");
    logger.debug("Categoria pai: {}", categoriaPai);

    return em.createNamedQuery(QUERY_POR_CATEGORIA_PAI, Categoria.class)
            .setParameter(ATTR_CATEGORIA_PAI, categoriaPai).getResultList();
  }

}
