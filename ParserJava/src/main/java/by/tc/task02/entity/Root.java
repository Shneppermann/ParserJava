package by.tc.task02.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Root extends Element implements Serializable{

    private String startName;
    private String body;
    private List <String> substrings;
    private List <Root> nodes = new ArrayList<Root>() ;

    public List<String> getSubstrings() {
        return substrings;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public void setSubstrings(List<String> substrings) {
        this.substrings = substrings;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStartName() {
        return startName;
    }

    public List<Root> getNodes() {
        return nodes;
    }

    public void putNode(Root node){
        if(node!=null) {
            this.nodes.add(node);
        }
    }

    public Root(){}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Root root = (Root) o;

        if (startName != null ? !startName.equals(root.startName) : root.startName != null) return false;
        if (body != null ? !body.equals(root.body) : root.body != null) return false;
        if (substrings != null ? !substrings.equals(root.substrings) : root.substrings != null) return false;
        return nodes != null ? nodes.equals(root.nodes) : root.nodes == null;
    }

    @Override
    public int hashCode() {
        int result = startName != null ? startName.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (substrings != null ? substrings.hashCode() : 0);
        result = 31 * result + (nodes != null ? nodes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Root{" +
                "startName='" + startName + '\'' +
                ", \n body='" + body + '\'' +
                ", \n substrings=" + substrings +
                ", \n nodes=" + nodes +
                ", \n name='" + name + '\'' +
                '}';
    }
}
