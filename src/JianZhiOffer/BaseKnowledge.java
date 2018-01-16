package JianZhiOffer;

import java.util.Map;
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
    public BiTreeNode parent;
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


