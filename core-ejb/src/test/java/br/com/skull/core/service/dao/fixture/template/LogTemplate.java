package br.com.skull.core.service.dao.fixture.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.skull.core.service.dao.entity.impl.Log;

/**
 * Fixture para log (entidade)
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class LogTemplate implements TemplateLoader {

  public static String VALIDO = "Valido";
  public static String INVALIDO = "Invalido";

  @Override
  public void load() {
    Fixture.of(Log.class)
            .addTemplate(VALIDO, loadLogValido())
            .addTemplate(INVALIDO, loadLogInvalido());
  }

  /**
   * Carrega template de log válido.
   *
   * @return Rule
   */
  private Rule loadLogValido() {
    return new Rule() {
      {
        add("descricao", name());
      }
    };
  }

  /**
   * Carrega template de log inválido.
   *
   * @return Rule
   */
  private Rule loadLogInvalido() {
    return new Rule() {
      {
        add("descricao", null);
      }
    };
  }

}
