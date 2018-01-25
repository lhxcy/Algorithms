package _100AlgorithmsCode;
import InterviewBook.*;
/**
 * Created by XCY on 2018/1/11.
 */
public class AlgorithmsCode {
}
class ListNode{
    int value;
    ListNode next;
    ListNode(int value){this.value = value;}
}
class BiListNode{
    int value;
    BiListNode pre;
    BiListNode next;
    BiListNode(int value){this.value = value;}
}

class BiTreeNode{
    int value;
    BiTreeNode left;
    BiTreeNode right;
    BiTreeNode(int value){this.value = value;}
}


/*
1：将二叉搜索树转换为双向链表
 */
class _01_BiSearchTree2BiList{
    BiTreeNode convert(BiTreeNode root){
        BiTreeNode lastNode = null;
        lastNode = convertCore(root,null);
        while (lastNode.left != null)
            lastNode = lastNode.left;
        return lastNode;
    }
    BiTreeNode convertCore(BiTreeNode root, BiTreeNode lastNode){
        if (root == null)
            return lastNode;
        if (root.left != null)
            lastNode = convertCore(root.left, lastNode);
        root.left = lastNode;
        if (lastNode != null) lastNode.right = root;
        lastNode = root;
        if (root.right != null)
            lastNode = convertCore(root.right, lastNode);
        return lastNode;
    }
}


/*
2：设计包含min函数的栈
push
pop
min
 */
class MinStackElements{
    int value;
    int min;
}
class MinStack{
    MinStackElements[] data;
    int top;
    int size;
    private MinStack(){}
    public static MinStack getMinStack(){
        return new MinStack();
    }
}
class MyMinStack{
    public MinStack MinStackInit(){
        MinStackElements[] data = new MinStackElements[100];
        MinStack stack = MinStack.getMinStack();
        stack.data = data;
        stack.size = 100;
        stack.top =0;
        return stack;
    }
    public void push(MinStack stack, int d) throws Exception{
        if (stack.top > stack.size)
            throw new Exception("out of stack space");
//        MinStackElements p = stack.data[stack.top];
        stack.data[stack.top].value = d;
        stack.data[stack.top].min = stack.top == 0 ? d: stack.data[stack.top - 1].min;
        if (stack.data[stack.top].min > d)
            stack.data[stack.top].min = d;
        ++stack.top;
    }
    public int pop(MinStack stack)throws Exception{
        if (stack.top == 0)
            throw new Exception("stack is empty！");
        return stack.data[--stack.top].value;
    }
    public int min(MinStack stack)throws Exception{
        if (stack.top == 0)
            throw new Exception("stack is empty！");
        return stack.data[stack.top - 1].min;
    }

}

/*
3：求子数组的最大和
描述：输入一个整型数组，数组里有正有负
 */
class MaxOfSumInSubArray{
    public static void main(String[] args){
        int[] arr = {2,3,-6,9,2,-1,7};
        System.out.println(maxSumOfSubArray(arr));
    }
    public static int maxSumOfSubArray(int[] arr){
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int value : arr){
            sum += value;
            if (sum > max) max = sum;
            else if (sum < 0) sum = 0;
        }
        return max;
    }
}
/*
4：在二元树中找出和为某一值的所有路径(不包含局部路径)
描述：输入一个整数和一个二元树
 */

class FindPathInBiTree{
    public void findPath(BiTreeNode root, int sum){
        int[] path = new int[getMaxDepth(root)];
        findPathCore(root,sum,path,0);
    }
    public void findPathCore(BiTreeNode root, int sum, int[] path, int top){
        path[top++] = root.value;
        sum -= root.value;
        if (root.left == null && root.right == null){//到叶节点
            if (sum == 0) printPath(path,top);
        }else {
            if (root.left != null) findPathCore(root.left, sum, path, top);
            if (root.right != null) findPathCore(root.right, sum, path, top);
        }

    }
    public void printPath(int[] path, int top){
        for (int i = 0; i < top; i++){
            System.out.print(path[i] + '\t');
            System.out.println();
        }
    }
    // 获取最大深度
    public  int getMaxDepth(BiTreeNode root) {
        if (root == null)
            return 0;
        else {
            int left = getMaxDepth(root.left);
            int right = getMaxDepth(root.right);
            return 1 + Math.max(left, right);
        }
    }
}


/*
5：查找最小的k个元素
构建堆，选取
堆是一颗完全二叉树，通常存放在一个数组中，父节点和孩子节点的父子关系通过数组下标来确定
 */
class MinHeap{
    int[] heap;
    private int size;
    public MinHeap(int[] array){
        this.heap = array;
        this.size = array.length;
    }
    public void heapSort(){
        for (int i = 0; i < heap.length; i++){
            swap(0,size - 1);
            size--;
            System.out.println("ssss  " + size);
            siftDown(0);
            Demo.printHeapTree(heap);
        }
    }
    public void buildMinHeap(){
        for (int i = size / 2 - 1; i >= 0; i--){
            siftDown(i);
        }
    }
    private void siftDown(int i){
        int left = letfNode(i);
        int right = rightNode(i);
        int minest;
        if (left < size && heap[left] < heap[i]){
            minest = left;
        }else {
            minest = i;
        }
        if (right < size && heap[right] < heap[minest]){
            minest = right;
        }
        if (minest == i || minest > size)
            return;
        swap(i,minest);
        siftDown(minest);
    }
    private void swap(int i, int j){
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    private int Parent(int i){
//        return i/2;//1存
        return (i-1)/2;//0存
    }
    private int letfNode(int i){
//        return 2 * i;//从1开始存
        return 2 * (i + 1) - 1;//从0开始存
    }
    private int rightNode(int i){
//        return 2 * i + 1;//从1存
        return 2 * (i + 1);//从0存
    }
}

class Demo {
    public static void main(String[] args)
    {
//        int[] array=new int[]{1,2,3,4,7,8,9,10,14,16};
        int[] array=new int[]{14,3,4,7,9,2,1};
        MinHeap heap1=new MinHeap(array);
        System.out.println("执行最小堆化前堆的结构：");
        printHeapTree(heap1.heap);
        heap1.buildMinHeap();
        System.out.println("执行最小堆化后堆的结构：");
        printHeapTree(heap1.heap);
        heap1.heapSort();
        System.out.println("执行堆排序后数组的内容");
        printHeap(heap1.heap);

    }
    public static void printHeapTree(int[] array)
    {
        for(int i=1;i<array.length;i=i*2)
        {
            for(int k=i-1;k<2*(i)-1&&k<array.length;k++)
            {
                System.out.print(array[k]+" ");
            }
            System.out.println();
        }
    }
    private static void printHeap(int[] array)
    {
        for(int i=0;i<array.length;i++)
        {
            System.out.print(array[i]+" ");
        }
    }


}





class MaxHeap {
    int[] heap;
    int heapsize;
    public MaxHeap(int[] array)
    {
        this.heap=array;
        this.heapsize=heap.length;
    }
    public void BuildMaxHeap()
    {
        for(int i=heapsize/2-1;i>=0;i--)
        {
            Maxify(i);//依次向上将当前子树最大堆化
        }
    }
    public void HeapSort()
    {
        for(int i=0;i<heap.length;i++)
        {
            //执行n次，将每个当前最大的值放到堆末尾
            int tmp=heap[0];
            heap[0]=heap[heapsize-1];
            heap[heapsize-1]=tmp;
            heapsize--;
            System.out.println("lllll  "+heap.length);
            System.out.println("ssss   "+heapsize);
            Maxify(0);
        }
    }
    public void Maxify(int i)
    {
        int l=Left(i);
        int r=Right(i);
        int largest;

        if(l<heapsize&&heap[l]>heap[i])
            largest=l;
        else
            largest=i;
        if(r<heapsize&&heap[r]>heap[largest])
            largest=r;
        if(largest==i||largest>=heapsize)//如果largest等于i说明i是最大元素 largest超出heap范围说明不存在比i节点大的子女
            return ;
        int tmp=heap[i];//交换i与largest对应的元素位置，在largest位置递归调用maxify
        heap[i]=heap[largest];
        heap[largest]=tmp;
        Maxify(largest);
    }
    public void IncreaseValue(int i,int val)
    {
        heap[i]=val;
        if(i>=heapsize||i<=0||heap[i]>=val)
            return;
        int p=Parent(i);
        if(heap[p]>=val)
            return;
        heap[i]=heap[p];
        IncreaseValue(p, val);
    }

    private int Parent(int i)
    {
        return (i-1)/2;
    }
    private int Left(int i)
    {
        return 2*(i+1)-1;
    }
    private int Right(int i)
    {
        return 2*(i+1);
    }
}

class Demo1 {
    public static void main(String[] args)
    {
        int[] array=new int[]{1,2,3,4,7,8,9,10,14,16};
        MaxHeap heap=new MaxHeap(array);
        System.out.println("执行最大堆化前堆的结构：");
        printHeapTree(heap.heap);
        heap.BuildMaxHeap();
        System.out.println("执行最大堆化后堆的结构：");
        printHeapTree(heap.heap);
        heap.HeapSort();
        System.out.println("执行堆排序后数组的内容");
        printHeap(heap.heap);

    }
    private static void printHeapTree(int[] array)
    {
        for(int i=1;i<array.length;i=i*2)
        {
            for(int k=i-1;k<2*(i)-1&&k<array.length;k++)
            {
                System.out.print(array[k]+" ");
            }
            System.out.println();
        }
    }
    private static void printHeap(int[] array)
    {
        for(int i=0;i<array.length;i++)
        {
            System.out.print(array[i]+" ");
        }
    }
}


/*
6：反转链表
 */
class AnotherReverseLisNode{
    ListNode reverseNodeByRecur(ListNode head){
        if (head == null || head.next == null)
            return head;
        ListNode ph = reverseNodeByRecur(head.next);
        head.next.next =head;
        head.next = null;
        return ph;
    }

    ListNode reverseNodeNoRecur(ListNode head){
        if (head == null || head.next == null)
            return head;
        ListNode pre = null;
        ListNode node = head;
        while (node.next != null){
            node.next = pre;
            pre = node;
            node = node.next;
        }
        node.next = pre;
        return node;
    }
}


/*
7：颠倒字符串
 */
class ReverseString{
    static String reverse(String str){
        char[] chs = str.toCharArray();
        for (int i = 0,j = chs.length - 1; i < j; i++,j--){
            char temp = chs[i];
            chs[i] = chs[j];
            chs[j] = temp;
        }
//        return new String(chs);
        return String.valueOf(chs);
    }
}
class StringTest{
    public static void main(String[] args){
        ReverseString rstr = new ReverseString();
        String str = "hello world!";
//        char[] chs =str.toCharArray();
        String ans = rstr.reverse(str);
        System.out.print(ans);

//        for (char c : chs)
//            System.out.print(c);
    }
}

/*
8：颠倒一个句子中的词的顺序,然后颠倒字母的顺序
 */

class ReverseSentence{
    void reverseWordsInSentence(String[] strs){
        for (int i = 0,j = strs.length - 1; i < j; i++,j--){
            String temp = strs[i];
            strs[i] = strs[j];
            strs[j] = temp;
        }
    }
}
class TestReverseSentence{
    public static void main(String[] args){
        ReverseSentence rs = new ReverseSentence();
        String string = "i am lucy tom";
        String revesAll = ReverseString.reverse(string);
        System.out.println(revesAll);
        String[] strs = string.trim().split(" ");
        rs.reverseWordsInSentence(strs);
        for (String str : strs)
            System.out.print(str + " ");
    }
}







