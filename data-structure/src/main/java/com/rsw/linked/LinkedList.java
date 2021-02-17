package com.rsw.linked;

/**
 * class description：链表实现原理
 *
 * @author rsw
 * Date: 2019/11/23
 * Time: 10:20
 **/
public class LinkedList<E> {

    /**
     * 内部类：表示链表的节点
     */
    private class Node<E> {
        /** 所存储的元素 */
        E e;

        /** 节点 */
        Node<E> next;

        /** 索引（算法题中的量，并不在数据中使用） */
        int val;

        Node(int x) { val = x; }

        Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    /** 虚拟头结点 */
    private Node<E> dummyHead;

    /** 存储元素数量 */
    private int size;

    public LinkedList() {
        dummyHead = new Node<E>();
        size = 0;
    }

    /**
     * 获取链表中的元素个数
     *
     * @return /
     */
    public int getSize() {
        return size;
    }

    /**
     * 返回链表是否为空
     *
     * @return /
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在链表的index(0-based)位置添加新的元素e
     *
     * @param index 索引
     * @param e     要添加的元素
     */
    public void add(int index, E e) {

        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index.");
        }

        Node<E> prev = dummyHead;
        //检索到index的位置
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        /*
          三步合并成一步（详情见下边注释）
                Node<E> node = new Node<E>(e);
                node.next = prev.next;
                prev.next = node;
        */
        prev.next = new Node<E>(e, prev.next);
        size++;
    }

    /**
     * 在链表头添加新的元素e
     *
     * @param e /
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 在链表末尾添加新的元素e
     *
     * @param e /
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * 获得链表的索引位置的元素
     *
     * @param index /
     * @return /
     */
    public E get(int index) {

        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get failed. Illegal index.");
        }

        Node<E> cur = dummyHead.next;
        //如果index是0，不执行循环，直接返回第一个元素，所以cur直接指向第一个元素，即dummyHead.next
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.e;
    }

    /**
     * 获得链表的第一个元素
     *
     * @return /
     */
    public E getFirst() {
        return get(0);
    }

    /**
     * 获得链表的最后一个元素
     *
     * @return /
     */
    public E getLast() {
        return get(size - 1);
    }

    /**
     * 修改链表的第index(0-based)个位置的元素为e
     *
     * @param index /
     * @param e     /
     */
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Set failed. Illegal index.");
        }

        Node<E> cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.e = e;
    }

    /**
     * 查找链表中是否有元素e
     *
     * @param e /
     * @return /
     */
    public boolean contains(E e) {
        Node<E> cur = dummyHead.next;
        while (cur != null) {
            if (cur.e.equals(e)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /**
     * 从链表中删除index(0-based)位置的元素, 返回删除的元素
     *
     * @param index /
     * @return /
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Remove failed. Index is illegal.");
        }

        Node<E> prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        Node<E> retNode = prev.next;
        prev.next = retNode.next;
        retNode.next = null;
        size--;

        return retNode.e;
    }

    /**
     * 从链表中删除第一个元素, 返回删除的元素
     *
     * @return /
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 从链表中删除最后一个元素, 返回删除的元素
     *
     * @return /
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 从链表中删除元素e
     *
     * @param e /
     */
    public void removeElement(E e) {

        Node<E> prev = dummyHead;
        while (prev.next != null) {
            if (prev.next.e.equals(e)) {
                break;
            }
            prev = prev.next;
        }

        if (prev.next != null) {
            Node<E> delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size--;
        }
    }

    /**
     * 寻找链表的中点
     *
     * @param head /
     * @return /
     */
    public Node<E> findMidpoint(Node<E> head) {
        /*
            快慢指针找到链表的中点
			快指针是慢指针的2倍，快指针为空或者快指针的下一个节点为空，
		证明慢指针找到了链表的中点
         */
        Node<E> fast = head.next.next;
        Node<E> slow = head.next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            //链表的中点
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 实现链表的反转（遍历法）
     *
     * @param head 链表
     * @return 反转后的链表
     */
    public Node<E> reverseIteratively(Node<E> head) {
        //pre用来保存先前结点
        Node<E> pre = null;
        //next用来做临时变量
        Node<E> next = null;
        /*
            循环过程：
               在头结点node遍历的时候此时为1结点
                next = 1结点.next(2结点)
                1结点.next=pre(null)
                pre = 1结点
                node = 2结点
                进行下一次循环node=2结点
                next = 2结点.next(3结点)
                2结点.next=pre(1结点)=>即完成2->1
                pre = 2结点
                node = 3结点
                进行循环…
         */
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 实现链表的反转（递归法）
     * 递归实质上就是系统帮你压栈的过程，系统在压栈的时候会保留现场
     *
     * @param head 链表
     * @return 反转后的链表
     */
    public Node<E> reverse(Node<E> head) {
        //递归
        if (null == head || null == head.next) {
            return head;
        }
        /*
            此时head=3结点，temp=3结点.next(实际上是4结点)
            执行Node newHead = reverse(head.next);传入的head.next是4结点，返回的newHead是4结点
         */
        Node<E> temp = head.next;
        //进入递归
        Node<E> newHead = reverse(head.next);
        /*
           弹栈过程:
            程序继续执行 temp.next = head就相当于4->3
            head.next = null 即把3结点指向4结点的指针断掉
            返回新链表的头结点newHead
         */
        temp.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * 判断链表是否是回文链表
     *
     * @param head /
     * @return /
     */
    public boolean isPalindrome(Node<E> head) {
        //链表是空，或者只有一个元素
        if (head == null || head.next == null) {
            return true;
        }
        //快慢指针找到链表的中点
        //快指针是慢指针的2倍，快指针为空或者快指针的下一个节点为空，证明慢指针找到了链表的中点
        Node<E> fast = head.next.next;
        Node<E> slow = head.next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            //链表的中点
            slow = slow.next;
        }

        //翻转链表前半部分
        Node<E> pre = null;
        Node<E> next = null;
        while (head != slow) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        /*
            //边移动（找中点），边反转
            Node slow = head, fast = head;
            Node pre = head, prepre = null;
            while(fast != null && fast.next != null) {
                pre = slow;
                slow = slow.next;
                fast = fast.next.next;
                pre.next = prepre;
                prepre = pre;
            }
         */
        //如果是奇数个节点，去掉后半部分的第一个节点。
        if (fast != null) {
            slow = slow.next;
        }
        //回文校验,前后链表一次进行对比
        while (pre != null) {
            if (pre.val != slow.val) {
                return false;
            }
            pre = pre.next;
            slow = slow.next;
        }

        return true;

    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        for (Node<E> cur = dummyHead.next; cur != null; cur = cur.next) {
            res.append(cur).append("->");
        }
        res.append("NULL");

        return res.toString();
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {

        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }

        linkedList.add(2, 666);
        System.out.println(linkedList);
    }


}


