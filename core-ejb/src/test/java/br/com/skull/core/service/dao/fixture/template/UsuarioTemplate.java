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
        add("email", uniqueRandom("test1@gmail.com", "test2@gmail.com", "test3@gmail.com",
                "test4@gmail.com", "test5@gmail.com", "test6@gmail.com", "test7@gmail.com",
                "test8@gmail.com", "test9@gmail.com", "test10@gmail.com", "test11@gmail.com",
                "test12@gmail.com", "test13@gmail.com", "test42@gmail.com", "test15@gmail.com",
                "test16@gmail.com", "test17@gmail.com", "test18@gmail.com", "test19@gmail.com",
                "test20@gmail.com", "test21@gmail.com", "test22@gmail.com", "test23@gmail.com",
                "test24@gmail.com", "test25@gmail.com", "test26@gmail.com", "test27@gmail.com",
                "test28@gmail.com", "test29@gmail.com"));
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
