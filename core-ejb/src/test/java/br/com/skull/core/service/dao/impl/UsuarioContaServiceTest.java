package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceRemote;
import br.com.skull.core.service.dao.ContaServiceRemote;
import br.com.skull.core.service.dao.UsuarioContaServiceRemote;
import br.com.skull.core.service.dao.UsuarioServiceRemote;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.entity.impl.UsuarioConta;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;
import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;

/**
 * Classe de testes para o serviço DAO de relação entre usuário e conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class UsuarioContaServiceTest {

  private static final String NOME_USUARIO_TESTES = "__IGNORE-UsuarioTestes";
  private static final String EMAIL_NOME_USUARIO_TESTES = "testes";
  private static final String EMAIL_DOMINIO_USUARIO_TESTES = "@testes.com";
  private static final String PASSWORD_USUARIO_TESTES = "passwd";
  private static final long CODIGO_TIPO_USUARIO_TESTES = TipoUsuarioEnum.REGULAR.getCodigo();

  private static final String NOME_CONTA_TESTES = "__IGNORE-ContaTestes";
  private static final String NOME_CATEGORIA_CONTA_TESTES = "__IGNORE-CategoriaContaTestes";
  private static final String DESCRICAO_CONTA_TESTES = "Descrição conta de testes"
          + " - não utilizar esta conta";
  private static final String DESCRICAO_CATEGORIA_CONTA_TESTES = "Descrição categoria conta "
          + "de testes - não utilizar esta conta";

  private static UsuarioContaServiceRemote SERVICE;
  private static UsuarioServiceRemote SERVICE_USUARIO;
  private static ContaServiceRemote SERVICE_CONTA;
  private static CategoriaServiceRemote SERVICE_CATEGORIA;

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Instancia os serviços para o teste.
   *
   * @throws NamingException caso não encontre os beans
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (UsuarioContaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioContaService");

    SERVICE_USUARIO = (UsuarioServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioService");

    SERVICE_CONTA = (ContaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/ContaService");

    SERVICE_CATEGORIA = (CategoriaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaService");
  }

  @AfterClass
  public static void tearDownClass() {
    cleanUp();
  }

  /**
   * Test of persist method, of class UsuarioContaService.
   */
  @Test
  @Repeat(times = 3)
  public void testPersist() {
    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    String sulfixo = Calendar.getInstance().getTime().toString();

    Conta novaConta = new Conta();

    novaConta.setNome(NOME_CONTA_TESTES);
    novaConta.setDescricao(DESCRICAO_CONTA_TESTES.concat(sulfixo));

    Categoria categoriaConta = new Categoria();

    categoriaConta.setNome(NOME_CATEGORIA_CONTA_TESTES);
    categoriaConta.setDescricao(DESCRICAO_CATEGORIA_CONTA_TESTES);
    categoriaConta.setTipo(TipoCategoriaEnum.CONTA.getCodigo());

    categoriaConta = SERVICE_CATEGORIA.persist(categoriaConta);

    novaConta.setCategoria(categoriaConta);

    novaConta = SERVICE_CONTA.persist(novaConta);

    UsuarioConta usuarioContaTeste = new UsuarioConta();

    usuarioContaTeste.setConta(novaConta);
    usuarioContaTeste.setUsuario(novoUsuario);

    usuarioContaTeste = SERVICE.persist(usuarioContaTeste);

    assertTrue("Id da relação entre usuário e conta não é maior que zero",
            (usuarioContaTeste.getId() > 0));
  }

  /**
   * Test of remove method, of class UsuarioContaService.
   */
  @Test
  public void testRemovePorUsuarioConta() {
    List<Conta> listaContas = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    List<UsuarioConta> listaUsuarioContaTeste = SERVICE.getByConta(listaContas.get(0));

    assertTrue("Lista de usuários não é maior que zero", listaUsuarioContaTeste.size() > 0);

    SERVICE.remove(listaUsuarioContaTeste.get(0));
    SERVICE_USUARIO.remove(listaUsuarioContaTeste.get(0).getUsuario());
    SERVICE_CONTA.remove(listaUsuarioContaTeste.get(0).getConta());
  }

  /**
   * Test of remove method, of class UsuarioContaService.
   */
  @Test
  public void testRemovePorId() {
    List<Conta> listaContas = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    List<UsuarioConta> listaUsuarioContaTeste = SERVICE.getByConta(listaContas.get(0));

    assertTrue("Lista de usuários não é maior que zero", listaUsuarioContaTeste.size() > 0);

    SERVICE.remove(listaUsuarioContaTeste.get(0).getId());
    SERVICE_USUARIO.remove(listaUsuarioContaTeste.get(0).getUsuario());
    SERVICE_CONTA.remove(listaUsuarioContaTeste.get(0).getConta());
  }

  /**
   * Test of getById method, of class UsuarioContaService.
   */
  @Test
  public void testGetById() {
    List<Conta> listaContas = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    List<UsuarioConta> listaUsuarioContaTeste = SERVICE.getByConta(listaContas.get(0));

    assertTrue("Lista de relações entre usuários e conta não é maior que zero",
            listaUsuarioContaTeste.size() > 0);

    UsuarioConta usuarioContaTeste = SERVICE.getById(listaUsuarioContaTeste.get(0).getId());

    assertEquals("Relação entre usuário e conta diferente do esperado",
            listaUsuarioContaTeste.get(0), usuarioContaTeste);
  }

  /**
   * Test of getByConta method, of class UsuarioContaService.
   */
  @Test
  public void testGetByConta() {
    List<Conta> listaContas = SERVICE_CONTA.getByNome(NOME_CONTA_TESTES);

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    List<UsuarioConta> listaUsuarioContaTeste = SERVICE.getByConta(listaContas.get(0));

    assertTrue("Lista de relações entre usuário e conta não é maior que zero",
            listaUsuarioContaTeste.size() > 0);

    assertEquals("Conta diferente da esperada", listaContas.get(0),
            listaUsuarioContaTeste.get(0).getConta());
  }

  /**
   * Test of getByUsuario method, of class UsuarioContaService.
   */
  @Test
  public void testGetByUsuario() {
    List<Usuario> listaUsuarios = SERVICE_USUARIO.getByNome(NOME_USUARIO_TESTES);

    assertTrue("Lista de usuários não é maior que zero", listaUsuarios.size() > 0);

    List<UsuarioConta> listaUsuarioContaTeste = SERVICE.getByUsuario(listaUsuarios.get(0));

    assertTrue("Lista de relações entre usuário e conta não é maior que zero",
            listaUsuarioContaTeste.size() > 0);

    assertEquals("Usuário diferente do esperado", listaUsuarios.get(0),
            listaUsuarioContaTeste.get(0).getUsuario());
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    List<Usuario> listaUsuarios = SERVICE_USUARIO.getByNome(NOME_USUARIO_TESTES);

    listaUsuarios.forEach((usuario) -> {
      List<UsuarioConta> listaUsuariosContas = SERVICE.getByUsuario(usuario);

      listaUsuariosContas.forEach((usuarioConta) -> {
        SERVICE.remove(usuarioConta);

        SERVICE_CONTA.remove(usuarioConta.getConta());
        SERVICE_CATEGORIA.remove(usuarioConta.getConta().getCategoria());
      });

      SERVICE_USUARIO.remove(usuario);
    });
  }

}
