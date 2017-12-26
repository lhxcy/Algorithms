package JianZhiOffer;

import java.util.Stack;

/**
 * Created by XCY on 2017/12/19.
 */
public class BaseKnowledge {
}
//2 实现single模式
class SingleDemo{//饿汉式
    private SingleDemo(){}
    private static final SingleDemo singleDemo = new SingleDemo();
    public static SingleDemo getInstance(){
        return singleDemo;
    }
}

class SingleDemo1{//懒汉式，不支持多线程
    private SingleDemo1(){}
    private static SingleDemo1 singleDemo1 = null;
    public static SingleDemo1 getInstance(){
        if (singleDemo1 == null){
            singleDemo1 = new SingleDemo1();
        }
        return singleDemo1;
    }
}

class SingleDemo2{
    //懒汉式，支持多线程,但是也可能出错。当程序读到第一次的判断singleDemo2不为空时，singleDemo2可能还没有初始化完成
    private SingleDemo2(){}
    private static SingleDemo2 singleDemo2 = null;
    public static SingleDemo2 getInstance(){
        if (singleDemo2 == null){
            synchronized (SingleDemo2.class){
                if (singleDemo2 == null){
                    singleDemo2 = new SingleDemo2();
                }
            }
        }
        return singleDemo2;
    }
}

class SingleDemo3{//懒汉式，支持多线程,通过添加volatile关键字，避免出现初始化问题
    private SingleDemo3(){}
    private volatile static SingleDemo3 singleDemo3 = null;
    public static SingleDemo3 getInstance(){
        if (singleDemo3 == null){
            synchronized (SingleDemo3.class){
                if (singleDemo3 == null){
                    singleDemo3 = new SingleDemo3();
                }
            }
        }
        return singleDemo3;
    }
}

class SingleDemo4{//懒汉式另一种解决方案
    private SingleDemo4(){}
    private static class InstanceHolder{
        public static SingleDemo4 singleDemo4 = new SingleDemo4();
    }
    public static SingleDemo4 getInstance(){
        return InstanceHolder.singleDemo4;//这里导致初始化
    }
}



//3 找出数组中重复的数字
//描述：数组长度为n，存入的数组在0-(n-1)，找出数组中重复的数字
//下面算法不能找出所有重复数字
class FindDuplication{//时间复杂度 O(n) 空间复杂度O(1)
    public static void main(String[] args){
        int[] arr = {2,3,1,0,2,5,3};
        int res = findDuplication(arr);
        System.out.println(res);
    }
    public static int findDuplication(int[] arr){
        if (arr == null || arr.length <= 1){
            return -1;
        }
        for (int i = 0; i < arr.length; i++){
            if (arr[i] < 0 || arr[i] > arr.length - 1)
                return -1;
        }
        for (int i = 0; i < arr.length; i++){
            if (arr[i] != i){
                if (arr[i] != arr[arr[i]]){
                    swap(arr, i, arr[i]);
                }else {
                    return arr[i];
                }
            }
//            if (arr[i] == i){
//                continue;
//            } else if (arr[i] != arr[arr[i]]){
//                swap(arr, i, arr[i]);
//            }else {
//                result = arr[i];
//                break;
//            }
        }
        return -1;
    }
    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


class FindDuplication1{//时间复杂度 O(n) 空间复杂度O(n)
    public static void main(String[] args){
//        int[] arr = {2,3,1,0,2,5,3};
        int[] arr = {2,4,1,0,6,5,3};
        int res = findDuplication(arr);
        System.out.println(res);
    }
    public static int findDuplication(int[] arr){
        if (arr == null || arr.length <= 1){
            return -1;
        }
        for (int i = 0; i < arr.length; i++){
            if (arr[i] < 0 || arr[i] > arr.length - 1)
                return -1;
        }
        int[] hash = new int[arr.length];
        for (int i = 0; i < arr.length; i++){
            if (hash[arr[i]] == 0){
                hash[arr[i]] = arr[i];
            }else {
                return arr[i];
            }
        }
        return -1;
    }
}


//不修改数组找出重复元素
//描述：长度为n+1，数组元素区间1--(n)
//可以使用hash来处理，时间复杂度 O(n) 空间复杂度O(n)
//下面算法不能找出所有重复数字
class FindDuplicationNoChange{//通过二分思想来解决问题  时间复杂度 O(nlog(n)) 空间复杂度O(1)
    public static void main(String[] args){
        int[] arr = {2,3,5,4,3,2,6,7};
        int ans = findDuplicationNoChange(arr);
        System.out.println(ans);
    }
    public static int findDuplicationNoChange(int[] arr){
        if (arr == null || arr.length <= 1)
            return -1;
        for (int m : arr){
            if (m < 1 || m >= arr.length)
                return -1;
        }
        int start = 1;
        int end = arr.length - 1;
        while (start <= end){
            int middle = (start + end) / 2;
            System.out.println(middle);
            int numCount = count(arr,start,middle);
            System.out.println("( " +start + " -- " + middle+ " )" + " : " + numCount);
            if (start == end){
                if (numCount > 1)
                    return start;
                else
                    break;
            }
            if (numCount > (middle - start + 1))
                end = middle;
            else
                start = middle + 1;
        }
        return -1;
    }

    public static int count(int[] arr, int start, int end){
        if (arr == null)
            return 0;
        int num = 0;
       for (int m : arr){
            if (m >= start && m <= end){
                num++;
            }
        }
        return num;
    }
}



//4 二维数组中的查找
//描述：在一个二维数组中，每一行从左到右递增，每一列从上到下递增，
//输入一个这样的二维数组和一个数字，判断该数字是否在二维数组中

//可以选区右上角开始，也可以选择左下角开始，这样求解时不存在重复区域，基于数组的特性
class FindNumInArray{
    public static void main(String[] args){
        int[][] arr = {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        int target = 3;
        boolean flag = isContain(arr, target);
        System.out.println(flag);
    }
    public static boolean isContain(int[][] arr, int target){//这个写法比下一个写法好。推荐使用这个
        if (arr == null)
            return false;
        int i = 0;
        int j = arr[0].length - 1;
        while (i < arr.length && j >= 0){//两者条件必须同时满足
            if (arr[i][j] == target){
                System.out.println(i);
                System.out.println(j);
                return true;
            } else if (arr[i][j] > target){
                --j;
            }else {
//                System.out.println(i);
                ++i;
            }
        }
        return false;
    }

    public static boolean isContain1(int[][] arr, int target){
        if (arr == null)
            return false;
        int i = 0;
        int j = arr[0].length - 1;
        while (i < arr.length){
            while (j >= 0){
                if (arr[i][j] == target){
                    System.out.println(i);
                    System.out.println(j);
                    return true;
                } else if (arr[i][j] > target){
                    --j;
                }else {
                    System.out.println(i);
                    ++i;
                }
            }
            if (j == -1 && arr[i][j + 1] > target)
                break;
        }
        return false;
    }
}


//5 替换空格
//描述：把字符数组中空格变为 %20 代替
//思路：从后向前遍历
class ReplaceSpace{
    public static void main(String[] args){
        String str = "We are Heppy";
        char[] cha = str.toCharArray();
        char[] ans = replaceSpace(cha);
        System.out.println(new String(ans));
    }
    public static char[] replaceSpace(char[] cha){
        if (cha == null)
            return null;
        int spaceCount = 0;
        for (char c : cha){
            if (c == ' '){
                ++spaceCount;
            }
        }
        int len = cha.length + 2 * spaceCount;
        char[] ansChar = new char[len];//在这里创建方法内数组然后传出容易造成内存泄露
        for (int i = cha.length - 1; i >= 0; i--){
            if (cha[i] != ' '){
                ansChar[--len] = cha[i];
            }else {
                ansChar[--len] = '0';
                ansChar[--len] = '2';
                ansChar[--len] = '%';
            }
        }
        cha = ansChar;
        return cha;
    }
}



/*
6 从尾到头打印链表
 */
class ListNode{
    public int value;
    public ListNode next;
    public ListNode(int value){this.value = value;}
}
class PrintListReversingly{//使用栈
    public static void main(String[] args){
        ListNode first = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(3);
        ListNode four = new ListNode(4);
        ListNode five = new ListNode(5);
        first.next =two;
        two.next = three;
        three.next = four;
        four.next = five;
//        printListReversingly(first);
        printListReversingly1(first);

    }
    public static void printListReversingly(ListNode head){//使用栈
        if (head == null)
            return;
        Stack<ListNode> stack = new Stack<ListNode>();
        while (head != null){
            stack.push(head);
            head = head.next;
        }
        System.out.println(stack.size());
        while (!stack.isEmpty()){
            ListNode temp = stack.pop();
            System.out.println(temp.value);
        }
    }

    public static void printListReversingly1(ListNode head){//使用递归
        if (head != null){
            if (head.next != null){
                printListReversingly1(head.next);
            }
            System.out.println("kkk" + head.value);
        }
    }

}



/*
二叉树
描述：分别用递归和非递归方法实现二叉树的前、中、后序遍历
 */
class BiTreeNode{
    public int value;
    public BiTreeNode left;
    public BiTreeNode right;
    public BiTreeNode(int value){this.value = value;}

}
//递归
class RecursiveTraverse{
    public void preOrderRecur(BiTreeNode head){
        if (head == null)
            return;
        System.out.print(head.value + " ");
        preOrderRecur(head.left);
        preOrderRecur(head.right);
    }

    public void inOrderRecur(BiTreeNode head){
        if (head == null)
            return;
        inOrderRecur(head.left);
        System.out.print(head.value + " ");
        inOrderRecur(head.right);
    }

    public void posOrderRecur(BiTreeNode head){
        if (head == null)
            return;
        inOrderRecur(head.left);
        inOrderRecur(head.right);
        System.out.print(head.value + " ");
    }
}

class UnRecursiveTraverse{
    public void preOrderUnRecur(BiTreeNode head){
        System.out.println("pre-order: ");
        if (head != null) {
            Stack<BiTreeNode> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()){
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null)
                    stack.push(head.right);
                if (head.left != null)
                    stack.push(head.left);
            }
        }
        System.out.println();
    }

    public void inOrderUnRecur(BiTreeNode head){
        System.out.println("in - order: ");
        if (head != null){
            Stack<BiTreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || head != null){
                if (head != null){
                    stack.push(head);
                    head = head.left;
                }else {
                    head = stack.pop();
                    System.out.print(head.value + " ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }

    public void posOrderUnRecur1(BiTreeNode head){//使用双栈
        //将head放入s1，不断从s1弹出节点，将其左右节点放入s1，
        // 每次从s1弹出的节点放入s2，打印出s2，即为后续遍历结果
        System.out.println("pos - order: ");
        if (head != null){
            Stack<BiTreeNode> s1 = new Stack<>();
            Stack<BiTreeNode> s2 = new Stack<>();
            s1.push(head);
            while (!s1.isEmpty()){
                head = s1.pop();
                s2.push(head);
                if (head.left != null)
                    s1.push(head.left);
                if (head.right != null)
                    s1.push(head.right);
            }
            while (!s2.isEmpty()){
                System.out.print(s2.pop().value + " ");
            }
        }
        System.out.println();
    }

    public void posOrderUnRecur2(BiTreeNode head){//使用一个栈
        System.out.println("pos - order: ");
        if (head != null){
            Stack<BiTreeNode> stack = new Stack<>();
            stack.push(head);
            BiTreeNode currentStackTop = null;//指向栈顶节点
            while (!stack.isEmpty()){
                currentStackTop = stack.peek();
                if (currentStackTop.left != null && head !=currentStackTop.left && head != currentStackTop.right){
                    stack.push(currentStackTop.left);
                }else if (currentStackTop.right != null && currentStackTop.right != head){
                    stack.push(currentStackTop.right);
                }else {
                    System.out.print(stack.pop().value + " ");
                    head = currentStackTop;//用head来表示最近一次处理过的节点
                }
            }
        }
        System.out.println();
    }
}


/*
7 重建二叉树      ///////////////需要学习
描述：输入某二叉树前序和中序遍历的结果，请重建该二叉树
假设输入的前序和中序遍历的结果中都不含重复数字，例如：
前序：{1，2，4，7，3，5，6，8}
中序：{4，7，2，1，5，3，8，6}
 */
class ConstructBiTree{
    public BiTreeNode Construct(int[] preOrder, int[] inOrder) throws Exception{
        if (preOrder == null || inOrder == null || preOrder.length <= 0 || inOrder.length == 0)
            return null;
        return constructCore(preOrder,0, preOrder.length -1,
                inOrder, 0, inOrder.length - 1);
    }
    public BiTreeNode constructCore (int[] preOrder, int startPreOrder, int endPreOrder,
                                    int[] inOrder, int startInOrder, int endInOrder)throws Exception{
        //前序遍历的第一个节点是根节点
        int rootValue = preOrder[startPreOrder];
        BiTreeNode root = new BiTreeNode(rootValue);
        root.left = root.right = null;
        if (startPreOrder == endPreOrder){
            if (startInOrder == endInOrder && preOrder[startPreOrder] == preOrder[endPreOrder]){
                return root;
            }else throw new Exception("Invalid input");
        }

        //在中序遍历序列中找到根节点的值
        int rootInOrder = startInOrder;
        while (rootInOrder <= endInOrder && inOrder[rootInOrder] != rootValue)
            ++rootInOrder;
        if (rootInOrder == endInOrder && inOrder[rootInOrder] != rootValue)
            throw new Exception("Invalid input");
        int leftLength = rootInOrder - startInOrder;
        int leftPreOrderEnd = startPreOrder + leftLength;
        if (leftLength > 0){
            //构建左子树
            root.left = constructCore(preOrder,startPreOrder + 1, leftPreOrderEnd,
                    inOrder, startInOrder,rootInOrder - 1);
        }
        if (leftLength < endPreOrder - startInOrder){
            //构建右子树
            root.right = constructCore(preOrder, leftPreOrderEnd + 1, endPreOrder,
                    inOrder, rootInOrder + 1, endInOrder);
        }
        return root;
    }
}


/*
8 二叉树的下一个节点
描述：给定一个二叉树和其中的一个节点，如何找出中序遍历序列的下一个节点？
树中的节点除了有两个分别指向左右子树的指针，还有一个指向父节点的指针
 */


