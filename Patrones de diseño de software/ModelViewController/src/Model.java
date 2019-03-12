/**
 * Created by gerardoayala on 3/3/16.
 */
public interface Model
{
    public void cargaDatosDelRepositorioALaEstructura();
    public void salvaDatosDeLaEstructuraAlRepositorio();
    public void agregaDatosALaEstructura(int indice, Object dato);
    public void modificaDatosEnLaEstructura(int indice, Object dato);
    public void eliminaDatosDeLaEstructura(int indice);
    public void ordenaLaEstructura();
    public double procesa(int indice);
    public boolean hayDatos();

}//end Model
