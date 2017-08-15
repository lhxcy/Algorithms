package ZuoChengYun_exercise;
import java.util.HashMap;
import java.util.Stack;

/**
 * 目的：构建maxTree
 * Created by XCY on 2017/7/10.
 * 栈  获取第i个数据左边第一个大于它的数和右边第一个大于它的数
 * 每个数的父节点是它左边第一个比它大的数和右边第一个比它大的数中的最小的那个数
 */
public class _22_maxTree {
    public  class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int data){
            this.value = data;
        }
    }
    public Node getMaxTree(int[] arr){
        Node[] nArr = new Node[arr.length];
        for (int i = 0; i != arr.length; i++){
            nArr[i] = new Node(arr[i]);//初始化
        }
        Stack<Node> stack = new Stack<Node>();
        HashMap<Node,Node> lBigMap = new HashMap<Node,Node>();//存放左边第一个大的数
        HashMap<Node,Node> rBigMap = new HashMap<Node,Node>();//右边第一个
        for (int i = 0; i != nArr.length; i++){
            Node curNode = nArr[i];
            while ((!stack.isEmpty()) && stack.peek().value < curNode.value){
                popStackSetMap(stack, lBigMap);
                //左边
                // 栈中数据比当前数据小，弹出直到栈中数据比当前数据大或栈为空，
                // 并将第一个比当前数据大的节点存入lBigMap
            }
            stack.push(curNode);
        }
        while (!stack.isEmpty()){
            popStackSetMap(stack,lBigMap);
            //处理栈中剩余元素，相当于使所有元素都得到其左边第一个大于它的节点
        }
        for (int i = nArr.length-1; i != -1;i--){
            Node curNode = nArr[i];
            while (!stack.isEmpty() && stack.peek().value < curNode.value){
                popStackSetMap(stack, rBigMap);
                //右边
                // 栈中数据比当前数据小，弹出直到栈中数据比当前数据大或栈为空，
                // 并将第一个比当前数据大的节点存入rBigMap
            }
            stack.push(curNode);
        }
        while (!stack.isEmpty()){
            popStackSetMap(stack, rBigMap);
        }
        Node head = null;
        for (int i = 0; i != nArr.length; i++){
            Node curNode = nArr[i];
            Node left = lBigMap.get(curNode);
            Node right = rBigMap.get(curNode);
            if (left == null && right == null){
                head = curNode;//若left，right都为null则其为最大值，所以为head
            }else if (left == null){//如果左边没有比它大的值
                if (right.left == null){//如果右边第一个比它大的值左侧还没有连接节点，则将该节点接入左侧
                    right.left = curNode;
                }else {//如果右边第一个比它大的值左侧有连接节点，则将该节点接入右侧
                    right.right = curNode;
                }
            }else if (right == null){//如果右边没有 比它大的值
                if (left.left == null){//如果左边第一个比它大的值左侧还没有连接节点，则将该节点接入左侧
                    left.left = curNode;
                }else {//如果左边第一个比它大的值左侧有连接节点，则将该节点接入右侧
                    left.right = curNode;
                }
            }else {//如果该节点两侧都有比它大的值
                Node parent = left.value < right.value ? left : right;//取两者中较小的
                if (parent.left == null){
                    parent.left = curNode;//如果较小的节点左侧还没有连接节点，则将该节点接入左侧
                }else {
                    parent.right = curNode;//如果较小的节点左侧有连接节点，则将该节点接入右侧
                }
            }
        }
        return head;
    }

    public void popStackSetMap(Stack<Node> stack, HashMap<Node, Node> map){
        //做节点与比其大的节点之间的联系
        Node popNode = stack.pop();
        if (stack.isEmpty()){
            map.put(popNode,null);
        }else {
            map.put(popNode,stack.peek());
        }
    }
}

