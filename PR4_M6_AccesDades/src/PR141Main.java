import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class PR141Main {
    public static void main(String[] args) {
        try {
            String basePath = System.getProperty("user.dir") + "/Dades/";
            String filePath = basePath + "biblioteca.xml";

            // Array de Libros y los ejemplos
            ArrayList<Libro> libros = new ArrayList<>();
            libros.add(new Libro("001", "El viatge dels venturons", "Joan Pla", 1998, "Edicions Mar", "Aventura", 320, true));
            //libros.add(new Libro("002", "El camino de los reyes", "Brandon Sanderson", 2010, "Tor Books", "Fantasia", 1007, false));

            // Crea una factoria de constructors de documents
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // Crea un constructor de documents
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Crea un nou document XML
            Document doc = db.newDocument();
            // Crea l'element root del document XML
            Element elmRoot = doc.createElement("biblioteca");
            // Afegeix l'element root al document XML
            doc.appendChild(elmRoot);

            // Añadir libros
            for (Libro book : libros) {
                // libro con id
                Element elmLibro = doc.createElement("llibre");
                Attr attrId = doc.createAttribute("id");
                attrId.setValue(book.id);
                elmLibro.setAttributeNode(attrId); 

                // titulo
                Element elmValue = doc.createElement("titol");
                Text nodeTextBook = doc.createTextNode(book.title);
                elmValue.appendChild(nodeTextBook);
                elmLibro.appendChild(elmValue);

                // autor
                elmValue = doc.createElement("autor");
                nodeTextBook = doc.createTextNode(book.author);
                elmValue.appendChild(nodeTextBook);
                elmLibro.appendChild(elmValue);

                // publicacio
                elmValue = doc.createElement("anyPublicacio");
                nodeTextBook = doc.createTextNode(Integer.toString(book.year));
                elmValue.appendChild(nodeTextBook);
                elmLibro.appendChild(elmValue);

                // editorial
                elmValue = doc.createElement("editorial");
                nodeTextBook = doc.createTextNode(book.publisher);
                elmValue.appendChild(nodeTextBook);
                elmLibro.appendChild(elmValue);

                // genere
                elmValue = doc.createElement("genere");
                nodeTextBook = doc.createTextNode(book.genre);
                elmValue.appendChild(nodeTextBook);
                elmLibro.appendChild(elmValue);

                // pagines
                elmValue = doc.createElement("pagines");
                nodeTextBook = doc.createTextNode(Integer.toString(book.pages));
                elmValue.appendChild(nodeTextBook);
                elmLibro.appendChild(elmValue);

                // disponible
                elmValue = doc.createElement("disponible");
                nodeTextBook = doc.createTextNode(Boolean.toString(book.available));
                elmValue.appendChild(nodeTextBook);
                elmLibro.appendChild(elmValue);

                // Afegir fill a root
                elmRoot.appendChild(elmLibro);
            }
            //Escribir documento en archivo
            write(filePath, doc);
        } catch (Exception e) {
            // TODO: handle exception
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

class Libro {
    String id;
    String title;
    String author;
    int year;
    String publisher;
    String genre;
    int pages;
    boolean available;
    public Libro(String id, String title, String author, int year, String publisher, String genre, int pages,
            boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
        this.genre = genre;
        this.pages = pages;
        this.available = available;
    }

    
}
