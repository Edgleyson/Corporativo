package esse.chat.acesso;

import esse.chat.modelo.Disciplina;
import esse.chat.modelo.Entidade;
import esse.chat.modelo.Usuario;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name = "TB_GRUPO")
@NamedQueries({
    @NamedQuery(name = Grupo.GRUPO_POR_NOME, query = "SELECT g FROM Grupo g WHERE g.nome = ?1")})
@XmlRootElement
public class Grupo extends Entidade implements Serializable {
    public static final String GRUPO_POR_NOME = "GrupoPorNome";
    public static final String USUARIO = "usr";
    public static final String ADMINISTRADOR = "admin";
    @NotBlank
    @Size(max = 45)
    @Column(name = "TXT_NOME")
    private String nome;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
