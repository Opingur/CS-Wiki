# C++ STL：`map` 与哈希表

## 1. 键值对容器

`map` 和哈希表都用于存储“键和值”的对应关系：

```text
key -> value
```

例如：

```text
姓名 -> 成绩
单词 -> 出现次数
学号 -> 学生信息
数字 -> 数字出现的位置
```

C++ STL 中常用的两种键值容器是：

```cpp
map
unordered_map
```

其中：

- `map` 是有序映射，底层通常使用红黑树。
    
- `unordered_map` 是哈希表，底层使用散列表。
    

---

# 2. `map`

## 2.1 基本定义

需要包含头文件：

```cpp
#include <map>
```

定义一个 `map`：

```cpp
map<string, int> scores;
```

其中：

```cpp
string
```

表示键的类型。

```cpp
int
```

表示值的类型。

也就是说：

```text
string -> int
```

例如：

```text
"Alice" -> 95
"Bob"   -> 88
```

---

## 2.2 `map` 的特点

`map` 具有以下特点：

- 键不能重复。
    
- 元素会按照键自动排序。
    
- 查找、插入和删除的时间复杂度通常是 `O(log n)`。
    
- 支持范围查询。
    
- 底层通常使用红黑树。
    

例如：

```cpp
map<int, string> mp;

mp[3] = "three";
mp[1] = "one";
mp[2] = "two";
```

遍历时的顺序为：

```text
1 one
2 two
3 three
```

即使插入顺序是 `3、1、2`，`map` 也会按照键从小到大排列。

---

# 3. `map` 的基本操作

## 3.1 插入元素

### 使用 `[]`

```cpp
map<string, int> scores;

scores["Alice"] = 95;
scores["Bob"] = 88;
scores["Tom"] = 76;
```

如果键已经存在，则修改对应的值：

```cpp
scores["Alice"] = 100;
```

### 使用 `insert`

C++98 可以写：

```cpp
scores.insert(make_pair("Jack", 90));
```

也可以先创建一个 `pair`：

```cpp
pair<string, int> p("Mike", 85);
scores.insert(p);
```

注意，下面这种写法需要 C++11：

```cpp
scores.insert({"Jack", 90});
```

因此严格的 C++98 环境中，应使用：

```cpp
make_pair("Jack", 90)
```

---

## 3.2 访问元素

使用 `[]`：

```cpp
cout << scores["Alice"] << endl;
```

如果 `"Alice"` 存在，就返回对应的值。

如果键不存在：

```cpp
cout << scores["Unknown"] << endl;
```

`map` 会自动创建：

```text
"Unknown" -> 0
```

因为 `int` 的默认值是 `0`。

因此，不能通过 `[]` 单纯判断某个键是否存在。

---

## 3.3 修改元素

```cpp
scores["Alice"] = 100;
```

也可以在原值基础上修改：

```cpp
scores["Alice"] += 5;
```

常见的出现次数统计：

```cpp
map<string, int> count;

count["apple"]++;
count["banana"]++;
count["apple"]++;
```

结果为：

```text
apple  -> 2
banana -> 1
```

---

## 3.4 判断键是否存在

### 使用 `find`

```cpp
if (scores.find("Alice") != scores.end()) {
    cout << "Alice exists" << endl;
}
```

`find` 会返回一个迭代器。

如果找到元素，迭代器指向这个元素。

如果没有找到，则返回：

```cpp
scores.end()
```

### 使用 `count`

```cpp
if (scores.count("Alice") > 0) {
    cout << "Alice exists" << endl;
}
```

因为 `map` 不允许重复键，所以 `count` 的返回值只能是：

```text
0 或 1
```

---

## 3.5 使用迭代器访问查找结果

```cpp
map<string, int>::iterator it;

it = scores.find("Alice");

if (it != scores.end()) {
    cout << it->first << endl;
    cout << it->second << endl;
}
```

其中：

```cpp
it->first
```

表示键。

```cpp
it->second
```

表示值。

也可以修改值：

```cpp
it->second = 100;
```

但是不能修改键：

```cpp
it->first = "Jack";  // 错误
```

因为 `map` 的键决定了元素在红黑树中的排列位置，所以键是只读的。

---

## 3.6 删除元素

根据键删除：

```cpp
scores.erase("Alice");
```

根据迭代器删除：

```cpp
map<string, int>::iterator it;

it = scores.find("Bob");

if (it != scores.end()) {
    scores.erase(it);
}
```

清空所有元素：

```cpp
scores.clear();
```

---

## 3.7 获取元素数量

```cpp
cout << scores.size() << endl;
```

判断是否为空：

```cpp
if (scores.empty()) {
    cout << "map is empty" << endl;
}
```

---

# 4. C++98 遍历 `map`

C++98 不支持以下语法：

```cpp
auto
for (auto x : container)
结构化绑定
```

因此必须完整写出迭代器类型。

## 4.1 普通遍历

```cpp
#include <iostream>
#include <map>
#include <string>
using namespace std;

int main() {
    map<string, int> scores;

    scores["Alice"] = 95;
    scores["Bob"] = 88;
    scores["Tom"] = 76;

    map<string, int>::iterator it;

    for (it = scores.begin(); it != scores.end(); ++it) {
        cout << it->first << " " << it->second << endl;
    }

    return 0;
}
```

通用模板：

```cpp
map<KeyType, ValueType>::iterator it;

for (it = mp.begin(); it != mp.end(); ++it) {
    cout << it->first << " " << it->second << endl;
}
```

---

## 4.2 使用 `const_iterator`

如果遍历过程中只读取元素，不修改值，可以使用：

```cpp
map<string, int>::const_iterator it;

for (it = scores.begin(); it != scores.end(); ++it) {
    cout << it->first << " " << it->second << endl;
}
```

使用 `const_iterator` 时，不能修改值：

```cpp
it->second = 100;  // 错误
```

普通的 `iterator` 可以修改值：

```cpp
map<string, int>::iterator it;

for (it = scores.begin(); it != scores.end(); ++it) {
    it->second += 10;
}
```

---

## 4.3 遍历时删除元素

删除元素后，原来的迭代器会失效。

C++98 中可以这样写：

```cpp
map<int, int>::iterator it = mp.begin();

while (it != mp.end()) {
    if (it->second == 0) {
        map<int, int>::iterator temp = it;
        ++it;
        mp.erase(temp);
    } else {
        ++it;
    }
}
```

不能这样写：

```cpp
mp.erase(it);
++it;
```

因为执行：

```cpp
mp.erase(it);
```

之后，`it` 已经失效。

---

# 5. `map` 的范围查询

因为 `map` 中的键是有序的，所以支持范围查找。

## 5.1 `lower_bound`

```cpp
map<int, string>::iterator it;

it = mp.lower_bound(10);
```

返回第一个键大于或等于 `10` 的元素。

即：

```text
第一个 key >= 10 的元素
```

---

## 5.2 `upper_bound`

```cpp
it = mp.upper_bound(10);
```

返回第一个键严格大于 `10` 的元素。

即：

```text
第一个 key > 10 的元素
```

---

## 5.3 示例

```cpp
#include <iostream>
#include <map>
#include <string>
using namespace std;

int main() {
    map<int, string> mp;

    mp[2] = "two";
    mp[5] = "five";
    mp[8] = "eight";
    mp[12] = "twelve";

    map<int, string>::iterator it;

    it = mp.lower_bound(6);

    if (it != mp.end()) {
        cout << it->first << " " << it->second << endl;
    }

    return 0;
}
```

输出：

```text
8 eight
```

因为 `8` 是第一个大于或等于 `6` 的键。

---

# 6. `unordered_map`：STL 哈希表

## 6.1 基本定义

`unordered_map` 是 C++ STL 中的哈希表。

需要包含：

```cpp
#include <unordered_map>
```

定义：

```cpp
unordered_map<string, int> scores;
```

表示：

```text
string -> int
```

---

## 6.2 `unordered_map` 的特点

- 键不能重复。
    
- 元素没有固定顺序。
    
- 底层使用哈希表。
    
- 平均查找、插入和删除复杂度为 `O(1)`。
    
- 最坏情况下可能退化到 `O(n)`。
    
- 不支持 `lower_bound`、`upper_bound` 等范围查询。
    

注意：

```cpp
unordered_map
```

是 C++11 才正式加入标准库的，严格的 C++98 环境中不能使用。

---

# 7. C++11 中使用 `unordered_map`

## 7.1 插入和修改

```cpp
unordered_map<string, int> scores;

scores["Alice"] = 95;
scores["Bob"] = 88;
scores["Tom"] = 76;
```

修改值：

```cpp
scores["Alice"] = 100;
```

统计次数：

```cpp
unordered_map<string, int> count;

count["apple"]++;
count["banana"]++;
count["apple"]++;
```

---

## 7.2 判断键是否存在

```cpp
if (scores.find("Alice") != scores.end()) {
    cout << "Alice exists" << endl;
}
```

也可以使用：

```cpp
if (scores.count("Alice") > 0) {
    cout << "Alice exists" << endl;
}
```

---

## 7.3 遍历

不用范围 `for` 和 `auto` 时，可以完整写迭代器：

```cpp
unordered_map<string, int>::iterator it;

for (it = scores.begin(); it != scores.end(); ++it) {
    cout << it->first << " " << it->second << endl;
}
```

注意，遍历顺序是不确定的。

---

# 8. C++98 中的哈希表

严格的 C++98 标准库中没有：

```cpp
unordered_map
```

部分编译器提供 TR1 扩展：

```cpp
#include <tr1/unordered_map>
```

容器类型为：

```cpp
std::tr1::unordered_map
```

示例：

```cpp
#include <iostream>
#include <string>
#include <tr1/unordered_map>
using namespace std;

int main() {
    tr1::unordered_map<string, int> scores;

    scores["Alice"] = 95;
    scores["Bob"] = 88;
    scores["Tom"] = 76;

    tr1::unordered_map<string, int>::iterator it;

    for (it = scores.begin(); it != scores.end(); ++it) {
        cout << it->first << " " << it->second << endl;
    }

    return 0;
}
```

但是：

```cpp
tr1::unordered_map
```

不是严格的 C++98 标准内容，不保证所有编译器都支持。

如果学校评测环境只支持标准 C++98，通常应直接使用：

```cpp
map
```

或者自己实现哈希表。

---

# 9. `map` 与 `unordered_map` 的区别

|特点|`map`|`unordered_map`|
|---|---|---|
|底层结构|红黑树|哈希表|
|是否有序|按键排序|无序|
|查找复杂度|`O(log n)`|平均 `O(1)`|
|插入复杂度|`O(log n)`|平均 `O(1)`|
|删除复杂度|`O(log n)`|平均 `O(1)`|
|最坏复杂度|`O(log n)`|`O(n)`|
|键能否重复|不能|不能|
|范围查询|支持|不支持|
|C++98 是否支持|支持|不支持|
|头文件|`<map>`|`<unordered_map>`|

---

# 10. 什么时候使用 `map`

适合使用 `map` 的情况：

- 需要按照键的大小排序。
    
- 需要从小到大输出。
    
- 需要使用 `lower_bound` 或 `upper_bound`。
    
- 需要稳定的 `O(log n)` 时间复杂度。
    
- 编译环境只支持 C++98。
    

例如，统计数字出现次数并按数字从小到大输出：

```cpp
#include <iostream>
#include <map>
using namespace std;

int main() {
    int n;
    cin >> n;

    map<int, int> count;

    for (int i = 0; i < n; ++i) {
        int x;
        cin >> x;
        count[x]++;
    }

    map<int, int>::iterator it;

    for (it = count.begin(); it != count.end(); ++it) {
        cout << it->first << " " << it->second << endl;
    }

    return 0;
}
```

输入：

```text
6
3 1 2 3 2 3
```

输出：

```text
1 1
2 2
3 3
```

---

# 11. 什么时候使用 `unordered_map`

适合使用 `unordered_map` 的情况：

- 只需要快速查找。
    
- 不关心元素顺序。
    
- 需要统计元素出现次数。
    
- 需要判断某个元素是否存在。
    
- 需要保存某个值对应的下标。
    
- 编译环境支持 C++11 或更高版本。
    

例如：

```cpp
unordered_map<int, int> count;

for (int i = 0; i < n; ++i) {
    count[a[i]]++;
}
```

---

# 12. 经典应用：统计出现次数

## 使用 `map`

```cpp
map<int, int> count;

for (int i = 0; i < n; ++i) {
    count[a[i]]++;
}
```

输出时会按照数字从小到大排列：

```cpp
map<int, int>::iterator it;

for (it = count.begin(); it != count.end(); ++it) {
    cout << it->first << " " << it->second << endl;
}
```

---

## 使用 `unordered_map`

```cpp
unordered_map<int, int> count;

for (int i = 0; i < n; ++i) {
    count[a[i]]++;
}
```

平均速度更快，但输出顺序不确定。

---

# 13. 经典应用：两数之和

给定一个数组和目标值，找到两个数，使它们之和等于目标值。

```cpp
#include <iostream>
#include <vector>
#include <unordered_map>
using namespace std;

vector<int> twoSum(const vector<int>& nums, int target) {
    unordered_map<int, int> position;

    for (int i = 0; i < nums.size(); ++i) {
        int need = target - nums[i];

        if (position.find(need) != position.end()) {
            vector<int> answer;
            answer.push_back(position[need]);
            answer.push_back(i);
            return answer;
        }

        position[nums[i]] = i;
    }

    return vector<int>();
}
```

哈希表中保存的是：

```text
数字 -> 下标
```

运行过程：

```text
当前数字为 2
需要数字 7
哈希表中没有 7
记录 2 -> 0

当前数字为 7
需要数字 2
哈希表中存在 2
得到下标 0 和 1
```

---

# 14. `[]` 的重要问题

下面的代码：

```cpp
map<string, int> mp;

cout << mp["apple"] << endl;
```

即使 `"apple"` 原本不存在，也会创建：

```text
"apple" -> 0
```

因此下面这种判断会修改容器：

```cpp
if (mp["apple"] == 0) {
    // 无法区分：
    // 1. apple原本不存在
    // 2. apple存在，但值就是0
}
```

判断键是否存在，应使用：

```cpp
if (mp.find("apple") != mp.end()) {
    cout << "exists" << endl;
}
```

或者：

```cpp
if (mp.count("apple") > 0) {
    cout << "exists" << endl;
}
```

---

# 15. 键和值的类型

常见写法：

```cpp
map<int, int> mp1;
map<string, int> mp2;
map<int, string> mp3;
map<string, vector<int> > mp4;
```

注意 C++98 中嵌套模板的两个右尖括号之间通常需要加空格：

```cpp
map<string, vector<int> > mp;
```

不要写成：

```cpp
map<string, vector<int>> mp;
```

因为旧的 C++98 编译器可能会把 `>>` 当成右移运算符。

---

## 使用 `pair` 作为值

```cpp
map<string, pair<int, int> > position;
```

表示：

```text
名称 -> 坐标
```

例如：

```cpp
position["player"] = make_pair(10, 20);

cout << position["player"].first << endl;
cout << position["player"].second << endl;
```

---

## 使用 `vector` 作为值

```cpp
map<string, vector<int> > scores;

scores["Alice"].push_back(90);
scores["Alice"].push_back(95);
scores["Bob"].push_back(80);
```

表示：

```text
姓名 -> 多个成绩
```

---

# 16. `map` 自定义排序

`map` 默认按照键从小到大排列：

```cpp
map<int, string> mp;
```

如果希望从大到小排列：

```cpp
#include <functional>

map<int, string, greater<int> > mp;
```

完整示例：

```cpp
#include <iostream>
#include <map>
#include <string>
#include <functional>
using namespace std;

int main() {
    map<int, string, greater<int> > mp;

    mp[3] = "three";
    mp[1] = "one";
    mp[2] = "two";

    map<int, string, greater<int> >::iterator it;

    for (it = mp.begin(); it != mp.end(); ++it) {
        cout << it->first << " " << it->second << endl;
    }

    return 0;
}
```

输出：

```text
3 three
2 two
1 one
```

---

# 17. 自定义类型作为键

## 17.1 自定义类型作为 `map` 的键

`map` 需要比较键的大小，因此自定义类型作为键时，需要定义比较规则。

```cpp
struct Student {
    int id;
    string name;

    bool operator<(const Student& other) const {
        return id < other.id;
    }
};
```

然后可以写：

```cpp
map<Student, int> scores;
```

此时 `map` 根据 `Student::id` 排序。

---

## 17.2 自定义类型作为哈希表的键

`unordered_map` 需要：

- 哈希函数。
    
- 相等比较函数。
    

因此自定义类型作为 `unordered_map` 的键更加复杂。

初学阶段，哈希表的键优先使用：

```cpp
int
char
string
long long
```

---

# 18. `map`、`multimap` 与哈希表

|容器|是否有序|键能否重复|
|---|---|---|
|`map`|有序|不能|
|`multimap`|有序|可以|
|`unordered_map`|无序|不能|
|`unordered_multimap`|无序|可以|

例如：

```cpp
multimap<int, string> students;

students.insert(make_pair(90, "Alice"));
students.insert(make_pair(90, "Bob"));
```

相同的键 `90` 可以出现多次。

---

# 19. C++98 常用模板

## 定义

```cpp
map<int, int> mp;
```

## 插入或修改

```cpp
mp[key] = value;
```

## 统计次数

```cpp
mp[key]++;
```

## 判断是否存在

```cpp
if (mp.find(key) != mp.end()) {
    // key存在
}
```

## 查找并访问

```cpp
map<int, int>::iterator it;

it = mp.find(key);

if (it != mp.end()) {
    cout << it->first << " " << it->second << endl;
}
```

## 遍历

```cpp
map<int, int>::iterator it;

for (it = mp.begin(); it != mp.end(); ++it) {
    cout << it->first << " " << it->second << endl;
}
```

## 删除

```cpp
mp.erase(key);
```

## 清空

```cpp
mp.clear();
```

## 元素数量

```cpp
mp.size();
```

## 判断是否为空

```cpp
mp.empty();
```

---

# 20. 选择规则

可以按照下面的规则选择：

```text
需要排序、范围查询、C++98兼容
              ↓
             map
```

```text
只需要快速查找，不关心顺序，支持C++11
              ↓
        unordered_map
```

简单记忆：

```cpp
map
```

特点：

```text
有序，O(log n)
```

```cpp
unordered_map
```

特点：

```text
无序，平均O(1)
```

它们的核心作用都是：

```text
通过key快速找到value
```