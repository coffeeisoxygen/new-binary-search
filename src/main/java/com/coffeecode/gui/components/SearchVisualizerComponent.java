package com.coffeecode.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.coffeecode.model.search.step.BinarySearchStep;
import com.coffeecode.model.search.step.LinearSearchStep;
import com.coffeecode.model.search.step.SearchStep;

public class SearchVisualizerComponent extends JPanel {

    // Constants for box dimensions and colors
    private static final int BOX_HEIGHT = 40;
    private static final int BOX_WIDTH = 40;
    private static final int BOX_GAP = 5;
    private static final int VERTICAL_PADDING = 20;

    // Colors for different search states
    private static final Color MID_COLOR = new Color(46, 204, 113);    // Green
    private static final Color LOW_COLOR = new Color(231, 76, 60);     // Red
    private static final Color HIGH_COLOR = new Color(52, 152, 219);   // Blue
    private static final Color LINEAR_COLOR = new Color(241, 196, 15); // Yellow
    private static final Color RANGE_COLOR = new Color(240, 240, 240); // Light gray
    private static final Color CHECKED_COLOR = new Color(245, 245, 245);// Very light gray
    private static final Color DEFAULT_COLOR = Color.WHITE;

    private int[] indices;
    private SearchStep currentStep;

    public SearchVisualizerComponent() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 120));
    }

    public void setDictionarySize(int size) {
        indices = new int[size];
        for (int i = 0; i < size; i++) {
            indices[i] = i;
        }
        repaint();
    }

    public void updateStep(SearchStep step) {
        this.currentStep = step;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (indices == null) return;

        Graphics2D g2d = (Graphics2D) g;
        setupGraphics(g2d);

        // Calculate starting position to center the visualization
        int totalWidth = indices.length * (BOX_WIDTH + BOX_GAP) - BOX_GAP;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = (getHeight() - BOX_HEIGHT) / 2;

        // Draw boxes
        for (int i = 0; i < indices.length; i++) {
            int x = startX + i * (BOX_WIDTH + BOX_GAP);
            drawBox(g2d, x, startY, indices[i], getBoxColor(i));
        }

        // Draw labels for binary search pointers
        if (currentStep instanceof BinarySearchStep bStep) {
            drawPointerLabels(g2d, startX, startY, bStep);
        }
    }

    private void setupGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private void drawPointerLabels(Graphics2D g2d, int startX, int startY, BinarySearchStep bStep) {
        g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        // Draw low pointer
        String lowLabel = "low";
        int lowX = startX + bStep.getLow() * (BOX_WIDTH + BOX_GAP);
        drawLabel(g2d, lowLabel, lowX, startY - 5, LOW_COLOR);

        // Draw high pointer
        String highLabel = "high";
        int highX = startX + bStep.getHigh() * (BOX_WIDTH + BOX_GAP);
        drawLabel(g2d, highLabel, highX, startY - 5, HIGH_COLOR);

        // Draw mid pointer
        String midLabel = "mid";
        int midX = startX + bStep.getCurrentIndex() * (BOX_WIDTH + BOX_GAP);
        drawLabel(g2d, midLabel, midX, startY + BOX_HEIGHT + 15, MID_COLOR);
    }

    private void drawLabel(Graphics2D g2d, String text, int x, int y, Color color) {
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        x = x + (BOX_WIDTH - textWidth) / 2;

        g2d.setColor(color);
        g2d.drawString(text, x, y);
    }

    private void drawBox(Graphics2D g2d, int x, int y, int value, Color color) {
        // Draw box background
        g2d.setColor(color);
        g2d.fillRect(x, y, BOX_WIDTH, BOX_HEIGHT);
        
        // Draw border
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, BOX_WIDTH, BOX_HEIGHT);

        // Draw index number
        drawCenteredText(g2d, String.valueOf(value), x, y);
    }

    private void drawCenteredText(Graphics2D g2d, String text, int x, int y) {
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (BOX_WIDTH - fm.stringWidth(text)) / 2;
        int textY = y + (BOX_HEIGHT + fm.getAscent()) / 2;
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, textX, textY);
    }

    private Color getBoxColor(int index) {
        if (currentStep == null) {
            return DEFAULT_COLOR;
        }

        if (currentStep instanceof BinarySearchStep bStep) {
            if (index == bStep.getCurrentIndex()) return MID_COLOR;
            if (index == bStep.getLow()) return LOW_COLOR;
            if (index == bStep.getHigh()) return HIGH_COLOR;
            if (index > bStep.getLow() && index < bStep.getHigh()) return RANGE_COLOR;
        } else if (currentStep instanceof LinearSearchStep lStep) {
            if (index == lStep.getCurrentIndex()) return LINEAR_COLOR;
            if (index < lStep.getCurrentIndex()) return CHECKED_COLOR;
        }
        return DEFAULT_COLOR;
    }
}
