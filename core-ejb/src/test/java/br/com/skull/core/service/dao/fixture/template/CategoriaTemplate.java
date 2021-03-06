package br.com.skull.core.service.dao.fixture.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import java.util.Calendar;

/**
 * Fixture para Categoria (entidade).
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaTemplate implements TemplateLoader {

  public static String VALIDO = "VALIDO";
  public static String VALIDO_COM_ID_E_MANUTENCAO = "VALIDO_COM_ID_E_MANUTENCAO";
  public static String INVALIDO = "INVALIDO";

  @Override
  public void load() {
    Fixture.of(Categoria.class)
            .addTemplate(VALIDO, loadCategoriaValida())
            .addTemplate(VALIDO_COM_ID_E_MANUTENCAO, loadCategoriaValidaComIdComManutencao())
            .addTemplate(INVALIDO, loadCategoriaInvalida());
  }

  /**
   * Carrega template de Categoria válida.
   *
   * @return Rule
   */
  private Rule loadCategoriaValida() {
    return new Rule() {
      {
        add("nome", name());
        add("descricao", name());
        add("tipo", random(TipoCategoriaEnum.CATEGORIA.getCodigo(),
                TipoCategoriaEnum.CONTA.getCodigo(),
                TipoCategoriaEnum.LANCAMENTO.getCodigo(),
                TipoCategoriaEnum.LOG.getCodigo()));
      }
    };
  }

  /**
   * Carrega template de Categoria válida.
   *
   * @return Rule
   */
  private Rule loadCategoriaValidaComIdComManutencao() {
    return new Rule() {
      {
        add("id", random(1L, 10L, 100L));
        add("nome", name());
        add("descricao", name());
        add("tipo", random(TipoCategoriaEnum.CATEGORIA.getCodigo(),
                TipoCategoriaEnum.CONTA.getCodigo(),
                TipoCategoriaEnum.LANCAMENTO.getCodigo(),
                TipoCategoriaEnum.LOG.getCodigo()));
        add("manutencao", Calendar.getInstance().getTime());
      }
    };
  }

  /**
   * Carrega template de Categoria inválida.
   *
   * @return Rule
   */
  private Rule loadCategoriaInvalida() {
    return new Rule() {
      {
        add("nome", null);
        add("descricao", name());
        add("tipo", random(TipoCategoriaEnum.CATEGORIA.getCodigo(),
                TipoCategoriaEnum.CONTA.getCodigo(),
                TipoCategoriaEnum.LANCAMENTO.getCodigo(),
                TipoCategoriaEnum.LOG.getCodigo()));
      }
    };
  }

}
