from simple_data_structure import Stack


def graph_dfs(G, start, goal):
    # G = (V, E) is the graph with vertices, V, and edges, E.
    V, E = G
    stack = Stack()
    visited = Set()
    stack.push(start)

    while not stack.isEmpty():
        # A vertex is popped from the stack. This is called the current vetex.
        current = stack.pop()
        # The current vertex is added to the visited set.
        visited.add(current)

        # If the current vertex is the goal vertex, then we discontinue the
        # search reporting that we found the goal.
        if current == goal:
            return True # or return path to goal perhaps

        # Otherwise, for every adjacent vertex, v, to the current vertex
        # in the graph, v is pushed on the stack of vertices yet to search
        # unless v is already in the visited set in which case the edge
        # leading to v is ignored.
        for v in adjacent(current, E):
            if not v in visited:
                stack.push(v)
        
        # If we get this far, then we did not find the goal.
        return False # or return an empty path