package seedu.address.searcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * <h1>Search Class</h1>
 *
 * Class provides simple search function which takes in
 * a module code and outputs simple module information
 * taken from NUSMODs
 */
public class Search {
    /**
     * Function takes in module code and returns Module Object.
     * For more info on Module Object, look at module Class.
     * @param moduleCode input Module Code all caps
     * @return module
     */
    public static Module findModule(String moduleCode) {
        String fileName = moduleCode + ".txt";
        File tempFile = new File("." + File.separator + "src" + File.separator + "main"
                + File.separator + "java" + File.separator + "seedu" + File.separator
                + "address" + File.separator + "searcher" + File.separator
                + "cache" + File.separator + fileName);

        String output = "";

        try {
            BufferedReader myReader = new BufferedReader(new FileReader(tempFile));
            String tempString;
            while ((tempString = myReader.readLine()) != null) {
                output = tempString;
            }
        } catch (FileNotFoundException e) {
            String httpsUrl = "https://api.nusmods.com/v2/2019-2020/modules/" + moduleCode + ".json";
            URL url;
            try {
                url = new URL(httpsUrl);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                output = print_content(con);
            } catch (IOException d) {
                d.printStackTrace();
            }

            try {
                BufferedWriter myWriter = new BufferedWriter(new FileWriter(tempFile));
                myWriter.write(output);
                myWriter.close();
            } catch (IOException f) {
                f.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Module(output);
    }

    /**
     * Function to get HTTP information
     * @param con just java stuff
     * @return Raw HTTP info in string
     */
    private static String print_content(HttpsURLConnection con) {
        String output = "";
        if (con != null) {
            try {
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                String input;

                while ((input = br.readLine()) != null) {
                    output = input;
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output + "\n";
    }
}
