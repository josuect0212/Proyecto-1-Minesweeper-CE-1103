/**
 * This Node class was taken from the Linear Data Structures presentation
 */
public class Node {
    private Object data;
    public Node next;

    /**
     * Creates a new node with a specific data
     * @param data the data that will be contained in the node
     */
    public Node(Object data){
        this.next = null;
        this.data = data;
    }

    /**
     * gets the data contained in the node
     * @return data of the node
     */
    public Object getData(){
        return this.data;
    }

    /**
     * sets the data of a specific node to the new data
     * @param data new data to be contained by the node
     */
    public void setData(Object data){
        this.data = data;
    }

    /**
     * gets the next node in the list
     * @return the next node in the list
     */
    public Node getNext(){
        return this.next;
    }

    /**
     * sets the next node to the specified node
     * @param node node that will be set as the next node
     */
    public void setNext(Node node){
        this.next = node;
    }
}
