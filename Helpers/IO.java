package Helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class IO {
    private static Scanner s = new Scanner(System.in);
    private static Frame frame;
    private static JPanel pane;
    private static BufferedImage image;

    public static void printImage(Color[][] img, int width) {
        initImage(img, width);
    }

    public static void initImage(Color[][] img, int width) {
        frame = new JFrame();
        int type = BufferedImage.TYPE_INT_RGB;
        image = new BufferedImage(img[0].length, img.length, type);


        //Scale
        double resizeFactor = width / img[0].length;
        int finalWidth = (int) Math.floor(image.getWidth() * resizeFactor);
        int finalHeight = (int) Math.floor(image.getHeight() * resizeFactor);
        pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(scaleImage(image, finalWidth, finalHeight), 0, 0, null);
            }
        };


        frame.add(pane);
        frame.setSize(finalWidth + 20, finalHeight + 40);
        frame.setVisible(true);

        redrawImage(img, width);
    }

    public static void redrawImage(Color[][] img, int width) {
        if (frame == null) {
            initImage(img, width);
        }
        for (int x = 0; x < img.length; x++) {
            for (int y = 0; y < img[x].length; y++) {
                image.setRGB(y, x, img[x][y].getRGB());
            }
        }
        frame.repaint();
    }

    public static void print(Object[][] arr) {
        for (Object[] row : arr) {
            print(row);
        }
    }

    public static void print(Object[] arr) {
        for (Object o : arr) {
            if (o == null) {
                System.out.print("*");
            } else {
                System.out.print(o);
            }
        }
        System.out.println();
    }

    public static String[][] get2DStringArraySplitByNewline(String s) {
        String[] lines = s.replaceAll("\\r", "").split("\n");
        String[][] res = new String[lines.length][lines[0].length()];
        int width = 0;
        for (int i = 0; i < lines.length; i++) {
            res[i] = lines[i].split("");
        }
        return res;
    }

    public static String[][] get2DStringArray(String s, int width, int height) {
        String[][] res = new String[height][width];
        //s = s.replaceAll("\n", "").replaceAll("\r", "");
        String[] c = s.split("");
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            if (i == 31) {
                System.out.println(c[i]);
            }
            for (int j = 0; j < res[i].length; j++) {
                if (index < c.length) {
                    res[i][j] = c[index++];
                } else {
                    res[i][j] = "";
                }
            }
        }
        return res;
    }

    public static String getInput() {
        return getInput("input.txt");
    }

    /**
     * Test, wether a file exists
     *
     * @param filename Name of the file to test as relative path from root-directory
     * @return true, if file exists; false else
     */
    public static boolean fileExists(String filename) {
        try {
            new FileReader(filename);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    /**
     * Creates empty file, if it does not exist
     *
     * @param filename Name of the file to create as relative path from root-directory
     */
    public static void createFile(String filename) {
        if (!fileExists(filename)) {
            try {
                new File(filename).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeFile(String input, String filename) {
        createFile(filename);
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(input);
            myWriter.close();
        } catch (IOException e) {
        }
    }

    /**
     * Get the content of a file and create an empty file, if no file exists
     *
     * @param filename Name of the file to read from as relative path from root-directory
     * @return Content of the file
     */
    public static String getFile(String filename) {
        BufferedReader br;
        String everything = "";
        createFile(filename);
        try {
            br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return everything;
    }

    /**
     * Get content of a file in ./src/Inputs/${filename}
     *
     * @param filename Filename asd.fd
     * @return Content of the file
     */
    public static String getInput(String filename) {
        return getFile("./../inputs/" + filename).replace("\r", "");
    }

    private static BufferedImage scaleImage(BufferedImage original, int newWidth, int newHeight) {
        BufferedImage resized = new BufferedImage(newWidth, newHeight, original.getType());
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(original, 0, 0, newWidth, newHeight, 0, 0, original.getWidth(),
                original.getHeight(), null);
        g.dispose();
        return resized;
    }
}
