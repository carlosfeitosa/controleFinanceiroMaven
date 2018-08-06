package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceRemote;
import br.com.skull.core.service.dao.ContaServiceRemote;
import br.com.skull.core.service.dao.LancamentoServiceRemote;
import br.com.skull.core.service.dao.UsuarioServiceRemote;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Lancamento;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;
import br.com.skull.core.service.dao.enums.TipoLancamentoEnum;
import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;

/**
 * Classe de testes para o serviço DAO de Lançamento.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class LancamentoServiceTest {

  private static LancamentoServiceRemote SERVICE;
  private static CategoriaServiceRemote SERVICE_CATEGORIA;
  private static ContaServiceRemote SERVICE_CONTA;
  private static UsuarioServiceRemote SERVICE_USUARIO;

  private static final String NOME_CATEGORIA_TESTES = "__IGNORE-CategoriaTestes";
  private static final String DESCRICAO_CATEGORIA_TESTES = "Descrição categoria de testes"
          + " - não utilizar esta categoria";
  private static final long CODIGO_TIPO_CATEGORIA_TESTES
          = TipoCategoriaEnum.CATEGORIA.getCodigo();

  private static final String NOME_CONTA_TESTES = "__IGNORE-ContaLancamentoTestes";
  private static final String DESCRICAO_CONTA_TESTES = "Descrição conta de testes"
          + " - não utilizar esta conta";

  private static final String NOME_USUARIO_TESTES = "__IGNORE-UsuarioTestes";
  private static final String EMAIL_NOME_USUARIO_TESTES = "testes";
  private static final String EMAIL_DOMINIO_USUARIO_TESTES = "@testes.com";
  private static final String PASSWORD_USUARIO_TESTES = "passwd";
  private static final long CODIGO_TIPO_USUARIO_TESTES = TipoUsuarioEnum.REGULAR.getCodigo();

  private static final String DESCRICAO_LANCAMENTO_TESTES = "Descrição lançamento de testes"
          + " - não utilizar este lançamento";
  private static final TipoLancamentoEnum TIPO_LANCAMENTO_TESTES = TipoLancamentoEnum.CREDITO;
  private static final long CODIGO_TIPO_LANCAMENTO_TESTES
          = TIPO_LANCAMENTO_TESTES.CREDITO.getCodigo();
  private static final double VALOR_LANCAMENTO_TESTES = 99321123;

  public LancamentoServiceTest() {
  }

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Inicializa container e serviços.
   *
   * @throws NamingException caso não encontre o bean
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (LancamentoServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/LancamentoService");

    SERVICE_CATEGORIA = (CategoriaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaService");

    SERVICE_CONTA = (ContaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/ContaService");

    SERVICE_USUARIO = (UsuarioServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioService");
  }

  /**
   * Finaliza serviços pendentes.
   *
   * @throws javax.naming.NamingException caso não consiga recuperar os serviços para limpeza
   */
  @AfterClass
  public static void tearDownClass() throws NamingException {
    cleanUp();
  }

  /**
   * Test of persist method, of class LancamentoService.
   *
   */
  @Test
  @Repeat(times = 3)
  public void testPersist() {
    Categoria novaCategoriaLancamento = new Categoria();

    novaCategoriaLancamento.setNome(NOME_CATEGORIA_TESTES);
    novaCategoriaLancamento.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    novaCategoriaLancamento.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    novaCategoriaLancamento = SERVICE_CATEGORIA.persist(novaCategoriaLancamento);

    Conta novaContaLancamento = new Conta();

    novaContaLancamento.setNome(NOME_CONTA_TESTES);
    novaContaLancamento.setDescricao(DESCRICAO_CONTA_TESTES);
    novaContaLancamento.setCategoria(novaCategoriaLancamento);

    novaContaLancamento = SERVICE_CONTA.persist(novaContaLancamento);

    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    Lancamento novoLancamento = new Lancamento();

    String sulfixo = Calendar.getInstance().getTime().toString();

    novoLancamento.setCategoria(novaCategoriaLancamento);
    novoLancamento.setConta(novaContaLancamento);
    novoLancamento.setUsuario(novoUsuario);
    novoLancamento.setDescricao(DESCRICAO_LANCAMENTO_TESTES.concat(sulfixo));
    novoLancamento.setTipo(CODIGO_TIPO_LANCAMENTO_TESTES);
    novoLancamento.setValor(VALOR_LANCAMENTO_TESTES);
    novoLancamento.setMomento(Calendar.getInstance().getTime());

    novoLancamento = SERVICE.persist(novoLancamento);

    assertTrue("Id da conta não é maior que zero", (novoLancamento.getId() > 0));
  }

  /**
   * Test of remove method, of class LancamentoService.
   */
  @Test
  public void testRemoveByLancamento() {
    List<Conta> listaContasTestes = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);

    if (listaContasTestes.size() > 0) {
      List<Lancamento> listaLancamentos = SERVICE.getByConta(listaContasTestes.get(0), null, null);

      if (listaLancamentos.size() > 0) {
        SERVICE.remove(listaLancamentos.get(0));
        SERVICE_CONTA.remove(listaContasTestes.get(0));
        SERVICE_CATEGORIA.remove(listaLancamentos.get(0).getCategoria());
      } else {
        assertTrue("Não é possível realizar o teste porque não existem lançamentos testáveis",
                false);
      }
    } else {
      assertTrue("Não é possível realizar o teste porque não existem contas testáveis", false);
    }
  }

  /**
   * Test of remove method, of class LancamentoService.
   */
  @Test
  public void testRemoveById() {
    List<Conta> listaContasTestes = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);

    if (listaContasTestes.size() > 0) {
      List<Lancamento> listaLancamentos = SERVICE.getByConta(listaContasTestes.get(0), null, null);

      if (listaLancamentos.size() > 0) {
        SERVICE.remove(listaLancamentos.get(0).getId());
        SERVICE_CONTA.remove(listaContasTestes.get(0));
        SERVICE_CATEGORIA.remove(listaLancamentos.get(0).getCategoria());
      } else {
        assertTrue("Não é possível realizar o teste porque não existem lançamentos testáveis",
                false);
      }
    } else {
      assertTrue("Não é possível realizar o teste porque não existem contas testáveis", false);
    }
  }

  /**
   * Test of getById method, of class LancamentoService.
   */
  @Test
  public void testGetById() {
    List<Conta> listaContasTestes = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);

    if (listaContasTestes.size() > 0) {
      List<Lancamento> listaLancamentos = SERVICE.getByConta(listaContasTestes.get(0), null, null);

      if (listaLancamentos.size() > 0) {
        Lancamento lancamentoExp = listaLancamentos.get(0);
        Lancamento lancamentoResult = SERVICE.getById(lancamentoExp.getId());

        assertEquals("Lançamento diferente do esperado", lancamentoExp, lancamentoResult);
      } else {
        assertTrue("Não é possível realizar o teste porque não existem lançamentos testáveis",
                false);
      }
    } else {
      assertTrue("Não é possível realizar o teste porque não existem contas testáveis", false);
    }
  }

  /**
   * Test of getByConta method, of class LancamentoService.
   */
  @Test
  public void testGetByConta() {
    List<Conta> listaContasTestes = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);

    if (listaContasTestes.size() > 0) {
      List<Lancamento> listaLancamentos = SERVICE.getByConta(listaContasTestes.get(0), null, null);

      assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);
    } else {
      assertTrue("Não é possível realizar o teste porque não existem contas testáveis", false);
    }
  }

  /**
   * Test of getByContaTipo method, of class LancamentoService.
   */
  @Test
  public void testGetByContaTipoEnum() {
    List<Conta> listaContasTestes = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);

    if (listaContasTestes.size() > 0) {
      List<Lancamento> listaLancamentos = SERVICE.getByContaTipo(listaContasTestes.get(0),
              TIPO_LANCAMENTO_TESTES, null, null);

      assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);
    } else {
      assertTrue("Não é possível realizar o teste porque não existem contas testáveis", false);
    }
  }

  /**
   * Test of getByContaTipo method, of class LancamentoService.
   */
  @Test
  public void testGetByContaTipoCodigo() {
    List<Conta> listaContasTestes = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);

    if (listaContasTestes.size() > 0) {
      List<Lancamento> listaLancamentos = SERVICE.getByContaTipo(listaContasTestes.get(0),
              CODIGO_TIPO_LANCAMENTO_TESTES, null, null);

      assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);
    } else {
      assertTrue("Não é possível realizar o teste porque não existem contas testáveis", false);
    }
  }

  /**
   * Test of getByContaCategoria method, of class LancamentoService.
   */
  @Test
  public void testGetByContaCategoria() {
    List<Conta> listaContasTestes = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);
    List<Categoria> listaCategoriasTestes = SERVICE_CATEGORIA.getByNome(NOME_CATEGORIA_TESTES);

    if ((listaContasTestes.size() > 0) && (listaCategoriasTestes.size() > 0)) {
      List<Lancamento> listaLancamentos = SERVICE.getByContaCategoria(listaContasTestes.get(0),
              listaCategoriasTestes.get(0), null, null);

      assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);
    } else {
      assertTrue("Não é possível realizar o teste porque não existem categorias testáveis", false);
    }
  }

  /**
   * Test of getByContaTipoCategoria method, of class LancamentoService.
   */
  @Test
  public void testGetByContaTipoEnumCategoria() {
    Categoria novaCategoriaLancamento = new Categoria();

    novaCategoriaLancamento.setNome(NOME_CATEGORIA_TESTES);
    novaCategoriaLancamento.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    novaCategoriaLancamento.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    novaCategoriaLancamento = SERVICE_CATEGORIA.persist(novaCategoriaLancamento);

    Conta novaContaLancamento = new Conta();

    novaContaLancamento.setNome(NOME_CONTA_TESTES);
    novaContaLancamento.setDescricao(DESCRICAO_CONTA_TESTES);
    novaContaLancamento.setCategoria(novaCategoriaLancamento);

    novaContaLancamento = SERVICE_CONTA.persist(novaContaLancamento);

    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    Lancamento novoLancamento = new Lancamento();

    String sulfixo = Calendar.getInstance().getTime().toString();

    novoLancamento.setCategoria(novaCategoriaLancamento);
    novoLancamento.setConta(novaContaLancamento);
    novoLancamento.setUsuario(novoUsuario);
    novoLancamento.setDescricao(DESCRICAO_LANCAMENTO_TESTES.concat(sulfixo));
    novoLancamento.setTipo(CODIGO_TIPO_LANCAMENTO_TESTES);
    novoLancamento.setValor(VALOR_LANCAMENTO_TESTES);
    novoLancamento.setMomento(Calendar.getInstance().getTime());

    SERVICE.persist(novoLancamento);

    List<Lancamento> listaLancamentos = SERVICE.getByContaTipoCategoria(novaContaLancamento,
            TIPO_LANCAMENTO_TESTES, novaCategoriaLancamento, null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);
  }

  /**
   * Test of getByContaTipoCategoria method, of class LancamentoService.
   */
  @Test
  public void testGetByContaTipoCodigoCategoria() {
    List<Conta> listaContasTestes = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);
    List<Categoria> listaCategoriasTestes = SERVICE_CATEGORIA.getByNome(NOME_CATEGORIA_TESTES);

    if ((listaContasTestes.size() > 0) && (listaCategoriasTestes.size() > 0)) {
      List<Lancamento> listaLancamentos = SERVICE.getByContaTipoCategoria(listaContasTestes.get(0),
              CODIGO_TIPO_LANCAMENTO_TESTES, listaCategoriasTestes.get(0), null, null);

      assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);
    } else {
      assertTrue("Não é possível realizar o teste porque não existem contas e categorias testáveis",
              false);
    }
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    List<Conta> listaContas = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);
    List<Categoria> listaCategorias = new ArrayList<>();

    listaContas.forEach((conta) -> {
      List<Lancamento> listaLancamentos = SERVICE.getByConta(conta, null, null);

      listaLancamentos.forEach((lancamento) -> {
        SERVICE.remove(lancamento);

        listaCategorias.add(lancamento.getCategoria());
      });

      SERVICE_CONTA.remove(conta);
    });

    listaCategorias.forEach(SERVICE_CATEGORIA::remove);
  }

}
