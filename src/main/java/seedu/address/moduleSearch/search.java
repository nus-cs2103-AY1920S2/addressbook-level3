package seedu.address.moduleSearch;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class search {
    /**
     * just a main for me to test the search
     * @param args just java stuff
     */
    public static void main(String[] args) {
        module mymod = new search().findModule("CS1101S");
        System.out.println(mymod);
    }

    /**
     * Function takes in module code and returns Module Object.
     * For more info on Module Object, look at module Class.
     * @param moduleCode input Module Code all caps
     * @return module
     */
    private module findModule(String moduleCode) {
        String https_url = "https://api.nusmods.com/v2/2019-2020/modules/" + moduleCode + ".json";
        URL url;
        String output = "";
        try {
            url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            output = print_content(con);
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return new module(output);
    }

    /**
     * Function to get HTTP information
     * @param con just java stuff
     * @return Raw HTTP info in string
     */
    private String print_content(HttpsURLConnection con) {
        String output = "";
        if(con!=null){
            try {
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                String input;

                while ((input = br.readLine()) != null){
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
