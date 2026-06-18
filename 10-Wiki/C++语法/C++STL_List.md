# C++ STL：`list` 容器

## 1. `list` 是什么

`std::list` 是 C++ STL 中的**双向链表容器**。

每个节点除了保存数据，还保存前一个节点和后一个节点的位置：

```text
[1] <-> [2] <-> [3] <-> [4]
```

使用时需要包含头文件：

```cpp
#include <list>
using namespace std;
```

---

## 2. `list` 和 `vector` 的区别

|特点|`vector`|`list`|
|---|---|---|
|底层结构|动态数组|双向链表|
|内存是否连续|连续|不连续|
|是否支持下标|支持 `v[i]`|不支持|
|随机访问|`O(1)`|不支持|
|已知位置插入|通常 `O(n)`|`O(1)`|
|已知位置删除|通常 `O(n)`|`O(1)`|
|查找元素|`O(n)`|`O(n)`|

`vector` 更适合：

- 按下标访问
    
- 排序
    
- 二分查找
    
- 主要在尾部插入
    

`list` 更适合：

- 经常在中间插入
    
- 经常删除中间元素
    
- 一边遍历一边删除
    
- 不需要下标访问
    

> `list` 在已知迭代器位置插入和删除是 `O(1)`，但寻找这个位置仍然是 `O(n)`。

---

## 3. 创建 `list`

```cpp
list<int> a;           // 空链表
list<int> b(5);        // 5 个元素，默认值为 0
list<int> c(5, 10);    // 5 个 10
```

`list` 不支持下标：

```cpp
a[0];   // 错误
```

因为链表节点不连续存放。

---

## 4. 头尾插入

```cpp
a.push_back(10);    // 尾部插入
a.push_front(5);    // 头部插入
```

示例：

```cpp
list<int> a;

a.push_back(2);
a.push_back(3);
a.push_front(1);
```

结果：

```text
1 2 3
```

---

## 5. 头尾删除

```cpp
a.pop_front();    // 删除第一个元素
a.pop_back();     // 删除最后一个元素
```

使用前应该先判断是否为空：

```cpp
if (!a.empty()) {
    a.pop_front();
}
```

---

## 6. 获取头尾元素

```cpp
cout << a.front() << endl;   // 第一个元素
cout << a.back() << endl;    // 最后一个元素
```

空链表不能调用 `front()` 和 `back()`。

---

## 7. 使用迭代器遍历

`list` 一般使用迭代器遍历：

```cpp
list<int>::iterator it;

for (it = a.begin(); it != a.end(); ++it) {
    cout << *it << " ";
}
```

相关含义：

```cpp
a.begin()
```

指向第一个元素。

```cpp
a.end()
```

指向最后一个元素后面的位置。

注意：

```cpp
*a.end();   // 错误
```

`end()` 不指向真实元素。

获取当前元素：

```cpp
*it
```

迭代器移动：

```cpp
++it;   // 向后移动
--it;   // 向前移动
```

但不能：

```cpp
it + 3;   // 错误
```

因为 `list` 不支持随机访问。

---

## 8. 在指定位置插入

基本形式：

```cpp
a.insert(it, value);
```

作用是：

> 在 `it` 指向的位置之前插入 `value`。

示例：

```cpp
list<int> a;

a.push_back(1);
a.push_back(3);

list<int>::iterator it = a.begin();
++it;               // 指向 3

a.insert(it, 2);    // 在 3 前插入 2
```

结果：

```text
1 2 3
```

---

## 9. 在某个元素后插入

因为 `insert()` 是插入到迭代器前面，所以要先让迭代器向后移动。

例如，在第一个 `i` 后插入 `j`：

```cpp
for (list<int>::iterator it = a.begin(); it != a.end(); ++it) {
    if (*it == i) {
        ++it;
        a.insert(it, j);
        break;
    }
}
```

如果 `i` 是最后一个元素：

```cpp
++it;
```

后会得到 `a.end()`。

此时：

```cpp
a.insert(a.end(), j);
```

相当于在末尾插入。

---

## 10. 删除指定位置元素

基本形式：

```cpp
it = a.erase(it);
```

`erase(it)` 会：

1. 删除当前元素
    
2. 返回下一个有效元素的迭代器
    

示例：

```cpp
list<int>::iterator it = a.begin();

while (it != a.end()) {
    if (*it <= 3) {
        it = a.erase(it);
    } else {
        ++it;
    }
}
```

错误写法：

```cpp
a.erase(it);
++it;
```

因为删除后，原来的 `it` 已经失效。

正确写法：

```cpp
it = a.erase(it);
```

这是 `list` 最重要的删除模板：

```cpp
list<int>::iterator it = a.begin();

while (it != a.end()) {
    if (需要删除当前元素) {
        it = a.erase(it);
    } else {
        ++it;
    }
}
```

---

## 11. 删除所有指定值

```cpp
a.remove(value);
```

它会删除所有等于 `value` 的元素。

示例：

```cpp
list<int> a;

a.push_back(1);
a.push_back(2);
a.push_back(1);
a.push_back(3);

a.remove(1);
```

结果：

```text
2 3
```

注意：

```cpp
a.remove(1);
```

不是删除第一个 `1`，而是删除所有 `1`。

---

## 12. 查找元素

`list` 本身没有普通的 `find()` 成员函数，需要使用 `<algorithm>`：

```cpp
#include <algorithm>
```

示例：

```cpp
list<int>::iterator it = find(a.begin(), a.end(), 5);

if (it != a.end()) {
    cout << "找到了" << endl;
} else {
    cout << "没有找到" << endl;
}
```

`find()` 找到时返回第一个匹配元素的迭代器。

找不到时返回：

```cpp
a.end()
```

---

## 13. 大小和判空

获取元素数量：

```cpp
a.size();
```

判断是否为空：

```cpp
a.empty();
```

示例：

```cpp
if (a.empty()) {
    cout << "链表为空" << endl;
}
```

---

## 14. 清空

```cpp
a.clear();
```

删除所有元素。

```cpp
a.clear();

cout << a.size() << endl;   // 0
```

---

## 15. 排序

`list` 有自己的排序成员函数：

```cpp
a.sort();
```

默认升序。

示例：

```cpp
list<int> a;

a.push_back(3);
a.push_back(1);
a.push_back(2);

a.sort();
```

结果：

```text
1 2 3
```

降序：

```cpp
#include <functional>

a.sort(greater<int>());
```

注意，不能使用普通 `sort`：

```cpp
sort(a.begin(), a.end());   // 错误
```

因为普通 `sort()` 要求随机访问迭代器，而 `list` 不支持。

正确写法：

```cpp
a.sort();
```

---

## 16. 反转

```cpp
a.reverse();
```

例如：

```text
1 2 3
```

执行后：

```text
3 2 1
```

---

## 17. 删除连续重复元素

```cpp
a.unique();
```

它只能删除**相邻重复元素**。

例如：

```text
1 1 2 2 2 3 1
```

执行：

```cpp
a.unique();
```

结果：

```text
1 2 3 1
```

最后一个 `1` 不会删除，因为它和前面的 `1` 不相邻。

如果想去除所有重复值，可以先排序：

```cpp
a.sort();
a.unique();
```

---

## 18. 交换两个链表

```cpp
a.swap(b);
```

也可以：

```cpp
swap(a, b);
```

---

## 19. 常用操作总结

|操作|写法|
|---|---|
|头部插入|`push_front(x)`|
|尾部插入|`push_back(x)`|
|删除头部|`pop_front()`|
|删除尾部|`pop_back()`|
|获取头元素|`front()`|
|获取尾元素|`back()`|
|指定位置前插入|`insert(it, x)`|
|删除指定位置|`it = erase(it)`|
|删除所有指定值|`remove(x)`|
|获取元素数量|`size()`|
|判断为空|`empty()`|
|清空|`clear()`|
|查找|`find(begin(), end(), x)`|
|排序|`sort()`|
|反转|`reverse()`|
|删除连续重复值|`unique()`|
|获取首迭代器|`begin()`|
|获取尾后迭代器|`end()`|

---

## 20. 完整示例

```cpp
#include <iostream>
#include <list>
#include <algorithm>
using namespace std;

int main() {
    list<int> nums;

    nums.push_back(1);
    nums.push_back(2);
    nums.push_back(3);
    nums.push_front(0);

    // 在第一个 2 后插入 10
    list<int>::iterator it;

    for (it = nums.begin(); it != nums.end(); ++it) {
        if (*it == 2) {
            ++it;
            nums.insert(it, 10);
            break;
        }
    }

    // 删除所有小于等于 1 的元素
    it = nums.begin();

    while (it != nums.end()) {
        if (*it <= 1) {
            it = nums.erase(it);
        } else {
            ++it;
        }
    }

    // 输出
    for (it = nums.begin(); it != nums.end(); ++it) {
        cout << *it << " ";
    }

    return 0;
}
```

输出：

```text
2 10 3
```

---

## 21. 为什么整理唱片题适合使用 `list`

题目中的主要操作有：

1. 找到第一个值为 `i` 的元素，在它后面插入 `j`
    
2. 删除所有小于等于 `i` 的元素
    
3. 删除所有值为 `i` 的元素，再插入一个新的 `i`
    

这些操作都涉及：

- 顺序查找
    
- 中间插入
    
- 遍历删除
    
- 批量删除
    

因此 `list` 比较适合。

---

## 22. 操作一：在第一个 `i` 后插入 `j`

```cpp
for (list<int>::iterator it = records.begin();
     it != records.end(); ++it) {
    if (*it == i) {
        ++it;
        records.insert(it, j);
        break;
    }
}
```

如果不存在 `i`，循环结束后什么都不做即可。

---

## 23. 操作二：删除所有小于等于 `i` 的元素

```cpp
list<int>::iterator it = records.begin();

while (it != records.end()) {
    if (*it <= i) {
        it = records.erase(it);
    } else {
        ++it;
    }
}
```

---

## 24. 操作三的注意事项

操作三要求：

> 删除所有值为 `i` 的唱片，再把一个新的 `i` 放到第一个 `j` 后。

这里不能直接先删除。

因为如果不存在 `j`，整个操作属于非法操作，应该忽略。

正确思路：

1. 先确认是否存在 `j`
    
2. 如果不存在，什么都不做
    
3. 如果存在，再删除所有 `i`
    
4. 最后插入新的 `i`
    

还要特别注意：

```cpp
i == j
```

如果保存了一个指向 `j` 的迭代器，然后执行：

```cpp
records.remove(i);
```

当 `i == j` 时，保存的迭代器会失效。

因此操作三不能简单保存旧迭代器后直接删除。

---

## 25. 必须记住的三个重点

### `list` 不支持下标

```cpp
a[0];   // 错误
```

必须使用迭代器。

### `insert(it, x)` 插入在 `it` 前面

想插入到当前元素后面：

```cpp
++it;
a.insert(it, x);
```

### 删除时必须接收 `erase()` 返回值

```cpp
it = a.erase(it);
```

不能删除后继续使用原来的迭代器。

---

## 26. 最常用模板

### 普通遍历

```cpp
for (list<int>::iterator it = a.begin(); it != a.end(); ++it) {
    cout << *it << " ";
}
```

### 查找第一个指定元素

```cpp
list<int>::iterator it = find(a.begin(), a.end(), value);

if (it != a.end()) {
    // 找到
}
```

### 遍历删除

```cpp
list<int>::iterator it = a.begin();

while (it != a.end()) {
    if (需要删除当前元素) {
        it = a.erase(it);
    } else {
        ++it;
    }
}
```

### 在第一个指定元素后插入

```cpp
for (list<int>::iterator it = a.begin(); it != a.end(); ++it) {
    if (*it == target) {
        ++it;
        a.insert(it, value);
        break;
    }
}
```

---

## 27. 总结

`list` 的核心特点是：

> 不擅长按位置访问，但擅长在已知位置插入和删除。

当前最需要掌握的是：

```cpp
begin()
end()
insert()
erase()
remove()
```

以及最关键的删除写法：

```cpp
it = a.erase(it);
```

掌握这些后，已经足够完成大多数基础 `list` 题目。