# Lambda 表达式（C++11）

## 什么是 Lambda

Lambda 表达式是 C++11 引入的**匿名函数（Anonymous Function）**。

它允许我们在需要函数的地方直接定义函数，而不必专门编写一个有名字的函数。

例如：

```cpp
bool cmp(int a, int b)
{
    return a > b;
}
```

可以写成：

```cpp
[](int a, int b)
{
    return a > b;
}
```

两者功能完全相同。

---

## Lambda 的基本语法

```cpp
[capture](参数列表)
{
    函数体
};
```

示例：

```cpp
[](int a, int b)
{
    return a + b;
}
```

对应普通函数：

```cpp
int add(int a, int b)
{
    return a + b;
}
```

---

# Lambda 在 sort 中的应用

## 普通写法

```cpp
#include <algorithm>
#include <vector>
using namespace std;

bool cmp(int a, int b)
{
    return a > b;
}

int main()
{
    vector<int> nums = {5,1,4,2,3};

    sort(nums.begin(), nums.end(), cmp);
}
```

---

## Lambda 写法

```cpp
#include <algorithm>
#include <vector>
using namespace std;

int main()
{
    vector<int> nums = {5,1,4,2,3};

    sort(nums.begin(), nums.end(),
         [](int a, int b)
         {
             return a > b;
         });
}
```

效果相同，但无需额外编写 cmp 函数。

---

# sort 中的 a 和 b 从哪里来？

例如：

```cpp
sort(nums.begin(), nums.end(),
     [](int a, int b)
     {
         return a > b;
     });
```

这里：

```cpp
int a
int b
```

不是捕获来的变量。

它们是 sort 在排序过程中自动传入的参数。

例如：

```cpp
5 1 4 2 3
```

排序时可能执行：

```cpp
lambda(5,1);
lambda(4,2);
lambda(3,1);
...
```

因此：

```cpp
(int a, int b)
```

属于参数列表。

---

# 捕获列表（capture）

Lambda 最特别的地方在于：

可以使用外部局部变量。

例如：

```cpp
int x = 10;

auto f =
[x](int a)
{
    return a + x;
};
```

这里：

```cpp
x
```

来自外部作用域。

```cpp
a
```

来自函数参数。

---

## 值捕获

```cpp
int x = 10;

auto f =
[x]()
{
    cout << x;
};
```

表示把 x 的值复制到 Lambda 内部。

---

## 引用捕获

```cpp
int x = 10;

auto f =
[&x]()
{
    x++;
};

f();

cout << x;
```

输出：

```cpp
11
```

表示直接操作外部变量。

---

# 常见捕获方式

## 不捕获

```cpp
[]
```

---

## 捕获一个变量

```cpp
[x]
```

---

## 引用捕获一个变量

```cpp
[&x]
```

---

## 所有变量值捕获

```cpp
[=]
```

---

## 所有变量引用捕获

```cpp
[&]
```

---

## 混合捕获

```cpp
[=, &x]
```

表示：

- 默认值捕获
    
- x 使用引用捕获
    

---

# Lambda 与普通函数的区别

普通函数：

```cpp
int k = 100;

bool cmp(int a, int b)
{
    return abs(a-k) < abs(b-k); // 错误
}
```

无法访问 main 函数中的局部变量。

>sort()要求函数只能传两个参数，这是就可以用Lambda表达式


---

Lambda：

```cpp
int k = 100;

sort(nums.begin(), nums.end(),
     [k](int a, int b)
     {
         return abs(a-k) < abs(b-k);
     });
```

可以直接使用 k。

---

# 为什么 Lambda 体现函数式编程思想

因为函数可以像变量一样保存。

例如：

```cpp
auto add =
[](int a, int b)
{
    return a + b;
};

cout << add(3,4);
```

输出：

```cpp
7
```

这里：

```cpp
auto add = ...
```

实际上把一个函数存进了变量。

函数不再只是代码，而是可以像数据一样传递和保存。

这正是函数式编程的重要思想。

---

# Java 对照

Java：

```java
x -> x * x
```

C++：

```cpp
[](int x)
{
    return x * x;
}
```

本质是同一种思想。

---

# 考试重点

### Lambda 是什么

匿名函数。

---

### Lambda 是哪个版本引入的

C++11。

---

### Lambda 基本语法

```cpp
[capture](参数列表)
{
    函数体
}
```

---

### sort 中的 a、b 是什么

参数列表中的参数，由 sort 自动传入。

---

### [] 中放什么

捕获外部变量。

---

### 函数式编程对应什么

Lambda 表达式。

---

# 一句话总结

Lambda = 可以捕获外部变量的匿名函数。

```cpp
[capture](参数列表)
{
    函数体
}
```

以后在 STL（sort、for_each、find_if 等算法）中会大量使用。