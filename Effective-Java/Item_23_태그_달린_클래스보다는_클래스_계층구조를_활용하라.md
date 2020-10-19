## 태그 달린 클래스보다는 클래스 계층구조를 활용하라

2가지 이상의 의미를 표현할 수 있으며, 그 중 현재 표현하는 의미를 태그 값으로 알려주는 클래스를 본 적이 있을 것이다. 다음 코드는 원과 사각형을 표현할 수 있는 클래스다.



```java
class Figure {
    enum Shape { RECTANGLE, CIRCLE };
    
    // 태그 필드 - 현재 모양을 나타낸다.
    final Shape shape;
    
    // 다음 필드들은 모양이 사각형일 때만 쓰인다.
    double length;
    double width;
    
    // 다음 필드는 모양이 원일 때만 쓰인다.
    double radius;
    
    // 원용 생성자
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }
    // 사각형용 생성자
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }
    
    dobuel area() {
        switch(shape) {
            case RECTANGLE:
                return (length * width);
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}
```

태그가 달린 클래스는 단점이 가득하다. 우선 열거 타입 선언, 태그 필드, switch 문 등 쓸데없는 코드가 많다. 여러 구현이 한 클래스에 속해있어서 가독성도 떨어진다. 사용하지 않는 코드도 언제나 함께하니 메모리도 많이 사용한다. 그리고 장황하고 오류를 범하기 쉽다.

다행히도 자바는 이러한 것을 개선할 수 있도록 객체를 표현할 수 있도록 훨씬 나은 수단을 제공한다. 바로 클래스 계층구조를 활용하는 서브타이핑이다.  태그 달린 클래스는 서브타이핑을 어설프게 따라한 아류일 뿐ㅋㅋ

이런 클래스를 분리하는 방법은 Figure라는 이름으로 루트 클래스를 만든다

```java
abstract class Figure {
    abstract double area();
}
// Figure를 구현한 Circle 클래스
class Circle extends Figure {
    final double radius;
    
    Cicle(double radius) {
        this.radius = radius;
    }
    
    @Override
    double area() {
        return Math.PI * (radius * radius);
    }
}

// Figure를 구현한 Rectangle 클래스
class Rectangle extends Figure {
    final double length;
    final double width;
    
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
        @Override
    double area() {
		return (length * width);
    }
}
```

위의 클래스 계층구조는 처음하였던 클래스의 단점을 모두 날려버린다. 간결하고 명확하며, 쓸데없는 코드도 모두 사려젔다. 실수로 빼먹은 case문 떄문에 런타임 오류가 날 이유도 없어졌다 !! 또한 타입사이의 자연스러운 계층 관계를 반영할 수 있어서 유연성은 물론 컴파일타임 타입 검사 능력을 높여준다는 장점이 있다.



#### 핵심 정리

>태그 달린 클래스를 써야 하는 상황은 거의 없다. 새로운 클래스를 작성하는 데 태그 필드가 등장한다면 태그를 없애고, 계층구조로 대체하는 방식으로 진행하자. 기존 클래스가 태그 필드가 사용하고 있다면 계층구조로 리팩터링하는 것을 고민하자.