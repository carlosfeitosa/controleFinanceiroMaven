package br.com.skull.core.service.dao.fixture.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.skull.core.service.dao.entity.impl.Conta;

/**
 * Fixture para Conta (entidade).
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class ContaTemplate implements TemplateLoader {

  public static String VALIDO = "VALIDO";
  public static String INVALIDO = "INVALIDO";

  @Override
  public void load() {
    Fixture.of(Conta.class)
            .addTemplate(VALIDO, loadContaValida())
            .addTemplate(INVALIDO, loadContaInvalida());
  }

  /**
   * Carrega template de Conta válida.
   *
   * @return Rule
   */
  private Rule loadContaValida() {
    return new Rule() {
      {
        add("nome", name());
        add("descricao", name());
      }
    };
  }

  /**
   * Carrega template de Conta inválida.
   *
   * @return Rule
   */
  private Rule loadContaInvalida() {
    return new Rule() {
      {
        add("nome", null);
        add("descricao", null);
      }
    };
  }

}
