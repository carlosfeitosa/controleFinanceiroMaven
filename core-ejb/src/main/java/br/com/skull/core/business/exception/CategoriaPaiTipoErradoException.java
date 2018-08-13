package br.com.skull.core.business.exception;

/**
 * Exceção para o bean de categoria, lançada na tentativa de persistir uma categoria pai que possui
 * o tipo errado.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaPaiTipoErradoException extends Exception {

  private static final String MSG_ERROR = "Não é possível persistir uma categoria pai que "
          + "com o tipo: ";

  public CategoriaPaiTipoErradoException(String descricaoTipo) {
    super(MSG_ERROR.concat(descricaoTipo));
  }

}
