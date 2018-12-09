package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    //Трудоемкость T=O(V*V*E); ресурсоемкость R=O(E)
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        for (Graph.Vertex vertex : graph.getVertices()) {          //T=O(V*E)
            if (graph.getConnections(vertex).size() % 2 == 1) {    //getConnections() имеет трудоемкость O(E)
                return new ArrayList<>();
            }
        }

        List<Graph.Vertex> resultVertex = new ArrayList<>();
        Set<Graph.Edge> edges = graph.getEdges();          //R=O(E)

        Stack<Graph.Vertex> stack = new Stack<>();
        Graph.Vertex start = graph.getVertices().iterator().next();
        stack.push(start);

        while (!stack.empty()) {                              //T=(V*V*E)
            Graph.Vertex currentVertex = stack.peek();
            if (vertexDegreeIsZero(currentVertex, graph, edges)) { //O(V*E)
                resultVertex.add(currentVertex);
                stack.pop();
            } else {
                for (Graph.Vertex vertex : graph.getNeighbors(currentVertex)) {                    //T=O(V*E)
                    Graph.Edge edge = graph.getConnection(currentVertex, vertex); //O(E)
                    if (edges.contains(edge)) {       //O(E)
                        edges.remove(edge);           //O(E)
                        stack.push(vertex);
                        break;
                    }
                }
            }
        }
        List<Graph.Edge> resultEdge = new ArrayList<>();
        for (int i = 1; i < resultVertex.size(); i++) {     //T=O(V*E)
            resultEdge.add(graph.getConnection(resultVertex.get(i-1), resultVertex.get(i)));
        }
        return resultEdge;
    }

    //Трудоекмость T=O(V*E); R=(1).
    private static boolean vertexDegreeIsZero(Graph.Vertex vertex, Graph graph, Set<Graph.Edge> edges) {
        for (Graph.Vertex node: graph.getNeighbors(vertex)) {    //T=O(V*E)
            Graph.Edge edge = graph.getConnection(node, vertex); //getConnection() имеет трудоемкость O(E)
            if (edges.contains(edge)) return false;
        }
        return true;
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */

    //Трудоемкость T=O(V*V*V); ресурсоемкость R=O(V)
    public static Path longestSimplePath(Graph graph) {
        Path resultPath = new Path(new ArrayList<>());
        for (Graph.Vertex v: graph.getVertices()) {   //T=O(V*V*V)
            Map<Graph.Vertex, VertexInfo2> allPaths = LongestPathKt.longestPath(graph, v);  //T=O(V*V), R=O(V)
            for (VertexInfo2 info: allPaths.values()) {                                     //O(V)
                if (info.getDistance() > resultPath.getLength()) {
                    resultPath = new Path(LongestPathKt.unrollLongestPath(allPaths, info.getVertex()));
                }
            }
            if (resultPath.getLength() == graph.getVertices().size() - 1) break;
        }
        return resultPath;
    }
}
