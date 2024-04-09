import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ascii2{

    // ASCII characters to use for converting image to ASCII art
    private static final String ASCIICHARS = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

    // ImageProcessor object to process the image
    private ImageProcessor processor;

    // Constructor that takes the full path of the image as input
    public ascii2(String imageFullPath){
        processor = new ImageProcessor(imageFullPath);
    }

    // Method to convert the brightness value to an ASCII character
    private static char convertToAscii(int brightnessValue) {
        char asciiValue;
        int asciiIndex;
        // Map the brightness value to an index in the ASCII characters string
        asciiIndex = (int) ((ASCIICHARS.length() - 1) * (brightnessValue / 255.0));
        // Get the ASCII character from the string using the index
        asciiValue = ASCIICHARS.charAt(asciiIndex);
        return asciiValue;
    }

    // ImageProcessor class to load the image and get its brightness matrix
   // ImageProcessor class to load the image and get its brightness matrix
private class ImageProcessor {

    private String imagePath;

    // Constructor which sets the image path
    public ImageProcessor(String imageFullPath){
        imagePath = imageFullPath;
    }

    // Get the brightness matrix of the image
    public int[][] getBrightnessMatrix(){
        BufferedImage image = getImage(imagePath);
        int[][] pixels = getPixelsFromImage(image);
        return setBrightnessMatrix(pixels);
    }

    // Get the BufferedImage from the given path
    private BufferedImage getImage(String imageFullPath){
        BufferedImage bufferedImage = null;
        try {
            if (imageFullPath == null) {
                throw new NullPointerException("Image full path cannot be null or empty");
            }
            bufferedImage = ImageIO.read(new File(imageFullPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    // Get the pixels of the image as a 2D int array
    private int[][] getPixelsFromImage(BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            throw new IllegalArgumentException();
        }
        int h = bufferedImage.getHeight();
        int w = bufferedImage.getWidth();
        int[][] pixels = new int[h][w];
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                pixels[j][i] = bufferedImage.getRGB(i, j);
            }
        }
        return pixels;
    }

    // Calculate the brightness matrix from the given 2D int array of pixels
    private int[][] setBrightnessMatrix(int[][] pixels) {
        int[][] avg = new int[pixels.length][pixels[0].length];
        // Iterate through the 2D int array of pixels
        for (int i = 0; i <pixels.length; ++i) {
            for (int j = 0; j < pixels[0].length; ++j) {
                // Extract the red, green and blue values of the current pixel
                int r = (pixels[i][j] >> 16) & 0xff;
                int g = (pixels[i][j] >> 8) & 0xff;
                int b = pixels[i][j] & 0xff;
                // Calculate the average brightness of the current pixel
                int average = (r + g + b) / 3;
                // Store the average brightness value in the brightness matrix
                avg[i][j] = average;
            }
        }
        return avg;
    }
}

    // Method to convert the brightness matrix to ASCII art string
    private String convertToAsciiArt(int[][] brightnessMatrix) {
        StringBuilder asciiArt = new StringBuilder();
        // Iterate over each row of the brightness matrix
        for (int[] row : brightnessMatrix) {
            // Iterate over each cell of the brightness matrix
            for (int cell : row) {
                // Convert brightness value to ASCII character and append to the ASCII art string
                asciiArt.append(convertToAscii(cell));
            }
            // Append newline character to move to the next row in the ASCII art
            asciiArt.append("\n");
        }
        return asciiArt.toString();
    }

    // Method to display ASCII art in a Swing GUI window
    public void displayAsciiArtGUI() {
        JFrame frame = new JFrame("ASCII Art");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int[][] brightnessMatrix = processor.getBrightnessMatrix();
        String asciiArtString = convertToAsciiArt(brightnessMatrix);

        JTextArea asciiArtArea = new JTextArea(asciiArtString);
        asciiArtArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 6)); // Adjust font size for better display
        asciiArtArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(asciiArtArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Main method to run the program
    public static void main(String[] args) {
        // Provide the full path of the image
        String imagePath = "C:\\Users\\Hp\\Desktop\\all subs of 4sem\\java\\java\\ascii art\\batman.jpg";
        ascii2 ascii2 = new ascii2(imagePath);
        ascii2.displayAsciiArtGUI();
    }
}
