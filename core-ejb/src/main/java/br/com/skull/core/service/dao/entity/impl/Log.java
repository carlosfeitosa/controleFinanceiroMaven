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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe que representa a entidade "Log".
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Entity
@Table(name = "LOG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Log.findById", 
          query = "SELECT l FROM Log l WHERE l.id = :id"),
    @NamedQuery(name = "Log.findByMomento", 
          query = "SELECT l FROM Log l "
                  + "WHERE l.momento BETWEEN :inicioMomento AND :terminoMomento"),
    @NamedQuery(name = "Log.findByCategoriaMomento", 
          query = "SELECT l FROM Log l "
                  + "WHERE l.categoria = :categoria "
                  + "AND l.momento BETWEEN :inicioMomento AND :terminoMomento"),
    @NamedQuery(name = "Log.findByUsuarioMomento", 
          query = "SELECT l FROM Log l "
                  + "WHERE l.usuario = :usuario "
                  + "AND l.momento BETWEEN :inicioMomento AND :terminoMomento"),
    @NamedQuery(name = "Log.findByCategoriaUsuarioMomento", 
          query = "SELECT l FROM Log l "
                  + "WHERE l.usuario = :usuario "
                  + "AND l.categoria = :categoria "
                  + "AND l.momento BETWEEN :inicioMomento AND :terminoMomento")})
public class Log implements IEntity, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 3000)
  @Column(name = "DESCRICAO")
  private String descricao;
  @Basic(optional = false)
  @NotNull
  @Column(name = "MOMENTO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date momento;
  @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID")
  @NotNull
  @ManyToOne(optional = false)
  private Categoria categoria;
  @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
  @NotNull
  @ManyToOne
  private Usuario usuario;
  
  /**
   * Prepara entidade para persistência.
   */
  @PrePersist
  public void setupPersist() {
    this.momento = Calendar.getInstance().getTime();
  }

  public Log() {
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long value) {
    this.id = value;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String value) {
    this.descricao = value;
  }

  public Date getMomento() {
    return momento;
  }

  public void setMomento(Date value) {
    this.momento = value;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria value) {
    this.categoria = value;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario value) {
    this.usuario = value;
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
    if (!(object instanceof Log)) {
      return false;
    }
    Log other = (Log) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "br.com.skull.core.model.Log[ id=" + id + " | descrição=" + descricao
            + " | categoria=" + categoria.toString()
            + " | usuario=" + usuario.toString()
            + " | momento=" + momento.toString() + " ]";
  }
  
}
