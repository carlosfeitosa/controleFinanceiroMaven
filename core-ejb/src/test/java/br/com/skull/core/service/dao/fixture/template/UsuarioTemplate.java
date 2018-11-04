package br.com.skull.core.service.dao.fixture.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;

/**
 * Fixture para Usuario (entidade)
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class UsuarioTemplate implements TemplateLoader {

  public static String VALIDO = "VALIDO";
  public static String INVALIDO = "INVALIDO";

  @Override
  public void load() {
    Fixture.of(Usuario.class)
            .addTemplate(VALIDO, loadUsuarioValido())
            .addTemplate(INVALIDO, loadUsuarioInvalido());
  }

  /**
   * Carrega template de Usuário válido.
   *
   * @return Rule
   */
  private Rule loadUsuarioValido() {
    return new Rule() {
      {
        add("nome", name());
        add("email", regex("usuario(\\d{5})@gmail.com"));
        add("password", name());
        add("tipo", random(TipoUsuarioEnum.ADMIN.getCodigo(), TipoUsuarioEnum.REGULAR.getCodigo()));
      }
    };
  }

  /**
   * Carrega template de Usuário inválido.
   *
   * @return Rule
   */
  private Rule loadUsuarioInvalido() {
    return new Rule() {
      {
        add("nome", null);
        add("password", null);
      }
    };
  }

}
