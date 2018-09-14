package noctis.canox.proyectonoctis.Clases;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ingresos {
    private String nombre;
    private Double dinero;
    private Date fecha;

    public Ingresos(String nombre, Double dinero, Date fecha) {
        this.nombre = nombre;
        this.dinero = dinero;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getDinero() {
        return dinero;
    }

    public void setDinero(Double dinero) {
        this.dinero = dinero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        DateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
        DecimalFormat df = new DecimalFormat("0.00");
        String f="";
        try{
            f =formatter2.format(fecha);
        }catch (Exception e){

        }
        return
                "Nombre=" + nombre + "\n"+
                        "Dinero=" + df.format(dinero) + "\n"+
                        "Fecha=" + f ;
    }
}
