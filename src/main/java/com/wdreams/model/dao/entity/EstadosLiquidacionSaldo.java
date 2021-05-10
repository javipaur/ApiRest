package com.wdreams.model.dao.entity;




import javax.persistence.*;

@Entity
@Table(name = "estadosliquidacionsaldo")
public class EstadosLiquidacionSaldo {

    public static enum tiposEstados{
        PENDIENTE(1),
        REALIZADA(2);

        Integer codigo;

        public Integer getCodigo() {
            return codigo;
        }

        public void setCodigo(Integer codigo) {
            this.codigo = codigo;
        }

        tiposEstados(int i) {
            this.codigo = i;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "nombre")
    protected String nombre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadosLiquidacionSaldo(){}


}
