class Stack(object):
    def __init__(self):
        self.items = []
    
    def pop(self):
        if self.is_empty():
            raise RuntimeError("Attempt to pop an empty stack")
        
        top_idx = len(self.items) - 1
        item = self.items[top_idx]
        del self.items[top_idx]
        return item

    def push(self, item):
        self.items.append(item)

    def top(self):
        if self.is_empty():
            raise RuntimeError("Attempt to get top of empty stack")
        
        top_idx = len(self.items) - 1
        return self.items[top_idx]

    def is_empty(self):
        return len(self.items) == 0

class HashSet(object):
    def __init__(self, contents=[]):
        self.items = [None] * 10
        self.num_items = 0
    
        for item in contents:
            self.add(item)

    def __add(item, items):
        idx = hash(item) % len(items)
        loc = -1

        while items[idx] != None:
            if items[idx] == item:
                # item already in set
                return False

            if loc < 0 and type(items[idx]):
                loc = idx
            
            idx = (idx + 1) % len(items)
        
        if loc < 0:
            loc = idx
        
        items[loc] = item

        return True

    def __rehash(old_list, new_list):
        for x in old_list:
            if x != None and type(x) != HashSet.__Placeholder:
                HashSet.__add(x, new_list)

        return new_list

    def  add(self, item):
        if HashSet.__add(item, self.items):
            self.num_items += 1
            load = self.num_items / len(self.items)
            if load >= 0.75:
                self.items = HashSet.__rehash(self.items, [None]*2*len(self.items))

    class __Placeholder(object):
        def __init__(self):
            pass

        def __eq__(self, other):
            return False
    
    def __remove(item, items):
        idx = hash(item) % len(items)

        while items[idx] != None:
            if items[idx] == item:
                next_idx = (idx + 1) % len(items)
                if items[next_idx] == None:
                    items[idx] = None
                else:
                    items[idx] = HashSet.__Placeholder()
                return True

            idx = (idx + 1) % len(items)
        
        return False

    def remove(self, item):
        if HashSet.__remove(item, self.items):
            self.num_items -= 1
            load = max(self.num_items, 10) / len(self.items)
            if load <= 0.25:
                self.items = HashSet.__rehash(self.items, [None]*int(len(self.items)/2))
        else:
            raise KeyError("Item not in HashSet")                

    def __contains__(self, item):
        idx = hash(item) % len(self.items)
        while self.items[idx] != None:
            if self.items[idx] == item:
                return True

            idx = (idx + 1) % len(self.items)

        return False

    def __iter__(self):
        for i in range(len(self.items)):
            if self.items[i] != None and type(self.items[i]) != HashSet.__Placeholder:
                yield self.items[i]

    def difference_update(self, other):
        for item in other:
            self.discard(item)

    def difference(self, other):
        result = HashSet(self)
        result.difference_update(other)
        return result
                            
def main():
    s = Stack()
    lst = list(range(10))
    lst2 = []

    for k in lst:
        s.push(k)
    
    if s.top() == 9:
        print("Test 1 passed!")
    else:
        print("Test 1 failed!")
    
    while not s.is_empty():
        lst2.append(s.pop())

    lst2.reverse()

    if lst2 != lst:
        print("Test 2 failed!")
    else:
        print("Test 2 passed!")

    try:
        s.pop()
        print("Test 3 failed!")
    except RuntimeError:
        print("Test 3 passed!")
    
    try:
        s.top()
        print("Test 4 failed!")
    except RuntimeError:
        print("Test 4 passed!")
    except:
        print("Test 4 failed!")


if __name__ == '__main__':
    main()
    