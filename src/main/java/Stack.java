/**
 * This Stack class was taken from the Linear Data Structures presentation
 */
public class Stack {
    private LinkedList stackList;

    /**
     * Creates a new stack
     */
    public Stack(){
        this.stackList=new LinkedList();
    }

    /**
     * Inserts a new element at the top position
     * @param newElement element to be added to the stack
     */
    public void push(Object newElement){
        this.stackList.insertFirst(newElement);
    }

    /**
     * deletes the top position of the stack
     * @return the object at the top
     */
    public Object pop(){
        return this.stackList.deleteFirst();
    }

    /**
     * returns the top position of the stack
     * @return the data contained at the top position of the stack
     */
    public Object peek(){
        return this.stackList.getHead().getData();
    }

    /**
     * checks if the stack is empty
     * @return true if the stack is empty, false if not
     */
    public boolean isEmpty(){
        return this.stackList.isEmpty();
    }
}