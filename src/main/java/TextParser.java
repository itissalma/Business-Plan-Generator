//Shady Nessim 900191322 Salma Aly 900203182
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {

    public static String[] parse(String inputText) {
        ArrayList<String> sectionsContent = new ArrayList<>();

        // split using '~' as a delimiter
        String[] sectionsContentArray = inputText.split("~");

        sectionsContentArray = Arrays.stream(sectionsContentArray).filter(s -> !s.isEmpty()).toArray(String[]::new);

        // add every other element to the sectionsContent array
        for (int i = 1; i < sectionsContentArray.length; i+=2){
            sectionsContent.add(sectionsContentArray[i]);
        }

        // if sectionsContent has more than 7 elements return first 7
        // if it has less than 7, return it and add empty strings to it until it has 7 elements
        if (sectionsContent.size() > 7){
            return sectionsContent.subList(0, 7).toArray(new String[0]);
        } else {
            while (sectionsContent.size() < 7){
                sectionsContent.add("");
            }
            return sectionsContent.toArray(new String[0]);
        }
    }

    public static void main(String[] args){
        System.out.println(Arrays.toString(parse("")));
    }
}
