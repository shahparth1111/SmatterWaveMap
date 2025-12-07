package app.smatter.smatterwavemap;

import javax.swing.*;

 
import jspectrumanalyzer.core.DatasetSpectrum;

import java.awt.*;
import java.util.Arrays;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Custom JPanel to visualize the frequency spectrum.
 */
public class SpectrumPanel extends JPanel {

    private double[] spectrum = new double[0];
    private final int MAX_BAR_HEIGHT = 150; 
    private final int BAR_WIDTH = 4;        
    public static DatasetSpectrum ds;
    public static AudioPlayerFull a;
    public static DatasetSpectrum uData;
    
    public SpectrumPanel() {
        setPreferredSize(new Dimension(800, 200));
        setBackground(Color.DARK_GRAY);
    }

    /**
     * Updates the spectrum data and triggers a repaint.
     */
    public void updateSpectrum(double[] magnitudes) {
        this.spectrum = Arrays.copyOf(magnitudes, magnitudes.length);
        ds.spectrumC = toFloatArray(magnitudes); 
        SwingUtilities.invokeLater(this::repaint);
    }
    
    public static float[] toFloatArray(double[] arr) {
        float[] out = new float[arr.length];
        for (int i = 0; i < arr.length; i++) {
            out[i] = (float) arr[i];
        }
        return out;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (spectrum.length == 0) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        
        int x = 0;
        int panelHeight = getHeight();

        // 

        for (double magnitude : spectrum) {
            // Convert magnitude to dB for a perceptual scale
            double db = 20.0 * Math.log10(magnitude);
            
            // Normalize the dB value (e.g., map -60dB to 0 height, 0dB to MAX_BAR_HEIGHT)
            double normalizedHeight = (db + 60.0) / 60.0;
            int barHeight = (int) (normalizedHeight * MAX_BAR_HEIGHT);
            
            // Clamp the height
            barHeight = Math.max(0, Math.min(barHeight, MAX_BAR_HEIGHT));

            int y = panelHeight - barHeight;
            g2d.fillRect(x, y, BAR_WIDTH, barHeight);

            x += BAR_WIDTH + 1;
            
            if (x > getWidth()) break;
        }
    }
}