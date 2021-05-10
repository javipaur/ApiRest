/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


/**
 *
 * @author victor
 */

public class ImageEditor {

    private BufferedImage image;
    private double[] coords;
    private static Integer MAX_WIDTH = 450;

    public ImageEditor(BufferedImage image) {
        this.image = image;
    }

    public void rotate(int angle) {
        BufferedImage newImage = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
        Graphics2D graphics = (Graphics2D) newImage.getGraphics();
        graphics.rotate(Math.toRadians(angle), newImage.getWidth() / 2, newImage.getHeight() / 2);
        graphics.translate((newImage.getWidth() - image.getWidth()) / 2, (newImage.getHeight() - image.getHeight()) / 2);
        graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        this.image = newImage;
    }
    
    public void escalar(){
        if(this.image.getWidth()>MAX_WIDTH){
            this.resize(MAX_WIDTH, ((image.getHeight()*MAX_WIDTH)/image.getWidth()));
        }
    }

    private void resize(int newW, int newH) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage bufim = new BufferedImage(newW, newH, image.getType());
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        this.image = bufim;
    }

    public BufferedImage cropImage() {
        return image.getSubimage((int)coords[0], (int)coords[1],  (int)(coords[2]-coords[0]),  (int)(coords[3]-coords[1]));
    }

    public BufferedImage getImage() {
        return image;
    }

    public double[] getCoords() {
        return coords;
    }

    public void setCoords(double[] coords) {
        this.coords = coords;
    }
    
    public ImageEditor setCoordsPrueba() {
        this.coords = new double[]{0.0,0.0,((double)image.getWidth()),((double)image.getHeight())};
        return this;
    }
        
}
