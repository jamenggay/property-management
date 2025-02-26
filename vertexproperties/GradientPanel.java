/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vertexproperties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import javax.swing.JPanel;

/**
 *
 * @author Jamaine
 */
public class GradientPanel extends JPanel{
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Define gradient colors
        Color color1 = new Color(0x737373); // Gray
        Color color2 = new Color(0x000000); // Black

        // Create a linear gradient from top to bottom
        Paint gradient = new LinearGradientPaint(0, 0, getWidth(), 0, // Width-based gradient
                new float[]{0.0f, 1.0f}, new Color[]{color1, color2});

        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
    
}
