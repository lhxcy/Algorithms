package JianZhiOffer;

import ZuoChengYun_exercise._40_reverseList;
import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;

import java.util.*;
import java.util.concurrent.Executor;

/**
 * Created by XCY on 2017/12/19.
 */
class SwapArray{
    public static void swap(Object[] arr, int i, int j){
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
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
    public BiTreeNode parent;
    public BiTreeNode(int value){this.value = value;}
    public BiTreeNode(){}

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
        if (rootInOrder == endInOrder && inOrder[rootInOrder] != rootValue)//这点是不是有问题，rootInOrder感觉应该是endInOrder+1
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
思路：
1：如果一个节点有右子树，那么下一个节点就是右子树中最左子节点
2：如果一个节点没有右子树且它是父节点的左子节点，则下一个节点就是父节点
3：如果一个节点没有右子树且它是父节点的右子节点，我们可以沿着指向父节点的指针一直向上，
直到找到一个是它父节点的左子节点的节点，如果这个节点存在，那么这个节点的父节点就是所求节点
 */
class GetNextNodeInBiTree{
    public BiTreeNode getNextNodeInInOrder(BiTreeNode node){
        if (node == null)
            return null;
        BiTreeNode nextBiTreeNode = null;
        if (node.right != null){
            BiTreeNode nodeRightMostLeftBiTreeNode = node.right;
            while (nodeRightMostLeftBiTreeNode.left != null){
                nodeRightMostLeftBiTreeNode = nodeRightMostLeftBiTreeNode.left;
            }
            nextBiTreeNode = nodeRightMostLeftBiTreeNode;
        }else if (node.parent != null){
            BiTreeNode currentBiTreeNode = node;
            BiTreeNode parentBiTreeNode = node.parent;
            while (parentBiTreeNode != null && currentBiTreeNode != parentBiTreeNode.left){
                currentBiTreeNode = parentBiTreeNode;
                parentBiTreeNode = parentBiTreeNode.parent;
            }
            nextBiTreeNode = parentBiTreeNode;
        }
        return nextBiTreeNode;
    }
}

/*
9； 用两个栈实现队列
描述：实现appendTail和deleteHead
 */
class ImplementQueueByStack<T>{
    Stack<T> stack1 = new Stack<T>();
    Stack<T> stack2 = new Stack<T>();
    public void appendTail(T value){
        stack1.push(value);
    }
    public T deleteHead() throws Exception{
        if (stack2.isEmpty()){
            while (!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty())
            throw new Exception("queue is empty");
        T pop = stack2.pop();
        return pop;
    }
}


/*
10:  斐波那契数列
描述：求斐波那契数列的第n项
 */
class Fibonacci{
    public int fibonacci(int n){
        if (n <= 0)
            return 0;
        if (n == 1)
            return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
    public int fibonacci1(int n){
        int f0 = 0;
        int f1 = 1;
        int ans = 0;
        if (n <= 1){
            return n == 1 ? 1 : 0;
        }
        for (int i = 2; i <= n; i++){
            ans = f0 + f1;
            f0 = f1;
            f1 = ans;
        }
        return ans;
    }
}


/*
快速排序
 */
class QuickSort{
    public static void main(String[] args){
        int[] arr = {3,6,2,5,2,1};
        quickSort(arr, 0, arr.length - 1);
        for (int i : arr)
            System.out.println(i);
    }
    public static void quickSort(int[] arr, int start, int end){
        if (start == end)
            return;
        int index = partition(arr, start, end);
        if (index == -1){
            return;
        }
        if (index > start){
            quickSort(arr, start, index - 1);
        }
        if (index < end){
            quickSort(arr, index + 1, end);
        }
    }
    public static int partition(int[] arr, int start, int end){
        if (arr == null || start < 0){
            return -1;
        }
        int small = start - 1;
        for (int index = start; index < end; index++){
            if (arr[index] < arr[end]){
                ++small;
                if (small != index)
                    Swap(arr, index, small);
            }
        }
        ++small;
        Swap(arr, end, small);
        return small;
    }
    public static void Swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //第二版快排
    public static int partition1(int[] arr, int start, int end){
        int i = start, j = end + 1;
        while (true){
            while (arr[++i] < arr[start]) if (i == end) break;
            while (arr[--j] < arr[start]) if (j == start) break;
            if (i >= j) break;
            Swap(arr, i, j);
        }
        Swap(arr, start, j);
        return j;
    }
}


/*
11： 旋转数组的最小数字
描述：把一个数组最开始的若干元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，
输出旋转数组的最小元素。例如，数组{3，4，5，1，2}是{1，2，3，4，5}的一个旋转，该数组最小值为1
 */
class MinInRotaeArray{
    //最简单方法，直接遍历求最小

    //another
    public int min(int[] arr){
        if (arr == null || arr.length < 0){
            return -1;
        }
        int start = 0;
        int end = arr.length - 1;
        int ans = start;
        while (arr[start] >= arr[end]){
            if (end - start == 1){
                ans = arr[end];
                break;
            }
            ans = (start + end) / 2;
            //如果start、end、ans指向的三个数相等，则只能顺序查找
            if (arr[start] == arr[end] && arr[start] == arr[ans])
                return minInOrder(arr, start, end);
            if (arr[ans] >= arr[start])
                start = ans;
            else if (arr[ans] <= arr[end])
                end = ans;
        }
        return ans;
    }
    public int minInOrder(int[] arr, int start, int end){
        int result = start;
        for (int index = start + 1; index <= end; index++){
            if (arr[index] < arr[result])
                result = index;
        }
        return result;
    }
}


/*
回溯法可以看成蛮力法的升级版，它从解决问题每一步的所有可能选项系统地选择出一个可行的解决方案
通常回溯法算法适合用递归实现代码
12: 矩阵中的路径
描述：设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串的路径。路径可以从矩阵中的任意
一格开始，每一步可以在矩阵中向左、右、上、下移动一格。如果一条路径经过了矩阵的某一格，那么该路径不能
再次进入该格子。
思路：任选一格子作为路径的起点，假设某格子字符为ch，并且这个格子将对应于路径上的第i个字符。如果
路径上的第i个字符不是ch，那么这个各自不可能处于路径上的第i个位置。如果是，那么到相邻各自寻找路径上
的第i+1个字符。
 */

class PathInMat{
    public boolean hasPath(char[][] mat, int rows, int cols, char[] chs){
        if (mat == null || rows < 1 || cols < 1 || chs == null)
            return false;
        boolean[][] hasVisit = new boolean[rows][cols];
        int pathLength = 0;
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                if (hasPathCore(mat,row,col,chs,pathLength,hasVisit))
                    return true;
            }
        }
        return false;
    }

    boolean hasPathCore(char[][] mat,int row, int col, char[] chs, int pathLength, boolean[][] hasVisit){
        if (pathLength == chs.length - 1)
            return true;
        boolean hasPathInMat = false;
        if (row >= 0 && row < mat.length && col >= 0 && col < mat[0].length &&
                mat[row][col] == chs[pathLength] && ! hasVisit[row][col]){
            ++pathLength;
            hasVisit[row][col] = true;
            hasPathInMat = hasPathCore(mat,row,col - 1, chs, pathLength, hasVisit) ||
                    hasPathCore(mat,row - 1,col, chs, pathLength, hasVisit) ||
                    hasPathCore(mat,row,col + 1, chs, pathLength, hasVisit) ||
                    hasPathCore(mat,row + 1,col, chs, pathLength, hasVisit);

            if (! hasPathInMat){
                --pathLength;
                hasVisit[row][col] = false;
            }
        }
        return hasPathInMat;
    }
}


/*
13：机器人的运动范围
描述：地上有一个m行n列的方格。一个机器人从坐标（0，0）的格子开始移动，可以左、右、上、下移动一格，
但不能进入行坐标和列坐标的数位之和大于k的格子。例如，k=18，机器人不能进入（35，38）因为3+5+3+8=19.
请问机器人能够到达多少格子？
 */

class MovingCountInMat{
    public int movingCount(int thresHold, int rows, int cols){
        if (thresHold < 0 || rows < 0 || cols < 0)
            return 0;
        boolean hasVisit[][] = new boolean[rows][cols];
        int count = movingCountCore(thresHold,rows,cols,0,0,hasVisit);
        return count;
    }
    public int movingCountCore(int thresHold, int rows, int cols, int row, int col, boolean[][] hasVisit){
        int count = 0;
        if (check(thresHold, rows, cols, row, col, hasVisit)){
            hasVisit[row][col] = true;
            count = 1 + movingCountCore(thresHold, rows, cols, row - 1, col, hasVisit) +
                    movingCountCore(thresHold, rows, cols, row, col - 1, hasVisit) +
                    movingCountCore(thresHold, rows, cols, row + 1, col, hasVisit)+
                    movingCountCore(thresHold, rows, cols, row, col + 1, hasVisit);
        }
        return count;
    }
    public boolean check(int thresHold, int rows, int cols,int row, int col, boolean[][] hasVisit){
        if (row > 0 && row < rows && col > 0 && col < cols &&
        !hasVisit[row][col] && getDigitSum(row)  + getDigitSum(col) < thresHold)
            return true;
        return false;
    }
    public int getDigitSum(int number){
        int sum = 0;
        while (number > 0){
            sum += sum % 10;
            number /= 10;
        }
        return sum;
    }
}


/*
14：剪绳子
描述：给你一根长度为n的绳子，请把绳子剪成m段（m,n都是整数，n>1并且m>1），每段绳子的长度记为k[0],k[1],,,k[m]
请问k[0]*k[1]*...*k[m]的最大是多少？例如，n为8，m为3时，最大乘积为2*3*3=18
两种思路：1：常规思路，时间复杂度O(n^2),空间复杂度O(n)  //动态规划
        f(n) = max(f(i)*f(n-i))    0<i<n,  f(2) = 1, f(3) = max(1*2,1*1*1) = 2
          2：O(1)的时间和空间复杂度  //贪婪算法
 */

class MaxCut{
    public int maxProductAfterCutting_Solution1(int length){//动态规划
        if (length < 2)
            return 0;
        if (length == 2)
            return 1;
        if (length == 3)
            return 2;
        int[] products = new int[length + 1];
        products[0] = 0;
        products[1] = 0;
        products[2] = 1;
        products[3] = 2;
        int max = 0;
        for (int i = 4; i <= length; i++){
            max = 0;
            for (int j = 1; j <= i; j++){//这里j可以只到i的一半
                int product = products[j] * products[i - j];
                if (max < product)
                    max = product;
            }
            products[i] = max;
        }
        max = products[length];
        return max;
    }


    public int maxProductAfterCutting_Solution2(int length){//贪婪算法  没什么用
        if (length < 2)
            return 0;
        if (length == 2)
            return 1;
        if (length == 3)
            return 2;
        //尽可能多的去剪长度为3的绳子段
        int timesOf3 = length / 3;
        //当绳子最后剩下的长度为4的时候，不能再剪长度为3的绳子段
        //此时更好的方法时把绳子剪成藏毒为2的两段
        if (length - timesOf3 * 3 == 1){
            timesOf3 -= 1;
        }
        int timesOf2 = (length - timesOf3 * 3) / 2;
        return (int)(Math.pow(3,timesOf3))*(int)(Math.pow(2,timesOf2));
    }
}


/*
15：二进制中的个数
描述：实现一个函数，输入一个整数，输出该数二进制表示中1的个数
 */

class NumberOfInt{
    public int countOf1_soltion1(int number){
        int count = 0;
        int flag = 1;
        while (flag > 0){
            if ((number & flag) > 1){
                count++;
            }
            flag = flag << 1;
        }
        return count;
    }

    /*
    思路：把一个整数减去1，再和原整数做与运算，会把该整数最右边的1变为0.那么一个整数的二进制表示中
    有多少个1就可以进行多少次这样的操作
     */
    public int countOf1_soltion2(int number){
        int count = 0;
        while (number != 0){
            ++count;
            number = (number - 1) & number;
        }
        return count;
    }
}


/*
16：数值的整数次方
描述：实现函数double power（double base， int exponent），求base的exponent次方。
不得使用库函数，不用考虑大数问题。
 */
class MyOwnPow{
    boolean flag = false;
    public static void main(String[] args) throws Exception{
        double x = 2.0000;
        double ans = myPow(2,5);
        System.out.println(ans);
    }

    public static double myPow(double x, int n) {//这个可以
        boolean flag = false;
        if(n < 0){
            n = -(n+1);
            x = 1 / x;
            flag = true;
        }
        double result = 1.0, temp = x;
        for( ; n > 0; n = n >> 1){
            if ((n & 0x1) == 1)//如果是奇数
                result *= temp;
            temp *= temp;
        }
        if(flag){
            result *= x;
        }
        return result;
    }

    public double myPow1(double x, int n) {
        if(n == -2147483648){//这块会让速度变慢，上面方法好
            if(Math.abs(x - 1.0) < 0.00000001 || Math.abs(x + 1.0) < 0.00000001)
                return 1;
            return 0;
        }
        if(n < 0){
            n = -n;
            x = 1 / x;
        }
        double result = 1.0;
        for( ; n > 0; n = n >> 1){
            if ((n & 0x1) == 1)
                result *= x;
            x *= x;
        }
        return result;
    }
}


/*
17：打印从1到最大的n位数
描述：输入数字n，按顺序打印出从1到最大的n位十进制数。比如输入3，则打印出1、2、3。。。999
 */

class PrintDataTillNMax{
    public static void main(String[] args){
        int n = 2;
        print1ToMaxOfNDigits(n);
        System.out.println();
        print1ToMaxOfNDigits1(n);
    }
    public static void print1ToMaxOfNDigits(int n){
        if (n <= 0)
            return;
        char[] number = new char[n];
        for (int i = 0; i < number.length; i++)
            number[i] = '0';
        while (!Incremen(number)){
            printNumber(number);
        }
    }
    public static boolean Incremen(char[] number){
        boolean isOverFlow = false;
        int nTakeOver = 0;
        int nLength = number.length;
        for (int i = nLength - 1; i >= 0; i--){
            int nSum = number[i] - '0' + nTakeOver;
            if (i == nLength - 1)
                ++nSum;
            if (nSum >= 10){
                if (i == 0)
                    isOverFlow = true;
                else {
                    nSum -= 10;
                    nTakeOver = 1;
                    number[i] = (char) (nSum + '0');
                }
            }else {
                number[i] = (char)(nSum + '0');
                break;//小于10时，从这里跳出，就不用考虑nTakeOver=0的问题了
            }
        }
        return isOverFlow;
    }
    public static void printNumber(char[] number){
        boolean isBegining0 = true;
        int nLength = number.length;
        for (int i = 0; i < nLength; i++){
            if (isBegining0 && number[i] != '0'){
                isBegining0 = false;
            }
            if (!isBegining0){
                System.out.printf("%c", number[i]);
            }
        }
        if (isBegining0)
            return;
        System.out.printf("\t");
    }


    //方法2：用全排列思想解决
    public static void print1ToMaxOfNDigits1(int n){//本质就是一个三层循环
        if (n <= 0)
            return;
        char[] number = new char[n];
        for (int i = 0; i < 10; i++){
            number[0] = (char)(i + '0');
//            System.out.println(number[0]);
            print1ToMaxOfNDigitsRecur(number, n, 0);
        }
    }
    public static void print1ToMaxOfNDigitsRecur(char[] number, int length, int index){
        if (index == length - 1){
            printNumber(number);
            return;
        }
        for (int i = 0; i < 10; i++){
            number[index + 1] = (char) (i + '0');
            print1ToMaxOfNDigitsRecur(number,length,index + 1);
        }
    }
}


/*
18：（一）删除链表节点
描述：在O(1)时间内删除链表节点。   给定单向链表的头指针和一个节点指针，定义一个函数在O(1)时间内删除该节点
 */
class MyDeleteListNode{
    void deleteNode(ListNode head, ListNode nodeToDelete){//该方法假设要删除节点在链表中
        if (head == null || nodeToDelete == null)
            return;
        //判断要删除的节点是不是尾节点
        if (nodeToDelete.next != null){
            ListNode tempNode = nodeToDelete.next;
            nodeToDelete.value = tempNode.value;
            nodeToDelete.next = tempNode.next;
            tempNode = null;
        }else if (head == nodeToDelete){//只有头结点，刚好等于要删除的节点
            head = null;
            nodeToDelete = null;
        }else {
            ListNode pre = head;
            while (pre.next != nodeToDelete){
                pre = pre.next;
            }
            pre.next = null;
            nodeToDelete = null;
        }
    }
}

/*
18：（二）删除链表中的重复节点（即值相同的节点）
描述：给定排序链表
 */
class DeleteDuplicationNodeInSortedListNode{
    void deleteDuplicationListNode(ListNode head){
        if (head == null)
            return;
        ListNode pre = head;
        ListNode tempNode = head;
        while (tempNode != null){
            if (pre.value == tempNode.value){
                pre.next = tempNode.next;
            }else {
                pre = tempNode;
            }
            tempNode = tempNode.next;
        }
    }
}


/*
19：正则表达式匹配
描述：请实现一个函数用来匹配包含'.'和'*'的正则表达式。模式中的'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次，
匹配是指字符串的所有字符匹配整个模式。例如：字符串“aaa”与模式“a.a”和“ab*ac*a”匹配，但是与“aa.a”和“ab*a"均不匹配
 */

class MatchClass{
    public  static void main(String[] args){
        String str = "aaabbcd";
        String pattern = "aaabbcdf*";
        char[] chs = str.toCharArray();
        char[] chp = pattern.toCharArray();
        System.out.println(match(chs, chp));
    }
    public static boolean match(char[] str, char[] pattern){
        if (str == null || pattern == null)
            return false;
        return matchCore(str, 0, pattern, 0);
    }
    public static boolean matchCore(char[] str, int strIndex, char[] pattern, int patternIndex){
        if (strIndex == str.length || patternIndex == pattern.length)
            return false;
        if (strIndex == str.length - 1 && (patternIndex == pattern.length - 1 || pattern[patternIndex + 2] == '*' || pattern[patternIndex + 1] == '.'))
            return true;
        if (strIndex != str.length - 1 && patternIndex == pattern.length - 1)
            return false;
        if (pattern[patternIndex + 1] == '*'){
            if (pattern[patternIndex] == str[strIndex] || (pattern[patternIndex] == '.' && strIndex != str.length - 1)){
                //move on the next state  一个
                return matchCore(str, strIndex + 1, pattern, patternIndex +1) ||
                        //stay on the current state 多个
                        matchCore(str, strIndex + 1, pattern, patternIndex) ||
                        // ignore a '*' 0个
                        matchCore(str, strIndex, pattern, patternIndex + 2);
            }else {
                // ignore a '*'  0个，因为pattern[patternIndex] ！= str[strIndex]
                return matchCore(str, strIndex, pattern, patternIndex + 2);
            }
        }
        if (str[strIndex] == pattern[patternIndex] || (pattern[patternIndex] == '.' && strIndex != str.length -1))
            return matchCore(str, strIndex + 1, pattern, patternIndex + 1);
        return false;
    }
}


/*
20：表示数值的字符串
描述：实现一个函数用来判断字符串是否表示数值（包括小数和整数）
例如，字符串“+100”，“5e2”，“-123”，“3.146”，“-1E-16”都是数值
但是“12e”，“1a3.14”等不是
表示数值的字符串遵循模式   A[.B][e|EC]或   .B[e|EC]
其中A表示数值的整数 部分，B表示小数部分，C表示指数部分。A、C为整数（可正可负），B只能表示整数
 */
class JudgeNumberInString{//1
    public static void main(String[] args){
//        String str = "+3.14e3";
        String str = "+3.14";
        System.out.println(isNumber(str));
    }
    public static boolean isNumber(String str){
        if (str == null)
            return false;
        char[] chs = str.toCharArray();
        int[] index = {0};
        boolean numeric = scanInteger(chs, index);
        //如果出现'.'，则接下来是数字的小数部分
        if (index[0] != chs.length && chs[index[0]] == '.'){
            ++index[0];
            //下面一行代码用 || 的原因：
            //1：小数部分可以没有整数部分。  2：小数点后可以没有数字
            //3：当然小数点前后都可以有数字
            numeric = scanUnsignedInteger(chs, index) || numeric;
        }
        //如果出现e或者E，则接下来是数字的指数部分
        if (index[0] != chs.length && (chs[index[0]] == 'e' || chs[index[0]] == 'E')){
            ++index[0];
            //下面使用&&的原因：
            //1：当e或者E前面没有数字时，整个字符串不能表示数字。如 .e1
            //2：当e或者E后没有数字时，整个字符串不能表示数字。如12e
            numeric = scanInteger(chs, index) && numeric;
        }
        return numeric && (index[0] == chs.length);
    }
    public static boolean scanInteger(char[] chs,  int[] index){
        if (index[0] != chs.length && (chs[index[0]] == '+' || chs[index[0]] == '-'))
            index[0] += 1;
        return scanUnsignedInteger(chs,index);
    }
    public static boolean scanUnsignedInteger(char[] chs, int[] index){
        int pre = index[0];
        while(index[0] != chs.length && chs[index[0]] >= '0' && chs[index[0]] <= '9'){
            ++index[0];
        }
        return index[0] > pre;
    }
}


class INT{//3
    public static int index=0;
}
class JudgeNumberInString1{//2
    private int index = 0;
    public static void main(String[] args){
//        String str = "+3.14e3";
        String str = "+33.14";
        System.out.println(new JudgeNumberInString1().isNumber(str));
    }
    public  boolean isNumber(String str){
        if (str == null)
            return false;
        char[] chs = str.toCharArray();
//        int[] index = {0};
//        Integer index = 0;
        boolean numeric = scanInteger(chs, this.index);
        //如果出现'.'，则接下来是数字的小数部分
        System.out.println("nnnnn   " + numeric);
        System.out.println("kkk   " + this.index);
        if (this.index != chs.length && chs[this.index] == '.'){
            this.index++;
            //下面一行代码用 || 的原因：
            //1：小数部分可以没有整数部分。  2：小数点后可以没有数字
            //3：当然小数点前后都可以有数字
            numeric = scanUnsignedInteger(chs, this.index) || numeric;
        }
        //如果出现e或者E，则接下来是数字的指数部分
        if (this.index != chs.length && (chs[this.index] == 'e' || chs[this.index] == 'E')){
            ++this.index;
            //下面使用&&的原因：
            //1：当e或者E前面没有数字时，整个字符串不能表示数字。如 .e1
            //2：当e或者E后没有数字时，整个字符串不能表示数字。如12e
            numeric = scanInteger(chs, this.index) && numeric;
        }
        return numeric && (this.index == chs.length);
    }
    public  boolean scanInteger(char[] chs,  int index){
        if (this.index != chs.length && (chs[this.index] == '+' || chs[this.index] == '-'))
            this.index += 1;
//            index.valueOf(index++);
        return scanUnsignedInteger(chs,this.index);
    }
    public  boolean scanUnsignedInteger(char[] chs, int index){
        int pre = this.index;
        while(this.index != chs.length && chs[this.index] >= '0' && chs[this.index] <= '9'){
            ++this.index;
//            index.valueOf(index++);
        }
        return this.index > pre;
    }
}



/*
21：调整数组顺序使奇数位于偶数前面
描述：输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
所有偶数位于数组的后半部分
 */

class MoveOddInArray{
    public void reorderOddEven(int[] arr){
        if (arr == null)
            return;
        int index = 0; //记录奇数下标
        while ((arr[index]&0x1) == 1) ++index;
        for (int i = index; i < arr.length; i++){
            if ((arr[i]&0x1) == 1){
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index++] = temp;
            }
        }
    }

    void reorderOddEven2(int[] arr){
        if (arr == null)
            return;
        int index = 0; //记录奇数下标
        while ((arr[index]&0x1) == 1) ++index;
        for (int i = index; i < arr.length; i++){
            if (isOdd(arr[i])){
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index++] = temp;
            }
        }
    }
    boolean isOdd(int i){
        return (i*0x1) == 1;
    }

    public static void main(String[] args){
        MoveOddInArray mia = new MoveOddInArray();
        int[] arr = {1,2,3,4,5,6,7,8,9};
        for (int t : arr){
            System.out.print(t + " ");
        }
        System.out.println();

        mia.reorderOddEven(arr);
        for (int t : arr){
            System.out.print(t + " ");
        }
    }
}



/*
22：链表中倒数第K个节点
描述：输入一个链表，输出链表中倒数第K个节点
 */
class FindKthListNode{
    public static void main(String[] args){

    }
    ListNode findKthToTail(ListNode head, int k){//默认长度大于K
        ListNode preNode = head;
        ListNode lastNode = head;
        for (int i = 0; i < k - 1; i++){
            preNode = preNode.next;
        }
        while (preNode.next != null){
            preNode = preNode.next;
            lastNode = lastNode.next;
        }
        return lastNode;
    }

    ListNode findKthToTail1(ListNode head, int k){//考虑长度小于K的情况
        if (head == null || k == 0)
            return null;
        ListNode preNode = head;
        ListNode lastNode = head;
        for (int i = 0; i < k - 1; i++){
            if (preNode.next != null)
                preNode = preNode.next;
            else
                return null;//跳出当前方法
     }
        while (preNode.next != null){
            preNode = preNode.next;
            lastNode = lastNode.next;
        }
        return lastNode;
    }
}

/*
23：链表中环的入口节点
描述：如果一个链表包含环，找出环的入口节点
1：确定有没有环
    定义2个指针，同时从头结点出发，一个一次走1步，一个一次走2步，如果走的快的指针追上走的慢的指针，
    证明有环，如果走的快的指针走到结尾也没有追上慢的指针，则没环
2：找到入口节点
    同样用2个指针解决问题，定义两个这阵p、q，初始时指向头结点。如果链表的环中有n个节点，则指针p先向前
    移动n步，然后两个指针以相同的速度向前移动。当第二个指针指向环的入口节点时，第一个指针已经绕着环走了一圈，
    又回到了入口节点
    接下来需要计算环中有几个节点，利用1中的节点，一边向前一边计数，直到再回到该节点，求出环中节点的个数
 */
class FindNodeInCircle{
    ListNode meetingNode(ListNode head){//如果存在环，返回快慢两指针遇到的节点，若没有环，返回null
        if (head == null)
            return null;
        ListNode slowNode = head.next;
        if (slowNode == null)
            return null;
        ListNode fastNode = slowNode.next;
        if (fastNode == null)
            return null;
        while (fastNode != null && slowNode != null){
            if (fastNode == slowNode)
                return fastNode;
            slowNode = slowNode.next;
            fastNode = fastNode.next;
            if (fastNode != null){//走2步，通过这种方法解决空指针问题，如果一次走2步，可能存在空指针
                fastNode = fastNode.next;
            }
        }
        return null;
    }
    ListNode EntryNodeOfLoop(ListNode head){
        ListNode meetNode = meetingNode(head);
        if (meetNode == null)
            return null;
        int lengthOfLoop = 1;
        ListNode tempNode = meetNode;
        while (tempNode.next != meetNode){//求出环的长度
            ++lengthOfLoop;
            tempNode = tempNode.next;
        }
        tempNode = head;
        for (int i = 0; i < lengthOfLoop; i++){
            tempNode = tempNode.next;
        }
        ListNode listNode = head;
        while (listNode != tempNode){//求环的入口
            listNode = listNode.next;
            tempNode = tempNode.next;
        }
        return listNode;
    }
}


/*
24：反转链表
描述：定义一个函数，输入一个链表头结点，反转给量表并输出反转后链表的头结点
 */
class ReverseListNode{
    ListNode reverse(ListNode head){
        if (head == null)
            return null;
        ListNode pre = null;
        ListNode tempNode;
        while (head != null){
            tempNode = head.next;
            head.next = pre;
            pre = head;
            head = tempNode;
        }
        return pre;
    }
}


/*
25：合并两个排序的链表
描述：输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是排序的
 */

class MergeTwoListNode{
    ListNode mergeListNodeRecur(ListNode head1, ListNode head2){
        if (head1 == null)
            return head2;
        if (head2 == null)
            return head1;
        ListNode mergeHead = null;
        if (head1.value < head2.value){
            mergeHead = head1;
            mergeHead.next = mergeListNodeRecur(head1.next, head2);
        }else {
            mergeHead = head2;
            mergeHead.next = mergeListNodeRecur(head1, head2.next);
        }
        return mergeHead;
    }

    ListNode mergeListNodeNoRecur(ListNode head1, ListNode head2){
        if (head1 == null)
            return head2;
        if (head2 == null)
            return head1;
        ListNode mergeHead = null;
        if (head1.value < head2.value){
            mergeHead = head1;
            head1 = head1.next;
        }else {
            mergeHead = head2;
            head2 = head2.next;
        }
        ListNode tempNode = mergeHead;
        while (head1 != null && head2 != null){
            if (head1.value < head2.value){
                tempNode.next = head1;
                tempNode = head1;
                head1 = head1.next;
            }else {
                tempNode.next = head2;
                tempNode = head2;
                head2 = head2.next;
            }
        }
        if (head1 == null)
            tempNode.next = head2;
        if (head2 == null)
            tempNode.next = head1;
        return mergeHead;
    }
}


/*
26：树的子结构
描述：输入两棵二叉树A、B，判断B是不是A的子结构
1：在树A中找到和树B的根节点的值一样的节点R
2：判断树A中以R为根节点的子树是不是包含和树B一样的结构
 */

class IsSubTree{
    boolean hasSubTree(BiTreeNode root1, BiTreeNode root2){
        if (root1 == null)
            return false;
        if (root2 == null)
            return true;
        boolean result = false;
        if (root1 != null && root2 != null){
            if (isEqual(root1.value, root2.value))
                result = isTree1HasTree2(root1, root2);
            if (!result)
                result = isTree1HasTree2(root1.left, root2);
            if (!result)
                result = isTree1HasTree2(root1.right, root2);
        }
        return result;
    }
    boolean isTree1HasTree2(BiTreeNode root1, BiTreeNode root2){
        if (root2 == null)
            return true;
        if (root1 == null)
            return false;
        if (!isEqual(root1.value, root2.value)){
            return false;
        }
        return isTree1HasTree2(root1.left,root2.left) &&
                isTree1HasTree2(root1.right, root2.right);
    }
    boolean isEqual(double num1, double num2){
        return Math.abs(num1 - num2) < 0.0000001;
    }
}


/*
27：二叉树的镜像
描述：定义一个函数，输入一棵二叉树，该函数输出它的镜像
 */

class MirrorOfBiTree{
    void mirrorRecur(BiTreeNode root){
        if(root == null || (root.left == null && root.right ==null))
            return;
        BiTreeNode tempNode = root.left;
        root.left = root.right;
        root.right = tempNode;
        if (root.left != null){
            mirrorRecur(root.left);
        }
        if (root.right != null){
            mirrorRecur(root.right);
        }
    }
}

/*
28：对称的二叉树
描述：实现函数判断二叉树是不是对称的。如果一棵二叉树和它的镜像是一样的，那么二叉树是对称的
 */
class BiTreeIsSymmetrical{
    boolean isSymmetrical(BiTreeNode root){
        return isSymmetricalCore(root,root);
    }
    boolean isSymmetricalCore(BiTreeNode root1, BiTreeNode root2){
        if (root1 == null && root2 == null)
            return true;
        if (root1 == null || root2 == null)
            return false;
        if (root1.value != root2.value)
            return false;
        return isSymmetricalCore(root1.left, root2.left) &&
                isSymmetricalCore(root1.right, root2.right);
    }
}


/*
29:顺时针打印矩阵
描述：输入一个矩阵，按照从外向里以顺时针的顺序一次打印出每一个数字
思路：定义一种遍历方式，先遍历父节点，再遍历它的右子节点，最后遍历左子节点，这种遍历方式称为 对称遍历算法
可以通过比较二叉树的前序遍历序列和对称遍历序列来判断二叉树是否为对称的，如果两个序列一样，则为对称的，需要考虑null
 */


class PrintMatrix{
    public static void main(String[] args){
        int[][] mat = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16},{17,18,19,20}};
        new PrintMatrix().printMatrixClockWisely(mat);
    }
    void printMatrixClockWisely(int[][] mat){
        if (mat == null || mat[0] == null || mat.length <= 0 || mat[0].length <= 0)
            return;
//        int start = 0;//注释的是一种方法
//        while (mat.length > start * 2 && mat[0].length > start * 2){
//            printMatrixInCircle(mat, start);
//            ++start;
//        }
        int lenRow = mat.length;//另一种写法
        int lenCol = mat[0].length;
        for (int start = 0; start < lenRow && start < lenCol; start++){
            printMatrixInCircle(mat,start);
            lenCol--;
            lenRow--;
        }
    }
    void printMatrixInCircle(int[][] mat, int start){
        if (mat == null || mat[0] == null)
            return;
        int endRow = mat.length - 1 - start;//行
        int endCol = mat[0].length - 1 - start;//列
        //1:从左到右打印一行
        for (int i = start; i <= endCol; i++){
            int num = mat[start][i];
            printNum(num);
        }
        //2:从上到下打印一列
        if (endRow > start){
            for (int i = start + 1; i <= endRow; i++){
                int num = mat[i][endCol];
                printNum(num);
            }
        }
        //3:从右向左打印一行
        if (endCol > start && endRow > start){
            for (int i = endCol - 1; i >= start; i--){
                int num = mat[endRow][i];
                printNum(num);
            }
        }
        //4:从下到上打印一列
        if (start < endCol && start < endRow - 1){
            for (int i = endRow - 1; i > start; i--){
                int num = mat[i][start];
                printNum(num);
            }
        }
    }
    void printNum(int x){
        System.out.print(x + " ");
    }
}


/*
30：包含min的栈
描述：定义栈的数据结构，子啊该类型上实现一个能够得到栈的最小元素的min函数
 */

class StackElement{
    int value;
    int min;
}
class MinStack{
    StackElement[] stack;
    int top;
    int size;
    private MinStack(){}
    public static MinStack getMinStack(){return new MinStack();}
}
class MyMinStack{
    public MinStack MyStackInit(){
        MinStack minStack = MinStack.getMinStack();
        minStack.stack = new StackElement[100];
        minStack.top = 0;
        minStack.size = 100;
        return minStack;
    }
    void push(MinStack minStack,int value) throws Exception{
        if (minStack.top > minStack.size)
            throw new Exception("out of stack space");
        minStack.stack[minStack.top].value = value;
        minStack.stack[minStack.top].min = minStack.top ==0 ? value : minStack.stack[minStack.top - 1].min;
        if (minStack.stack[minStack.top].min > value){
            minStack.stack[minStack.top].min = value;
        }
        ++minStack.top;
    }
    public int pop(MinStack minStack) throws Exception{
        if (minStack.top == 0)
            throw new Exception("stack is empty");
        return minStack.stack[--minStack.top].value;
    }
    public int min(MinStack minStack) throws Exception{
        if (minStack.top == 0)
            throw new Exception("stack is empty");
        return minStack.stack[minStack.top - 1].min;
    }
}


/*
31：栈的压入、弹出序列
描述：输入两个整数序列，第一个序列表示栈的压入序列，判断第二个是否为该栈的弹出序列。
假设压入栈的所有数字都不同
思路：建立一个辅助栈，把输入的序列一次压入辅助栈并按照第二个序列的顺序一次弹出序列
 */

class IsPopOrder{//默认长度一样
    boolean isPopOrder(int[] first, int[] second, int length){
        boolean possible = false;
        if (first != null && second != null && length > 0){
            Stack<Integer> stack = new Stack<>();
            int pushIndex = 0;
            int popIndex = 0;
            while (popIndex < length){
                while (stack.isEmpty() || stack.peek() != second[popIndex]){
                    if (pushIndex == length)
                        break;
                    stack.push(first[pushIndex]);
                    pushIndex++;
                }
                if (stack.peek() != second[popIndex])
                    break;
                stack.pop();
                popIndex++;
            }
            if (stack.isEmpty() && popIndex == length)
                possible = true;
        }

        return possible;
    }
}



/*
32：从上往下打印二叉树
描述：从上到下打印出二叉树的每一个节点，同一层，从左到右打印。即层次遍历二叉树
 */

class HierarchyPrintNodeOfBiTreeNode{
    void printFromTopToBottom(BiTreeNode root){
        if (root == null)
            return;
        Queue<BiTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            BiTreeNode tempNode = queue.peek();
            queue.poll();
            System.out.print(tempNode.value + " ");
            if (tempNode.left != null)
                queue.add(tempNode.left);
            if (tempNode.right != null)
                queue.add(tempNode.right);
        }
    }
}
/*
扩展1：分行从上到下打印二叉树
描述：从上到下打印二叉树，同一层的节点按从左到右顺序打印，每一层打印一行
思路：2个变量，一个表示下一层节点树，另一个表示再当前层中还没有打印的节点个数
 */
class PrintlnHierarchNodeOfBiTree{
    void printlnNodeOfBiTree(BiTreeNode root){
        if (root == null)
            return;
        Queue<BiTreeNode> queue = new LinkedList<>();
        queue.add(root);
        int nextLevelCount = 0;
        int currentCount = 1;
        while (!queue.isEmpty()){
            BiTreeNode tempNode = queue.peek();
            System.out.print(tempNode.value + " ");
            if (tempNode.left != null){
                queue.add(tempNode.left);
                nextLevelCount++;
            }
            if (tempNode.right != null){
                queue.add(tempNode.right);
                nextLevelCount++;
            }
            queue.poll();
            --currentCount;
            if (currentCount == 0){
                currentCount = nextLevelCount;
                nextLevelCount = 0;
                System.out.println();
            }
        }
    }
}

/*
扩展2：之字形打印二叉树
描述：实现函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左打印，第三行从左到右打印
思路：奇数层先保存左节点再保存右节点，偶数层先保存右节点再保存左节点，用双栈存储
 */
class ZigzagPrintOfBiTree{
    public static void main(String[] args){
        BiTreeNode root1 = new BiTreeNode(8);
        BiTreeNode root2 = new BiTreeNode(6);
        BiTreeNode root3 = new BiTreeNode(10);
        BiTreeNode root4 = new BiTreeNode(5);
        BiTreeNode root5 = new BiTreeNode(7);
        BiTreeNode root6 = new BiTreeNode(9);
        BiTreeNode root7 = new BiTreeNode(11);
        root1.left = root2;
        root1.right = root3;
        root2.left = root4;
        root2.right = root5;
        root3.left = root6;
        root3.right = root7;
        new ZigzagPrintOfBiTree().zigzagPrint(root1);
    }
    void zigzagPrint(BiTreeNode root){
        if (root == null)
            return;
        Stack<BiTreeNode> currentStack = new Stack<>();//偶数行存放
        Stack<BiTreeNode> nextStack = new Stack<>();//奇数行存放
        currentStack.push(root);
        int level = 0;
        while (!currentStack.isEmpty() || !nextStack.isEmpty()){
            BiTreeNode tempNode = level == 0 ? currentStack.pop() : nextStack.pop();//也可以定义变量然后弹出，不过需要判断level的值
            System.out.print(tempNode.value + " ");
            if (level == 0){//偶数层
                if (tempNode.left != null)
                    nextStack.push(tempNode.left);
                if (tempNode.right != null)
                    nextStack.push(tempNode.right);
            }else {//奇数层
                if (tempNode.right != null)
                    currentStack.push(tempNode.right);
                if (tempNode.left != null)
                    currentStack.push(tempNode.left);
            }
            if ((level == 0 && currentStack.isEmpty()) || (level == 1 && nextStack.isEmpty())){
                System.out.println();
                level = 1- level;
            }
        }
    }
}



/*
33：二叉搜索树的后续遍历序列
描述：输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历结果，是返回true，不是返回false
假设输入数组的任意两个数字都不相同
思路：后续遍历中，最后一个为根节点的值，数组前面的数组可以分为2部分，小于根节点的为左子树，大于根节点的为右子树节点
 */

class IsPostOrder{
    public static void main(String[] args){
        int[] arr = {5,7,6,9,11,10,8};
        System.out.println(new IsPostOrder().vertifyQuecenOfBST(arr,0,arr.length));
    }
    boolean vertifyQuecenOfBST(int[] arr, int start, int end){
        if (arr == null || arr.length < 0)
            return false;
        int root = arr[end - 1];
        int leftEndIndex = start;
        //在二叉搜索树中左子树的节点小于根节点
        for (; leftEndIndex < end; leftEndIndex++){
            if (arr[leftEndIndex] > root) {
                break;
            }
        }
        //在二叉搜索树中右子树的节点大于根节点
        int rightEndIndex = leftEndIndex;
        for (; rightEndIndex < end; rightEndIndex++){
            if (arr[rightEndIndex] < root)
                return false;
        }
        boolean leftFlag = true;
        if (leftEndIndex - 1 > start)//这里减一是因为上面循环中leftEndIndex在最后加了一，因此需要减去之后再做比较
            leftFlag = vertifyQuecenOfBST(arr, start, leftEndIndex);
        boolean  rightFlag = true;
        if (rightEndIndex < end - 1)
            rightFlag = vertifyQuecenOfBST(arr, leftEndIndex, rightEndIndex);
        return (leftFlag && rightFlag);
    }
}



/*
34：二叉树中和为某值的路径
描述：输入一棵二叉树和一个整数，打印出二叉树中节点值得和为输入整数的所有路径。
从树的根节点开始往下一直到叶节点所经过的节点形成一条路径
 */
class FindPathOfSumKInBiTree{
    void findPath(BiTreeNode root, int sum){//用数组实现
        int[] path = new int[getMaxDeepth(root)];
        findPathCore(root,sum,path,0);
    }
    void findPathCore(BiTreeNode root, int sum, int[] path, int top){
        if (root == null)
            return;
        path[top++] = root.value;
        sum -= root.value;
        if ( root.left == null && root.right == null){
            if (sum == 0){
                printPath(path,top);
                System.out.println();
            }
        }else {
            if (root.left != null)
                findPathCore(root.left, sum, path, top);
            if (root.right != null)
                findPathCore(root.right, sum, path, top);
        }
    }
    void printPath(int[] path, int top){
        for (int i = 0; i < top; i++){//因为存入时top已经加1
            System.out.print(path[i] + " ");
        }
    }
    int getMaxDeepth(BiTreeNode root){
        if (root == null)
            return 0;
        else {
            int left = getMaxDeepth(root.left);
            int right = getMaxDeepth(root.right);
            return 1 + Math.max(left,right);
        }
    }
    int getMaxDeepth1(BiTreeNode root){
        if (root == null)
            return 0;
        int left = 1 + getMaxDeepth(root.left);
        int right = 1 + getMaxDeepth(root.right);
        return Math.max(left,right);
    }

    void findPathByQueue(BiTreeNode root, int sum){//用双向链表实现
//        int[] path = new int[getMaxDeepth(root)];
        LinkedList<Integer> path = new LinkedList<>();
        findPathCoreByQueue(root,sum,path,0);
    }
    void findPathCoreByQueue(BiTreeNode root, int sum, LinkedList path, int currentSum){
        if (root == null)
            return;
        path.addLast(root.value);
        currentSum += root.value;
        if ( root.left == null && root.right == null){
            if (sum == currentSum){
//                printPath(path,top);
                for (Object x : path)
                    System.out.print((int)x + " ");
                System.out.println();
            }
        }else {
            if (root.left != null)
                findPathCoreByQueue(root.left, sum, path, currentSum);
            if (root.right != null)
                findPathCoreByQueue(root.right, sum, path, currentSum);
        }
        path.removeLast();
    }

    public static void main(String[] args){
        BiTreeNode root1 = new BiTreeNode(10);
        BiTreeNode root2 = new BiTreeNode(5);
        BiTreeNode root3 = new BiTreeNode(12);
        BiTreeNode root4 = new BiTreeNode(4);
        BiTreeNode root5 = new BiTreeNode(7);
        root1.left = root2;
        root1.right = root3;
        root2.left = root4;
        root2.right = root5;
        System.out.println(new FindPathOfSumKInBiTree().getMaxDeepth(root1));
        new FindPathOfSumKInBiTree().findPathByQueue(root1,22);
    }
}


/*
35：复杂链表的复制
描述：设计函数复制复杂链表，在复杂链表中，每个节点除了又有next指针外，还有sibling指针，指向链表中任意节点或null
 */
class ComplexListNode{
    int value;
    ComplexListNode next;
    ComplexListNode sibling;
}
class CopyComplexListNode{
    void cloneNode(ComplexListNode head){//复制节点，并连接
        ComplexListNode node = head;
        if (head == null)
            return;
        while (node != null){
            ComplexListNode cloneNode = new ComplexListNode();
            cloneNode.value = node.value;
            cloneNode.next = node.next;
            cloneNode.sibling = null;
            node.next = cloneNode;
            node = cloneNode.next;
        }
    }
    void connectSiblingNode(ComplexListNode head){//复制sibling
        if (head == null)
            return;
        ComplexListNode tempNode = head;
        while (tempNode != null){
            ComplexListNode cloneNode = tempNode.next;
            if (tempNode.sibling != null){
                cloneNode.sibling = tempNode.sibling;
            }
            tempNode = cloneNode.next;
        }
    }
    ComplexListNode reConnectNode(ComplexListNode head){//拆分
        ComplexListNode cloneHeadNode = null;
        ComplexListNode cloneTempNode = null;
        ComplexListNode tempNode = head;
        if (tempNode != null){
            cloneHeadNode = cloneTempNode = head.next;
            tempNode.next = cloneTempNode.next;
            tempNode = tempNode.next;
        }
        while (tempNode != null){
            cloneTempNode.next = tempNode.next;
            cloneTempNode = cloneTempNode.next;
            tempNode.next = cloneTempNode.next;
            tempNode = tempNode.next;
        }
        return cloneHeadNode;
    }

    ComplexListNode clone(ComplexListNode head){
        if (head == null)
            return null;
        cloneNode(head);
        connectSiblingNode(head);
        return reConnectNode(head);
    }
}


/*
36：二叉搜索树与双向链表
描述：输入一个二叉搜索树，将该二叉搜索树转换成一个排序的双向链表，要求不能创建任何新节点，只能调整树中指针的指向
 */
class PackBiTreeNode{
    BiTreeNode btnode =null;
}
class BiTree2BiList{
    public static void main(String[] args){
        BiTreeNode root1 = new BiTreeNode(10);
        BiTreeNode root2 = new BiTreeNode(5);
        BiTreeNode root3 = new BiTreeNode(12);
        BiTreeNode root4 = new BiTreeNode(4);
        BiTreeNode root5 = new BiTreeNode(7);
        root1.left = root2;
        root1.right = root3;
        root2.left = root4;
        root2.right = root5;
//        BiTreeNode head = new BiTree2BiList().convert(root1);
//        while (head != null){
//            System.out.print(head.value + " ");
//            head = head.right;
//    }
        BiTreeNode head1 = new BiTree2BiList().convert1(root1);
//        System.out.println(head1.value);
        while (head1 != null){
            System.out.print(head1.value + " ");
            head1 = head1.right;
        }

    }
    BiTreeNode convert(BiTreeNode root){
        BiTreeNode lastNode = null;
        lastNode = convertCore(root, null);
        BiTreeNode head = lastNode;
//        System.out.println(head.value);
        while (head.left != null){
            head = head.left;
        }
        return head;
    }
    BiTreeNode convertCore(BiTreeNode root, BiTreeNode lastNode){//lastNode来保存最后一个结点的指针，以便在与根结点连续时使用
        if (root == null)
            return lastNode;
        BiTreeNode currentNode = root;
        if (currentNode.left != null)
            lastNode = convertCore(root.left, lastNode);
        currentNode.left = lastNode;
        if (lastNode != null) lastNode.right = currentNode;
        lastNode = currentNode;
        if (currentNode.right != null)
            lastNode = convertCore(root.right, lastNode);
        return lastNode;
    }
    BiTreeNode convert1(BiTreeNode root){
        PackBiTreeNode head = new PackBiTreeNode();
        PackBiTreeNode tail = new PackBiTreeNode();
        convertCore1(root, head, tail);
//        convertCore2(root, head, tail);
        return head.btnode;
    }

    void convertCore1(BiTreeNode root, PackBiTreeNode head, PackBiTreeNode tail){
        PackBiTreeNode leftNode = new PackBiTreeNode(), rightNode = new PackBiTreeNode();
        if (root == null){
            return;
        }
        System.out.println(root.value + " root value");
        convertCore1(root.left, head,leftNode);
        convertCore1(root.right,rightNode,tail);
        if (leftNode.btnode != null){
            leftNode.btnode.right = root;
            root.left = leftNode.btnode;
        }else {
//            System.out.println("head change");
            head.btnode = root;
//            System.out.println(root.value);
//            System.out.println(head.btnode.value);
        }
        if (rightNode.btnode != null){
            root.right = rightNode.btnode;
            rightNode.btnode.left = root;
        }else {
//            System.out.println("tail change");
            tail.btnode = root;
        }
    }
}


/*
37：序列化二叉树
描述：实现两个函数，分别用来序列化和反序列化二叉树
思路：通过前序遍历，将null指针堪为特殊字符$
 */

class SerializeAndDeSerializeBiTree{
    int index = -1;
    String serialize(BiTreeNode root){
        StringBuilder stringBuilder = new StringBuilder();
        if (root == null){
            stringBuilder.append("&,");
            return stringBuilder.toString();
        }
        stringBuilder.append(root.value);
        stringBuilder.append(",");
        stringBuilder.append(serialize(root.left));
        stringBuilder.append(serialize(root.right));
        return stringBuilder.toString();
    }
    BiTreeNode deSerialize(String string){
        index++;
        String[] strs = string.split(",");
        BiTreeNode root = null;
        if (strs[index] != "$"){
            root = new BiTreeNode(Integer.parseInt(strs[index]));
            root.left = deSerialize(string);
            root.right = deSerialize(string);
        }
        return root;
    }
    public static void main(String[] args){
        BiTreeNode root1 = new BiTreeNode(10);
        BiTreeNode root2 = new BiTreeNode(5);
        BiTreeNode root3 = new BiTreeNode(12);
        BiTreeNode root4 = new BiTreeNode(4);
        BiTreeNode root5 = new BiTreeNode(7);
        root1.left = root2;
        root1.right = root3;
        root2.left = root4;
        root2.right = root5;
        System.out.println(new SerializeAndDeSerializeBiTree().serialize(root1));
    }
}


/*
38：字符串的排列
描述：输入一个字符串，打印出该字符串中字符的所有排列，即全排列
 */

class PermutationsOfString{
    void permutations(String str){
        char[] chs = str.toCharArray();
        permutationsCore(chs, 0);
    }
    void permutationsCore(char[] chs, int index){
        if (index >= chs.length){
            print(chs);
            return;
        }
        for (int m = index; m < chs.length; m++){
            swap(chs, index, m);
            permutationsCore(chs, index + 1);
            swap(chs, index, m);
        }
    }
    void print(char[] chs){
        for (char c : chs){
            System.out.print(c);
        }
        System.out.println();
    }
    void swap(char[] chs, int i, int j){
        char temp = chs[i];
        chs[i] = chs[j];
        chs[j] = temp;
    }
    public static void main(String[] args){
        String str = "abc";
        new PermutationsOfString().permutations(str);
    }
}


/*
39：数组中出现次数超过一半的数字
描述：数组中有一个数字出现的次数超过数字长度的一半，请找出这个数字。
 */

class FindMainNumber{
    int findNumber(int[] arr){
        int ans = arr[0];
        int count = 1;
        for (int i = 1; i < arr.length; i++){
            if (arr[i] != ans){
                if (count == 1) ans = arr[i];
                else --count;
            }
            else ++count;
        }
        if (!checkMoreThanHalf(arr, ans)) return -1;
        return ans;
    }
    int findNumber1(int[] arr){//基于Partition函数的时间复杂度为O(n)的算法,但是改变了数组
        if (arr == null || arr.length < 0)
            return -1;
        int middle = arr.length >> 1;
        int start = 0;
        int end = arr.length - 1;
        int index = partition(arr, start, end);
        while (index != middle){
            if (index > middle){
                end = index - 1;
                index = partition(arr, start, end);
            }else {
                start = index + 1;
                index = partition(arr,start, end);
            }
        }
        int ans = arr[middle];
        if (!checkMoreThanHalf(arr, ans))
            return -1;
        return ans;

    }
    int partition(int[] arr, int start, int end){
        if (arr == null || arr.length <= 0 || start < 0 || end >= arr.length)
            return -1;
        Random random = new Random();
        int index = (int)random.nextDouble()*(end - start);
        swap(arr, index,end);
        int small = start - 1;
        for (index = start; index < end; index++){
            if (arr[index] < arr[end]){
                ++small;
                if (small != index){
                    swap(arr, small, index);
                }
            }
        }
        ++small;
        swap(arr, index, end);
        return small;
    }
    void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    boolean checkMoreThanHalf(int[] arr, int num){
        int times = 0;
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == num) ++times;
        }
        boolean isMoreThanHalf = true;
        if (times * 2 <= arr.length)
            isMoreThanHalf = false;
        return isMoreThanHalf;
    }
    public static void main(String[] args){
        int[] arr = {1,2,3,2,2,2,5,4,2};
        System.out.println(new FindMainNumber().findNumber1(arr));
    }
}


/*
40：最小的K个数
描述：输入n个整数，找出其中最小的k个数
 */
class FindMinNumberOfK{
    //方法一：小顶堆 修改了原数组 时间复杂度O(nlog(n))
    void buildMinTree(int[] arr){
        for (int i = arr.length / 2 - 1; i >= 0; i--){
            buildMinTreeCore(arr,i,arr.length);
        }
    }
    void buildMinTreeCore(int[] arr, int index, int buildLength){
        int leftChildrenIndex = findLeftChildren(index);
        int rightChildrenIndex = findRightChildren(index);
        int minIndex = index;
        if (leftChildrenIndex < buildLength && arr[minIndex] > arr[leftChildrenIndex]){
            minIndex = leftChildrenIndex;
        }
        if (rightChildrenIndex < buildLength && arr[minIndex] > arr[rightChildrenIndex]){
            minIndex = rightChildrenIndex;
        }
        if (minIndex == index || minIndex > buildLength)
            return;
        swap(arr,minIndex,index);
        buildMinTreeCore(arr, minIndex,buildLength);
    }
    int findRightChildren(int i){ return 2 * (i + 1);}
    int findLeftChildren(int i){ return 2 * i + 1;}
    void heapSort(int[] arr){
        for (int i = 0; i < arr.length; i++){
            swap(arr,0,arr.length-i-1);
            buildMinTreeCore(arr,0, arr.length - i - 1);
        }
    }
    void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    void findKMinNum(int[] arr,int k){
        this.buildMinTree(arr);
        this.heapSort(arr);
        int index = arr.length;
        for (int i = 0; i < k; i++)
            System.out.print(arr[--index] + " ");
    }

    //方法二 基于partition 改变数组
    //如果基于数组的第k个数字来调整，则使得比第k个数字小的所有数字都位于数组的左边
    void findKMinNum1(int[] arr, int k){
        if (arr == null || arr.length == 0 || k > arr.length || k < 0)
            return;
        int start = 0;
        int end = arr.length - 1;
        int index = partition(arr,start, end);
        while (index != k - 1){
            if (index > k - 1){
                end = index - 1;
                index = partition(arr, start,end);
            }else {
                start = index + 1;
                index = partition(arr, start, end);
            }
        }
        for (int s : arr)
            System.out.print(s + " ");
        System.out.println();
        for (int i = 0; i < k; i++)
            System.out.println(arr[i] + "  ");
    }
    int partition(int[] arr, int start, int end){
        if (arr == null || start < 0 || end > arr.length || arr.length == 0)
            return -1;
        Random random = new Random();
        int index = (int)random.nextDouble() * (end - start);
        swap(arr,index,end);
        int small = start - 1;
        for (index = start; index < end; index++){
            if (arr[index] < arr[end]){
                ++small;
                if (small != index){
                    swap(arr, small, index);
                }
            }
        }
        ++small;
        swap(arr, small, end);
        return small;
    }

    //方法三 设计容量为K的大顶堆存储最小K个数，然后来一个数与大顶堆最大值比较，如果小，更新一下大顶堆
    class MaxHeap{
        void buildMaxTree(int[] arr){
            for (int i = arr.length / 2 - 1; i >= 0; i--){
                buildMaxTreeCore(arr,i,arr.length);
            }
        }
        void buildMaxTreeCore(int[] arr, int index, int buildLength){
            int leftChildrenIndex = findLeftChildren(index);
            int rightChildrenIndex = findRightChildren(index);
            int maxIndex = index;
            if (leftChildrenIndex < buildLength && arr[maxIndex] < arr[leftChildrenIndex]){
                maxIndex = leftChildrenIndex;
            }
            if (rightChildrenIndex < buildLength && arr[maxIndex] < arr[rightChildrenIndex]){
                maxIndex = rightChildrenIndex;
            }
            if (maxIndex == index || maxIndex > buildLength)
                return;
            swap(arr,maxIndex,index);
            buildMaxTreeCore(arr, maxIndex,buildLength);
        }
        void insert(int[] arr, int value){
            arr[0] = value;
            buildMaxTreeCore(arr,0,arr.length - 1);
        }
    }
    void findKMinNum2(int[] arr, int k){
        if (arr == null || arr.length <=0 || arr.length < k)
            return;
        int[] tempArr = new int[k];
        for (int i = 0;i < k; i++)
            tempArr[i] = arr[i];
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.buildMaxTree(tempArr);
        for (int i = k; i < arr.length; i++){
            if (arr[i] < tempArr[0]){
                maxHeap.insert(tempArr, arr[i]);
            }
        }
        for (int t : tempArr)
            System.out.print(t + " ");
        System.out.println();
    }
    public static void main(String[] args){
        int[] arr = {4,5,1,6,2,7,3,8};
//        new FindMinNumberOfK().findKMinNum(arr,4);
//        new FindMinNumberOfK().findKMinNum1(arr,4);
        new FindMinNumberOfK().findKMinNum2(arr,4);
    }
}


/*
41：数据流中的中位数
描述：如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间那两个数的平均值。

基础：如果能够保证数据容器左边的数据小于右边的数据，那么即使左、右两边内部的数据没有排序，
也可以根据左边最大的数以及右边最小的数得到中位数

思路：考虑将数据序列从中间开始分为两个部分，左边部分使用大根堆表示，右边部分使用小根堆存储。
每遍历一个数据，计数器count增加1，当count是偶数时，将数据插入小根堆；
当count是奇数时，将数据插入大根堆。当所有数据遍历插入完成后（时间复杂度为O(logn)，
如果count最后为偶数，则中位数为大根堆堆顶元素和小根堆堆顶元素和的一半；
如果count最后为奇数，则中位数为小根堆堆顶元素。
 */

class GetMedianNumberInIOStream{
    private int count = 0;//数据流中数据的个数
    //优先队列实现了堆，默认实现的小顶堆
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(15, new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;//o1 - o2是小顶堆
        }
    });
    public void insert(int num){
        if ((count & 0x1) == 0){//当数据总数为偶数时，新加入的元素应进入小顶堆
                                //（注意不是直接进入小顶堆，是经过大顶堆筛选后选取大顶堆中最大元素进入小顶堆
                                //1：新加入的元素先加入大顶堆，有大顶堆筛选后选出最大元素
            maxHeap.offer(num);
            int filterMaxNum = maxHeap.poll();
            minHeap.offer(filterMaxNum);
        }else {             //当数据总数为奇数时，新加入的元素应进入大顶堆
                            //（注意不是直接进入大顶堆，是经过小顶堆筛选后选取小顶堆中最小元素进入大顶堆
                            //1：新加入的元素先加入小顶堆，有小顶堆筛选后选出最小元素
            minHeap.offer(num);
            int filterMinNum = minHeap.poll();
            maxHeap.offer(filterMinNum);
        }
        ++count;
    }
    public Double getMedian(){
        // 数目为偶数时，中位数为小根堆堆顶元素与大根堆堆顶元素和的一半
        if ((count & 0x1) == 0){
            return (minHeap.peek() + maxHeap.peek()) / 2.0;
        }else {
            return new Double(maxHeap.peek());
        }
    }
}



/*
42：连续子数组的最大和
描述：输入一个整型数组，数组中有正有负，数组中的一个或连续多个整数组成一个子数组。
求所有子数组的和的最大值，要求时间复杂对O(n)
 */

class MaxSumOfSubArray{
    int findMaxSum(int[] arr) throws Exception{
        if (arr == null || arr.length <= 0)
            throw new Exception("the array your Input is error");
        int maxSum = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++){
            maxSum += arr[i];
            if (maxSum < 0){
                maxSum = 0;
            }else {
                if (maxSum > ans){
                    ans = maxSum;
                }
            }
        }
        return ans;
    }
    public static void main(String[] args) throws Exception{
        int[] arr = {1,-2,3,10,-4,7,2,-5};
        System.out.println(new MaxSumOfSubArray().findMaxSum(arr));
    }
}


/*
43：(1--n)整数中 1 出现的次数
描述：输入一个整数n，求 1--n这n个整数的十进制表示中 1 出现的次数
 */
class NumOf1{
    //方法一 暴力枚举 时间复杂度O(nlog(n))
    int numberOf1(int n){
        int number = 0;
        for (int i = 1; i <= n; i++)
            number += numberOf1Core(i);
        return number;
    }
    int numberOf1Core(int i){
        int number = 0;
        while (i != 0){
            if (i % 10 == 1)
                ++number;
            i = i / 10;
        }
        return number;
    }

    //方法二 通过统计的思想去做
    //例如：21345 分为 1346 - 21345 和 1 - 1345两块。1346 - 21345中 1 出现在万位上是 10000 - 19999 一万次
    //10000 - 12345 1 出现在万位上次数 为 （12345-10000+1） = 2346
    //再看剩下的四位 1346 - 21345 分为 1346 - 11345 ， 11346 - 21345两部分，剩下的每一段中的4位数，选择其中一位为1，
    //其他随便，总共 2 * 4 * 10^3 = 8000
    //剩下的 1-1345用递归

    int numberOf11(int n){
        if (n <= 0)
            return 0;
        char[] strN = (n + "").toCharArray();
        return numberOf1Core1(strN,0);
    }

    int numberOf1Core1(char[] strN, int startIndex){
        if (strN == null || strN[startIndex] < '0' || strN[startIndex] > '9' || strN.length == startIndex)
            return 0;
        int first = strN[startIndex] - '0';
        int length = strN.length - startIndex;
        if (length == 1 && first == 0) return 0;
        if (length == 1 && first > 0) return 1;
        //假设strN是“21345”
        //numFirstDigit 是数字10000 - 19999的第一位中的数目
        int numFirstDigit = 0;
        if (first > 1)
            numFirstDigit = PowerBase10(length - 1);
        else if (first == 1)
            numFirstDigit = getNum(strN,startIndex + 1);//求解10000 - 12345 1 出现在万位上次数 为 （12345-10000+1） = 2346
        int numOtherDigits = first * (length - 1) * PowerBase10(length - 2);//计算1346 - 21345 中除第一位外数位中1的次数
        int numRecursive = numberOf1Core1(strN, startIndex + 1);
        System.out.println(startIndex + " numFirstDigit " + numFirstDigit);
        System.out.println(startIndex + " numOtherDigits " + numOtherDigits);
        System.out.println(startIndex + " numRecursive " + numRecursive);
        return numFirstDigit + numOtherDigits + numRecursive;
    }

    int getNum(char[] strN, int startIndex){//求解10000 - 12345 1 出现在万位上次数 为 （12345-10000+1） = 2346
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex; i < strN.length; i++)
            stringBuilder.append(strN[i]);
        System.out.println(stringBuilder.toString());
        return Integer.valueOf(stringBuilder.toString()) + 1;
    }

    int PowerBase10(int n){
        System.out.println(n);
        int result = 1;
        for (int i = 0; i < n; i++)
            result *= 10;
        return result;
    }

    public static void main(String[] args){
        int n = 21345;
        System.out.println(new NumOf1().numberOf1(n));
        System.out.println(new NumOf1().numberOf11(n));
    }
}



/*
44：数字序列中某一位的数字
描述：数字以 0123456789101112131415。。。的格式序列化到字符序列中。在这个序列中，第5位（从0开始）是5，第13位是 1 ，
第19位是4，等等，编写函数，求任意第n位对应的数字
思路：第一种方法 暴力破解 枚举
    第二种方法：例如 第1001位， 序列前10位 0 - 9 这10个数字只有 1 位，显然 1001在这之后，因此直接跳过这10个数字
                接下来从后面紧跟的序列中找第991（1001 - 10 = 991）位。后面的180位数字是90个10 - 99 两位数，由于
                991 > 180，所以跳过90个两位数，接下来的2700位是 900 个 100 - 999 三位数，由于 811（991 - 180） < 2700,
                所以第811位一定是某个三位数中1位，又因为 811 = 3 * 270 +1，这以为着第811位是从100开始的第270个数字即
                370的中间一位，也就是7（因为是从第0位开始的，否则就是3）
 */

class DigitAtIndex{
    int findNumberAtIndex(int index){
        if (index < 0)
            return -1;
        int digits = 1;
        int ans = -1;
        while (true){
            int numbers = countOfIntegers(digits);
            if (index < numbers * digits){
                ans = findNumberAtIndexCore(index, digits);
                break;
            }
            index -= numbers * digits;
            digits++;
        }
        return ans;
    }

    int countOfIntegers(int digits){//用来得到m位的数字一共又多少个
        if (digits == 1)
            return 10;
        int count = (int)Math.pow(10, digits - 1);
        return 9 * count;
    }

    int findNumberAtIndexCore(int index, int digits){//当知道所求位于某m位数之后后，求出该数字
        int number = beginNumber(digits) + index / digits;
        System.out.println(number + " nnn");
        int indexFromRight = digits - index % digits;//从第0位开始
        for (int i = 1; i < indexFromRight; i++)
            number = number/10;
        return number % 10;
    }

    int beginNumber(int digits){
        if (digits == 1)
            return 0;
        return (int)Math.pow(10, digits - 1);
    }

    public static void main(String[] args){
        int n = 1001;
        int ans = new DigitAtIndex().findNumberAtIndex(0);
        System.out.println(ans);
    }
}


/*
45：把数组排成最小的整数
描述：输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出所有数字中最小的一个。
例如输入{3，32，321} 输出 321323
思路：1：需要一个函数判断两个数字谁排在前面
      2：隐含大数问题，两个数字都符合int但是拼接后不一定，因此用字符串表示。
 */

class FindMinCombinationOfNum{
    void printMinNumber(int[] num){
        if (num == null || num.length <= 0)
            return;
        int len = num.length;
        String[] str = new String[len];
        for (int i = 0; i < len; i++)
            str[i] = String.valueOf(num[i]);
        for (int i = 0; i  < len; i++){
            int min = i;
            for (int j = i + 1; j < len; j++){
                if (compare1(str[i],str[j])){
                    min = j;
                }
            }
            if (min != i)
                SwapArray.swap(str,i,min);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : str)
            stringBuilder.append(s);
        System.out.println(stringBuilder.toString());
    }

    boolean compare(String s1, String s2){//s1s2 > s2s1   true
        int length = s1.length() + s2.length();
        String str1 = s1 + s2;
        String str2 = s2 + s1;
        for (int i = 0; i < length; i++){
            if (Integer.parseInt(str1.substring(i, i + 1)) > Integer.parseInt(str2.substring(i, i + 1)))
                return true;
            if (Integer.parseInt(str1.substring(i, i + 1)) < Integer.parseInt(str2.substring(i, i + 1)))
                return false;
        }
        return false;
    }
    boolean compare1(String s1, String s2){
        int length = s1.length() + s2.length();
        String str1 = s1 + s2;
        String str2 = s2 + s1;
        int flag = str1.compareTo(str2);
        return flag > 0 ;
    }

    public static void main(String[] args){
        int[] num = {3,32,321};
        new FindMinCombinationOfNum().printMinNumber(num);
    }
}


/*
46：把数字翻译成字符串
描述：给定一个数字，按照如下规则把它翻译成字符串：0 - "a", 1 - "b",......,11 - "l",......,25 - "z"
一个数字可能有多种翻译，例如 12258可以翻译成 "bccfi","bwfi","bczi","mcfi","mzi"，编写函数计算一个数字有多少种翻译方法
思路：使用递归来解决。定义f(i)表示从第i位数字开始的不同翻译数目，那么f(i)= f(i+1) + g(i,i+1)*f(i+2),
    其中，当第i位和第i+1位数字凭借其来的数字在10 - 25的范围时，g(i,i+1) = 1,否则为0
    由于从前向后会存在重复计算问题，因此考虑从后向前计算
 */

class TranslateInt2String{
    int getTranslateCount(int number){
        if (number < 0)
            return 0;
        String str = String.valueOf(number);
        char[] chs = str.toCharArray();
        return getTranslateCountCore(chs);
    }
    int getTranslateCountCore(char[] chs){
        int length = chs.length;
        int[] counts = new int[length];
        int count = 0;
        for (int i = length - 1; i >= 0; i--){
            count = 0;
            if (i < length - 1)
                count = counts[i + 1];
            else count = 1;
            if (i < length - 1){
                int digit1 = chs[i] - '0';
                int digit2 = chs[i + 1] - '0';
                int converted = digit1 * 10 + digit2;
                if (converted >= 10 && converted <= 25){
                    if (i < length - 2)
                        count += counts[i + 2];
                    else count += 1;
                }
            }
            counts[i] = count;
        }
        count = counts[0];
        return count;
    }
    public static void main(String[] args){
        int num = 12258;
        System.out.println(new TranslateInt2String().getTranslateCount(num));
    }
}


/*
47：礼物的最大价值
描述：在一个m*n的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于0）。
你可以从期盼的左上角开始拿格子里的礼物，并每次向左或向下移动一格，知道到达棋盘的右下角。
给定一个棋盘机器上面的礼物，请计算你最多能拿到多少价值的礼物

思路：动态规划
      1：定义函数f(i,j)表示到达坐标(i,j)的格子时能拿到的礼物的最大值，根据题目要求，我们有两种可能的途径叨叨坐标(i,j)
      的格子，通过格子(i-1,j)或者(i,j-1)，所以f(i,j) = max((i-1,j),(i,j-1))+gift[i,j],gift[i,j]表示坐标(i,j)里的礼物价值
      2:由于上面方法空间需求多，我们进一步优化。上面我们提到到达坐标为(i,j)的格子时能拿到的礼物的最大值只依赖于(i-1,j)和
      (i,j-1)的两个格子，因此第i-2行及更上面的礼物的最大值实际上没必要保存下下来。使用一维数组代替上面的二维数组。
      该数组前j个数字分表表示当前第i行前面j个格子礼物的最大值，而之后的数字分表表村前面第i-1行n-j个格子礼物的最大值

 */
class MaxValueOfGiftMatrix{
    //1
    int getMaxValue(int[][] gift){
        if (gift == null || gift[0] == null || gift.length <= 0 || gift[0].length <= 0)
            return 0;
        int[][] maxValueDP = new int[gift.length][gift[0].length];
        for (int i = 0; i < gift.length; i++){
            for (int j = 0; j < gift[0].length; j++){
                int left = 0;
                int up = 0;
                if (i > 0)
                    up = maxValueDP[i-1][j];
                if (j > 0)
                    left = maxValueDP[i][j-1];
                maxValueDP[i][j] = Math.max(up,left) + gift[i][j];
            }
        }

        return maxValueDP[gift.length-1][gift[0].length-1];
    }

    //2 列
    int getMaxValue2(int[][] gift){
        if (gift == null || gift[0] == null || gift.length <= 0 || gift[0].length <= 0)
            return 0;
        int[] maxValueDP = new int[gift[0].length];//列
        for (int i = 0; i < gift.length; i++){
            for (int j = 0; j < gift[0].length; j++){
                int left = 0;
                int up = 0;
                if (i > 0)
                    up = maxValueDP[j];
                if (j > 0)
                    left = maxValueDP[j - 1];
                maxValueDP[j] = Math.max(up,left) + gift[i][j];
            }
        }
        return maxValueDP[gift[0].length - 1];
    }

    //3 行 maxValueDP[i] 表示第j列前面i个格子礼物的最大值
    int getMaxValue3(int[][] gift){
        if (gift == null || gift[0] == null || gift.length <= 0 || gift[0].length <= 0)
            return 0;
        int[] maxValueDP = new int[gift.length];
        for (int j = 0; j < gift[0].length; j++){
            for (int i = 0; i < gift.length; i++){
                int left = 0;
                int up = 0;
                if (i > 0)
                    up = maxValueDP[i - 1];
                if (j > 0)
                    left = maxValueDP[i];
                maxValueDP[i] = Math.max(up,left) + gift[i][j];
            }
        }
        return maxValueDP[gift.length - 1];
    }
    public static void main(String[] args){
        int[][] gift = {{1,10,3,8},{12,2,9,6},{5,7,4,11},{3,7,16,5}};
        System.out.println(new MaxValueOfGiftMatrix().getMaxValue(gift));
        System.out.println(new MaxValueOfGiftMatrix().getMaxValue2(gift));
        System.out.println(new MaxValueOfGiftMatrix().getMaxValue3(gift));
    }
}



/*
48：最长不含重复字符的子字符串
描述：请从字符串中招呼最长的不包含重复字符的子字符串，计算改最长子字符串的长度
思路：动态规划
      定义f(i)表示以第i个字符为结尾的不包含重复字符的子字符串的最长长度。从左向右扫
      如果第i个字符没有出现，f(i) = f(i-1) + 1
      如果第i个字符出现过，先计算第i个字符和它上次出现在字符串中的位置的距离，记为d
      如果d <= f(i-1),此时第i个字符上次出现在f(i-1)对应的最长子字符串之中，所以f(i) = d
      如果d >f(i-1),此时第i个字符上次出现在f(i-1)对应的最长子字符串之前，仍然有f(i) = f(i-1) + 1
 */

class FindMaxLengthOfNoRepeatSubstring{
    int findLongestSubstringWithoutDuplication(String str){
        int curLength = 0;
        int maxLength = 0;
        char[] chs = str.toCharArray();
        int[] position = new int[26];//记录当前字符上次出现的位置
        for (int i = 0; i < 26; i++)
            position[i] = -1;
        for (int i = 0; i < chs.length; i++){
            int preIndex = position[chs[i] - 'a'];
            if (preIndex < 0 || i - preIndex > curLength)
                ++curLength;
            else {
                if (curLength > maxLength)
                    maxLength = curLength;
                curLength = i - preIndex;
            }
            position[chs[i] - 'a'] = i;
        }
        if (curLength > maxLength)
            maxLength = curLength;
        return maxLength;
    }
    public static void main(String[] args){
        String str = "arabcacfr";
        System.out.println(new FindMaxLengthOfNoRepeatSubstring().findLongestSubstringWithoutDuplication(str));
    }
}


/*
49：丑数
丑数定义：我们把只包含因子2、3、5的数称作丑数
描述：求从小到大的顺序的第1500个丑数，习惯上，我们把1当作第一个丑数
思路：1：暴力
      2：创建数组记录丑数
      假设数组中已经有若干个排序号的丑数，最大的记为m，
            方法1：将所有丑数乘以2，找出第一个大于m的丑数m2存入下一个位置
            方法1：将所有丑数乘以3，找出第一个大于m的丑数m3存入下一个位置
            方法1：将所有丑数乘以5，找出第一个大于m的丑数m5存入下一个位置...
            方法2:事实上对于乘以2，看的存在一个t2，排在他之前的每个丑数乘以2都小于m，之后乘以2都大于m
                我们只需记下这个位置，每次产生丑数时更新t2即可，同样存在t3、t5
 */

class UglyNumber{
    //暴力方法
    int getUglyNumber(int index){
        if (index < 0)
            return 0;
        int number = 0;
        int ugly = 0;
        while (ugly < index){
            ++number;
            if (isUgly(number)){
                ++ugly;
            }
        }
        return number;
    }
    boolean isUgly(int number){
        while (number % 2 == 0)
            number /= 2;
        while (number % 3 == 0)
            number /= 3;
        while (number % 5  == 0)
            number /= 5;
        return number == 1;
    }


    //方法二
    int getUglyNumber2(int index){
        if (index <= 0)
            return 0;
        int[] uglyNumber = new int[index];
        uglyNumber[0] = 1;
        int nextUglyIndex = 1, minNumber;
        int nextIndex2 = 0, nextIndex3 = 0, nextIndex5 = 0;
        while (nextUglyIndex < index){
            minNumber = min(uglyNumber[nextIndex2]*2, uglyNumber[nextIndex3]*3, uglyNumber[nextIndex5]*5);
            uglyNumber[nextUglyIndex] = minNumber;
            while (uglyNumber[nextIndex2]*2 <= minNumber)
                nextIndex2++;
            while (uglyNumber[nextIndex3]*3 <= minNumber)
                nextIndex3++;
            while (uglyNumber[nextIndex5]*5 <= minNumber)
                nextIndex5++;
            ++nextUglyIndex;
        }
        return uglyNumber[nextUglyIndex - 1];
    }
    int min(int num1, int num2, int num3){
        int min = num1 < num2 ? num1 : num2;
        min = min < num3 ? min : num3;
        return min;
    }
    public static void main(String[] args){
        int index = 0;
        long time = System.currentTimeMillis();
        System.out.println(new UglyNumber().getUglyNumber(index));
        System.out.println(System.currentTimeMillis() - time);

        long time1 = System.currentTimeMillis();
        System.out.println(new UglyNumber().getUglyNumber2(index));
        System.out.println(System.currentTimeMillis() - time1);
    }
}


/*
50：第一个只出现一次的字符
 */
/*
题目一：字符串中第一个只出现一次的字符
描述：在字符串中找到第一个只出现一次的字符。例如“abaccdeff” 输出b
思路：使用hashMap
 */
class FirstOneTimesCharInString{
    char getFirst(String str){
        if (str.equals(""))
            return '\0';
        char[] chs = str.toCharArray();
        int tableSize = 256;//ASCII  256个字符
//        HashMap map = new HashMap(256);
        int[] map = new int[tableSize];
        for (int i = 0; i < tableSize; i++)
            map[i] = 0;
        for (char c : chs){
            map[c]++;
        }
        for (char c  : chs){
            if (map[c] == 1)
                return c;
        }
        return '\0';
    }
    public static void main(String[] args){
        String str = "abaccdeff";
        System.out.println(new FirstOneTimesCharInString().getFirst(str));
    }
}


/*
题目二：字符流中第一个只出现一次的字符
描述：实现函数，用来中出字符流中的第一个只出现一次的字符。
例如，当从字符流中只读出前两个字符“go”时，第一个只出现一次的字符是g，当从字符流中只读出前六个字符“google”时，
第一个只出现一次的字符是l
思路：因为是字符流，当读到一个字符时，该字符存入容器，如果再一次读到该字符，那么它就不是只出现一次的字符，
也就可以忽略，这是把容器里保存的值更新为一个特殊值，如负数
 */

class FirstOneTimesCharInStream{
    private int index = 0;//记录每个词的下标
    private int[] occurenced;
    void init(){
        occurenced = new int[256];
        for (int i = 0; i < 256; i++)
            occurenced[i] = -1;
    }
    void insert(char ch){
        if (occurenced[ch] == -1)
            occurenced[ch] = index;
        else if (occurenced[ch] >= 0)
            occurenced[ch] = -2;
        index++;
    }
    char getFirst(){
        char ch = '\0';
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 256; i++){
            if (occurenced[i] >= 0 && occurenced[i] < min){
                ch = (char)i;
                min = occurenced[i];
            }
        }
        return ch;
    }
}


/*
51：数组中的逆序对
描述：在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组从一个逆序对。
输入一个数组，求出这个数组的逆序对

思路：先将数组分隔成子数组，统计出子数组内部的逆序对的数目，然后再统计出两个相邻子数组之间的逆序对数目。
在统计过程中，还需要对数组进行排序

    如果第一个子数组中的数字大于第二个子数组中的数字，则构成逆序对，并且逆序对的数目就是第二个子数组中剩余的数字的个数
    如果第一个数组中的数组小于或等于第二个数组中的数字，则不构成逆序对。每次比较的时候，
    我们都把较大的数字从后往前复制到辅助数组，确保辅助数组中的数字是递增排序的，
    在把较大的数字复制到辅助数组之后，把对应的指针向前移动一位
 */
class InversionNumber{
    int inversePair(int[] data){
        if (data == null || data.length < 0)
            return 0;
        int[] copy = new int[data.length];
        for (int i = 0; i <data.length; i++)
            copy[i] = data[i];
        return inversePairCore(data, copy,0,data.length - 1);
    }
    int inversePairCore(int[] data, int[] copy, int start, int end){
        if (start == end){
//            copy[start] = data[start];
            return 0;
        }
        int length = (end - start) / 2;
        int left = inversePairCore(copy, data, start,start + length);
        int right = inversePairCore(copy, data,start + length + 1, end);
        //i 初始化为前半段最后一个数字的下标
        int i = start + length;
        //j 初始化为后半段最后一个数字的下标
        int j = end;
        int indexCopy = end;
        int count = 0;
        while (i >= start && j >= start + length + 1){
            if (data[i] > data[j]){
                copy[indexCopy--] = data[i--];
                count += j - start - length;
            }else {
                copy[indexCopy--] = data[j--];
            }
        }
        for (; i >= start; --i)
            copy[indexCopy--] = data[i];
        for (; j >= start + length + 1; j--)
            copy[indexCopy--] = data[j];
        return left + right + count;
    }
    public static void main(String[] args){
        int[] arr = {7,5,6,4};
        System.out.println(new InversionNumber().inversePair(arr));
    }
}



/*
52：两个链表的第一个公共节点
描述：输入两个链表，找出它们的第一个公共节点
 */
class FindFirstCommonNode{
    ListNode getFirstCommonNode(ListNode head1, ListNode head2){
        int length1 = getListLength(head1);
        int length2 = getListLength(head2);
        int lengthDif = length1 - length2;
        ListNode tempLong = head1;
        ListNode tempShort = head2;
        if (length2 > length1){
            tempLong = head2;
            tempShort = head1;
            lengthDif = length2 - length1;
        }
        //先在长链表上走几步，使两个链表剩余长度相同
        for (int i = 0; i < lengthDif; i++)
            tempLong = tempLong.next;
        while (tempLong != null && tempShort != null && tempLong != tempShort){
            tempLong = tempLong.next;
            tempShort = tempShort.next;
        }
        return tempLong;
    }

    int getListLength(ListNode head){
        int length = 0;
        ListNode tempNode = head;
        while (tempNode != null){
            ++length;
            tempNode = tempNode.next;
        }
        return length;
    }
}



/*
53：在排序的数组中查找数字
 */
/*
题目一：数字在骗子徐数组中出现的次数
描述：统计一个数字在排序数组中出现的次数
思路：1：用二分查找到数字，前后延伸记录次数
      2：用二分查找找到数字的第一位和最后一位
 */

class FindTimesOfNum{
    int getFirstK(int[] data, int k, int start, int end){
        if (start > end)
            return -1;
        int middleIndex = (end + start) / 2;
        int middleData = data[middleIndex];
        if (middleData == k){
            if ((middleIndex > 0 && data[middleIndex - 1] != k) || middleIndex == 0)
                return middleIndex;
            else end = middleIndex - 1;
        }else if (middleData > k)
            end = middleIndex - 1;
        else start = middleIndex + 1;
        return getFirstK(data, k, start, end);
    }
    int getFirstK1(int[] data, int k, int start, int end){
        int firstK = -1;
        while (start <= end){
            int middleIndex = (end + start) / 2;
            int middleData = data[middleIndex];
            if (middleData == k){
                if ((middleIndex > 0 && data[middleIndex - 1] != k) || middleIndex == 0){
                    firstK = middleIndex;
                    break;
                }
                else end = middleIndex - 1;
            }else if (middleData > k)
                end = middleIndex - 1;
            else start = middleIndex + 1;
        }
        return firstK;
    }

    int getLastK(int[] data, int k, int start, int end){
        if (start > end)
            return -1;
        int middleIndex = (end + start) / 2;
        int middleData = data[middleIndex];
        if (middleData == k){
            if ((middleIndex < data.length - 1 && data[middleIndex + 1] != k) || middleIndex == data.length - 1)
                return middleIndex;
            else start = middleIndex + 1;
        }else if (middleData > k)
            end = middleIndex - 1;
        else start = middleIndex + 1;
        return getLastK(data, k, start, end);
    }

    int getLastK1(int[] data, int k, int start, int end){
        int lastK = -1;
        while (start <= end){
            int middleIndex = (end + start) / 2;
            int middleData = data[middleIndex];
            if (middleData == k){
                if ((middleIndex < data.length - 1 && data[middleIndex + 1] != k) || middleIndex == data.length - 1){
                    lastK = middleIndex;
                    break;
                }
                else start = middleIndex + 1;
            }else if (middleData > k)
                end = middleIndex - 1;
            else start = middleIndex + 1;
        }
        return lastK;
    }

    int getTimesOfK(int[] data, int k){
        int times = 0;
        if(data != null && data.length > 0){
            int first = getFirstK(data, k, 0, data.length - 1);
            int last = getLastK(data, k, 0, data.length - 1);
            if (first > -1 && last > -1)
                times = last - first + 1;
        }
        return times;
    }
    int getTimesOfK1(int[] data, int k){
        int times = 0;
        if(data != null && data.length > 0){
            int first = getFirstK1(data, k, 0, data.length - 1);
            int last = getLastK1(data, k, 0, data.length - 1);
            if (first > -1 && last > -1) {
                times = last - first + 1;
            }
        }
        return times;
    }
    public static void main(String[] args){
        int[]arr = {1,2,3,3,3,3,4,5};
//        System.out.println(new FindTimesOfNum().getTimesOfK(arr,3));
        System.out.println(new FindTimesOfNum().getTimesOfK1(arr,3));
    }
}



/*
题目二：0 - n-1中缺失的数字
描述：一个长度为 n-1 的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围 0 - n-1 之内。
在范围 0 - n-1内的n个数字中有且仅有一个数字不在改数组中，找出该数字
 */

class FindMissingNum{
    int getMissingNum(int[] data){
        if (data == null || data.length < 0)
            return -1;
        int left = 0;
        int right = data.length - 1;
        while (left <= right){
            int middle = (left + right) / 2;
            if (data[middle] != middle){
                if (middle == 0 || data[middle - 1] == middle - 1)
                    return middle;
                right = middle - 1;
            }else left = middle + 1;
        }
        if (left == data.length)//最后一位缺失
            return left;
        return -1;
    }
    public static void main(String[] args){
        int[] arr = {0,1,2,3,4,5,6,7};
        System.out.println(new FindMissingNum().getMissingNum(arr));
    }
}


/*
题目三：数组中和下标相等的元素
描述：假设一个单调递增的数组里的每个元素都是整数并且是唯一的。
编写函数，找出数组中任意一个数值等于其下标的元素
思路：用二分法
      假设某一部抵达数组中的第i个数字，如果该数字正好是i，那么刚好找到。
      假设数字的值为m，先考虑m > i的情况，由于数组中的所有数组都唯一并且单调递增，
      那么对于任意大于0的k，位于下标i+k上的值大于或等于m+k。又因为m > i,所以 m+k > i+k
      这就意味着，如果第i位上的值大于i那么，它后面的所有数字都大于它的下标
      m<i的情况一样
 */

class FindNumSameAsIndex{
    int getNumSameAsIndex(int[] arr){
        if (arr == null || arr.length < 0)
            return -1;
        int left = 0;
        int right = arr.length - 1;
        while (left <= right){
            int middle = (left + right) / 2;
            if (arr[middle] == middle)
                return middle;
            if (arr[middle] > middle)
                right = middle - 1;
            else left = middle + 1;
        }
        return -1;
    }
}


/*
54：二叉搜索树的第K大节点
描述：给定一棵二叉搜索树，请找出其中第k大的节点
思路：使用中序遍历即可
 */

class FindKthNode{
    BiTreeNode getKthNode(BiTreeNode root, int k){
        if (root == null || k == 0)
            return null;
        int[] packK = {k};
        return getKthNodeCore(root,packK);
    }
    BiTreeNode getKthNodeCore(BiTreeNode root, int[] packK){
        BiTreeNode target = null;
        if (root.left != null)
            target = getKthNodeCore(root.left, packK);
        if (target == null){
            if (packK[0] == 1)
                target = root;
            packK[0]--;
        }
        if (target == null && root.right != null)
            target = getKthNodeCore(root.right, packK);
        return target;
    }
    public static void main(String[] args){
        BiTreeNode root1 = new BiTreeNode(5);
        BiTreeNode root2 = new BiTreeNode(3);
        BiTreeNode root3 = new BiTreeNode(7);
        BiTreeNode root4 = new BiTreeNode(2);
        BiTreeNode root5 = new BiTreeNode(4);
        root1.left = root2;
        root1.right = root3;
        root2.left = root4;
        root2.right = root5;
        System.out.println(new FindKthNode().getKthNode(root1,3).value);
    }
}


/*
55：二叉树的深度
描述：输入一个二叉树的根节点，求概述的深度。从根节点到叶节点一次经过的节点（含根节点和叶节点）形成一条路径。
最长路径的长度为数的深度
 */
class GetDeepOfBiTree{
    int getDeep(BiTreeNode root){
        if (root == null)
            return 0;
        int left = getDeep(root.left);
        int right = getDeep(root.right);
        return Math.max(left, right) + 1;
    }
    int getDeep1(BiTreeNode root){
        if (root == null)
            return 0;
        int left = 1 + getDeep(root.left);
        int right = 1 +getDeep(root.right);
        return Math.max(left, right);
    }
    public static void main(String[] args){
        BiTreeNode root1 = new BiTreeNode(5);
        BiTreeNode root2 = new BiTreeNode(3);
        BiTreeNode root3 = new BiTreeNode(7);
        BiTreeNode root4 = new BiTreeNode(2);
        BiTreeNode root5 = new BiTreeNode(4);
        BiTreeNode root6 = new BiTreeNode(8);
        root1.left = root2;
        root1.right = root3;
        root2.left = root4;
        root2.right = root5;
        root5.left = root6;
        System.out.println(new GetDeepOfBiTree().getDeep(root1));
    }
}

/*
题目二：平衡二叉树
输入一棵二叉树的根节点，判断该数是不是平衡二叉树。
 */

class IsBalancedBiTree{
    boolean isBalanced(BiTreeNode root){//该方法重复遍历多次节点，不是很好
        if (root == null)
            return true;
        int leftDeep = getDeep(root.left);
        int rightDeep = getDeep(root.right);
        int dif = rightDeep - leftDeep;
        if (dif > 1 || dif < -1)
            return false;
        return isBalanced(root.left) && isBalanced(root.right);
    }
    int getDeep(BiTreeNode root){
        if (root == null)
            return 0;
        int left = getDeep(root.left);
        int right = getDeep(root.right);
        return Math.max(left, right) + 1;
    }

    boolean isBalanced1(BiTreeNode root){//该方法重复遍历多次节点，不是很好
        int[] deep = {0};
        boolean flag = isBalanced1Core(root,deep);
        System.out.println(deep[0]);
        return flag;
    }
    boolean isBalanced1Core(BiTreeNode root, int[] deep){//该方法重复遍历多次节点，不是很好
        if (root == null){
            deep[0] = 0;
            return true;
        }
        int[] left = {0}, right = {0};
        if (isBalanced1Core(root.left,left) && isBalanced1Core(root.right, right)){
            int dif = left[0] - right[0];
            if (dif <= 1 && dif >= -1){
                deep[0] = 1 + (left[0] > right[0] ? left[0] : right[0]);
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args){
        BiTreeNode root1 = new BiTreeNode(5);
        BiTreeNode root2 = new BiTreeNode(3);
        BiTreeNode root3 = new BiTreeNode(7);
        BiTreeNode root4 = new BiTreeNode(2);
        BiTreeNode root5 = new BiTreeNode(4);
        BiTreeNode root6 = new BiTreeNode(8);
        root1.left = root2;
        root1.right = root3;
        root2.left = root4;
        root2.right = root5;
        root5.left = root6;
        System.out.println(new IsBalancedBiTree().isBalanced(root1));
        System.out.println(new IsBalancedBiTree().isBalanced1(root1));
    }
}


/*
56：数组中数字出现的次数
 */
/*
题目一：数组中只出现一次的两个数字
描述：数组里除了两个数字之外，其他数字都出现了两次。编写函数找出这两个只出现一次的数字
要求时间复杂度O(n) 空间复杂度O(1)

思路：异或 不同为1，相同为0
      任何一个数字异或自己为0
      假若一个数组中只有一个出现一次，其他数字出现2次，那么我们通过从开始异或到结尾，最后的结果就是该数字
      该题的关键是把数组分为2个只包含一个出现一次的数的数组
      遍历数据，将所有数据异或，得到一值，找出该值的二进制中最右边为1的位数，然后再次遍历数据，将该位为1和
      不为1的分为两部分，这两部分里每部分都包含一个出现一次的值
 */


class FindNumAppearenceOnce{
    void findNumApperanceOnce(int[] data){
        int num1 = 0;
        int num2 = 0;
        if (data == null || data.length < 2)
            return;
        int result = 0;
        for (int i : data)
            result ^= i;
        int indexOf1 = findFirstBitIs1(result);
        for (int i = 0; i < data.length; i++){
            if (isBit1(data[i],indexOf1))
                num1 ^= data[i];
            else num2 ^= data[i];
        }
        System.out.println(num1);
        System.out.println(num2);
    }
    int findFirstBitIs1(int num){
        int indexBit = 0;
        while (((num & 0x1) == 0) && (indexBit < 8 * 4)){
            num = num >> 1;
            ++indexBit;
        }
        return indexBit;
    }
    boolean isBit1(int num, int indexBit){
        num = num >> indexBit;
        return (num & 0x1) == 1;
    }
    public static void main(String[] args){
        int[] arr = {2,4,3,6,3,2,5,5};
        new FindNumAppearenceOnce().findNumApperanceOnce(arr);
    }
}


/*
题目二：数组中唯一只出现一次的数字
描述：在一个数组中除了一个数字只出现一次之外，其他数字都出现了三次，找出只出现一次的值
思路：把数组中所有数字的二进制表示的每一位都加起来。如果某一位的和能被3整除，
那么那个只出现一次的数字二进制表示中对应的那一位是0，否则就是1
 */

class FindNumAppearenceOnceThree{
    int findNumOnce(int[] data) throws Exception{
        if (data == null || data.length <= 0)
            throw new Exception("data is error");
        int[] bitSum = new int[32];
        for (int i = 0; i < data.length; i++){
            int bitMask = 1;
            for (int j = 31; j >= 0; --j){
                int bit = data[i] & bitMask;
                if (bit != 0)
                    bitSum[j] += 1;
                bitMask = bitMask << 1;
            }
        }
        int result = 0;
        for (int i = 0; i < 32; ++i){
            result = result << 1;
            result += bitSum[i] % 3;
        }
        return result;
    }
}


/*
57：和为s的数字
 */
/*
题目一：和为s的两个数字
描述：输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。
如果有多对数字的和等于s，则输出任意一对即可

思路一：先固定一个数k，接着用用二分查找 s-k
思路二：两边压缩，左指针指向最左边，右指针指向最右边，如果左右指针指向的数字之和大于s，
则右指针向左移，反之左指针向右移，知道遇见相等的情况，如果左指针大于等于右指针还没有找到等于的，返回false
 */

class FindTwoNumSumS{
    boolean findTwoSum(int[] arr, int sum){
        boolean flag = false;
        if (arr == null || arr.length <= 0)
            return false;
        int left = 0;
        int right = arr.length - 1;
        while (left < right){
            int curSum = arr[left] + arr[right];
            if (curSum == sum){
                System.out.println(arr[left]);
                System.out.println(arr[right]);
                flag = true;
                break;
            }
            else if (curSum > sum)
                --right;
            else ++left;
        }
        return flag;
    }
    public static void main(String[] args){
        int[] arr = {1,2,4,7,11,15};
        new FindTwoNumSumS().findTwoSum(arr,15);
    }
}


/*
题目二：和为s的连续正数序列
描述：输入一个正数s，打印出所有和为s的连续正数序列（至少含有两个数）,序列为{1，2，3，4，5.。。。。}
思路：初始化left=1 和 right=2，如果从left+right>s，则small=small+1，反之right=right+1，若相等则打印
一直增加 small到(1+s)/2为止
 */

class FindContinuousSequence{
    void findSequence(int sum){
        if (sum < 3)
            return;
        int left = 1;
        int right = 2;
        int middle = (1 + sum) / 2;
        int curSum = left + right;
        while (left < middle){
            if (curSum == sum){
                print(left,right);
                ++right;
                curSum += right;
            }
            else if (curSum > sum){
                curSum -= left;
                ++left;
            }else {
                ++right;
                curSum += right;
            }
        }
    }
    void print(int left, int right){
        for (int i = left; i <= right; i++){
            System.out.print(i + " ");
        }
        System.out.println();
    }
    public static void main(String[] args){
        new FindContinuousSequence().findSequence(25);
    }
}


/*
58：翻转字符串
 */
/*
题目一：翻转单词顺序
描述：输入哟个英文句子，反转句子中单词的顺序，但单词内字符的顺序不变，为简单起见，标点符号和普通字幕一样处理
例如 “I am a student.” --> “student. a am I”
 */

class ReverseSeq{
    String reverse(String str){
        if (str.equals(""))
            return null;
        String[] strs = str.split(" ");
        for (int i = 0, j = strs.length - 1; i < j; ++i,--j){
            SwapArray.swap(strs,i,j);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strs.length; ++i){
            if (i == 0)
                stringBuilder.append(strs[i]);
            else stringBuilder.append(" " + strs[i]);
        }
        return stringBuilder.toString();
    }
    public static void main(String[] args){
        String str = "I am a student.";
        System.out.println(new ReverseSeq().reverse(str));
    }
}



/*
题目二：左旋转字符串
描述：字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部
    请定义一个函数实现字符串的左旋转功能
    例如输入"abcdefg", 2 输出 “bcdefgab”
 */

class LeftRotteString{
    void reverse(char[] chs, int left, int right){
        if (chs == null || left > right || right >chs.length)
            return;
        while (left < right){
            char temp = chs[left];
            chs[left] = chs[right];
            chs[right] = temp;
            ++left;
            --right;
        }
    }

    String leftRotate(String str, int n){
        if (str.equals("") || str.length() <= 0 || n > str.length())
            return null;
        char[] chs = str.toCharArray();
        reverse(chs,0,n - 1);
        reverse(chs,n,chs.length - 1);
        reverse(chs,0,chs.length - 1);
        return String.valueOf(chs);
    }
    public static void main(String[] args){
        String str = "abcdef";
        System.out.println(new LeftRotteString().leftRotate(str, 3));
    }
}



/*
59：队列的最大值
 */
/*
题目一：滑动窗口的最大值
描述：给定一个数组和滑动窗口的大小，找出所有滑动窗口里的最大值
思路一：可设计两个栈实现队列，该栈中包含最大值函数，（面试时时间不一定够）
思路二：以{2，3，4，2，6，2，5，1}，窗口为3为例说明
        第一个数字是2，把它存入队列，此时队列里只有2，第二个数字是3，由于它比2大，所以2不可能为窗口最大值，从队列删除2，加入3
        第三个数字4的步骤类似，此时滑动窗口已经有3个数了，它的最大值是4.第4个数字是2，比4小，当4滑出窗口时，2可能为最大值，
        因此把2加入队列尾部。此时队列只有4，2最大值仍是4。第5个数字是6，比2，4都打，因此2，4都不可能是最大值了，删除2，4.插入6
        此时最大值为6，第6个数字是2，加入队列，第7个数字是5，比2大，此时删除2加入5，最大值仍是6.第8个数字是1，但此时6已经不在
        窗口中，所以最大值是5.判断队列中最大值是不是在窗口里可以通过将下标存入队列而不是最大值存入队列来判断
 */

class FindMaxNumInWindows{
    Queue findMaxInWindow(int[] arr, int size){
        if (arr == null || size <= 0 || size >arr.length)
            return null;
        LinkedList<Integer> queueIndex = new LinkedList<Integer>();
        Queue<Integer> maxNumQueue = new LinkedList<Integer>();
        for (int i = 0; i < size; ++i){
            while (!queueIndex.isEmpty() && arr[i] > arr[queueIndex.peekFirst()])
                queueIndex.pollFirst();
            queueIndex.offerFirst(i);
        }
        for (int i = size; i < arr.length; ++i){
            maxNumQueue.offer(arr[queueIndex.peekFirst()]);
            while (!queueIndex.isEmpty() && arr[i] > arr[queueIndex.peekLast()])
                queueIndex.pollLast();
            while (!queueIndex.isEmpty() && queueIndex.peekFirst() <= (int)(i - size))
                queueIndex.pollFirst();
            queueIndex.offerLast(i);
        }
        maxNumQueue.offer(arr[queueIndex.pollFirst()]);
        return maxNumQueue;
    }
    public static void main(String[] args){
        int[] arr = {2,3,4,2,6,2,5,1};
        @SuppressWarnings("unchecked")
        Queue<Integer> queue = (Queue<Integer>) new FindMaxNumInWindows().findMaxInWindow(arr,3);
        for (Integer i : queue)
            System.out.print(i + " ");
    }
}




/*
题目二：队列的最大值
描述：定义一个队列并实现函数得到队列里的最大值，要求函数max,push,pop的时间复杂度都为O(1)
 */
class Element{
    int number;
    int max;
    Element next;
}
class MyQueue{
    Element[] data;
    int front;
    int back;
    int size;
    private MyQueue(){}
    public static MyQueue getMyQueue(){
        return new MyQueue();
    }
}
class MaxQueue{
    public MyQueue MaxQueueInit(){
        Element[] data = new Element[100];
        MyQueue myQueue = MyQueue.getMyQueue();
        myQueue.data = data;
        myQueue.size = 100;
        myQueue.front =0;
        myQueue.back =0;
        return myQueue;
    }
    public void push(MyQueue queue,int number){
        Element element = new Element();
        element.number = number;
    }

}
























































