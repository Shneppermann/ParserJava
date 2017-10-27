package by.tc.task02.service;

import by.tc.task02.dao.DAOFactory;
import by.tc.task02.dao.XMLDAO;
import by.tc.task02.entity.Root;

public class XMLServiceImpl implements XMLService {

    @Override
    public Root createTree(){

        DAOFactory factory = DAOFactory.getInstance();
        XMLDAO xmlDAO = factory.getXMLDAO();


        Root root = xmlDAO.createTree();

        return root;

    }
}
