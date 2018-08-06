package br.com.skull.core.service.dao.entity.impl;

import br.com.skull.core.service.dao.entity.IEntity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe que representa a entidade "Usuário".
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", 
            query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findById", 
            query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.findByNome", 
            query = "SELECT u FROM Usuario u WHERE u.nome = :nome"),
    @NamedQuery(name = "Usuario.findByNomeAproximado", 
            query = "SELECT u FROM Usuario u WHERE u.nome LIKE :nome"),
    @NamedQuery(name = "Usuario.findByEmail", 
            query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByTipo", 
            query = "SELECT u FROM Usuario u WHERE u.tipo = :tipo")})
public class Usuario implements IEntity, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 300)
  @Column(name = "NOME")
  private String nome;
  @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9]"
          + "(?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
          message = "Formato de e-mail inválido")
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 300)
  @Column(name = "EMAIL")
  private String email;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PASSWORD")
  private String password;
  @Basic(optional = true)
  @Column(name = "ULTIMO_LOGON")
  @Temporal(TemporalType.TIMESTAMP)
  private Date ultimoLogon;
  @Basic(optional = false)
  @NotNull
  @Column(name = "MANUTENCAO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date manutencao;
  @Basic(optional = false)
  @NotNull
  @Column(name = "TIPO")
  private long tipo;

  /**
   * Prepara entidade para persistência.
   */
  @PrePersist
  public void setupPersist() {
    this.manutencao = Calendar.getInstance().getTime();
  }

  public Usuario() {
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long value) {
    this.id = value;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String value) {
    this.nome = value;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String value) {
    this.email = value;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String value) {
    this.password = value;
  }

  public Date getUltimoLogon() {
    return ultimoLogon;
  }

  public void setUltimoLogon(Date value) {
    this.ultimoLogon = value;
  }

  public Date getManutencao() {
    return manutencao;
  }

  public void setManutencao(Date value) {
    this.manutencao = value;
  }

  public long getTipo() {
    return tipo;
  }

  public void setTipo(long value) {
    this.tipo = value;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Usuario)) {
      return false;
    }
    Usuario other = (Usuario) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "br.com.skull.core.model.Usuario[ id=" + id + " | nome="
            + nome + " | email=" + email + " ]";
  }

}
