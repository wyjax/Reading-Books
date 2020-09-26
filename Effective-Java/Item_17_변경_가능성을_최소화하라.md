## item 17. 변경 가능성을 최소화하라.	



불변 클래스란 간단히 그 인스턴스의 내부 값을 수정할 수 없는 클래스를 일컫는다. 인스턴스 내부 값은 객체가 파괴되는 순간까지 절대 달라지지 않는다. 자바에도 불변 클래스가 존재한다. String, 기본 타입의 박싱된 클래스들 BigInteger, BigDecimal이 있다. 불변 클래스는 가변 클래스보다 사용하기 쉽고 설계하기 쉽다.



##### 불변 클래스 만드는 규칙

- 객체의 상태를 변경하는 메서드를 제공하지 않는다.
- 클래스를 확장할 수 없도록 한다. >> final class xxx 로 선언하는 방법이 있다.
- 모든 필드를 final로 선언한다. 
- 모든 필드를 private으로 선언한다. 클라이언트에서 직접적으로 수정되는 것을 막아준다.
- 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없게 한다.



```java
// 코드 17-1 불변 복소수 클래스 (106-107쪽)
public final class Complex {
    private final double re;
    private final double im;

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE  = new Complex(1, 0);
    public static final Complex I    = new Complex(0, 1);

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double realPart()      { return re; }
    public double imaginaryPart() { return im; }

    public Complex plus(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    // 코드 17-2 정적 팩터리(private 생성자와 함께 사용해야 한다.) (110-111쪽)
    public static Complex valueOf(double re, double im) {
        return new Complex(re, im);
    }

    public Complex minus(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    public Complex times(Complex c) {
        return new Complex(re * c.re - im * c.im,
                re * c.im + im * c.re);
    }

    public Complex dividedBy(Complex c) {
        double tmp = c.re * c.re + c.im * c.im;
        return new Complex((re * c.re + im * c.im) / tmp,
                (im * c.re - re * c.im) / tmp);
    }

    @Override public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Complex))
            return false;
        Complex c = (Complex) o;

        // == 대신 compare를 사용하는 이유는 63쪽을 확인하라.
        return Double.compare(c.re, re) == 0
                && Double.compare(c.im, im) == 0;
    }
    @Override public int hashCode() {
        return 31 * Double.hashCode(re) + Double.hashCode(im);
    }

    @Override public String toString() {
        return "(" + re + " + " + im + "i)";
    }
}

```



불변 객체는 단순하다. 불변 객체는 생성된 시점의 상태를 파괴될 때까지 그대로 간직한다. 모든 생성자가 클래스 불변식을 보장한다면 그 클래스를 사용하는 프로그래머가 노력을 들이지 않아도 영원히 불변으로 남는다.

반면에 가변 객체는 복잡한 상태에 놓일 수 있고, 스레드에 대해서 안정한 불변 객체에 반해서 가변 객체는 스레드에 의해서 상태값이 변경될 수 가 있다. 그렇기 때문에 불변 객체를 만들고 계속해서 재활용하는 것이 좋다.

불변 클래스는 clone 메서드나 복사 생성자를 제공하지 않는게 좋다. 복사 생성자는 이 사실을 잘 이해하지 못한 자바 초창기 때 만들어진 것으로, 되도록 사용하지 말아야 한다.

불변 객체는 그 자체로 실패 원자성을 제공한다. 상태가 절대 변하지 않으니 잠깐이라도 불일치 상태에 절대 빠지지 않는다는 것이다.



##### 불변 클래스 만드는 법

클래스가 불변임을 보장하려면 자신을 상속하지 못하게 해야 한다. 자신을 상속하게 하지 못하는 가장 간단한 방법은 final을 선언하는 것이지만, 이것보다 유연한 방법이 있다. 모든 생성자를 private 혹은 package-private으로 만들고 public 정적 팩터리 메서드를 제공하는 것이다. 



```java
public class Complex {
    private final double re;
    private final double im;
    
    private Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }
    
    private static Complex valueOf(double re, double im) {
        return new Complex(re, im);
    }
}
// 이 방식이 최선일 때가 많다. 바깥에서 볼수 없는 package-private으로 구현 클래스를 원하는 만큼
// 활용할 수 잇으니 훨씬 유연하다.
```

위와 같은 클래스는 밖에서 바라본다면 사실상 final이다. public이나 protected가 없으니 다른 패키지에서는 이 클래스를 확장하는게 불가능하다. 정적 메서드 방식은 다수의 구현 클래스를 활용한 유연성을 제공하고, 다음 릴리즈에서 객체 캐싱 기능을 추가하여 성능을 끌어올릴 수 있다.



불변 클래스의 규칙 목록에 따르면 모든 필드가 final이고 어떤 메서드도 그 객체를 수정할 수 없어야 한다. 사실 이 규칙은 좀 과한 감이 있고, 성능을 위해서 다음처럼 완화할 수 있다. "어떤 메서드도 객체의 상태 중 외부에 비치는 값을 변경할 수 없다."

모든 클래스는 불변으로 만들 수 없다. 그렇기 때문에 불변으로 만들 수 없는 캘래스라도 변경할 수 있는 부분은 최소한으로 줄이자. 객체가 가질 수 있는 상태의 수를 줄이면 그 객체를 예측하기 쉬워지고 오류가 생길 가능성이 줄어든다. 그러니 꼭 변경해야 하는 필드를 뺀 나머지는 모두 final로 선언하자. 

#### 다른 이유가 없다면 모든 필드는 private final로 선언한다.

