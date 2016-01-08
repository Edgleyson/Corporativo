package esse.chat.modelo;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name="TB_EMAIL")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Email extends Entidade implements Serializable {

    @Expose
    @XmlAttribute(required = true)
    @NotNull
    @org.hibernate.validator.constraints.Email
    @Column(name="TXT_EMAIL", length = 150)
    private String email;
    
    public Email() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Email)) {
            return false;
        }
        Email other = (Email) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        } else if (this.id == null && other.id == null) {
            return this.email.equals(other.email);
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "EmailUsuario{" + "id=" + id + ", email=" + email + '}';
    }
    
}
