import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ascii1 {

    public static void main(String[] args) throws IOException {

        String text = "priyanshu";
        int width = 100;
        int height = 30;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE); // Set background color to black
        g.fillRect(0, 0, width, height); // Fill the background with black
        g.setColor(Color.BLACK); // Set text color to white
        g.setFont(new Font("SansSerif", Font.BOLD, 20)); // Increase font size for better readability

        FontMetrics fm = g.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        int y = ((height - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(text, x, y);

        for (int i = 0; i < height; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < width; j++) {
                Color color = new Color(image.getRGB(j, i));
                int luminance = (int) (0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue());
                char c = (luminance > 128) ? ' ' : '#'; // Use '#' for dark pixels and ' ' for light pixels
                sb.append(c);
            }
            System.out.println(sb);
        }
    }
}
