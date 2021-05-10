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
@DiscriminatorValue(value = "0")
public class LiquidacionAmbitoPospago extends LiquidacionAmbito {
     
    public LiquidacionAmbitoPospago() {}
    
    @Override
    public String getTipoLiquidacionAmbito() {
        return "Pospago";
    }
    
}
