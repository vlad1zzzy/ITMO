package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class Md2Html {
    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(args[0]), StandardCharsets.UTF_8));
            String s;
            StringBuilder block = new StringBuilder();
            while ((s = input.readLine()) != null) {
                while (s != null && !s.equals("")) {
                    block.append(s).append('\n');
                    s = input.readLine();
                }
                if (block.length() > 0) {
                    block.setLength(block.length() - 1);
                    int countSharp = 0;
                    while (countSharp < block.length() && block.charAt(countSharp) == '#') {
                        countSharp++;
                    }
                    if (countSharp > 0 && block.charAt(countSharp) == ' ') {
                        result.append("<h").append(countSharp).append(">");
                        result.append(new Parser(new StringBuilder(block.substring(countSharp + 1))).toHtml());
                        result.append("</h").append(countSharp).append(">");
                    } else {
                        result.append("<p>");
                        result.append(new Parser(block).toHtml());
                        result.append("</p>");
                    }
                    result.append('\n');
                    block = new StringBuilder();
                }
            }
            result.setLength(result.length() - 1);
            try {
                try (PrintWriter output = new PrintWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(new File(args[1])), StandardCharsets.UTF_8))) {
                    output.println(result.toString());
                }
            } catch (IOException e) {
                System.err.println("Something wrong with writing: " + e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Enter your file: " + e.getMessage());
            } finally {
                input.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Something wrong with reading: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Enter your file: " + e.getMessage());
        }
    }
}