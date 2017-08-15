package ZuoChengYun_exercise;
/**
 * 目的：一种删除链表节点的方式
 * Created by XCY on 2017/7/20.
 */
public class _81_removeNodeWired {
    public class Node{
        public int value;
        public Node next;
        public Node(int data){
            this.value = data;
        }
    }

    public void reverseNodeWired(Node node){
        if (node == null){
            return;
        }
        Node nextNode = node.next;
        if (nextNode == null){
            throw new RuntimeException("can not remove last node.");
        }
        node.value = nextNode.value;
        node.next = nextNode.next;
    }
}
