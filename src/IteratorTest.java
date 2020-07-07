import java.util.NoSuchElementException;

class IteratorTest {
    public static void main(String[] args) {

        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();
        doublyLinkedList.addFirst(1);
        doublyLinkedList.addFirst(2);
        doublyLinkedList.addLast(5);
        doublyLinkedList.addLast(9);
        System.out.println(doublyLinkedList);
        LinkInterator<Integer> li = new LinkInterator<>(doublyLinkedList);
        System.out.println(li.getCurrent().value);
        li.next();
        System.out.println(li.getCurrent().value);
        System.out.println("hasNext:" + li.hasNext());
        li.next();
        System.out.println(li.getCurrent().value);
        System.out.println("hasNext:" + li.hasNext());
        li.reset();
        System.out.println(li.getCurrent().value);
        System.out.println("atEnd:" + li.atEnd());
        li.insertAfter(10);
        System.out.println(doublyLinkedList);
        li.insertAfter(11);
        System.out.println(doublyLinkedList);
        li.insertBefore(20);
        System.out.println(doublyLinkedList);
        System.out.println(li.deleteCurrent());
        System.out.println(doublyLinkedList);


    }
}

class Node<E> {

    E value;
    Node<E> next;
    Node<E> prev;

    Node(E element, Node<E> next, Node<E> prev) {
        this.value = element;
        this.next = next;
        this.prev = prev;
    }

    E getValue() {
        return value;
    }

    Node<E> getNext() {
        return next;
    }
}

class DoublyLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        size = 0;
    }

    public Node<E> getHead() {
        return head;
    }

    public int size() {
        return size;
    }

    public String toString() {

        Node<E> tmp = head;
        StringBuilder result = new StringBuilder();

        while (tmp != null) {
            result.append(tmp.getValue()).append(" ");
            tmp = tmp.getNext();
        }

        return result.toString();
    }

    // Добавляем элемент в начало
    void addFirst(E elem) {

        Node<E> tmp = new Node<>(elem, head, null);

        if (head != null) {
            head.prev = tmp;
        }

        head = tmp;

        if (tail == null) {
            tail = tmp;
        }
        size++;
    }

    // Добавляем элемент в конец
    void addLast(E elem) {

        Node<E> tmp = new Node<>(elem, null, tail);

        if (tail != null) {
            tail.next = tmp;
        }

        tail = tmp;

        if (head == null) {
            head = tmp;
        }
        size++;
    }

    // Удаляем первый элемент
    public void removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size--;
    }

    // Удаляем последний элемент
    public void removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        size--;
    }

}

/**
 * Класс итератора по списку
 *
 * @param <E> В тесте будем использовать объекты типа Integer для простоты отладки
 */
class LinkInterator<E> {
    private Node<E> current;
    private Node<E> previous;
    private final DoublyLinkedList<E> list;

    public LinkInterator(DoublyLinkedList<E> list) {
        this.list = list;
        this.reset();
    }

    // Устанавливаем итератор в начало списка
    public void reset() {
        current = list.getHead();
        previous = null;
    }

    // Перемещаем итератор к слеующему элементу
    public void next() {
        previous = current;
        current = current.next;
    }

    // Получаем значение элемента, на кторый указывает итератор
    public Node<E> getCurrent() {
        return current;
    }

    // Мы в конце списка?
    public boolean atEnd() {
        return (current.next == null);
    }

    // Есть чего ещё поитерировать?
    public boolean hasNext() {
        return (current.next != null);
    }

    // Вставляем элемент ПОСЛЕ указателя итератора
    // Если список изначально пуст - вставим элемент как первый и на него установим итератор
    // Если список не пуст - переприсвоим значения указателей и вставим элемент
    // Не забудем передвинуть итератор
    public void insertAfter(E elem) {
        if (list.size() == 0) {
            list.addFirst(elem);
            current = list.getHead();
        } else {
            Node<E> newNode = new Node<>(elem, current, previous);
            newNode.next = current.next;
            current.next = newNode;
            next();
        }
    }

    // Вставляем элемент ПЕРЕД указателем итератора
    // Если список изначально пуст - вставим элемент как первый и сбросим итератор
    // Если список не пуст - переприсвоим значения указателей для вставки элемента
    public void insertBefore(E elem) {
        Node<E> newNode = new Node<>(elem, current, previous);
        if (previous == null) {
            list.addFirst(elem);
            reset();
        } else {
            newNode.next = previous.next;
            previous.next = newNode;
            current = newNode;
        }
    }

    // Удаление элемента, на который указывает итератор с возвратом этого элемента.
    public E deleteCurrent() {
        E value = current.value;
        // Здесь нало бы сделать проверку на previous==null, оставлю на потом
        previous.next = current.next;
        if (atEnd()) {
            reset();
        } else {
            current = current.next;
        }
        return value;
    }

}

