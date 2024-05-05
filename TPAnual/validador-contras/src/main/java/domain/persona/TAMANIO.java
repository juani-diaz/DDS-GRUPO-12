package domain.persona;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TAMANIO extends Requisitos {

    public boolean evaluarContrasena(String contra){
        if(contra.length() > 7 && contra.length() < 64){
            return true;
        }else{
            return false;
        }
    }
}
