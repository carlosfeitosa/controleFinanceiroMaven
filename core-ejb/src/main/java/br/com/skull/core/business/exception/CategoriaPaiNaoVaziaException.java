package br.com.skull.core.business.exception;

/**
 * Exceção para o bean de categoria, lançada na tentativa de persistir uma categoria pai que já
 * possui categoria pai.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaPaiNaoVaziaException extends Exception {

  private static final String MSG_ERROR = "Não é possível persistir uma categoria pai que já é "
          + "filha de outra categoria: ";

  public CategoriaPaiNaoVaziaException(String nomeCategoriaEncontrada) {
    super(MSG_ERROR.concat(nomeCategoriaEncontrada));
  }

}
