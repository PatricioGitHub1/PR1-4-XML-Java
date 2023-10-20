import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PR142Main {
    public static void main (String[] args) {
        String basePath = System.getProperty("user.dir") + "/Dades/";
        String filePath = basePath + "cursos.xml";

        Scanner sc = new Scanner(System.in);
        String menu = "\nDades del Curs 2023-24\n======================\n1) Llistar ids de cursos, tutors, i total alumnes\n" +
                        "2) Mostrar ids i titols de mòduls a partir d'un id de curs\n" +
                        "3) Llistar alumnes d'un curs\n" +
                        "4) Afegir un alumne a un curs\n" +
                        "5) Eliminar un alumne d'un curs\n" +
                        "6) Sortir";
        boolean exit = false;
        String optID;
        while (!exit) {
            System.out.println(menu);
            System.out.print("Option: ");
            String opt = sc.next();
            switch (opt) {
                case "1":
                    listOption1(filePath);
                    break;
                case "2":
                    System.out.print("ID del curs: ");
                    optID = sc.next();
                    modulsDataById(optID, filePath);
                    break;
                case "3":
                    System.out.print("ID del curs: ");
                    optID = sc.next();
                    listStudentsById(optID, filePath);
                    break;
                case "4":
                    System.out.print("ID del curs:");
                    optID = sc.next();
                    addStudents(optID, filePath, sc);
                    break;
                case "5":
                    System.out.print("ID del curs:");
                    optID = sc.next();
                    deleteStudent(optID, filePath, sc);
                    break;
                case "6":
                    exit = true;
                    System.out.println("Exiting from the programm...");
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }

    static void listOption1(String pathFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
            // Crea un constructor de documents
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
            // Analitza el fitxer XML
            Document doc = dBuilder.parse(pathFile);

             // Normalitza l'element arrel del document
             doc.getDocumentElement();
            
             // Obté una llista de tots els elements "persona" del document
            NodeList listCurs = doc.getElementsByTagName("curs");
            
            // Iterar para sacar los datos de cada Curso
            for (int i = 0; i < listCurs.getLength(); i++) {
                System.out.println("------");

                // Id del curs

                Element eElement = (Element) listCurs.item(i);
                String id_curs = eElement.getAttribute("id");
                System.out.println("Curs: "+ id_curs);

                // Tutor del curs

                XPath xPath = XPathFactory.newInstance().newXPath();
                String expression = "/cursos/curs[@id='" + id_curs + "']";
                // Avaluem l'expressió XPath i obtenim una llista de nodes
                NodeList listExpression = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
                // Obtenim el primer element de la llista de nodes
                Node nd = listExpression.item(0);
                Element ndElement = (Element) nd;
                System.out.println("Tutor: "+ndElement.getElementsByTagName("tutor").item(0).getTextContent());

                // Numero d'alumnes

                expression = "/cursos/curs[@id='" + id_curs + "']/alumnes/alumne";
                listExpression = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
                System.out.println("Alumnes totals: "+listExpression.getLength());

                System.out.println("------");

            }
        } catch (Exception e) {
            // Imprimeix la pila d'errors en cas d'excepció
            e.printStackTrace();
        }
    }

    static void modulsDataById(String id, String pathFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
            // Crea un constructor de documents
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
            // Analitza el fitxer XML
            Document doc = dBuilder.parse(pathFile);

            // Normalitza l'element arrel del document
            doc.getDocumentElement();

            // Configurem XPath
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/cursos/curs[@id='" + id + "']/moduls/modul";
            NodeList listExpression = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

            System.out.println("Mostrant info dels moduls del curs amb id = "+id+"\n");
            //System.out.println(listExpression.getLength());
            // iteracio
            for (int i = 0; i < listExpression.getLength(); i++) {
                System.out.println("-----");

                Element element = (Element) listExpression.item(i);
                // id modul
                System.out.println("ID del modul: "+element.getAttribute("id"));
                // titol
                System.out.println("Nom del modul: "+element.getElementsByTagName("titol").item(0).getTextContent());
                System.out.println("-----");

            }
        } catch (Exception e) {
            // Imprimeix la pila d'errors en cas d'excepció
            e.printStackTrace();

        }
        
    }

    static void listStudentsById(String id, String pathFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
            // Crea un constructor de documents
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
            // Analitza el fitxer XML
            Document doc = dBuilder.parse(pathFile);

            // Normalitza l'element arrel del document
            //doc.getDocumentElement();

            // Configurem XPath
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/cursos/curs[@id='" + id + "']/alumnes/alumne";
            NodeList listExpression = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

            System.out.println("Listant estudiants del curs amb ID = "+id+"\n");
            // iteracio
            for (int i = 0; i < listExpression.getLength(); i++) {

                Element element = (Element) listExpression.item(i);

                System.out.println((i+1)+" - "+element.getTextContent());

            }
        } catch (Exception e) {
            // Imprimeix la pila d'errors en cas d'excepció
            e.printStackTrace();

        }
    }

    static void addStudents(String id, String pathFile, Scanner sc) {
        try {
            // Si el ID existe
            if (checkID(id, pathFile)) {
                System.out.print("Nom del nou estudiant (Nom + Cognom: Ej \'Patricio Rojas\'): ");
                String studentName = sc.next();
                String studentSurname = sc.next();
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                // Crea un constructor de documents
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                // Analitza el fitxer XML
                Document doc = dBuilder.parse(pathFile);

                // Normalitza l'element arrel del document
                //doc.getDocumentElement();
                // Configurem XPath
                XPath xPath = XPathFactory.newInstance().newXPath();
                String expression = "/cursos/curs[@id='" + id + "']/alumnes";
                NodeList listExpression = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
                
                Node ndAlumnes = (Node) listExpression.item(0);
                Element eAlumnes = (Element) ndAlumnes;
                Element newAlumne = doc.createElement("alumne");
                newAlumne.setTextContent(studentSurname.toUpperCase() + ", "+studentName);
                eAlumnes.appendChild(newAlumne);

                CleanAndSave(xPath,doc);
                write(pathFile, doc);
            // Si el ID no existe
            } else {
                System.out.println("No existeix el curs amb ID "+id);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    static void deleteStudent(String id, String pathFile, Scanner sc) {
        try {
            // Si el ID existe
            if (checkID(id, pathFile)) {
                System.out.print("Nom complet del estudiant a eliminar (Nom + Cognom: Ej \'Patricio Rojas\'): ");
                String studentName = sc.next();
                String studentSurname = sc.next();
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                // Crea un constructor de documents
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                // Analitza el fitxer XML
                Document doc = dBuilder.parse(pathFile);

                // Normalitza l'element arrel del document
                //doc.getDocumentElement();
                // Configurem XPath
                XPath xPath = XPathFactory.newInstance().newXPath();
                String expression = "/cursos/curs[@id='" + id + "']/alumnes/alumne[text()='" + studentSurname.toUpperCase() + ", " + studentName + "']";
                NodeList listExpression = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
                
                if (listExpression.getLength() > 0) {
                    Node alumneDeleteNode = listExpression.item(0);
                    Node parentNode = alumneDeleteNode.getParentNode();
                    parentNode.removeChild(alumneDeleteNode);
                    CleanAndSave(xPath,doc);
                    write(pathFile, doc);
                } else {
                    System.out.println("No es va poder trobar a l'alumne "+studentName+" "+studentSurname);
                }

                
            // Si el ID no existe
            } else {
                System.out.println("No existeix el curs amb ID "+id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static boolean checkID(String id, String pathFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
            // Crea un constructor de documents
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
            // Analitza el fitxer XML
            Document doc = dBuilder.parse(pathFile);

            // Normalitza l'element arrel del document
            doc.getDocumentElement();

            // Configurem XPath
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/cursos/curs[@id='" + id + "']";
            NodeList listExpression = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

            if (listExpression.getLength() > 0) {
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    static public void CleanAndSave(XPath xPath, Document doc) {
        // Para quitar linias en blanco
        NodeList nl;
        try {
            nl = (NodeList) xPath.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);

            for (int i=0; i < nl.getLength(); ++i) {
                Node node = nl.item(i);
                node.getParentNode().removeChild(node);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    // Save a Document into an XML file
    static public void write (String path, Document doc) throws IOException, TransformerException {
        if (!new File(path).exists()) { new File(path).createNewFile(); }
        // Crea una factoria de transformadors XSLT
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        // Crea un transformador XSLT
        Transformer transformer = transformerFactory.newTransformer();
        // Estableix la propietat OMIT_XML_DECLARATION a "no" per no ometre la declaració XML del document XML resultant
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        // Estableix la propietat INDENT a "yes" per indentar el document XML resultant
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // Elimina els espais en blanc innecessaris del document XML. Implementació pròpia
        //trimWhitespace(doc);
        // Crea una instància de DOMSource a partir del document XML
        DOMSource source = new DOMSource(doc);
        // Crea una instància de StreamResult a partir del camí del fitxer XML
        StreamResult result = new StreamResult(new File(path));
        // Transforma el document XML especificat per source i escriu el document XML
        // resultant a l'objecte especificat per result
        transformer.transform(source, result);
    }
}
