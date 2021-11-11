package com.nick;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    /**
     * Creates a new folder
     * @param path The full path that the folder will take
     */
    public static void createFolder(String path) {
        if (path.charAt(0) == '/') {
            path = path.substring(1);
        }
        File f = new File(path);
        if (f.exists()) {
            System.out.println("Found " + path + " folder");
        } else {
            if (f.mkdir()){
                System.out.println(path + " folder created successfully");
            } else {
                System.out.println("Failed to create " + path + " folder");
            }
        }
    }

    /**
     * Retrieve all the lines of a file of format ".txt"
     * @param path The full path of the file
     * @return All the lines in the file split into an array
     */
    public static String[] getLines(String path) {

        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
            String s;
            while ((s = br.readLine()) != null) {
                lines.add(s);
            }
        } catch (IOException e) {
            System.out.println("Could not retrieve lines from " + path);
        }

        String[] linesArr = new String[lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            linesArr[i] = lines.get(i);
        }

        return linesArr;

    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * Creates a new file of any type
     * @param folderPath The destination location for the file to be stored in
     * @param fileName The desired name of the file with a valid file extension (e.g. ".txt")
     * @return True if the file creation was successful
     */
    public static boolean createNewFile(String folderPath, String fileName) {
        if (!folderPath.equals("")) {
            folderPath += "/";
        }
        File file = new File(folderPath + fileName);
        if (!file.exists()) {
            boolean success = false;
            try {
                success = file.createNewFile();
            } catch(IOException e) {
                System.out.println("Could not create file " + fileName);
                e.printStackTrace();
            }
            if (success) {
                System.out.println("Created file " + fileName);
                return true;
            } else {
                System.out.println("Could not create file " + fileName);
            }
        } else {
            System.out.println(fileName + " exists");
        }
        return false;
    }

    /**
     * Replaces entire file with the extension ".txt" with given lines
     * @param path The full path of the file
     * @param data The line(s) to be appended to the file
     * @return True if the writing to file was successful
     */
    public static boolean writeToFile(String path, String[] data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)))) {
            for(String s : data) {
                bw.write(s);
                bw.newLine();
                System.out.println("Wrote " + s + " to file " + path);
            }
            System.out.println("Successfully finished writing to file " + path);
            return true;
        } catch(IOException e) {
            System.out.println("Failed writing to file " + path);
        }
        return false;
    }

    /**
     * Saves an image
     * @param path The full path that the image will take
     * @param image The image to be saved
     * @param formatExtension The type of image to be produced (png, jpg)
     * @return True if the saving was successful
     */
    public static boolean saveImage(String path, ImageIO image, String formatExtension) {
        try {
            //ImageIO.write(image ,formatExtension, new File(path));
            return true;
        } catch (Exception e) {
            System.out.println("Could not save image");
        }
        return false;
    }

    /**
     * Appends a line to the end of a file with format ".txt"
     * @param path The full path of the file
     * @param data The line of text to be written
     */
    public static void appendLine(String path, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)))) {
            bw.append(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a line from a file with extension ".txt"
     * @param path The full path of the file
     * @param line The line to be retrieved
     */
    public static String getLine(String path, int line) {

        String data = null;

        try (RandomAccessFile raf = new RandomAccessFile(path, "r")) {
            raf.seek(line);
            data = raf.readLine();
        } catch (IOException e) {
            System.out.println("Could not retrieve line " + line + " from " + path);
        }

        return data;

    }

    /**
     * Replaces specific line in file with extension ".txt"
     * @param path The full path of the file
     * @param line The number of the line to be replaced
     * @param replacement The line to be written
     */
    public static void writeAtLine(String path, int line, String replacement) {
        try (RandomAccessFile raf = new RandomAccessFile(path, "w")) {
            raf.seek(line);
            raf.write(replacement.getBytes());
        } catch (IOException e) {
            System.out.println("Could not write to " + path);
        }
    }

}
