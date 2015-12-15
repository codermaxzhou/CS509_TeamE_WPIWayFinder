package adminmodule;

import java.util.Collection;

public interface RoutingAlgorithm {
    public void resetGraph(Collection<Edge> edgeSet, Collection<Point> vertexSet);
    public Collection<Edge> calculate(Point start, Point end);
}
