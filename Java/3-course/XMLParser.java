//Вот пример простого XML-файла и как его содержимое может быть отображено с
//использованием DOM-парсера и компонента JTree:
//Предположим, у вас есть следующий XML-файл с именем "example.xml":

//<root>
//<person>
//<name>John Doe</name>
//<age>30</age>
//<email>john@example.com</email>
//</person>
//<person>
//<name>Jane Smith</name>
//<age>25</age>
//<email>jane@example.com</email>
//</person>
//</root>

package com.mycompany.xmlparserwithjtree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLParserWithJTree {
    public static void main(String[] args) {
        // Запускаем главный поток Swing для создания и отображения GUI.
        SwingUtilities.invokeLater(() -> {
            // Создаем главное окно приложения.
            JFrame frame = new JFrame("XML Parser with JTree");

            // Устанавливаем операцию закрытия окна на завершение приложения при закрытии окна.
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Создаем JTree и устанавливаем его модель с помощью XMLTreeModel, передав имя XML-файла.
            JTree tree = new JTree(new XMLTreeModel("example.xml"));

            // Создаем панель прокрутки для JTree.
            JScrollPane treeScrollPane = new JScrollPane(tree);

            // Добавляем панель прокрутки с JTree в главное окно приложения.
            frame.add(treeScrollPane);

            // Устанавливаем размер окна в соответствии с содержимым и делаем его видимым.
            frame.pack();
            frame.setVisible(true);
        });
    }
}

class XMLTreeModel extends DefaultMutableTreeNode {
    // Конструктор класса XMLTreeModel, принимающий имя XML-файла.
    public XMLTreeModel(String xmlFileName) {
        // Вызываем конструктор суперкласса для создания корневого узла "XML Data".
        super("XML Data");

        // Вызываем метод для построения дерева JTree на основе XML-файла.
        buildTreeModel(xmlFileName);
    }

    // Метод для построения дерева JTree из XML-файла.
    private void buildTreeModel(String xmlFileName) {
        try {
            // Создаем фабрику для создания парсера XML-документов.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Создаем парсер XML-документов.
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Разбираем XML-файл и получаем документ.
            Document document = builder.parse(xmlFileName);

            // Получаем корневой элемент документа.
            Element rootElement = document.getDocumentElement();

            // Вызываем метод для рекурсивного анализа элементов и построения дерева.
            parseElement(rootElement, this);
        } catch (Exception e) {
            // Обрабатываем исключения, если что-то пошло не так.
            System.out.println("Ошибка!!!");
        }
    }

    // Метод для рекурсивного анализа элементов и построения дерева.
    private void parseElement(Element element, DefaultMutableTreeNode parent) {
        // Создаем узел для текущего элемента и добавляем его в родительский узел.
        DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode(element.getTagName());
        parent.add(elementNode);

        // Получаем дочерние узлы текущего элемента.
        NodeList children = element.getChildNodes();

        // Проходимся по дочерним узлам и вызываем этот метод для элементов типа Element.
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element) {
                parseElement((Element) children.item(i), elementNode);
            }
        }
    }
}

