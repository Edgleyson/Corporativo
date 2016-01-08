package esse.chat.modelo;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="TB_SALA_DE_CHAT")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SalaDeChat extends Entidade implements Serializable {
    @Expose
    @XmlAttribute(required = true)
    @NotBlank 
    @Range (max = 255)
    @Column (name = "TXT_DESCRICAO")
    private String descricao;
    @Expose
    @Valid
    @OneToOne(mappedBy="chat", optional = false)
    private Disciplina disciplina;

    public SalaDeChat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SalaDeChat)) {
            return false;
        }
        SalaDeChat other = (SalaDeChat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SalaDeChat{" + "id=" + id + ", descricao=" + descricao + '}';
    }           
}
