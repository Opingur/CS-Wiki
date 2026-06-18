
# C++ 为什么父类析构函数要加 virtual？

## 一、问题背景

假设有如下继承关系：

```cpp
class Vehicle {
public:
    ~Vehicle() {
        cout << "Vehicle destroyed" << endl;
    }
};

class MotorCar : public Vehicle {
public:
    ~MotorCar() {
        cout << "MotorCar destroyed" << endl;
    }
};
```

创建对象：

```cpp
Vehicle* v = new MotorCar();
delete v;
```

你可能认为输出应该是：

```text
MotorCar destroyed
Vehicle destroyed
```

实际上输出却是：

```text
Vehicle destroyed
```

子类析构函数根本没有执行！

---

## 二、为什么会这样？

因为：

```cpp
delete v;
```

中的 `v` 类型是：

```cpp
Vehicle*
```

编译器只知道它是一个 Vehicle 指针。

于是执行：

```cpp
Vehicle::~Vehicle()
```

而不会去检查它实际指向的是：

```cpp
MotorCar
```

因此：

```text
MotorCar::~MotorCar()
```

不会被调用。

---

## 三、会导致什么问题？

如果子类管理资源：

```cpp
class MotorCar : public Vehicle {
private:
    int* data;

public:
    MotorCar() {
        data = new int[100];
    }

    ~MotorCar() {
        delete[] data;
    }
};
```

执行：

```cpp
Vehicle* v = new MotorCar();
delete v;
```

时：

```text
Vehicle::~Vehicle()
```

执行了。

而：

```text
MotorCar::~MotorCar()
```

没有执行。

结果：

```cpp
delete[] data;
```

永远不会发生。

造成：

```text
内存泄漏（Memory Leak）
```

---

## 四、解决方案：虚析构函数

把父类析构函数改成：

```cpp
class Vehicle {
public:
    virtual ~Vehicle() {
        cout << "Vehicle destroyed" << endl;
    }
};
```

再次执行：

```cpp
Vehicle* v = new MotorCar();
delete v;
```

输出：

```text
MotorCar destroyed
Vehicle destroyed
```

这次正确了。

---

## 五、virtual 为什么能解决问题？

当析构函数是 virtual 时：

```cpp
virtual ~Vehicle();
```

析构函数会进入虚函数表（vtable）。

执行：

```cpp
delete v;
```

时：

编译器会：

1. 查看 v 指向的真实对象类型
    
2. 找到对应析构函数
    
3. 先执行子类析构
    
4. 再自动执行父类析构
    

即：

```text
MotorCar::~MotorCar()
↓
Vehicle::~Vehicle()
```

---

## 六、构造与析构顺序

### 构造顺序

```cpp
MotorCar car;
```

执行：

```text
Vehicle()
↓
MotorCar()
```

先父后子。

---

### 析构顺序

对象销毁：

```text
MotorCar::~MotorCar()
↓
Vehicle::~Vehicle()
```

先子后父。

与构造顺序相反。

---

## 七、什么时候必须写 virtual 析构？

只要满足下面两个条件：

### 条件1

类会被当作父类使用。

例如：

```cpp
class Vehicle {};
```

有子类：

```cpp
class MotorCar : public Vehicle {};
```

---

### 条件2

可能出现：

```cpp
Vehicle* p = new MotorCar();
delete p;
```

这种通过父类指针删除子类对象的情况。

---

满足这两个条件：

```cpp
virtual ~Vehicle(){}
```

必须加。

---

## 八、标准写法

实际开发中通常这样写：

```cpp
class Base {
public:
    virtual ~Base() = default;
};
```

或者：

```cpp
class Base {
public:
    virtual ~Base() {}
};
```

---

## 九、考试结论

记住一句话：

> 如果一个类将作为多态基类（父类指针指向子类对象），析构函数必须声明为 virtual。

标准模板：

```cpp
class Base {
public:
    virtual ~Base() {}
};
```

这是最安全、最规范的写法。