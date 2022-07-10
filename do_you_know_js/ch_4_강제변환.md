Chapter 4 강제변환



어떤 값을 다른 타입의 값으로 바꾸는 과정이 명시적 > 타입 캐스팅, 암묵적이면 강제변환

명확한 차이? 명시적 강제변환은 코드만 봐도 의도적으로 타입변환을 일으킴, 암시적은 다른 작업 도중 불분명한 부수효과(사이드 이펙트)로 발생S$

```javascript
var a = 42;
var b = a + ""; // 암묵적 강제변환 공백과 '+' 연산은 문자열 접합처리를 한다
var c = String(a); // 명시적 강제변환, 위와 대조적으로 string으로 명시적
```

명시적 : 암시적 = 명백한 : 사이드이펙트 >> 이러한 대응관계가 만들어짐Í

" " + 42 이런식으로 '의도적'으로 했다면 암묵적이 아니고 명시적이지 않냐 > 다른 사람 생각은 아닐 수도 있다



##### 4.2 추상 연산 (ES5에 대한 내용)

tostring, tonumber, toboolean

문자열이 아닌 값 -> 문자열로 변환 작업은 tostring 추상 연산이 담당함

> 내장 원시 값은 본연의 문자열화 방법이 있음
>
> null > "null"
>
> undefined > "undefined"
>
> true > "true"
>
> 문자열 > 문자열
>
> 숫자 > 너무 작거나 큰 값은 지수형태로 ex) 1.07e21
>
> 일반 객체는 지정하지 않으면 toString() 메서드가 내부 [[Class]]로 변환 (예: [object Object])
>
> 배열 var a = [1,2,3]; a.toString(); "1,2,3"



JSON 문자열화S

tostirng은 JSON.stringify() 유틸리티를 사용하여 어떤 값을 JSON 문자열로 직렬화하는 문제와도 연관된다.

```javascript
JSON.stringfy(42); // "42"
JSON.stringfy("42"); // ""42""
JSON.stringfy(null); // "null"
JSON.stringfy(true); // "true"
```

JSON 안전값이 아닌 것들 (undefined, function, symbol, 환형 참조(객체 참조가 순환하여 참조하는 형태))



JSON.stringify() 는 인에 안전 값이 아닌 것들이 있으면 자동으로 누락시킨다. 만약 배열에 있으면 null로 인식한다. 객체 프로퍼티에 있으면 지워버림

```javascript
JSON.stringify(undefined); // undefined
JSON.stringify(function(){}); // undefined

var a = [1, undefined, function(){}, 4];
JSON.stringify(a) // [1, null, null, 4]로 된당

var b = {
  a: 2,
  b: function(){}
}
JSON.stringify(b);
// 환형 참조 객체를 넘기면 에러가 남
```

객체 자체에 toJSON() 메서드가 정의되어 있으면 먼저 이 메서드를 호출하여 직렬화한 값을 반환함, 부적절한 json 값이나 직렬화하기 곤란한 값이면 따로 toJSON()을 정의해야 한다.

toJSON()은 적절히 평범한 실제 값을 반환하고 문자열화 처리는 json.stringify가 담당한다. toJSON의 역할은 적당한 json 안전값으로 바꾸는 것이지 json 문자열로 바꾸는 것이 아니다.

JSON.stringify() 의 잘 알려지지 않은 기능

```javascript
var a = {
  b: 42,
  c: "42",
  d: [1,2,3]
}
JSON.stringify(a, ["b", "c"]); // "{"b":42, "c":"42"}"
JSON.stringify(a, function(key, value) {
  if (key !== "c") {
    return value;
  }
}) // "{"b":42, "d":[1,2,3]}"
```

JSON.stringify() 세 번째 option 인자는 스페이스라고 하며 읽기 쉽도록 해주는 인자다. 숫자 or 문자를 넣을 수 있다



#### ToNumber

숫자 아닌 값 -> 수식 연산이 가능한 숫자

true == 1, faluse == 0, undefined는 NaN, null은 0, 객체는 NaN

tonumber 변환이 실패하면 nan으로 표시된다. 0이 앞에 붙은 8진수는 tonumber에서 올바른 숫자 리터럴이라도 8진수로 처리하지 않는 (0144) > 100이지만 10진수로 처리



동등한 원시 값으로 바꾸기 위해 ToPrimitive 추상 연산 과정에서 valueof 메서드를 구현했는지 보고 없으면 tostring을 이용하여 강제변환함, 변환이 안되는 경우에는 TypeError 오류

ES5Dㅔ서 Prototype이 null인 경우 대부분 Object.create(null) 사용하여 강제변환이 불가능한 객체를 생성할 수 [ES5] 불변성 - 자바스크립트고수

https://skytechnology.tistory.com › ... 있음

```javascript
var a = {
  valueOf: function() {
    return "42";
  }
}

var b = {
  toString: function() {
    return "42";
  }
}

var c = [4, 2];
c.toString = function() {
  return this.join(""); // "42"
}

Number(a); // 42
Number(b); // 42
Number(c); // 42
Number("");// 0
Number([]);// 0
Number(["abc"]);// NaN
```



#### Boolean()

boolean은 많은 혼동을 일으키는 주제

js에서는 숫자는 숫자고, boolean은 boolean이다



자바스크립트의 모든 값

1. 불리언으로 강제변환하면 false가 되는 값
2. 1번을 제외한 나머지, 명백한 true



boolean 강제변환 시 false가 되는 몇 개 안되는 특별한 값

>undefined
>
>null
>
>false
>
>+0, -0, NaN
>
>""

위의 목록에 있으면 falsy한 값, boolean()을 통해 강제변환하면 false임

```java
var a = new Boolean(false); // true
var b = new Number(0); // true
var c = new String(""); // true
var d = new Boolean(null); // true
var e = new Boolean(undefined); // true
// a, b, c는 명백한 falsy 값을 감싼 객체이다

var f = Boolean(a && b && c)
Boolean(a&&b&&c) // true
```

document.all 은 웹 페이지의 요소를 자바스크립트에서 가져올 수 있게 해주고 truthy한 객체처럼 작동함

> 어떤게 falthy 한 지만 알면 truthy



```javascript
var a = "false";
var b = "0";
var c = "''";
var d= Boolean(a && b && c);

d; // true
```





#### 4.3 명시적 강제변환

분명하고 확실한 타입변환, 타입변환 과정을 명확하게 하여 의도를 보여준다.



##### 4.3.1 문자열 <---> 숫자

String(), Number() 함수를 사용 new를 사용하지 않기 때문에 객체 래퍼를 생성하는게 아님

```javascript
var a = 42;
var b = String(a); // "42"
var c = "3.14"; // 
var d = Number(c); // 3.14
```

toString 추상 연산 로직에 따라서 String()은 값을 받아서 원시 문자열로 강제변환함, ToNumber도 마찬가지

```javascript
var a = 42;
var b = a.toString(); 
// 겉보기엔 명시적이지만, 42에는 toString() 메서드가 없어서 엔진에서 42를 객체 래퍼로 박싱한다'

var c = "3.14"
var d = +c; // +는 숫자로 강제변환하는 기능을 가진다

b; // "42"
d; // 3.14

1 + - + + + - + 1; // 2가 나온다. 근데 이렇게 쓸까? 1 + - - 1 이랑 같다.
```

##### 날짜 ----> 숫자

\+ 연산자는 date 객체 -> 숫자 강제변환 용도로도 사용 결과값이 1970 1월1일 00:00:00이후의 초단위 이기 때문

```javascript
var d = new Date("Mon, 18 Aug 2022 14:00:00 UTC")
+d; // 1660831200000
var timestamp = Date.now(); 위와 동일
```

###### 날짜 타입에 관해서는 강제변환을 권하지 않음. Date.now()나 new Date().getTime()을 쓰자



##### 틸드? (~)

강제변환 연산자, 엄밀히는 강제변환은 아니지만 숫자에 |나 ~ 비트 연산자를 적용하면 전혀 다른 숫자 값을 생성한다

~ 연산자는 먼저 32비트로 강제변환하고 NOT 연산을 한다.



##### 4.3.2 명시적 강제변환: 숫자 형태의 문자열 파싱

앞서 배운 문자열 -> 숫자 강제변환과 결과는 비슷

```javascript
var a = "42";
var b = "42px";
Number(a); // 42
parseInt(a); // 42

Number(b); // NaN (not a number)
parseInt(b); // 42
```

문자열로부터 숫자 값의 파싱은 비 숫자형 문자를 허용 숫자같지 않으면 멈추고, 강제변환은 비 숫자형 문자를 허용안함

parsing은 강제변환의 대안이 안됨

parseInt()는 문자열에 쓰는 함수임, 애당초에 숫자라면 쓸 필요가 없다는데 본인 생각도 그러함 이런거 왜 씀;

```javascript
const hour = parseInt(selectedHour.value); // 시간 "08"
var minute = parseInt(selectedMinute.value); // 분은 "09"라고 가정
```

ES5 이전에는 0으로 시작하면 8진수, x로 시작하면 16진수로 해석했음, ES5부터는 0x(16진수)로 시작할 때만 제외하고 10진수로 인식 

> ES5에서 "08"을 10진수로 인식하려면? 2번째 파라메터로 10을 넣어줘야함



나머지 내용은 parseInt()가 문자열을 숫자로 변환하는 함수라는 거를 생각하면 상관없는 내용인 듯 하다

```javascript
parseInt(new String("42")); // 객체 래퍼
```

3장에서 객체 래퍼로 직접 박싱하는 건 권장하지 않는다고 했다. 내 생각 역시 그러함.. "42"라고 쓸 걸 new String("42")으로 쓰는 걸 본 적이 없어서 그런거 같다



##### 4.3.3 명시적 강제변환: * -> 불리언

Boolean()은 ToBoolean() 추상 연산에 의한 강제변환 방법

```javascript
// 주석은 Boolean() 한 값
var a = "0"; // T
var b = []; // T
var c = {}; // T

var d = ""; // F
var e = 0; // F
var f = null; // F
var g; // F
```

\+단항 연산자가 값을 숫자로 강제변환 하는 것 처럼 ! (부정 연산자)도 값을 불리언으로 명시적으로 강제변환함
일반적으로는 boolean으로 강제변환 할 때 !! 으로 쓴다고함



```javascript
var a = "0"; // !!a > true
var b = []; // !!b > true
var c = {}; // !!c > true

var d = ""; // !!d > false
var e = 0; // !!e > false
var f = null; // !!f > false
var g; // !!f > false
```

!!나 Boolean()을 사용하지 않으면 if 문 등에서 암시적 변환이 일어난다.

```javascript
var a = [1, function(){}, 2, function(){}];
JSON.stringify(a); // "[1, null, 2, null]"
JSON.stringify(a, function(k, v) {
  if (typeof v == "function") {
    return !!v;
  }
  return v;
}); // '[1,true,2,true]'

var c = 42;
var d = a ? true : false; // a는 암시적 강제변환이 된다. 작가는 이렇게 무조건 쓰지 말라고함 사용하려면?
var e = Boolean(a) ? true : false; // 이렇게 사용하라고함 아니면 !!
```



#### 4.4 암시적 변환

부수 효과가 명확하지 않게 숨겨진 형태로 일어나는 타입변환 (분명하지 않은 타입변환), 간단히 명시적 반대

```javascript
SomeType x = SomeType(AnotherType(y));
// y는 AnotherType()를 통해서 SomeType으로 변환됨
SomeType x = SomeType(y);
```

자바스크립트의 암시적 강제변환은 우리에게 도움이 될 수도 있다는 것, 암시적 강제변환을 바라보고 수용해라

암시적 강제변환의 목적 

- 장황함
- 보일러플레이 > 최소한의 변경으로 여러 곳에서 재사용되는 것

암시적 변환의 해로운 부분을 알면 잘 사용할 수 있다 (내생각 : 다같이 해로운 부분을 알아야 하는데..)





##### 4.4.2 암시적 강제변환: 문자열 <-> 숫자

\+ 연산자의 오버로드 목적

- 숫자의 덧셈
- 문자열 접합

```javascript
var a = "42";
var b = "0";

var c = 42;
var d = 0;

a + b; // "420"
c + d; // 42
```

한 쪽이 문자열이면 + 연산은 문자열로 계산된다

```javascript
var a = [1, 2];
var b = [3, 4];
a + b; // "1,23, 4" > 이 결과는 a.toString() + b.toString() 결과로 된다

var c = [1, 2];
var d = {};
c + d; // '1,2[object Object]'
```

한 쪽 피연산자가 문자열이거나 다음 과정을 통해 문자열로 된다.

>1. 피연산자 중 하나가 객체 > toPrimitive 연산 수행 > number context에 힌트 남김 DefaultValue
>
>배열은 valueOf() 연산을 통해서 단순한 원시 값을 넘길 수 없다... 그래서 toString() 연산 수행

피연산자 중 하나가 객체(배열 포함)라면 먼저 이 값에 ToPrimitive 추상 연산을 수행하고, 다시 ToPrimitive는 number context 힌트를 넘겨 [[DefaultValue]] 알고리즘을 호출함



##### 숫자 문자열의 암시적 변환

```javascript
var a = 42;
var b = a + "";
b; // "42"
```

대표적인 암시적 코드 a + b

a + b가 암시적 변환으로 되면 ToPrimitive 연산 과정에서 a값은 valueOf() 연산ㅇㄹ 호출하고 그 겨로가를 ToString() 추상연산하여 문자열로 됨

하지만, toString()을 하면 toStrnig()만 단순 호출함 // 여기 연산의 차이 시간복잡도 찍어보고 싶다

더하는 피연산자가 객체라면 갑이 달라질 수 있다.

```javascript
var a= {
  valueOf: function() { return 42; },
  toString: function() { return 4 }
}

// toString : String(a) >> '4'
// a + "good" >> "42good"
```



##### 문자 > 숫자 암시적 변환

```javascript
var a = "3.14";
var b = a - 0;
// - 연산운 숫자 뺄셈 기능 밖에 없다 그레서 a는 숫자로 변환됨
// 객체의 - 연산은 +와 똑같이 동작한다.
```



#### 4.4.3 Boolean -> 숫자

암시적 변환은 불리언을 + 연산할 때 빛난다

```javascript
function onlyOne(a, b, c) {
  return !!((a && !b && !c) || (!a && b && !c) || (!a && !b && c));
}

var a = true;
var b = false;
onlyOne(a, b, b); // true
onlyOne(a, b, b); // true

onlyOne(a, b, a); // false
```

일단 이런 코드는 (말로) 좀 맞아야 한다. 나눠서 쓰던가 하지..

아무튼 세 인자 중 하나만 truthy인지 아닌지 확인하느 함수이고 truthy 체크시 암시적 강제변환을 하고 최종반환값을 포함해서 다른 부분은 명시적 강제변환을 한다.

```javascript
// 암시적(implicit) 변환 version
function easyyOnlyOne() {
  let sum = 0;
  for (let i = 0; i < arguments.length; i++) {
   	if (arguments[i]) { // truthy만 취금
      sum += arguments[i]; // boolean은 암시적 변환으로 숫자로 된다 0 or 1
    }
  }
  return sum == 1;
}
// 명시적(explicit) 변환 version
function explicitOnlyOne() {
  let sum = 0;
  for (let i = 0; i < arguments.length; i++) {
    sum += Number(!!arguments[i]); // or Boolean(arguments[i])
  }
  return sum == 1;
}
```



#### 4.4.4 * -> Boolean 암시적 강제변환

boolean으로의 강제변환

1. if 문의 조건 표현식
2. for() 두 번째 조건 표현식
3. while() do...while() 루프의 조건 표현식
4. ? : 삼항 연산 시 첫 번째 조건 표현식
5. || 및 &&의 좌측 피연산자

```javascript
var a = 42;
var b = "abc";
var c;
var d = null;

위의 5가지에서 조건문 실행이 안되는 것들
- null
- undefined
- ""
- 0
- NaN
```

##### 4.4.5 && 와 ||

자바스크립트에서는 위의 연산자의 결과값이 논리값이 아니라 한 쪽의 값이다.

```javascript
var a= 42;
var b = "abc";
var c = null;

a || b; // 42 차례대로 피연산자가 truthy이면 끝남
a && b; // "abc"

c || b; // "abc";
c && b; // null > false가 되는 것을 내보낸다

a ? a : b == a || b
a ? b : a == a && b
```



##### 4.4.6 심벌의 강제변환

심볼의 강제변환 함정

```javascript
var s1 = Symbol("good");
String(s1); // output: 'Symbol(good)'

var s2 = Symbol("bad");
s2 + ""; // output : TypeError
```

symbol은 절대 숫자로 변환되지 않지만 희한하게도 불리언 값으로는 명시적 암시적 강제변환이 가능함

#### 4.5 느슨/엄격 동등 비교

느슨한(loose Equals)는 == 연산자를, 엄격한(strict) 동등 비교는 === 연산자를 각각 사용

느슨함과 엄격함의 차이? ==는 강제변환을 허용 ===는 강제변환을 허용 안함

강제변환이 필요하다면 ==, 아니라면 ===을 사용하자

##### 4.5.2 추상 동등비교

> 42와 42는 동등하지만 NaN, +0 and -0은 동등하지 않다

객체의 동등비교는 두 객체가 똑같은 값에 대한 레퍼런스일 경우 동등



##### 비교하기: 문자열 -> 숫자

```javascript
var a = 42;
var b = "42";

a == b; // true > 강제변환하여 true 비교
a === b; // false > 강제변환을 허용하지 않음

// 그럼, a가 문자열로 되어서 변환되는 걸까, 아님 문자열이 숫자로 되는걸까
// 한 쪽이 숫자면 문자열이 Number()로 변환되어 계산이 된다.
```

boolean 비교

```javascript
var a = "42";
var b = true;
a == b; // false
// true는 ToNumber(1)로 변환되고 a는 ToNumber(42)로 변환됨
```

x가 boolean이면 ToNumber() 하고 비교, y가 boolean이면 ToNumber(y) 연산 후 비교

== true, == false 같은 코드는 안 쓰는 것이 좋다고 함



##### null -> undefined

둘 다 null, undefined면 true를 반환

##### 객체 -> 비객체 비교

```javascript
var a = 42;
var b = [42]; // "42"로 변환된다 > 42로 변환
a == b; // true
```

```javascript
var a = "abc";
var b = Object(a); // new String(a)과 같음 비교시 언박싱 된다.

a === b; // false
a == b; // true
```



```javascript
var a = null;
var b = Object(a); // Object()와 동일
a == b; // false

var c = undefined;
var d = Object(c); // Object()와 동일
c == d // false

var e = NaN;
var f = Object(e); // new Number(e)와 같음
e == f; // false

// null, undefined는 객체 래퍼가 없으므로 박싱이 안됨
```



##### 4.5.3 희귀 사례

```javascript
Number.prototype.valueOf = function() {
  return 3;
}
new Number(2) == 3; // true > new Number(2)는 ToPrimitive() 연산을 하기 때문에 valueOf() 함수를 호출한다.
```

##### Falsy 비교

==의 암시적 강제변환을 힐난하는 사람들은 falsy값 비교에 관한 이상한 로직

```javascript
```

#### 4.6 추상 관계 비교

추상적 비교는 피연산자 모두 문자열 일 때와 그 외의 경우 두 가지로 나뉜다.

두 피연산자에 대하여 ToPrimitive  강제변환을 실시

결과로 어느 한쪽이라도 문자열이 아닐 경우 양쪽 모두 ToNumber로 강제변환하여 숫자값으로 만들어서 비교

```javascript
var a = [42];
var b = ["43"];
a < b; // true
b < a; // false
```

둘 다 문자열이라면? 알파벳 순서로 비교

```javascript
var a = ["42"];
var b = ["043"];
a < b; // false
// a, b를 강제변환하면 ToPrimitive에 의해 문자열로 변화이 되고 어휘순으로 42가 더 크다

var c = [4, 2];
var d = [0, 4, 3];
c < b; // false
// 이 또한 "4,2", "0,4,3"으로 변환이 되기 때문에 같다

var a = {
  b: 42
};
var b = {
  b : 43
};
a < b; // false > 이 경우 둘 다 객체이기 때문에 레퍼런스가 같아야 한다
// a <= b는 b < a 평과 결과를 부정하돌ㅗㄱ 명세에 기술되어 있다 그래서.. b < a는 false이므로 a <= b는 true가 된다.
```

자바스크립트는 <= 더 크지 않은 이라고 해석한다. a >= b는  b <= a로 재해석한 후 동일한 추론을 적용



#### 정리

명시적, 암시적 2가지의 타입변환을 알아봤다. 자바스크립트에서 강제변환은 꼭 학습해야할 부분인거 같다. 많은 부분들이 쓰이고 많은 부분들로 인해서 생각지 못한 결과를 만든다. 그렇기 때문에 필요한 학습내용인거 같다.

>개발자들이 쉽게 배우고 이해할 수 있는 코드를 짜자!!





































;