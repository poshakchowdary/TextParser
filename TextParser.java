import java.io.*;
import java.util.*;

public class TextParser {
    private Set<String> stopwords;
    private Porter porterStemmer;

    public TextParser(String stopwordFile) throws IOException {
        stopwords = new HashSet<>();
        porterStemmer = new Porter();
        loadStopwords(stopwordFile);
    }

    private void loadStopwords(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            stopwords.add(line.trim().toLowerCase());
        }
        br.close();
    }

    public List<String> parseText(String text) {
        List<String> tokens = new ArrayList<>();
        String[] words = text.split("[^a-zA-Z]+"); // Split on non-alphabetic characters

        for (String word : words) {
            if (!word.isEmpty() && !stopwords.contains(word.toLowerCase())) {
                tokens.add(porterStemmer.stripAffixes(word.toLowerCase())); // Apply stemming
            }
        }
        return tokens;
    }

    public void processFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            List<String> parsedTokens = parseText(line);
            System.out.println("Parsed Tokens: " + parsedTokens);
        }
        reader.close();
    }

    public static void main(String[] args) throws IOException {
        String stopwordFile = "stopwordlist.txt";
        TextParser parser = new TextParser(stopwordFile);
        
        // Process the full text file
        String inputFile = "input.txt";
        parser.processFile(inputFile);
    }
}


