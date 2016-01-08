package esse.chat.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resposta {
    @XmlAttribute
    private boolean sucesso;
    @XmlElement
    private String mensagem;

    public Resposta() {
        
    }
    
    public Resposta(boolean  sucesso, String mensagem) {
        setMensagem(mensagem);
        setSucesso(sucesso);
    }
    
    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }  
}
