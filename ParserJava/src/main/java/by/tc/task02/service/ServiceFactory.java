package by.tc.task02.service;

public final class ServiceFactory {

        private static final ServiceFactory instance = new ServiceFactory();

        private final XMLService xmlService = new XMLServiceImpl();

        private ServiceFactory() {}

        public XMLService getXMLService() {

            return xmlService;
        }

        public static ServiceFactory getInstance() {
            return instance;
        }
}
