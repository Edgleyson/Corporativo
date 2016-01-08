package esse.chat.modelo;

import com.google.gson.annotations.Expose;
import esse.chat.controle.ValidaFone;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name="TB_FONE")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Fone extends Entidade implements Serializable {
    @Expose
    @XmlAttribute(required = true)
    @NotNull
    @ValidaFone
    @Size (min = 13, max = 14)
    @Column(name="TXT_FONE")
    private String fone;
    
    public Fone() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fone)) {
            return false;
        }
        Fone other = (Fone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        } else if (this.id == null && other.id == null) {
            return this.fone.equals(other.fone);
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "FoneUsuario{" + "id=" + id + ", fone=" + fone + '}';
    }
    
}
