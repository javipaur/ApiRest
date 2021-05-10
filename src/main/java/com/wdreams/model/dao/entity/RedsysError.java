package com.wdreams.model.dao.entity;

import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "redsyserror")
@DataTransferObject
public class RedsysError {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Column(name = "hora")
    private Date hora;
    @Column(name = "code")
    private String code;
    @Column(name = "message")
    private String message;

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RedsysError(Date hora, String code, String message) {
        this.hora = hora;
        this.code = code;
        this.message = message;
    }
}
