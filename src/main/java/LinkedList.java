/**
 * This LinkedList class was taken from the Linear Data Structures presentation
 */
public class LinkedList {
    private Node head;
    private int size;

    /**
     * this method checks if the list is empty
     * @return if it's not empty it will return false
     */
    public boolean isEmpty(){
        return this.head == null;
    }

    /**
     * This method inserts a node to the first position of the list
     * @param data the data that will be added to the list in the first position
     */
    public void insertFirst(Object data){
        Node newNode = new Node(data);
        newNode.next = this.head;
        this.head = newNode;
        this.size++;
    }

    /**
     * This method deletes the node at the first position of the list
     * @return the removed node or null if the list is empty
     */
    public Node deleteFirst(){
        if (this.head != null){
            Node temp = this.head;
            this.head = this.head.next;
            this.size--;
            return temp;
        }
        else{
            return null;
        }
    }

    /**
     * This method displays the current list horizontally
     */
    public void displayList() {
        Node current = this.head;
        while (current != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println();
    }

    /**
     * This method looks for a specific node inside the list
     * @param searchValue the value of the node to search
     * @return null if the value is not found, or the node containing the value if it's found
     */
    public Node find(Object searchValue){
        Node current = this.head;
        while(current!=null){
            if(current.getData().equals(searchValue)){
                return current;
            }
            else{
                current = current.getNext();
            }
        }
        return null;
    }

    /**
     * Deletes the node that contains a specific value
     * @param searchValue value to be deleted from the list
     * @return deleted value, null if its not found
     */
    public Node delete(Object searchValue){
        Node current = this.head;
        Node previous = this.head;

        while(current!=null){
            if (current.getData().equals(searchValue)){
                if (current==this.head){
                    this.head = this.head.getNext();
                }
                else {
                    previous.setNext(current.getNext());
                }
                return current;
            }
            else{
                previous=current;
                current=current.getNext();
            }
        }
        return null;
    }

    /**
     * gets the head of the list
     * @return node that represents the head of the list
     */
    public Node getHead(){
        return this.head;
    }
    /**
     * This method gets the current size of the list
     * @return the size of the list as an int
     */
    public int getSize(){
        return this.size;
    }
}
