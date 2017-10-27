package by.tc.task02.main;

import by.tc.task02.entity.Root;

public class PrintTreeInfo {
    private final static String START ="<";
    private final static String END =">";
    private final static String SEPARATOR = "\\";
    private final static String NEW_STRING = "\n";
    private final static String EMPTY_STRING = "";
    private final static String ROOT_NOT_FOUND = "Root not found";



    public static void print(Root root) {

        if(root==null)
        {
            System.out.println(ROOT_NOT_FOUND);
        }
        else
        {
            System.out.println(START + root.getStartName() + END+allNodesToString(root)+ START+SEPARATOR + root.getName() + END);
        }
    }


    private static String allNodesToString(Root root)
    {
        String result =EMPTY_STRING;
        if (root.getNodes().size()>1){
            for (Root r: root.getNodes()) {
                result+=NEW_STRING+outString(r);
            }
        }
        else {
            return result = root.getBody();
        }
        return result+NEW_STRING;
    }

    private static String outString(Root root)
    {
        if (root.getBody().equals(EMPTY_STRING))
        {
            return START+root.getStartName()+END;
        }
        else {
            return START + root.getStartName() + END + allNodesToString(root) + START+SEPARATOR + root.getName() + END;
        }

    }


}
