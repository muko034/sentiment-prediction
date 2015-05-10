import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
//        final String PATH = "sorted_data_acl/asd.txt";
        final String PATH = "sorted_data_acl/kitchen_&_housewares/negative.review";
//        final String PATH = "sorted_data_acl/kitchen_&_housewares/positive.review";
//        final String PATH = "sorted_data_acl/kitchen_&_housewares/unlabeled.review";

        System.out.println("Hello World!");

        fixXml(PATH);

        try {
            unMarshalingExample(PATH);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void unMarshalingExample(String path) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Reviews.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        //We had written this file in marshalling example
//        Reviews emps = (Reviews) jaxbUnmarshaller.unmarshal( new File("sorted_data_acl/books/negative.review") );
        Reviews emps = (Reviews) jaxbUnmarshaller.unmarshal( new File(path) );

        for(Review emp : emps.getReviews())
        {
//            System.out.println(emp.getUnique_id());
//            System.out.println(emp.getRating());
            System.out.println(emp.getReview_text());
        }
    }

    /**
     * This method ensures that the output String has only
     * @param in the string that has a non valid character.
     * @return the string that is stripped of the non-valid character
     */
    private static String stripNonValidXMLCharacters(String in) {
        if (in == null ) return null;
        StringBuffer out = new StringBuffer(in);
        for (int i = 0; i < out.length(); i++) {
            if(out.charAt(i) == 0x1a) {
                out.setCharAt(i, ' ');
            }
        }
        return out.toString();
    }

    private static void fixXml(String path) {
//        File file = new File("sorted_data_acl/books/negative.review");
        File file = null;
        BufferedReader br = null;
        List<String> xml = new ArrayList<String>();

        xml.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        xml.add("<reviews>");

        try {

            String sCurrentLine;
            file = new File(path);
            br = new BufferedReader(new FileReader(file));
//            br = new BufferedReader(new FileReader("sorted_data_acl/asd.txt"));

            while ((sCurrentLine = br.readLine()) != null) {
                xml.add(sCurrentLine);
            }
            xml.add("</reviews>");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

//        System.out.println(xml);

        final Pattern unescapedAmpersands = Pattern.compile("(&(?!amp;))");
        final Pattern quots = Pattern.compile("&quot;");
        List<String> xmlWithAmpersandsEscaped = new ArrayList<String>();

        for (String line : xml) {
            Matcher m = quots.matcher(line);
            line = m.replaceAll("\"");
            m = unescapedAmpersands.matcher(line);
            line = m.replaceAll("&amp;");
            line = stripNonValidXMLCharacters(line);
            xmlWithAmpersandsEscaped.add(line);
        }

        Writer fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {

            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);

            // Write the lines one by one
            for (String line : xmlWithAmpersandsEscaped) {
                line += System.getProperty("line.separator");
                bufferedWriter.write(line);

                // alternatively add bufferedWriter.newLine() to change line
            }

        } catch (IOException e) {
            System.err.println("Error writing the file : ");
            e.printStackTrace();
        } finally {

            if (bufferedWriter != null && fileWriter != null) {
                try {
                    bufferedWriter.close();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
