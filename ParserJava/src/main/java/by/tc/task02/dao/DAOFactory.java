package by.tc.task02.dao;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private final XMLDAO xmlDAO = new XMLDAOImpl();

    private DAOFactory() {}

    public XMLDAO getXMLDAO() {
        return xmlDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
