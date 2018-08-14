package br.com.skull.core.business.exception;

/**
 * Exceção para o bean de categoria, lançada na tentativa de persistir uma categoria de lançamento
 * sem pai
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaLancamentoSemPaiException extends Exception {

  private static final String MSG_ERROR = "Não é possível persistir uma categoria de lançamento "
          + "sem pai";

  public CategoriaLancamentoSemPaiException() {
    super(MSG_ERROR);
  }

}
