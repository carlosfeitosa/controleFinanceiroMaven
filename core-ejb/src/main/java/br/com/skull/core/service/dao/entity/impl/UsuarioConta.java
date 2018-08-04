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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe que representa a entidade "UsuarioConta" (vinculação de usuário com conta).
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Entity
@Table(name = "USUARIO_CONTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioConta.findById", 
            query = "SELECT u FROM UsuarioConta u WHERE u.id = :id"),
    @NamedQuery(name = "UsuarioConta.findByConta", 
            query = "SELECT u FROM UsuarioConta u WHERE u.conta = :conta"),
    @NamedQuery(name = "UsuarioConta.findByUsuario", 
            query = "SELECT u FROM UsuarioConta u WHERE u.usuario = :usuario")})
public class UsuarioConta implements IEntity, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  @JoinColumn(name = "ID_CONTA", referencedColumnName = "ID")
  @ManyToOne(optional = false)
  private Conta conta;
  @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
  @NotNull
  @ManyToOne
  private Usuario usuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "MANUTENCAO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date manutencao;
  
  /**
   * Prepara entidade para persistência.
   */
  @PrePersist
  public void setupPersist() {
    this.manutencao = Calendar.getInstance().getTime();
  }

  public UsuarioConta() {
  }

  public UsuarioConta(Long id) {
    this.id = id;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long value) {
    this.id = value;
  }

  public Conta getConta() {
    return conta;
  }

  public void setConta(Conta value) {
    this.conta = value;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario value) {
    this.usuario = value;
  }

  public Date getManutencao() {
    return manutencao;
  }

  public void setManutencao(Date value) {
    this.manutencao = value;
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
    if (!(object instanceof UsuarioConta)) {
      return false;
    }
    UsuarioConta other = (UsuarioConta) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "br.com.skull.core.model.UsuarioConta[ id=" + id 
            + " | conta=" + conta.toString() + " ]";
  }
  
}
