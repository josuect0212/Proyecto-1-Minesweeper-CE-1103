public class LinkedList {
    private Node head;
    private int size;

    public boolean isEmpty(){
        return this.head == null;
    }
    public int size(){
        return this.size;
    }
    public void insertFirst(Object data){
        Node newNode = new Node(data);
        newNode.next = this.head;
        this.head = newNode;
        this.size++;
    }
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
    public void displayList(){
        Node current = this.head;
        while (current != null){
            System.out.println(current.getData());
            current = current.getNext();
        }
    }
    public boolean find(Object searchValue){
        Node current = this.head;
        while(current!=null){
            if(current.getData().equals(searchValue)){
                return true;
            }
            else{
                current = current.getNext();
            }
        }
        return false;
    }
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
            }
            else{
                previous=current;
                current=current.getNext();
            }
        }
        return null;
    }
}
