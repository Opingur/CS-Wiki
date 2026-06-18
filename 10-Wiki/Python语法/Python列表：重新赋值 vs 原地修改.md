
## 一、问题引入

下面两段代码最终都会输出：

```python
[4, 5, 6]
```

### 方法1：重新赋值

```python
eggs = [1, 2, 3]
eggs = [4, 5, 6]

print(eggs)
```

### 方法2：修改原列表

```python
eggs = [1, 2, 3]

del eggs[2]
del eggs[1]
del eggs[0]

eggs.append(4)
eggs.append(5)
eggs.append(6)

print(eggs)
```

虽然结果相同，但底层机制完全不同。

---

# 二、重新赋值（=）

```python
eggs = [1, 2, 3]
eggs = [4, 5, 6]
```

执行过程：

```text
eggs
  |
  v
[1,2,3]
```

执行第二句后：

```text
eggs
  |
  v
[4,5,6]
```

发生的事情：

- 创建了一个新的列表对象 `[4,5,6]`
- 变量 `eggs` 改为指向新对象
- 原来的 `[1,2,3]` 不再被 `eggs` 引用

本质：

> 改变变量的指向（Reference）

---

# 三、原地修改（In-place Modify）

```python
eggs = [1,2,3]

del eggs[2]
del eggs[1]
del eggs[0]

eggs.append(4)
eggs.append(5)
eggs.append(6)
```

执行过程：

```text
[1,2,3]
 ↓
[1,2]
 ↓
[1]
 ↓
[]
 ↓
[4]
 ↓
[4,5]
 ↓
[4,5,6]
```

注意：

```text
eggs
  |
  v
[4,5,6]
```

始终是同一个列表对象。

发生的事情：

- 没有创建新的列表
- 只是修改了原列表内容
- `eggs` 一直指向同一个对象

本质：

> 改变对象内容，而不是改变变量指向

---

# 四、真正体现区别的例子

## 创建两个引用

```python
eggs = [1,2,3]
spam = eggs
```

此时：

```text
eggs ----+
         |
         v
      [1,2,3]
         ^
         |
spam ----+
```

两个变量指向同一个列表。

---

# 五、情况1：重新赋值

```python
eggs = [4,5,6]
```

变成：

```text
eggs ----> [4,5,6]

spam ----> [1,2,3]
```

测试：

```python
print(spam)
```

输出：

```python
[1,2,3]
```

原因：

`spam` 仍然指向旧列表。

---

# 六、情况2：原地修改

```python
eggs.clear()
eggs.extend([4,5,6])
```

变成：

```text
eggs ----+
         |
         v
      [4,5,6]
         ^
         |
spam ----+
```

测试：

```python
print(spam)
```

输出：

```python
[4,5,6]
```

原因：

两个变量仍然指向同一个对象。

---

# 七、函数传参中的经典坑

## 错误示例

```python
def change(lst):
    lst = [4,5,6]

a = [1,2,3]
change(a)

print(a)
```

输出：

```python
[1,2,3]
```

原因：

```python
lst = [4,5,6]
```

只是让 lst 指向了新对象。

并没有修改 a 指向的列表。

---

## 正确修改原列表

```python
def change(lst):
    lst.clear()
    lst.extend([4,5,6])

a = [1,2,3]
change(a)

print(a)
```

输出：

```python
[4,5,6]
```

原因：

修改的是原对象。

---

# 八、Python 与 C++ 对比

Python：

```python
eggs = [1,2,3]
spam = eggs
```

最接近：

```cpp
vector<int>* eggs = new vector<int>{1,2,3};
vector<int>* spam = eggs;
```

---

重新赋值：

```cpp
eggs = new vector<int>{4,5,6};
```

结果：

```text
spam -> [1,2,3]
eggs -> [4,5,6]
```

---

原地修改：

```cpp
eggs->clear();

eggs->push_back(4);
eggs->push_back(5);
eggs->push_back(6);
```

结果：

```text
spam -> [4,5,6]
eggs -> [4,5,6]
```

---

# 九、常见原地修改函数

这些函数会修改原对象：

```python
append()
extend()
insert()
remove()
pop()
clear()
sort()
reverse()
```

例如：

```python
nums.sort()
nums.reverse()
nums.clear()
```

都会影响所有引用该列表的变量。

---

# 十、总结

| 操作 | 是否创建新对象 | 是否改变变量指向 | 是否影响其他引用 |
|--------|--------|--------|--------|
| `=` | √ | √ | × |
| `append()` | × | × | √ |
| `extend()` | × | × | √ |
| `sort()` | × | × | √ |
| `clear()` | × | × | √ |
| `del lst[i]` | × | × | √ |

---

# 一句话记忆

Python 变量不是装数据的盒子，而是指向对象的标签（引用）。

- `=` ：换一个对象
- `append()`、`sort()`、`del` ：修改当前对象

判断一个操作到底干了什么：

> 它是让变量指向新对象，还是修改原对象？