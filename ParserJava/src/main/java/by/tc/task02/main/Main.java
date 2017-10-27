package by.tc.task02.main;


import by.tc.task02.entity.Root;
import by.tc.task02.service.ServiceFactory;
import by.tc.task02.service.XMLService;

public class Main {


    public static void main(String[] args) {



        ServiceFactory factory = ServiceFactory.getInstance();
        XMLService service = factory.getXMLService();

        Root root = service.createTree();

        PrintTreeInfo.print(root);

    }

}


