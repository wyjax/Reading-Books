## Chapter. 3 함수



#### 작게 만들어라

함수는 만드는 첫 번째 규칙은 '작게' 만드는 것이다. 두 번째는 '더 작게',,,ㅋㅋ

```java
public static String renderPage(PageData pageData, boolean isSuite) {
	boolean isTestPage = pageData.hasAttribute("Test");
    
    if (isTestPage) {
        WikiPage testPage = pageData.getWikiPAge();
        StringBuffer newPageContent = new StringBuffer();
       	includeSetupPages(testPage, newPageContent, isSuite);
        newPageContent.append(pageData.getContent());
        pageData.setContent(newPageContent.toString());
    }
    
    return pageData.getHtml();
}

// ==================== 리팩토링

public static String renderPageWithSetipAndTeardowns(PageData pageData, boolean isSuite) {
    if (isTestPage) {
        includeSetupAndTeardownPages(pageData, isSuite);
    }
    
    return pageData.getHtml();
}
```



##### 블록과 들여쓰기

if/else문 while문 등에 들어가는 블록은 한 줄이어야 한다는 뜻이다. 한 줄로 간결하게 쓰려면 함수의 이름을 잘 지어야 한다.

 

#### 한 가지만 해라 !

> 함수는 한 가지를 해야 한다. 그 한 가지를 잘 해야 한다. 그 한 가지만을 해야 한다. >> 정기야 명심하자



#### 함수 당 추상화 수준은 하나로!



#### 서술적인 이름을 사용하라

서술적인 이름을 사용하면 개발을 하면서 설계를 하는데 도움이 된다.



#### 함수 인수

함수에서 이상적인 인수 개수는 0개이다. 다음은 1개이고, 다음은 2개이고, 3개는 가능한 피하는 편이 좋다. 4개 이상은

특별한 이유가 필요하다.

테스트 관점에서 보면 인수는 더 어렵다. 갖가지 인수 조합으로 함수를 검증하는 테스트 케이스를 작성한다고 하면 인수가 없는 테스트가 가장 간단하고, 인수가 많아질수록 복잡해진다.

```java
void tranform(StringBuffer sb); // 보다는
StringBuffer tranform(StringBuffer sb); // 가 더 좋다. 입력인수를 그대로 돌려주는 함수라 함수라 할지라도
// 변환 함수 형식을 다르는 편이 좋다.
```



##### 많이 쓰는 단항 형식

함수에 인수 1개를 넘기는 이유로 갖아 흔한 경우는 두 가지 !

1. 인수에 질문을 던지는 경우 >> public boolean fileExists("MyFile")

2. 인수를 다른 형식으로 변환 >> InputStream fileOpen("MyFile") 라는 함수는 String을 InputStream으로 변경

    **입력 인수를 변환하는 함수라면 변환 결과는 반환값으로 돌려준다.**

    ex) StringBuffer transForm(StringBuffer in)



##### 플래그 인수

플래그 인수 추하다고 한다. 함수로 부울 값을 넘기는 것은 정말 안 좋은 거다. 왜냐하면 함수가 한꺼번에 여러 가지 일을 처리한다고 대놓고 말하는셈이라서



##### 이항 함수

인수가 2개인 함수는 인수가 1개인 함수보다 이해하기 어렵다. 

```java
writeField(name);
writeField(outputStream, name);
// 인수가 2개인 것보다 1개인 것이 이해하기가 더 쉽다. 인수는 무조건 작을수록 좋다.
```

하지만 인수가 적절한 경우도 있다. Point p = new Point(x, y) 라는 것은 일단 좌표는 x, y 2개의 좌표를 일반적으로 생각하기 때문이다. 위으 writeField의 outputStream, name은 한 값을 표현하지도 않고, 자연적인 순서도 다르다.



##### 삼항 함수

삼항 함수는 이항 함수보다 이해하기가 어렵다...



##### 인수 목록

떄로는 인수가 가변적인 함수가 필요하다. 이 예로는 String.format 함수가 적절하다. 

```java
String.format("%s worked %.2f", name, hours);

// 선어눕
public String format(String format, Object... args);
```

위 예제처럼하면 가변 인수 전부를 동등하게 취급하면 List형 인수 ßß하나로 취급할 수 있다.



##### 동사와 키워드

함수의 의도나 인수의 순서와 의도를 제대로 표현하려면 좋은 함수 이름이 필수다. 단항 함수는 함수와 인수가 동사/명사 쌍을 이뤄야 한다. 예를 들면 write(name)은 누구나 바로 이해한다. '이름을 쓴다'라는 뜻이 된다. 여기에서 더 나은 이름은 writeField(name)이다. 그러면 이름이 필드라는 사실이 분명히 드러난다. 

또 다른 것은 함수 이름에 키워드를 추가하는 형식이다. assertEquals() 보다 assertExpectedEqualsActual(expected, actual)이 더 좋다. 그러면 인수 순서를 기억할 필요가 없어진다.



#### 부수 효과를 일으키지 말아라

함수에서 한 가지만 하겠다고 해놓고 남몰래 다른 짓도 하니까. 때로는 예상치 못하게 클래스 변수를 수정한다. 때로는 함수로 넘어온 인수나 시스템 전역변수를 수정한다. 어느 쪽이든 교활하고 해로운 거짓 말이다.

```java
public class UserValidator {
    private Cryptographer cryptographer;
    
    public boolean checkPassword(String userName, String password) {
        User user = UserGateway.findByName(userName);
        
        if (user != User.NULL) {
            String codedPharase = user.getPhraseEncodedByPassword();
            String phrase = cryptographer.decrypt(codePhrase, password);
            
            if ("Valid Password".equals(phrase)) {
                Session.initialize(); // 부수 효과를 일으키는 부분
                return true;
            }
        }
        
        return false;
    }
}
/*
checkPassword 함수는 이름 그대로 암호를 확인한다. 이름만 봐서는 안에서 session을 초기화한다는 느낌을 받을 수가 없고 유추할 수도 없다. 그래서 함수를 호출하는 사용자는 사용자를 인증하면서 세션정보를 초기화하기까지 된다.

!!! 만약 세션을 초기화해야 한다면 함수이름에 명시하도록 해야 한다.
*/

public boolean checkPasswordAndInitializeSession(...); 
// 으로 하면 된다. 물론 함수가 1가지만 해야하는 것을 위반한다.
```

ñ