package ZuoChengYun_exercise;
/**
 * 目的：逆时针顺序打印边界节点
 * Created by XCY on 2017/7/20.
 * 标准一：1，头结点为边界节点
 *         2，叶节点为边界节点
 *         3，如果节点在其所在层是最左或最右的，那么也是边界节点
 * 标准二：1，头结点为边界节点
 *         2，叶节点为边界节点
 *         3，树左边界延伸下去的路径为边界节点
 *         4，树右边界延伸下去的路径为边界节点
 *
 */
public class _95_printEdge {
    public class Node{
        public int value;
        public Node left;
        public Node right;
        public Node(int data){
            this.value = data;
        }
    }

    public void printEdge1(Node head){//标准一
        if (head == null){
            return;
        }
        int height = getHeight(head,0);
        Node[][] edgeMap = new Node[height][2];
        setEdgeMap(head,0,edgeMap);
        //打印左边界
        for (int i = 0; i < edgeMap.length; i++){
            System.out.print(edgeMap[i][0].value + " ");
        }
        //打印既不是左边界，也不是右边界的叶子节点
        printLeafNotInMap(head,0,edgeMap);
        //打印右边界，但不是左边界的节点
        for (int i = edgeMap.length - 1; i != -1; i--){
            if (edgeMap[i][0] != edgeMap[i][1]){
                System.out.print(edgeMap[i][1].value + " ");
            }
        }
        System.out.println();
    }

    public int getHeight(Node head,int h){
        if (head == null){
            return 1;
        }
        return Math.max(getHeight(head.left,h+1),getHeight(head.right,h+1));
    }

    public void setEdgeMap(Node head, int h, Node[][] edgeMap){
        if (head == null){
            return;
        }
        edgeMap[h][0] = edgeMap[h][0] == null ? head : edgeMap[h][0];
        edgeMap[h][1] = head;
        setEdgeMap(head.left,h+1,edgeMap);
        setEdgeMap(head.right,h+1,edgeMap);
    }

    public void printLeafNotInMap(Node head, int h, Node[][] m){
        if (head == null){
            return;
        }
        if (head.left == null && head.right == null && head != m[h][0] && head != m[h][1]){
            System.out.print(head.value + " ");
        }
        printLeafNotInMap(head.left,h+1,m);
        printLeafNotInMap(head.right,h+1,m);
    }

    public void printEdge2(Node head){//标准二
        if (head == null){
            return;
        }
        System.out.print(head.value + " ");
        if (head.left != null && head.right != null){
            printLeftEdge(head,true);
            printRightEdge(head,true);
        }else {
            printEdge2(head.left != null ? head.left : head.right);
        }
        System.out.println();
    }

    public void printLeftEdge(Node h, boolean print){//打印左边界的延伸路径及左子树上所有叶节点
        if (h == null){
            return;
        }
        if (print || (h.left == null && h.right == null)){
            System.out.print(h.value + " ");
        }
        printLeftEdge(h.left,print);
        printLeftEdge(h.right, print && h.left == null);
    }

    public void printRightEdge(Node h, boolean print){//打印右边界的延伸路径及右子树上所有叶节点
        if (h == null){
            return;
        }
        printRightEdge(h.left,print && h.right ==null);//
        printRightEdge(h.right,print);//
        if (print || (h.left == null && h.right == null)){//顺序与逆时针打印有关
            System.out.print(h.value + " ");//
        }
    }

}
