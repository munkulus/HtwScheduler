package org.munkulus.htwscheduler.repository;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ColorRepository {
    
    private final Color tomato = new Color(255, 99, 71);
    private final Color coral = new Color(255, 127, 80);
    private final Color orange = new Color(255, 165, 0);
    private final Color goldenRod = new Color(218, 165, 32);
    private final Color khaki = new Color(240, 230, 140);
    private final Color yellow = new Color(255, 255, 0);
    private final Color yellowGreen = new Color(154, 205, 50);
    private final Color lawnGreen = new Color(124, 252, 0);
    private final Color mediumSpringGreen = new Color(0, 250, 154);
    private final Color lightSeaGreen = new Color(32, 178, 170);
    private final Color aqua = new Color(0, 255, 255);
    private final Color aquaMarine = new Color(127, 255, 212);
    private final Color cornFlowerBlue = new Color(100, 149, 237);
    private final Color skyBlue = new Color(135, 206, 235);
    private final Color royalBlue = new Color(65, 105, 225);
    private final Color blueViolet = new Color(138, 43, 226);
    private final Color plum = new Color(221, 160, 221);
    private final Color hotPink = new Color(255, 105, 180);
    private final Color wheat = new Color(245, 222, 179);
    private final Color sandyBrown = new Color(244, 164, 96);
    private final Color aliceBlue = new Color(240, 248, 255);
    private final Color lightSteelBlue = new Color(176, 196, 222);
    
    private List<Color> colors;
    private List<Color> usedColors;
    
    public ColorRepository() {
        colors = new ArrayList<>();
        usedColors = new ArrayList<>();
        addColors();
    }
    
    public Color getFreeColor() {
        int ok = 0;
        int counter = 0;
        int index = getRandomNumber(1, colors.size());
        Color c = colors.get(index);
        do {
            if (counter == 10) {
                ok = 1;
                c = createNewRandomColor();
                colors.add(c);
                break;
            }
            if (usedColors.contains(c)) {
                index = getRandomNumber(1, colors.size());
                c = colors.get(index);
                counter++;
            } else {
                ok = 1;
            }
            
        } while (ok != 1);
        usedColors.add(c);
        return c;
    }
    
    public void addUsedColor(Color c) {
        if (colors.contains(c)) {
            usedColors.add(c);
        }        
    }
    
    public void removeColor(Color c) {
        usedColors.remove(c);
    }
    
    public void removeAllColors() {
        usedColors.clear();
    }
    
    private Color createNewRandomColor() {
        int r = getRandomNumber(60, 250);
        int g = getRandomNumber(60, 250);
        int b = getRandomNumber(60, 250);
        Color c = new Color(r, g, b);
        return c;
    }
    
    private void addColors() {
        colors.add(tomato);
        colors.add(coral);
        colors.add(orange);        
        colors.add(goldenRod);
        colors.add(khaki);
        colors.add(yellow);
        colors.add(yellowGreen);
        colors.add(lawnGreen);
        colors.add(mediumSpringGreen);
        colors.add(lightSeaGreen);
        colors.add(aqua);
        colors.add(aquaMarine);
        colors.add(cornFlowerBlue);
        colors.add(skyBlue);
        colors.add(royalBlue);
        colors.add(blueViolet);
        colors.add(plum);
        colors.add(hotPink);
        colors.add(wheat);
        colors.add(sandyBrown);
        colors.add(aliceBlue);
        colors.add(lightSteelBlue);
        
    }
    
    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
    
}
