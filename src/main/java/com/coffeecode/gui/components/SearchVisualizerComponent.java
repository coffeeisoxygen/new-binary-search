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

    // Replace fixed constants with dynamic calculations
    private static final int BOXES_PER_ROW = 10;
    private static final int MIN_BOX_SIZE = 30;
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

        // Calculate dimensions
        int rows = (int) Math.ceil(indices.length / (double)BOXES_PER_ROW);
        int availableWidth = getWidth() - (2 * VERTICAL_PADDING);
        int availableHeight = getHeight() - (2 * VERTICAL_PADDING);

        // Calculate box size
        int boxWidth = Math.max(MIN_BOX_SIZE, 
            (availableWidth - (BOXES_PER_ROW - 1) * BOX_GAP) / BOXES_PER_ROW);
        int boxHeight = Math.max(MIN_BOX_SIZE,
            (availableHeight - (rows - 1) * BOX_GAP) / rows);
        
        // Use smaller of width/height to keep boxes square
        int boxSize = Math.min(boxWidth, boxHeight);

        // Draw boxes in grid
        for (int i = 0; i < indices.length; i++) {
            int row = i / BOXES_PER_ROW;
            int col = i % BOXES_PER_ROW;
            
            int x = VERTICAL_PADDING + col * (boxSize + BOX_GAP);
            int y = VERTICAL_PADDING + row * (boxSize + BOX_GAP);
            
            drawBox(g2d, x, y, indices[i], getBoxColor(i), boxSize);
        }

        // Update pointer labels for binary search
        if (currentStep instanceof BinarySearchStep bStep) {
            drawPointerLabels(g2d, VERTICAL_PADDING, boxSize, bStep);
        }
    }

    private void setupGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private void drawPointerLabels(Graphics2D g2d, int startX, int boxSize, BinarySearchStep bStep) {
        g2d.setFont(new Font("SansSerif", Font.BOLD, boxSize/3));
        
        // Calculate positions based on grid layout
        int lowIndex = bStep.getLow();
        int highIndex = bStep.getHigh();
        int midIndex = bStep.getCurrentIndex();

        drawLabel(g2d, "low", 
            startX + (lowIndex % BOXES_PER_ROW) * (boxSize + BOX_GAP),
            startX + (lowIndex / BOXES_PER_ROW) * (boxSize + BOX_GAP) - 5,
            LOW_COLOR);

        // Similar updates for high and mid labels...
    }

    private void drawLabel(Graphics2D g2d, String text, int x, int y, Color color) {
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        x = x + (MIN_BOX_SIZE - textWidth) / 2;

        g2d.setColor(color);
        g2d.drawString(text, x, y);
    }

    private void drawBox(Graphics2D g2d, int x, int y, int value, Color color, int size) {
        // Draw box background
        g2d.setColor(color);
        g2d.fillRect(x, y, size, size);
        
        // Draw border
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, size, size);

        // Draw index number
        drawCenteredText(g2d, String.valueOf(value), x, y, size);
    }

    private void drawCenteredText(Graphics2D g2d, String text, int x, int y, int size) {
        g2d.setFont(new Font("SansSerif", Font.PLAIN, size/3));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (size - fm.stringWidth(text)) / 2;
        int textY = y + (size + fm.getAscent()) / 2;
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
