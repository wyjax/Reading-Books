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

