package domain.contra;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public abstract class  Requisitos {
    abstract boolean evaluarContrasena(String contra);
}
