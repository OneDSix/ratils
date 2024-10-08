package net.onedsix.ratils.tests;

import net.onedsix.ratils.Result;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;

public class ResultTest {
    
    @Test
    public void testResult() {
        File internalText;
        try {
            internalText = new File(this.getClass().getClassLoader().getResource("test.txt").toURI());
        } catch (URISyntaxException urise) {
            return;
        }
        Result<String, Exception> readRes = readResult(internalText);
    
        // Check if the result is an error
        boolean containsError = readRes.errored();
        System.out.println("Errored?: "+containsError);
    
        // Lambda-based port of Rust's switch statement
        readRes.match(
            ok -> {
                System.out.println("Contents of ./test.txt:");
                System.out.println(ok);
            },
            err -> {
                System.out.println("Errored while reading:");
                System.out.println(err.getMessage());
            }
        );
        
        String matched = readRes.match(
            ok -> "Ok!",
            err -> "Bad!"
        );
        System.out.println(matched);
    }
    
    public static Result<String, Exception> readResult(File file) {
        try {
            // Reading a file line-by-line,
            // might throw a IOException
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) content.append(line);
            br.close();
            
            // Return an OK!
            return Result.Ok(content.toString());
        } catch (Exception e) {
            // On error, return ERR!
            return Result.Err(e);
        }
    }
    
}
