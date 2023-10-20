import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PR140Main {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + "/Dades/";
        String filePath = basePath + "persones.xml";
        
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
            // Crea un constructor de documents
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
            // Analitza el fitxer XML
            Document doc = dBuilder.parse(filePath);
        
            // Normalitza l'element arrel del document
            doc.getDocumentElement().normalize();
        
            // Obté una llista de tots els elements "persona" del document
            NodeList listPeople = doc.getElementsByTagName("persona");
            // Para sacar los titulos
            Node nodeTitle = listPeople.item(0);
            Element eElement = (Element) nodeTitle;
            NodeList titles = eElement.getChildNodes();
            
            // Iterar para sacar el header de la tabla
            System.out.print("PERSON TABLE\n==========================================================================================\n||");
            // Lista de atributos
            ArrayList<String> atributos = new ArrayList<>();
            for (int i = 0; i < titles.getLength(); i++) {
                String output = titles.item(i).getNodeName();
                if (!output.equals("#text")) {
                    if (output.length() < 20) {
                        System.out.print(String.format("%1$" + 20 + "s", output)+"||");
                        atributos.add(output);
                    }
                }
            }


            System.out.println("\n==========================================================================================");
            
            // Iterar para sacar los datos de cada Persona y ponerlo en la tabla
            for (int i = 0; i < listPeople.getLength(); i++) {
                Node node = listPeople.item(i);
                String entry = "||";
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement2 = (Element) node;
                    for (String type : atributos) {
                        String value = eElement2.getElementsByTagName(type).item(0).getTextContent();
                        entry += String.format("%1$" + 20 + "s", value)+"||";
                    }
                    
                }
                System.out.println(entry+"\n------------------------------------------------------------------------------------------");
            }

        } catch(Exception e) {
            // Imprimeix la pila d'errors en cas d'excepció
            e.printStackTrace();
        }  
    }    
}
