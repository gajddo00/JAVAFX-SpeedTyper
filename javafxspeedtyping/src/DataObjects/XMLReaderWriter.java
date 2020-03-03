package DataObjects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLReaderWriter {

    private String pathName = "C:\\Users\\domin\\Desktop\\UPOL\\ZP4JV\\javafxspeedtyping\\src\\scores.XML";

    public boolean findUser(String user) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(pathName);
        Element root = doc.getDocumentElement();
        NodeList users = root.getElementsByTagName("Username");
        for (int i = 0; i < users.getLength(); i++) {
            Node node = users.item(i);
            Element userName = (Element) node;
            if (userName.getTextContent().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(String user) throws Exception {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(pathName);
            Element root = doc.getDocumentElement();

            Element userObj = doc.createElement("User");
            root.appendChild(userObj);
            Element userName = doc.createElement("Username");
            userName.setTextContent(user);
            userObj.appendChild(userName);
            Element scores = doc.createElement("Scores");
            userObj.appendChild(scores);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathName));
            transformer.transform(source, result);

        } catch (Exception e) {
            throw new Exception("Adding failed");
        }
    }

    public void addScore(String user, Score score) throws Exception {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(pathName);
            Element root = doc.getDocumentElement();
            NodeList users = root.getElementsByTagName("Username");
            Element userName = null;
            for (int i = 0; i < users.getLength(); i++) {
                Node node = users.item(i);
                userName = (Element) node;
                if (userName.getTextContent().equals(user)) {
                    break;
                }
            }

            if (userName != null) {
                Element parent = (Element) userName.getParentNode();
                Element scores = (Element) parent.getElementsByTagName("Scores").item(0);

                Element scoree = doc.createElement("Score");
                scores.appendChild(scoree);
                Element time = doc.createElement("Time");
                time.setTextContent(String.valueOf(score.getTime()));
                scoree.appendChild(time);
                Element charCount = doc.createElement("CharCount");
                charCount.setTextContent(String.valueOf(score.getCharCount()));
                scoree.appendChild(charCount);
                Element difficulty = doc.createElement("Difficulty");
                difficulty.setTextContent(score.getDifficulty());
                scoree.appendChild(difficulty);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(pathName));
                transformer.transform(source, result);
            }
        } catch (Exception e) {
            throw new Exception("Adding failed");
        }
    }

    public boolean isPresent(List<User> users, String username){
        if (users.stream().noneMatch(p -> p.getUserName().equals(username)))
            return false;
        else
            return true;
    }

    public User getUser(List<User> users, String username){
        for (User user : users){
            if (user.getUserName().equals(username))
                return user;
        }
        return null;
    }

    public List<User> read(String difficulty) throws Exception {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(pathName);
            Element root = doc.getDocumentElement();
            NodeList users = root.getElementsByTagName("Difficulty");
            List<User> userList = new ArrayList<>();

            for (int i = 0; i < users.getLength(); i++) {
                Node node = users.item(i);
                Element difnode = (Element) node;
                if (difnode.getTextContent().equals(difficulty)){
                    Element scoreParent = (Element) difnode.getParentNode();
                    int time = Integer.parseInt(scoreParent.getElementsByTagName("Time").item(0).getTextContent());
                    int charCount = Integer.parseInt(scoreParent.getElementsByTagName("CharCount").item(0).getTextContent());

                    Element scoresParent = (Element) scoreParent.getParentNode();
                    Element userNode = (Element) scoresParent.getParentNode();
                    String userName = userNode.getElementsByTagName("Username").item(0).getTextContent();

                    User user;
                    Score score = new Score(time, charCount, difficulty);
                    if (!isPresent(userList, userName)){
                        user = new User(userName);
                        user.getScoreList().add(score);
                        userList.add(user);
                    } else {
                        user = getUser(userList, userName);
                        user.getScoreList().add(score);
                    }
                }
            }
            return userList;
        } catch (Exception e) {
            throw new Exception("Searching failed");
        }
    }
}
