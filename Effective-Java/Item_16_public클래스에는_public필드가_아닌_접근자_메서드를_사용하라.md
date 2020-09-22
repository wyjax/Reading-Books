## Item 16. public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라



이따금 인스턴스 필드들을 모아놓는 일 외에는 아무 목적도 없는 퇴보한 클래스를 작성하려 할 때가 있다.

```java
class Point {
    public double x;
    public double y;
}
```

위와 같은 클래스에는 데이터 필드에 직접 접근할 수 있으니 캘슐화의 이점을 제공하지 못한다. API를 수정하지 않고는 내부 표현식을 보장할 수 없으며, 외부에서 필드에 접근할 때 부수 작업을 수행할 수도 없다. 그렇기 때문에 철저한 객체지향 프로그래머는 필드를 private으로 선언하고 이러한 필드에 접근할 수 있는 메서드를 제공한다.



```java
class Point {
    private double x;
    private double y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
}
```

이렇게 하면 내부 표현 방식을 언제든 바꿀수 있는 유연성을 얻을 수 있다. public 클래스가 필드를 공개하면 이를 사용하는 클라이언트가 생겨날 것이므로 내부 표현 방식을 마음대로 바꿀 수 없게 된다. 

하지만 package-private 클래스 혹은 private 중첩 클래스라면 데이터 필드를 노출한다 해도 하등의 문제가 없다. 그 클래스가 표현하고자 하는 추상화 개념만 올바르게 표현해주면 된다.

이를 무시한 자바 패키지가 존재한다. java.awt.package의 Point와 Dimension 클래스다. 이러한 것들은 흉내내지 말고 타산지석으로 삼아라. 이것은 오늘까지도 심각한 성능 문제를 야기하고 있다.



#### 핵심 정리

###### public 클래스는 절대 가변 필드를 직접 노출해서는 안 된다. 불변 필드라면 노출해도 덜 위험하지만 완전히 안심할 수는 없다. 하지만 package-private 클래스나 private 중첩 클래스에서는 종종 (불변이든 가변이든) 필드를 노출하는 편이 나을 때도 있다.