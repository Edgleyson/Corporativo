package esse.chat.webservice.util;

import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Classe usada para "setar" o charset=UTF-8. Evita que caracteres 
 * especiais sejam exibidos incorretamente.
 */
public class ContentTypeUtil {
    private HttpHeaders httpHeaders;
    
    public ContentTypeUtil(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }
    
    public String getContentType() {
        String contentType = httpHeaders.getHeaderString(HttpHeaders.ACCEPT);
        
        if (APPLICATION_JSON.equals(contentType)) {
            contentType = APPLICATION_JSON + ";charset=UTF-8";
        } else {
            contentType = APPLICATION_XML + ";charset=UTF-8";
        }
        
        return contentType;
    } 
}
