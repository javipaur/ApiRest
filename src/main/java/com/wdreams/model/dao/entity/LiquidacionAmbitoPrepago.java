/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.model.dao.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author rosa
 */
@Entity
//@Table(name = "LiquidacionesAmbito")
@DiscriminatorValue(value = "1")
public class LiquidacionAmbitoPrepago extends LiquidacionAmbito {
     
    public LiquidacionAmbitoPrepago() {}
    
    @Override
    public String getTipoLiquidacionAmbito() {
        return "Prepago";
    }
}
