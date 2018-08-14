package br.com.skull.core.business.exception;

/**
 * Exceção para o bean de categoria, lançada na tentativa de persistir uma categoria de contas sem
 * pai
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaContaSemPaiException extends Exception {

  private static final String MSG_ERROR = "Não é possível persistir uma categoria de conta "
          + "sem pai";

  public CategoriaContaSemPaiException() {
    super(MSG_ERROR);
  }

}
