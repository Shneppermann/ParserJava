package by.tc.task02.dao;

import by.tc.task02.entity.Root;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLDAOImpl implements XMLDAO {

    private final static String START ="<";
    private final static String S_END ="</";
    private final static String END = ">";
    private final static String EMPTY_STRING = "";
    private final static String PATTERN_STRING ="(<.+?>)";
    private final static String PATTERN_CHECK_PARAMETER = "(\\s.+\\=\\s\\\".+?>)";
    private final static String START_NEW_STRING = "(<";
    private final static String S_END_NEW_STRING ="(<\\/";
    private final static String END_NEW_STRING =">)";
    private final static String ANY_SYMBOLS = ".+?";
    private final static String TABULATION_STRING = "\t";
    private final static String CHECK_PATTERN = "(<[!\\?].+?>)";
    private final static String CHECK_EMPTY_TAG = "(<.+?[\\/]>)";
    private final static String ERROR_LOADING = "Error while loading text: ";
    private final static String FILE_NAME = "/newXML.xml";


    /**
     * Creates tree of the roots. No matter how funny it sounded.
     * @return root that contains tree of the roots.
     */
    @Override
    public Root createTree(){

        String xmlDocumentString = convertXmlToString(FILE_NAME);

        xmlDocumentString =checkDocument(xmlDocumentString);
        xmlDocumentString = xmlDocumentString.replaceAll(TABULATION_STRING, EMPTY_STRING);

        Root root = createObject(xmlDocumentString);

        return root;

    }

    /**
     * Creates and returns object Root.
     *
     * @param string the string with which to create a new object and a new substring for creating new objects.
     * @return new object Root.
     */
    private Root createObject(String string){
        int counter;
        String substring;
        Root root = new Root();

        String [] name = createName(string);

        root.setStartName(name[0]);
        root.setName(name[1]);


        substring = string.replace(START + root.getStartName() + END, EMPTY_STRING);
        substring = substring.replace(S_END + root.getName() + END, EMPTY_STRING);

        root.setBody(substring);

        root.setSubstrings(createLevelString(substring));

        if (root.getSubstrings().size() > 1) {
            for (counter = 0; counter < root.getSubstrings().size(); counter++) {
                root.putNode(createObject(root.getSubstrings().get(counter)));
            }
        }

        return root;

    }


    /**
     * Creates and returns string from the XML file.
     *
     * @param fileName the name of the XML file.
     * @return string.
     */
    private String convertXmlToString(String fileName){

        StringBuilder stringBuilder = new StringBuilder();
        try {

            InputStream inputStream = getClass().getResourceAsStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                stringBuilder.append(line);
            }

        } catch (IOException ex) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.flush();
            pw.close();
            stringBuilder.append(ERROR_LOADING).append(TABULATION_STRING);
            stringBuilder.append(sw.getBuffer().toString());

        }

        return stringBuilder.toString();
    }


    /**
     * Creates and returns List<String> from string.
     * @param str the string with which to create a List of strings.
     * @return array List<String>.
     */
    private List<String> createLevelString(String str)
    {

        List<String> array=new ArrayList<>();
        String temp;

        while (!str.equals(EMPTY_STRING)){
            temp = createSubstring(str);
            array.add(temp);
            str=str.replace(temp,EMPTY_STRING);
        }

        return array;
    }

    /**
     * Creates object name
     * Finds and creates names of tag elements in the string
     * @param string the string that can contains XML tags
     * @return result the array of string that contains names of the xml tag
     */
    private String [] createName(String string){

        String [] result = new String[2];

        String name = EMPTY_STRING;

        Scanner newScanner = new Scanner(string);
        while (newScanner.hasNext()){
            name = newScanner.findInLine(Pattern.compile(PATTERN_STRING));
            if(name!=null){
                break;
            }
            newScanner.nextLine();
        }


        if (name==null){
            result[0]=EMPTY_STRING;
            result[1]=EMPTY_STRING;
        }
        else{
            result = notNullResult(name);
        }
        newScanner.close();
        return result;
    }

    /**
     * Creates array of string for createName method
     * The method checks empty tags
     * @param string the string that contains XML tags
     * @return result the array of string that contains names of the xml tag
     */
    private String[] notNullResult(String string){
        String [] result = new String[2];
        Scanner newScanner = new Scanner(string);
        String deleteItemString =newScanner.findInLine(Pattern.compile(PATTERN_CHECK_PARAMETER));
        if (deleteItemString==null){
            string = string.replace(START, EMPTY_STRING);
            string = string.replace(END, EMPTY_STRING);
            result[0] = string ;
            result[1] = string ;
        }
        else {
            string= string.replace(START, EMPTY_STRING);
            string = string.replace(deleteItemString, EMPTY_STRING);
            deleteItemString = deleteItemString.replace(END, EMPTY_STRING);
            result[0] = string + deleteItemString;
            result[1] = string;
        }
        newScanner.close();
        return result;

    }

    /**
     * Creates the substring if string contains XML tags.
     * @param string the string that can contains substrings with XML tags.
     * @return result if string contains XML tags; string if string does not contain XML tags.
     */
    private String createSubstring(String string)
    {
        String result = EMPTY_STRING;
        String [] name = createName(string);
        if (name[0].equals(EMPTY_STRING)){
           return string;
        }
        else {
               result = notEmptyName(string,name);
               return result;
        }
    }


    /**
     * Checks the string for empty tags. Creates a substring limited to tags.
     * @param string the string that can contains empty tags.
     * @param name the names of the tags.
     * @return if string contains empty tag, method returns this tag;
     * otherwise, method returns a substring limited to tags.
     */
    private String notEmptyName(String string, String [] name)
    {
        String patternString;
        String result = EMPTY_STRING;
        if (checkEmptyTag(START + name[0] + END)) {
            return START + name[0] + END;
        }
        else {
            patternString = START_NEW_STRING + name[0] + END_NEW_STRING + ANY_SYMBOLS + S_END_NEW_STRING + name[1] + END_NEW_STRING;
            Scanner newScanner = new Scanner(string);
            while (newScanner.hasNext()) {
                result = newScanner.findInLine(Pattern.compile(patternString));
                if (result != null) {
                    break;
                }
                newScanner.nextLine();
            }
            newScanner.close();
        }
        return result;
    }


    /**
     * Finds and removes comments and declarations from the string.
     * @param string the string that can contains comments and declarations.
     * @return string without comments and declarations.
     */
    private String checkDocument (String string)
    {

        Pattern p = Pattern.compile(CHECK_PATTERN);
        Matcher m = p.matcher(string);
        String temp = EMPTY_STRING;
        while (m.find()){
            temp = m.group();
            string = trimGroup(string, temp);
        }
        return string;

    }

    /**
     * Removes temp from the string.
     * @param string the string that contains temp.
     * @param temp the substring needs to remove.
     * @return string without temp.
     */
    private String trimGroup(String string, String temp){
        string = string.replace(temp, EMPTY_STRING);
        return string;
    }


    /**
     * Checks string for empty tag.
     * @param string the string that can contains empty tag.
     * @return {@code true} if string contains empty tag;
     * {@code false} otherwise.
     */
    private  boolean checkEmptyTag(String string){
        Pattern p = Pattern.compile(CHECK_EMPTY_TAG);
        Matcher m = p.matcher(string);
        return m.find();
    }
}
