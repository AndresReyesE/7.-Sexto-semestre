import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gerardoayala on 3/3/16.
 */
public interface Controller extends ActionListener
{
    public Object obtieneDatoDelModel(int indice);
    public Object obtieneDatoDelView();
    public void actualizaElView();
    public void solicitaActualizacionDelModel(String accion);
    public void actionPerformed(ActionEvent evento);

}//end Controller
