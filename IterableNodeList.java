
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
public class IterableNodeList<T> implements NodeList, Iterable<Node> {
 
    List<Node> nodeList;
 
    /**
     * Iterable Node List implementation
     * @param list - NodeList to make compatible with iteration
     */
    public IterableNodeList(NodeList list) {
        nodeList = new ArrayList<Node>();
 
        if(list.getLength() > 0) {
            for(Node childNode = list.item(0); childNode != null; childNode = childNode.getNextSibling()) {
                nodeList.add(childNode);
            }
        }
    }
 
    @Override
    public Iterator<Node> iterator() {
        return nodeList.iterator();
    }
 
    @Override
    public Node item(int index) {
        return nodeList.get(index);
    }
 
    @Override
    public int getLength() {
        return nodeList.size();
    }
}