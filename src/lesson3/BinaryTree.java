package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node<T> parent = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
            newNode.parent = closest;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
            newNode.parent = closest;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> removed = find(t);
        if (removed == null || removed.value != t) return false;
        Node<T> right = removed.right;
        Node<T> left = removed.left;
        if (removed == root) {
            if (size == 1) root = null;
            else {
                Node<T> moved = right != null ? right : left;
                moved.parent = null;
                root = moved;
                if (right != null && left != null) rearrangeSmallerNodeToBiggerSubtree(left, moved);
            }
        }
        else if (right == null && left == null) {
            if (removed.parent.left == removed) removed.parent.left = null;
            else removed.parent.right = null;
        }
        else {
            Node<T> moved = right != null ? right : left;
            moved.parent = removed.parent;
            if (removed.parent.left == removed) removed.parent.left = moved;
            else removed.parent.right = moved;
            if (left != null && right != null) rearrangeSmallerNodeToBiggerSubtree(left, moved);
        }
        size--;
        return true;
    }

    private void removeNode(Node<T> node) {
        remove(node.value);
    }

    private void rearrangeSmallerNodeToBiggerSubtree(Node<T> node, Node<T> subtree) {
        Node<T> closest = find(subtree, node.value);
        node.parent = closest;
        closest.left = node;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    //Трудоемкость: T=O(N) - самый худший случай; T=O(logN) - в среднем случае.
    //Ресурсоемкость R=O(1). Во всех остальных реализованных методах T и R такие же.
    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {}

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private Node<T> findNext() {
            if (current == null) {
                Node<T> first = root;
                if (first == null) return null;
                while (first.left != null) {
                    first = first.left;
                }
                return first;
            }
            else if (current.parent == null && current.right == null) {
                return null;
            }
            else if (current == root) {
                return current.right != null ? find(root.right, root.value) : null;
            }
            else if (current.right != null) {
                return find(current.right, current.value);
            }
            else if (current == current.parent.left) {
                return current.parent;
            }
            else {
                Node<T> branch = current;
                Node<T> previous = current;
                while (branch != root && branch.left != previous) {
                    previous = branch;
                    branch = branch.parent;
                }
                if (branch == root && branch.left != previous) return null;
                return branch;
            }
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            if (current == null) throw new NoSuchElementException();
            BinaryTree.this.removeNode(current);
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        Integer element = (Integer) toElement;
        return new ShellBinaryTree<>(this, element);
    }

    class ShellBinaryTree<G extends Comparable<G>> extends AbstractSet<G> implements SortedSet<G> {
        private BinaryTree delegate;
        private Integer bound;

        ShellBinaryTree(BinaryTree binaryTree, Integer bound) {
            this.delegate = binaryTree;
            this.bound = bound;
        }

        @Override
        public boolean add(G t) {
            @SuppressWarnings("unchecked")
            Integer element = (Integer) t;
            if (element.compareTo(bound) < 0) {
                return delegate.add(element);
            }
            return false;
        }

        @Override
        public boolean remove(Object o) {
            @SuppressWarnings("unchecked")
            Integer element = (Integer) o;
            if (element.compareTo(bound) < 0) {
                return delegate.remove(element);
            }
            return false;
        }

        @Override
        public boolean contains(Object o) {
            @SuppressWarnings("unchecked")
            Integer element = (Integer) o;
            return element.compareTo(bound) < 0 && delegate.contains(o);
        }

        @Override
        public int size() {
            return (int) delegate.stream().filter(it -> (Integer) it < bound).count();
        }

        class ShellBinaryTreeIterator implements Iterator<G> {

            private Iterator iterator = delegate.iterator();

            private G next = null;

            private ShellBinaryTreeIterator() {
                while (iterator.hasNext()) {
                    Integer nextElement = (Integer) iterator.next();
                    if (nextElement.compareTo(bound) < 0) {
                        this.next =  (G) nextElement;
                        break;
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public G next() {
                G result;
                if (next == null) throw new NoSuchElementException();
                else result = next;
                if (iterator.hasNext()) next = (G) iterator.next();
                else next = null;
                return result;
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        }

        @NotNull
        @Override
        public Iterator<G> iterator() {
            return new ShellBinaryTreeIterator();
        }

        @Nullable
        @Override
        public Comparator<? super G> comparator() {
            return null;
        }

        @NotNull
        @Override
        public SortedSet<G> subSet(G fromElement, G toElement) {
            //TODO()
            throw new NotImplementedError();
        }

        @NotNull
        @Override
        public SortedSet<G> headSet(G toElement) {
            //TODO()
            throw new NotImplementedError();
        }

        @NotNull
        @Override
        public SortedSet<G> tailSet(G fromElement) {
            //TODO()
            throw new NotImplementedError();
        }

        @Override
        public G first() {
            //TODO()
            throw new NotImplementedError();
        }

        @Override
        public G last() {
            //TODO()
            throw new NotImplementedError();
        }
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
