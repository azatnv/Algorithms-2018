package lesson5

import lesson5.Graph.Vertex
import java.util.*

class VertexInfo2(
        val vertex: Vertex,
        val distance: Int,
        val path: List<Vertex>
) : Comparable<VertexInfo2> {
    override fun compareTo(other: VertexInfo2): Int {
        return (distance.compareTo(other.distance))
    }
}

//Трудоемкость T=O(V*V); ресурсоемкость R=O(V)
fun Graph.longestPath(from: Vertex): Map<Vertex, VertexInfo2> {
    val info = mutableMapOf<Vertex, VertexInfo2>()      //R=O(V)
    for (vertex in this.vertices) {
        info[vertex] = VertexInfo2(vertex, 0, listOf(vertex))
    }
    val fromInfo = VertexInfo2(from, 0, listOf(from))
    val queue = PriorityQueue<VertexInfo2>()
    queue.add(fromInfo)
    info[from] = fromInfo
    while (queue.isNotEmpty()) {       //T=O(V*V)
        val currentInfo = queue.poll()
        val currentVertex = currentInfo.vertex
        val currentDistant = currentInfo.distance
        val newDistance = currentDistant + 1
        for (child in this.getNeighbors(currentVertex)) {
            if (info[child]!!.distance < newDistance
                    && !currentInfo!!.path.contains(child)) {
                for (grandchild in this.getNeighbors(child)) {
                    if (info[grandchild]!!.distance == currentDistant && queue.contains(info[grandchild]) &&
                            !currentInfo.path.contains(child) && !currentInfo.path.contains(grandchild) &&
                            !info[grandchild]!!.path.contains(child) && !info[grandchild]!!.path.contains(currentVertex)) {
                        queue.remove(info[grandchild])
                        val pathForParent = info[grandchild]!!.path + child + currentVertex
                        val pathForGrandchild = currentInfo.path + child + grandchild
                        val newInfoForParent = VertexInfo2(currentVertex, newDistance + 1, pathForParent)
                        val newInfoForGrandchild = VertexInfo2(grandchild, newDistance + 1, pathForGrandchild)
                        val newInfoForChild = VertexInfo2(child, newDistance, currentInfo.path + child)
                        queue.add(newInfoForChild)
                        queue.add(newInfoForGrandchild)
                        queue.add(newInfoForParent)
                        info[grandchild] = newInfoForGrandchild
                        info[child] = newInfoForChild
                        info[currentVertex] = newInfoForParent
                    }
                }
                if (queue.contains(info[child]) && info[child]!!.distance == currentDistant &&
                        !info[child]!!.path.contains(currentVertex) && !currentInfo.path.contains(child)) {
                    queue.remove(info[child])
                    val newInfoForParent = VertexInfo2(currentVertex, newDistance, info[child]!!.path + currentVertex)
                    queue.add(newInfoForParent)
                    info[currentVertex] = newInfoForParent
                }
                val newInfoForChild = VertexInfo2(child, newDistance, currentInfo.path + child)
                queue.add(newInfoForChild)
                info[child] = newInfoForChild
            }
        }
    }
    return info
}

fun Map<Vertex, VertexInfo2>.unrollLongestPath(to: Vertex): List<Vertex> {
    val result = mutableListOf<Vertex>()
    val listPath: List<Vertex> = this[to]!!.path
    for (vertex in listPath) {
        result += vertex
    }
    return result
}