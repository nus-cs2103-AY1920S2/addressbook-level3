package seedu.address.searcher;

import java.io.BufferedReader;
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
     * just a main for me to test the search
     * @param args just java stuff
     */
    public static void main(String[] args) {
        Module mymod = new Search().findModule("CS1101S");
        System.out.println(mymod);
    }

    /**
     * Function takes in module code and returns Module Object.
     * For more info on Module Object, look at module Class.
     * @param moduleCode input Module Code all caps
     * @return module
     */
    public Module findModule(String moduleCode) {
        String httpsUrl = "https://api.nusmods.com/v2/2019-2020/modules/" + moduleCode + ".json";
        URL url;
        String output = "";
        try {
            url = new URL(httpsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            output = print_content(con);
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
    private String print_content(HttpsURLConnection con) {
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
        return output;
    }
}
